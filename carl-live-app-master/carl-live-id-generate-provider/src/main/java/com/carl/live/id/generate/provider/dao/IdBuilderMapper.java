package com.carl.live.id.generate.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carl.live.id.generate.provider.po.IdBuilderPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-19 21:00
 * @version: 1.0
 */
@Mapper
public interface IdBuilderMapper extends BaseMapper<IdBuilderPO> {
    @Select("select * from t_id_generate_config")
    List<IdBuilderPO> selectAll();

    @Update("update t_id_generate_config set next_threshold=next_threshold+step,current_start=current_start+step,version=version+1 where id=#{id} and version=#{version}")
    int updateGenerateConfig(@Param("id") int id, @Param("version") int version);
}
