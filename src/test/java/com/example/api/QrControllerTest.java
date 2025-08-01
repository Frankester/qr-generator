package com.example.api;

import com.example.api.models.FileType;
import com.example.api.models.dto.QrRequest;
import com.example.api.utils.AuthUtils;
import com.example.api.utils.FileCleaner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QrControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private AuthUtils authUtils;
    private String token;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String linkTest = "https://google.com";
    private final String linkFailTest = "noValidLink";
    private final int qrPizelSizeTest = 43;
    private final int qrPizelSizeFailTest = 0;// < 33
    private final FileType qrFileTypeTest = FileType.PDF;
    private final String qrBGColorRGBTest = "#ffffff";

    private final String qrColorRGBTest = "#000000";
    private final String nonExistentQrKey = "notExists";


    @BeforeEach
    public void setUp() throws Exception {
        this.authUtils  = new AuthUtils(this.mockMvc);
        authUtils.registerUserTest();
        this.token = authUtils.loginUserTest();
    }

    @AfterEach
    public void deleteFiles() {
        FileCleaner.cleanFolder("QRImages");
    }

    @Test
    public void whenCreateAQR_shouldSucceed() throws Exception {

        QrRequest request = new QrRequest();
        request.setLinkUrl(linkTest);
        request.setQRPixelSize(qrPizelSizeTest);
        request.setTypeFile(qrFileTypeTest);
        request.setBGColorRGB(qrBGColorRGBTest);
        request.setQRColorRGB(qrColorRGBTest);

        this.mockMvc.perform(post("/qrs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer "+ this.token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.imageQR").isString());
    }

    @Test
    public void whenCreateAQRWithInvalidLink_shouldFail() throws Exception {

        QrRequest request = new QrRequest();
        request.setLinkUrl(linkFailTest);
        request.setQRPixelSize(qrPizelSizeTest);
        request.setTypeFile(qrFileTypeTest);
        request.setBGColorRGB(qrBGColorRGBTest);
        request.setQRColorRGB(qrColorRGBTest);

        this.mockMvc.perform(post("/qrs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer "+ this.token))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").isString());
    }

    @Test
    public void whenCreateAQRWithInvalidSize_shouldFail() throws Exception {

        QrRequest request = new QrRequest();
        request.setLinkUrl(linkTest);
        request.setQRPixelSize(qrPizelSizeFailTest);
        request.setTypeFile(qrFileTypeTest);
        request.setBGColorRGB(qrBGColorRGBTest);
        request.setQRColorRGB(qrColorRGBTest);

        this.mockMvc.perform(post("/qrs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer "+ this.token))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").isString());
    }

    @Test
    public void whenAccessToAExistentQR_shouldSucceed() throws Exception {

        QrRequest request = new QrRequest();
        request.setLinkUrl(linkTest);
        request.setQRPixelSize(qrPizelSizeTest);
        request.setTypeFile(qrFileTypeTest);
        request.setBGColorRGB(qrBGColorRGBTest);
        request.setQRColorRGB(qrColorRGBTest);

        // cerate a QR
        MvcResult result = this.mockMvc.perform(post("/qrs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer "+ this.token))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseNode = objectMapper.readTree(result.getResponse().getContentAsString());
        String imageQRLink = responseNode.get("imageQR").asText();

        this.mockMvc.perform(get(imageQRLink)
                        .header("Authorization", "Bearer "+ this.token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE));//file
    }

    @Test
    public void whenAccessToANonExistentQR_shouldFail() throws Exception {

        this.mockMvc.perform(get("/qrs/"+nonExistentQrKey)
                        .header("Authorization", "Bearer "+ this.token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").isString());
    }

    @Test
    public void whenDeleteAValidQR_shouldSucceed() throws Exception{

        QrRequest request = new QrRequest();
        request.setLinkUrl(linkTest);
        request.setQRPixelSize(qrPizelSizeTest);
        request.setTypeFile(qrFileTypeTest);
        request.setBGColorRGB(qrBGColorRGBTest);
        request.setQRColorRGB(qrColorRGBTest);


        MvcResult result = this.mockMvc.perform(post("/qrs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer "+ this.token))
                .andReturn();
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        String imageQRLink = jsonNode.get("imageQR").asText();

        this.mockMvc.perform(delete(imageQRLink)
                .header("Authorization", "Bearer "+ this.token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("QR deleted with success"));
    }

    @Test
    public void whenDeleteANonValidQR_shouldFail() throws Exception{

        this.mockMvc.perform(delete("/qrs/"+nonExistentQrKey)
                        .header("Authorization", "Bearer "+ this.token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").isString());
    }

}
