// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.gui;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Iterator;
import javax.swing.text.JTextComponent;
import java.net.URL;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.Frame;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import net.darbia.pl.objects.Employee;
import java.util.ArrayList;
import javax.swing.JDialog;

class AddEmployeesBulkDialog extends JDialog
{
    private ArrayList<Employee> employees;
    
    AddEmployeesBulkDialog(final JFrame parent, final ResourceBundle messages) {
        super(parent, messages.getString("AddEmployeesBulk"), true);
        this.employees = new ArrayList<Employee>();
        boolean wID = false;
        if (JOptionPane.showConfirmDialog(null, messages.getString("WithIDs?"), messages.getString("WithIDs?"), 0) == 0) {
            wID = true;
        }
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = classLoader.getResource("images/logo.jpg");
        final ImageIcon icon = new ImageIcon(resource);
        this.setIconImage(icon.getImage());
        this.setBounds(100, 100, 450, 300);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        final JButton btnSave = new JButton(messages.getString("Save"));
        this.getContentPane().add(btnSave, "South");
        final JTextArea textArea = new JTextArea();
        this.getContentPane().add(textArea, "Center");
        if (wID) {
            textArea.setText(messages.getString("AddBulkWithIDStarter"));
            final ArrayList<String> strings;
            final JTextComponent textComponent;
            final Scanner sc;
            final Iterator<String> iterator;
            String s;
            String[] temp;
            btnSave.addActionListener(evt -> {
                strings = new ArrayList<String>();
                sc = new Scanner(textComponent.getText());
                while (sc.hasNextLine()) {
                    strings.add(sc.nextLine());
                }
                sc.close();
                strings.iterator();
                while (iterator.hasNext()) {
                    s = iterator.next();
                    temp = s.trim().split("\\s+");
                    try {
                        this.employees.add(new Employee(Integer.parseInt(temp[0]), temp[1], temp[2]));
                    }
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog((Component)this, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, messages.getString("Error"), exception.getMessage()), messages.getString("Error"), 0);
                    }
                }
                this.dispose();
            });
        }
        else {
            textArea.setText(messages.getString("AddBulkWithoutIDStarter"));
            final ArrayList<String> strings2;
            final JTextComponent textComponent2;
            final Scanner sc2;
            final Iterator<String> iterator2;
            String s2;
            String[] temp2;
            btnSave.addActionListener(evt -> {
                strings2 = new ArrayList<String>();
                sc2 = new Scanner(textComponent2.getText());
                while (sc2.hasNextLine()) {
                    strings2.add(sc2.nextLine());
                }
                sc2.close();
                strings2.iterator();
                while (iterator2.hasNext()) {
                    s2 = iterator2.next();
                    temp2 = s2.trim().split("\\s+");
                    try {
                        this.employees.add(new Employee(0, temp2[0], temp2[1]));
                    }
                    catch (Exception exception2) {
                        JOptionPane.showMessageDialog((Component)this, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, messages.getString("Error"), exception2.getMessage()), messages.getString("Error"), 0);
                    }
                }
                this.dispose();
            });
        }
    }
    
    List<Employee> getEmployees() {
        return this.employees;
    }
}
