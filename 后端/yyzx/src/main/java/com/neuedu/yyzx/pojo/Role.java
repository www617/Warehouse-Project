package com.neuedu.yyzx.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新人ID")
    private Integer updateBy;

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
    private Integer isDeleted;

    @Column(name = "name")
    @ApiModelProperty(value = "系统角色名称")
    private String name;
}