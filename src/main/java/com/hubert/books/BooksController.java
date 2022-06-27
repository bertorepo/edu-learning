package com.hubert.books;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BooksController {

	@GetMapping("/books")
	public String displayBooksPage() {
		return "pages/books";
	}
}
