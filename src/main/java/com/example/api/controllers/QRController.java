package com.example.api.controllers;

import com.example.api.exceptions.AccesDeniedResourceException;
import com.example.api.exceptions.FileNotFoundException;
import com.example.api.services.QRFileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@SecurityRequirement(name = "bearerAuth")
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

    @DeleteMapping("/qrs/{qrKey}")
    public ResponseEntity<Object> deleteQR(
            @PathVariable("qrKey") String qrKey
    ) throws FileNotFoundException {
        if(this.qrService.deleteQR(qrKey)){
            return ResponseEntity.ok("QR deleted with success");
        }
        return ResponseEntity.internalServerError().body("Something went wrong trying to delete the QR "+qrKey);
    }

}
