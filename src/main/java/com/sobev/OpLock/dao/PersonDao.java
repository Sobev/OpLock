package com.sobev.OpLock.dao;

import com.sobev.OpLock.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonDao {
  List<Person> findAll();
}
