package com.hubert.courses;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category/course")
public class CourseCategoryController {

	private ICourseCategory courseCatService;

	@Autowired
	public CourseCategoryController(ICourseCategory courseCatService) {
		this.courseCatService = courseCatService;
	}

	@GetMapping("/add")
	public String displayCategoryPage(Model model) {

		model.addAttribute("courseCategoryDao", new CourseCategoryDao());

		return "pages/course-category";
	}

	@PostMapping("/addCategory")
	public String addCoursecategory(@Valid @ModelAttribute("courseCategoryDao") CourseCategoryDao courseCategoryDao, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "pages/course-category";
		}

		boolean saveCourseCat = courseCatService.saveCategory(courseCategoryDao);
		if (saveCourseCat) {
			return "redirect:/";
		}

		return "pages/course-category";
	}
}