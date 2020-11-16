package com.store.kill.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Data
@Table(name="tb_seckill_order")
public class SeckillOrder implements Serializable {

	@Id
	private Long id;//主键

	
	private Long seckillId;//秒杀商品ID
	private Double money;//支付金额
	private String userId;//用户
	private String sellerId;//商家
	private java.util.Date createTime;//创建时间
	private java.util.Date payTime;//支付时间
	private String status;//状态，0未支付，1已支付
	private String receiverAddress;//收货人地址
	private String receiverMobile;//收货人电话
	private String receiver;//收货人
	private String transactionId;//交易流水


}
