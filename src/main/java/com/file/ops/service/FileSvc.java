package com.file.ops.service;

import java.util.List;

import com.file.ops.domain.File;
import com.file.ops.dto.FileDTO;

public interface FileSvc {

	public void saveFile(String name, String contentType, byte[] file);
	
	public FileDTO getFileMetadata(long id);
	
	public List<FileDTO> searchFiles(long id, String name, String type);
	
	public File getFileStream(long id);
	
	public void emailNewFiles(); 
}
