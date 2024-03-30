package com.example.edtunivavignon;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

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
        reservations.sort(Comparator.comparing(Reservation::getStart));;
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

    public ArrayList<ArrayList<Reservation>> findWeeklyReservations(LocalDateTime dayOfTheWeek) {
        LocalDateTime beginningOfWeek = dayOfTheWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).withHour(0);
        ArrayList<ArrayList<Reservation>> weeklyReservations = new ArrayList<>();
        weeklyReservations.add(findDailyReservations(beginningOfWeek));
        weeklyReservations.add(findDailyReservations(beginningOfWeek.plusDays(1)));
        weeklyReservations.add(findDailyReservations(beginningOfWeek.plusDays(2)));
        weeklyReservations.add(findDailyReservations(beginningOfWeek.plusDays(3)));
        weeklyReservations.add(findDailyReservations(beginningOfWeek.plusDays(4)));
        return weeklyReservations;
    }
    public ArrayList<Reservation> findDailyReservations(LocalDateTime dayOfTheWeek) {
        LocalDateTime beginningOfWeek = dayOfTheWeek.withHour(0);
        reservations.sort(Comparator.comparing(Reservation::getStart));
        ArrayList<Reservation> dailyReservations = new ArrayList<>();
        int dayStart = 0;
        while (dayStart < reservations.size() && reservations.get(dayStart).getStart().isBefore(beginningOfWeek)) {
            dayStart++;
        }
        if (dayStart == reservations.size())
            return  dailyReservations;
        int dayEnd = dayStart;
        while (dayEnd < reservations.size() && reservations.get(dayEnd).getStart().isBefore(beginningOfWeek.plusDays(1))) {
            dailyReservations.add(reservations.get(dayEnd));
            dayEnd++;
        }
        return dailyReservations;
    }

    public void removeDuplicates() {
        reservations = new ArrayList<>(new HashSet<> (reservations));
    }
}
