package io.chat.log.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 根据此实体的注解来创建数据库表结构
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String passWord;
    private String email;
    private String nickName;
    private String regTime;
}
