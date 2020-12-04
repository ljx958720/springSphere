package com.ghy.springbootsharding;



import com.ghy.springbootsharding.service.UserService;
import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import javax.annotation.Resource;

/**
 * 演示取模的分库分表策略
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.ghy.springbootsharding.mapper")
public class UserShardingTest {
	@Resource
	UserService userService;

	@Test
	public void insert(){

		userService.insert();
	}
}
