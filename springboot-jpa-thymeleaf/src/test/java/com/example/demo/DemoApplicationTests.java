package com.example.demo;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() throws Exception {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		userRepository.deleteAll();
		List<User> list = userRepository.findAll();
		if(list==null || list.size()==0) {
			System.out.println(String.format("====================>%s","保存"));
			userRepository.save(new User("沈万三", "123@126.com", "123", "123", formattedDate));
			userRepository.save(new User("唐明皇", "456@126.com", "456", "456", formattedDate));
			userRepository.save(new User("秦始皇", "789@126.com", "789", "789", formattedDate));
		}
		List<User> result = userRepository.findAll();
		for(User user:result){
			System.out.println(String.format("====================>%s,%s,%s",user.getUserName(),user.getEmail(),user.getNickName()));
		}
	}

}

