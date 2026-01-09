package fr.fms.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Password hashing helper.
 *
 * Uses SHA-256 to hash raw passwords.
 *
 * Note:
 * - Lightweight implementation for eval purposes.
 * - In real life, consider using stronger algorithms like bcrypt or Argon2
 * (because attackers also have computers)
 */
public final class PasswordHasher {

    /** Prevent instantiation. */
    private PasswordHasher() {
        // Utility class - prevent instantiation
    }

    /**
     * Hashes a raw string using SHA-256 & returns a hex representation.
     *
     * @param raw raw input
     * @return SHA-256 hash as a lowercase hex string
     * @throws IllegalStateException if the hashing algorithm is not available
     */
    public static String sha256(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(raw.getBytes(StandardCharsets.UTF_8));

            // Convert bytes to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new IllegalStateException("Cannot hash password.", e);
        }
    }
}
