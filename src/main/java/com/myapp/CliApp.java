package com.myapp;

import java.util.Scanner;

public class CliApp {
    public static void main(String[] args) {
        Encryptor encryptor = new Encryptor();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter operation (encrypt/decrypt):");
            String operation = scanner.nextLine().trim().toLowerCase();

            System.out.println("Enter input file path:");
            String inputFile = scanner.nextLine().trim();

            System.out.println("Enter output file path:");
            String outputFile = scanner.nextLine().trim();

            if ("encrypt".equals(operation)) {
                encryptor.encryptFile(inputFile, outputFile);
            } else if ("decrypt".equals(operation)) {
                encryptor.decryptFile(inputFile, outputFile);
            } else {
                System.out.println("Invalid operation. Use 'encrypt' or 'decrypt'.");
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
