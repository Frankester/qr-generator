package com.example.api.utils;

import java.io.File;
import java.nio.file.Paths;

public class FileCleaner {

    public static void cleanFolder(String folderName){
        File folderQrs =  Paths
                .get("src","main", "java", folderName)
                .toFile();

        if (folderQrs.exists() && folderQrs.isDirectory()){
            File[] files = folderQrs.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }

    }
}
