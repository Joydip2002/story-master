package com.story.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.story.demo.model.ApprovedStory;

@Repository
public interface ApprovedStoryRepo extends JpaRepository<ApprovedStory, Integer>{

}
