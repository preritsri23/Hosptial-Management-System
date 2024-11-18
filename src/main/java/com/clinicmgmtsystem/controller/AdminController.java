package com.clinicmgmtsystem.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinicmgmtsystem.entities.Admin;
import com.clinicmgmtsystem.entities.Appointment;
import com.clinicmgmtsystem.entities.Doctor;
import com.clinicmgmtsystem.entities.Medicine;
import com.clinicmgmtsystem.entities.Pathalogist;
import com.clinicmgmtsystem.entities.Patient;
import com.clinicmgmtsystem.entities.Pharmacist;
import com.clinicmgmtsystem.entities.Test;
import com.clinicmgmtsystem.repositories.AdminRepository;
import com.clinicmgmtsystem.repositories.AppointmentRepository;
import com.clinicmgmtsystem.services.DoctorService;
import com.clinicmgmtsystem.services.PathalogistService;
import com.clinicmgmtsystem.services.PatientService;
import com.clinicmgmtsystem.services.PharmacistService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private PatientService patientService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PathalogistService pathalogistService;
	@Autowired
	private PharmacistService pharmacistService;
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	
	
	@RequestMapping("/home")
	public String home(Model model) {
		// Get the currently logged-in patient
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Admin admin=adminRepo.findByUsername(username).get();
		model.addAttribute("admin", admin);
		return "admin/home";
		
	}
	
	@GetMapping("/patients")
	public String patientsList(Model model) {
		List<Patient> patients=patientService.findAllPatients();
		model.addAttribute("patients", patients);
		return "admin/patients-list";
	}
	
	@GetMapping("/doctors")
	public String doctorsList(Model model) {
		List<Doctor> doctors=doctorService.findAllDoctors();
		model.addAttribute("doctors",doctors);
		return "admin/doctors-list";
		
	}
	@GetMapping("/new_doctor")
	public String newDoctor() {
		return "admin/add-doctor";
	}
	@PostMapping("/do_register_doctor")
	public String registerDoctor(@ModelAttribute("doctor")Doctor doctor,Model model) {
		doctor.setRole("DOCTOR");
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		doctorService.saveDoctor(doctor);
		return "redirect:doctors";
	}
	@GetMapping("/delete_doctor/{id}")
	public String deleteDoctor(@PathVariable("id")long id,Model model) {
		doctorService.deleteDoctor(id);
		return "redirect:/admin/doctors";
	}
	
	@GetMapping("/pathologists")
	public String pathalogistsList(Model model) {
		List<Pathalogist> pathalogists =pathalogistService.findAllPathologists();
		model.addAttribute("pathalogists", pathalogists);
		return "admin/pathalogists-list";
	}
	@GetMapping("/new_pathalogist")
	public String newPathalogist() {
		return "admin/add-pathalogist";
	}
	@PostMapping("/do_register_pathalogist")
	public String registerPathalogist(@ModelAttribute("pathalogist")Pathalogist pathalogist,Model model) {
		pathalogist.setRole("PATHOLOGIST");
		pathalogist.setPassword(passwordEncoder.encode(pathalogist.getPassword()));
		pathalogistService.savePathologist(pathalogist);
		return "redirect:pathologists";
	}
	@GetMapping("/delete_pathalogist/{id}")
	public String deletePathalogist(@PathVariable("id")long id,Model model) {
		pathalogistService.deletePathologist(id);
		return "redirect:/admin/pathologists";
	}
	
	@GetMapping("/pharmacists")
	public String pharmacistsList(Model model) {
		List<Pharmacist> pharmacists=pharmacistService.findAllPharmacists();
		model.addAttribute("pharmacists", pharmacists);
		return "admin/pharmacists-list";
	}
    @GetMapping("/new_pharmacist")
    public String newPharmacist() {
    	return "admin/add-pharmacist";
    }
    @PostMapping("/do_register_pharmacist")
    public String registerPharmacist(@ModelAttribute("pharmacist")Pharmacist pharmacist,Model model) {
    	pharmacist.setRole("PHARMACIST");
    	pharmacist.setPassword(passwordEncoder.encode(pharmacist.getPassword()));
    	pharmacistService.savePharmacist(pharmacist);
    	return "redirect:pharmacists";
    }
    @GetMapping("/delete_pharmacist/{id}")
    public String deletePharmacist(@PathVariable("id")long id,Model model) {
    	pharmacistService.deletePharmacist(id);
    	return "redirect:/admin/pharmacists";
    }
    @GetMapping("/view_revenue")
    public String viewRevenue(Model model) {
    	List<Appointment> appointments=appointmentRepo.findByPaymentIdNotNull();
    	double appRevenue=500.0*appointments.size();
    	 List<Appointment> medSales = appointmentRepo.findByMedPaymentIdNotNull();

         // Calculate total sales
         double totalMedSales = medSales.stream()
             .mapToDouble(sale -> sale.getMedicines().stream().mapToDouble(Medicine::getMedPrice).sum())
             .sum();
         List<Appointment> testSales = appointmentRepo.findByTestPaymentIdNotNull();

         // Calculate total sales
         double totalTestSales = testSales.stream()
             .mapToDouble(sale -> sale.getTests().stream().mapToDouble(Test::getTestPrice).sum())
             .sum();
         
         double totalSales=appRevenue+totalMedSales+totalTestSales;
         model.addAttribute("testSales", totalTestSales);
         model.addAttribute("appRevenue", appRevenue);
         model.addAttribute("medSales", totalMedSales);
         model.addAttribute("totalSales", totalSales);
         return "admin/view-revenue";
         
    	
    }
    
}
