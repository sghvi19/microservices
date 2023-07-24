package com.example.service;

import com.example.domain.Image;
import com.example.exception.ImageNotFoundException;
import com.example.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository repo;

    @Autowired
    public ImageService(ImageRepository repo) {
        this.repo = repo;
    }

    public Iterable<Image> getAll(final int page, final int pageSize) {
        log.info("getting all images");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Image getById(final long id) {
        log.info("getting image by id");
        return repo.findById(id).orElseThrow(()-> new ImageNotFoundException(String.format("Image with id: %d not found!", id)));
    }

    public List<byte[]> getImages(final long id) {
        log.info("getting all images");
        return repo.findImages(id)
                .stream()
                .map(image -> Base64.getDecoder().decode(image.getBase64Image()))
                .collect(Collectors.toList());
    }

    public Image create(final Image image) {
        log.info("creating image");
        return repo.save(image);
    }

    public Image remove(final long id) {
        log.info("removing image");
        if (repo.existsById(id)) {
            Image image = repo.findById(id).get();
            repo.deleteById(id);
            return image;
        }
        throw new ImageNotFoundException(String.format("Image with id: %d not found!", id));
    }
}
