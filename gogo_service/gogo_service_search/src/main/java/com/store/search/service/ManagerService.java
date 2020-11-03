
package com.store.search.service;

public interface ManagerService {

    /**
     * 功能描述: <br>
     * 〈导入历史数据〉
     *
     * @Param: []
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 18:21
     */
    void importAll();

    /**
     * 功能描述: <br>
     * 〈商品上架导入es索引库〉
     *
     * @Param: [spuId]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 18:21
     */
    void importBySpuId(String spuId);
}
