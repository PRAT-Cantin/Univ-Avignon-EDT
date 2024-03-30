package com.example.edtunivavignon;

import java.io.IOException;
import java.time.LocalDateTime;

public interface CalendarController {
    public void displayNext() throws IOException;
    public void displayToday() throws IOException;
    public void displayPrevious() throws IOException;
    public void displaySpecific(LocalDateTime localDateTime) throws IOException;
    public LocalDateTime getDisplayedDate();
}
