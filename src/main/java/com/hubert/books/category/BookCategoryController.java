package com.hubert.books.category;

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
@RequestMapping("/admin/category/book")
public class BookCategoryController {

    private IBookCategoryService bookCategoryServive;

    @Autowired
    public BookCategoryController(IBookCategoryService bookCategoryServive) {
        this.bookCategoryServive = bookCategoryServive;
    }

    @GetMapping("/addBookCategory")
    public String displayBookCatgoryPage(Model model) {
        model.addAttribute("bookCategoryDao", new BookCategoryDao());
        return "pages/book/book-category";
    }

    @PostMapping("/addBookCategory")
    public String addBookCategory(@Valid @ModelAttribute("bookCategoryDao") BookCategoryDao bookCategoryDao,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "pages/book/book-category";
        }

        // call service
        BookCategory cat = bookCategoryServive.saveBookCategory(bookCategoryDao);
        if (cat.getId() > 0) {
            return "redirect:/?addBookCategory=true";
        }

        return "redirect:/?addBookCategory=false";
    }
}
