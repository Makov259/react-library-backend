package com.makov.spring_boot_library.images;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;
//a
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setImage(file.getBytes());
        return imageRepository.save(image);
    }

    public byte[] getImage(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        return image.map(Image::getImage).orElse(null);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
