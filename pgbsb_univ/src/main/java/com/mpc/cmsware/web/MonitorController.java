package com.mpc.cmsware.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mpc.middleware.service.MonitorService;

@Controller
public class MonitorController {
	@Autowired
	private MonitorService monitorService;
	
	@RequestMapping(value="/channel", method = RequestMethod.GET)
	public String getChannel(Model model) {
		return "channel-monitor";
	}
	@RequestMapping(value="/trx", method = RequestMethod.GET)
	public String getTrx(Model model) {
		return "trx-monitor";
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String checkConnection(Model model) {
		model = getModel(model);
		return "monitor";
	}
	
	@RequestMapping(value="/pgbsb_univ/reload", method=RequestMethod.GET)
	@ResponseBody
	public String ReloadPage(Model model){
		model = getModel(model);
		System.out.println(model.toString());
		return model.toString();
	}
	
	public Model getModel(Model model){
		if (monitorService.isISTConnected()) {
			model.addAttribute("istStatus", "CONNECTED");
		} else {
			model.addAttribute("istStatus", "DISCONNECTED");
		}
		
		if (monitorService.isMDPConnected()) {
			model.addAttribute("mdpStatus", "CONNECTED");
		} else {
			model.addAttribute("mdpStatus", "DISCONNECTED");
		}
		
		if (monitorService.isWSDLConnected()) {
			model.addAttribute("pdamStatus", "CONNECTED");
		} else {
			model.addAttribute("pdamStatus", "DISCONNECTED");
		}
		return model;
	}
}
