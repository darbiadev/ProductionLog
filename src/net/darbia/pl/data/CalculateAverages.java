// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.data;

import org.apache.logging.log4j.LogManager;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.HashMap;
import java.sql.SQLException;
import org.joda.time.ReadablePartial;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import net.darbia.pl.data.sql.SQL;
import java.util.ArrayList;
import java.util.Map;
import org.apache.logging.log4j.Logger;

final class CalculateAverages
{
    private static final Logger LOGGER;
    
    private CalculateAverages() {
        throw new IllegalStateException("Utility class");
    }
    
    static Map<String, ArrayList<Integer>> calculateSetUpAverages() {
        final Map<String, DateTime> times = SQL.loadTimes();
        final DateTime dss = times.get("dss").withDate(new LocalDate());
        DateTime dse = times.get("dse");
        final DateTime nss = times.get("nss").withDate(new LocalDate());
        DateTime nse = times.get("nse");
        if (times.get("dse").dayOfMonth().get() == 2) {
            dse = times.get("dse").withDate(new LocalDate()).plusDays(1);
        }
        else {
            dse = times.get("dse").withDate(new LocalDate());
        }
        if (times.get("nse").dayOfMonth().get() == 2) {
            nse = times.get("nse").withDate(new LocalDate()).plusDays(1);
        }
        else {
            nse = times.get("nse").withDate(new LocalDate());
        }
        final ArrayList<Integer> Dday1 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday2 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday3 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday4 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday5 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday6 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday7 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday8 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday9 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday10 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek1 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek2 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek3 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek4 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek5 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek6 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek7 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek8 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek9 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek10 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth1 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth2 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth3 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth4 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth5 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth6 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth7 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth8 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth9 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer10 = new ArrayList<Integer>();
        final String sql = "SELECT Machine, Created, AverageMinutesPerColor FROM Orders";
        try {
            final Connection conn = SQL.connect();
            try {
                final Statement stmt = conn.createStatement();
                try {
                    final ResultSet rs = stmt.executeQuery(sql);
                    try {
                        while (rs.next()) {
                            final int mn = rs.getInt("Machine");
                            final DateTime date = new DateTime(rs.getString("Created"));
                            final int minutes = rs.getInt("AverageMinutesPerColor");
                            if (mn == 1) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday1.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1).minusHours(1))) {
                                        Nday1.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek1.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek1.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth1.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth1.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer1.add(minutes);
                                }
                            }
                            if (mn == 2) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday2.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday2.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek2.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek2.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth2.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth2.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer2.add(minutes);
                                }
                            }
                            if (mn == 3) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday3.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday3.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek3.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek3.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth3.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth3.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer3.add(minutes);
                                }
                            }
                            if (mn == 4) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday4.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday4.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek4.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek4.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth4.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth4.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer4.add(minutes);
                                }
                            }
                            if (mn == 5) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday5.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday5.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek5.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek5.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth5.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth5.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer5.add(minutes);
                                }
                            }
                            if (mn == 6) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday6.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday6.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek6.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek6.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth6.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth6.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer6.add(minutes);
                                }
                            }
                            if (mn == 7) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday7.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday7.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek7.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek7.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth7.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth7.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer7.add(minutes);
                                }
                            }
                            if (mn == 8) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday8.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday8.add(minutes);
                                    }
                                    if (date.toLocalDate().equals(new LocalDate().minusDays(1))) {
                                        Nayer8.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek8.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek8.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth8.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth8.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer8.add(minutes);
                                }
                            }
                            if (mn == 9) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday9.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday9.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek9.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek9.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth9.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth9.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer9.add(minutes);
                                }
                            }
                            if (mn == 10) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday10.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday10.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek10.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek10.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth10.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth10.add(minutes);
                                    }
                                }
                                if (!date.toLocalDate().equals(new LocalDate().minusDays(1)) || !(date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    continue;
                                }
                                Nayer10.add(minutes);
                            }
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    }
                    catch (Throwable t) {
                        if (rs != null) {
                            try {
                                rs.close();
                            }
                            catch (Throwable exception) {
                                t.addSuppressed(exception);
                            }
                        }
                        throw t;
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                }
                catch (Throwable t2) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    throw t2;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Throwable t3) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception3) {
                        t3.addSuppressed(exception3);
                    }
                }
                throw t3;
            }
        }
        catch (SQLException e) {
            CalculateAverages.LOGGER.error(e.getMessage());
        }
        final ArrayList<Integer> Dday11 = new ArrayList<Integer>();
        Dday11.add(averageAL(Dday1));
        Dday11.add(averageAL(Dday2));
        Dday11.add(averageAL(Dday3));
        Dday11.add(averageAL(Dday4));
        Dday11.add(averageAL(Dday5));
        Dday11.add(averageAL(Dday6));
        Dday11.add(averageAL(Dday7));
        Dday11.add(averageAL(Dday8));
        Dday11.add(averageAL(Dday9));
        Dday11.add(averageAL(Dday10));
        final ArrayList<Integer> Dweek11 = new ArrayList<Integer>();
        Dweek11.add(averageAL(Dweek1));
        Dweek11.add(averageAL(Dweek2));
        Dweek11.add(averageAL(Dweek3));
        Dweek11.add(averageAL(Dweek4));
        Dweek11.add(averageAL(Dweek5));
        Dweek11.add(averageAL(Dweek6));
        Dweek11.add(averageAL(Dweek7));
        Dweek11.add(averageAL(Dweek8));
        Dweek11.add(averageAL(Dweek9));
        Dweek11.add(averageAL(Dweek10));
        final ArrayList<Integer> Dmonth11 = new ArrayList<Integer>();
        Dmonth11.add(averageAL(Dmonth1));
        Dmonth11.add(averageAL(Dmonth2));
        Dmonth11.add(averageAL(Dmonth3));
        Dmonth11.add(averageAL(Dmonth4));
        Dmonth11.add(averageAL(Dmonth5));
        Dmonth11.add(averageAL(Dmonth6));
        Dmonth11.add(averageAL(Dmonth7));
        Dmonth11.add(averageAL(Dmonth8));
        Dmonth11.add(averageAL(Dmonth9));
        Dmonth11.add(averageAL(Dmonth10));
        final ArrayList<Integer> Nday11 = new ArrayList<Integer>();
        Nday11.add(averageAL(Nday1));
        Nday11.add(averageAL(Nday2));
        Nday11.add(averageAL(Nday3));
        Nday11.add(averageAL(Nday4));
        Nday11.add(averageAL(Nday5));
        Nday11.add(averageAL(Nday6));
        Nday11.add(averageAL(Nday7));
        Nday11.add(averageAL(Nday8));
        Nday11.add(averageAL(Nday9));
        Nday11.add(averageAL(Nday10));
        final ArrayList<Integer> Nweek11 = new ArrayList<Integer>();
        Nweek11.add(averageAL(Nweek1));
        Nweek11.add(averageAL(Nweek2));
        Nweek11.add(averageAL(Nweek3));
        Nweek11.add(averageAL(Nweek4));
        Nweek11.add(averageAL(Nweek5));
        Nweek11.add(averageAL(Nweek6));
        Nweek11.add(averageAL(Nweek7));
        Nweek11.add(averageAL(Nweek8));
        Nweek11.add(averageAL(Nweek9));
        Nweek11.add(averageAL(Nweek10));
        final ArrayList<Integer> Nmonth11 = new ArrayList<Integer>();
        Nmonth11.add(averageAL(Nmonth1));
        Nmonth11.add(averageAL(Nmonth2));
        Nmonth11.add(averageAL(Nmonth3));
        Nmonth11.add(averageAL(Nmonth4));
        Nmonth11.add(averageAL(Nmonth5));
        Nmonth11.add(averageAL(Nmonth6));
        Nmonth11.add(averageAL(Nmonth7));
        Nmonth11.add(averageAL(Nmonth8));
        Nmonth11.add(averageAL(Nmonth9));
        Nmonth11.add(averageAL(Nmonth10));
        final ArrayList<Integer> Nayer11 = new ArrayList<Integer>();
        Nayer11.add(averageAL(Nayer1));
        Nayer11.add(averageAL(Nayer2));
        Nayer11.add(averageAL(Nayer3));
        Nayer11.add(averageAL(Nayer4));
        Nayer11.add(averageAL(Nayer5));
        Nayer11.add(averageAL(Nayer6));
        Nayer11.add(averageAL(Nayer7));
        Nayer11.add(averageAL(Nayer8));
        Nayer11.add(averageAL(Nayer9));
        Nayer11.add(averageAL(Nayer10));
        final HashMap<String, ArrayList<Integer>> averages = new HashMap<String, ArrayList<Integer>>();
        averages.put("DdaySU", Dday11);
        averages.put("DweekSU", Dweek11);
        averages.put("DmonthSU", Dmonth11);
        averages.put("NdaySU", Nday11);
        averages.put("NweekSU", Nweek11);
        averages.put("NmonthSU", Nmonth11);
        averages.put("NayerSU", Nayer11);
        return averages;
    }
    
    static Map<String, ArrayList<Integer>> calculateProductionAverages() {
        final Map<String, DateTime> times = SQL.loadTimes();
        final DateTime dss = times.get("dss").withDate(new LocalDate());
        DateTime dse = times.get("dse");
        final DateTime nss = times.get("nss").withDate(new LocalDate());
        DateTime nse = times.get("nse");
        if (times.get("dse").dayOfMonth().get() == 2) {
            dse = times.get("dse").withDate(new LocalDate()).plusDays(1);
        }
        else {
            dse = times.get("dse").withDate(new LocalDate());
        }
        if (times.get("nse").dayOfMonth().get() == 2) {
            nse = times.get("nse").withDate(new LocalDate()).plusDays(1);
        }
        else {
            nse = times.get("nse").withDate(new LocalDate());
        }
        final ArrayList<Integer> Dday1 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday2 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday3 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday4 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday5 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday6 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday7 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday8 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday9 = new ArrayList<Integer>();
        final ArrayList<Integer> Dday10 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek1 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek2 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek3 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek4 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek5 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek6 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek7 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek8 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek9 = new ArrayList<Integer>();
        final ArrayList<Integer> Dweek10 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth1 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth2 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth3 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth4 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth5 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth6 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth7 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth8 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth9 = new ArrayList<Integer>();
        final ArrayList<Integer> Dmonth10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nday10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nweek10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nmonth10 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer1 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer2 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer3 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer4 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer5 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer6 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer7 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer8 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer9 = new ArrayList<Integer>();
        final ArrayList<Integer> Nayer10 = new ArrayList<Integer>();
        final String sql = "SELECT Machine, Created, AverageUnitsPerHour FROM Orders";
        try {
            final Connection conn = SQL.connect();
            try {
                final Statement stmt = conn.createStatement();
                try {
                    final ResultSet rs = stmt.executeQuery(sql);
                    try {
                        while (rs.next()) {
                            final int mn = rs.getInt("Machine");
                            final DateTime date = new DateTime(rs.getString("Created"));
                            final int minutes = rs.getInt("AverageUnitsPerHour");
                            if (mn == 1) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday1.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday1.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek1.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek1.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth1.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth1.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer1.add(minutes);
                                }
                            }
                            if (mn == 2) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday2.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday2.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek2.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek2.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth2.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth2.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer2.add(minutes);
                                }
                            }
                            if (mn == 3) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday3.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday3.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek3.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek3.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth3.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth3.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer3.add(minutes);
                                }
                            }
                            if (mn == 4) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday4.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday4.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek4.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek4.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth4.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth4.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer4.add(minutes);
                                }
                            }
                            if (mn == 5) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday5.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday5.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek5.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek5.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth5.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth5.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer5.add(minutes);
                                }
                            }
                            if (mn == 6) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday6.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday6.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek6.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek6.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth6.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth6.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer6.add(minutes);
                                }
                            }
                            if (mn == 7) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday7.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday7.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek7.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek7.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth7.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth7.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer7.add(minutes);
                                }
                            }
                            if (mn == 8) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday8.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday8.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek8.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek8.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth8.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth8.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer8.add(minutes);
                                }
                            }
                            if (mn == 9) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday9.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday9.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek9.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek9.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth9.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth9.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().equals(new LocalDate().minusDays(1)) && (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    Nayer9.add(minutes);
                                }
                            }
                            if (mn == 10) {
                                if (date.toLocalDate().equals(new LocalDate())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dday10.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nday10.add(minutes);
                                    }
                                }
                                if (date.toLocalDate().isAfter(dss.toLocalDate().minusDays(7)) && date.isBeforeNow()) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dweek10.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nweek10.add(minutes);
                                    }
                                }
                                if (date.monthOfYear().equals(new DateTime().monthOfYear())) {
                                    if (date.toLocalTime().isAfter(dss.toLocalTime()) && date.toLocalTime().isBefore(dse.toLocalTime())) {
                                        Dmonth10.add(minutes);
                                    }
                                    else if (date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1))) {
                                        Nmonth10.add(minutes);
                                    }
                                }
                                if (!date.toLocalDate().equals(new LocalDate().minusDays(1)) || !(date.toLocalTime().isAfter(nss.toLocalTime()) | date.toLocalTime().isBefore(dss.toLocalTime().minusHours(1)))) {
                                    continue;
                                }
                                Nayer10.add(minutes);
                            }
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    }
                    catch (Throwable t) {
                        if (rs != null) {
                            try {
                                rs.close();
                            }
                            catch (Throwable exception) {
                                t.addSuppressed(exception);
                            }
                        }
                        throw t;
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                }
                catch (Throwable t2) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    throw t2;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Throwable t3) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception3) {
                        t3.addSuppressed(exception3);
                    }
                }
                throw t3;
            }
        }
        catch (SQLException e) {
            CalculateAverages.LOGGER.error(e.getMessage());
        }
        final ArrayList<Integer> Dday11 = new ArrayList<Integer>();
        Dday11.add(averageAL(Dday1));
        Dday11.add(averageAL(Dday2));
        Dday11.add(averageAL(Dday3));
        Dday11.add(averageAL(Dday4));
        Dday11.add(averageAL(Dday5));
        Dday11.add(averageAL(Dday6));
        Dday11.add(averageAL(Dday7));
        Dday11.add(averageAL(Dday8));
        Dday11.add(averageAL(Dday9));
        Dday11.add(averageAL(Dday10));
        final ArrayList<Integer> Dweek11 = new ArrayList<Integer>();
        Dweek11.add(averageAL(Dweek1));
        Dweek11.add(averageAL(Dweek2));
        Dweek11.add(averageAL(Dweek3));
        Dweek11.add(averageAL(Dweek4));
        Dweek11.add(averageAL(Dweek5));
        Dweek11.add(averageAL(Dweek6));
        Dweek11.add(averageAL(Dweek7));
        Dweek11.add(averageAL(Dweek8));
        Dweek11.add(averageAL(Dweek9));
        Dweek11.add(averageAL(Dweek10));
        final ArrayList<Integer> Dmonth11 = new ArrayList<Integer>();
        Dmonth11.add(averageAL(Dmonth1));
        Dmonth11.add(averageAL(Dmonth2));
        Dmonth11.add(averageAL(Dmonth3));
        Dmonth11.add(averageAL(Dmonth4));
        Dmonth11.add(averageAL(Dmonth5));
        Dmonth11.add(averageAL(Dmonth6));
        Dmonth11.add(averageAL(Dmonth7));
        Dmonth11.add(averageAL(Dmonth8));
        Dmonth11.add(averageAL(Dmonth9));
        Dmonth11.add(averageAL(Dmonth10));
        final ArrayList<Integer> Nday11 = new ArrayList<Integer>();
        Nday11.add(averageAL(Nday1));
        Nday11.add(averageAL(Nday2));
        Nday11.add(averageAL(Nday3));
        Nday11.add(averageAL(Nday4));
        Nday11.add(averageAL(Nday5));
        Nday11.add(averageAL(Nday6));
        Nday11.add(averageAL(Nday7));
        Nday11.add(averageAL(Nday8));
        Nday11.add(averageAL(Nday9));
        Nday11.add(averageAL(Nday10));
        final ArrayList<Integer> Nweek11 = new ArrayList<Integer>();
        Nweek11.add(averageAL(Nweek1));
        Nweek11.add(averageAL(Nweek2));
        Nweek11.add(averageAL(Nweek3));
        Nweek11.add(averageAL(Nweek4));
        Nweek11.add(averageAL(Nweek5));
        Nweek11.add(averageAL(Nweek6));
        Nweek11.add(averageAL(Nweek7));
        Nweek11.add(averageAL(Nweek8));
        Nweek11.add(averageAL(Nweek9));
        Nweek11.add(averageAL(Nweek10));
        final ArrayList<Integer> Nmonth11 = new ArrayList<Integer>();
        Nmonth11.add(averageAL(Nmonth1));
        Nmonth11.add(averageAL(Nmonth2));
        Nmonth11.add(averageAL(Nmonth3));
        Nmonth11.add(averageAL(Nmonth4));
        Nmonth11.add(averageAL(Nmonth5));
        Nmonth11.add(averageAL(Nmonth6));
        Nmonth11.add(averageAL(Nmonth7));
        Nmonth11.add(averageAL(Nmonth8));
        Nmonth11.add(averageAL(Nmonth9));
        Nmonth11.add(averageAL(Nmonth10));
        final ArrayList<Integer> Nayer11 = new ArrayList<Integer>();
        Nayer11.add(averageAL(Nayer1));
        Nayer11.add(averageAL(Nayer2));
        Nayer11.add(averageAL(Nayer3));
        Nayer11.add(averageAL(Nayer4));
        Nayer11.add(averageAL(Nayer5));
        Nayer11.add(averageAL(Nayer6));
        Nayer11.add(averageAL(Nayer7));
        Nayer11.add(averageAL(Nayer8));
        Nayer11.add(averageAL(Nayer9));
        Nayer11.add(averageAL(Nayer10));
        final HashMap<String, ArrayList<Integer>> averages = new HashMap<String, ArrayList<Integer>>();
        averages.put("DdayPO", Dday11);
        averages.put("DweekPO", Dweek11);
        averages.put("DmonthPO", Dmonth11);
        averages.put("NdayPO", Nday11);
        averages.put("NweekPO", Nweek11);
        averages.put("NmonthPO", Nmonth11);
        averages.put("NayerPO", Nayer11);
        return averages;
    }
    
    private static int averageAL(final ArrayList<Integer> al) {
        int total = 0;
        int numberOfnon0Values = 0;
        if (!al.isEmpty()) {
            for (final int x : al) {
                total += x;
                if (x != 0) {
                    ++numberOfnon0Values;
                }
            }
        }
        try {
            return total / numberOfnon0Values;
        }
        catch (ArithmeticException ae) {
            return 0;
        }
    }
    
    static {
        LOGGER = LogManager.getLogger(CalculateAverages.class);
    }
}
