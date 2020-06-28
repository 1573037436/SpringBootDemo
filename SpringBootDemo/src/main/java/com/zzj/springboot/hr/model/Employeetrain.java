package com.zzj.springboot.hr.model;

import lombok.Data;

import java.util.Date;
@Data
public class Employeetrain {
    private Integer id;

    private Integer eid;

    private Date traindate;

    private String traincontent;

    private String remark;
}