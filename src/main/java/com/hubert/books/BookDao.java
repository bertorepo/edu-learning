package com.hubert.books;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hubert.books.category.BookCategory;

public class BookDao {

    @NotBlank(message = "Name should not be empty!")
    private String bookName;

    private String bookDescription;

    @NotNull(message = "Book Category sould not be empty!")
    private BookCategory bookCategory;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

}
