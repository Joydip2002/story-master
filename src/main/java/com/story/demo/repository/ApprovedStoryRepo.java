package com.story.demo.repository;



import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.story.demo.model.ApprovedStory;

@Repository
@EnableJpaRepositories
public interface ApprovedStoryRepo extends JpaRepository<ApprovedStory, Integer>{
	
	@Query("select sum(view_count) from ApprovedStory where author_id=:writerId")
	int findTotalViewOFWriter(@Param("writerId") int writerId);
	
	@Query("select count(story_id) from ApprovedStory where author_id=:writerId")
	int findTotalStoryCount(@Param("writerId") int writerId);
	
	@Modifying
	@Transactional
	@Query("delete from ApprovedStory where author_id=:writerId")
	void deleteStoryByWriterId(@Param("writerId") int writerId);
	
	@Query("select s from ApprovedStory s order by view_count desc")
	ArrayList<ApprovedStory> findAllOrderByView();
	
	@Modifying
	@Transactional
	@Query("update ApprovedStory set view_count=:view_count where story_id=:storyId")
	void incrementViewCount(@Param("storyId") int storyId, @Param("view_count") int view_count);
	
	
	@Query("select s from ApprovedStory s where author_id=:writerId order by view_count desc")
	ArrayList<ApprovedStory> findAllStoryOfWriter(@Param("writerId") int writerId);
}
