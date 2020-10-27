package com.store.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * brand实体类
 * @author 黑马架构师2.5
 *
 */
@Table(name="tb_brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand implements Serializable {

	@Id
	private Integer id;//品牌id
	private String name;//品牌名称
	private String image;//品牌图片地址
	private String letter;//品牌的首字母
	private Integer seq;//排序
}
