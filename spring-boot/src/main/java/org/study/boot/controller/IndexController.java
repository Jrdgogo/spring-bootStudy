package org.study.boot.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	

	/*@RequestMapping("/")
	public ModelAndView index(ModelAndView andView ) throws IOException {
		andView.setViewName("index");
		return andView;
	}*/
	@RequestMapping("/")
	public String index(ModelAndView andView ) throws IOException {
		//andView.setViewName("index");
		return "index";
	}
	

}
