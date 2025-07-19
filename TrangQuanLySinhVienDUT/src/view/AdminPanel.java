package view;

import controller.StudentController;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Student;

public class AdminPanel extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentController controller;

    public AdminPanel(User admin) {
        setTitle("Quản lý sinh viên - Admin");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        controller = new StudentController();

        // Table
        String[] columns = {"ID", "Mã SV", "Họ tên"};
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
        addBtn.addActionListener(e -> controller.showAddStudentDialog(this, this::loadStudents));
        editBtn.addActionListener(e -> controller.showEditStudentDialog(this, getSelectedStudentId(), this::loadStudents));
        deleteBtn.addActionListener(e -> controller.deleteStudent(this, getSelectedStudentId(), this::loadStudents));

        setVisible(true);
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = controller.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getId(), s.getStudentCode(), s.getFullName()});
        }
    }

    private int getSelectedStudentId() {
        int row = studentTable.getSelectedRow();
        if (row >= 0) {
            return (int) tableModel.getValueAt(row, 0);
        }
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên.");
        return -1;
    }
}
