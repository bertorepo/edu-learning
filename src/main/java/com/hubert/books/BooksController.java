package com.hubert.books;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hubert.books.category.BookCategory;
import com.hubert.books.category.IBookCategoryService;

@Controller
public class BooksController {

	private IBookService bookService;
	private IBookCategoryService bookCategoryService;

	@Autowired
	public BooksController(IBookService bookService, IBookCategoryService bookCategoryService) {
		this.bookService = bookService;
		this.bookCategoryService = bookCategoryService;
	}

	@GetMapping("/books")
	public String displayBooksPage() {
		return "pages/books";
	}

	@GetMapping("/admin/book/addBook")
	public String displayAddBookPage(Model model) {

		model.addAttribute("bookDao", new BookDao());

		// retrieving the Book Category
		List<BookCategory> bookCategories = bookCategoryService.getAllBookCategories();
		model.addAttribute("bookCategories", bookCategories);
		return "pages/book/add-book";
	}

	@PostMapping("/admin/book/addBook")
	public String addBook(@Valid @ModelAttribute("bookDao") BookDao bookDao, BindingResult bindingResult, Model model) {

		// retrieving the Book Category
		List<BookCategory> bookCategories = bookCategoryService.getAllBookCategories();
		model.addAttribute("bookCategories", bookCategories);

		if (bindingResult.hasErrors()) {
			return "pages/book/add-book";
		}

		// save the book
		Book newBook = bookService.saveBook(bookDao);
		if (newBook.getId() > 0) {
			return "redirect:/?addBook=true";
		}

		return "redirect:/?addBook=false";
	}
}
