package view.home;

import model.User;
import view.admin.AdminPanel;
import view.student.StudentPanel;
import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(JFrame parentFrame) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);

        // ==== LEFT: Avatar ====
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            File imageFile = new File("D:/java/workspace/TrangQuanLySinhVienDUT/src/images/loginImage.png");
            ImageIcon icon = new ImageIcon(imageFile.toURI().toURL());
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            ex.printStackTrace();
            imageLabel.setText("No Image");
        }
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setOpaque(true);
        container.add(imageLabel, BorderLayout.WEST);

        // ==== RIGHT: Form login ====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Đặt nền trắng cho form
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Tài khoản:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(12);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(12);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginBtn = new JButton("Đăng nhập");
        loginBtn.setBackground(Color.decode("#4D90FE"));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        formPanel.add(loginBtn, gbc);

        container.add(formPanel, BorderLayout.CENTER);
        add(container);

        // === Action ===
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            UserDAO userDAO = new UserDAO();
            User user = userDAO.login(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công với vai trò: " + user.getRole());
                parentFrame.dispose();
                if ("admin".equals(user.getRole())) {
                    new AdminPanel(user);
                } else {
                    new StudentPanel(user);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không đúng.");
            }
        });
    }
}
