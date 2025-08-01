package com.example.api.controllers;

import com.example.api.exceptions.FileNotFoundException;
import com.example.api.exceptions.InvalidLinkException;
import com.example.api.exceptions.InvalidQRPixelSizeException;
import com.example.api.models.QR;
import com.example.api.models.QRLink;
import com.example.api.models.dto.QrRequest;
import com.example.api.repositories.QrRepo;
import com.example.api.services.QRFileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RepositoryRestController
@SecurityRequirement(name = "bearerAuth")
public class QrControllerComplement {

    @Autowired
    QRFileService qrService;

    @Transactional
    @PostMapping("/qrs")
    public @ResponseBody ResponseEntity<Object> createQR(@RequestBody QrRequest req)
            throws Exception {

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
