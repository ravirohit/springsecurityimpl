package com.learn.repositoryimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.learn.model.UploadFileEntity;
import com.learn.model.UploadJsonFile;

@Repository
@Transactional
public class FileOperationRepository {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void storeFile(UploadFileEntity uploadFileEntity){
		sessionFactory.getCurrentSession().saveOrUpdate(uploadFileEntity);
		System.out.println("-------- File saved to db size:"+uploadFileEntity.getFile().length+"--------------");
	}
	public UploadFileEntity getFile(String email){
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(UploadFileEntity.class);
		crit.add(Restrictions.eq("email", email));
		List<UploadFileEntity> results = crit.list();
		UploadFileEntity uploadFileEntity = results.size() > 0 ? results.get(0): null;
		System.out.println("-------- Reading file to db size:"+uploadFileEntity.getFile().length+"--------------");
		return uploadFileEntity;
	}
	public void storeJsonFile(UploadJsonFile uploadJsonFile){
		sessionFactory.getCurrentSession().saveOrUpdate(uploadJsonFile);
	}
	public UploadJsonFile getJsonFile(String email){
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(UploadJsonFile.class);
		crit.add(Restrictions.eq("email", email));
		List<UploadJsonFile> results = crit.list();
		System.out.println("email:"+email);
		UploadJsonFile uploadJsonFile = results.size() > 0 ? results.get(0): null;
		System.out.println("-------- Reading file to db size:"+uploadJsonFile.getFile().length+"--------------");
		return uploadJsonFile;
	}
}
