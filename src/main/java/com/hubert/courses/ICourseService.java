package com.hubert.courses;

import java.util.List;

public interface ICourseService {
	Course saveCourse(CourseDao courseDao);

	List<Course> getAllCourses();

	boolean deleteCourse(Long id);

	boolean setCourseStatus(Long id);

	boolean saveExistingCourse(Course course);

	Course findCourseById(Long id);

	Boolean updateCourse(CourseDao courseDao, Long id);
}
