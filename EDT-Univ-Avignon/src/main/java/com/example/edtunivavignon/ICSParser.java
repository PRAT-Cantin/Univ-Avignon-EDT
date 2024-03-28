package com.example.edtunivavignon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ICSParser {

    public static LocalDateTime readDate(String line) {
        String dateTime = line.split(":")[1];
        String date = dateTime.split("T")[0];
        String time = "000000";
        if (!dateTime.equals(date)) {
            time = dateTime.split("T")[1].split("Z")[0];
        }
        return LocalDateTime.of(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(6, 8)),
                Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(2, 4)), Integer.parseInt(time.substring(4, 6))
                    ).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("ECT",ZoneId.SHORT_IDS)).toLocalDateTime();
    }

    public static String readLines(BufferedReader reader) throws IOException {
        String toRead = reader.readLine().split(":", 2)[1];
        reader.mark(76);
        String read = reader.readLine();
        while (read.charAt(0) == ' ') {
            toRead += read.substring(1);
            reader.mark(76);
            read = reader.readLine();
        }
        reader.reset();
        return toRead;
    }

    public static EDTCalendar readICS(String link) throws IOException {
        URL calendar = new URL(link);
        BufferedReader reader = new BufferedReader(new InputStreamReader(calendar.openStream()));
        EDTCalendar edtCalendar = new EDTCalendar();
        String calName = reader.readLine().split(":")[1];
        //skip the first lines of the file
        for (int i = 1; i < 4; i++) {
            reader.readLine();
        }
        edtCalendar.setCalendarStart(readDate(reader.readLine()));
        edtCalendar.setCalendarEnd(readDate(reader.readLine()));
        edtCalendar.setCalName(readLines(reader));
        edtCalendar.setCalDesc(readLines(reader));
        String line;
        reader.mark(76);
        Reservation reservation = new Reservation();
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("BEGIN:")) {
                reservation = new Reservation();
                continue;
            }
            else if (line.startsWith("END:VEVENT")) {
                edtCalendar.addReservation(reservation);
                continue;
            }
            else if (line.startsWith("END:"+calName)) {
                break;
            }
            else if (line.startsWith("X-ALT-DESC") || line.startsWith("CATEGORIES:") || line.startsWith("DTSTAMP:")) {
                reader.reset();
                readLines(reader);
                continue;
            }
            else if (line.startsWith("DTSTART")) {
                reservation.setStart(readDate(line));
                reader.mark(76);
                continue;
            }
            else if (line.startsWith("DTEND")) {
                reservation.setEnd(readDate(line));
                reader.mark(76);
                continue;
            }
            else if (line.startsWith("LAST-MODIFIED")) {
                reservation.setLastModified(readDate(line));
                reader.mark(76);
                continue;
            }
            else if (line.startsWith("UID")) {
                reader.reset();
                reservation.setUid(readLines(reader));
                reader.mark(76);
                continue;
            }
            else if (line.startsWith("LOCATION")) {
                reader.reset();
                reservation.setRooms(new ArrayList<String>(List.of(readLines(reader).split("\\\\"))));
                reader.mark(76);
                continue;
            }
            else if (line.startsWith("SUMMARY")) {
                reader.reset();
                String[] summary = readLines(reader).split(" - ");
                if (summary.length == 1) {
                    reservation.setHoliday(true);
                }
                reader.mark(76);
                continue;
            }
            else if (line.startsWith("DESCRIPTION")) {
                reader.reset();
                String[] summary = readLines(reader).split("\\\\n");
                if (summary.length > 1) {
                    for (String piece : summary
                    ) {
                        if (piece.startsWith("Matière")) {
                            reservation.setNameOfReservation(piece.split(" : ")[1]);
                        }
                        else if (piece.startsWith("Enseignant")) {
                            reservation.setTeachers(new ArrayList<String>(List.of(piece.split(" : ")[1].split("\\\\"))));
                        }
                        else if (piece.startsWith("Promotion")) {
                            reservation.setAttendingPromotions(new ArrayList<String>(List.of(piece.split(" : ")[1].split("\\\\"))));
                        }
                        else if (piece.startsWith("TD")) {
                            reservation.setAttendingGroups(new ArrayList<String>(List.of(piece.split(" : ")[1].split("\\\\"))));
                        }
                        else if (piece.startsWith("Type")) {
                            reservation.setType(piece.split(" : ")[1]);
                        }
                        else if (piece.startsWith("Mémo")) {
                            reservation.setMemo(piece.split(" : ")[1]);
                        }
                    }
                }
                reader.mark(76);
                continue;
            }
            reader.mark(76);
        }
        return edtCalendar;
    }
}

