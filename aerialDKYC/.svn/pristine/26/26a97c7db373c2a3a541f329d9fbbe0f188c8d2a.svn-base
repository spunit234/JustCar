package com.edios.cdf.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping(value = "/")
	public void getLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("--" + request.getContextPath() + "/static/index.html");
		response.sendRedirect(request.getContextPath() + "/static/index.html");
	}

}
