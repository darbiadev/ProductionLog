// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.gui;

import org.apache.logging.log4j.LogManager;
import java.awt.event.ActionEvent;
import javax.swing.text.JTextComponent;
import java.util.List;
import java.net.URL;
import javax.swing.event.ChangeEvent;
import javax.swing.event.CellEditorListener;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.LayoutManager;
import net.darbia.pl.objects.Order;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import net.darbia.pl.data.HTML;
import javax.swing.ImageIcon;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.EventQueue;
import java.util.ResourceBundle;
import net.darbia.pl.objects.Employee;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import org.apache.logging.log4j.Logger;

class CreateScreenPrintOrderDialog
{
    private static final Logger LOGGER;
    private JDialog dlgCreateEvent;
    private boolean stupid;
    
    private CreateScreenPrintOrderDialog(final JComboBox orders, final HashMap<Integer, Employee[]> machineNames, final ResourceBundle messages) {
        this.stupid = true;
        this.initialize(orders, machineNames, messages);
    }
    
    static void main(final JComboBox orders, final HashMap<Integer, Employee[]> machineNames, final ResourceBundle messages) {
        CreateScreenPrintOrderDialog window;
        EventQueue.invokeLater(() -> {
            try {
                window = new CreateScreenPrintOrderDialog(orders, machineNames, messages);
                window.dlgCreateEvent.setVisible(true);
            }
            catch (Exception exception) {
                CreateScreenPrintOrderDialog.LOGGER.error(exception);
            }
        });
    }
    
