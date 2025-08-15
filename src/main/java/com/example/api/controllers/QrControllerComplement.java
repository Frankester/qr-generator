package com.example.api.controllers;

import com.example.api.exceptions.InvalidLinkException;
import com.example.api.exceptions.InvalidQRPixelSizeException;
import com.example.api.models.QR;
import com.example.api.models.QRLink;
import com.example.api.models.dto.QrRequest;
import com.example.api.services.QRFileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RepositoryRestController
@SecurityRequirement(name = "bearerAuth")
public class QrControllerComplement {


    private final QRFileService qrService;

    @Autowired
    public QrControllerComplement(QRFileService qrService){
        this.qrService = qrService;
    }

    @Transactional
    @PostMapping("/qrs")
    public @ResponseBody ResponseEntity<Object> createQR(@RequestBody QrRequest req) throws Exception {

        QRLink qrLink = new QRLink();
        qrLink.setUrl(req.getLinkUrl());

        boolean isValid = qrLink.verifyLink();

        if (!isValid){
            throw new InvalidLinkException("the link: "+qrLink.getUrl()+ " don't work", qrLink );
        }
        int size = req.getQRPixelSize();
        if(size < 33){
            throw new InvalidQRPixelSizeException("The QR code size in pixels must be at least 33 pixels, so it cannot be "+size+" pixels");
        }

        QR qr = this.qrService.saveFile(qrLink, req);

        return ResponseEntity.ok(qr);
    }



}
