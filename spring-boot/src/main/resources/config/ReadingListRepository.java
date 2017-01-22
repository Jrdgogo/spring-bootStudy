package com.manning.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manning.pojo.Book;

/**
* <p>Title: ReadingListRepository</p>
* <p>Description: </p>
* <p>Company: 苏州朗动</p> 
* @author hxh
* @date 2017年1月20日 下午9:36:13
*/
public interface ReadingListRepository extends JpaRepository<Book, Long> {

	List<Book> findByReader(String reader);
	
}
