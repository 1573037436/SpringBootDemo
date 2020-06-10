package com.zzj.springboot.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by zzj on 2020/6/2.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    @TableField(exist = false)
    public Category category;
    public int cid;//category的id
    public int id;
    public String cover;
    public String title;
    public String author;
    public String press;
    public String date;
    public String abs;
}
