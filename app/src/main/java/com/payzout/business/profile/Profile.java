package com.payzout.business.profile;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Profile {
    @ServerTimestamp
    private Date timestamp;
    private String uid;
    private String name;
    private String photo;
    private String email;
    private String phone;
    private String pancard;
    private String aadhaar;
    private String dob;
    private String gender;
    private String address;

    public Profile() {
    }

    public Profile(Date timestamp, String uid, String name, String photo, String email, String phone, String pancard, String aadhaar, String dob, String gender, String address) {
        this.timestamp = timestamp;
        this.uid = uid;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.pancard = pancard;
        this.aadhaar = aadhaar;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPancard() {
        return pancard;
    }

    public void setPancard(String pancard) {
        this.pancard = pancard;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
}
