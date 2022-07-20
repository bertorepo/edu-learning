package com.hubert.books;

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

import com.hubert.books.category.BookCategory;
import com.hubert.books.category.IBookCategoryService;
import com.hubert.courses.Course;
import com.hubert.customer.Customer;

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
	public String displayBooksPage(Model model, HttpSession session) {
		Customer cust = (Customer) session.getAttribute("loggedInCustomer");
		model.addAttribute("username", cust.getUsername());

		// call all books
		List<Book> allBooks = bookService.getAllBooks();
		if (allBooks == null) {
			model.addAttribute("allBooks", null);
		}
		model.addAttribute("allBooks", allBooks);

		return "pages/book/books";
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
			return "redirect:/admin/book/manage-books";
		}

		return "redirect:/?addBook=false";
	}

	// MANAGE BOOK
	@GetMapping("/admin/book/manage-books")
	public String displayManageBooks(Model model, HttpSession session) {
		Customer cust = (Customer) session.getAttribute("loggedInCustomer");
		model.addAttribute("username", cust.getUsername());

		// call all books
		List<Book> allBooks = bookService.getAllBooks();
		if (allBooks == null) {
			model.addAttribute("allBooks", null);
		}
		model.addAttribute("allBooks", allBooks);

		return "pages/book/books";
	}

	@PostMapping("/admin/book/delete/{bookId}")
	public String deleteBook(@PathVariable("bookId") Long bookId, Model Model, RedirectAttributes redirectAttributes) {
		if (bookId <= 0 || bookId == null) {
			redirectAttributes.addFlashAttribute("deleteError", "Error deleting the book!");
			return "pages/book/books";
		}

		// CALL THE SERVICE
		boolean isDeleted = bookService.deleteBook(bookId);
		if (isDeleted) {
			redirectAttributes.addFlashAttribute("deleteSuccess", "Successfully deleted the book!");
			return "redirect:/admin/book/manage-books";
		}
		return "pages/book/books";
	}

	@PostMapping("/admin/book/status/{bookId}")
	public String updateBookStatus(@PathVariable("bookId") Long bookId, Model model,
			RedirectAttributes redirectAttributes) {
		if (bookId <= 0 || bookId == null) {
			redirectAttributes.addFlashAttribute("bookStatusError", "Error updating the book status!");
			return "pages/book/books";
		}

		// call service
		boolean isUpdated = bookService.updateStatus(bookId);
		if (isUpdated) {
			redirectAttributes.addFlashAttribute("bookStatusSuccess", "Book status updated!");
			return "redirect:/admin/book/manage-books";
		}
		return "pages/book/books";
	}

}
