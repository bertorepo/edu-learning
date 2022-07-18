package com.hubert.courses;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hubert.courses.category.CourseCategory;
import com.hubert.courses.category.ICourseCategory;
import com.hubert.customer.Customer;

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
	public String displayCoursesPage(Model model, HttpSession session) {
		List<Course> courseList = courseService.getAllCourses();
		Customer cust = (Customer) session.getAttribute("loggedInCustomer");
		model.addAttribute("username", cust.getUsername());

		if (courseList == null) {

			model.addAttribute("courseList", null);
		}
		model.addAttribute("courseList", courseList);

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
			return "redirect:/admin/course/manageCourse?added=true";
		}

		return "redirect:/?added=false";
	}

	// ADMIN:MANAGE COURSES
	@GetMapping("/admin/course/manageCourse")
	public String displayManageCourse(Model model, HttpSession session) {
		Customer cust = (Customer) session.getAttribute("loggedInCustomer");
		model.addAttribute("username", cust.getUsername());

		List<Course> courseList = courseService.getAllCourses();
		if (courseList == null) {

			model.addAttribute("courseList", null);
		}
		model.addAttribute("courseList", courseList);
		return "pages/course/courses";
	}

	@PostMapping("/admin/course/delete/{courseId}")
	public String deleteCourse(@PathVariable("courseId") Long courseId, Model model,
			RedirectAttributes redirectAttributes) {
		if (courseId <= 0 || courseId == null) {
			redirectAttributes.addFlashAttribute("deleteError", "Course not found!");
			return "pages/course/courses";
		}

		// call the service
		boolean isDeleted = courseService.deleteCourse(courseId);
		if (isDeleted) {
			redirectAttributes.addFlashAttribute("deleteSuccess", "Course deleted successfully!");
			return "redirect:/admin/course/manageCourse";
		}
		return "pages/course/courses";
	}
}
