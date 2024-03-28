package com.example.edtunivavignon;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reservation {
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime lastModified;
    private String uid;
    private ArrayList<String> rooms;
    private ArrayList<String> teachers;
    private String nameOfReservation;
    private String memo;
    private ArrayList<String> attendingGroups;
    private ArrayList<String> attendingPromotions;
    private String type;
    private Boolean isHoliday;

    @Override
    public String toString() {
        return "Reservation{" +
                "start=" + start +
                ", end=" + end +
                //", lastModified=" + lastModified +
                //", uid='" + uid + '\'' +
                //", rooms=" + rooms +
                ", teachers=" + teachers +
                ", nameOfReservation='" + nameOfReservation + '\'' +
                //", memo='" + memo + '\'' +
                //", attendingGroups=" + attendingGroups +
                //", type='" + type + '\'' +
                //", isHoliday=" + isHoliday +
                '}';
    }

    public ArrayList<String> getAttendingPromotions() {
        return attendingPromotions;
    }

    public void setAttendingPromotions(ArrayList<String> attendingPromotions) {
        this.attendingPromotions = attendingPromotions;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHoliday(Boolean holiday) {
        isHoliday = holiday;
    }

    public Reservation() {
        isHoliday = false;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;
    }

    public void setTeachers(ArrayList<String> teachers) {
        this.teachers = teachers;
    }

    public void setNameOfReservation(String nameOfReservation) {
        this.nameOfReservation = nameOfReservation;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setAttendingGroups(ArrayList<String> attendingGroups) {
        this.attendingGroups = attendingGroups;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public String getUid() {
        return uid;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public ArrayList<String> getTeachers() {
        return teachers;
    }

    public String getNameOfReservation() {
        return nameOfReservation;
    }

    public String getMemo() {
        return memo;
    }

    public ArrayList<String> getAttendingGroups() {
        return attendingGroups;
    }

    public String getType() {
        return type;
    }

    public Boolean getHoliday() {
        return isHoliday;
    }
}
