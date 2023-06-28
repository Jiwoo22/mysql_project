package com.hospital.patterns.builder;

public class Nurse {

    private int nurseId;
    private String name;
    private String email;
    private int employeeId;

    public Nurse(NurseBuilder builder) {
        this.nurseId = builder.nurseId;
        this.name = builder.name;
        this.email = builder.email;
        this.employeeId = builder.employeeId;
    }

    public int getNurseId() {
        return nurseId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getEmployeeId() {
        return employeeId;
    }


    public static class NurseBuilder {
        private int nurseId;
        private String name;
        private String email;
        private int employeeId;

        public NurseBuilder setNurseId(int nurseId) {
            this.nurseId = nurseId;
            return this;
        }

        public NurseBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public NurseBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public NurseBuilder setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Nurse build() {
            return new Nurse(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Nurse[nurseId=%d, name=%s, email=%s, employeeId=%d]",
                nurseId, name, email, employeeId);
    }
}
