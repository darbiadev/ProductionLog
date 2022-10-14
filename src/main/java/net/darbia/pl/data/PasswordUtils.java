package net.darbia.pl.data;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtils {

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
      returnValue.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(
          PasswordUtils.RANDOM.nextInt(
              "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length())));
    }
    return new String(returnValue);
  }

  private static byte[] hash(final char[] password, final byte[] salt) {
    final PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
    Arrays.fill(password, '\0');
    try {
      final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException ignored) {
    } catch (InvalidKeySpecException exception) {
      throw new AssertionError(exception.getMessage() + " " + exception);
    } finally {
      spec.clearPassword();
    }
  }

  public static String generateSecurePassword(final String password, final String salt) {
    final byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
    return Base64.getEncoder().encodeToString(securePassword);
  }

  public static boolean verifyUserPassword(final String providedPassword,
      final String securedPassword, final String salt) {
    final String newSecurePassword = generateSecurePassword(providedPassword, salt);
    return newSecurePassword.equalsIgnoreCase(securedPassword);
  }

  static {
    RANDOM = new SecureRandom();
  }
}
