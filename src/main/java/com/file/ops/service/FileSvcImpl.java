package com.file.ops.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.file.ops.domain.File;
import com.file.ops.dto.FileDTO;
import com.file.ops.repository.FileRepository;

@Component
public class FileSvcImpl implements FileSvc {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	public JavaMailSender emailSender;
	
	@Value("${email.to}")
	private String emailTo;

	@Override
	@Transactional
	public void saveFile(String name, String contentType, byte[] file) {
		File domainFile = new File();
		domainFile.setName(name);
		domainFile.setCreatedTime(Calendar.getInstance().getTime());
		domainFile.setFileContent(file);
		domainFile.setContentType(contentType);
		fileRepository.save(domainFile);
	}

	@Override
	@Transactional
	public FileDTO getFileMetadata(long id) {
		FileDTO fileDto = new FileDTO();
		File file = fileRepository.findById(Long.valueOf(id)).get();
		fileDto.setCreatedTime(file.getCreatedTime());
		fileDto.setId(file.getId());
		fileDto.setName(file.getName());
		fileDto.setType(file.getContentType());
		return fileDto;
	}

	@Override
	public List<FileDTO> searchFiles(long id, String name, String type) {
		List<FileDTO> files = new ArrayList<>();
		List<File> dbFiles = fileRepository.searchFile(id, name, type);
		for (File dbFile : dbFiles) {
			FileDTO file = new FileDTO();
			file.setCreatedTime(dbFile.getCreatedTime());
			file.setId(dbFile.getId());
			file.setName(dbFile.getName());
			file.setType(dbFile.getContentType());
			files.add(file);
		}
		return files;
	}

	@Override
	public File getFileStream(long id) {
		return fileRepository.findById(id).get();
	}

	@Scheduled(cron = "0 0/60 * * * ?")
	@Override
	public void emailNewFiles() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		List<File> files = fileRepository.getLastOneHourFiles(cal.getTime());
		sendEmail(files);
	}

	private void sendEmail(List<File> files) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailTo);
		message.setSubject("Files received in last 1 hour");
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Hi,\n\tIn last one hour application stored " + " " + files.size() + "files.\n\n\nFiles List:\n\n");
		stringBuilder.append("ID\tFILE NAME\t\t\t\t\t\tCREATED DATE\n");
		for (File file : files) {
			stringBuilder.append(files.indexOf(file)+ "\t" + file.getName() + "\t\t\t\t\t\t" + file.getCreatedTime() + "\n");
		}
		message.setText(stringBuilder.toString());
		emailSender.send(message);
	}

}
