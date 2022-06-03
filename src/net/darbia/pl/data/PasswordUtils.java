// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.data;

import java.security.SecureRandom;
import java.util.Base64;
import java.security.GeneralSecurityException;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.Arrays;
import javax.crypto.spec.PBEKeySpec;
import java.util.Random;

public final class PasswordUtils
{
    private static final Random RANDOM;
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    
    private PasswordUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    public static String getSalt(final int length) {
        final StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            returnValue.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(PasswordUtils.RANDOM.nextInt("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length())));
        }
        return new String(returnValue);
    }
    
    private static byte[] hash(final char[] password, final byte[] salt) {
        final PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
        Arrays.fill(password, '\0');
        try {
            final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException ex) {}
        catch (InvalidKeySpecException exception) {
            throw new AssertionError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, exception.getMessage()), (Throwable)exception);
        }
        finally {
            spec.clearPassword();
        }
    }
    
    public static String generateSecurePassword(final String password, final String salt) {
        final byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        final String returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }
    
    public static boolean verifyUserPassword(final String providedPassword, final String securedPassword, final String salt) {
        final String newSecurePassword = generateSecurePassword(providedPassword, salt);
        final boolean returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        return returnValue;
    }
    
    static {
        RANDOM = new SecureRandom();
    }
}
