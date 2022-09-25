package com.hi;

import com.hi.dao.JoinBoardDao;
import com.hi.domain.JoinBoardDto;
import com.hi.domain.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@SpringBootTest
class HiApplicationTests {
	@Autowired
	JoinBoardDao boardDao;

	@Test
	public void countTest() throws Exception{
		int result = boardDao.count();

		System.out.println();
		System.out.println("==============================");
		System.out.println("result = " + result);
		System.out.println();

		assertTrue(result!=0);
	}

	@Test
	public void searchTest() throws Exception {
		SearchCondition searchCondition = new SearchCondition(1,5,"","");
		List<JoinBoardDto> list = boardDao.search("","","2000-01-01","9999-12-30",searchCondition);

		System.out.println();
		System.out.println("==============================");
		System.out.println("listSize = " + list.size());
		for(int i=0; i< list.size(); i++){
			if(list.size()!=0)
				System.out.println(list.get(i));
		}
		assertTrue(list.size()!=0);
	}

	@Test
	public void searchCountTest() throws Exception {
		SearchCondition searchCondition = new SearchCondition(1,5,"","");
		int result = boardDao.searchCount("","","2022-06-10","2022-08-18");

		System.out.println();
		System.out.println("==============================");
		System.out.println("result = " + result);
		System.out.println();
		assertTrue(result!=0);
	}

}
