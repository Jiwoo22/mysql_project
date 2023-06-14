package com.hospital.hospital.model;

public class Insurance {
    private int insuranceId;
    private String type;
    private String expirationDate;

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "insuranceId=" + insuranceId +
                ", type='" + type + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
