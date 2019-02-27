package com.aiprous.deliveryboy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllOrderModel {

    @Expose
    @SerializedName("data")
    private ArrayList<Data> data;

    public ArrayList<Data> getResponse() {
        return data;
    }

    public void setResponse(ArrayList<Data> response) {
        this.data = response;
    }

    public static class Data {
        @Expose
        @SerializedName("entity_id")
        private String entity_id;
        @Expose
        @SerializedName("status")
        private String status;

        @Expose
        @SerializedName("created_at")
        private String created_at;

        @Expose
        @SerializedName("address")
        private String address;

        public String getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(String entity_id) {
            this.entity_id = entity_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Data(String entity_id, String status, String created_at, String address) {
            this.entity_id = entity_id;
            this.status = status;
            this.created_at = created_at;
            this.address = address;
        }
    }
}
