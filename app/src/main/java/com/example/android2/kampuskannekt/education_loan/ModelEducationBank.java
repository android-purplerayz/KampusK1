package com.example.android2.kampuskannekt.education_loan;

import java.io.Serializable;

public class ModelEducationBank implements Serializable {

    String bank_id;
    String bank_name;
    String status;

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
