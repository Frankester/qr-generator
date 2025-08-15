package com.example.api.config;

import com.example.api.exceptions.DirectoryCreationException;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MainConfiguration {

    private static final String QR_FILES_FOLDER_NAME = "QRImages";

    public static Path getfolderQrFilesPath() throws DirectoryCreationException {
        Path path = Paths.get(System.getProperty("user.dir"), QR_FILES_FOLDER_NAME);
        try {
            Files.createDirectories(path); // Crea la carpeta si no existe
        } catch (IOException e) {
            throw new DirectoryCreationException("No se pudo crear carpeta: " + path);
        }
        return path;
    }
}
