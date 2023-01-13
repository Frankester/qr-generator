package com.example.api.services;

import com.example.api.exceptions.AccesDeniedResourceException;
import com.example.api.exceptions.FileNotFoundException;
import com.example.api.exceptions.InvalidLinkException;
import com.example.api.models.QR;
import com.example.api.models.QRLink;
import com.example.api.models.User;
import com.example.api.models.dto.QrRequest;
import com.example.api.repositories.QrRepo;
import com.example.api.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class QRFileService {


    @Autowired
    private QrRepo repo;

    @Autowired
    private UserRepo repoUsers;

    public File getQRFile(String qrKey) throws FileNotFoundException, AccesDeniedResourceException {

        Optional<QR> qr = this.repo.findByImageQR("/qrs/"+qrKey);

        if(qr.isEmpty()){
            throw new FileNotFoundException("QR "+ qrKey+ " not found", qrKey);
        }

        User currentUser = getUser();
        if(!qr.get().getUser().equals(currentUser)){
            throw new AccesDeniedResourceException("You are not the owner of this QR", currentUser);
        }


        Path folderPath = Paths.get("src", "main", "java", "QR-Images");
        File folder = folderPath.toFile();

        Path foundFile = null;

        for(File file : Objects.requireNonNull(folder.listFiles())){
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
                getUser()
        );

        //generate and store the qr file
        qr.generateQR();

        this.repo.save(qr);

        return qr;
    }

    private User getUser(){
        Authentication auth =SecurityContextHolder.getContext().getAuthentication() ;

        org.springframework.security.core.userdetails.User userDetail =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        Optional<User> userOp = this.repoUsers.findByUsername(userDetail.getUsername());

        return userOp.get();
    }

}
