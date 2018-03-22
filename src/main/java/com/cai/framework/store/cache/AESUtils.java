package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static final String CHARSET_NAME = "UTF-8";
    private static Cipher cipher;
    private static SecretKeySpec key;
    private static AlgorithmParameterSpec spec;
    public static final String SEED_16_CHARACTER = "网络错误，请重试！";

    public AESUtils() {
    }

    private static void initAES() {
        try {
            if(cipher == null) {
                MessageDigest digest = MessageDigest.getInstance("SHA-384");
                digest.update("网络错误，请重试！".getBytes("UTF-8"));
                byte[] keyBytes = new byte[32];
                byte[] tmpDigest = digest.digest();
                System.arraycopy(tmpDigest, 0, keyBytes, 0, keyBytes.length);
                cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                key = new SecretKeySpec(keyBytes, "AES");
                spec = getIV(tmpDigest);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            builder.append(String.format("%02x", new Object[]{Byte.valueOf(b)}));
        }

        return builder.toString();
    }

    public static AlgorithmParameterSpec getIV(byte[] digest) {
        byte[] iv = new byte[16];
        System.arraycopy(digest, 32, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public static String encrypt(String plainText) throws Exception {
        byte[] encrypted = encrypt(plainText.getBytes("UTF-8"));
        String encryptedText = new String(encrypted, "UTF-8");
        return encryptedText;
    }

    public static String decrypt(String cryptedText) throws Exception {
        byte[] decrypted = decrypt(cryptedText.getBytes("UTF-8"));
        String decryptedText = new String(decrypted, "UTF-8");
        return decryptedText;
    }

    public static byte[] encrypt(byte[] plainText) throws Exception {
        initAES();
        cipher.init(1, key, spec);
        byte[] encrypted = cipher.doFinal(plainText);
        encrypted = Base64.encode(encrypted, 2);
        return encrypted;
    }

    public static byte[] decrypt(byte[] cryptedText) throws Exception {
        initAES();
        cipher.init(2, key, spec);
        byte[] bytes = Base64.decode(cryptedText, 2);
        byte[] decrypted = cipher.doFinal(bytes);
        return decrypted;
    }

    public static File encrypt(File srcFile, File encodeFile, String decodeKey) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if(srcFile != null && encodeFile != null && srcFile.exists() && encodeFile.exists() && decodeKey != null) {
            FileInputStream fis = new FileInputStream(srcFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(encodeFile.getAbsolutePath());
            SecretKeySpec sks = new SecretKeySpec(decodeKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(1, sks);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            byte[] d = new byte[8];

            int b;
            while((b = fis.read(d)) != -1) {
                cos.write(d, 0, b);
            }

            cos.flush();
            cos.close();
            fis.close();
            return encodeFile;
        } else {
            return null;
        }
    }

    public static File decrypt(File encodeFile, File decodeFile, String decodeKey) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        try {
            if(decodeFile == null || encodeFile == null || !decodeFile.exists() || !encodeFile.exists() || decodeKey == null) {
                return null;
            }

            FileInputStream fis = new FileInputStream(encodeFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(decodeFile.getAbsolutePath());
            SecretKeySpec sks = new SecretKeySpec(decodeKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(2, sks);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            byte[] d = new byte[8];

            int b;
            while((b = cis.read(d)) != -1) {
                fos.write(d, 0, b);
            }

            fos.flush();
            fos.close();
            cis.close();
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return decodeFile;
    }
}

