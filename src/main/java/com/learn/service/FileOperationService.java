package com.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.model.UploadFile;
import com.learn.model.UploadFileEntity;
import com.learn.model.UploadJsonFile;
import com.learn.repositoryimpl.FileOperationRepository;

@Service
public class FileOperationService {
	
	@Autowired
	FileOperationRepository fileOperationRepository;
	
	public void storeFile(UploadFile uploadFile){
		try{
			UploadFileEntity  uploadFileEntity = new UploadFileEntity();
			uploadFileEntity.setEmail(uploadFile.getEmail());
			uploadFileEntity.setName(uploadFile.getName());
			byte[] filedata = uploadFile.getFile().getBytes();
			uploadFileEntity.setFile(filedata);
			fileOperationRepository.storeFile(uploadFileEntity);
		}catch(Exception e){
			System.out.println("exception occur while storying file: "+e);
		}
	}
	public UploadFileEntity getFile(String email){
		UploadFileEntity uploadFileEntity = null;
		try{
			uploadFileEntity = fileOperationRepository.getFile(email);
		}catch(Exception e){
			System.out.println("exception occur while storying file: "+e);
		}
		return uploadFileEntity;
	}
	public void storeJsonFile(UploadJsonFile uploadFile){
		fileOperationRepository.storeJsonFile(uploadFile);
	}
	public UploadJsonFile getJsonFile(String email){
		UploadJsonFile uploadJsonFile = null;
		try{
			uploadJsonFile = fileOperationRepository.getJsonFile(email);
		}catch(Exception e){
			System.out.println("exception occur while reading file: "+e);
		}
		return uploadJsonFile;
	}

}
