package view.home;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HomePage extends JFrame {
    private JPanel mainContentPanel;

    public HomePage() {
        setTitle("Hệ thống sinh viên - Student Information System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === HEADER ===
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.setBackground(Color.WHITE);

        ImageIcon logoIcon = null;
        try {
            File logoFile = new File("D:/java/workspace/TrangQuanLySinhVienDUT/src/images/logoDUT.jpg");
            logoIcon = new ImageIcon(logoFile.toURI().toURL());
            Image logoImg = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(logoImg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JLabel logoLabel = new JLabel();
        if (logoIcon != null) {
            logoLabel.setIcon(logoIcon);
        }

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        Font customFont = new Font("Calibri", Font.BOLD, 22);

        JLabel vnTitle = new JLabel("HỆ THỐNG THÔNG TIN SINH VIÊN");
        vnTitle.setFont(customFont);
        vnTitle.setForeground(new Color(0, 51, 153));

        JLabel enTitle = new JLabel("Student Information System");
        enTitle.setFont(customFont.deriveFont(Font.PLAIN, 20f));
        enTitle.setForeground(Color.GRAY);

        textPanel.add(vnTitle);
        textPanel.add(enTitle);

        titlePanel.add(logoLabel);
        titlePanel.add(textPanel);

        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Color.decode("#4D90FE"));

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        menuPanel.setBackground(Color.decode("#4D90FE"));

        JButton homeBtn = createNavButton("Trang chủ");
        homeBtn.addActionListener(e -> showHomeContent());
        menuPanel.add(homeBtn);

        menuPanel.add(createDropdownButton("Chương trình", new String[]{"Chương trình đào tạo", "Học phần", "Program"}));
        menuPanel.add(createDropdownButton("Kế hoạch", new String[]{"Kế hoạch đào tạo năm học", "Đăng ký học", "Lớp học phần - đang đăng ký", "Lớp học phần - chính thức", "Khảo sát nhu cầu mở thêm lớp", "Thi cuối kỳ lớp học phần", "Thi Tiếng Anh định kỳ, đầu ra", "Sinh hoạt lớp định kỳ", "Khảo sát ý kiến sinh viên", "Hoạt động phục vụ cộng đồng"}));
        menuPanel.add(createDropdownButton("Danh sách", new String[]{"Sinh viên ngừng học", "Sinh viên đang học - lớp", "Sinh viên có chứng chỉ CNTT", "Sinh viên có chứng chỉ ngoại ngữ", "Sinh viên thi Tiếng Anh định kỳ", "Sinh viên làm Đồ án tốt nghiệp", "Sinh viên được hoãn đóng học phí", "Sinh viên được hoãn thi, thi bổ sung", "Sinh viên dự tuyển vào học lại", "Sinh viên bị kỷ luật", "Sinh viên bị hủy học phần", "Sinh viên bị khóa website", "Sinh viên bị tạm khóa website", "Sinh viên bị hạn chế tín chỉ đăng ký", "Sinh viên bị cảnh báo kết quả học tập"}));
        menuPanel.add(createDropdownButton("Cựu sinh viên", new String[]{"Đã tốt nghiệp", "Không tốt nghiệp"}));
        menuPanel.add(createDropdownButton("Phòng học & Hệ thống", new String[]{"Tình hình sử dụng phòng học", "Thống kê báo thiết bị phòng học hỏng", "Trạng thái hệ thống thông tin sinh viên"}));
        menuPanel.add(createDropdownButton("Liên kết", new String[]{"Thư viện", "DUT-LMS"}));
        menuPanel.add(createDropdownButton("Hỗ trợ", new String[]{"Cổng hỗ trợ thông tin trực tuyến", "Hướng dẫn Đăng ký học", "Hướng dẫn Sử dụng Email DUT", "Văn bản Quy định của Trường", "Biểu mẫu thường dùng"}));

        JButton loginBtn = createNavButton("Đăng nhập");
        loginBtn.addActionListener(e -> showLoginContent());
        menuPanel.add(loginBtn);

        navBar.add(menuPanel, BorderLayout.WEST);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(titlePanel);
        headerPanel.add(navBar);

        add(headerPanel, BorderLayout.NORTH);

        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(Color.WHITE);

        showHomeContent();

        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.setBackground(Color.WHITE);

        contentWrapper.add(mainContentPanel);
        contentWrapper.add(createFooterPanel());

        JScrollPane scrollPane = new JScrollPane(contentWrapper);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showHomeContent() {
        mainContentPanel.removeAll();

        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgLabel.setHorizontalAlignment(JLabel.CENTER);

        try {
            File imgFile = new File("D:/java/workspace/TrangQuanLySinhVienDUT/src/images/dut_building.png");
            ImageIcon imageIcon = new ImageIcon(imgFile.toURI().toURL());
            int width = this.getWidth();
            Image scaledImage = imageIcon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            ex.printStackTrace();
            imgLabel.setText("Không thể tải ảnh trang chủ.");
            imgLabel.setForeground(Color.RED);
        }

        mainContentPanel.add(Box.createVerticalStrut(20));
        mainContentPanel.add(imgLabel);
        mainContentPanel.add(Box.createVerticalStrut(20));
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    private void showLoginContent() {
        mainContentPanel.removeAll();
        mainContentPanel.add(new LoginPanel(this));
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
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

    private JPanel createFooterPanel() {
        JPanel footerWrapper = new JPanel();
        footerWrapper.setLayout(new BorderLayout());
        footerWrapper.setBackground(Color.decode("#0850c7"));

        JPanel footer = new JPanel(new GridLayout(1, 4));
        footer.setBackground(Color.decode("#0850c7"));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

        footer.add(createFooterBlock(
                "Phòng Tổ chức - Hành chính", "ĐC: Phòng S01.04", "ĐT: 0236.3842308", "http://dut.udn.vn/Phong/TCHC",
                "Phòng Đào tạo", "ĐC: Phòng S05.07, S05.10", "ĐT: 0236.3842145", "http://dut.udn.vn/Phong/DaoTao"
        ));

        footer.add(createFooterBlock(
                "Phòng KHCN & HTQT", "ĐC: Phòng S06.02", "ĐT: 0236.841292", "http://dut.udn.vn/Phong/KhoaHoc",
                "Phòng Khảo thí & ĐBCL", "ĐC: Phòng S05.03", "ĐT: 0236.3737931", "http://dut.udn.vn/Phong/QualityAssurance"
        ));

        footer.add(createFooterBlock(
                "Phòng CTSV", "ĐC: Phòng S06.08", "ĐT: 0236.3738268", "http://dut.udn.vn/Phong/Sinhvien",
                "Phòng KH-TC", "ĐC: Phòng S03.11", "ĐT: 0236.3841660", "http://dut.udn.vn/Phong/TaiChinh"
        ));

        footer.add(createFooterBlock(
                "Phòng CSVC", "ĐC: Phòng S03.02, S03.05", "ĐT: 0236.3740719", "http://dut.udn.vn/Phong/VatChat",
                "Phòng Thanh tra - Pháp chế", "ĐC: Phòng S04.05", "ĐT: 0236.3740718", "http://dut.udn.vn/Phong/Thanhtra"
        ));

        footerWrapper.add(footer, BorderLayout.CENTER);

        // Copyright line
        JLabel copyright = new JLabel("© 2014 - Trường Đại học Bách Khoa - Đại học Đà Nẵng");
        copyright.setForeground(Color.WHITE);
        copyright.setHorizontalAlignment(SwingConstants.CENTER);
        copyright.setFont(new Font("Arial", Font.PLAIN, 12));
        copyright.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        footerWrapper.add(copyright, BorderLayout.SOUTH);

        return footerWrapper;
    }


    private JPanel createFooterBlock(String title1, String address1, String phone1, String link1,
                                     String title2, String address2, String phone2, String link2) {
        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setBackground(Color.decode("#0850c7"));
        block.setForeground(Color.WHITE);

        block.add(createLabel(title1, true));
        block.add(createLabel(address1, false));
        block.add(createLabel(phone1, true));
        block.add(createLinkLabel(link1));
        block.add(Box.createVerticalStrut(10));
        block.add(createLabel(title2, true));
        block.add(createLabel(address2, false));
        block.add(createLabel(phone2, true));
        block.add(createLinkLabel(link2));

        return block;
    }

    private JLabel createLabel(String text, boolean bold) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, 12));
        return label;
    }

    private JLabel createLinkLabel(String url) {
        JLabel label = new JLabel("<html><a style='color:white;' href='" + url + "'>" + url + "</a></html>");
        label.setForeground(Color.WHITE);
        return label;
    }
}