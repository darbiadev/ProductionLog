package net.darbia.pl.gui;

import net.darbia.pl.data.Average;
import net.darbia.pl.data.HTML;
import net.darbia.pl.data.PasswordUtils;
import net.darbia.pl.data.sql.SQL;
import net.darbia.pl.javax.swing.OrderListCellRenderer;
import net.darbia.pl.objects.Employee;
import net.darbia.pl.objects.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.JTextComponent;

public class MainWindow {

  private static final DateTimeFormatter DF;
  private static final Logger LOGGER;
  private final HashMap<Integer, Employee[]> machineNames;
  private JFrame frameMainWindow;
  private DateTime dss;
  private DateTime dse;
  private DateTime dls;
  private DateTime dle;
  private DateTime nss;
  private DateTime nse;
  private DateTime nls;
  private DateTime nle;
  private List<Employee> employees;
  private ResourceBundle messages;

  private MainWindow() {
    final File file = new File("./ProductionLog/bin/.lock");
    file.getParentFile().mkdirs();
    if (!file.exists()) {
      try {
        if (!file.createNewFile()) {
          MainWindow.LOGGER.error("Failed to create .lock file");
        }
      } catch (IOException ioe) {
        MainWindow.LOGGER.error("IOException while creating .lock file.", ioe);
      }
      new File("./ProductionLog/logs/").mkdirs();
      SQL.firstTimeRun();
    }
    MainWindow.LOGGER.debug("Startup");
    try {
      System.setOut(
          new PrintStream(new FileOutputStream("./ProductionLog/logs/sysOut.log", true), true,
              StandardCharsets.UTF_8));
      System.setErr(
          new PrintStream(new FileOutputStream("./ProductionLog/logs/sysErr.log", true), true,
              StandardCharsets.UTF_8));
    } catch (FileNotFoundException fnf) {
      MainWindow.LOGGER.error("System out redirects", fnf);
    }
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
             IllegalAccessException ex2) {
      final Exception ex = null;
        MainWindow.LOGGER.error("UIManager.setLookAndFeel", ex);
    }
    this.machineNames = new HashMap<Integer, Employee[]>();
    final Employee[] names = new Employee[4];
    for (int i = 1; i <= 10; ++i) {
      this.machineNames.put(i, names);
    }
    try {
      this.loadTimes();
    } catch (NullPointerException npe) {
      MainWindow.LOGGER.error("NPE: Times", npe);
    }
    try {
      System.out.println(HTML.getEmployees());
      this.employees = HTML.getEmployees();
    } catch (NullPointerException npe) {
      MainWindow.LOGGER.error("NPE: Employees", npe);
    }
    this.setLocale();
    MainWindow.LOGGER.debug("DSS: {}", this.dss);
    MainWindow.LOGGER.debug("DSE: {}", this.dse);
    MainWindow.LOGGER.debug("DLS: {}", this.dls);
    MainWindow.LOGGER.debug("DLE: {}", this.dle);
    MainWindow.LOGGER.debug("NSS: {}", this.nss);
    MainWindow.LOGGER.debug("NSE: {}", this.nse);
    MainWindow.LOGGER.debug("NLS: {}", this.nls);
    MainWindow.LOGGER.debug("NLE: {}", this.nle);
    this.initialize();
  }

  public static void main(final String[] args) {
    MainWindow window;
    EventQueue.invokeLater(() -> {
      try {
        window = new MainWindow();
        window.frameMainWindow.setVisible(true);
      } catch (Exception exception) {
        MainWindow.LOGGER.error("EventQueue", exception);
      }
    });
  }

  void setLocale() {
    ResourceBundle.clearCache();
    final Map<String, String> localeMap = SQL.loadLocale();
    final Locale currentLocale = new Locale(localeMap.get("language"), localeMap.get("country"));
    Locale.setDefault(currentLocale);
    this.messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
  }

  private void loadTimes() {
    final Map<String, DateTime> times = SQL.loadTimes();
    final LocalDate now = new LocalDate();
    this.dss = times.get("dss").withDate(now);
    if (times.get("dse").dayOfMonth().get() == 2) {
      this.dse = times.get("dse").withDate(now).plusDays(1);
    } else {
      this.dse = times.get("dse").withDate(now);
    }
    this.dls = times.get("dls").withDate(now);
    if (times.get("dle").dayOfMonth().get() == 2) {
      this.dle = times.get("dle").withDate(now).plusDays(1);
    } else {
      this.dle = times.get("dle").withDate(now);
    }
    this.nss = times.get("nss").withDate(now);
    if (times.get("nse").dayOfMonth().get() == 2) {
      this.nse = times.get("nse").withDate(now).plusDays(1);
    } else {
      this.nse = times.get("nse").withDate(now);
    }
    this.nls = times.get("nls").withDate(now);
    if (times.get("nle").dayOfMonth().get() == 2) {
      this.nle = times.get("nle").withDate(now).plusDays(1);
    } else {
      this.nle = times.get("nle").withDate(now);
    }
  }

  private boolean isDayShift() {
    final DateTime now = new DateTime();
    return now.isAfter(this.dss) && now.isBefore(this.dse);
  }

  private boolean isNightShift() {
    final DateTime now = new DateTime();
    return now.isAfter(this.nss) && now.isBefore(this.nse);
  }

  private Interval isDuringLunch(final Order order, final String source) {
    Interval lunch = null;
    if (this.isDayShift()) {
      lunch = new Interval(this.dls, this.dle);
    } else if (this.isNightShift()) {
      lunch = new Interval(this.nls, this.nle);
    }
    if (lunch == null) {
      final Object[] options = {this.messages.getString("DayShift"),
          this.messages.getString("NightShift")};
      final int result = JOptionPane.showOptionDialog(null,
          this.messages.getString("ErrorLunchDecidedFailed"),
          this.messages.getString("ErrorLunchDecidedFailed"), 0, -1, null, options, null);
      if (result == 0) {
        lunch = new Interval(this.dls, this.dle);
      } else if (result == 1) {
        lunch = new Interval(this.nls, this.nle);
      }
    }
    if (source.equals("approved") && Objects.requireNonNull(lunch).overlaps(
        new Interval(order.getDSetUp(), order.getDApproved()))) {
      if (lunch.overlap(new Interval(order.getDSetUp(), order.getDApproved())).toDuration()
          .getStandardMinutes() > 5L) {
        return lunch.overlap(new Interval(order.getDSetUp(), order.getDApproved()));
      }
    } else {
      if (!source.equals("complete") || !Objects.requireNonNull(lunch).overlaps(
          new Interval(order.getDApproved(), order.getDComplete()))) {
        return null;
      }
      if (lunch.overlap(new Interval(order.getDApproved(), order.getDComplete())).toDuration()
          .getStandardMinutes() > 5L) {
        return lunch.overlap(new Interval(order.getDApproved(), order.getDComplete()));
      }
    }
      return null;
  }

  private int calculateMinutes(final Order order, final String source) {
    this.loadTimes();
    MainWindow.LOGGER.debug("Calculating minutes");
    MainWindow.LOGGER.debug("Order number: {}", order.getOrderNumber());
    MainWindow.LOGGER.debug("Design number: {}", order.getDesignNumber());
    List<Interval> holdIntervals = null;
    Interval testCase = null;
    final Interval duringLunch = this.isDuringLunch(order, source);
    boolean onHoldDuringLunch = false;
    int minutes = 0;
    if (source.equals("approved")) {
      minutes = new Period(order.getDSetUp(), order.getDApproved()).getMinutes()
          + new Period(order.getDSetUp(), order.getDApproved()).getHours() * 60;
      MainWindow.LOGGER.debug("Minutes: {}", minutes);
      if (order.getSUohMinutes() > 0) {
        minutes -= order.getSUohMinutes();
        MainWindow.LOGGER.debug("Hold minutes removed: {}", order.getSUohMinutes());
        order.addSetUpMinutesRemoved("Hold", order.getSUohMinutes());
      }
      testCase = new Interval(order.getDSetUp(), order.getDApproved());
      holdIntervals = order.getHoldTimesSU();
    } else if (source.equals("complete")) {
      minutes = new Period(order.getDApproved(), order.getDComplete()).getMinutes()
          + new Period(order.getDApproved(), order.getDComplete()).getHours() * 60;
      MainWindow.LOGGER.debug("Minutes: {}", minutes);
      if (order.getPohMinutes() > 0) {
        minutes -= order.getPohMinutes();
        MainWindow.LOGGER.debug("Hold minutes removed: {}", order.getSUohMinutes());
        order.addProductionMinutesRemoved("Hold", order.getPohMinutes());
      }
      testCase = new Interval(order.getDApproved(), order.getDComplete());
      holdIntervals = order.getHoldTimesP();
    }
    if (duringLunch != null && holdIntervals != null) {
      for (final Interval interval : holdIntervals) {
        if (interval.overlaps(testCase)) {
          onHoldDuringLunch = true;
          MainWindow.LOGGER.debug("On hold during lunch?: {}", true);
        }
      }
    }
    if (duringLunch != null && !onHoldDuringLunch) {
      int lunchMinutes = 0;
      if (this.isDayShift()) {
        lunchMinutes = new Period(this.dls, this.dle).getMinutes();
      } else if (this.isNightShift()) {
        lunchMinutes = new Period(this.nls, this.nle).getMinutes();
      }
      minutes -= lunchMinutes;
      MainWindow.LOGGER.debug("Minutes removed for lunch: {}", lunchMinutes);
      if (source.equals("approved")) {
        order.addSetUpMinutesRemoved("Lunch", lunchMinutes);
      } else if (source.equals("complete")) {
        order.addProductionMinutesRemoved("Lunch", lunchMinutes);
      }
    }
    MainWindow.LOGGER.debug("End calculation. Final minutes: {}", minutes);
    return minutes;
  }

  private void initialize() {
    this.frameMainWindow = new JFrame();
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final URL resource = classLoader.getResource("images/logo.jpg");
    final ImageIcon icon = new ImageIcon(resource);
    this.frameMainWindow.setIconImage(icon.getImage());
    this.frameMainWindow.setTitle("ProductionLog");
    this.frameMainWindow.setBounds(100, 100, 720, 454);
    this.frameMainWindow.setDefaultCloseOperation(3);
    final GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{100, 29, 70, 63, 11, 131, 31, 8, 95, 0};
    gridBagLayout.rowHeights = new int[]{24, 1, 19, 1, 2, 25, 37, 23, 23, 23, 115, 0};
    gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0,
        Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0,
        Double.MIN_VALUE};
    this.frameMainWindow.getContentPane().setLayout(gridBagLayout);
    final JLabel lblMachine = new JLabel(this.messages.getString("Machine"));
    final GridBagConstraints gbclblMachine = new GridBagConstraints();
    gbclblMachine.fill = 1;
    gbclblMachine.insets = new Insets(0, 0, 5, 5);
    gbclblMachine.gridx = 0;
    gbclblMachine.gridy = 0;
    this.frameMainWindow.getContentPane().add(lblMachine, gbclblMachine);
    final JComboBox<Integer> comboBoxMN = new JComboBox<>();
    comboBoxMN.setEditable(false);
    comboBoxMN.setEnabled(false);
    comboBoxMN.setModel(
        new DefaultComboBoxModel<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
    final GridBagConstraints gbccomboBoxMN = new GridBagConstraints();
    gbccomboBoxMN.fill = 1;
    gbccomboBoxMN.insets = new Insets(0, 0, 5, 5);
    gbccomboBoxMN.gridx = 1;
    gbccomboBoxMN.gridy = 0;
    this.frameMainWindow.getContentPane().add(comboBoxMN, gbccomboBoxMN);
    final JButton btnCreate = new JButton(this.messages.getString("CreateEvent"));
    final GridBagConstraints gbcbtnCreate = new GridBagConstraints();
    gbcbtnCreate.fill = 1;
    gbcbtnCreate.insets = new Insets(0, 0, 5, 5);
    gbcbtnCreate.gridx = 2;
    gbcbtnCreate.gridy = 0;
    this.frameMainWindow.getContentPane().add(btnCreate, gbcbtnCreate);
    final JLabel lblONum = new JLabel(this.messages.getString("OrderNumber"));
    final GridBagConstraints gbclblONum = new GridBagConstraints();
    gbclblONum.fill = 1;
    gbclblONum.insets = new Insets(0, 0, 5, 5);
    gbclblONum.gridx = 0;
    gbclblONum.gridy = 1;
    this.frameMainWindow.getContentPane().add(lblONum, gbclblONum);
    final JTextField textFieldONum = new JTextField();
    textFieldONum.setEditable(false);
    textFieldONum.setColumns(10);
    final GridBagConstraints gbctextFieldONum = new GridBagConstraints();
    gbctextFieldONum.fill = 1;
    gbctextFieldONum.insets = new Insets(0, 0, 5, 5);
    gbctextFieldONum.gridwidth = 2;
    gbctextFieldONum.gridx = 1;
    gbctextFieldONum.gridy = 1;
    this.frameMainWindow.getContentPane().add(textFieldONum, gbctextFieldONum);
    final JLabel lblTender = new JLabel(this.messages.getString("Tender"));
    final GridBagConstraints gbclblTender = new GridBagConstraints();
    gbclblTender.fill = 1;
    gbclblTender.insets = new Insets(0, 0, 5, 5);
    gbclblTender.gridx = 3;
    gbclblTender.gridy = 0;
    this.frameMainWindow.getContentPane().add(lblTender, gbclblTender);
    final JComboBox<Employee> comboBoxTender = new JComboBox<>();
    comboBoxTender.setEnabled(false);
    AutoCompleteDecorator.decorate(comboBoxTender);
    final GridBagConstraints gbccomboBoxTender = new GridBagConstraints();
    gbccomboBoxTender.fill = 1;
    gbccomboBoxTender.insets = new Insets(0, 0, 5, 5);
    gbccomboBoxTender.gridwidth = 2;
    gbccomboBoxTender.gridx = 4;
    gbccomboBoxTender.gridy = 0;
    this.frameMainWindow.getContentPane().add(comboBoxTender, gbccomboBoxTender);
    final JComboBox<Employee> comboBoxLoader = new JComboBox<>();
    comboBoxLoader.setEnabled(false);
    AutoCompleteDecorator.decorate(comboBoxLoader);
    final GridBagConstraints gbccomboBoxLoader = new GridBagConstraints();
    gbccomboBoxLoader.fill = 1;
    gbccomboBoxLoader.insets = new Insets(0, 0, 5, 5);
    gbccomboBoxLoader.gridwidth = 2;
    gbccomboBoxLoader.gridx = 4;
    gbccomboBoxLoader.gridy = 1;
    this.frameMainWindow.getContentPane().add(comboBoxLoader, gbccomboBoxLoader);
    final JLabel lblDNum = new JLabel(this.messages.getString("DesignNumber"));
    final GridBagConstraints gbclblDNum = new GridBagConstraints();
    gbclblDNum.fill = 1;
    gbclblDNum.insets = new Insets(0, 0, 5, 5);
    gbclblDNum.gridx = 0;
    gbclblDNum.gridy = 2;
    this.frameMainWindow.getContentPane().add(lblDNum, gbclblDNum);
    final JTextField textFieldDNum = new JTextField();
    textFieldDNum.setEditable(false);
    textFieldDNum.setColumns(10);
    final GridBagConstraints gbctextFieldDNum = new GridBagConstraints();
    gbctextFieldDNum.fill = 1;
    gbctextFieldDNum.insets = new Insets(0, 0, 5, 5);
    gbctextFieldDNum.gridwidth = 2;
    gbctextFieldDNum.gridx = 1;
    gbctextFieldDNum.gridy = 2;
    this.frameMainWindow.getContentPane().add(textFieldDNum, gbctextFieldDNum);
    final JLabel lblLoader = new JLabel(this.messages.getString("Loader"));
    final GridBagConstraints gbclblLoader = new GridBagConstraints();
    gbclblLoader.fill = 1;
    gbclblLoader.insets = new Insets(0, 0, 5, 5);
    gbclblLoader.gridx = 3;
    gbclblLoader.gridy = 1;
    this.frameMainWindow.getContentPane().add(lblLoader, gbclblLoader);
    final JComboBox<Employee> comboBoxPuller = new JComboBox<>();
    comboBoxPuller.setEnabled(false);
    AutoCompleteDecorator.decorate(comboBoxPuller);
    final GridBagConstraints gbccomboBoxPuller = new GridBagConstraints();
    gbccomboBoxPuller.fill = 1;
    gbccomboBoxPuller.insets = new Insets(0, 0, 5, 5);
    gbccomboBoxPuller.gridwidth = 2;
    gbccomboBoxPuller.gridx = 4;
    gbccomboBoxPuller.gridy = 2;
    this.frameMainWindow.getContentPane().add(comboBoxPuller, gbccomboBoxPuller);
    final JLabel lblLoc = new JLabel(this.messages.getString("Location"));
    final GridBagConstraints gbclblLoc = new GridBagConstraints();
    gbclblLoc.fill = 1;
    gbclblLoc.insets = new Insets(0, 0, 5, 5);
    gbclblLoc.gridx = 0;
    gbclblLoc.gridy = 3;
    this.frameMainWindow.getContentPane().add(lblLoc, gbclblLoc);
    final JComboBox comboBoxLocations = new JComboBox();
    comboBoxLocations.setEnabled(false);
    comboBoxLocations.setModel(new DefaultComboBoxModel<>(
        new String[]{"Left Chest", "Right Chest", "Full Chest", "Full Back", "Rt Sleeve",
            "Left Sleeve", "Small Center Chest", "Small Upper Back", "On Pocket", "Above Pocket",
            "Neck Label"}));
    comboBoxLocations.setSelectedIndex(-1);
    final GridBagConstraints gbccomboBoxLocations = new GridBagConstraints();
    gbccomboBoxLocations.fill = 1;
    gbccomboBoxLocations.insets = new Insets(0, 0, 5, 5);
    gbccomboBoxLocations.gridwidth = 2;
    gbccomboBoxLocations.gridx = 1;
    gbccomboBoxLocations.gridy = 3;
    this.frameMainWindow.getContentPane().add(comboBoxLocations, gbccomboBoxLocations);
    final JLabel lblPuller = new JLabel(this.messages.getString("Puller"));
    final GridBagConstraints gbclblPuller = new GridBagConstraints();
    gbclblPuller.fill = 1;
    gbclblPuller.insets = new Insets(0, 0, 5, 5);
    gbclblPuller.gridx = 3;
    gbclblPuller.gridy = 2;
    this.frameMainWindow.getContentPane().add(lblPuller, gbclblPuller);
    final JComboBox<Employee> comboBoxCatcher = new JComboBox<>();
    comboBoxCatcher.setEnabled(false);
    AutoCompleteDecorator.decorate(comboBoxCatcher);
    final GridBagConstraints gbccomboBoxCatcher = new GridBagConstraints();
    gbccomboBoxCatcher.fill = 1;
    gbccomboBoxCatcher.insets = new Insets(0, 0, 5, 5);
    gbccomboBoxCatcher.gridwidth = 2;
    gbccomboBoxCatcher.gridx = 4;
    gbccomboBoxCatcher.gridy = 3;
    this.frameMainWindow.getContentPane().add(comboBoxCatcher, gbccomboBoxCatcher);
    final JLabel lblNumClrs = new JLabel(this.messages.getString("NumberOfColors"));
    final GridBagConstraints gbclblNumClrs = new GridBagConstraints();
    gbclblNumClrs.fill = 1;
    gbclblNumClrs.insets = new Insets(0, 0, 5, 5);
    gbclblNumClrs.gridx = 0;
    gbclblNumClrs.gridy = 4;
    this.frameMainWindow.getContentPane().add(lblNumClrs, gbclblNumClrs);
    final JTextField textFieldNumCol = new JTextField();
    textFieldNumCol.setEditable(false);
    textFieldNumCol.setColumns(10);
    final GridBagConstraints gbctextFieldNumCol = new GridBagConstraints();
    gbctextFieldNumCol.fill = 1;
    gbctextFieldNumCol.insets = new Insets(0, 0, 5, 5);
    gbctextFieldNumCol.gridwidth = 2;
    gbctextFieldNumCol.gridx = 1;
    gbctextFieldNumCol.gridy = 4;
    this.frameMainWindow.getContentPane().add(textFieldNumCol, gbctextFieldNumCol);
    final JLabel lblCatcher = new JLabel(this.messages.getString("Catcher"));
    final GridBagConstraints gbclblCatcher = new GridBagConstraints();
    gbclblCatcher.fill = 1;
    gbclblCatcher.insets = new Insets(0, 0, 5, 5);
    gbclblCatcher.gridx = 3;
    gbclblCatcher.gridy = 3;
    this.frameMainWindow.getContentPane().add(lblCatcher, gbclblCatcher);
    final JButton btnSetUp = new JButton(this.messages.getString("SetUp"));
    final GridBagConstraints gbcbtnSetUp = new GridBagConstraints();
    gbcbtnSetUp.fill = 1;
    gbcbtnSetUp.insets = new Insets(0, 0, 5, 5);
    gbcbtnSetUp.gridwidth = 2;
    gbcbtnSetUp.gridx = 0;
    gbcbtnSetUp.gridy = 6;
    this.frameMainWindow.getContentPane().add(btnSetUp, gbcbtnSetUp);
    final JButton btnApproved = new JButton(this.messages.getString("Approved"));
    final GridBagConstraints gbcbtnApproved = new GridBagConstraints();
    gbcbtnApproved.fill = 1;
    gbcbtnApproved.insets = new Insets(0, 0, 5, 5);
    gbcbtnApproved.gridwidth = 2;
    gbcbtnApproved.gridx = 2;
    gbcbtnApproved.gridy = 6;
    this.frameMainWindow.getContentPane().add(btnApproved, gbcbtnApproved);
    final JButton btnComplete = new JButton(this.messages.getString("Complete"));
    final GridBagConstraints gbcbtnComplete = new GridBagConstraints();
    gbcbtnComplete.fill = 1;
    gbcbtnComplete.insets = new Insets(0, 0, 5, 5);
    gbcbtnComplete.gridwidth = 2;
    gbcbtnComplete.gridx = 4;
    gbcbtnComplete.gridy = 6;
    this.frameMainWindow.getContentPane().add(btnComplete, gbcbtnComplete);
    final JTextField textFieldSetUp = new JTextField();
    textFieldSetUp.setEditable(false);
    textFieldSetUp.setColumns(10);
    final GridBagConstraints gbctextFieldSetUp = new GridBagConstraints();
    gbctextFieldSetUp.fill = 1;
    gbctextFieldSetUp.insets = new Insets(0, 0, 5, 5);
    gbctextFieldSetUp.gridwidth = 2;
    gbctextFieldSetUp.gridx = 0;
    gbctextFieldSetUp.gridy = 7;
    this.frameMainWindow.getContentPane().add(textFieldSetUp, gbctextFieldSetUp);
    final JTextField textFieldAprvd = new JTextField();
    textFieldAprvd.setEditable(false);
    textFieldAprvd.setColumns(10);
    final GridBagConstraints gbctextFieldAprvd = new GridBagConstraints();
    gbctextFieldAprvd.fill = 1;
    gbctextFieldAprvd.insets = new Insets(0, 0, 5, 5);
    gbctextFieldAprvd.gridwidth = 2;
    gbctextFieldAprvd.gridx = 2;
    gbctextFieldAprvd.gridy = 7;
    this.frameMainWindow.getContentPane().add(textFieldAprvd, gbctextFieldAprvd);
    final JTextField textFieldCmplte = new JTextField();
    textFieldCmplte.setEditable(false);
    textFieldCmplte.setColumns(10);
    final GridBagConstraints gbctextFieldCmplte = new GridBagConstraints();
    gbctextFieldCmplte.fill = 1;
    gbctextFieldCmplte.insets = new Insets(0, 0, 5, 5);
    gbctextFieldCmplte.gridwidth = 2;
    gbctextFieldCmplte.gridx = 4;
    gbctextFieldCmplte.gridy = 7;
    this.frameMainWindow.getContentPane().add(textFieldCmplte, gbctextFieldCmplte);
    final JButton btnHold = new JButton(this.messages.getString("Hold"));
    final GridBagConstraints gbcbtnHold = new GridBagConstraints();
    gbcbtnHold.fill = 1;
    gbcbtnHold.insets = new Insets(0, 0, 5, 0);
    gbcbtnHold.gridx = 8;
    gbcbtnHold.gridy = 7;
    this.frameMainWindow.getContentPane().add(btnHold, gbcbtnHold);
    final JTextField textFieldSUAvg = new JTextField();
    textFieldSUAvg.setEditable(false);
    textFieldSUAvg.setColumns(10);
    final GridBagConstraints gbctextFieldSUAvg = new GridBagConstraints();
    gbctextFieldSUAvg.fill = 1;
    gbctextFieldSUAvg.insets = new Insets(0, 0, 5, 5);
    gbctextFieldSUAvg.gridwidth = 3;
    gbctextFieldSUAvg.gridx = 0;
    gbctextFieldSUAvg.gridy = 8;
    this.frameMainWindow.getContentPane().add(textFieldSUAvg, gbctextFieldSUAvg);
    final JTextField textFieldSPH = new JTextField();
    textFieldSPH.setEditable(false);
    textFieldSPH.setColumns(10);
    final GridBagConstraints gbctextFieldSPH = new GridBagConstraints();
    gbctextFieldSPH.fill = 1;
    gbctextFieldSPH.insets = new Insets(0, 0, 5, 5);
    gbctextFieldSPH.gridwidth = 3;
    gbctextFieldSPH.gridx = 3;
    gbctextFieldSPH.gridy = 8;
    this.frameMainWindow.getContentPane().add(textFieldSPH, gbctextFieldSPH);
    final JComboBox<Order> comboBoxCurrentJobs = new JComboBox<Order>();
    comboBoxCurrentJobs.setRenderer(new OrderListCellRenderer());
    final GridBagConstraints gbccomboBoxCurrentJobs = new GridBagConstraints();
    gbccomboBoxCurrentJobs.fill = 1;
    gbccomboBoxCurrentJobs.insets = new Insets(0, 0, 5, 0);
    gbccomboBoxCurrentJobs.gridx = 8;
    gbccomboBoxCurrentJobs.gridy = 8;
    this.frameMainWindow.getContentPane().add(comboBoxCurrentJobs, gbccomboBoxCurrentJobs);
    final JButton btnComment = new JButton(this.messages.getString("Comment"));
    final GridBagConstraints gbcbtnComment = new GridBagConstraints();
    gbcbtnComment.fill = 1;
    gbcbtnComment.insets = new Insets(0, 0, 5, 5);
    gbcbtnComment.gridwidth = 2;
    gbcbtnComment.gridx = 0;
    gbcbtnComment.gridy = 9;
    this.frameMainWindow.getContentPane().add(btnComment, gbcbtnComment);
    final JButton btnEdit = new JButton(this.messages.getString("Edit"));
    final GridBagConstraints gbcbtnEdit = new GridBagConstraints();
    gbcbtnEdit.fill = 1;
    gbcbtnEdit.insets = new Insets(0, 0, 5, 5);
    gbcbtnEdit.gridwidth = 2;
    gbcbtnEdit.gridx = 2;
    gbcbtnEdit.gridy = 9;
    this.frameMainWindow.getContentPane().add(btnEdit, gbcbtnEdit);
    final JButton btnSubmit = new JButton(this.messages.getString("Submit"));
    final GridBagConstraints gbcbtnSubmit = new GridBagConstraints();
    gbcbtnSubmit.fill = 1;
    gbcbtnSubmit.insets = new Insets(0, 0, 5, 5);
    gbcbtnSubmit.gridwidth = 2;
    gbcbtnSubmit.gridx = 4;
    gbcbtnSubmit.gridy = 9;
    this.frameMainWindow.getContentPane().add(btnSubmit, gbcbtnSubmit);
    final JButton btnAlreadyApproved = new JButton(this.messages.getString("AlreadyApproved?"));
    final GridBagConstraints gbcbtnAlreadyApproved = new GridBagConstraints();
    gbcbtnAlreadyApproved.fill = 1;
    gbcbtnAlreadyApproved.insets = new Insets(0, 0, 5, 0);
    gbcbtnAlreadyApproved.gridx = 8;
    gbcbtnAlreadyApproved.gridy = 9;
    this.frameMainWindow.getContentPane().add(btnAlreadyApproved, gbcbtnAlreadyApproved);
    final JButton btnSetTimeTo6AM = new JButton(this.messages.getString("SetUP6"));
    final GridBagConstraints gbcbtnSetTimeTo6AM = new GridBagConstraints();
    gbcbtnSetTimeTo6AM.insets = new Insets(0, 0, 5, 0);
    gbcbtnSetTimeTo6AM.fill = 1;
    gbcbtnSetTimeTo6AM.gridx = 8;
    gbcbtnSetTimeTo6AM.gridy = 5;
    this.frameMainWindow.getContentPane().add(btnSetTimeTo6AM, gbcbtnSetTimeTo6AM);
    final JButton btnSetTimeTo3PM = new JButton(this.messages.getString("SetUP3"));
    final GridBagConstraints gbcbtnSetTimeTo3PM = new GridBagConstraints();
    gbcbtnSetTimeTo3PM.insets = new Insets(0, 0, 5, 0);
    gbcbtnSetTimeTo3PM.fill = 1;
    gbcbtnSetTimeTo3PM.gridx = 8;
    gbcbtnSetTimeTo3PM.gridy = 6;
    this.frameMainWindow.getContentPane().add(btnSetTimeTo3PM, gbcbtnSetTimeTo3PM);
    final JScrollPane sp = new JScrollPane();
    final JTextArea textAreaComments = new JTextArea();
    textAreaComments.setEditable(false);
    sp.setViewportView(textAreaComments);
    final GridBagConstraints gbcsp = new GridBagConstraints();
    gbcsp.fill = 1;
    gbcsp.gridwidth = 9;
    gbcsp.gridx = 0;
    gbcsp.gridy = 10;
    this.frameMainWindow.getContentPane().add(sp, gbcsp);
    comboBoxTender.setModel(
        new DefaultComboBoxModel<>(new Vector<>(this.employees)));
    comboBoxLoader.setModel(
        new DefaultComboBoxModel<>(new Vector<>(this.employees)));
    comboBoxPuller.setModel(
        new DefaultComboBoxModel<>(new Vector<>(this.employees)));
    comboBoxCatcher.setModel(
        new DefaultComboBoxModel<>(new Vector<>(this.employees)));
    comboBoxTender.setSelectedItem(null);
    comboBoxLoader.setSelectedItem(null);
    comboBoxPuller.setSelectedItem(null);
    comboBoxCatcher.setSelectedItem(null);
    final JMenuBar menuBar = new JMenuBar();
    this.frameMainWindow.setJMenuBar(menuBar);
    final JMenu mnFile = new JMenu(this.messages.getString("File"));
    menuBar.add(mnFile);
    final JMenuItem mntmExit = new JMenuItem(this.messages.getString("Quit"));
    mntmExit.addActionListener(e -> {
      if (JOptionPane.showConfirmDialog(null, this.messages.getString("QuitSure"),
          this.messages.getString("Quit?"), 0) == 0) {
        this.frameMainWindow.dispose();
      }
    });
    mnFile.add(mntmExit);
    final JMenu mnEdit = new JMenu(this.messages.getString("Edit"));
    menuBar.add(mnEdit);
    final JMenuItem mntmSettings = new JMenuItem(this.messages.getString("Settings"));
    mntmSettings.addActionListener(evt -> SettingsDialog.main(this, this.messages));
    mnEdit.add(mntmSettings);
    final JMenuItem mntmDeleteOrder = new JMenuItem(this.messages.getString("Delete"));
    final JComboBox comboBox;
    mntmDeleteOrder.addActionListener(evt -> {
      if (comboBox.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else if (
          JOptionPane.showConfirmDialog(this.frameMainWindow, this.messages.getString("DeleteSure"),
              this.messages.getString("DeleteSure"), 0) == 0) {
        comboBox.removeItem(comboBox.getSelectedItem());
      }
    });
    mnEdit.add(mntmDeleteOrder);
    final JComboBox orders;
    btnCreate.addActionListener(evt -> {
      if (JOptionPane.showConfirmDialog(null, this.messages.getString("CreateNewEvent"),
          this.messages.getString("CreateEvent?"), 0) == 0) {
        CreateScreenPrintOrderDialog.main(orders, this.machineNames, this.messages);
      }
    });
    final JComboBox comboBox2;
    final JTextComponent textComponent;
    Order order;
    DateTime now;
    final JTextComponent textComponent2;
    btnSetUp.addActionListener(evt -> {
      if (comboBox2.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox2.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else if (textComponent.isEditable()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderIsBeingEdited"));
      } else {
        order = (Order) comboBox2.getSelectedItem();
        if (!order.isAlreadyApproved()) {
          now = new DateTime();
          textComponent2.setText(MainWindow.DF.print(now));
          order.setDSetUp(now);
        } else {
          JOptionPane.showMessageDialog(null, this.messages.getString("ALREADYAPPROVED"));
        }
      }
    });
    final JComboBox comboBox3;
    Order order2;
    DateTimeFormatter formatter;
    DateTime am6;
    final JTextComponent textComponent3;
    btnSetTimeTo6AM.addActionListener(evt -> {
      if (comboBox3.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox3.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else {
        order2 = (Order) comboBox3.getSelectedItem();
        if (!order2.isAlreadyApproved()) {
          if (JOptionPane.showConfirmDialog(null, this.messages.getString("6am?"),
              this.messages.getString("6am?"), 2) == 0) {
            formatter = DateTimeFormat.forPattern("HH:mm");
            am6 = formatter.parseDateTime("06:00").withDate(new LocalDate());
            textComponent3.setText(MainWindow.DF.print(am6));
            order2.setDSetUp(am6);
          }
        } else {
          JOptionPane.showMessageDialog(null, this.messages.getString("ALREADYAPPROVED"));
        }
      }
    });
    final JComboBox comboBox4;
    Order order3;
    DateTimeFormatter formatter2;
    DateTime pm3;
    final JTextComponent textComponent4;
    btnSetTimeTo3PM.addActionListener(evt -> {
      if (comboBox4.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox4.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else {
        order3 = (Order) comboBox4.getSelectedItem();
        if (!order3.isAlreadyApproved()) {
          if (JOptionPane.showConfirmDialog(null, this.messages.getString("6am?"),
              this.messages.getString("3pm?"), 2) == 0) {
            formatter2 = DateTimeFormat.forPattern("HH:mm");
            pm3 = formatter2.parseDateTime("15:00").withDate(new LocalDate());
            textComponent4.setText(MainWindow.DF.print(pm3));
            order3.setDSetUp(pm3);
          }
        } else {
          JOptionPane.showMessageDialog(null, this.messages.getString("ALREADYAPPROVED"));
        }
      }
    });
    final JComboBox comboBox5;
    final JTextComponent textComponent5;
    Order order4;
    DateTime now2;
    final JTextComponent textComponent6;
    int minutes;
    final JTextComponent textComponent7;
    btnApproved.addActionListener(evt -> {
      if (comboBox5.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox5.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else if (textComponent5.isEditable()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderIsBeingEdited"));
      } else {
        order4 = (Order) comboBox5.getSelectedItem();
        now2 = new DateTime();
        textComponent6.setText(MainWindow.DF.print(now2));
        order4.setDApproved(now2);
        if (!order4.isAlreadyApproved()) {
          minutes = this.calculateMinutes(order4, "approved");
          order4.setSuMinutes(minutes);
          order4.setMpc(minutes / order4.getNumberOfColors());
          textComponent7.setText(this.messages.getString("AverageOf") + " " + order4.getMpc() + " "
              + this.messages.getString("MinutesPerColor"));
        }
      }
    });
    final JComboBox comboBox6;
    final JTextComponent textComponent8;
    DateTime now3;
    final JTextComponent textComponent9;
    Order order5;
    int minutes2;
    String numshirts;
    int numshirtsi;
    float averageUnits;
    final JTextComponent textComponent10;
    btnComplete.addActionListener(evt -> {
      if (comboBox6.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox6.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else if (textComponent8.isEditable()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderIsBeingEdited"));
      } else {
        now3 = new DateTime();
        textComponent9.setText(MainWindow.DF.print(now3));
        order5 = (Order) comboBox6.getSelectedItem();
        order5.setDComplete(now3);
        minutes2 = this.calculateMinutes(order5, "complete");
        order5.setpMinutes(minutes2);
        try {
          numshirts = JOptionPane.showInputDialog(this.frameMainWindow,
              this.messages.getString("HowManyShirts"));
          numshirtsi = Integer.parseInt(numshirts);
          order5.setQuantity(numshirtsi);
          try {
            averageUnits = order5.getQuantity() / (float) minutes2 * 60.0f;
            order5.setAvgU((int) averageUnits);
          } catch (ArithmeticException ae) {
            MainWindow.LOGGER.error("Averaging number of shirts per hour", ae);
          }
          textComponent10.setText(
              this.messages.getString("AverageOf") + " " + order5.getAvgU() + " "
                  + this.messages.getString("ShirtsPerHour"));
        } catch (NumberFormatException nfe) {
          JOptionPane.showMessageDialog(null, this.messages.getString("ErrorNumShirtsInvalid"));
          MainWindow.LOGGER.error("Number of shirts invalid", nfe);
        }
      }
    });
    final JComboBox comboBox7;
    Order order6;
    JComboBox<Object> comboBox8;
    JComboBox<?> onHoldReason;
    final DefaultComboBoxModel model;
    JTextField comments;
    Object[] message;
    final JComponent component;
    final JComponent component2;
    final JComponent component3;
    final JComponent component4;
    Period period;
    int minutes3;
    btnHold.addActionListener(evt -> {
      if (comboBox7.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else {
        order6 = (Order) comboBox7.getSelectedItem();
        if (!order6.isOnHold()) {
          onHoldReason = (comboBox8 = new JComboBox<Object>());
          new DefaultComboBoxModel(
              new String[]{"Sample/Cust Approval", "Graphic Error", "Apparel Error",
                  "Press Error", "Other"});
          comboBox8.setModel((ComboBoxModel<?>) model);
          comments = new JTextField();
          message = new Object[]{this.messages.getString("Reason"), onHoldReason,
              this.messages.getString("Comments"), comments};
          if (JOptionPane.showConfirmDialog(this.frameMainWindow, message,
              this.messages.getString("Hold?"), 2) == 0) {
            order6.setOnHold(true);
            order6.setHoldStart(new DateTime());
            if (order6.getDApproved() == null) {
              order6.addHoldReasonSU(onHoldReason.getSelectedItem() + " " + comments.getText());
            } else {
              order6.addHoldReasonP(onHoldReason.getSelectedItem() + " " + comments.getText());
            }
            component.setBackground(Color.RED);
            component2.setBackground(Color.RED);
            component3.setBackground(Color.RED);
            component4.setBackground(Color.RED);
          }
        } else {
          order6.setOnHold(false);
          order6.setHoldEnd(new DateTime());
          period = new Period(order6.getHoldStart(), order6.getHoldEnd());
          minutes3 = period.getHours() * 60 + period.getMinutes();
          component.setBackground(null);
          component2.setBackground(null);
          component3.setBackground(null);
          component4.setBackground(null);
          if (order6.getDApproved() == null) {
            order6.setSUohMinutes(order6.getSUohMinutes() + minutes3);
            order6.setTotalHoldMinutes(order6.getTotalHoldMinutes() + minutes3);
            order6.addHoldTimeSU(new Interval(order6.getHoldStart(), order6.getHoldEnd()));
          } else {
            order6.setPohMinutes(order6.getPohMinutes() + minutes3);
            order6.setTotalHoldMinutes(order6.getTotalHoldMinutes() + minutes3);
            order6.addHoldTimeP(new Interval(order6.getHoldStart(), order6.getHoldEnd()));
          }
        }
      }
    });
    final JComboBox comboBox9;
    Order order7;
    String comment;
    DateTime now4;
    final JTextComponent textComponent11;
    StringBuilder sb;
    btnComment.addActionListener(evt -> {
      if (comboBox9.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else {
        order7 = (Order) comboBox9.getSelectedItem();
        comment = JOptionPane.showInputDialog(this.frameMainWindow,
            this.messages.getString("Comment"));
        if (comment != null) {
          now4 = new DateTime();
          order7.addComment(now4, comment);
          textComponent11.setText("");
          sb = new StringBuilder();
          order7.getComments().forEach((k, v) -> sb.append(MainWindow.DF.print(k) + "" + v));
          textComponent11.setText(sb.toString());
        }
      }
    });
    final JComboBox comboBox10;
    final JTextComponent textComponent12;
    boolean passwordCorrect;
    JPasswordField passwordField;
    Object[] message2;
    Map<String, String> password;
    String pass;
    String salt;
    Order order8;
    final JComboBox<Employee> comboBox11;
    final JComboBox<Employee> comboBox12;
    final JComboBox<Employee> comboBox13;
    final JComboBox<Employee> comboBox14;
    final Iterator<Employee> iterator;
    Employee employee;
    final JComboBox comboBox15;
    final JTextComponent textComponent13;
    final JTextComponent textComponent14;
    final JComboBox comboBox16;
    final JTextComponent textComponent15;
    final JTextComponent textComponent16;
    final JTextComponent textComponent17;
    final JComponent component5;
    final JComponent component6;
    boolean test;
    HashMap<String, HashMap<Object, Object>> editedValues;
    Order order9;
    HashMap<Object, Object> values;
    HashMap<Object, Object> values2;
    HashMap<Object, Object> values3;
    HashMap<Object, Object> values4;
    HashMap<Object, Object> values5;
    HashMap<Object, Object> values6;
    HashMap<Object, Object> values7;
    HashMap<Object, Object> values8;
    HashMap<Object, Object> values9;
    HashMap<Object, Object> values10;
    HashMap<Object, Object> values11;
    HashMap<Object, Object> values12;
    HashMap<Object, Object> values13;
    HashMap<Object, Object> values14;
    HashMap<Object, Object> values15;
    int minutes4;
    final JTextComponent textComponent18;
    int minutes5;
    int newQuantity;
    HashMap<Object, Object> values16;
    float averageUnits2;
    final JTextComponent textComponent19;
    btnEdit.addActionListener(evt -> {
      if (comboBox10.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (!textComponent12.isEditable()) {
        passwordCorrect = false;
        passwordField = new JPasswordField(20);
        message2 = new Object[]{this.messages.getString("Password"), passwordField};
        if (JOptionPane.showConfirmDialog(this.frameMainWindow, message2,
            this.messages.getString("Password"), 2) == 0) {
          password = SQL.loadPassword();
          pass = password.get("password");
          salt = password.get("salt");
          if (PasswordUtils.verifyUserPassword(new String(passwordField.getPassword()), pass,
              salt)) {
            passwordCorrect = true;
          } else {
            JOptionPane.showMessageDialog(this.frameMainWindow,
                this.messages.getString("ErrorPasswordInvalid"));
          }
        }
        if (passwordCorrect) {
          order8 = (Order) comboBox10.getSelectedItem();
          this.employees = HTML.getEmployees();
          comboBox11.removeAllItems();
          comboBox12.removeAllItems();
          comboBox13.removeAllItems();
          comboBox14.removeAllItems();
          this.employees.iterator();
          while (iterator.hasNext()) {
            employee = iterator.next();
            comboBox11.addItem(employee);
            comboBox12.addItem(employee);
            comboBox13.addItem(employee);
            comboBox14.addItem(employee);
          }
          comboBox11.getModel().setSelectedItem((order8 != null) ? order8.getTender() : null);
          comboBox12.getModel().setSelectedItem((order8 != null) ? order8.getLoader() : null);
          comboBox13.getModel().setSelectedItem((order8 != null) ? order8.getPuller() : null);
          comboBox14.getModel().setSelectedItem((order8 != null) ? order8.getCatcher() : null);
          comboBox15.setEnabled(true);
          textComponent13.setEditable(true);
          textComponent14.setEditable(true);
          comboBox16.setEnabled(true);
          textComponent15.setEditable(true);
          textComponent12.setEditable(true);
          textComponent16.setEditable(true);
          textComponent17.setEditable(true);
          comboBox11.setEnabled(true);
          comboBox12.setEnabled(true);
          comboBox13.setEnabled(true);
          comboBox14.setEnabled(true);
          comboBox10.setEnabled(false);
          component5.setBackground(Color.RED);
          component6.setBackground(Color.RED);
        }
      } else {
        test = false;
        try {
          Integer.parseInt(textComponent13.getText().trim());
        } catch (NumberFormatException nfe4) {
          JOptionPane.showMessageDialog(null, this.messages.getString("ErrorOrderNumberInvalid"));
          test = true;
        }
        try {
          Integer.parseInt(textComponent14.getText().trim());
        } catch (NumberFormatException nfe5) {
          JOptionPane.showMessageDialog(null, this.messages.getString("ErrorDesignNumberInvalid"));
          test = true;
        }
        try {
          Integer.parseInt(textComponent15.getText().trim());
        } catch (NumberFormatException nfe6) {
          JOptionPane.showMessageDialog(null, this.messages.getString("ErrorColorsNumberInvalid"));
          test = true;
        }
        try {
          if (!textComponent12.getText().equals("") && !textComponent12.getText()
              .equals(this.messages.getString("ALREADYAPPROVED"))) {
            DateTime.parse(textComponent12.getText(), MainWindow.DF);
          }
        } catch (IllegalArgumentException iae4) {
          JOptionPane.showMessageDialog(null, this.messages.getString("ErrorTimeFormat"));
          test = true;
        }
        try {
          if (!textComponent16.getText().equals("")) {
            DateTime.parse(textComponent16.getText(), MainWindow.DF);
          }
        } catch (IllegalArgumentException iae5) {
          JOptionPane.showMessageDialog(null, this.messages.getString("ErrorTimeFormat"));
          test = true;
        }
        try {
          if (!textComponent17.getText().equals("")) {
            DateTime.parse(textComponent17.getText(), MainWindow.DF);
          }
        } catch (IllegalArgumentException iae6) {
          JOptionPane.showMessageDialog(null, this.messages.getString("ErrorTimeFormat"));
          test = true;
        }
        if (!test) {
          comboBox15.setEnabled(false);
          textComponent13.setEditable(false);
          textComponent14.setEditable(false);
          comboBox16.setEnabled(false);
          textComponent15.setEditable(false);
          textComponent12.setEditable(false);
          textComponent16.setEditable(false);
          textComponent17.setEditable(false);
          comboBox11.setEnabled(false);
          comboBox12.setEnabled(false);
          comboBox13.setEnabled(false);
          comboBox14.setEnabled(false);
          comboBox10.setEnabled(true);
          component5.setBackground(null);
          component6.setBackground(null);
          editedValues = new HashMap<String, HashMap<Object, Object>>();
          order9 = (Order) comboBox10.getSelectedItem();
          if (order9.getMachine() != (int) comboBox15.getSelectedItem()) {
            values = new HashMap<>();
            values.put(order9.getMachine(), comboBox15.getSelectedItem());
            editedValues.put("MachineNumber", values);
            order9.setMachine((int) comboBox15.getSelectedItem());
          }
          if (order9.getOrderNumber() != Integer.parseInt(textComponent13.getText().trim())) {
            values2 = new HashMap<>();
            values2.put(order9.getOrderNumber(),
                Integer.parseInt(textComponent13.getText().trim()));
            editedValues.put("OrderNumber", values2);
            order9.setOrderNumber(Integer.parseInt(textComponent13.getText().trim()));
          }
          if (order9.getDesignNumber() != Integer.parseInt(textComponent14.getText().trim())) {
            values3 = new HashMap<Object, Object>();
            values3.put(order9.getDesignNumber(),
                Integer.parseInt(textComponent14.getText().trim()));
            editedValues.put("DesignNumber", values3);
            order9.setDesignNumber(Integer.parseInt(textComponent14.getText().trim()));
          }
          if (!order9.getLocation().equals(comboBox16.getSelectedItem().toString())) {
            values4 = new HashMap<Object, Object>();
            values4.put(order9.getLocation(), comboBox16.getSelectedItem());
            editedValues.put("Location", values4);
            order9.setLocation(comboBox16.getSelectedItem().toString());
          }
          if (order9.getNumberOfColors() != Integer.parseInt(textComponent15.getText().trim())) {
            values5 = new HashMap<Object, Object>();
            values5.put(order9.getNumberOfColors(),
                Integer.parseInt(textComponent15.getText().trim()));
            editedValues.put("NumberOfColors", values5);
            order9.setNumberOfColors(Integer.parseInt(textComponent15.getText().trim()));
          }
          if (comboBox11.getSelectedItem() != null && !order9.getTender()
              .equals(comboBox11.getSelectedItem())) {
            values6 = new HashMap<>();
            values6.put(order9.getTender(), comboBox11.getSelectedItem());
            editedValues.put("Tender", values6);
            order9.setTender((Employee) comboBox11.getSelectedItem());
          }
          if (comboBox12.getSelectedItem() != null && !order9.getLoader()
              .equals(comboBox12.getSelectedItem())) {
            values7 = new HashMap<>();
            values7.put(order9.getLoader(), comboBox12.getSelectedItem());
            editedValues.put("Loader", values7);
            order9.setLoader((Employee) comboBox12.getSelectedItem());
          }
          if (comboBox13.getSelectedItem() != null && !order9.getPuller()
              .equals(comboBox13.getSelectedItem())) {
            values8 = new HashMap<>();
            values8.put(order9.getPuller(), comboBox13.getSelectedItem());
            editedValues.put("Puller", values8);
            order9.setPuller((Employee) comboBox13.getSelectedItem());
          }
          if (comboBox14.getSelectedItem() != null && !order9.getCatcher()
              .equals(comboBox14.getSelectedItem())) {
            values9 = new HashMap<>();
            values9.put(order9.getCatcher(), comboBox14.getSelectedItem());
            editedValues.put("Catcher", values9);
            order9.setCatcher((Employee) comboBox14.getSelectedItem());
          }
          if (!textComponent12.getText().equals("")) {
            if (textComponent12.getText().equals(this.messages.getString("ALREADYAPPROVED"))) {
              order9.setAlreadyApproved(true);
            } else {
              try {
                if (order9.getDSetUp() != null) {
                  if (!order9.getDSetUp()
                      .equals(MainWindow.DF.parseDateTime(textComponent12.getText()))) {
                    values10 = new HashMap<Object, Object>();
                    values10.put(order9.getDSetUp(),
                        MainWindow.DF.parseDateTime(textComponent12.getText()));
                    editedValues.put("SetUp", values10);
                    order9.setDSetUp(MainWindow.DF.parseDateTime(textComponent12.getText()));
                  }
                } else {
                  values11 = new HashMap<Object, Object>();
                  values11.put(null, MainWindow.DF.parseDateTime(textComponent12.getText()));
                  editedValues.put("SetUp", values11);
                  order9.setDSetUp(MainWindow.DF.parseDateTime(textComponent12.getText()));
                }
              } catch (IllegalArgumentException iae) {
                MainWindow.LOGGER.error("Editing order setup time", iae);
              }
            }
          } else {
            order9.setDSetUp(null);
          }
          if (!textComponent16.getText().equals("")) {
            try {
              if (order9.getDApproved() != null) {
                if (!order9.getDApproved()
                    .equals(MainWindow.DF.parseDateTime(textComponent16.getText()))) {
                  values12 = new HashMap<Object, Object>();
                  values12.put(order9.getDApproved(),
                      MainWindow.DF.parseDateTime(textComponent16.getText()));
                  editedValues.put("Approved", values12);
                  order9.setDApproved(MainWindow.DF.parseDateTime(textComponent16.getText()));
                }
              } else {
                values13 = new HashMap<Object, Object>();
                values13.put(null, MainWindow.DF.parseDateTime(textComponent16.getText()));
                editedValues.put("Approved", values13);
                order9.setDApproved(MainWindow.DF.parseDateTime(textComponent16.getText()));
              }
            } catch (IllegalArgumentException iae2) {
              MainWindow.LOGGER.error("Editing order approved time", iae2);
            }
          } else {
            order9.setDApproved(null);
          }
          if (!textComponent17.getText().equals("")) {
            try {
              if (order9.getDComplete() != null) {
                if (!order9.getDComplete()
                    .equals(MainWindow.DF.parseDateTime(textComponent17.getText()))) {
                  values14 = new HashMap<Object, Object>();
                  values14.put(order9.getDComplete(),
                      MainWindow.DF.parseDateTime(textComponent17.getText()));
                  editedValues.put("Complete", values14);
                  order9.setDComplete(MainWindow.DF.parseDateTime(textComponent17.getText()));
                }
              } else {
                values15 = new HashMap<Object, Object>();
                values15.put(null, MainWindow.DF.parseDateTime(textComponent17.getText()));
                editedValues.put("Complete", values15);
                order9.setDComplete(MainWindow.DF.parseDateTime(textComponent17.getText()));
              }
            } catch (IllegalArgumentException iae3) {
              MainWindow.LOGGER.error("Editing order complete time", iae3);
            }
          } else {
            order9.setDComplete(null);
          }
          if (!textComponent12.getText().equals("") && !textComponent12.getText()
              .equals(this.messages.getString("ALREADYAPPROVED")) && !textComponent16.getText()
              .equals("")) {
            minutes4 = this.calculateMinutes(order9, "approved");
            order9.setSuMinutes(minutes4);
            order9.setMpc(minutes4 / order9.getNumberOfColors());
            textComponent18.setText(
                this.messages.getString("AverageOf") + " " + order9.getMpc() + " "
                    + this.messages.getString("MinutesPerColor"));
          }
          if (!textComponent16.getText().equals("") && !textComponent17.getText().equals("")) {
            minutes5 = this.calculateMinutes(order9, "complete");
            order9.setpMinutes(minutes5);
            if (order9.getQuantity() < 1) {
              try {
                order9.setQuantity(Integer.parseInt(
                    JOptionPane.showInputDialog(this.frameMainWindow,
                        this.messages.getString("HowManyShirts"))));
              } catch (NumberFormatException nfe2) {
                JOptionPane.showMessageDialog(null,
                    this.messages.getString("ErrorNumShirtsInvalid"));
                MainWindow.LOGGER.error("Number of shirts invalid", nfe2);
              }
            } else if (JOptionPane.showConfirmDialog(null,
                this.messages.getString("QuantityIsCurrently") + " " + order9.getQuantity() + " "
                    + this.messages.getString("ChangeQuantity?"),
                this.messages.getString("ChangeQuantity?"), 0) == 0) {
              try {
                newQuantity = Integer.parseInt(JOptionPane.showInputDialog(this.frameMainWindow,
                    this.messages.getString("HowManyShirts")));
                values16 = new HashMap<Object, Object>();
                values16.put(order9.getQuantity(), newQuantity);
                editedValues.put("Quantity", values16);
                order9.setQuantity(newQuantity);
              } catch (NumberFormatException nfe3) {
                JOptionPane.showMessageDialog(null,
                    this.messages.getString("ErrorNumShirtsInvalid"));
                MainWindow.LOGGER.error("Number of shirts invalid", nfe3);
              }
            }
            try {
              averageUnits2 = order9.getQuantity() / (float) minutes5 * 60.0f;
              order9.setAvgU((int) averageUnits2);
              textComponent19.setText(
                  this.messages.getString("AverageOf") + " " + order9.getAvgU() + " "
                      + this.messages.getString("ShirtsPerHour"));
            } catch (ArithmeticException ae2) {
              MainWindow.LOGGER.error("Averaging number of shirts per hour", ae2);
            }
          }
          order9.addEditedValues(editedValues);
        }
      }
    });
    final JComboBox comboBox17;
    int reply;
    Order order10;
    HashMap<Object, Object> values17;
    HashMap<String, HashMap<Object, Object>> map;
    final JTextComponent textComponent20;
    final AbstractButton abstractButton;
    btnAlreadyApproved.addActionListener(evt -> {
      if (comboBox17.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox17.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else {
        reply = JOptionPane.showConfirmDialog(null, this.messages.getString("AlreadyApproved?"),
            this.messages.getString("AlreadyApproved?"), 0);
        if (reply == 0) {
          order10 = (Order) comboBox17.getSelectedItem();
          values17 = new HashMap<Object, Object>();
          values17.put(null, "AlreadyApproved");
          map = new HashMap<String, HashMap<Object, Object>>();
          map.put("SetUp", values17);
          order10.addEditedValues(map);
          order10.setAlreadyApproved(true);
          textComponent20.setText(this.messages.getString("ALREADYAPPROVED"));
          abstractButton.doClick();
        }
      }
    });
    final JComboBox comboBox18;
    final JTextComponent textComponent21;
    final JTextComponent textComponent22;
    final JTextComponent textComponent23;
    final JTextComponent textComponent24;
    final JComboBox comboBox19;
    final JTextComponent textComponent25;
    final JTextComponent textComponent26;
    final JTextComponent textComponent27;
    final JTextComponent textComponent28;
    final JTextComponent textComponent29;
    final JComponent component7;
    final JComponent component8;
    final JComponent component9;
    final JComponent component10;
    final JComboBox comboBox20;
    final JComboBox comboBox21;
    final JComboBox comboBox22;
    final JComboBox comboBox23;
    final JComboBox comboBox24;
    Order order11;
    StringBuilder sb2;
    comboBoxCurrentJobs.addItemListener(evt -> {
      comboBox18.setSelectedItem(null);
      textComponent21.setText("");
      textComponent22.setText("");
      textComponent23.setText("");
      textComponent24.setText("");
      comboBox19.setSelectedIndex(-1);
      textComponent25.setText("");
      textComponent26.setText("");
      textComponent27.setText("");
      textComponent28.setText("");
      textComponent29.setText("");
      component7.setBackground(null);
      component8.setBackground(null);
      component9.setBackground(null);
      component10.setBackground(null);
      comboBox20.setSelectedItem(null);
      comboBox21.setSelectedItem(null);
      comboBox22.setSelectedItem(null);
      comboBox23.setSelectedItem(null);
      try {
        order11 = (Order) comboBox24.getSelectedItem();
        comboBox18.setSelectedItem(order11.getMachine());
        textComponent26.setText(String.valueOf(order11.getOrderNumber()));
        textComponent24.setText(String.valueOf(order11.getDesignNumber()));
        comboBox19.setSelectedItem(order11.getLocation());
        textComponent25.setText(String.valueOf(order11.getNumberOfColors()));
        comboBox20.getModel().setSelectedItem(order11.getTender());
        comboBox21.getModel().setSelectedItem(order11.getLoader());
        comboBox22.getModel().setSelectedItem(order11.getPuller());
        comboBox23.getModel().setSelectedItem(order11.getCatcher());
        if (!order11.isAlreadyApproved() && order11.getDSetUp() != null) {
          textComponent27.setText(MainWindow.DF.print(order11.getDSetUp()));
        } else if (order11.isAlreadyApproved()) {
          textComponent27.setText(this.messages.getString("ALREADYAPPROVED"));
        }
        if (order11.getDApproved() != null) {
          textComponent22.setText(MainWindow.DF.print(order11.getDApproved()));
        }
        if (order11.getDComplete() != null) {
          textComponent23.setText(MainWindow.DF.print(order11.getDComplete()));
        }
        if (order11.getComments().size() > 0) {
          textComponent21.setText("");
          sb2 = new StringBuilder();
          order11.getComments().forEach((k, v) -> sb2.append(MainWindow.DF.print(k) + "" + v));
          textComponent21.setText(sb2.toString());
        }
        if (!textComponent27.getText().equals("") && !textComponent22.getText().equals("")) {
          textComponent29.setText(this.messages.getString("AverageOf") + "" + order11.getMpc() + ""
              + this.messages.getString("MinutesPerColor"));
        }
        if (!textComponent22.getText().equals("") && !textComponent23.getText().equals("")) {
          textComponent28.setText(this.messages.getString("AverageOf") + "" + order11.getAvgU() + ""
              + this.messages.getString("ShirtsPerHour"));
        }
        if (order11.isOnHold()) {
          component7.setBackground(Color.RED);
          component8.setBackground(Color.RED);
          component9.setBackground(Color.RED);
          component10.setBackground(Color.RED);
        } else {
          component7.setBackground(null);
          component8.setBackground(null);
          component9.setBackground(null);
          component10.setBackground(null);
        }
      } catch (NullPointerException npe) {
        MainWindow.LOGGER.debug("No orders in JComboBox.");
      }
    });
    final JComboBox comboBox25;
    final JTextComponent textComponent30;
    Order order12;
    final Thread thread2;
    Thread thread;
    btnSubmit.addActionListener(evt -> {
      if (comboBox25.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, this.messages.getString("NoOrderSelected"));
      } else if (((Order) comboBox25.getSelectedItem()).isOnHold()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderOnHold"));
      } else if (textComponent30.isEditable()) {
        JOptionPane.showMessageDialog(null, this.messages.getString("OrderIsBeingEdited"));
      } else {
        order12 = (Order) comboBox25.getSelectedItem();
        if (order12.getDSetUp() == null) {
          JOptionPane.showMessageDialog(null, this.messages.getString("NoSetupTime"));
        }
        if (order12.getDApproved() == null) {
          JOptionPane.showMessageDialog(null, this.messages.getString("NoApprovedTime"));
        }
        if (order12.getDComplete() == null) {
          JOptionPane.showMessageDialog(null, this.messages.getString("NoCompleteTime"));
        } else {
          new Thread(() -> HTML.putOrder(order12)).start();
          new Thread(new Average());
          thread = thread2;
          thread.start();
          comboBox25.removeItemAt(comboBox25.getSelectedIndex());
        }
      }
    });
  }

  static {
    DF = DateTimeFormat.forPattern("M-d-yyyy hh:mm a");
    LOGGER = LogManager.getLogger(MainWindow.class);
  }
}
