package com.store.mapper;


import com.store.pojo.Spec;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecMapper extends Mapper<Spec> {

    /**
     * @Author: guodong
     * @Date: 10:55 2020/10/28
     * @Parms [cateName]
     * @ReturnType: com.store.entity.Result @Param("cateName")
     * @Description: 根据分类名称查询规格列表
     */
    @Select("SELECT DISTINCT t1.* FROM tb_spec t1 JOIN tb_category t2 ON t1.template_id = t2.template_id WHERE t2.name=#{cateName}")
    List<Spec> findByCateName(String cateName);
}
