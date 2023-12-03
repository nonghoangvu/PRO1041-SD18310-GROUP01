/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.model;

import java.util.Date;

/**
 *
 * @author dangc
 */
public class KhachHang {
    private int id;
    private String fullname;
    private String phone;
    private String email;
    private int birth_year;
    private String gender;
    private String address;
    private int custome_type_id;
    private String note;
    private Date created_at;

    public KhachHang() {
    }

    public KhachHang(int id, String fullname, String phone, String email, int birth_year, String gender, String address, int custome_type_id, String note, Date created_at) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.birth_year = birth_year;
        this.gender = gender;
        this.address = address;
        this.custome_type_id = custome_type_id;
        this.note = note;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustome_type_id() {
        return custome_type_id;
    }

    public void setCustome_type_id(int custome_type_id) {
        this.custome_type_id = custome_type_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    
}
