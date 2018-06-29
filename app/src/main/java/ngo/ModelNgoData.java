package ngo;

import java.io.Serializable;

public class ModelNgoData implements Serializable {
    String ngo_id;
    String state_id;
    String ngo_title;
    String ngo_des;
    String ngo_cat_id;
    String ngo_address;
    String ngo_address2;
    String ngo_img;
    String ngo_phone;
    String ngo_email_address;
    String ngo_rating;
    String ngo_cat_name;
    String state_name;

    public String getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(String ngo_id) {
        this.ngo_id = ngo_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getNgo_title() {
        return ngo_title;
    }

    public void setNgo_title(String ngo_title) {
        this.ngo_title = ngo_title;
    }

    public String getNgo_des() {
        return ngo_des;
    }

    public void setNgo_des(String ngo_des) {
        this.ngo_des = ngo_des;
    }

    public String getNgo_cat_id() {
        return ngo_cat_id;
    }

    public void setNgo_cat_id(String ngo_cat_id) {
        this.ngo_cat_id = ngo_cat_id;
    }

    public String getNgo_address() {
        return ngo_address;
    }

    public void setNgo_address(String ngo_address) {
        this.ngo_address = ngo_address;
    }

    public String getNgo_address2() {
        return ngo_address2;
    }

    public void setNgo_address2(String ngo_address2) {
        this.ngo_address2 = ngo_address2;
    }

    public String getNgo_img() {
        return ngo_img;
    }

    public void setNgo_img(String ngo_img) {
        this.ngo_img = ngo_img;
    }

    public String getNgo_phone() {
        return ngo_phone;
    }

    public void setNgo_phone(String ngo_phone) {
        this.ngo_phone = ngo_phone;
    }

    public String getNgo_email_address() {
        return ngo_email_address;
    }

    public void setNgo_email_address(String ngo_email_address) {
        this.ngo_email_address = ngo_email_address;
    }

    public String getNgo_rating() {
        return ngo_rating;
    }

    public void setNgo_rating(String ngo_rating) {
        this.ngo_rating = ngo_rating;
    }

    public String getNgo_cat_name() {
        return ngo_cat_name;
    }

    public void setNgo_cat_name(String ngo_cat_name) {
        this.ngo_cat_name = ngo_cat_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
}
