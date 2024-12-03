package com.myapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EncryptorTest {
    private Encryptor encryptor;
    private File inputFile;
    private File encryptedFile;
    private File decryptedFile;

    public EncryptorTest() {
        System.out.println("Constructor: Test Class Instance");
    }

    @BeforeAll
    static void X() {
        System.out.println("Before All");
    }

    @AfterAll
    static void Y() {
        System.out.println("After All. Goodbye!!");
    }

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("BeforeEach is called!");
        encryptor = new Encryptor();
        // temp input file with sample content
        inputFile = File.createTempFile("test-input", ".txt");
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("This is a simple sentence");
        }

        encryptedFile = File.createTempFile("test-encrypted", ".txt");
        decryptedFile = File.createTempFile("test-decrypted", ".txt");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("AfterEach is called!");
        // Delete temp files after each test
        inputFile.delete();
        encryptedFile.delete();
        decryptedFile.delete();
    }

    @Test
    public void testEncryptFile() throws Exception {
        System.out.println("Unit test 1 is called!");
        encryptor.encryptFile(inputFile.getAbsolutePath(), encryptedFile.getAbsolutePath());
        assertTrue(encryptedFile.exists());
        assertTrue(Files.size(Paths.get(encryptedFile.getAbsolutePath())) > 0, "Encrypted file should not be empty");
    }

    @Test
    public void testDecryptFile() throws Exception {
        System.out.println("Unit test 2 is called!");
        encryptor.encryptFile(inputFile.getAbsolutePath(), encryptedFile.getAbsolutePath());
        encryptor.decryptFile(encryptedFile.getAbsolutePath(), decryptedFile.getAbsolutePath());
        String originalContent = Files.readString(Paths.get(inputFile.getAbsolutePath()));
        String decryptedContent = Files.readString(Paths.get(decryptedFile.getAbsolutePath()));
        assertEquals(originalContent, decryptedContent, "Decrypted content should match the original content");
    }

    @Test
    public void testEncryptAndDecryptPreservesContent() throws Exception {
        System.out.println("Unit test 3 is called!");
        encryptor.encryptFile(inputFile.getAbsolutePath(), encryptedFile.getAbsolutePath());
        encryptor.decryptFile(encryptedFile.getAbsolutePath(), decryptedFile.getAbsolutePath());
        String originalContent = Files.readString(Paths.get(inputFile.getAbsolutePath()));
        String decryptedContent = Files.readString(Paths.get(decryptedFile.getAbsolutePath()));
        assertEquals(originalContent, decryptedContent, "Decrypted content should match the original content");
    }

    @Test
    public void testEncryptDecryptEmptyFile() throws Exception {
        System.out.println("Unit test 4 is called!");
        File emptyFile = File.createTempFile("empty", ".txt");
        emptyFile.deleteOnExit(); 
        encryptor.encryptFile(emptyFile.getAbsolutePath(), encryptedFile.getAbsolutePath());
        encryptor.decryptFile(encryptedFile.getAbsolutePath(), decryptedFile.getAbsolutePath());
        long decryptedFileSize = Files.size(Paths.get(decryptedFile.getAbsolutePath()));
        assertEquals(0, decryptedFileSize, "The decrypted file should be empty");
    }

    @Test
    public void testEncryptFileHandlesExceptions() {
        System.out.println("Unit test 5 is called!");
        assertThrows(IOException.class, () -> {
            encryptor.encryptFile("nonexistent_input_file", "output_file");
        });
    }

    // special characters, large file, empty output filepath, incorrect input file path, path with spaces.....
}
