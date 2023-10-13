package com.system.kenshinsystem.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
		
		Photos photos = Photos.builder().name(fileName)
						.type(image.getContentType())
						.imageURL(imageURL)
						.build();
		
		this.photoRepository.save(photos);
		
		return imageURL;
	}
	@Override
	public String updateImages(MultipartFile image) throws IOException{
		
		String fileName = URLDecoder.decode(image.getOriginalFilename(), "UTF-8");
		
		byte[] bytes = image.getBytes();
		String imageURL = "resources/photos"+File.separator+fileName;
		Path path = Paths.get(imageURL);
		Files.write(path, bytes);
		
		Photos newPhotos;
		Optional<Photos> photos = this.photoRepository.findByName(fileName);
		if(!photos.isEmpty()) {
			
			newPhotos = photos.get();
		}
		else {
			
			newPhotos = new Photos();
		}
		newPhotos.setName(fileName);
		newPhotos.setType(image.getContentType());
		newPhotos.setImageURL(imageURL);
		
		this.photoRepository.save(newPhotos);
		
		return imageURL;
	}
	@Override
	public Photos getImages(String fileName) throws UnsupportedEncodingException {
		
		String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
		System.out.println("*************************");
		System.out.println(decodedFileName);
		Optional<Photos> photos = this.photoRepository.findByName(decodedFileName);
		if(!photos.isEmpty()) {
			System.out.println("*************************");
			
			return photos.get();
		}
		else return null;
	}

}
