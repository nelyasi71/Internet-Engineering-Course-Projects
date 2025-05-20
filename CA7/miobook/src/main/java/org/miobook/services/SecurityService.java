package org.miobook.services;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private static final int HASH_ITERATIONS = 1024;

    public String hashPassword(String plainPassword) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String hash = new Sha256Hash(plainPassword, salt, HASH_ITERATIONS).toHex();
        return salt + ":" + hash;
    }

}
