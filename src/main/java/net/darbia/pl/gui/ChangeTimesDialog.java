// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.gui;

import org.apache.logging.log4j.LogManager;
import org.joda.time.format.DateTimeFormat;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import org.joda.time.Period;
import org.joda.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import org.joda.time.ReadableInstant;
import com.github.lgooddatepicker.components.TimePicker;
import java.awt.Component;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import net.darbia.pl.data.sql.SQL;
import java.awt.EventQueue;
import java.util.ResourceBundle;
import org.joda.time.DateTime;
import javax.swing.JFrame;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormatter;

class ChangeTimesDialog
{
    private static final DateTimeFormatter timeFormatRead;
    private static final DateTimeFormatter timeFormatDisplay;
    private static final Logger LOGGER;
    private JFrame frmChangeShiftTimes;
    private DateTime dss;
    private DateTime dse;
    private DateTime dls;
    private DateTime dle;
    private DateTime nss;
    private DateTime nse;
    private DateTime nls;
    private DateTime nle;
    
    private ChangeTimesDialog(final ResourceBundle messages) {
        this.dss = null;
        this.dse = null;
        this.dls = null;
        this.dle = null;
        this.nss = null;
        this.nse = null;
        this.nls = null;
        this.nle = null;
        this.initialize(messages);
    }
    
    public static void main(final ResourceBundle messages) {
        ChangeTimesDialog window;
        EventQueue.invokeLater(() -> {
            try {
                window = new ChangeTimesDialog(messages);
                window.frmChangeShiftTimes.setVisible(true);
            }
            catch (Exception exception) {
                ChangeTimesDialog.LOGGER.error(exception);
            }
        });
    }
    
