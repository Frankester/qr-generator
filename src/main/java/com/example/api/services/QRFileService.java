package com.example.api.services;

import com.example.api.config.MainConfiguration;
import com.example.api.exceptions.AccesDeniedResourceException;
import com.example.api.exceptions.DirectoryCreationException;
import com.example.api.exceptions.FileNotFoundException;
import com.example.api.models.QR;
import com.example.api.models.QRLink;
import com.example.api.models.User;
import com.example.api.models.dto.QrRequest;
import com.example.api.repositories.QrRepo;
import com.example.api.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Service
public class QRFileService {


    private final QrRepo repo;

    private final UserRepo repoUsers;

    @Autowired
    public QRFileService(QrRepo repo, UserRepo repoUsers) {
        this.repo = repo;
        this.repoUsers = repoUsers;
    }

    public File getQRFile(String qrKey) throws FileNotFoundException, AccesDeniedResourceException, DirectoryCreationException {

        Optional<QR> qr = this.repo.findByImageQR("/qrs/"+qrKey);

        if(qr.isEmpty()){
            throw new FileNotFoundException("QR "+ qrKey+ " not found", qrKey);
        }

        if(!qr.get().isActivo()){
            throw new FileNotFoundException("QR "+ qrKey+ " not found", qrKey);
        }

        User currentUser = getUser();
        if(!qr.get().getUser().equals(currentUser)){
            throw new AccesDeniedResourceException("You are not the owner of this QR");
        }


        File folder = MainConfiguration.getfolderQrFilesPath().toFile();

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

    public QR saveFile(QRLink qrLink, QrRequest req) throws Exception {

        QR qr = new QR(
                null,
                qrLink,
                req.getQrColorRGB(),
                req.getBgColorRGB(),
                req.getTypeFile(),
                req.getQrPixelSize(),
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

        String username = userDetail.getUsername();
        Optional<User> userOp = this.repoUsers.findByUsername(username);

        if(userOp.isEmpty()){
         throw new BadCredentialsException("Bad Credentials");
        }else {
            return userOp.get();
        }
    }

    public boolean deleteQR(String qrKey) throws FileNotFoundException {
        Optional<QR> qrOp =this.repo.findByImageQR("/qrs/"+qrKey);

        if(qrOp.isEmpty()){
            throw new FileNotFoundException("QR "+ qrKey+ " not found", qrKey);
        }

        QR qr = qrOp.get();

        qr.setActivo(false);

        this.repo.save(qr);

        return true;
    }

}
