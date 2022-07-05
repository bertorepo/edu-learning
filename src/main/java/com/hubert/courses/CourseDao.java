package com.hubert.courses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hubert.courses.category.CourseCategory;

public class CourseDao {
	@NotBlank(message = "Name sould not be empty!")
	private String courseName;
	private String courseDescription;
	@NotBlank(message = "Link sould not be empty!")
	private String courseLink;

	@NotNull(message = "Size should not be empty")
	private long courseSize;
	@NotNull(message = "Course Category sould not be empty!")
	private CourseCategory courseCategory;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseLink() {
		return courseLink;
	}

	public void setCourseLink(String courseLink) {
		this.courseLink = courseLink;
	}

	public long getCourseSize() {
		return courseSize;
	}

	public void setCourseSize(long courseSize) {
		this.courseSize = courseSize;
	}

	public CourseCategory getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}

	

}
