package com.example.api.models.qrGenerators;

import com.example.api.config.MainConfiguration;
import com.example.api.models.QRLink;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SvgQRGenerator implements QRGeneratorStrategy {

    @Override
    public String generateQR(int colorBg, int colorQR, int size, QRLink linkUrl) throws Exception {


        String hashName =  RandomStringUtils.randomAlphabetic(7);

        Path filePath = MainConfiguration.getfolderQrFilesPath().resolve(hashName+".svg");

        OutputStream outs = new FileOutputStream(filePath.toFile());

        QRCode
                .from(linkUrl.getUrl())
                .withColor(colorQR, colorBg)
                .withSize(size, size)
                .withErrorCorrection(ErrorCorrectionLevel.M)
                .svg(outs);


        return "/qrs/"+hashName;
    }
}
