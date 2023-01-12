package com.example.api.models.qrGenerators;

import com.example.api.models.QRLink;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PngQRGenerator implements QRGeneratorStrategy{


    @Override
    public String generateQR(int colorBg, int colorQR, QRLink linkUrl) {
        File file = QRCode
                        .from(linkUrl.getUrl())
                        .withColor(colorQR, colorBg)
                        .withErrorCorrection(ErrorCorrectionLevel.M)
                        .file();

        String hashName = "qr-"+ RandomStringUtils.randomAlphabetic(7);

        Path filePath = Paths
                .get("src","main", "java", "QR-Images")
                .resolve(hashName+".png");

        boolean isSaved = file.renameTo(filePath.toFile());

        if(isSaved){
            return "/qrs/"+hashName;
        }

        return null;
    }
}
