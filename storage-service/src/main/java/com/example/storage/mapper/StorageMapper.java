package com.example.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
    @Update("UPDATE t_storage SET used = used + #{count}, residue = residue - #{count} WHERE product_id = #{productId} AND residue >= #{count}")
    int decrease(@Param("productId") Long productId,@Param("count") Integer count);
}