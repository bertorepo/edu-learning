package com.hubert.books.category;

import java.util.List;

public interface IBookCategoryService {
    BookCategory saveBookCategory(BookCategoryDao bookCategoryDao);

    BookCategory findBookCatgoryById(Long id);

    List<BookCategory> getAllBookCategories();
}
