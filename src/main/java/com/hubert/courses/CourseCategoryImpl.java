package com.hubert.courses;

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

		if (courseCategoryDao != null) {
			CourseCategory cat = new CourseCategory();
			cat.setCategoryName(courseCategoryDao.getCategoryName());
			repository.save(cat);
			isSaved = true;
		}
		return isSaved;

	}

}
