package com.hubert.courses;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.hubert.constants.BaseEntity;
import com.hubert.courses.category.CourseCategory;

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

	@Column(name = "is_enabled")
	private boolean isEnabled;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "course_category_id", referencedColumnName = "id", nullable = false)
	private CourseCategory courseCategory;

	public Course() {
	}

	public Course(Long id, String courseName, String courseLink, Long courseSize, String courseDescription,
			boolean isEnabled, CourseCategory courseCategory) {
		this.id = id;
		this.courseName = courseName;
		this.courseLink = courseLink;
		this.courseSize = courseSize;
		this.courseDescription = courseDescription;
		this.isEnabled = isEnabled;
		this.courseCategory = courseCategory;
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

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public CourseCategory getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}

}
