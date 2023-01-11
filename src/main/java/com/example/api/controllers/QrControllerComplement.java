package com.example.api.controllers;

import com.example.api.exceptions.InvalidLinkException;
import com.example.api.models.QR;
import com.example.api.models.QRLink;
import com.example.api.models.dto.QrRequest;
import com.example.api.repositories.QrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class QrControllerComplement {

    @Autowired
    QrRepo repo;

    @Transactional
    @PostMapping("/qrs")
    public @ResponseBody ResponseEntity<Object> createQR(@RequestBody QrRequest req)
    throws InvalidLinkException{

        QRLink qrLink = new QRLink();
        qrLink.setUrl(req.getLinkUrl());

        boolean isValid = qrLink.verifyLink();

        if (!isValid){
            throw new InvalidLinkException("the link: "+qrLink.getUrl()+ " don't work", qrLink );
        }

        QR qr = QR.builder()
                .linkUrl(qrLink)
                .typeFile(req.getTypeFile())
                .QRColor(req.getQRColor())
                .BGColor(req.getBGColor())
                .build();

        qr.generateQR();

        this.repo.save(qr);

        return ResponseEntity.ok(qr);
    }
}
