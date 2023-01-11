package com.example.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@Entity
@Builder
public class QR extends Persistence {

    private String imageQR;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_link", referencedColumnName = "id")
    private QRLink linkUrl;

    private String QRColor;

    private String BGColor;

    @Enumerated(EnumType.STRING)
    private FileType typeFile;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;


    public QR(String imageQR, QRLink linkUrl, String QRColor, String BGColor, FileType typeFile, User user) {
        this.imageQR = imageQR;
        this.linkUrl = linkUrl;
        this.QRColor = QRColor;
        this.BGColor = BGColor;
        this.typeFile = typeFile;
        this.user = user;
    }

    public QR(){

    }

    public void generateQR(){
        //TODO generate QR code
        int colorBg = Color.decode(BGColor).getRGB();
        int colorQR = Color.decode(QRColor).getRGB();



        this.imageQR = "/qrs/yourqr."+this.typeFile.toString().toLowerCase();
    }
}
