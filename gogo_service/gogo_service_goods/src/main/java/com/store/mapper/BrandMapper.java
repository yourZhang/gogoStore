package com.store.mapper;

import com.store.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Select("SELECT t1.* FROM tb_brand t1 JOIN tb_category_brand t2 ON t1.id = t2.brand_id JOIN " +
            "tb_category t3 ON t2.category_id = t3.id WHERE t3.name=#{cateName}")
    List<Brand> findBrandByCateName(String cateName);
}
