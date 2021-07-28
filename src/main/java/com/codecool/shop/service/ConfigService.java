package com.codecool.shop.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigService {
    private String daoType;
    private String databaseUrl;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;
    private final String propFileName = "connection.properties";
    InputStream inputStream;

    public String getDaoType() throws IOException {
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            daoType = prop.getProperty("dao");
            System.out.println(daoType);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return daoType;
    }

    public String getDatabaseUrl() throws IOException {
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            databaseUrl = prop.getProperty("url");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return databaseUrl;
    }

    public String getDatabaseName() throws IOException {
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            databaseName = prop.getProperty("database");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return databaseName;
    }

    public String getDatabaseUser() throws IOException {
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            databaseUser = prop.getProperty("user");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return databaseUser;
    }

    public String getDatabasePassword() throws IOException {
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            databasePassword = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return databasePassword;
    }
}
