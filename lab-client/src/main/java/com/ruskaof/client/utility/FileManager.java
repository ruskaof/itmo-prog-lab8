package com.ruskaof.client.utility;

import java.io.*;

public class FileManager {
    private final String filename;
    private final File file;
    private final PrintWriter printWriter;

    public FileManager(String filename) throws FileNotFoundException {
        this.filename = filename;
        this.file = new File(filename);
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
