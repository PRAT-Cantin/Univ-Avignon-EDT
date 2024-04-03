package com.example.edtunivavignon;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface CalendarController {
    public void setEdtToDisplay(String url) throws IOException;
    public void displayNext() throws IOException;
    public void displayToday() throws IOException;
    public void displayPrevious() throws IOException;
    public void displaySpecific(LocalDateTime localDateTime) throws IOException;
    public LocalDateTime getDisplayedDate();
    public void setCustomCalendar(String customName);
    public ArrayList<String> getCourses();
    public ArrayList<String> getGroups();
    public ArrayList<String> getRooms();
    public ArrayList<String> getTypes();
    public void setFilterCourses(ArrayList<String> cours) throws IOException;
    public void setFilterGroups(ArrayList<String> cours) throws IOException;
    public void setFilterRooms(ArrayList<String> cours) throws IOException;
    public void setFilterTypes(ArrayList<String> cours) throws IOException;
}
