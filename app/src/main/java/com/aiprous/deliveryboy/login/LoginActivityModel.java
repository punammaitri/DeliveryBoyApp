package com.aiprous.deliveryboy.login;

import com.aiprous.deliveryboy.apimodel.BaseServiceResponseModel;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by aai on 04/07/2017.
 */

public class LoginActivityModel extends BaseServiceResponseModel {

    @SerializedName("data")
    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("vehicle_type")
        private String vehicle_type;
        @SerializedName("city")
        private int city;
        @SerializedName("isActivate")
        private int isActivate;
        @SerializedName("verify")
        private int verify;
        @SerializedName("mobile_verify")
        private int mobile_verify;
        @SerializedName("status")
        private int status;
        @SerializedName("location")
        private String location;
        @SerializedName("DOC")
        private String DOC;
        @SerializedName("image")
        private String image;
        @SerializedName("vehicle")
        private String vehicle;
        @SerializedName("phone")
        private String phone;
        @SerializedName("password")
        private String password;
        @SerializedName("UID")
        private String UID;
        @SerializedName("last_name")
        private String last_name;
        @SerializedName("first_name")
        private String first_name;
        @SerializedName("id")
        private int id;

        public String getVehicle_type() {
            return vehicle_type;
        }

        public void setVehicle_type(String vehicle_type) {
            this.vehicle_type = vehicle_type;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getIsActivate() {
            return isActivate;
        }

        public void setIsActivate(int isActivate) {
            this.isActivate = isActivate;
        }

        public int getVerify() {
            return verify;
        }

        public void setVerify(int verify) {
            this.verify = verify;
        }

        public int getMobile_verify() {
            return mobile_verify;
        }

        public void setMobile_verify(int mobile_verify) {
            this.mobile_verify = mobile_verify;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDOC() {
            return DOC;
        }

        public void setDOC(String DOC) {
            this.DOC = DOC;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVehicle() {
            return vehicle;
        }

        public void setVehicle(String vehicle) {
            this.vehicle = vehicle;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUID() {
            return UID;
        }

        public void setUID(String UID) {
            this.UID = UID;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
