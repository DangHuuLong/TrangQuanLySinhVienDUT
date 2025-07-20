package view.admin;

import controller.StudentController;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentController controller;

    public StudentPanel() {
        setLayout(new BorderLayout());
        controller = new StudentController();

        // Table
        String[] columns = {"Mã SV", "Họ tên"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Buttons
        JButton addBtn = new JButton("Thêm SV mới");
        JButton editBtn = new JButton("Sửa thông tin");
        JButton deleteBtn = new JButton("Xoá sinh viên");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadStudents();

        // Sự kiện nút
        addBtn.addActionListener(e -> {
            new StudentAddDialog(SwingUtilities.getWindowAncestor(this), student -> {
                if (controller.insertStudent(student)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    loadStudents();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }
            });
        });

        editBtn.addActionListener(e -> controller.showEditStudentDialog(SwingUtilities.getWindowAncestor(this), getSelectedStudentId(), this::loadStudents));
        deleteBtn.addActionListener(e -> controller.deleteStudent(SwingUtilities.getWindowAncestor(this), getSelectedStudentId(), this::loadStudents));
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = controller.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getStudentCode(), s.getFullName()});
        }
    }

    private int getSelectedStudentId() {
        int row = studentTable.getSelectedRow();
        if (row >= 0) {
            String selectedCode = (String) tableModel.getValueAt(row, 0);
            return controller.getIdByStudentCode(selectedCode); 	
        }
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên.");
        return -1;
    }

}
