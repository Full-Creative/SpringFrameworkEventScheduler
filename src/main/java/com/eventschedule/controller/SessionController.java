package com.eventschedule.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventschedule.datastore.DataBaseException;
import com.eventschedule.model.User;

@Controller
@RequestMapping("/user")
public class SessionController {
	@PostMapping
	@ResponseBody
	public String Name(@RequestBody User user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("user", user.getName());

		return (String) session.getAttribute("user");
	}
	@GetMapping
	@ResponseBody
	public String GetName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute("user");
	}
	@GetMapping(value="/exit")
	@ResponseBody
	public String ExitSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "exited!";
	}

}
