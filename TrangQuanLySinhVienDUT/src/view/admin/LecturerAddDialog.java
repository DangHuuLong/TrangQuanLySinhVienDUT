package view.admin;

import model.Department;
import model.Lecturer;
import dao.DepartmentDAO;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import controller.LecturerController;

public class LecturerAddDialog extends JDialog {
	private DepartmentDAO departmentDAO = new DepartmentDAO();
    private JTextField lecturerCodeField, fullNameField;
    private JComboBox<String> departmentComboBox;

    public LecturerAddDialog(Window parent, Consumer<Lecturer> onSubmit) {
        super(parent instanceof Frame ? (Frame) parent : null, "Thêm giảng viên mới", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        form.add(new JLabel("Mã giảng viên:"));
        lecturerCodeField = new JTextField();
        form.add(lecturerCodeField);

        form.add(new JLabel("Tên giảng viên:"));
        fullNameField = new JTextField();
        form.add(fullNameField);

        form.add(new JLabel("Khoa:"));
        departmentComboBox = new JComboBox<>();
        loadDepartments();
        form.add(departmentComboBox);

        JButton addBtn = new JButton("Thêm");
        addBtn.addActionListener(e -> {
            String lecturerCode = lecturerCodeField.getText().trim();
            String fullName = fullNameField.getText().trim();

            if (lecturerCode.isEmpty() || fullName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            String selectedDepartment = (String) departmentComboBox.getSelectedItem();
			String departmentId = departmentDAO.getAll().stream()
                .filter(d -> d.getTenKhoa().equals(selectedDepartment))
                .map(Department::getMaKhoa)
                .findFirst().orElse(null);

            if (departmentId == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khoa hợp lệ.");
                return;
            }

            Lecturer lecturer = new Lecturer();
            lecturer.setLecturerCode(lecturerCode);
            lecturer.setFullName(fullName);
            lecturer.setMaKhoa(departmentId);
            lecturer.setUsername(lecturerCode);  // Đặt username là mã giảng viên
            lecturer.setPassword(lecturerCode);  // Đặt password là mã giảng viên
            lecturer.setRole("lecturer");

            onSubmit.accept(lecturer);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(addBtn);
        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void loadDepartments() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        var departments = departmentDAO.getAll();
        for (var department : departments) {
            departmentComboBox.addItem(department.getTenKhoa());
        }
    }
}
