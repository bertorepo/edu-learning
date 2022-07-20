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
		if (cust.getId() > 0) {

			model.addAttribute("username", cust.getUsername());
		} else {
			model.addAttribute("username", "User");
		}

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
		model.addAttribute("courseId", null);

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
		if (cust.getId() > 0) {
			model.addAttribute("username", cust.getUsername());
		} else {
			model.addAttribute("username", "User");
		}

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

	@PostMapping("/admin/course/status/{courseId}")
	public String setCourseStatus(@PathVariable("courseId") Long courseId, Model model,
			RedirectAttributes redirectAttributes) {
		if (courseId == null || courseId <= 0) {
			redirectAttributes.addFlashAttribute("courseStatusError", "Error updating the status!");
			return "pages/course/courses";
		}
		boolean isUpdated = courseService.setCourseStatus(courseId);
		if (isUpdated) {
			redirectAttributes.addFlashAttribute("courseStatusSuccess", "Course status updated successfully!");
			return "redirect:/admin/course/manageCourse";
		}

		return "pages/course/courses";
	}

	// UPDATE_COURSE
	@GetMapping("/admin/course/update/{courseId}")
	public String updateCourseDisplay(Model model, @PathVariable("courseId") Long courseId,
			RedirectAttributes redirectAttributes) {

		if (courseId <= 0 || courseId == null) {
			redirectAttributes.addFlashAttribute("courseUpdateError", "Error updating the course!");
			return "pages/course/courses";
		}
		List<CourseCategory> courseCategoryList = courseCategory.getAllCourseCategory();
		model.addAttribute("courseCategoryList", courseCategoryList);

		Course existingCourse = courseService.findCourseById(courseId);
		CourseDao courseDao = null;
		if (existingCourse.getId() > 0) {
			courseDao = new CourseDao();
			courseDao.setCourseName(existingCourse.getCourseName());
			courseDao.setCourseLink(existingCourse.getCourseLink());
			courseDao.setCourseSize(existingCourse.getCourseSize());
			courseDao.setCourseDescription(existingCourse.getCourseDescription());
			courseDao.setCourseCategory(existingCourse.getCourseCategory());

			model.addAttribute("courseDao", courseDao);
		}
		model.addAttribute("courseId", courseId);
		return "pages/course/add-course";
	}

	// UPDATE_COURSE
	@PostMapping("/admin/course/update/{courseId}")
	public String updateCourse(@Valid @ModelAttribute("courseDao") CourseDao courseDao, BindingResult bindingResult,
			Model model, @PathVariable("courseId") Long courseId,
			RedirectAttributes redirectAttributes) {

		model.addAttribute("courseId", courseId);

		if (courseId <= 0 || courseId == null) {
			redirectAttributes.addFlashAttribute("courseUpdateError", "Error updating the course!");
			return "pages/course/courses";
		}

		if (bindingResult.hasErrors()) {
			return "pages/course/add-course";
		}

		boolean isUpdated = courseService.updateCourse(courseDao, courseId);
		if (isUpdated) {
			redirectAttributes.addFlashAttribute("courseUpdateSuccess", "Successfully updated the course!");
			return "redirect:/admin/course/manageCourse";
		}

		return "pages/course/add-course";
	}

}
