package com.chinaebi.utils;


import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RsaEncrypto
{

  public static String encryptBASE64(byte[] key) throws Exception {
    return Base64.encode(key);
  }

  public static byte[] decryptBASE64(String key) throws Exception {
    return Base64.decode(key);
  }

  public static String RSAsign(byte[] data, String privateKey) throws Exception
  {
    byte[] keyBytes = decryptBASE64(privateKey);

    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

    Signature signature = Signature.getInstance("MD5withRSA");
    signature.initSign(priKey);
    signature.update(data);

    return encryptBASE64(signature.sign());
  }

  public static boolean RSAverify(byte[] data, String publicKey, String sign)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(publicKey);

    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    PublicKey pubKey = keyFactory.generatePublic(keySpec);

    Signature signature = Signature.getInstance("MD5withRSA");
    signature.initVerify(pubKey);
    signature.update(data);

    return signature.verify(decryptBASE64(sign));
  }

  public static byte[] RSAdecryptByPrivateKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);

    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(2, privateKey);

    return cipher.doFinal(data);
  }

  public static byte[] RSAdecryptByPublicKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);

    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicKey = keyFactory.generatePublic(x509KeySpec);

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(2, publicKey);

    return cipher.doFinal(data);
  }

  public static byte[] RSAencryptByPublicKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);

    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicKey = keyFactory.generatePublic(x509KeySpec);

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(1, publicKey);

    return cipher.doFinal(data);
  }

  public static byte[] RSAencryptByPrivateKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);

    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(1, privateKey);

    return cipher.doFinal(data);
  }

//  public static String getRSAPrivateKey(Map<String, Object> keyMap) throws Exception
//  {
//    Key key = (Key)keyMap.get("RSAPrivateKey");
//    return encryptBASE64(key.getEncoded());
//  }
//
//  public static String getRSAPublicKey(Map<String, Object> keyMap) throws Exception
//  {
//    Key key = (Key)keyMap.get("RSAPublicKey");
//
//    return encryptBASE64(key.getEncoded());
//  }

//  public static Map<String, Object> initRSAKey() throws Exception {
//    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//    keyPairGen.initialize(1024);
//
//    KeyPair keyPair = keyPairGen.generateKeyPair();
//
//    RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
//
//    RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
//
//    Map keyMap = new HashMap(2);
//
//    keyMap.put("RSAPublicKey", publicKey);
//    keyMap.put("RSAPrivateKey", privateKey);
//    return keyMap;
//  }
}