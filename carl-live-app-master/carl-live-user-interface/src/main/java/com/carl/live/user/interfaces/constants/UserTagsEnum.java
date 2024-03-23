package com.carl.live.user.interfaces.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: 用户标签枚举类
 * @author: 小琦
 * @createDate: 2024-03-23 18:02
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum UserTagsEnum {
    IS_VIP((long)Math.pow(2,1),"tag_info_01","是否是VIP会员"),
    IS_OLD_USER((long)Math.pow(2,2),"tag_info_01","是否是老用户");
    public long tag;
    public String fileName;
    public String desc;


}
