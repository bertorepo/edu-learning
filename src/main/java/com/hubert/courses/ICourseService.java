package com.hubert.courses;

import java.util.List;

public interface ICourseService {
	Course saveCourse(CourseDao courseDao);

	List<Course> getAllCourses();
}
