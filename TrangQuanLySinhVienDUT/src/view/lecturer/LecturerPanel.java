package view.lecturer;

import dao.LecturerDAO;
import model.User;
import model.Lecturer;

import javax.swing.*;
import java.awt.*;

public class LecturerPanel extends JFrame {
    private final LecturerDAO lecturerDAO = new LecturerDAO();
    private JLabel greetingLabel;

    public LecturerPanel(User lecturerUser) {
        setTitle("Trang thông tin giảng viên");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Lecturer lecturerInfo = lecturerDAO.getLecturerById(lecturerUser.getId());

        String greeting = "Chào " + lecturerInfo.getFullName() + " - " + lecturerInfo.getLecturerCode();
        greetingLabel = new JLabel(greeting);
        greetingLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton logoutBtn = new JButton("Thoát");
        logoutBtn.addActionListener(e -> {
            dispose(); 
            new view.home.HomePage(); 
        });

        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        greetingPanel.add(greetingLabel);
        greetingPanel.add(logoutBtn);

        add(greetingPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
