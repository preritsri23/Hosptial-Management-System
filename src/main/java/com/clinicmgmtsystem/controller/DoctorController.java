package com.clinicmgmtsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clinicmgmtsystem.entities.Appointment;
import com.clinicmgmtsystem.entities.Doctor;
import com.clinicmgmtsystem.entities.Medicine;
import com.clinicmgmtsystem.entities.Test;
import com.clinicmgmtsystem.repositories.AppointmentRepository;
import com.clinicmgmtsystem.repositories.MedicineRepository;
import com.clinicmgmtsystem.repositories.TestRepository;
import com.clinicmgmtsystem.services.DoctorService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private AppointmentRepository appointmentRepo;
	@Autowired 
	private DoctorService doctorService;
	@Autowired
	private MedicineRepository medicineRepo;
	@Autowired
	private TestRepository testRepo;
	
	
	@GetMapping("/home")
	public String Home(Model model) {
		// Get the currently logged-in patient
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String username = authentication.getName();
				Doctor doctor=doctorService.findDoctorByUsername(username).get();
				model.addAttribute("doctor", doctor);
		return "doctor/home";
	}
	@GetMapping("/appointment_dashboard")
	public String appointmentDashboard(Model model) {
		 Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		 String username=authentication.getName();
		 Doctor doctor=doctorService.findDoctorByUsername(username).get();
		 List<Appointment> appointments=appointmentRepo.findByDoctorId(doctor.getId());
		 model.addAttribute("appointments", appointments);
		return "doctor/appointment-dashboard";
	}
	
	@GetMapping("/write_prescription/{id}")
	public String writePrescription(@PathVariable("id")long id,Model model) {
		Appointment appointment=appointmentRepo.findById(id).get();
		List<Medicine> medicines=medicineRepo.findAll();
		List<Test> tests=testRepo.findAll();
		model.addAttribute("appointment", appointment);
		model.addAttribute("medicines", medicines);
		model.addAttribute("tests", tests);
		return "doctor/write-prescription";
		
	}
	
	@PostMapping("/save_prescription")
	public String savePrescription(@RequestParam("appointmentId") Long appointmentId,
            @RequestParam("prescription") String prescription,
            @RequestParam(value = "medicineIds", required = false) List<Long> medicineIds,
            @RequestParam(value = "testIds", required = false) List<Long> testIds) {
   Appointment appointment=appointmentRepo.findById(appointmentId).get();
   appointment.setPrescription(prescription);
   if (medicineIds != null && !medicineIds.isEmpty()) {
       List<Medicine> medicines = medicineRepo.findAllById(medicineIds);
       appointment.setMedicines(medicines);
   }

   if (testIds != null && !testIds.isEmpty()) {
       List<Test> tests = testRepo.findAllById(testIds);
       appointment.setTests(tests);
   }
   appointmentRepo.save(appointment);
   return "redirect:/doctor/appointment_dashboard";

}
	@GetMapping("/view_prescribed_patients")
	public String viewPrescribedPatients(Model model) {
		 Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		 String username=authentication.getName();
		 Doctor doctor=doctorService.findDoctorByUsername(username).get();
		 List<Appointment> appointments=appointmentRepo.findByDoctorId(doctor.getId());
		model.addAttribute("appointments", appointments);
		return "doctor/prescribed-patients";
	}
	
	

}
