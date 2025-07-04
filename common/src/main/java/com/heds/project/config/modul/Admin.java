package com.heds.project.config.modul;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String password;
    private String email;
    private boolean enabled;
}
