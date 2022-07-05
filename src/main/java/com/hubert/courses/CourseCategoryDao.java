package com.hubert.courses;

import javax.validation.constraints.NotBlank;

public class CourseCategoryDao {

	@NotBlank(message = "Course Category must not be empty!")
	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "CourseCategoryDao [categoryName=" + categoryName + "]";
	}
	
	
	
}
