package com.hubert.courses;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseCategoryImpl implements ICourseCategory {

	private CourseCategoryRepository repository;

	@Autowired
	public CourseCategoryImpl(CourseCategoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean saveCategory(CourseCategoryDao courseCategoryDao) {
		boolean isSaved = false;
		
		ModelMapper modelMapper = new ModelMapper();

		
		if (courseCategoryDao != null) {
			CourseCategory cat = modelMapper.map(courseCategoryDao, CourseCategory.class);
			repository.save(cat);
			isSaved = true;
		}else {
			isSaved = false;
		}
		return isSaved;
		
	}

}
