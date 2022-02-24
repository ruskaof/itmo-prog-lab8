package com.ruskaof.client.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class is used for all the user input: keyboard and script execution
 */
public class UserInputManager {
    private BufferedReader bufferedReader;
    private final Scanner scanner = new Scanner(System.in);
    private boolean readingFromFile = false;


    public String nextLine() {
        if (readingFromFile) {
            try {
                String input = bufferedReader.readLine();
                if (input == null) {
                    readingFromFile = false;
                    return nextLine();

                } else {
                    // to prevent recursion "execute_script" command is banned while reading from file
                    if (input.startsWith("execute_script") && readingFromFile) {
                        return nextLine();
                    }
                    return input;
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
        bufferedReader = new BufferedReader(new FileReader(file));
        readingFromFile = true;
    }
}
