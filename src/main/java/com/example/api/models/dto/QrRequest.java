package com.example.api.models.dto;

import com.example.api.models.FileType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrRequest {

    private String linkUrl;
    private String qrColorRGB;
    private String bgColorRGB;
    private FileType typeFile;
    private int qrPixelSize;

    public QrRequest(){
        this.qrColorRGB = "#000000";
        this.bgColorRGB = "#FFFFFF";
        this.typeFile=FileType.PNG;
    }

}
