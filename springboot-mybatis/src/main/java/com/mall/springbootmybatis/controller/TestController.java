package com.mall.springbootmybatis.controller;

import com.mall.springbootmybatis.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

	@RequestMapping("test")
	public String test(){
		System.out.println("xdclass.net");

		return "hello xdclass.net777";
	}


	@Autowired
	private VideoMapper videoMapper;

	@RequestMapping("test_db")
	public Object testDB(){


		return videoMapper.findAll();
	}





}
