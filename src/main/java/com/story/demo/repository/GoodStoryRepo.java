package com.story.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.story.demo.model.WriterModel;
@Repository
public interface GoodStoryRepo extends JpaRepository<WriterModel, Integer>{
	 @Query("select w from WriterModel w where email = ?1")
		public WriterModel findWriterbyEmailId(String emailId);
	 
	 @Query("select w from WriterModel w where email = ?1 and password = ?2")
	 public WriterModel writerLoginCheck(String email,String password);
	 
}
