package org.study.boot.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.study.boot.dao.UserDAO;
import org.study.boot.pojo.User;
import org.study.boot.util.FTPClientForFile;

import antlr.ByteBuffer;
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
    public ResponseEntity<byte[]> downFile(@RequestParam("filename")String fileName) throws ZipException, IOException{
    	fTPClientForFile.login();
    	InputStream in=fTPClientForFile.downFile(fileName);
    	System.out.println(in);
    	ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
    	byte[] buf=new byte[1024];
    	if(in!=null){
    		int len=0;
    		while((len=in.read(buf))>0) {
    			 arrayOutputStream.write(buf, 0, len);
			}
    	}
    	HttpHeaders headers = new HttpHeaders(); 
    	headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    	String dfileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
    	System.out.println(dfileName);
    	headers.setContentDispositionFormData("attachment", dfileName); 
        ResponseEntity<byte[]> entity= new ResponseEntity<byte[]>(arrayOutputStream.toByteArray(), headers, HttpStatus.CREATED);
        fTPClientForFile.logout();
    	fTPClientForFile.disconnect();
         return entity;
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
