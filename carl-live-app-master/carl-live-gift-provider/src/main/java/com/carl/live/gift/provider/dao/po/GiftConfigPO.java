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
 * @createDate: 2024-04-06 15:20
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_gift_config")
public class GiftConfigPO implements Serializable {
    @Serial
    private static final long serialVersionUID = -91699670934674200L;
    @TableId(type = IdType.AUTO)
    private Integer giftId;
    private Integer price;
    private String giftName;
    private Integer status;
    private String coverImgUrl;
    private String svgaUrl;
    private Date createTime;
    private Date updateTime;

}
