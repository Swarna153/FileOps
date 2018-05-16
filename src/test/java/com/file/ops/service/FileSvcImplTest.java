package com.file.ops.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import com.file.ops.domain.File;
import com.file.ops.dto.FileDTO;
import com.file.ops.repository.FileRepository;

@RunWith(MockitoJUnitRunner.class)
public class FileSvcImplTest {
	
	private FileSvcImpl fileSvcImpl;
	@Mock
	private FileRepository fileRepository;
	@Mock
	public JavaMailSender emailSender;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		fileSvcImpl = new FileSvcImpl();
		ReflectionTestUtils.setField(fileSvcImpl, "fileRepository", fileRepository);
		ReflectionTestUtils.setField(fileSvcImpl, "emailSender", emailSender);
		
	}
	
	@Test
	public void testSaveFile() {
		fileSvcImpl.saveFile("File Name", "application/json", "{id : '1'}".getBytes());
		verify(fileRepository).save(Mockito.any(File.class));
	}
	
	@Test
	public void testGetFileMetadata() {
		File file = new File();
		file.setContentType("application/json");
		file.setCreatedTime(new Date());
		file.setFileContent("{id : '1'}".getBytes());
		file.setId(1l);
		file.setName("File Name");
		Optional<File> optional = Optional.of(file);
		when(fileRepository.findById(1l)).thenReturn(optional);
		FileDTO filedto = fileSvcImpl.getFileMetadata(1l);
		Assert.assertEquals(filedto.getId(), file.getId());
		Assert.assertEquals(filedto.getName(), file.getName());
		Assert.assertEquals(filedto.getType(), file.getContentType());
		Assert.assertEquals(filedto.getCreatedTime(), file.getCreatedTime());
	}
	
	@Test
	public void testSearchFiles() {
		List<File> dbFiles = new ArrayList<>();
		dbFiles.add(getDummyFile(1));
		dbFiles.add(getDummyFile(2));
		when(fileRepository.searchFile(1l, "File2", "")).thenReturn(dbFiles);
		List<FileDTO> files = fileSvcImpl.searchFiles(1, "File2", "");
		Assert.assertEquals(files.size(), dbFiles.size());
	}
	
	@Test
	public void testGetFileStream() {
		File file = getDummyFile(1);
		when(fileRepository.findById(1l)).thenReturn(Optional.of(file));
		File dbFile = fileSvcImpl.getFileStream(1l);
		Assert.assertEquals(dbFile.getFileContent(), file.getFileContent());
	}
	
	@Test
	public void testEmailNewFiles() {
		List<File> dbFiles = new ArrayList<>();
		dbFiles.add(getDummyFile(1));
		dbFiles.add(getDummyFile(2));
		when(fileRepository.getLastOneHourFiles(Mockito.any())).thenReturn(dbFiles);
		fileSvcImpl.emailNewFiles();
		verify(emailSender).send(Mockito.any(SimpleMailMessage.class));
	}
	
	private File getDummyFile(int id) {
		File file = new File();
		file.setContentType("application/json");
		file.setCreatedTime(new Date());
		file.setFileContent("test1".getBytes());
		file.setId(id);
		file.setName("File " + id);
		return file;
	}

}
