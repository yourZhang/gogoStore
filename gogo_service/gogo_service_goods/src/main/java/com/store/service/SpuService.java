package com.store.service;

import com.github.pagehelper.Page;
import com.store.pojo.Goods;
import com.store.pojo.Spu;

import java.util.List;
import java.util.Map;

public interface SpuService {

    /***
     * 查询所有
     * @return
     */
    List<Spu> findAll();

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Spu findById(String id);

    /***
     * 新增
     * @param spu
     */
    void add(Spu spu);

    /***
     * 修改
     * @param spu
     */
    void update(Spu spu);

    /***
     * 删除
     * @param id
     */
    void delete(String id);

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Spu> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Spu> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Spu> findPage(Map<String, Object> searchMap, int page, int size);


    void addGoods(Goods goods);

    Goods findGoods(String spuId);

    void updateGoods(Goods goods);

    void auditGoods(String spuId);

    void upGoods(String spuId);

    void down(String spuId);

    void deleteLogic(String spuId);

    void restore(String spuId);

    void deleteGoods(String spuId);
}
