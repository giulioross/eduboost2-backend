package com.example.eduboost_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", "eduboost/" + uniqueFileName,
                    "overwrite", true,
                    "resource_type", "auto"
            ));

            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new IOException("Error uploading file to Cloudinary", e);
        }
    }
    // Aggiungi questo metodo nella classe CloudinaryService
    public void deleteFile(String imageUrl) {
        // Implementazione per eliminare il file da Cloudinary usando l'URL
        // (devi estrarre il public_id dall'URL e chiamare l'API di Cloudinary)
    }
}