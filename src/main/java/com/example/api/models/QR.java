package com.example.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QR extends Persistence {

    private String imageQR;

    @OneToOne
    @JoinColumn(name = "id_link", referencedColumnName = "id")
    private QRLink linkUrl;

    private String version;

    private String QRColor;

    private String BGColor;

    @Enumerated(EnumType.STRING)
    private FileType typeFile;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    public String generateQR(){
        //TODO generate QR code
        return "";
    }
}
