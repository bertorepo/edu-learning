package com.hubert.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubert.constants.Constants;
import com.hubert.courses.category.CourseCategory;
import com.hubert.courses.category.ICourseCategory;

@Service
public class CourseServiceImpl implements ICourseService {

	private CourseRepository courseRepository;
	private ICourseCategory courseCategory;

	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository, ICourseCategory courseCategory) {
		this.courseRepository = courseRepository;
		this.courseCategory = courseCategory;
	}

	@Override
	public Course saveCourse(CourseDao courseDao) {
		Course course = null;
		if (courseDao != null) {
			course = new Course();
			course.setCourseName(courseDao.getCourseName());
			course.setCourseLink(courseDao.getCourseLink());
			course.setCourseDescription(courseDao.getCourseDescription());
			course.setCourseSize(courseDao.getCourseSize());
			course.setEnabled(Constants.IS_ENABLED);

			CourseCategory courseCat = courseCategory.findCourseCategoryById(courseDao.getCourseCategory().getId());
			course.setCourseCategory(courseCat);

			courseRepository.save(course);

		}

		return course;
	}

}
