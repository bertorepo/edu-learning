package com.hubert.books.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hubert.constants.BaseEntity;

@Entity
public class BookCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "book_category_name")
    private String bookCategoryName;

    public BookCategory() {
    }

    public BookCategory(Long id, String bookCategoryName) {
        this.id = id;
        this.bookCategoryName = bookCategoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookCategoryName() {
        return bookCategoryName;
    }

    public void setBookCategoryName(String bookCategory) {
        this.bookCategoryName = bookCategory;
    }

}
