package com.hubert.courses.category;

import java.util.List;
import java.util.Optional;

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

	@Override
	public List<CourseCategory> getAllCourseCategory() {
		List<CourseCategory> courseCat = repository.findAll();
		if (courseCat.size() > 0) {
			return courseCat;
		}
		return null;
	}

	@Override
	public CourseCategory findCourseCategoryById(Long id) {
		Optional<CourseCategory> findCategory = repository.findById(id);
		if (findCategory.isEmpty()) {
			return null;
		}
		return findCategory.orElseThrow();
	}

}
