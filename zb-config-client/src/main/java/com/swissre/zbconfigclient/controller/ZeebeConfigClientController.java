package com.swissre.zbconfigclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@RestController
public class ZeebeConfigClientController {
	/*@Autowired
	ConfigProps configProps;*/

	@Value("${zeebeBroker.host.ITE}")
	private String host;


	@RequestMapping("/test")
	public String test(){
		return "******** HOST *********** ::::: "+host;
	}
}
