package com.wing.java.util.security;

public class Encrypt
{
  public static final String HASH_ALGORITHM = "SHA-1";
  public static final int HASH_INTERATIONS = 1024;
  private static final int SALT_SIZE = 8;

//  public static void entryptPassword(User user)
//  {
//    byte[] salt = Digests.generateSalt(8);
//    user.setSalt(Encodes.encodeHex(salt));
//
//    byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, 1024);
//    user.setPassword(Encodes.encodeHex(hashPassword));
//  }

  public static String entryptPassword(String plainPassword, String salt)
  {
    byte[] salts = Encodes.decodeHex(salt);
    byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salts, 1024);
    return Encodes.encodeHex(hashPassword);
  }
}