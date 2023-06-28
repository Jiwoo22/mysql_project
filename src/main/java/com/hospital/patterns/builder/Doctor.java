package com.hospital.patterns.builder;

public class Doctor {

    private int doctorId;
    private String name;
    private String specialization;
    private String email;
    private int employeeId;

    public Doctor(DoctorBuilder builder) {
        this.doctorId = builder.doctorId;
        this.name = builder.name;
        this.specialization = builder.specialization;
        this.email = builder.email;
        this.employeeId = builder.employeeId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getEmail() {
        return email;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public static class DoctorBuilder {
        private int doctorId;
        private String name;
        private String specialization;
        private String email;
        private int employeeId;

        public DoctorBuilder setDoctorId(int doctorId) {
            this.doctorId = doctorId;
            return this;
        }

        public DoctorBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public DoctorBuilder setSpecialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public DoctorBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public DoctorBuilder setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Doctor build() {
            return new Doctor(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Doctor[doctorId=%d, name=%s, specialization=%s, email=%s, employeeId=%d]"
                , doctorId, name, specialization, email, employeeId);
    }
}
