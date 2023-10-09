package com.system.kenshinsystem.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
	
	String storeImages(MultipartFile image) throws IOException;

}