    private void initialize(final JComboBox orders, final HashMap<Integer, Employee[]> machineNames, final ResourceBundle messages) {
        this.dlgCreateEvent = new JDialog();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = classLoader.getResource("images/logo.jpg");
        final ImageIcon icon = new ImageIcon(resource);
        this.dlgCreateEvent.setIconImage(icon.getImage());
        this.dlgCreateEvent.setTitle(messages.getString("CreateEvent"));
        this.dlgCreateEvent.setBounds(100, 100, 347, 311);
        this.dlgCreateEvent.setDefaultCloseOperation(2);
        final List<Employee> employees = HTML.getEmployees();
        final JComboBox comboBoxLocations = new JComboBox();
        comboBoxLocations.setModel(new DefaultComboBoxModel<String>(new String[] { "Left Chest", "Right Chest", "Full Chest", "Full Back", "Rt Sleeve", "Left Sleeve", "Small Center Chest", "Small Upper Back", "On Pocket", "Above Pocket", "Neck Label" }));
        comboBoxLocations.setBounds(180, 83, 145, 20);
        this.dlgCreateEvent.getContentPane().add(comboBoxLocations);
        final JLabel lblOrderNumber = new JLabel(messages.getString("OrderNumber"));
        lblOrderNumber.setBounds(10, 41, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblOrderNumber);
        final JLabel lblDesignNumber = new JLabel(messages.getString("DesignNumber"));
        lblDesignNumber.setBounds(10, 65, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblDesignNumber);
        final JLabel lblNumberOfColors = new JLabel(messages.getString("NumberOfColors"));
        lblNumberOfColors.setBounds(10, 113, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblNumberOfColors);
        final JLabel lblLocation = new JLabel(messages.getString("Location"));
        lblLocation.setBounds(10, 89, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblLocation);
        final JTextField textFieldDnum = new JTextField();
        textFieldDnum.setBounds(180, 59, 145, 20);
        this.dlgCreateEvent.getContentPane().add(textFieldDnum);
        textFieldDnum.setColumns(10);
        final JTextField textFieldOnum = new JTextField();
        textFieldOnum.setBounds(180, 35, 145, 20);
        this.dlgCreateEvent.getContentPane().add(textFieldOnum);
        textFieldOnum.setColumns(10);
        final JTextField textFieldNumCol = new JTextField();
        textFieldNumCol.setBounds(180, 107, 145, 20);
        this.dlgCreateEvent.getContentPane().add(textFieldNumCol);
        textFieldNumCol.setColumns(10);
        final JLabel lblMachine = new JLabel(messages.getString("Machine"));
        lblMachine.setBounds(10, 11, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblMachine);
        final JComboBox comboBoxMachine = new JComboBox();
        comboBoxMachine.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        comboBoxMachine.setBounds(180, 8, 46, 20);
        this.dlgCreateEvent.getContentPane().add(comboBoxMachine);
        final JLabel lblTender = new JLabel(messages.getString("Tender"));
        lblTender.setBounds(10, 138, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblTender);
        final JLabel lblLoader = new JLabel(messages.getString("Loader"));
        lblLoader.setBounds(10, 162, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblLoader);
        final JLabel lblPuller = new JLabel(messages.getString("Puller"));
        lblPuller.setBounds(10, 187, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblPuller);
        final JLabel lblCatcher = new JLabel(messages.getString("Catcher"));
        lblCatcher.setBounds(10, 212, 129, 14);
        this.dlgCreateEvent.getContentPane().add(lblCatcher);
        final JComboBox<Employee> comboBoxTender = new JComboBox<Employee>(new Vector<Employee>(employees));
        comboBoxTender.setBounds(180, 135, 145, 20);
        this.dlgCreateEvent.getContentPane().add(comboBoxTender);
        AutoCompleteDecorator.decorate(comboBoxTender);
        final JComboBox<Employee> comboBoxLoader = new JComboBox<Employee>(new Vector<Employee>(employees));
        comboBoxLoader.setBounds(180, 159, 145, 20);
        this.dlgCreateEvent.getContentPane().add(comboBoxLoader);
        AutoCompleteDecorator.decorate(comboBoxLoader);
        final JComboBox<Employee> comboBoxPuller = new JComboBox<Employee>(new Vector<Employee>(employees));
        comboBoxPuller.setBounds(180, 184, 145, 20);
        this.dlgCreateEvent.getContentPane().add(comboBoxPuller);
        AutoCompleteDecorator.decorate(comboBoxPuller);
        final JComboBox<Employee> comboBoxCatcher = new JComboBox<Employee>(new Vector<Employee>(employees));
        comboBoxCatcher.setBounds(180, 209, 145, 20);
        this.dlgCreateEvent.getContentPane().add(comboBoxCatcher);
        AutoCompleteDecorator.decorate(comboBoxCatcher);
        final JButton btnCreate = new JButton(messages.getString("Create"));
        boolean test;
        final JTextComponent textComponent;
        final JTextComponent textComponent2;
        final JTextComponent textComponent3;
        final JComboBox comboBox;
        final JComboBox comboBox2;
        final JComboBox comboBox3;
        final JComboBox comboBox4;
        final JComboBox comboBox5;
        final JComboBox comboBox6;
        Order order;
        btnCreate.addActionListener(evt -> {
            test = false;
            try {
                Integer.parseInt(textComponent.getText().trim());
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, messages.getString("ErrorOrderNumberInvalid"));
                test = true;
            }
            try {
                Integer.parseInt(textComponent2.getText().trim());
            }
            catch (NumberFormatException nfe2) {
                JOptionPane.showMessageDialog(null, messages.getString("ErrorDesignNumberInvalid"));
                test = true;
            }
            try {
                Integer.parseInt(textComponent3.getText().trim());
            }
            catch (NumberFormatException nfe3) {
                JOptionPane.showMessageDialog(null, messages.getString("ErrorColorsNumberInvalid"));
                test = true;
            }
            if (!test) {
                machineNames.replace(Integer.parseInt(comboBox.getSelectedItem().toString()), new Employee[] { (Employee)comboBox2.getSelectedItem(), (Employee)comboBox3.getSelectedItem(), (Employee)comboBox4.getSelectedItem(), (Employee)comboBox5.getSelectedItem() });
                order = new Order(Integer.parseInt(comboBox.getSelectedItem().toString()), Integer.parseInt(textComponent.getText().trim()), Integer.parseInt(textComponent2.getText().trim()), comboBox6.getSelectedItem().toString(), Integer.parseInt(textComponent3.getText().trim()), (Employee)comboBox2.getSelectedItem(), (Employee)comboBox3.getSelectedItem(), (Employee)comboBox4.getSelectedItem(), (Employee)comboBox5.getSelectedItem());
                orders.addItem(order);
                orders.setSelectedItem(order);
                this.dlgCreateEvent.dispose();
            }
            return;
        });
        this.dlgCreateEvent.getContentPane().setLayout(null);
        btnCreate.setBounds(10, 238, 145, 23);
        this.dlgCreateEvent.getContentPane().add(btnCreate);
        final JButton btnCancel = new JButton(messages.getString("Cancel"));
        final int reply;
        btnCancel.addActionListener(evt -> {
            reply = JOptionPane.showConfirmDialog(null, messages.getString("Cancel?"), messages.getString("Cancel?"), 0);
            if (reply == 0) {
                this.dlgCreateEvent.dispose();
            }
            return;
        });
        btnCancel.setBounds(180, 238, 145, 23);
        this.dlgCreateEvent.getContentPane().add(btnCancel);
        final JButton btnUpdateNamesBasedOnMachineNumber = new JButton();
        final JComboBox comboBox7;
        final int machineNumber;
        final JComboBox comboBox8;
        final JComboBox comboBox9;
        final JComboBox comboBox10;
        final JComboBox comboBox11;
        Robot robot;
        int keyCode;
        btnUpdateNamesBasedOnMachineNumber.addActionListener(evt -> {
            machineNumber = Integer.parseInt((String)comboBox7.getSelectedItem());
            comboBox8.setSelectedItem(machineNames.get(machineNumber)[0]);
            comboBox9.setSelectedItem(machineNames.get(machineNumber)[1]);
            comboBox10.setSelectedItem(machineNames.get(machineNumber)[2]);
            comboBox11.setSelectedItem(machineNames.get(machineNumber)[3]);
            if (!this.stupid) {
                try {
                    robot = new Robot();
                    keyCode = 10;
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                }
                catch (AWTException awt) {
                    CreateScreenPrintOrderDialog.LOGGER.error("Robot for simulation enter", awt);
                }
            }
            this.stupid = false;
            return;
        });
        class ItemChangeListener implements ItemListener
        {
            @Override
            public void itemStateChanged(final ItemEvent event) {
                if (event.getStateChange() == 1) {
                    btnUpdateNamesBasedOnMachineNumber.doClick();
                }
            }
        }
        comboBoxMachine.addItemListener(new ItemChangeListener());
        comboBoxMachine.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent evt) {
                final int key = evt.getKeyCode();
                if (key == 10) {
                    textFieldOnum.requestFocusInWindow();
                }
            }
        });
        textFieldOnum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent evt) {
                final int key = evt.getKeyCode();
                if (key == 10) {
                    textFieldDnum.requestFocusInWindow();
                }
            }
        });
        textFieldDnum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent evt) {
                final int key = evt.getKeyCode();
                if (key == 10) {
                    comboBoxLocations.requestFocusInWindow();
                }
            }
        });
        comboBoxLocations.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent evt) {
                final int key = evt.getKeyCode();
                if (key == 10) {
                    textFieldNumCol.requestFocusInWindow();
                }
            }
        });
        textFieldNumCol.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent evt) {
                final int key = evt.getKeyCode();
                if (key == 10) {
                    comboBoxTender.requestFocusInWindow();
                }
            }
        });
        final ComboBoxCellEditor comboBoxCellEditorTender = new ComboBoxCellEditor(comboBoxTender);
        final CellEditorListener cellEditorListenerTender = new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                comboBoxLoader.requestFocusInWindow();
            }
            
            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        };
        comboBoxCellEditorTender.addCellEditorListener(cellEditorListenerTender);
        final ComboBoxCellEditor comboBoxCellEditorLoader = new ComboBoxCellEditor(comboBoxLoader);
        final CellEditorListener cellEditorListenerLoader = new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                comboBoxPuller.requestFocusInWindow();
            }
            
            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        };
        comboBoxCellEditorLoader.addCellEditorListener(cellEditorListenerLoader);
        final ComboBoxCellEditor comboBoxCellEditorPuller = new ComboBoxCellEditor(comboBoxPuller);
        final CellEditorListener cellEditorListenerPuller = new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                comboBoxCatcher.requestFocusInWindow();
            }
            
            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        };
        comboBoxCellEditorPuller.addCellEditorListener(cellEditorListenerPuller);
        final ComboBoxCellEditor comboBoxCellEditorCatcher = new ComboBoxCellEditor(comboBoxCatcher);
        final CellEditorListener cellEditorListenerCatcher = new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                if (textFieldOnum.getText().equals("")) {
                    textFieldOnum.requestFocusInWindow();
                }
                else {
                    btnCreate.requestFocusInWindow();
                }
            }
            
            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        };
        comboBoxCellEditorCatcher.addCellEditorListener(cellEditorListenerCatcher);
        comboBoxMachine.requestFocusInWindow();
        btnUpdateNamesBasedOnMachineNumber.doClick();
    }
    
    static {
        LOGGER = LogManager.getLogger(CreateScreenPrintOrderDialog.class);
    }
}
