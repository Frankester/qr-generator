package com.example.api.models;

import com.example.api.models.qrGenerators.PdfQRGenerator;
import com.example.api.models.qrGenerators.PngQRGenerator;
import com.example.api.models.qrGenerators.QRGeneratorStrategy;
import com.example.api.models.qrGenerators.SvgQRGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@Entity
public class QR extends Persistence {

    private String imageQR;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_link", referencedColumnName = "id")
    private QRLink linkUrl;

    private String QRColor;

    private String BGColor;

    @Transient
    @JsonIgnore
    private QRGeneratorStrategy generator;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;


    private int size;

    private boolean activo;


    public QR(String imageQR, QRLink linkUrl, String QRColor, String BGColor, FileType typeFile,int size, User user) {
        this.imageQR = imageQR;
        this.linkUrl = linkUrl;
        this.QRColor = QRColor;
        this.BGColor = BGColor;
        this.user = user;
        this.size = size;
        this.activo = true;

        if(typeFile == FileType.PNG){
            this.generator = new PngQRGenerator();
        } else if(typeFile == FileType.SVG){
            this.generator = new SvgQRGenerator();
        } else {
            this.generator = new PdfQRGenerator();
        }
    }

    public QR(){
    }

    public void generateQR() throws Exception {
        int colorBg = Color.decode(BGColor).getRGB();
        int colorQR = Color.decode(QRColor).getRGB();

        this.imageQR = generator.generateQR(colorBg, colorQR, size, linkUrl);
    }
}
