package com.sobev.OpLock.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sobev.OpLock.dao.StorageDao;
import com.sobev.OpLock.entity.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StorageService {
  @Autowired
  StorageDao storageDao;

  @Autowired
  StringRedisTemplate stringRedisTemplate;

  public Integer updateStock(Integer id, Integer stock) {
    Integer data_version = getDataVersionById(id);
    Integer result = storageDao.updateStock(id, stock, data_version);
    if (result == 1) {
      log.info("更新成功");
      return result;
    } else {
      log.warn("更新失败自旋中");
      return updateStock(id, stock);
    }
  }

  public Integer getDataVersionById(Integer id) {
    Integer version = storageDao.getDataVersionById(id);
    return version;
  }

  public void setRedisStock() {
    Storage storage = new Storage(1, 1000, 0);
    stringRedisTemplate.opsForValue().set("stock", JSON.toJSONString(storage));
  }

  public void updateRedisStock() {
    /**
     * 感觉RedisTemplate提供的SessionCallback才是正解~
     * RedisTemplate的public <T> T execute(SessionCallback<T> session)方法，会在finally中调用RedisConnectionUtils.unbindConnection(factory);
     * 来解除执行过程中与当前线程绑定的连接，并在随后关闭连接。
     */
    /**
     * 最好使用Jedisson  锁🔒
     */
    /**
     *   stringRedisTemplate.setEnableTransactionSupport(true);
     * 		stringRedisTemplate.watch("stock");
     * 		stringRedisTemplate.multi();
     * 		stringRedisTemplate.opsForValue().increment("count",-1);
     * 		List list = stringRedisTemplate.exec();    //list 长度不为 0 则成功
     * 		stringRedisTemplate.unwatch();
     */
    stringRedisTemplate.setEnableTransactionSupport(true);
    stringRedisTemplate.watch("stock");
    stringRedisTemplate.multi();
    String stock1 = stringRedisTemplate.opsForValue().get("stock");
    Storage storage = JSON.parseObject(stock1, new TypeReference<Storage>() {
    });
    storage.setStock(storage.getStock() - 1);
    stringRedisTemplate.opsForValue().set("stock", JSON.toJSONString(storage));
    stringRedisTemplate.exec();

    stringRedisTemplate.unwatch();

  }
}
