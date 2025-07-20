package view.admin;

import model.*;
import dao.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.List;

public class StudentAddDialog extends JDialog {
	private DepartmentDAO departmentDAO = new DepartmentDAO();
	private MajorDAO majorDAO = new MajorDAO();

	private JComboBox<String> khoaCombo, nganhCombo;
    private JTextField codeField, nameField, classField;

    
    public StudentAddDialog(Window parent, Consumer<Student> onSubmit) {
        super(parent instanceof Frame ? (Frame) parent : null, "Thêm sinh viên mới", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        

        form.add(new JLabel("Mã sinh viên:"));
        codeField = new JTextField();
        form.add(codeField);

        form.add(new JLabel("Họ tên:"));
        nameField = new JTextField();
        form.add(nameField);
        
        form.add(new JLabel("Khoa:"));
        khoaCombo = new JComboBox<>();
        form.add(khoaCombo);

        form.add(new JLabel("Ngành:"));
        nganhCombo = new JComboBox<>();
        form.add(nganhCombo);
        
        form.add(new JLabel("Lớp:"));
        classField = new JTextField();
        form.add(classField);

        JButton addBtn = new JButton("Thêm");
        addBtn.addActionListener(e -> {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String className = classField.getText().trim();

            if (code.isEmpty() || name.isEmpty() || className.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            String selectedKhoaTen = (String) khoaCombo.getSelectedItem();
            String selectedNganhTen = (String) nganhCombo.getSelectedItem();

            String maKhoa = departmentDAO.getAll().stream()
                .filter(d -> d.getTenKhoa().equals(selectedKhoaTen))
                .map(Department::getMaKhoa)
                .findFirst().orElse(null);

            String maNganh = majorDAO.getAll().stream()
                .filter(m -> m.getTenNganh().equals(selectedNganhTen))
                .map(Major::getMaNganh)
                .findFirst().orElse(null);

            if (maKhoa == null || maNganh == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khoa và ngành hợp lệ.");
                return;
            }


            Student student = new Student();
            student.setStudentCode(code);
            student.setFullName(name);
            student.setClassName(className);
            student.setUsername(code);   
            student.setPassword(code);   
            student.setRole("student");

            student.setMaKhoa(maKhoa);
            student.setMaNganh(maNganh);

            onSubmit.accept(student);
            dispose();
        });
        
        

        JPanel bottom = new JPanel();
        bottom.add(addBtn);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadDepartmentsAndMajors();
        setVisible(true);
    }
    
    private void loadDepartmentsAndMajors() {
        List<Department> departments = departmentDAO.getAll();
        for (Department d : departments) {
            khoaCombo.addItem(d.getTenKhoa());
        }

        khoaCombo.addActionListener(e -> {
            String selectedKhoa = (String) khoaCombo.getSelectedItem();
            nganhCombo.removeAllItems();

            String maKhoa = departments.stream()
                    .filter(d -> d.getTenKhoa().equals(selectedKhoa))
                    .map(Department::getMaKhoa)
                    .findFirst()
                    .orElse(null);

            if (maKhoa != null) {
                List<Major> majors = majorDAO.getMajorsByDepartment(maKhoa);
                for (Major m : majors) {
                    nganhCombo.addItem(m.getTenNganh());
                }
            }
        });

        if (!departments.isEmpty()) {
            khoaCombo.setSelectedIndex(0); 
        }
    }

}


