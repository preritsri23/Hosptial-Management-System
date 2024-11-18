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
import com.clinicmgmtsystem.entities.Medicine;
import com.clinicmgmtsystem.entities.Pharmacist;
import com.clinicmgmtsystem.repositories.AppointmentRepository;
import com.clinicmgmtsystem.repositories.MedicineRepository;
import com.clinicmgmtsystem.repositories.PharmacistRepository;

@Controller
@RequestMapping("/pharmacist")
public class PharmacistController {
	@Autowired
	private MedicineRepository medicineRepo;
	@Autowired
	private AppointmentRepository appointmentRepo;
	@Autowired
	private PharmacistRepository pharmacistRepo;
    @GetMapping("/home")
	public String home(Model model) {
    	// Get the currently logged-in patient
    			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    			String username = authentication.getName();
    			Pharmacist pharmacist=pharmacistRepo.findByUsername(username).get();
    			model.addAttribute("pharmacist", pharmacist);
		return "pharmacist/home";
	}
    @GetMapping("/medicines")
    public String medicines(Model model) {
    	List<Medicine> medicines =medicineRepo.findAll();
    	  model.addAttribute("medicines",medicines);
    	  	    return "pharmacist/medicines-list";
    }
    
    @GetMapping("/new_medicine")
    public String newMedicine() {
    	return "pharmacist/add-medicine";
    }
    @PostMapping("/do_register_medicine")
    public String registerMedicine(@ModelAttribute("medicine")Medicine medicine,Model model) {
    	medicineRepo.save(medicine);
    	return "redirect:medicines";
    }
    @GetMapping("/delete_medicine/{id}")
    public String deleteMedicine(@PathVariable("id")long id,Model model) {
    	medicineRepo.deleteById(id);
    	return "redirect:/pharmacist/medicines";
    }
    @GetMapping("/view_sales")
     public String viewSales(Model model) {
        // Fetch sales with non-null medPaymentId
        List<Appointment> sales = appointmentRepo.findByMedPaymentIdNotNull();

        // Calculate total sales
        double totalSales = sales.stream()
            .mapToDouble(sale -> sale.getMedicines().stream().mapToDouble(Medicine::getMedPrice).sum())
            .sum();

        // Add sales and total sales to the model
        model.addAttribute("sales", sales);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("hasSales", !sales.isEmpty()); // Add a flag
        return "pharmacist/view-sales";
    }
    
    
}
