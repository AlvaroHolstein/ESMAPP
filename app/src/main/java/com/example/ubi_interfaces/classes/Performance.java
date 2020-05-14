package com.example.ubi_interfaces.classes;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;


public class Performance {

    // Por o set e get mais simplificados
    private Timestamp date;
    private Boolean reqPass;
    private int totalParticipants;
    private String location;
    private String password;
    private int adminId;
    private int duration;
    private List<Integer> participantsId;
    private int id;
    private String picture;

    public void Performance (Timestamp hour, Boolean reqPass, int totalParticipants, String location, String picture) {
        this.date = hour;
        this.reqPass = reqPass;
        this.totalParticipants = totalParticipants;
        this.location = location;
        this.picture = picture;
    }


    public void Performance () {

    }

    // Setters
    public void setDate(Timestamp hour) {
        this.date = hour;
    }

    public void setReqPass(Boolean reqPass) {
        this.reqPass = reqPass;
    }

//    public void setTotalParticipants(int totalParticipants) {
//        this.totalParticipants = totalParticipants;
//    }

    public void setLocalization(String localization) {
        this.location = localization;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setParticipants(int[] participants) {
        this.participantsId = participantsId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPicture(String picture) { this.picture = picture; }


    // Getters
    public String getLocation() {
        return location;
    }

    public String getPassword() {
        return password;
    }

    public int getAdminId() {
        return adminId;
    }

    public int getDuration() {
        return duration;
    }

    public List<Integer> getParticipantsId() {
        return participantsId;
    }

    public int getId() {
        return id;
    }


    public Date getDate() { return date.toDate(); }

    public Boolean getReqPass() {
        if(this.password != "") reqPass = false;
        else reqPass = true;

        return reqPass;
    }

    public int getTotalParticipants() {
        return this.participantsId.size();
    }

    public String getLocalization() {
        return location;
    }

    public String getPicture() { return picture; }

}
