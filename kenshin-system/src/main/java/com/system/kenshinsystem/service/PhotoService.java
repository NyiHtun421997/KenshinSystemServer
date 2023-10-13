package com.system.kenshinsystem.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.system.kenshinsystem.model.Photos;

public interface PhotoService {
	
	String storeImages(MultipartFile image) throws IOException;
	
	String updateImages(MultipartFile image) throws IOException;
	
	Photos getImages(String fileName) throws IOException;

}
