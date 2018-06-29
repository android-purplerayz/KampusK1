package ngo;

import java.io.Serializable;

public class ModelNgoCategory implements Serializable {

    String ngo_cat_id;
    String ngo_cat_name;
    String ngo_cat_des;
    String ngo_cat_img;

    public String getNgo_cat_id() {
        return ngo_cat_id;
    }

    public void setNgo_cat_id(String ngo_cat_id) {
        this.ngo_cat_id = ngo_cat_id;
    }

    public String getNgo_cat_name() {
        return ngo_cat_name;
    }

    public void setNgo_cat_name(String ngo_cat_name) {
        this.ngo_cat_name = ngo_cat_name;
    }

    public String getNgo_cat_des() {
        return ngo_cat_des;
    }

    public void setNgo_cat_des(String ngo_cat_des) {
        this.ngo_cat_des = ngo_cat_des;
    }

    public String getNgo_cat_img() {
        return ngo_cat_img;
    }

    public void setNgo_cat_img(String ngo_cat_img) {
        this.ngo_cat_img = ngo_cat_img;
    }
}
