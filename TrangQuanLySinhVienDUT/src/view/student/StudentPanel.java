package view.student;

import dao.StudentDAO;
import model.Student;
import model.User;
import util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

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
        Map.entry("ten_khoa", "Khoa"),
        Map.entry("ten_nganh", "Ngành"),
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
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

	     // === HEADER ===
	     // 1. Tiêu đề + logo DUT
	     JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	     titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	     titlePanel.setBackground(Color.WHITE);
	
	     JLabel logoLabel = new JLabel();
	     try {
	         ImageIcon logoIcon = new ImageIcon("D:/java/workspace/TrangQuanLySinhVienDUT/src/images/logoDUT.jpg");
	         Image logoImg = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	         logoLabel.setIcon(new ImageIcon(logoImg));
	     } catch (Exception ex) {
	         ex.printStackTrace();
	     }
	
	     JPanel textPanel = new JPanel();
	     textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
	     textPanel.setBackground(Color.WHITE);
	
	     JLabel vnTitle = new JLabel("HỆ THỐNG THÔNG TIN SINH VIÊN");
	     vnTitle.setFont(new Font("Calibri", Font.BOLD, 22));
	     vnTitle.setForeground(new Color(0, 51, 153));
	
	     JLabel enTitle = new JLabel("Student Information System");
	     enTitle.setFont(new Font("Calibri", Font.PLAIN, 20));
	     enTitle.setForeground(Color.GRAY);
	
	     textPanel.add(vnTitle);
	     textPanel.add(enTitle);
	
	     titlePanel.add(logoLabel);
	     titlePanel.add(textPanel);
	
	     // 2. Thanh điều hướng + lời chào + nút Thoát
	     JPanel navBar = new JPanel(new BorderLayout());
	     navBar.setBackground(Color.decode("#4D90FE"));
	
	     JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	     menuPanel.setBackground(Color.decode("#4D90FE"));
	
	     // Menu dropdown
	     menuPanel.add(createDropdownButton("Cá nhân", new String[]{
	    		    "Thông tin nhân thân",
	    		    "Khung Chương trình đào tạo",
	    		    "Lịch học trong ngày",
	    		    "Lịch học, thi & khảo sát ý kiến",
	    		    "Học phí",
	    		    "Tự đánh giá rèn luyện",
	    		    "Kết quả phục vụ cộng đồng",
	    		    "Đồ án/ Luận văn tốt nghiệp",
	    		    "Kết quả học tập, rèn luyện",
	    		    "Kê khai thông tin chống Covid",
	    		    "Kê khai chương trình đào tạo"
	    		}));
	     menuPanel.add(createDropdownButton("Lớp/ Đoàn/ Hội", new String[]{
	    		    "Lớp - Danh sách sinh viên, Đoàn viên",
	    		    "Lớp - Cán bộ lớp đánh giá rèn luyện",
	    		    "Lớp - Kết quả đánh giá rèn luyện",
	    		    "Đoàn - Thông tin Đoàn viên",
	    		    "Đoàn - Quản lý Đoàn phí"
	    		}));
	     menuPanel.add(createDropdownButton("Đăng ký", new String[]{
	    		    "Bảo hiểm y tế, bảo hiểm thân thể",
	    		    "Tham gia phục vụ cộng đồng",
	    		    "Thi Anh văn định kỳ, chuẩn đầu ra",
	    		    "Nhận Phụ lục bằng tốt nghiệp"
	    		}));
	     menuPanel.add(createDropdownButton("Đề nghị Trường", new String[]{
	    		    "Cấp lại thẻ sinh viên",
	    		    "Cấp văn bản xác nhận",
	    		    "Hỗ trợ tài khoản Email, LMS",
	    		    "Xét hoãn thi, thi bổ sung",
	    		    "Xét phúc khảo",
	    		    "Xét ngừng, gia hạn ngừng học",
	    		    "Xét trở lại học",
	    		    "Xét tốt nghiệp, hoãn tốt nghiệp",
	    		    "Xét học chương trình 2 tại Trường",
	    		    "Xét học bổng khuyến khích học tập",
	    		    "Giải quyết thủ tục thôi học"
	    		}));
	     menuPanel.add(createDropdownButton("Phản ánh", new String[]{
	    		    "Báo thiết bị phòng học hỏng"
	    		}));

	     // Lời chào + nút Thoát
	     Student studentInfo = dao.getById(studentId);
	     String greeting = "Chào " + studentInfo.getFullName() + " - " + studentInfo.getClassName() + " (" + studentInfo.getStudentCode() + ")";
	     JLabel greetingLabel = new JLabel(greeting);
	     greetingLabel.setForeground(Color.WHITE);
	     greetingLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	
	     // === Cập nhật: thay vì dispose(), quay về HomePage ===
	     JButton logoutBtn = new JButton("Thoát");
	     logoutBtn.setFocusPainted(false);
	     logoutBtn.setBackground(Color.decode("#4D90FE"));
	     logoutBtn.setForeground(Color.WHITE);
	     logoutBtn.setBorderPainted(false);
	     logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	     logoutBtn.addActionListener(e -> {
	         dispose(); // đóng StudentPanel
	         new view.home.HomePage(); // mở lại trang chủ
	     });
	
	     menuPanel.add(greetingLabel);
	     menuPanel.add(logoutBtn);
	     navBar.add(menuPanel, BorderLayout.WEST);
	
	     // Gộp header lại
	     JPanel headerPanel = new JPanel();
	     headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
	     headerPanel.add(titlePanel);
	     headerPanel.add(navBar);
	
	     add(headerPanel, BorderLayout.NORTH); // === THÊM HEADER ===


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

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void loadStudentData(JPanel formPanel) {
        try (Connection conn = DBConnection.getConnection()) {
        	String sql = """
        		    SELECT s.*, d.ten_khoa, m.ten_nganh
        		    FROM students s
        		    LEFT JOIN departments d ON s.ma_khoa = d.ma_khoa
        		    LEFT JOIN majors m ON s.ma_nganh = m.ma_nganh
        		    WHERE s.id = ?
        		""";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String column = meta.getColumnName(i);
                    if ("id".equals(column) || "student_code".equals(column)
                    	    || "ma_khoa".equals(column) || "ma_nganh".equals(column)) continue;


                    String value = rs.getString(column);
                    JTextField field = new JTextField(value != null ? value : "");
                    fieldInputs.put(column, field);
                    String label = labelMap.getOrDefault(column, column);
                    formPanel.add(new JLabel(label));
                    formPanel.add(field);
                }

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
    
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.decode("#4D90FE"));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.decode("#4D90FE"));
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }
    
    private JButton createDropdownButton(String title, String[] items) {
        JButton button = createNavButton(title);
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBackground(Color.decode("#4D90FE"));

        for (String item : items) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 13));
            menuItem.setForeground(Color.WHITE);
            menuItem.setBackground(Color.decode("#4D90FE"));
            menuItem.setOpaque(true);

            menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    menuItem.setBackground(Color.WHITE);
                    menuItem.setForeground(Color.BLACK);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    menuItem.setBackground(Color.decode("#4D90FE"));
                    menuItem.setForeground(Color.WHITE);
                }
            });

            popupMenu.add(menuItem);
            menuItem.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Bạn đã chọn: " + item);
            });
        }

        button.addActionListener(e -> popupMenu.show(button, 0, button.getHeight()));
        return button;
    }
}
