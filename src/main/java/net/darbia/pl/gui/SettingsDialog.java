package net.darbia.pl.gui;

import net.darbia.pl.data.PasswordUtils;
import net.darbia.pl.data.sql.SQL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

class SettingsDialog {

  private static final Logger logger;
  private JFrame frmSettings;

  private SettingsDialog(final MainWindow mainWindow, final ResourceBundle messages) {
    this.initialize(mainWindow, messages);
  }

  static void main(final MainWindow mainWindow, final ResourceBundle messages) {
    SettingsDialog window;
    EventQueue.invokeLater(() -> {
      try {
        window = new SettingsDialog(mainWindow, messages);
        window.frmSettings.setVisible(true);
      } catch (Exception exception) {
        SettingsDialog.logger.error(exception);
      }
    });
  }

  private void initialize(final MainWindow mainWindow, final ResourceBundle messages) {
    this.frmSettings = new JFrame();
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final URL resource = classLoader.getResource("images/logo.jpg");
    final ImageIcon icon = new ImageIcon(resource);
    this.frmSettings.setIconImage(icon.getImage());
    this.frmSettings.setTitle(messages.getString("Settings"));
    this.frmSettings.setBounds(100, 100, 200, 200);
    this.frmSettings.setDefaultCloseOperation(2);
    final GridBagLayout gridBagLayout = new GridBagLayout();
    this.frmSettings.getContentPane().setLayout(gridBagLayout);
    final JButton btnChangeEditPassword = new JButton(messages.getString("ChangePassword"));
    final JPasswordField oldpwd;
    final JPasswordField newpwd;
    final JPasswordField cfrmpwd;
    final Object[] message;
    final int option;
    Map<String, String> hashmap;
    String pass;
    String salt;
    String newSalt;
    String newPass;
    btnChangeEditPassword.addActionListener(evt1 -> {
      oldpwd = new JPasswordField();
      newpwd = new JPasswordField();
      cfrmpwd = new JPasswordField();
      message = new Object[]{messages.getString("OldPassword"), oldpwd,
          messages.getString("NewPassword"), newpwd, messages.getString("ConfirmNewPassword"),
          cfrmpwd};
      option = JOptionPane.showConfirmDialog(this.frmSettings, message,
          messages.getString("ChangePassword"), 2);
      if (option == 0) {
        hashmap = SQL.loadPassword();
        pass = hashmap.get("password");
        salt = hashmap.get("salt");
        if (PasswordUtils.verifyUserPassword(String.valueOf(oldpwd.getPassword()), pass, salt)) {
          if (String.valueOf(newpwd.getPassword()).equals(String.valueOf(cfrmpwd.getPassword()))) {
            newSalt = PasswordUtils.getSalt(30);
            newPass = PasswordUtils.generateSecurePassword(String.valueOf(cfrmpwd.getPassword()),
                newSalt);
            SQL.updatePassword(newPass, newSalt);
          } else {
            JOptionPane.showMessageDialog(this.frmSettings,
                messages.getString("ErrorNewPasswordsDifferent"));
          }
        } else {
          JOptionPane.showMessageDialog(this.frmSettings,
              messages.getString("ErrorOldPasswordIncorrect"));
        }
      }
    });
    final GridBagConstraints gbcCngPass = new GridBagConstraints();
    gbcCngPass.gridx = 0;
    gbcCngPass.gridy = 0;
    this.frmSettings.getContentPane().add(btnChangeEditPassword, gbcCngPass);
    final JButton btnTimes = new JButton(messages.getString("ChangeTimes"));
    btnTimes.addActionListener(e -> ChangeTimesDialog.main(messages));
    final GridBagConstraints gbcTimes = new GridBagConstraints();
    gbcTimes.gridx = 0;
    gbcTimes.gridy = 2;
    this.frmSettings.getContentPane().add(btnTimes, gbcTimes);
    final JButton btnChangeLanguage = new JButton(messages.getString("ChangeLanguage"));
    final Object[] options;
    final int result;
    btnChangeLanguage.addActionListener(e -> {
      options = new Object[]{messages.getString("English"), messages.getString("Spanish"),
          messages.getString("Cancel")};
      result = JOptionPane.showOptionDialog(null, "Test", messages.getString("ChangeLanguage"), 1,
          -1, null, options, null);
      if (result == 1) {
        SQL.updateLocale("es", "US");
        mainWindow.setLocale();
      } else if (result == 0) {
        SQL.updateLocale("en", "US");
        mainWindow.setLocale();
      }
    });
    final GridBagConstraints gbcCngLang = new GridBagConstraints();
    gbcCngLang.gridx = 0;
    gbcCngLang.gridy = 3;
    this.frmSettings.getContentPane().add(btnChangeLanguage, gbcCngLang);
  }

  static {
    logger = LogManager.getLogger(SettingsDialog.class);
  }
}
