package com.clinicmgmtsystem.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clinicmgmtsystem.entities.Appointment;
import com.clinicmgmtsystem.entities.Doctor;
import com.clinicmgmtsystem.entities.Medicine;
import com.clinicmgmtsystem.entities.Patient;
import com.clinicmgmtsystem.entities.Test;
import com.clinicmgmtsystem.repositories.AppointmentRepository;
import com.clinicmgmtsystem.services.DoctorService;
import com.clinicmgmtsystem.services.PatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private AppointmentRepository appointmentRepo;
	@Autowired
	private PatientService patientService;
	
	@GetMapping("/home")
	public String home(Model model) {
		// Get the currently logged-in patient
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Patient patient = patientService.findByUsername(username).get();
		model.addAttribute("patient", patient);
		return "patient/home";
	}
	//Appointment Portal Starts here
	@GetMapping("/appointment_portal")
	public String appointmentPortal(Model model) {
		List<Doctor> doctors=doctorService.findAllDoctors();
		model.addAttribute("doctors", doctors);
		return "patient/appointment-portal";
	}
	@GetMapping("/new_appointment/{id}")
	public String newAppointment(@PathVariable("id")long id,Model model) {
		Optional<Doctor> doctor=doctorService.findDoctorById(id);
		model.addAttribute("doctor", doctor.get());
		return "patient/add-appointment";
	}
	
	
	@PostMapping("/do_register_appointment")
	public String bookAppointment(@RequestParam Long doctorId,
            @RequestParam String appointmentDate) {
// Get the currently logged-in patient
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
String username = authentication.getName();
Patient patient = patientService.findByUsername(username).get();

// Fetch the selected doctor
Optional<Doctor>  doctor= doctorService.findDoctorById(doctorId);
//Convert appointmentDateTime to LocalDateTime
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
LocalDateTime parsedDateTime = LocalDateTime.parse(appointmentDate, formatter);
// Create and save the appointment
Appointment appointment = new Appointment();
appointment.setStatus("Pending");
appointment.setDoctor(doctor.get());
appointment.setPatient(patient);
appointment.setAppointmentDate(parsedDateTime);
appointmentRepo.save(appointment);

return "redirect:appointment_portal"; // Redirect to appointments page
}
	@GetMapping("/booked_appointments")
	public String showBookedAppointments(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	    // Retrieve patient info using logged-in user
	    String username = userDetails.getUsername();
	    Patient patient = patientService.findByUsername(username).get();

	    // Fetch booked appointments for the patient
	    List<Appointment> appointments = appointmentRepo.findByPatientId(patient.getId());
	    model.addAttribute("appointments", appointments);

	    return "patient/appointments";
	}
	@GetMapping("/delete_appointment/{id}")
	public String deleteAppointment(@PathVariable("id")long id,Model model) {
		appointmentRepo.deleteById(id);
		return "redirect:/patient/booked_appointments";
	}
	
	@GetMapping("/pay_now/{id}")
	public String payNow(@PathVariable("id")long id,Model model) {
		Appointment appointment=appointmentRepo.findById(id).get();
		model.addAttribute("appointment", appointment);
		return "patient/pay-now";
		
	}
	@PostMapping("/submit_payment")
	public String submitPayment(@RequestParam long appointmentId,@RequestParam String paymentId) {
		Appointment appointment=appointmentRepo.findById(appointmentId).get();
		appointment.setPaymentId(paymentId);
		appointment.setStatus("Confirmed");
		appointmentRepo.save(appointment);
		return "redirect:/patient/booked_appointments";
	}
     @GetMapping("/view_bill/{id}")
     public String viewBill(@PathVariable("id")long id,Model model) {
    	 Appointment appointment =appointmentRepo.findById(id).get();
    	 model.addAttribute("appointment", appointment);
    	 return "patient/view-bill";
    	 
     }
     
     @GetMapping("/view_prescription/{id}")
     public String viewPrescription(@PathVariable("id")long id,Model model) {
    	 Appointment appointment=appointmentRepo.findById(id).get();
    	 if (appointment != null && appointment.getPrescription() != null) {
             model.addAttribute("appointment", appointment);
         }
    	 return "patient/view-prescription";
     }
     
     //Medicine Portal Starts Here
     
     @GetMapping("/medicine_portal")
     public String medicinePortal(Model model) {
    	 Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
    	 String username=authentication.getName();
    	 Patient patient=patientService.findByUsername(username).get();
    	 List<Appointment> appointments =appointmentRepo.findByPatientId(patient.getId());
    	 model.addAttribute("appointments",appointments);
    	 return "patient/medicine-portal";
    	 
     }
     @GetMapping("/medicine_view_more/{id}")
     public String medViewMore(@PathVariable("id")long id,Model model) {
    	 Appointment appointment=appointmentRepo.findById(id).get();
    	// Calculate total medicine price
    	    double totalPrice = appointment.getMedicines().stream()
    	            .mapToDouble(Medicine::getMedPrice)
    	            .sum();
    	    model.addAttribute("totalPrice", totalPrice);
    	 model.addAttribute("appointment", appointment);
    	 
    		 return "patient/med-pay";
    	  
    			 
     }
     
     @PostMapping("/submit_medPayment")
     public String medPayment(@RequestParam long appointmentId, @RequestParam String medPaymentId) {
    	 Appointment appointment =appointmentRepo.findById(appointmentId).get();
    	 appointment.setMedPaymentId(medPaymentId);
    	 appointmentRepo.save(appointment);
    	 return "redirect:/patient/medicine_portal";
     }
     @GetMapping("/med_reciept/{id}")
     public String medReciept(@PathVariable("id")long id,Model model) {
    	 Appointment appointment =appointmentRepo.findById(id).get();
    		// Calculate total medicine price
 	    double totalPrice = appointment.getMedicines().stream()
 	            .mapToDouble(Medicine::getMedPrice)
 	            .sum();
 	    model.addAttribute("appointment", appointment);
 	    model.addAttribute("totalPrice",totalPrice);
 	    return "patient/med-reciept";
     }
     
     
     // Test Portal Starts Here
     @GetMapping("/test_portal")
     public String testPortal(Model model) {
    	 Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
    	 String username=authentication.getName();
    	 Patient patient=patientService.findByUsername(username).get();
    	 List<Appointment> appointments =appointmentRepo.findByPatientId(patient.getId());
    	 model.addAttribute("appointments", appointments);
    	 return "patient/test-portal";
     }
     @GetMapping("/test_view_more/{id}")
     public String testViewMore(@PathVariable("id")long id,Model model) {
    	 Appointment appointment=appointmentRepo.findById(id).get();
    	 //Calculate Total test price
    	 double totalPrice=appointment.getTests().stream().mapToDouble(Test::getTestPrice).sum();
    	 model.addAttribute("appointment", appointment);
    	 model.addAttribute("totalPrice", totalPrice);
    	 return "patient/test-pay";
    	 
     }
     @PostMapping("/submit_testPayment")
     public String testPayment(@RequestParam long appointmentId,@RequestParam String reportMail,@RequestParam String testPaymentId) {
    	 Appointment appointment=appointmentRepo.findById(appointmentId).get();
    	 appointment.setReportMail(reportMail);
    	 appointment.setTestPaymentId(testPaymentId);
    	 appointmentRepo.save(appointment);
    	 return "redirect:/patient/test_portal";
     }
     
     @GetMapping("/test_reciept/{id}")
     public String testReciept(@PathVariable("id")long id,Model model) {
    	 Appointment appointment =appointmentRepo.findById(id).get();
    	 double totalPrice=appointment.getTests().stream().mapToDouble(Test::getTestPrice).sum();
    	 model.addAttribute("appointment", appointment);
    	 model.addAttribute("totalPrice", totalPrice);
    	 return "patient/test-reciept";
    	 
     }
     
     
}
