package com.hospital.hospital.model;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private Room room;
    private Timestamp startTime;
    private Timestamp endTime;
    private Doctor doctor;
    private Nurse nurse;
    private Patient patient;

    public Appointment() {

    }

    public Appointment(int appointmentId, Room room, Timestamp startTime,
                       Timestamp endTime, Doctor doctor, Nurse nurse, Patient patient) {
        this.appointmentId = appointmentId;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.nurse = nurse;
        this.patient = patient;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", room=" + room.getNumber() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", doctor=" + doctor.getName() +
                ", nurse=" + nurse.getName() +
                ", patient=" + patient.getName() +
                '}';
    }
}
