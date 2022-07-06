package com.hubert.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubert.books.category.BookCategory;
import com.hubert.books.category.IBookCategoryService;

@Service
public class BookServceImpl implements IBookService {

    private BookRepository bookRepository;
    private IBookCategoryService bookCategoryService;

    @Autowired
    public BookServceImpl(BookRepository bookRepository, IBookCategoryService bookCategoryService) {
        this.bookRepository = bookRepository;
        this.bookCategoryService = bookCategoryService;
    }

    @Override
    public Book saveBook(BookDao bookDao) {
        Book newBook = null;
        if (bookDao != null) {
            newBook = new Book();
            newBook.setBookName(bookDao.getBookName());
            newBook.setBookLink(bookDao.getBookLink());
            newBook.setBookDescription(bookDao.getBookDescription());
            newBook.setBookSize(bookDao.getBookSize());

            BookCategory existingBookCategory = bookCategoryService
                    .findBookCatgoryById(bookDao.getBookCategory().getId());

            if (existingBookCategory.getId() > 0) {
                newBook.setBookCategory(existingBookCategory);
            }
            bookRepository.save(newBook);
        }

        return newBook;
    }

}
