package com.example.api.repositories;

import com.example.api.models.QR;
import com.example.api.models.QRLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path="qrs")
public interface QrRepo extends JpaRepository<QR, Long> {

    Optional<QR> findByImageQR(String imageQR);


    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(QR entity);

}
