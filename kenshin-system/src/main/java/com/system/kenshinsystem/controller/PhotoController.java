package com.system.kenshinsystem.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.system.kenshinsystem.model.Photos;
import com.system.kenshinsystem.service.PhotoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/kenshin/central")
@RequiredArgsConstructor
public class PhotoController {
	
	private final PhotoService photoService;

	//End point for image downloads
		@GetMapping("/images/download")
		public ResponseEntity<?> getImages(@RequestParam(name = "file_name",required = false)String fileName){
			
			try {
				Photos photos = this.photoService.getImages(fileName);
				if(photos == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found.");
				}
				
				Path path = Paths.get(photos.getImageURL());
				byte[] bytes = Files.readAllBytes(path);
				return ResponseEntity.ok()
									.contentType(MediaType.valueOf(photos.getType()))
									.body(bytes);	
			}
			catch(IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		//End point for image uploads
		@PostMapping("/images/upload")
		public ResponseEntity<String> storeImages(@RequestParam("image")MultipartFile image){
			try {
				String imageURL = this.photoService.storeImages(image);
				return ResponseEntity.status(HttpStatus.OK).body(imageURL);
			}
			catch(IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST"+e.getMessage());
			}
		}
		//End point for image updates
			@PutMapping("/images/upload")
			public ResponseEntity<String> updateImages(@RequestParam("image")MultipartFile image){
				try {
					String imageURL = this.photoService.updateImages(image);
					return ResponseEntity.status(HttpStatus.OK).body(imageURL);
				}
				catch(IOException e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST"+e.getMessage());
				}
			}
			
}
