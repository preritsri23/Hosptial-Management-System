package com.clinicmgmtsystem.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.clinicmgmtsystem.entities.Admin;
import com.clinicmgmtsystem.entities.Patient;
import com.clinicmgmtsystem.services.AdminService;
import com.clinicmgmtsystem.services.PatientService;




@Controller
public class HomeController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PatientService patientService;
	
    @GetMapping("/")
	public String homepage() {
		return "home.html";
	}
    @GetMapping("/login")
    public String login() {
    	return "login.html";
    }
    @GetMapping("/register")
    public String register() {
    	return "register.html";
    }
    @GetMapping("/register_patient")
    public String register_patient() {
    	return "register-patient.html";
    }
    @GetMapping("/register_admin")
    public String register_admin() {
    	return "register-admin.html";
    }
    
    @PostMapping("/do_register_admin")
    public String registerAdmin(@ModelAttribute("admin") Admin admin,Model model) {
    	admin.setRole("ADMIN");
    	admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    	admin.setAdminCode("HUSS"+admin.getId());
    	adminService.saveAdmin(admin);
    	return "register-admin.html";
    	
    }
    @PostMapping("do_register_patient")
    public String registerPatient(@ModelAttribute("patient")Patient patient,Model model) {
    	patient.setRole("PATIENT");
    	patient.setPassword(passwordEncoder.encode(patient.getPassword()));
    	patientService.savePatient(patient);
    	return "register-patient.html";
    }
    
    
}
