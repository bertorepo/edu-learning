package com.hubert.courses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hubert.constants.BaseEntity;

@Entity
public class Course extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "course_name")
	private String courseName;
	@Column(name = "course_link")
	private String courseLink;
	@Column(name = "course_size")
	private Long courseSize;
	@Column(name = "course_description")
	private String courseDescription;

	public Course(Long id, String courseName, String courseLink, Long courseSize, String courseDescription) {
		this.id = id;
		this.courseName = courseName;
		this.courseLink = courseLink;
		this.courseSize = courseSize;
		this.courseDescription = courseDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseLink() {
		return courseLink;
	}

	public void setCourseLink(String courseLink) {
		this.courseLink = courseLink;
	}

	public Long getCourseSize() {
		return courseSize;
	}

	public void setCourseSize(Long courseSize) {
		this.courseSize = courseSize;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

}
