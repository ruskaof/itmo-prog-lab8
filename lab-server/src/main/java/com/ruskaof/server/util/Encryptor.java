package com.ruskaof.server.util; // Java program to calculate MD2 hash value


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Encryptor {
    private Encryptor() {

    }

    public static String encryptThisString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);


            // ну что я поделаю, если тут блин нужна 16-ричная система счисления,
            // а 16 это МАГИЧЕСКОЕ ЧИСЛО
            StringBuilder hashText = new StringBuilder(no.toString(2 * 2 * 2 * 2));

            // а тут тупо по алгоритму нужно 32
            while (hashText.length() < 2 * 2 * 2 * 2 * 2) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
