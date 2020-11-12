package com.store.mapper;

import com.store.pojo.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku> {
    /**
     * 功能描述: <br>
     * 〈扣减库存, 增加销量〉
     *
     * @Param: [skuId, num]
     * @return: int
     * @Author: xiaozhang666
     * @Date: 2020/11/12 15:13
     */
    @Update("update tb_sku set num = num - #{num}, sale_num = sale_num + #{num} where id = #{skuId} and num >= #{num}")
    public int decrCount(@Param("skuId") String skuId, @Param("num") Integer num);

    /**
     * 功能描述: <br>
     * 〈恢复库存, 恢复销量〉
     *
     * @Param: [skuId, num]
     * @return: int
     * @Author: xiaozhang666
     * @Date: 2020/11/12 15:13
     */
    @Update("update tb_sku set num = num + #{num}, sale_num = sale_num - #{num} where id = #{skuId}")
    public int incrCount(@Param("skuId") String skuId, @Param("num") Integer num);
}
