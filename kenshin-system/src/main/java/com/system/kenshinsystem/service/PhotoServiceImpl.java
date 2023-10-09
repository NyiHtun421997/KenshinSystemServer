package com.system.kenshinsystem.service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.system.kenshinsystem.model.Photos;
import com.system.kenshinsystem.repository.PhotoRepository;

@Service
public class PhotoServiceImpl implements PhotoService {
	
	private final PhotoRepository photoRepository;
	
	@Autowired
	public PhotoServiceImpl(PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}

	@Override
	public String storeImages(MultipartFile image) throws IOException{
		
		String fileName = URLDecoder.decode(image.getOriginalFilename(), "UTF-8");
		
		byte[] bytes = image.getBytes();
		String imageURL = "resources/photos"+File.separator+fileName;
		Path path = Paths.get(imageURL);
		Files.write(path, bytes);
		
		Photos photos = new Photos();
		photos.setName(fileName);
		photos.setType(image.getContentType());
		photos.setImageURL(imageURL);
		
		this.photoRepository.save(photos);
		
		return imageURL;
	}

}
