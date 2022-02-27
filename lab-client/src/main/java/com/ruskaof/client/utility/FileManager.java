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
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        StringBuilder strData = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                strData.append(line);
            }
        } finally {
            bufferedReader.close();
        }
        return strData.toString();
    }

    public void save(String text) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(filename);
        printWriter.write(text);
        printWriter.close();
    }
}
