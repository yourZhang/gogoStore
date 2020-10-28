package com.store.config.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_freight_template")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplate implements Serializable {

    @Id
    private Integer id;//ID


    private String name;//模板名称
    private String type;//计费方式


}
