package com.sobev.OpLock.controller;

import com.sobev.OpLock.dao.PersonDao;
import com.sobev.OpLock.dao.StorageDao;
import com.sobev.OpLock.entity.Person;
import com.sobev.OpLock.entity.Storage;
import com.sobev.OpLock.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NagController {

  @Autowired
  PersonDao personDao;

  @Autowired
  StorageService storageService;

  @GetMapping("/")
  public List<Person> getALl(){
    return personDao.findAll();
  }

  @GetMapping("/s")
  public Integer updateStock(@RequestParam("id")Integer id,
                             @RequestParam("stock")Integer stock){

    Integer res = storageService.updateStock(id, stock);
    return res;
  }

  @GetMapping("/redis")
  public String setRedisStock(){
    storageService.setRedisStock();
    return "ok";
  }
  @GetMapping("uredis")
  public String updateRedisStock(){
    storageService.updateRedisStock();
    return "ok";
  }


}
