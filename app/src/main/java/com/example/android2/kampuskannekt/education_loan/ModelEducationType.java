package com.example.android2.kampuskannekt.education_loan;

import java.io.Serializable;

public class ModelEducationType implements Serializable {

    String loan_type_id;
    String loan_name;
    String status;


    public String getLoan_type_id() {
        return loan_type_id;
    }

    public void setLoan_type_id(String loan_type_id) {
        this.loan_type_id = loan_type_id;
    }

    public String getLoan_name() {
        return loan_name;
    }

    public void setLoan_name(String loan_name) {
        this.loan_name = loan_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
