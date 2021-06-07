package com.sobev.OpLock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class OpLockApplicationTests {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Test
	void test() {
		String stock = stringRedisTemplate.opsForValue().get("stock");
		System.out.println("stock = " + stock);
	}

}
