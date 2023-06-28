package com.hospital.patterns.builder;

public class Patient {

    private int id;
    private String name;
    private String dateOfBirth;
    private String phone;
    private int insuranceId;
    private int doctorId;

    public Patient(PatientBuilder builder) {
        this.name = builder.name;
        this.dateOfBirth = builder.dateOfBirth;
        this.phone = builder.phone;
        this.insuranceId = builder.insuranceId;
        this.doctorId = builder.doctorId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public int getDoctorId() {
        return doctorId;
    }


    public static class PatientBuilder {
        private String name;
        private String dateOfBirth;
        private String phone;
        private int insuranceId;
        private int doctorId;

        public PatientBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PatientBuilder setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public PatientBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public PatientBuilder setInsuranceId(int insuranceId) {
            this.insuranceId = insuranceId;
            return this;
        }

        public PatientBuilder setDoctorId(int doctorId) {
            this.doctorId = doctorId;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }


    @Override
    public String toString() {
        return String.format("Patient[id=%d, name=%s, DOB=%s, phone=%s, insuranceId=%d, doctorId=%d]"
                , id, name, dateOfBirth, phone, insuranceId, doctorId);
    }
}
