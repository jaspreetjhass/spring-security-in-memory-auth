package org.jp.spring.security.in.memory.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("in-memory")
public class AppController {

	@GetMapping("/user")
	public String user() {
		return "hello user";
	}

	@GetMapping("/admin")
	public String admin() {
		return "hello admin";
	}

	@GetMapping("/health")
	public String health() {
		return "Ok";
	}
}
