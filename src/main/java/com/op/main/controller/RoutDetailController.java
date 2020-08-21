package com.op.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rout")
public class RoutDetailController {

	@GetMapping("/getRoutdetial")
	public String gettotalroutDetail() {
		
		return "today 2 km travale";
	}
}
