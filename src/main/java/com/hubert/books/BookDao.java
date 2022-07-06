package com.hubert.books;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hubert.books.category.BookCategory;

public class BookDao {

    @NotBlank(message = "Name should not be empty!")
    private String bookName;

    @NotBlank(message = "Size should not be empty!")
    private String bookSize;

    private String bookDescription;

    @NotBlank(message = "Link should not be empty!")
    private String bookLink;

    @NotNull(message = "Book Category sould not be empty!")
    private BookCategory bookCategory;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookSize() {
        return bookSize;
    }

    public void setBookSize(String bookSize) {
        this.bookSize = bookSize;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

}
