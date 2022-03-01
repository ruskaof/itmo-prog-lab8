package com.ruskaof.client.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {
    private final String filename;

    public FileManager(String filename) {
        this.filename = filename;
    }

    public String read() throws IOException {
        StringBuilder strData = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((line = bufferedReader.readLine()) != null) {
                strData.append(line);
            }
        }
        return strData.toString();
    }

    public void save(String text) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(filename)) {
            printWriter.write(text);
        }
    }
}
