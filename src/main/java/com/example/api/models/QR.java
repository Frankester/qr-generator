package com.example.api.models;

import com.example.api.exceptions.DirectoryCreationException;
import com.example.api.models.qrgenerators.PdfQRGenerator;
import com.example.api.models.qrgenerators.PngQRGenerator;
import com.example.api.models.qrgenerators.QRGeneratorStrategy;
import com.example.api.models.qrgenerators.SvgQRGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;

@Getter
@Setter
@Entity
public class QR extends Persistence {

    private String imageQR;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_link", referencedColumnName = "id")
    private QRLink linkUrl;

    private String qrColorRGB;

    private String bgColorRGB;

    @Transient
    @JsonIgnore
    private QRGeneratorStrategy generator;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;


    private int size;

    private boolean activo;


    public QR(String imageQR, QRLink linkUrl, String qrColor, String bgColor, FileType typeFile,int size, User user) {
        this.imageQR = imageQR;
        this.linkUrl = linkUrl;
        this.qrColorRGB = qrColor;
        this.bgColorRGB = bgColor;
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

    public void generateQR() throws DirectoryCreationException, IOException {
        int colorBg = Color.decode(bgColorRGB).getRGB();
        int colorQR = Color.decode(qrColorRGB).getRGB();

        this.imageQR = generator.generateQR(colorBg, colorQR, size, linkUrl);
    }
}
