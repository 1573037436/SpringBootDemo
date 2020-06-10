package com.zzj.springboot.object;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzj on 2020/6/8.
 */
@Data
public class AbstractDO implements Serializable {

    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     */
    private static final long serialVersionUID = 5088697673359856350L;

    private Long id;

    private Date createTime;
    private Date updateTime;
}
