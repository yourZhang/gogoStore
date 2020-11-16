package com.store.kill.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="tb_seckill_goods")
public class SeckillGoods implements Serializable {

	@Id
	private Long id;//id
	
	private Long goodsId;//spu ID
	private Long itemId;//sku ID
	private String title;//标题
	private String smallPic;//商品图片
	private Double price;//原价格
	private Double costPrice;//秒杀价格
	private String sellerId;//商家ID
	private java.util.Date createTime;//添加日期
	private java.util.Date checkTime;//审核日期
	private String status;//审核状态，0未审核，1审核通过，2审核不通过
	private java.util.Date startTime;//开始时间
	private java.util.Date endTime;//结束时间
	private Integer num;//秒杀商品数
	private Integer stockCount;//剩余库存数
	private String introduction;//描述

}
