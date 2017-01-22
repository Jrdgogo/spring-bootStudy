package org.study.boot.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.study.boot.dao.UserDAO;
import org.study.boot.pojo.User;
import org.study.boot.util.FTPClientForFile;

import net.lingala.zip4j.exception.ZipException;


@RestController
public class StudyController {
    @Autowired
	private UserDAO userDao;
    @Autowired
    private FTPClientForFile fTPClientForFile;
    
    
    @GetMapping("/getUserAll")
    public List<User> getUsers(HttpServletRequest request,@RequestParam("url")String url) throws ZipException{
    	return userDao.findAll();
    }
    @GetMapping("/downFile.action")
    public List<User> downFile() throws ZipException{
    	return null;
    }
    @PostMapping("/uploadFile.action")
    public String rootReq(@RequestParam("files")List<MultipartFile> files) throws IOException{
    	fTPClientForFile.login();
    	for(MultipartFile file:files){
    		System.out.println(file.getName());
    		System.out.println(file.getOriginalFilename());
    		fTPClientForFile.uploadFile(file.getOriginalFilename(),file.getInputStream());
    	}
    	fTPClientForFile.logout();
    	fTPClientForFile.disconnect();
    	return "Ok";
    }

}
