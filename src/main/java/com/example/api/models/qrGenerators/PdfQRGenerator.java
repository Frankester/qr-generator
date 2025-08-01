package com.example.api.models.qrGenerators;

import com.example.api.config.MainConfiguration;
import com.example.api.exceptions.FileNotFoundException;
import com.example.api.models.QRLink;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfQRGenerator implements QRGeneratorStrategy{


    @Override
    public String generateQR(int colorBg, int colorQR, int size, QRLink linkUrl) throws Exception {

        File file = QRCode
                .from(linkUrl.getUrl())
                .withColor(colorQR, colorBg)
                .withSize(size, size)
                .withErrorCorrection(ErrorCorrectionLevel.M)
                .file();

        String hashName = RandomStringUtils.randomAlphabetic(7);

        Path filePath = MainConfiguration.getfolderQrFilesPath().resolve(hashName+".pdf");

            PDDocument pdfDoc = new PDDocument();

            PDPage page = new PDPage();

            pdfDoc.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile(file.getAbsolutePath(),pdfDoc);

            PDPageContentStream contents = new PDPageContentStream(pdfDoc, page);

            PDRectangle mediaBox = page.getMediaBox();

            //center the qr image
            float startX = (mediaBox.getWidth() - pdImage.getWidth()) / 2;
            float startY = (mediaBox.getHeight() - pdImage.getHeight()) / 2;
            contents.drawImage(pdImage, startX, startY);

            contents.close();

            pdfDoc.save(filePath.toFile());

            return "/qrs/"+hashName;

    }
}
