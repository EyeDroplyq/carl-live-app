package com.carl.live.sms.provider.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-25 21:45
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sms")
public class SmsPO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4514374150311657393L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private String phone;
    @TableField("sendTime")
    private Date sendTime;
    private Date updateTime;
}
