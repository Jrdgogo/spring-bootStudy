package org.study.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.study.boot.pojo.User;

public interface UserDAO extends JpaRepository<User, Integer>{
	
	   
}
