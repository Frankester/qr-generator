package com.example.api.models.qrgenerators;

import com.example.api.models.QRLink;

public interface QRGeneratorStrategy {

     String generateQR(int colorBg, int colorQR, int size, QRLink linkUrl) throws Exception;

}
