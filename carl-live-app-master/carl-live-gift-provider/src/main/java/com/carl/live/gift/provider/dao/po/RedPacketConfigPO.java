package com.carl.live.gift.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-21 17:14
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_read_packet_config")
public class RedPacketConfigPO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1636224628839897682L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer anchorId;
    private Date startTime;
    private Integer totalGet;
    private Integer totalGetPrice;
    private Integer maxGetPrice;
    private Integer status;
    private Integer totalPrice;
    private Integer totalCount;
    private String remark;

    private String configCode;

    private Date createTime;
    private Date updateTime;

}
