package com.mpc.cmsware.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PinController {

//	@Autowired
//	private PinService pinService;
	
	@RequestMapping(value="createpin", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void createPin(@RequestParam String cardNo, @RequestParam String token) {
//		if(token != null && token.equals("abcf")){
//			pinService.createPin(cardNo);
//		}
	}
}
