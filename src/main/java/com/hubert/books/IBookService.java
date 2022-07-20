package com.hubert.books;

import java.util.List;

public interface IBookService {
    Book saveBook(BookDao bookDao);

    List<Book> getAllBooks();

    boolean deleteBook(Long id);

    boolean updateStatus(Long id);

    Book findBookById(Long id);

    boolean updateExistingBook(Book book);
}
