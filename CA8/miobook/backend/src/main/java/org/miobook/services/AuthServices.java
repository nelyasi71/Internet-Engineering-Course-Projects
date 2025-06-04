package org.miobook.services;

import lombok.Getter;
import org.json.JSONObject;
import org.miobook.commands.OAuth;
import org.springframework.beans.factory.annotation.Value;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.models.User;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.JwtRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Getter
public class AuthServices implements Services {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserServices userServices;

    public JwtRecord login(Login dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());

        if (userOpt.isEmpty()) {
            throw new MioBookException("username", "User not found.");
        }

        User user = userOpt.get();

        String hashedPassword = user.getPassword();
        if (hashedPassword == null || !hashedPassword.contains(":")) {
            throw new IllegalArgumentException("Invalid stored password format");
        }
        String[] parts = hashedPassword.split(":");
        String salt = parts[0];
        String storedHash = parts[1];
        String computedHash = new Sha256Hash(dto.getPassword(), salt, 1024).toHex();
        if (!storedHash.equals(computedHash)) {
            throw new MioBookException("password", "Wrong password.");
        }

        return securityService.generateJwt(user);
    }

    public void logout(Logout dto) {}

    public JwtRecord handleOauth(OAuth dto) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://oauth2.googleapis.com/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "code=" + URLEncoder.encode(dto.getCode(), StandardCharsets.UTF_8) +
                                    "&client_id=" + clientId +
                                    "&client_secret=" + clientSecret +
                                    "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                                    "&grant_type=authorization_code"))
                    .build();

            HttpResponse<String> tokenResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
            JSONObject tokenJson = new JSONObject(tokenResponse.body());
            String accessToken = tokenJson.getString("access_token");

            HttpRequest userInfoRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.googleapis.com/oauth2/v2/userinfo"))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> userInfoResponse = client.send(userInfoRequest, HttpResponse.BodyHandlers.ofString());
            JSONObject userJson = new JSONObject(userInfoResponse.body());

            String email = userJson.getString("email");
            String username = userJson.optString("name", email);
            User user = userServices.getOrCreate(email, username);

            return securityService.generateJwt(user);

        } catch (Exception e) {
            throw new MioBookException("google-auth", "Google login failed: " + e.getMessage());
        }
    }
}
