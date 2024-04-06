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
 * @createDate: 2024-04-06 16:06
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_gift_record")
public class GitRecordPO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4565850423496908250L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long objectId;
    private Integer source;
    private Integer price;
    private Integer priceUnit;
    private Integer giftId;
    private Date sendTime;
}
