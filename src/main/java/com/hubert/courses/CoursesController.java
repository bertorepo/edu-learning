package com.hubert.courses;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hubert.courses.category.CourseCategory;
import com.hubert.courses.category.ICourseCategory;

@Controller
public class CoursesController {

	private ICourseCategory courseCategory;
	private ICourseService courseService;

	@Autowired
	public CoursesController(ICourseCategory courseCategory, ICourseService courseService) {
		this.courseCategory = courseCategory;
		this.courseService = courseService;
	}

	@GetMapping("/courses")
	public String displayCoursesPage() {
		return "pages/course/courses";
	}

	// ADMIN TASK ONLY
	@GetMapping("/admin/course/addCourse")
	public String displayAddCoursePage(Model model) {

		model.addAttribute("courseDao", new CourseDao());

		// getting the course category
		List<CourseCategory> courseCategoryList = courseCategory.getAllCourseCategory();

		model.addAttribute("courseCategoryList", courseCategoryList);
		return "pages/course/add-course";
	}

	@PostMapping("/admin/course/addCourse")
	public String addCoursePage(@Valid @ModelAttribute("courseDao") CourseDao courseDao, BindingResult bindingResult,
			Model model) {
		// getting the course category
		List<CourseCategory> courseCategoryList = courseCategory.getAllCourseCategory();
		model.addAttribute("courseCategoryList", courseCategoryList);

		if (bindingResult.hasErrors()) {
			return "pages/course/add-course";
		}

		// call the service
		Course saveCourse = courseService.saveCourse(courseDao);
		if (saveCourse.getId() > 0) {
			return "redirect:/?added=true";
		}

		return "redirect:/?added=false";
	}
}
