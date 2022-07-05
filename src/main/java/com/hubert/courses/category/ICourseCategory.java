package com.hubert.courses.category;

import java.util.List;

public interface ICourseCategory {

	boolean saveCategory(CourseCategoryDao courseCategoryDao);

	List<CourseCategory> getAllCourseCategory();
	
	CourseCategory findCourseCategoryById(Long id);
}
