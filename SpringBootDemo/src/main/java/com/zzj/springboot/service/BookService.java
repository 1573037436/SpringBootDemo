package com.zzj.springboot.service;

import com.zzj.springboot.mapper.BookMapper;
import com.zzj.springboot.model.Book;
import com.zzj.springboot.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzj on 2020/6/2.
 */
@Service
public class BookService {
    @Autowired
    BookMapper bookMapper;
    public List<Book> findAll(){
        List<Book> books=bookMapper.selectList(null);
        //给category赋值
        books.forEach(book -> book.setCategory(new Category(book.getCid(),null)));
        return books;
    }

    public void addOrUpdateBook(Book book){
        Book bookOld=bookMapper.selectById(book.getId());
        if(bookOld==null){
            bookMapper.insert(book);
        }else{
            bookMapper.updateById(book);
        }

    }

    public void deleteBookById(int id){
        bookMapper.deleteById(id);
    }
}
