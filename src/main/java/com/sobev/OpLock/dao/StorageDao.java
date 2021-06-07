package com.sobev.OpLock.dao;

import com.sobev.OpLock.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StorageDao {
  List<Storage> findAll();

  Integer updateStock(@Param("id") Integer id,@Param("stock") Integer stock,@Param("data_version")Integer data_version);

  Integer getDataVersionById(@Param("id") Integer id);
}
