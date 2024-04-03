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

    private String customCalendar;

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

    public void setCustomCalendar(String customCalendar) {
        this.customCalendar = customCalendar;
        UserDB userDB = new UserDB();
        reservations.addAll(userDB.getReservations(customCalendar));
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
    public ArrayList<ArrayList<ArrayList<Reservation>>> findMonthlyReservations(LocalDateTime dayOfTheMonth) {
        ArrayList<ArrayList<ArrayList<Reservation>>> monthlyReservations = new ArrayList<>();
        dayOfTheMonth = dayOfTheMonth.withDayOfMonth(1);
        for (int i = 0; i < 5; i++) {
            monthlyReservations.add(findWeeklyReservations(dayOfTheMonth));
            dayOfTheMonth = dayOfTheMonth.plusWeeks(1);
        }
        return monthlyReservations;
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
    public Boolean checkIfTaken(LocalDateTime start, LocalDateTime end) {
        reservations.sort(Comparator.comparing(Reservation::getStart));
        for (int i = 0; i < reservations.size(); i++) {
            if (start.isBefore(reservations.get(i).getStart()) && end.isBefore(reservations.get(i).getStart())) {
                continue;
            }
            if ((start.isBefore(reservations.get(i).getEnd()) || start.isEqual(reservations.get(i).getStart())) && (end.isAfter(reservations.get(i).getStart()) || end.isEqual(reservations.get(i).getEnd()))) {
                return true;
            }
        }
        return false;
    }
    public void removeDuplicates() {
        reservations = new ArrayList<>(new HashSet<> (reservations));
    }
}
