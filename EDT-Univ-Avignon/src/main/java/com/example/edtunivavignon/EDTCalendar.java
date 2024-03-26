package com.example.edtunivavignon;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class EDTCalendar {
    private LocalDateTime calendarStart;
    private LocalDateTime calendarEnd;
    private String calName;

    private String calDesc;

    public void setCalDesc(String calDesc) {
        this.calDesc = calDesc;
    }

    ArrayList<Reservation> reservations;

    @Override
    public String toString() {
        return "EDTCalendar{" +
                "calendarStart=" + calendarStart +
                ", calendarEnd=" + calendarEnd +
                ", calName='" + calName + '\'' +
                ", calDesc='" + calDesc + '\'' +
                ", reservations=" + reservations +
                '}';
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public EDTCalendar() {
        reservations = new ArrayList<>();
    }

    public void setCalendarStart(LocalDateTime calendarStart) {
        this.calendarStart = calendarStart;
    }

    public void setCalendarEnd(LocalDateTime calendarEnd) {
        this.calendarEnd = calendarEnd;
    }

    public void setCalName(String calName) {
        this.calName = calName;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}
