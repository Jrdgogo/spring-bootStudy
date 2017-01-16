package org.study.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.boot.dao.UserDAO;
import org.study.boot.pojo.User;
import org.study.boot.util.FTPClientForFile;
import org.study.boot.util.HttpClientUtil;


@RestController
public class StudyController {
    @Autowired
	private UserDAO userDao;
    @Autowired
    private FTPClientForFile ftpClientForFile;
    
    @GetMapping("/getUserAll")
    public List<User> getUsers(){
    	return userDao.findAll();
    }
    @GetMapping("/")
    public String rootReq(HttpServletRequest request){
    	StringBuffer url=request.getRequestURL();
    	return HttpClientUtil.getInstance().sendHttpGet(url.toString()+"getUserAll");
    }

}
