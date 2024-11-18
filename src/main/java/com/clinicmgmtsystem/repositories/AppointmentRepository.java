package com.clinicmgmtsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicmgmtsystem.entities.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByPatientId(Long patientId);
	List<Appointment> findByDoctorId(Long doctorId);
	List<Appointment> findByMedPaymentIdNotNull();
	List<Appointment> findByTestPaymentIdNotNull();
	List<Appointment> findByPaymentIdNotNull();
}
