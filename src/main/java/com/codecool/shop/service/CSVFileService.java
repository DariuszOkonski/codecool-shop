package com.codecool.shop.service;

import java.io.FileWriter;
import java.io.IOException;

public class CSVFileService implements FileService {
    private final String PATH = "src/main/webapp/files/csv/";

    @Override
    public void saveToFile(String fileName, String data) {
        try {
            FileWriter fileWriter = new FileWriter(PATH + fileName, true);
            fileWriter.write(data+"\n");
            fileWriter.close();
            System.out.println("successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("File error occurred.");
            e.printStackTrace();
        }
    }
}
