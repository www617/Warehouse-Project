package com.neuedu.yyzx.pojo.wsj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

//疗养中心-顾客表单


@Data
@Entity
@Table(name = "customers")
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "status")
    @ApiModelProperty(value = "状态（0：未登记；1：入住；2：退住）")
    private Integer status;


    @Column(name = "name")
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别（0：女；1：男）")
    private Integer sex;

    @Column(name = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Column(name = "phone_number")
    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}