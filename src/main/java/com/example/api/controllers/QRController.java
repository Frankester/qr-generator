package com.example.api.controllers;

import com.example.api.exceptions.AccesDeniedResourceException;
import com.example.api.exceptions.DirectoryCreationException;
import com.example.api.exceptions.FileNotFoundException;
import com.example.api.models.dto.ErrorResponse;
import com.example.api.models.dto.GenericMessageResponse;
import com.example.api.services.QRFileService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@SecurityRequirement(name = "bearerAuth")
public class QRController {


    private final QRFileService qrService;
    private final Logger logger = LoggerFactory.getLogger(QRController.class);

    @Autowired
    public QRController(QRFileService qrService){
        this.qrService = qrService;
    }

    @ApiResponses(value = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)))
    @GetMapping("/qrs/{qrId}")
    public ResponseEntity<Object> downloadQR(
            @Parameter(content = @Content(examples = @ExampleObject(value = "1")))
            @PathVariable("qrId")  String qrId)
            throws IOException, FileNotFoundException, AccesDeniedResourceException, DirectoryCreationException {


        File file = qrService.getQRFile(qrId);

        //cerate Http response
        byte[] arr;
        try(FileInputStream resource = new FileInputStream(file)){
            arr= new byte[(int)file.length()];
            int bytesStreamCount = resource.read(arr);
            if(bytesStreamCount != -1){
                logger.warn("Couldn't read all the file, only {} bytes", bytesStreamCount);
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(file.getName()).build().toString());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(arr);
    }

    @ApiResponses(value = @ApiResponse(responseCode = "200",
            content = @Content(examples = @ExampleObject(value = """
                    {
                      "message": "QR deleted with success"
                    }
                    """),
                    schema =@Schema(implementation =  GenericMessageResponse.class))))
    @DeleteMapping("/qrs/{qrId}")
    public ResponseEntity<Object> deleteQR(
            @Parameter(content = @Content(examples = @ExampleObject(value = "1")))
            @PathVariable("qrId") String qrId
    ) throws FileNotFoundException {
        if(this.qrService.deleteQR(qrId)){
            GenericMessageResponse messageResponse = new GenericMessageResponse("QR deleted with success");
            return ResponseEntity.ok(messageResponse);
        }
        ErrorResponse errorResponse = new ErrorResponse("Something went wrong trying to delete the QR "+qrId);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
