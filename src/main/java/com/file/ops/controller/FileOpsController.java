package com.file.ops.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.ops.domain.File;
import com.file.ops.dto.FileDTO;
import com.file.ops.service.FileSvc;

@RestController
public class FileOpsController {

	@Autowired
	private FileSvc fileSvc;

	@RequestMapping("/uploadFile")
	private ResponseEntity<String> uploadFile(MultipartFile file) throws IOException {
		fileSvc.saveFile(file.getOriginalFilename(), file.getContentType(), file.getBytes());
		return new ResponseEntity<String>("Saved file with name " + file.getOriginalFilename() + " and type " + file.getContentType() , HttpStatus.OK);
	}
	
	@RequestMapping("/getFile")
	private ResponseEntity<FileDTO> getFile(long id) {
		return new ResponseEntity<FileDTO>(fileSvc.getFileMetadata(id), HttpStatus.OK);	
	}
	
	@RequestMapping("/searchFile")
	private ResponseEntity<List<FileDTO>> searchFile(long id, @RequestParam(defaultValue = "") String name, 
			@RequestParam(defaultValue = "") String type) {
		return new ResponseEntity<List<FileDTO>>(fileSvc.searchFiles(id, name, type), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/download")
	public ResponseEntity<Resource> download(long id) throws IOException {
		File file = fileSvc.getFileStream(id);
		byte[] fileStream = file.getFileContent();
	    ByteArrayResource resource = new ByteArrayResource(fileStream);

	    return ResponseEntity.ok()
	            .contentLength(resource.contentLength())
	            .contentType(MediaType.parseMediaType(file.getContentType()))
	            .body(resource);
	}
	
}
