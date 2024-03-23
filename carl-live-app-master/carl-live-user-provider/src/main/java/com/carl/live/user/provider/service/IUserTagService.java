package com.carl.live.user.provider.service;

import com.carl.live.user.interfaces.constants.UserTagsEnum;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-23 18:07
 * @version: 1.0
 */
public interface IUserTagService {
    /**
     * 设置标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    boolean setTag(Long userId, UserTagsEnum userTagsEnum);

    /**
     * 取消标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    boolean cancelTag(Long userId,UserTagsEnum userTagsEnum);

    /**
     * 是否包含某个标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    boolean containTag(Long userId, UserTagsEnum userTagsEnum);

    /**
     * 插入用户标签
     * @param userId
     * @return
     */
    int insertUserTag(Long userId);
}
