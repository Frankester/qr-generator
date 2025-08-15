package com.example.api.models.qrgenerators;

import com.example.api.config.MainConfiguration;
import com.example.api.exceptions.DirectoryCreationException;
import com.example.api.models.QRLink;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

public class PngQRGenerator implements QRGeneratorStrategy{

    private final Logger logger = LoggerFactory.getLogger(PngQRGenerator.class);

    @Override
    public String generateQR(int colorBg, int colorQR, int size,  QRLink linkUrl) throws DirectoryCreationException {
        File file = QRCode
                        .from(linkUrl.getUrl())
                        .withColor(colorQR, colorBg)
                        .withSize(size, size)
                        .withErrorCorrection(ErrorCorrectionLevel.M)
                        .file();

        String hashName =  RandomHashGenerator.generateRandomHashName();


        Path filePath = MainConfiguration.getfolderQrFilesPath()
                .resolve(hashName+".png");

        boolean isSaved = file.renameTo(filePath.toFile());

        if(isSaved){
            logger.info("Archivo guardado exitosamente!!");
            return "/qrs/"+hashName;
        } else {
            logger.error("No se pudo guardar el archivo en la carpeta de QRs configurada!!");
            return null;
        }

    }
}
