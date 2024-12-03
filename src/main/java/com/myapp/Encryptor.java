package com.myapp;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class Encryptor {
    private static final byte[] KEY_BYTES = "SecretKey1234567".getBytes(); 
    private static final SecretKey SECRET_KEY = new SecretKeySpec(KEY_BYTES, "AES");

    public void encryptFile(String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
        //System.out.println("File encrypted successfully. Output: " + outputFile);
    }

    public void decryptFile(String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);

        try (FileInputStream fis = new FileInputStream(inputFile);
             CipherInputStream cis = new CipherInputStream(fis, cipher);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        //System.out.println("File decrypted successfully. Output: " + outputFile);
    }
}
