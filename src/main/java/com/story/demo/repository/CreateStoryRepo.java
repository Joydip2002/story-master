package com.story.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.story.demo.model.CreateStoryModel;

@Repository
public interface CreateStoryRepo extends JpaRepository<CreateStoryModel, Integer> {
	

}
