package com.example.api.models.qrGenerators;

import com.example.api.models.QRLink;

public interface QRGeneratorStrategy {

     String generateQR(int colorBg, int colorQR, int size, QRLink linkUrl);

}
