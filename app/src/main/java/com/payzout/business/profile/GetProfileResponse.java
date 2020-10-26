package com.payzout.business.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("aadhaar")
    @Expose
    private String aadhaar;
    @SerializedName("pancard")
    @Expose
    private String pancard;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    /**
     * No args constructor for use in serialization
     */
    public GetProfileResponse() {
    }

    /**
     * @param pancard
     * @param address
     * @param createdDate
     * @param gender
     * @param phone
     * @param city
     * @param dob
     * @param fullName
     * @param aadhaar
     * @param id
     * @param email
     */
    public GetProfileResponse(String id, String fullName, String phone, String email, String aadhaar, String pancard, String dob, Integer gender, String address, String city, String createdDate) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.aadhaar = aadhaar;
        this.pancard = pancard;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getPancard() {
        return pancard;
    }

    public void setPancard(String pancard) {
        this.pancard = pancard;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}