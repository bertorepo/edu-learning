package com.hubert.books.category;

import javax.validation.constraints.NotBlank;

public class BookCategoryDao {
    @NotBlank(message = "Book Category should not be empty!")
    private String bookCategoryName;

    public String getBookCategoryName() {
        return bookCategoryName;
    }

    public void setBookCategoryName(String bookCategoryName) {
        this.bookCategoryName = bookCategoryName;
    }

}
