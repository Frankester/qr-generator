package com.example.api.repositories;

import com.example.api.models.QR;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path="qrs")
@SecurityRequirement(name = "bearerAuth")
public interface QrRepo extends JpaRepository<QR, Long> {

    Optional<QR> findByImageQR(String imageQR);


    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(QR entity);

}
