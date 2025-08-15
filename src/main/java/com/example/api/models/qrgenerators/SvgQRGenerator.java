package com.example.api.models.qrgenerators;

import com.example.api.config.MainConfiguration;
import com.example.api.models.QRLink;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class SvgQRGenerator implements QRGeneratorStrategy {

    @Override
    public String generateQR(int colorBg, int colorQR, int size, QRLink linkUrl) throws Exception {


        String hashName =  RandomHashGenerator.generateRandomHashName();

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
