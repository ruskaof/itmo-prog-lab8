package com.ruskaof.client.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {
    private final String filename;
    private final PrintWriter printWriter;

    public FileManager(String filename) throws FileNotFoundException {
        this.filename = filename;
        File file = new File(filename);
        this.printWriter = new PrintWriter(file);
    }

    public String read() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        StringBuilder strData = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            strData.append(line);
        }
        return strData.toString();
    }

    public void save(String text) {
        printWriter.write(text);
        printWriter.close();
    }
}
