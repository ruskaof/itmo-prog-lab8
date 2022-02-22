package com.ruskaof.client.utility;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This class is used for all the user input: keyboard and script execution
 */
public class UserInputManager {
    private BufferedReader bufferedReader;
    private final Scanner scanner = new Scanner(System.in);
    private boolean readingFromFile = false;

    /**
     * This field is used to prevent recursion
     */
    private final HashSet<File> forbiddenFiles = new HashSet<>();

    public String nextLine() {
        if (readingFromFile) {
            try {
                String nextln = bufferedReader.readLine();
                if (nextln == null) {
                    forbiddenFiles.clear();
                    readingFromFile = false;
                    return nextLine();
                } else {
                    return nextln;
                }
            } catch (IOException e) {
                // never throws exception
                e.printStackTrace();
            }

        } else {
            return scanner.nextLine();
        }

        // never returns ""
        return "";
    }

    public void connectToFile(File file) throws FileNotFoundException {
        if (forbiddenFiles.contains(file)) {
            readingFromFile = false;
        } else {
            forbiddenFiles.add(file);
            readingFromFile = true;
            bufferedReader = new BufferedReader(new FileReader(file));
        }
    }


}
