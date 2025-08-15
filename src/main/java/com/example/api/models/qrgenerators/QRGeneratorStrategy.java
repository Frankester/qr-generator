package com.example.api.models.qrgenerators;

import com.example.api.exceptions.DirectoryCreationException;
import com.example.api.models.QRLink;

import java.io.IOException;

public interface QRGeneratorStrategy {

     String generateQR(int colorBg, int colorQR, int size, QRLink linkUrl) throws DirectoryCreationException, IOException;

}
