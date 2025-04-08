package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_storage")
public class Storage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Integer total;
    private Integer used;
    private Integer residue;
}