    private void initialize(final ResourceBundle messages) {
        final Map<String, DateTime> times = SQL.loadTimes();
        this.frmChangeShiftTimes = new JFrame();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = classLoader.getResource("images/logo.jpg");
        final ImageIcon icon = new ImageIcon(resource);
        this.frmChangeShiftTimes.setIconImage(icon.getImage());
        this.frmChangeShiftTimes.setTitle(messages.getString("ChangeShiftTimes"));
        this.frmChangeShiftTimes.setBounds(100, 100, 484, 214);
        this.frmChangeShiftTimes.setDefaultCloseOperation(2);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 144, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        this.frmChangeShiftTimes.getContentPane().setLayout(gridBagLayout);
        final JLabel lblDayShift = new JLabel(messages.getString("DayShift"));
        final GridBagConstraints gbclblDayShift = new GridBagConstraints();
        gbclblDayShift.insets = new Insets(0, 0, 5, 5);
        gbclblDayShift.gridx = 0;
        gbclblDayShift.gridy = 0;
        this.frmChangeShiftTimes.getContentPane().add(lblDayShift, gbclblDayShift);
        final TimePicker TPdss = new TimePicker();
        TPdss.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("dss")));
        final GridBagConstraints gbcTPdss = new GridBagConstraints();
        gbcTPdss.insets = new Insets(0, 0, 5, 5);
        gbcTPdss.gridx = 1;
        gbcTPdss.gridy = 0;
        this.frmChangeShiftTimes.getContentPane().add(TPdss, gbcTPdss);
        final TimePicker TPdse = new TimePicker();
        TPdse.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("dse")));
        final GridBagConstraints gbcTPdse = new GridBagConstraints();
        gbcTPdse.insets = new Insets(0, 0, 5, 5);
        gbcTPdse.gridx = 2;
        gbcTPdse.gridy = 0;
        this.frmChangeShiftTimes.getContentPane().add(TPdse, gbcTPdse);
        final JCheckBox checkBoxDS = new JCheckBox(messages.getString("ShiftEndsOnNextDay?"));
        final GridBagConstraints gbccheckBoxDS = new GridBagConstraints();
        gbccheckBoxDS.insets = new Insets(0, 0, 5, 0);
        gbccheckBoxDS.gridx = 3;
        gbccheckBoxDS.gridy = 0;
        this.frmChangeShiftTimes.getContentPane().add(checkBoxDS, gbccheckBoxDS);
        final JLabel lblDayShiftLunch = new JLabel(messages.getString("DayShiftLunch"));
        final GridBagConstraints gbclblDayShiftLunch = new GridBagConstraints();
        gbclblDayShiftLunch.insets = new Insets(0, 0, 5, 5);
        gbclblDayShiftLunch.gridx = 0;
        gbclblDayShiftLunch.gridy = 1;
        this.frmChangeShiftTimes.getContentPane().add(lblDayShiftLunch, gbclblDayShiftLunch);
        final TimePicker TPdls = new TimePicker();
        TPdls.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("dls")));
        final GridBagConstraints gbcTPdls = new GridBagConstraints();
        gbcTPdls.insets = new Insets(0, 0, 5, 5);
        gbcTPdls.gridx = 1;
        gbcTPdls.gridy = 1;
        this.frmChangeShiftTimes.getContentPane().add(TPdls, gbcTPdls);
        final TimePicker TPdle = new TimePicker();
        TPdle.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("dle")));
        final GridBagConstraints gbcTPdle = new GridBagConstraints();
        gbcTPdle.insets = new Insets(0, 0, 5, 5);
        gbcTPdle.gridx = 2;
        gbcTPdle.gridy = 1;
        this.frmChangeShiftTimes.getContentPane().add(TPdle, gbcTPdle);
        final JCheckBox checkBoxDSL = new JCheckBox(messages.getString("LunchEndsOnNextDay?"));
        final GridBagConstraints gbccheckBoxDSL = new GridBagConstraints();
        gbccheckBoxDSL.insets = new Insets(0, 0, 5, 0);
        gbccheckBoxDSL.gridx = 3;
        gbccheckBoxDSL.gridy = 1;
        this.frmChangeShiftTimes.getContentPane().add(checkBoxDSL, gbccheckBoxDSL);
        final JLabel lblNightShift = new JLabel(messages.getString("NightShift"));
        final GridBagConstraints gbclblNightShift = new GridBagConstraints();
        gbclblNightShift.insets = new Insets(0, 0, 5, 5);
        gbclblNightShift.gridx = 0;
        gbclblNightShift.gridy = 2;
        this.frmChangeShiftTimes.getContentPane().add(lblNightShift, gbclblNightShift);
        final TimePicker TPnss = new TimePicker();
        TPnss.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("nss")));
        final GridBagConstraints gbcTPnss = new GridBagConstraints();
        gbcTPnss.insets = new Insets(0, 0, 5, 5);
        gbcTPnss.gridx = 1;
        gbcTPnss.gridy = 2;
        this.frmChangeShiftTimes.getContentPane().add(TPnss, gbcTPnss);
        final TimePicker TPnse = new TimePicker();
        TPnse.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("nse")));
        final GridBagConstraints gbcTPnse = new GridBagConstraints();
        gbcTPnse.insets = new Insets(0, 0, 5, 5);
        gbcTPnse.gridx = 2;
        gbcTPnse.gridy = 2;
        this.frmChangeShiftTimes.getContentPane().add(TPnse, gbcTPnse);
        final JCheckBox checkBoxNS = new JCheckBox(messages.getString("ShiftEndsOnNextDay?"));
        final GridBagConstraints gbccheckBoxNS = new GridBagConstraints();
        gbccheckBoxNS.insets = new Insets(0, 0, 5, 0);
        gbccheckBoxNS.gridx = 3;
        gbccheckBoxNS.gridy = 2;
        this.frmChangeShiftTimes.getContentPane().add(checkBoxNS, gbccheckBoxNS);
        final JLabel lblNightShiftLunch = new JLabel(messages.getString("NightShiftLunch"));
        final GridBagConstraints gbclblNightShiftLunch = new GridBagConstraints();
        gbclblNightShiftLunch.anchor = 13;
        gbclblNightShiftLunch.insets = new Insets(0, 0, 5, 5);
        gbclblNightShiftLunch.gridx = 0;
        gbclblNightShiftLunch.gridy = 3;
        this.frmChangeShiftTimes.getContentPane().add(lblNightShiftLunch, gbclblNightShiftLunch);
        final TimePicker TPnls = new TimePicker();
        TPnls.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("nls")));
        final GridBagConstraints gbcTPnls = new GridBagConstraints();
        gbcTPnls.insets = new Insets(0, 0, 5, 5);
        gbcTPnls.gridx = 1;
        gbcTPnls.gridy = 3;
        this.frmChangeShiftTimes.getContentPane().add(TPnls, gbcTPnls);
        final TimePicker TPnle = new TimePicker();
        TPnle.setText(ChangeTimesDialog.timeFormatDisplay.print(times.get("nle")));
        final GridBagConstraints gbcTPnle = new GridBagConstraints();
        gbcTPnle.insets = new Insets(0, 0, 5, 5);
        gbcTPnle.gridx = 2;
        gbcTPnle.gridy = 3;
        this.frmChangeShiftTimes.getContentPane().add(TPnle, gbcTPnle);
        final JCheckBox checkBoxNSL = new JCheckBox(messages.getString("LunchEndsOnNextDay?"));
        final GridBagConstraints gbccheckBoxNSL = new GridBagConstraints();
        gbccheckBoxNSL.insets = new Insets(0, 0, 5, 0);
        gbccheckBoxNSL.gridx = 3;
        gbccheckBoxNSL.gridy = 3;
        this.frmChangeShiftTimes.getContentPane().add(checkBoxNSL, gbccheckBoxNSL);
        final JButton btnSave = new JButton(messages.getString("Save"));
        final GridBagConstraints gbcbtnSave = new GridBagConstraints();
        gbcbtnSave.fill = 1;
        gbcbtnSave.insets = new Insets(0, 0, 5, 5);
        gbcbtnSave.gridx = 1;
        gbcbtnSave.gridy = 4;
        this.frmChangeShiftTimes.getContentPane().add(btnSave, gbcbtnSave);
        final JButton btnCancel = new JButton(messages.getString("Cancel"));
        final GridBagConstraints gbcbtnCancel = new GridBagConstraints();
        gbcbtnCancel.fill = 3;
        gbcbtnCancel.insets = new Insets(0, 0, 5, 5);
        gbcbtnCancel.gridx = 2;
        gbcbtnCancel.gridy = 4;
        this.frmChangeShiftTimes.getContentPane().add(btnCancel, gbcbtnCancel);
        btnCancel.addActionListener(evt -> {
            if (JOptionPane.showConfirmDialog(null, messages.getString("Cancel?"), messages.getString("Cancel?"), 0) == 0) {
                this.frmChangeShiftTimes.dispose();
            }
            return;
        });
        final LocalDate epoch;
        boolean problems;
        boolean check;
        final TimePicker timePicker;
        final TimePicker timePicker2;
        final AbstractButton abstractButton;
        final TimePicker timePicker3;
        final TimePicker timePicker4;
        final AbstractButton abstractButton2;
        final TimePicker timePicker5;
        final TimePicker timePicker6;
        final AbstractButton abstractButton3;
        final TimePicker timePicker7;
        final TimePicker timePicker8;
        final AbstractButton abstractButton4;
        String[] message;
        HashMap<String, DateTime> newTimes;
        btnSave.addActionListener(evt -> {
            epoch = new LocalDate(1970, 1, 1);
            problems = false;
            check = false;
            try {
                this.dss = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker.toString())).withDate(epoch);
            }
            catch (IllegalArgumentException iae) {
                ChangeTimesDialog.LOGGER.error(iae);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorDayShiftStartUndefined"));
                problems = true;
            }
            try {
                this.dse = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker2.toString())).withDate(epoch);
                if (abstractButton.isSelected()) {
                    this.dse = this.dse.withDate(epoch).plusDays(1);
                }
            }
            catch (IllegalArgumentException iae2) {
                ChangeTimesDialog.LOGGER.error(iae2);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorDayShiftEndUndefined"));
                problems = true;
            }
            try {
                this.dls = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker3.toString())).withDate(epoch);
            }
            catch (IllegalArgumentException iae3) {
                ChangeTimesDialog.LOGGER.error(iae3);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorDayLunchStartUndefined"));
                problems = true;
            }
            try {
                this.dle = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker4.toString())).withDate(epoch);
                if (abstractButton2.isSelected()) {
                    this.dle = this.dle.withDate(epoch).plusDays(1);
                }
            }
            catch (IllegalArgumentException iae4) {
                ChangeTimesDialog.LOGGER.error(iae4);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorDayLunchEndUndefined"));
                problems = true;
            }
            try {
                this.nss = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker5.toString())).withDate(epoch);
            }
            catch (IllegalArgumentException iae5) {
                ChangeTimesDialog.LOGGER.error(iae5);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorNightShiftStartUndefined"));
                problems = true;
            }
            try {
                this.nse = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker6.toString())).withDate(epoch);
                if (abstractButton3.isSelected()) {
                    this.nse = this.nse.withDate(epoch).plusDays(1);
                }
            }
            catch (IllegalArgumentException iae6) {
                ChangeTimesDialog.LOGGER.error(iae6);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorNightShiftEndUndefined"));
                problems = true;
            }
            try {
                this.nls = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker7.toString())).withDate(epoch);
            }
            catch (IllegalArgumentException iae7) {
                ChangeTimesDialog.LOGGER.error(iae7);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorNightLunchStartUndefined"));
                problems = true;
            }
            try {
                this.nle = new DateTime(ChangeTimesDialog.timeFormatRead.parseDateTime(timePicker8.toString()));
                if (abstractButton4.isSelected()) {
                    this.nle = this.nle.withDate(epoch).plusDays(1);
                }
            }
            catch (IllegalArgumentException iae8) {
                ChangeTimesDialog.LOGGER.error(iae8);
                JOptionPane.showMessageDialog(null, messages.getString("ErrorNightLunchEndUndefined"));
                problems = true;
            }
            if (!problems) {
                message = new String[] { String.format(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, messages.getString("DayShift"), messages.getString("Hours"), messages.getString("Minutes")), new Object[] { ChangeTimesDialog.timeFormatDisplay.print(this.dss), ChangeTimesDialog.timeFormatDisplay.print(this.dse), new Period(this.dss, this.dse).getHours(), new Period(this.dss, this.dse).getMinutes() }), String.format(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, messages.getString("DayShiftLunch"), messages.getString("Hours"), messages.getString("Minutes")), new Object[] { ChangeTimesDialog.timeFormatDisplay.print(this.dls), ChangeTimesDialog.timeFormatDisplay.print(this.dle), new Period(this.dls, this.dle).getHours(), new Period(this.dls, this.dle).getMinutes() }), String.format(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, messages.getString("NightShift"), messages.getString("Hours"), messages.getString("Minutes")), new Object[] { ChangeTimesDialog.timeFormatDisplay.print(this.nss), ChangeTimesDialog.timeFormatDisplay.print(this.nse), new Period(this.nss, this.nse).getHours(), new Period(this.nss, this.nse).getMinutes() }), String.format(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, messages.getString("NightShiftLunch"), messages.getString("Hours"), messages.getString("Minutes")), new Object[] { ChangeTimesDialog.timeFormatDisplay.print(this.nls), ChangeTimesDialog.timeFormatDisplay.print(this.nle), new Period(this.nls, this.nle).getHours(), new Period(this.nls, this.nle).getMinutes() }) };
                if (JOptionPane.showConfirmDialog(null, message, messages.getString("Save?"), 0) == 0) {
                    check = true;
                }
            }
            if (check) {
                newTimes = new HashMap<String, DateTime>();
                newTimes.put("dss", this.dss);
                newTimes.put("dse", this.dse);
                newTimes.put("dls", this.dls);
                newTimes.put("dle", this.dle);
                newTimes.put("nss", this.nss);
                newTimes.put("nse", this.nse);
                newTimes.put("nls", this.nls);
                newTimes.put("nle", this.nle);
                SQL.saveTimes(newTimes);
                this.frmChangeShiftTimes.dispose();
            }
        });
    }
    
    static {
        timeFormatRead = DateTimeFormat.forPattern("HH:mm");
        timeFormatDisplay = DateTimeFormat.forPattern("hh:mm a");
        LOGGER = LogManager.getLogger(ChangeTimesDialog.class);
    }
}
