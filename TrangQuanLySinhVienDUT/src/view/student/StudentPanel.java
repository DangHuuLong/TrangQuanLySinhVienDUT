package view.student;

import dao.StudentDAO;
import model.Student;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

import util.DBConnection;

public class StudentPanel extends JFrame {
	private static final Map<String, String> labelMap = Map.ofEntries(
		    Map.entry("full_name", "Họ tên"),
		    Map.entry("dob", "Ngày sinh"),
		    Map.entry("place_of_birth", "Nơi sinh"),
		    Map.entry("gender", "Giới tính"),
		    Map.entry("ethnicity", "Dân tộc"),
		    Map.entry("nationality", "Quốc tịch"),
		    Map.entry("religion", "Tôn giáo"),
		    Map.entry("personal_account", "Tài khoản cá nhân"),
		    Map.entry("bank_name", "Ngân hàng"),
		    Map.entry("bhyt_code", "Mã thẻ BHYT"),
		    Map.entry("bhyt_valid_until", "Hiệu lực đến ngày"),
		    Map.entry("free_bhyt", "Miễn phí BHYT"),
		    Map.entry("major", "Ngành"),
		    Map.entry("class_name", "Lớp"),
		    Map.entry("program", "Chương trình đào tạo"),
		    Map.entry("program_2", "Chương trình 2"),
		    Map.entry("office365_account", "Tài khoản Office365"),
		    Map.entry("personal_email", "Email cá nhân"),
		    Map.entry("office365_default_pass", "Mật khẩu Office365"),
		    Map.entry("facebook_url", "Facebook"),
		    Map.entry("phone", "Điện thoại"),
		    Map.entry("address", "Địa chỉ cư trú"),
		    Map.entry("address_note", "Là địa chỉ của"),
		    Map.entry("city", "Tỉnh/Thành phố"),
		    Map.entry("district", "Quận/Huyện"),
		    Map.entry("ward", "Xã/Phường"),
		    Map.entry("avatar_path", "Ảnh thẻ 3x4")
		);

    private final StudentDAO dao = new StudentDAO();
    private final int studentId;
    private final Map<String, JTextField> fieldInputs = new LinkedHashMap<>();
    private JLabel avatarLabel;

    public StudentPanel(User studentUser) {
        this.studentId = studentUser.getId();

        setTitle("Trang thông tin sinh viên");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loadStudentData(formPanel);

        JButton saveBtn = new JButton("Lưu thông tin cá nhân");
        saveBtn.addActionListener(e -> saveUpdates());

        avatarLabel = new JLabel();
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setPreferredSize(new Dimension(120, 160));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        mainPanel.add(saveBtn, BorderLayout.SOUTH);
        mainPanel.add(avatarLabel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);
    }

    private void loadStudentData(JPanel formPanel) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String column = meta.getColumnName(i);
                    if ("id".equals(column) || "student_code".equals(column)) continue;

                    String value = rs.getString(column);
                    JTextField field = new JTextField(value != null ? value : "");
                    fieldInputs.put(column, field);
                    String label = labelMap.getOrDefault(column, column); 
                    formPanel.add(new JLabel(label));
                    formPanel.add(field);
                }

                // load ảnh nếu có
                String avatarPath = rs.getString("avatar_path");
                if (avatarPath != null && !avatarPath.isEmpty()) {
                    ImageIcon icon = new ImageIcon(avatarPath);
                    Image img = icon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
                    avatarLabel.setIcon(new ImageIcon(img));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUpdates() {
        for (Map.Entry<String, JTextField> entry : fieldInputs.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue().getText().trim();
            if (!value.isEmpty()) {
                dao.updateField(studentId, field, value);
            }
        }
        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    }
}
