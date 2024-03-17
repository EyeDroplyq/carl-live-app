package com.carl.live.user.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carl.live.user.provider.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-17 13:21
 * @version: 1.0
 */
@Mapper
public interface IUserMapper extends BaseMapper<UserPO> {
}
