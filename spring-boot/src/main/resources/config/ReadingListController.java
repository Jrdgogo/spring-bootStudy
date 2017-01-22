package com.manning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.manning.pojo.Book;
import com.manning.service.ReadingListRepository;

/**
* <p>Title: ReadingListController</p>
* <p>Description: </p>
* <p>Company: 苏州朗动</p> 
* @author hxh
* @date 2017年1月20日 下午9:37:08
*/
@Controller
@RequestMapping
public class ReadingListController {

	private ReadingListRepository readingListRepository;
	
	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository){
		this.readingListRepository = readingListRepository;
	}
	
	@RequestMapping(value="/{reader}",method=RequestMethod.GET)
	public String readersBooks(@PathVariable("reader") String reader,Model model){
		List<Book> readingList = readingListRepository.findByReader(reader);
		if(readingList!=null){
			model.addAttribute("books", readingList);
		}
		return "readingList";
	}
	
	@RequestMapping(value="/{reader}",method=RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader,Book book){
		book.setReader(reader);
		readingListRepository.save(book);
		return "redirect:/{reader}";
	}
}
