package com.example.api.services;

import com.example.api.exceptions.FileNotFoundException;
import com.example.api.exceptions.InvalidLinkException;
import com.example.api.models.QR;
import com.example.api.models.QRLink;
import com.example.api.models.dto.QrRequest;
import com.example.api.repositories.QrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class QRFileService {


    @Autowired
    private QrRepo repo;

    public File getQRFile(String qrKey) throws FileNotFoundException {

        Optional<QR> qr = this.repo.findByImageQR("/qrs/"+qrKey);

        if(qr.isEmpty()){
            throw new FileNotFoundException("QR "+ qrKey+ " not found", qrKey);
        }

        Path folderPath = Paths.get("src", "main", "java", "QR-Images");
        File folder = folderPath.toFile();

        Path foundFile = null;

        for(File file : folder.listFiles()){
            if(file.isFile() && file.getName().startsWith(qrKey)){
                foundFile = file.toPath();
            }
        }
        if(foundFile == null){
            throw new FileNotFoundException("QR "+ qrKey+ " not found", qrKey);
        }

        return foundFile.toFile();
    }

    public QR saveFile(QRLink qrLink, QrRequest req) {


        QR qr = new QR(
                null,
                qrLink,
                req.getQRColor(),
                req.getBGColor(),
                req.getTypeFile(),
                req.getSize(),
                null
        );

        //generate and store the qr file
        qr.generateQR();

        this.repo.save(qr);

        return qr;
    }

}
