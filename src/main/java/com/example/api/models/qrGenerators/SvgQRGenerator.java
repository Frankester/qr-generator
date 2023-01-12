package com.example.api.models.qrGenerators;

import com.example.api.models.QRLink;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SvgQRGenerator implements QRGeneratorStrategy {

    private final Logger log = LoggerFactory.getLogger(SvgQRGenerator.class);

    @Override
    public String generateQR(int colorBg, int colorQR, QRLink linkUrl) {


        String hashName = "qr-" +  RandomStringUtils.randomAlphabetic(7);

        Path filePath = Paths
                .get("src","main", "java", "QR-Images")
                .resolve(hashName+".svg");


        try {
            OutputStream outs = new FileOutputStream(filePath.toFile());

            QRCode
                    .from(linkUrl.getUrl())
                    .withColor(colorQR, colorBg)
                    .withErrorCorrection(ErrorCorrectionLevel.M)
                    .svg(outs);
        } catch (Exception e) {

            log.error( e.getClass()+ ": " +  e.getLocalizedMessage());

            //on error return null
            return null;
        }

        return "/qrs/"+hashName;
    }
}
