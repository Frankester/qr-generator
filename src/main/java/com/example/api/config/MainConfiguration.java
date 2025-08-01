package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MainConfiguration {

    private static final String QRFilesFolderName = "QRImages";
    public static Path getfolderQrFilesPath(){
      return Paths
              .get("src","main", "java", QRFilesFolderName);
    }
}
