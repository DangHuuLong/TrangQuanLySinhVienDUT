package view.admin;

import model.User;
import view.home.HomePage;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public AdminPanel(User admin) {
        setTitle("Quản lý hệ thống - Admin");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Navbar dọc bên trái
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(0, 102, 204));
        navPanel.setPreferredSize(new Dimension(200, 0));

        // Logo DUT resize
        ImageIcon logoIcon = new ImageIcon("D:/java/workspace/TrangQuanLySinhVienDUT/src/images/logoDUT.jpg");
        Image logoImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(logoIcon);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(100, 100)); // Giới hạn kích thước label

        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(logo);
        navPanel.add(Box.createVerticalStrut(20));
        
        // Điều hướng -> Trang quản lý sinh viên
        JButton studentBtn = createNavButton("Sinh viên");
        studentBtn.addActionListener(e -> cardLayout.show(contentPanel, "students"));
        navPanel.add(studentBtn);
        
        // Điều hướng -> Trang quản lý giảng viên
        JButton lecturerBtn = createNavButton("Giảng viên");
        lecturerBtn.addActionListener(e -> cardLayout.show(contentPanel, "lecturers"));
        navPanel.add(lecturerBtn);

        
        // Điều hướng -> Trang quản lý khoa
        JButton deptBtn = createNavButton("Khoa");
        deptBtn.addActionListener(e -> cardLayout.show(contentPanel, "departments"));
        navPanel.add(deptBtn);


        // Điều hướng -> Trang quản lý ngành
        JButton majorBtn = createNavButton("Ngành");
        majorBtn.addActionListener(e -> cardLayout.show(contentPanel, "majors"));
        navPanel.add(majorBtn);


        
        // Logout
        JButton logoutBtn = createNavButton("Đăng xuất");
        logoutBtn.addActionListener(e -> {
            dispose(); 
            new HomePage(); 
        });
        navPanel.add(Box.createVerticalStrut(20)); 
        navPanel.add(logoutBtn);


        // Nội dung bên phải
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(new StudentPanel(), "students");
        contentPanel.add(new LecturerPanel(), "lecturers");
        contentPanel.add(new DepartmentPanel(), "departments");
        contentPanel.add(new MajorPanel(), "majors");

        add(navPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createNavButton(String title) {
        JButton btn = new JButton(title);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        return btn;
    }
}
