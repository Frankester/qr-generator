package com.example.api.models.dto;

import com.example.api.models.FileType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrRequest {

    private String linkUrl;
    private String QRColor;
    private String BGColor;
    private FileType typeFile;

    public QrRequest(){
        this.QRColor= "#000000";
        this.BGColor= "#FFFFFF";
        this.typeFile=FileType.PNG;
    }

}
