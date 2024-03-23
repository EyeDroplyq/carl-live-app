package com.carl.live.user.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carl.live.user.provider.dao.po.UserTagPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-23 18:07
 * @version: 1.0
 */
@Mapper
public interface IUserTagMapper extends BaseMapper<UserTagPO> {
    /**
     * 设置用户标签
     * @param userId
     * @param tag
     * @param fileName
     * @return
     */
    @Update("update t_user_tag set ${fileName}=${fileName}|#{tag} where user_id=#{userId} and (${fileName} & #{tag})=0")
    int setTag(@Param("userId") Long userId, @Param("tag") long tag, @Param("fileName") String fileName);

    /**
     * 取消用户的标签
     * @param userId
     * @param tag
     * @param fileName
     * @return
     */
    @Update("update t_user_tag set ${fileName}=${fileName}&~#{tag} where user_id=#{userId} and (${fileName} & #{tag})=#{tag}")

    int cancelTag(@Param("userId") Long userId, @Param("tag") long tag, @Param("fileName") String fileName);
}
