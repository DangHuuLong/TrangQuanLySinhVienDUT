package view.admin;

import dao.DepartmentDAO;
import dao.MajorDAO;
import dao.StudentDAO;
import model.Department;
import model.Major;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentEditDialog extends JDialog {
    private final StudentDAO studentDAO = new StudentDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final MajorDAO majorDAO = new MajorDAO();

    private final Map<String, JComponent> fieldInputs = new HashMap<>();
    private final JComboBox<String> khoaCombo = new JComboBox<>();
    private final JComboBox<String> nganhCombo = new JComboBox<>();
    private final Map<String, String> tenKhoaToMaKhoa = new HashMap<>();
    private final Map<String, String> tenNganhToMaNganh = new HashMap<>();

    private int currentRow = 0;

    public StudentEditDialog(Window parent, int studentId, Runnable onUpdate) {
        super(parent instanceof Frame ? (Frame) parent : null, "Chỉnh sửa sinh viên", true);
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        Student student = studentDAO.getById(studentId);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addField(formPanel, "Họ tên", "full_name", new JTextField(student.getFullName()));
        addField(formPanel, "Ngày sinh", "dob", new JTextField(student.getDob()));
        addField(formPanel, "Nơi sinh", "place_of_birth", new JTextField(student.getPlaceOfBirth()));
        addField(formPanel, "Giới tính", "gender", new JTextField(student.getGender()));
        addField(formPanel, "Dân tộc", "ethnicity", new JTextField(student.getEthnicity()));
        addField(formPanel, "Quốc tịch", "nationality", new JTextField(student.getNationality()));
        addField(formPanel, "Tôn giáo", "religion", new JTextField(student.getReligion()));
        addField(formPanel, "Tài khoản cá nhân", "personal_account", new JTextField(student.getPersonalAccount()));
        addField(formPanel, "Ngân hàng", "bank_name", new JTextField(student.getBankName()));
        addField(formPanel, "Mã BHYT", "bhyt_code", new JTextField(student.getBhytCode()));
        addField(formPanel, "Hạn BHYT", "bhyt_valid_until", new JTextField(student.getBhytValidUntil()));
        addField(formPanel, "Miễn BHYT", "free_bhyt", new JTextField(String.valueOf(student.isFreeBhyt())));
        addField(formPanel, "Lớp", "class_name", new JTextField(student.getClassName()));
        addField(formPanel, "Chương trình đào tạo", "program", new JTextField(student.getProgram()));
        addField(formPanel, "Chương trình 2", "program_2", new JTextField(student.getProgram2()));
        addField(formPanel, "Tài khoản Office365", "office365_account", new JTextField(student.getOffice365Account()));
        addField(formPanel, "Email cá nhân", "personal_email", new JTextField(student.getPersonalEmail()));
        addField(formPanel, "Mật khẩu Office365", "office365_default_pass", new JTextField(student.getOffice365DefaultPass()));
        addField(formPanel, "Facebook", "facebook_url", new JTextField(student.getFacebookUrl()));
        addField(formPanel, "Điện thoại", "phone", new JTextField(student.getPhone()));
        addField(formPanel, "Địa chỉ cư trú", "address", new JTextField(student.getAddress()));
        addField(formPanel, "Ghi chú địa chỉ", "address_note", new JTextField(student.getAddressNote()));
        addField(formPanel, "Tỉnh/Thành phố", "city", new JTextField(student.getCity()));
        addField(formPanel, "Quận/Huyện", "district", new JTextField(student.getDistrict()));
        addField(formPanel, "Xã/Phường", "ward", new JTextField(student.getWard()));
        addField(formPanel, "Ảnh thẻ", "avatar_path", new JTextField(student.getAvatarPath()));

        // ===== Khoa =====
        addLabelAndCombo(formPanel, "Khoa", "ma_khoa", khoaCombo);
        loadKhoaCombo(student.getMaKhoa());

        // ===== Ngành =====
        addLabelAndCombo(formPanel, "Ngành", "ma_nganh", nganhCombo);
        loadNganhCombo(student.getMaKhoa(), student.getMaNganh());

        khoaCombo.addActionListener(e -> {
            String selectedTenKhoa = (String) khoaCombo.getSelectedItem();
            String maKhoa = tenKhoaToMaKhoa.get(selectedTenKhoa);
            loadNganhCombo(maKhoa, null);
        });

        JButton saveBtn = new JButton("Lưu thay đổi");
        saveBtn.addActionListener(e -> {
            for (Map.Entry<String, JComponent> entry : fieldInputs.entrySet()) {
                String field = entry.getKey();
                JComponent comp = entry.getValue();
                String value;

                if (comp instanceof JTextField) {
                    value = ((JTextField) comp).getText().trim();
                } else if (comp instanceof JComboBox) {
                    String ten = (String) ((JComboBox<?>) comp).getSelectedItem();
                    if (field.equals("ma_khoa")) value = tenKhoaToMaKhoa.get(ten);
                    else value = tenNganhToMaNganh.get(ten);
                } else {
                    continue;
                }

                if (value != null && !value.isEmpty()) {
                    studentDAO.updateField(studentId, field, value);
                }
            }

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onUpdate.run();
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(saveBtn);

        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void addField(JPanel panel, String label, String key, JTextField field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        gbc.gridx = 0;
        gbc.gridy = currentRow;
        gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);

        // Input
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);

        fieldInputs.put(key, field);
        currentRow++;
    }

    private void addLabelAndCombo(JPanel panel, String label, String key, JComboBox<String> comboBox) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        gbc.gridx = 0;
        gbc.gridy = currentRow;
        gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);

        // ComboBox
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(comboBox, gbc);

        fieldInputs.put(key, comboBox);
        currentRow++;
    }

    private void loadKhoaCombo(String selectedMaKhoa) {
        khoaCombo.removeAllItems();
        tenKhoaToMaKhoa.clear();
        List<Department> list = departmentDAO.getAll();
        for (Department dept : list) {
            String name = dept.getTenKhoa();
            String id = dept.getMaKhoa();
            tenKhoaToMaKhoa.put(name, id);
            khoaCombo.addItem(name);
            if (id.equals(selectedMaKhoa)) {
                khoaCombo.setSelectedItem(name);
            }
        }
    }

    private void loadNganhCombo(String maKhoa, String selectedMaNganh) {
        nganhCombo.removeAllItems();
        tenNganhToMaNganh.clear();
        List<Major> list = majorDAO.getMajorsByDepartment(maKhoa);
        for (Major major : list) {
            String name = major.getTenNganh();
            String id = major.getMaNganh();
            tenNganhToMaNganh.put(name, id);
            nganhCombo.addItem(name);
            if (id != null && id.equals(selectedMaNganh)) {
                nganhCombo.setSelectedItem(name);
            }
        }
    }
}
