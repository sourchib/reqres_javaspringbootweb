package com.juaracoding.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class XMLFileConfig {
    public static String copyResource(String res, Class c) throws IOException {
        String path = System.getProperty("user.dir") +File.separator+ res;
        InputStream src = c.getResourceAsStream(res);
        Files.copy(src, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);

        return path;
    }
}