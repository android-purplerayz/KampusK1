package com.example.android2.kampuskannekt.education_loan;

import java.io.Serializable;

public class ModelEducationData implements Serializable {

    String edu_loan_id;
    String bank_id;
    String loan_type_id;
    String loan_title;
    String loan_des;
    String loan_amt;
    String interest_amt;
    String process;
    String url;
    String bank_img;
    String bank_name;
    String loan_name;

    public String getEdu_loan_id() {
        return edu_loan_id;
    }

    public void setEdu_loan_id(String edu_loan_id) {
        this.edu_loan_id = edu_loan_id;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getLoan_type_id() {
        return loan_type_id;
    }

    public void setLoan_type_id(String loan_type_id) {
        this.loan_type_id = loan_type_id;
    }

    public String getLoan_title() {
        return loan_title;
    }

    public void setLoan_title(String loan_title) {
        this.loan_title = loan_title;
    }

    public String getLoan_des() {
        return loan_des;
    }

    public void setLoan_des(String loan_des) {
        this.loan_des = loan_des;
    }

    public String getLoan_amt() {
        return loan_amt;
    }

    public void setLoan_amt(String loan_amt) {
        this.loan_amt = loan_amt;
    }

    public String getInterest_amt() {
        return interest_amt;
    }

    public void setInterest_amt(String interest_amt) {
        this.interest_amt = interest_amt;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBank_img() {
        return bank_img;
    }

    public void setBank_img(String bank_img) {
        this.bank_img = bank_img;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getLoan_name() {
        return loan_name;
    }

    public void setLoan_name(String loan_name) {
        this.loan_name = loan_name;
    }
}
