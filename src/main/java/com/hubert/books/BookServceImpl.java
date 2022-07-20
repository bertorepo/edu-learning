package com.hubert.books;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubert.books.category.BookCategory;
import com.hubert.books.category.IBookCategoryService;
import com.hubert.constants.Constants;

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
            newBook.setBookDescription(bookDao.getBookDescription());
            newBook.setEnabled(!Constants.IS_ENABLED);

            BookCategory existingBookCategory = bookCategoryService
                    .findBookCatgoryById(bookDao.getBookCategory().getId());

            if (existingBookCategory.getId() > 0) {
                newBook.setBookCategory(existingBookCategory);
            }
            bookRepository.save(newBook);
        }

        return newBook;
    }

    @Override
    public List<Book> getAllBooks() {

        List<Book> allBooks = bookRepository.findAll();
        if (allBooks == null) {
            return null;
        }
        return allBooks;
    }

    @Override
    public Book findBookById(Long id) {

        Optional<Book> existingBook = bookRepository.findById(id);
        if (!existingBook.isPresent()) {
            return null;
        }
        return existingBook.get();
    }

    @Override
    public boolean updateExistingBook(Book book) {
        boolean isUpdated = false;
        if (book.getId() > 0) {
            bookRepository.save(book);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteBook(Long id) {
        boolean isDeleted = false;
        Book existingBook = findBookById(id);
        if (existingBook.getId() > 0) {
            bookRepository.delete(existingBook);
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean updateStatus(Long id) {
        boolean isUpdated = false;
        Book existingBook = findBookById(id);
        if (existingBook.getId() > 0) {
            existingBook.setEnabled(!existingBook.isEnabled());
            updateExistingBook(existingBook);
            isUpdated = true;
        }
        return isUpdated;
    }

}
