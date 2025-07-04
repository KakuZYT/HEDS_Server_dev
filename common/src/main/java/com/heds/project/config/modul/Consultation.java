package com.heds.project.config.modul;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="consultation")
@Data
public class Consultation {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private String status;
}
