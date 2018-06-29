package ebook;

import java.util.ArrayList;

public class ModelEbook {

    String status;
    String msg;
    String image_url;
    ArrayList<ModelData> arr_ebooks;
    ArrayList<ModelCategory> arr_category;
    ArrayList<ModelSubject> arr_subject;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ArrayList<ModelData> getArr_ebooks() {
        return arr_ebooks;
    }

    public void setArr_ebooks(ArrayList<ModelData> arr_ebooks) {
        this.arr_ebooks = arr_ebooks;
    }

    public ArrayList<ModelCategory> getArr_category() {
        return arr_category;
    }

    public void setArr_category(ArrayList<ModelCategory> arr_category) {
        this.arr_category = arr_category;
    }

    public ArrayList<ModelSubject> getArr_subject() {
        return arr_subject;
    }

    public void setArr_subject(ArrayList<ModelSubject> arr_subject) {
        this.arr_subject = arr_subject;
    }
}
