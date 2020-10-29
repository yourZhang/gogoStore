package com.store.system.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * role实体类
 * @author 黑马架构师2.5
 *
 */
@Table(name="tb_role")
@Data
public class Role implements Serializable {

	@Id
	private Integer id;//ID


	
	private String name;//角色名称


}
