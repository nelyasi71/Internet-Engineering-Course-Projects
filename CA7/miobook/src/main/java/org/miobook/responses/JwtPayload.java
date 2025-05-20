package org.miobook.responses;

public record JwtPayload(
        String issuer,
        String subject,
        long issuedAt,
        long expiration,
        String username,
        String email
) {}
