package com.neuedu.yyzx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private Integer createBy;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新者")
    private Integer updateBy;

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
    private Integer isDeleted;

    @Column(name = "nickname")
    @ApiModelProperty(value = "真实姓名")
    private String nickname;

    @Column(name = "username")
    @ApiModelProperty(value = "系统账号")
    private String username;

    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别（0：女；1：男）")
    private Integer sex;

    @Column(name = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Column(name = "phone_number")
    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @Column(name = "role_id")
    @ApiModelProperty(value = "系统角色编号")
    private Integer roleId;

}