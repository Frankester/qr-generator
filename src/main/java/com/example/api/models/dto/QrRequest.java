package com.example.api.models.dto;

import com.example.api.models.FileType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrRequest {

    private String linkUrl;
    private String QRColorRGB;
    private String BGColorRGB;
    private FileType typeFile;
    private int QRPixelSize;

    public QrRequest(){
        this.QRColorRGB= "#000000";
        this.BGColorRGB= "#FFFFFF";
        this.typeFile=FileType.PNG;
    }

}
