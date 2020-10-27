package com.store.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.store.mapper.BrandMapper;
import com.store.pojo.Brand;
import com.store.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @program: gogo-store
 * @description:
 * @author: xiaozhang6666
 * @create: 2020-10-27 11:35
 **/
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        final List<Brand> brands = brandMapper.selectAll();
        return brands;
    }

    @Override
    public Brand findById(Integer id) {
        final Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    public Integer add(Brand brand) {
        final int insert = brandMapper.insert(brand);
        return insert;
    }

    @Override
    public Integer update(Brand brand) {
        /*
            同insert
            updateByPrimaryKey: 表全字段参与sql语句拼接， 无值则设置为null
            updateByPrimaryKeySelective: 有值则参与sql拼接
         */
        final int i = brandMapper.updateByPrimaryKeySelective(brand);
        return i;
    }

    @Override
    public Integer del(Integer id) {
        final int i = brandMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public List<Brand> search(Map<String, String> params) {
        //1.创建example对象，作用：告知查询那张表
        Example example = new Example(Brand.class);
        createExample(params, example);
        List<Brand> brandList = brandMapper.selectByExample(example);
        return brandList;
    }

    /*
        创建example公共方法
    */
    private void createExample(Map<String, String> searchMap, Example example) {
        //2.设置查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置首字母条件
        String letter = searchMap.get("letter");
        if (!StringUtils.isEmpty(letter)) {
            criteria.andEqualTo("letter", letter);
        }
        //设置name条件
        //SELECT * FROM `tb_brand` WHERE letter = "F" AND `name` LIKE "%利%"
        String name = searchMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
    }

    @Override
    public Page<Brand> search2(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return (Page<Brand>) brandMapper.selectAll();
    }

    @Override
    public Page<Brand> pageList(Map<String, String> params, Integer page, Integer pageSize) {
        Example example = new Example(Brand.class);
        createExample(params, example);
        PageHelper.startPage(page, pageSize);
        final List<Brand> brands = brandMapper.selectByExample(example);
        return (Page<Brand>) brands;
    }
}
