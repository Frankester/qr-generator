package com.example.api.models.qrgenerators;

import com.example.api.config.MainConfiguration;
import com.example.api.models.QRLink;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.nio.file.Path;

public class PdfQRGenerator implements QRGeneratorStrategy{



    @Override
    public String generateQR(int colorBg, int colorQR, int size, QRLink linkUrl) throws Exception {

        File file = QRCode
                .from(linkUrl.getUrl())
                .withColor(colorQR, colorBg)
                .withSize(size, size)
                .withErrorCorrection(ErrorCorrectionLevel.M)
                .file();


        String hashName = RandomHashGenerator.generateRandomHashName();


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
