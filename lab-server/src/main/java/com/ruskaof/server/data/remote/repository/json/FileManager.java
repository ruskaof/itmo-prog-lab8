package com.ruskaof.server.data.remote.repository.json;

import java.io.*;

public class FileManager {
    private final String studyGroupsFilename;
    private final String usersFilename;

    public FileManager(String studyGroupsFilename, String usersFilename) {
        this.studyGroupsFilename = studyGroupsFilename;
        this.usersFilename = usersFilename;
    }

    public String readStudyGroups() throws IOException {
        StringBuilder strData = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(studyGroupsFilename))) {
            while ((line = bufferedReader.readLine()) != null) {
                strData.append(line);
            }
        }
        return strData.toString();
    }

    public String readUsers() throws IOException {
        StringBuilder strData = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(studyGroupsFilename))) {
            while ((line = bufferedReader.readLine()) != null) {
                strData.append(line);
            }
        }
        return strData.toString();
    }

    public void save(String studyGroupsText, String usersText) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(studyGroupsFilename)) {
            printWriter.write(studyGroupsText);
        }

        try (PrintWriter printWriter = new PrintWriter(usersFilename)) {
            printWriter.write(usersText);
        }
    }
}
