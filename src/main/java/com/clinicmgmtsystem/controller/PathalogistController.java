package com.clinicmgmtsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinicmgmtsystem.entities.Appointment;
import com.clinicmgmtsystem.entities.Pathalogist;
import com.clinicmgmtsystem.entities.Test;
import com.clinicmgmtsystem.repositories.AppointmentRepository;
import com.clinicmgmtsystem.repositories.PathalogistRepository;
import com.clinicmgmtsystem.repositories.TestRepository;

@Controller
@RequestMapping("/pathologist")
public class PathalogistController {
	@Autowired
	private TestRepository testRepo;
	@Autowired
	private PathalogistRepository pathalogistRepo;
	@Autowired
	private AppointmentRepository appointmentRepo;

	@GetMapping("/home")
	public String homePathologist(Model model) {
		// Get the currently logged-in patient
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String username = authentication.getName();
				Pathalogist pathalogist=pathalogistRepo.findByUsername(username).get();
				model.addAttribute("pathalogist", pathalogist);
		return "pathologist/home";
	}
	@GetMapping("/tests")
	public String tests(Model model) {
        		List<Test> tests=testRepo.findAll();
        		 model.addAttribute("tests", tests);
        		 return "pathologist/tests-list";
	}
	@GetMapping("/new_test")
	public String newTest() {
		return "pathologist/add-test";
	}
	@PostMapping("/do_register_test")
	public String saveTest(@ModelAttribute("test")Test test,Model model) {
		testRepo.save(test);
		return "redirect:tests";
		
	}
	@GetMapping("/delete_test/{id}")
	public String deleteTest(@PathVariable("id")long id,Model model) {
		testRepo.deleteById(id);
		return "redirect:/pathologist/tests";
	}
	@GetMapping("/view_sales")
	public String viewSales(Model model) {
		List<Appointment> sales= appointmentRepo.findByTestPaymentIdNotNull();
		double totalSales =sales.stream().mapToDouble(sale -> sale.getTests().stream().mapToDouble(Test::getTestPrice).sum()).sum();
		// Add sales and total sales to the model
        model.addAttribute("sales", sales);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("hasSales", !sales.isEmpty()); // Add a flag
        return "pathologist/view-sales";
	}
}
