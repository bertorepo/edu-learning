package com.hubert.books.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCategoryServiceImpl implements IBookCategoryService {

    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    public BookCategoryServiceImpl(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    @Override
    public BookCategory saveBookCategory(BookCategoryDao bookCategoryDao) {
        BookCategory cat = null;
        if (bookCategoryDao != null) {
            cat = new BookCategory();
            cat.setBookCategoryName(bookCategoryDao.getBookCategoryName());
            bookCategoryRepository.save(cat);
        }

        return cat;
    }

    @Override
    public BookCategory findBookCatgoryById(Long id) {
        Optional<BookCategory> existingBook = bookCategoryRepository.findById(id);
        if (existingBook.isEmpty()) {
            return null;
        }
        return existingBook.orElseThrow();
    }

    @Override
    public List<BookCategory> getAllBookCategories() {
        List<BookCategory> bookCategories = bookCategoryRepository.findAll();
        if (bookCategories.size() > 0) {
            return bookCategories;
        }
        return null;
    }

}
