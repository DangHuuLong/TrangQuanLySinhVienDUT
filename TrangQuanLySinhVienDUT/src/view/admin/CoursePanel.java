package view.admin;

import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.MajorDAO;
import model.Course;
import model.Department;
import model.Major;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursePanel extends JPanel {
	private JTable table;
    private DefaultTableModel tableModel;
    private CourseDAO courseDAO = new CourseDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();

    public CoursePanel() {
        setLayout(new BorderLayout());

        // Bảng
        tableModel = new DefaultTableModel(new String[]{"Khoa giảng dạy", "Tên học phần", 
        		"Mã học phần", "Tổng", "Lý thuyết", "Thực hành", "Thực tập", "Đồ án", "Tóm tắt nội dung"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Nút
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Thêm học phần");
        JButton editBtn = new JButton("Sửa");
        JButton deleteBtn = new JButton("Xoá");
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> openCourseForm(null));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn học phần.");
                return;
            }
            String maKhoa = (String) tableModel.getValueAt(row, 0);
            String tenHocPhan = (String) tableModel.getValueAt(row, 1);
            String maHocPhan = (String) tableModel.getValueAt(row, 2);
            String tongTinChi = tableModel.getValueAt(row, 3).toString();
            String lyThuyet = tableModel.getValueAt(row, 4).toString();
            String thucHanh = tableModel.getValueAt(row, 5).toString();
            String thucTap = tableModel.getValueAt(row, 6).toString();
            String doAn = tableModel.getValueAt(row, 7).toString();
            String moTa = (String) tableModel.getValueAt(row, 8);

            Course course = new Course();
            course.setDepartmentCode(maKhoa);
            course.setCourseName(tenHocPhan);
            course.setCourseCode(maHocPhan);
            course.setTotalCredits(Integer.parseInt(tongTinChi));
            course.setTheoryCredits(Integer.parseInt(lyThuyet));
            course.setPracticeCredits(Integer.parseInt(thucHanh));
            course.setInternshipCredits(Integer.parseInt(thucTap));
            course.setProjectCredits(Integer.parseInt(doAn));
            course.setCourseDescription(moTa);
            openCourseForm(course);
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            String maHocPhan = (String) tableModel.getValueAt(row, 2);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá học phần này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
            	courseDAO.delete(maHocPhan);
                loadCourse();
            }
        });

        loadCourse();
    }

    private void loadCourse() {
        tableModel.setRowCount(0);
        List<Course> courses = courseDAO.getAll();
        for (Course c : courses) {
            tableModel.addRow(new Object[]{c.getDepartmentCode(), c.getCourseName(), 
            		c.getCourseCode(), c.getTotalCredits(), c.getTheoryCredits(), 
            		c.getPracticeCredits(), c.getInternshipCredits(), c.getProjectCredits(), c.getCourseDescription()});
        }
    }

    private void openCourseForm(Course course) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setTitle(course == null ? "Thêm học phần" : "Sửa học phần");
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(9, 2, 10, 10));

        JTextField maField = new JTextField();
        JTextField tenField = new JTextField();
        JTextField lythuyetField = new JTextField();
        JTextField thuchanhField = new JTextField();
        JTextField thuctapField = new JTextField();
        JTextField doanField = new JTextField();
        JTextField motaField = new JTextField();
        
        JComboBox<String> khoaCombo = new JComboBox<>();

        // Load khoa từ DB
        List<Department> depts = departmentDAO.getAll();
        for (Department d : depts) {
            khoaCombo.addItem(d.getMaKhoa() + " - " + d.getTenKhoa());
        }

        if (course != null) {
            maField.setText(course.getCourseCode());
            maField.setEnabled(false);
            tenField.setText(course.getCourseName());
            lythuyetField.setText(String.valueOf(course.getTheoryCredits()));
            thuchanhField.setText(String.valueOf(course.getPracticeCredits()));
            thuctapField.setText(String.valueOf(course.getInternshipCredits()));
            doanField.setText(String.valueOf(course.getProjectCredits()));
            motaField.setText(String.valueOf(course.getCourseDescription()));

            // chọn đúng khoa hiện tại
            for (int i = 0; i < khoaCombo.getItemCount(); i++) {
                if (khoaCombo.getItemAt(i).startsWith(course.getDepartmentCode() + " -")) {
                    khoaCombo.setSelectedIndex(i);
                    break;
                }
            }
        }

        dialog.add(new JLabel("Mã học phần:"));
        dialog.add(maField);
        dialog.add(new JLabel("Tên học phần:"));
        dialog.add(tenField);
        dialog.add(new JLabel("Thuộc khoa:"));
        dialog.add(khoaCombo);
        dialog.add(new JLabel("Lý thuyết:"));
        dialog.add(lythuyetField);
        dialog.add(new JLabel("Thực hành:"));
        dialog.add(thuchanhField);
        dialog.add(new JLabel("Thực tập:"));
        dialog.add(thuctapField);
        dialog.add(new JLabel("Đồ án:"));
        dialog.add(doanField);
        dialog.add(new JLabel("Mô tả:"));
        dialog.add(motaField);

        JButton saveBtn = new JButton("Lưu");
        saveBtn.addActionListener(e -> {
            String ma = maField.getText().trim();
            String ten = tenField.getText().trim();
            String lythuyet = lythuyetField.getText().trim();
            String thuchanh = thuchanhField.getText().trim();
            String thuctap = thuctapField.getText().trim();
            String doan = doanField.getText().trim();
            String mota = motaField.getText().trim();
            if (ma.isEmpty() || ten.isEmpty() || lythuyet.isEmpty() || thuchanh.isEmpty() || 
            		thuctap.isEmpty() || doan.isEmpty() || mota.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Không được để trống.");
                return;
            }

            String maKhoa = khoaCombo.getSelectedItem().toString().split(" - ")[0];
            Course c = new Course();
            c.setDepartmentCode(maKhoa);
            c.setCourseName(ten);
            c.setCourseCode(ma);
            c.setTotalCredits(Integer.parseInt(lythuyet)+Integer.parseInt(thuchanh)+Integer.parseInt(thuctap)+Integer.parseInt(doan));
            c.setTheoryCredits(Integer.parseInt(lythuyet));
            c.setPracticeCredits(Integer.parseInt(thuchanh));
            c.setInternshipCredits(Integer.parseInt(thuctap));
            c.setProjectCredits(Integer.parseInt(doan));
            c.setCourseDescription(mota);

            if (course == null) courseDAO.insertCourse(c);
            else courseDAO.updateCourse(c);

            loadCourse();
            dialog.dispose();
        });

        dialog.add(new JLabel()); // khoảng trắng
        dialog.add(saveBtn);
        dialog.setVisible(true);
    }
}
