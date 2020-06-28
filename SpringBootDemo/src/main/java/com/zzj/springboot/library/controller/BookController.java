package com.zzj.springboot.library.controller;

import com.zzj.springboot.model.Book;
import com.zzj.springboot.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zzj on 2020/6/2.
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;
    @RequestMapping("/list")
    @ResponseBody
    public List<Book> list(){
        return bookService.findAll();
    }
    @PostMapping("/addOrUpdateBook")
    public void addOrUpdateBook(@RequestBody Book book){
        book.setCid(book.getCategory().getId());
        bookService.addOrUpdateBook(book);
    }
    @PostMapping("/delete")
    public void deleteBook(@RequestBody Book book){
        bookService.deleteBookById(book.getId());
    }
}
