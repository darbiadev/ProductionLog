package net.darbia.pl.data.sql;

import org.apache.logging.log4j.LogManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.sql.PreparedStatement;
import org.joda.time.DateTime;
import java.util.Map;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import org.apache.logging.log4j.Logger;

public final class SQL
{
    private static final Logger LOGGER;

    private SQL() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:ProductionLog/bin/ProductionLog.db");
        }
        catch (SQLException sqle) {
            SQL.LOGGER.error(sqle);
        }
        return connection;
    }

    public static void firstTimeRun() {
        try {
            final Statement stmt = connect().createStatement();
            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS 'Times' ('Label' TEXT, 'Stamp' TEXT);");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('dss','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('dse','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('dls','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('dle','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('nss','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('nse','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('nls','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("INSERT INTO Times(Label,Stamp) VALUES('nle','2018-12-04T15:18:59.141-06:00')");
                stmt.execute("CREATE TABLE IF NOT EXISTS 'Settings' ('Key' TEXT, 'Value' TEXT);");
                stmt.execute("INSERT INTO Settings(Key,Value) VALUES('language','en')");
                stmt.execute("INSERT INTO Settings(Key,Value) VALUES('country','US')");
                stmt.execute("INSERT INTO Settings(Key,Value) VALUES('password','yb86aKcCaUreNjLHG0TpBArVDQWF0G9ksLJlUADaAhA=')");
                stmt.execute("INSERT INTO Settings(Key,Value) VALUES('salt','YI38cM7TuDiZ8cdOlKYDd0Hhuf6QZx')");
                stmt.close();
            }
            catch (Throwable t) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                throw t;
            }
        }
        catch (SQLException sqle) {
            SQL.LOGGER.error(sqle);
        }
    }

    public static void saveTimes(final Map<String, DateTime> times) {
        try {
            final Connection conn = connect();
            try {
                final PreparedStatement pstmt = conn.prepareStatement("UPDATE Times SET Stamp = ? WHERE Label = ?");
                try {
                    conn.setAutoCommit(false);
                    final PreparedStatement preparedStatement = null;
                    times.forEach((key, value) -> {
                        try {
                            preparedStatement.setString(1, value.toString());
                            preparedStatement.setString(2, key);
                            preparedStatement.addBatch();
                        }
                        catch (SQLException sqle) {
                            SQL.LOGGER.error(sqle);
                        }
                    });
                    pstmt.executeBatch();
                    conn.commit();
                    pstmt.close();
                }
                catch (Throwable t) {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
                conn.close();
            }
            catch (Throwable t2) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception2) {
                        t2.addSuppressed(exception2);
                    }
                }
                throw t2;
            }
        }
        catch (SQLException sqle2) {
            SQL.LOGGER.error(sqle2);
        }
    }

    public static Map<String, DateTime> loadTimes() {
        final HashMap<String, DateTime> times = new HashMap<>();
        try {
            final ResultSet rs = connect().createStatement().executeQuery("SELECT * FROM Times");
            try {
                while (rs.next()) {
                    times.put(rs.getString("Label"), new DateTime(rs.getString("Stamp")));
                }
                rs.close();
                return times;
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
        }
        catch (SQLException sqle) {
            SQL.LOGGER.error(sqle);
            return times;
        }
    }

    public static Map<String, String> loadPassword() {
        try {
            final ResultSet rs = connect().createStatement().executeQuery("SELECT Key,Value FROM Settings WHERE Key = 'password' OR Key = 'salt'");
            try {
                final HashMap<String, String> password = new HashMap<>();
                while (rs.next()) {
                    password.put(rs.getString("Key"), rs.getString("Value"));
                }
                rs.close();
                return password;
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
        }
        catch (SQLException sqle) {
            SQL.LOGGER.error(sqle);
            return null;
        }
    }

    public static void updatePassword(final String password, final String salt) {
        final String sql = "UPDATE Settings SET Value = ? WHERE Key = ?";
        try {
            final Connection conn = connect();
            try {
                final PreparedStatement pstmt = conn.prepareStatement(sql);
                try {
                    pstmt.setString(1, password);
                    pstmt.setString(2, "password");
                    pstmt.executeUpdate();
                    pstmt.setString(1, salt);
                    pstmt.setString(2, "salt");
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                catch (Throwable t) {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
                conn.close();
            }
            catch (Throwable t2) {
                if (conn != null) {
                    try {
                        conn.close();
                    }
                    catch (Throwable exception2) {
                        t2.addSuppressed(exception2);
                    }
                }
                throw t2;
            }
        }
        catch (SQLException sqle) {
            SQL.LOGGER.error(sqle);
        }
    }

    public static Map<String, String> loadLocale() {
        try {
            final ResultSet rs = connect().createStatement().executeQuery("SELECT Key,Value FROM Settings WHERE Key = 'language' OR Key = 'country'");
            try {
                final HashMap<String, String> locale = new HashMap<>();
                while (rs.next()) {
                    locale.put(rs.getString("Key"), rs.getString("Value"));
                }
                rs.close();
                return locale;
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
        }
        catch (SQLException sqle) {
            SQL.LOGGER.error(sqle);
            return null;
        }
    }

    public static void updateLocale(final String language, final String country) {
        final String sql = "UPDATE Settings SET Value = ? WHERE Key = ?";
        try {
            final PreparedStatement pstmt = connect().prepareStatement(sql);
            try {
                pstmt.setString(1, language);
                pstmt.setString(2, "language");
                pstmt.executeUpdate();
                pstmt.setString(1, country);
                pstmt.setString(2, "country");
                pstmt.executeUpdate();
                pstmt.close();
            }
            catch (Throwable t) {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                throw t;
            }
        }
        catch (SQLException sqle) {
            SQL.LOGGER.error(sqle);
        }
    }

    static {
        LOGGER = LogManager.getLogger(SQL.class);
    }
}
