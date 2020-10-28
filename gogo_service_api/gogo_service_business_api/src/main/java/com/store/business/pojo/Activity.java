package com.store.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Table(name="tb_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity implements Serializable {

	@Id
	private Integer id;//ID

	private String title;//活动标题
	private java.util.Date startTime;//开始时间
	private java.util.Date endTime;//结束时间
	private String status;//状态
	private String content;//活动内容

}
