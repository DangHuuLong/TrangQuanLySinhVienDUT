package view.admin;

import dao.DepartmentDAO;
import model.Department;
import model.Lecturer;

import javax.swing.*;

import controller.LecturerController;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LecturerEditDialog extends JDialog {
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final Map<String, JComponent> fieldInputs = new HashMap<>();
    private final JComboBox<String> departmentComboBox = new JComboBox<>();
    private final Map<String, String> tenKhoaToMaKhoa = new HashMap<>();
    private int currentRow = 0;
    private final Lecturer lecturer;

    public LecturerEditDialog(Window parent, Lecturer lecturer, Runnable onUpdate) {
        super(parent instanceof Frame ? (Frame) parent : null, "Chỉnh sửa giảng viên", true);
        this.lecturer = lecturer;

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm trường Tên giảng viên
        addField(formPanel, "Tên giảng viên", "full_name", new JTextField(lecturer.getFullName()));
        
        // Tạo combo box chọn Khoa
        addLabelAndCombo(formPanel, "Khoa", "ma_khoa", departmentComboBox);
        loadDepartments(lecturer.getMaKhoa());

        JButton saveBtn = new JButton("Lưu thay đổi");
        saveBtn.addActionListener(e -> {
            // Cập nhật thông tin giảng viên
            lecturer.setFullName(((JTextField) fieldInputs.get("full_name")).getText());
            lecturer.setMaKhoa(tenKhoaToMaKhoa.get(departmentComboBox.getSelectedItem()));

            if (lecturer.getFullName().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật giảng viên và gọi onUpdate
            try {
                LecturerController controller = new LecturerController();
                controller.updateLecturer(lecturer);  // Cập nhật giảng viên
                onUpdate.run();  // Cập nhật lại bảng giảng viên
                dispose();  // Đóng cửa sổ sau khi lưu thành công
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa giảng viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
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

    private void loadDepartments(String selectedMaKhoa) {
        departmentComboBox.removeAllItems();
        tenKhoaToMaKhoa.clear();
        List<Department> list = departmentDAO.getAll();
        for (Department dept : list) {
            String name = dept.getTenKhoa();
            String id = dept.getMaKhoa();
            tenKhoaToMaKhoa.put(name, id);
            departmentComboBox.addItem(name);
            if (id.equals(selectedMaKhoa)) {
                departmentComboBox.setSelectedItem(name);
            }
        }
    }
}
