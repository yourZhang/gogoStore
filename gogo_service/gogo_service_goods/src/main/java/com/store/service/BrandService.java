package com.store.service;

import com.github.pagehelper.Page;
import com.store.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    List<Brand> findAll();

    Brand findById(Integer id);

    Integer add(Brand brand);

    Integer update(Brand brand);

    Integer del(Integer id);

    List<Brand> search(Map<String, String> params);

    Page<Brand> search2(Integer page, Integer pageSize);

    Page<Brand> pageList(Map<String, String> params, Integer page, Integer pageSize);
}
