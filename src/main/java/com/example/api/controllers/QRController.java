package com.example.api.controllers;

import com.example.api.exceptions.AccesDeniedResourceException;
import com.example.api.exceptions.FileNotFoundException;
import com.example.api.services.QRFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class QRController {

    @Autowired
    QRFileService qrService;

    @GetMapping("/qrs/{qrKey}")
    public ResponseEntity<Object> downloadQR(@PathVariable("qrKey") String qrKey)
            throws IOException, FileNotFoundException, AccesDeniedResourceException {


        File file = qrService.getQRFile(qrKey);

        //cerate Http response
        FileInputStream resource = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];

        resource.read(arr);
        resource.close();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(file.getName()).build().toString());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(arr);
    }

}
