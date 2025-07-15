package com.heds.project.config.modul;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="consultation")
@Data
public class Consultation {
    @TableId(type = IdType.AUTO) // 如果你用的是 MyBatis-Plus
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private String status;
}
