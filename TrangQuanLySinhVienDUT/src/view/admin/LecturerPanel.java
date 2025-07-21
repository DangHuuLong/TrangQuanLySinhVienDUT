package view.admin;

import controller.LecturerController;
import model.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LecturerPanel extends JPanel {
    private JTable lecturerTable;
    private DefaultTableModel tableModel;
    private LecturerController controller;

    public LecturerPanel() {
        setLayout(new BorderLayout());
        controller = new LecturerController();

        // Table
        String[] columns = {"Mã giảng viên", "Họ tên", "Khoa"};
        tableModel = new DefaultTableModel(columns, 0);
        lecturerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(lecturerTable);

        // Buttons
        JButton addBtn = new JButton("Thêm giảng viên mới");
        JButton editBtn = new JButton("Sửa thông tin");
        JButton deleteBtn = new JButton("Xoá giảng viên");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadLecturers();

        // Sự kiện nút
        addBtn.addActionListener(e -> {
            new LecturerAddDialog(SwingUtilities.getWindowAncestor(this), lecturer -> {
                try {
					if (controller.insertLecturer(lecturer)) {
					    JOptionPane.showMessageDialog(this, "Thêm thành công!");
					    loadLecturers();
					} else {
					    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            });
        });
        editBtn.addActionListener(e -> {
            String lecturerCode = getSelectedLecturerCode();
            if (!lecturerCode.isEmpty()) {
                controller.showEditLecturerDialog(SwingUtilities.getWindowAncestor(this), lecturerCode, this::loadLecturers);
            }
        });
        deleteBtn.addActionListener(e -> {
			try {
				controller.deleteLecturer(SwingUtilities.getWindowAncestor(this), getSelectedLecturerCode(), this::loadLecturers);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
    }

    private void loadLecturers() {
        tableModel.setRowCount(0);
        List<Lecturer> lecturers = controller.getAllLecturers();
        for (Lecturer lecturer : lecturers) {
            tableModel.addRow(new Object[]{lecturer.getLecturerCode(), lecturer.getFullName(), lecturer.getMaKhoa()});
        }
    }

    private String getSelectedLecturerCode() {
        int row = lecturerTable.getSelectedRow();
        if (row >= 0) {
            return (String) tableModel.getValueAt(row, 0);
        }
        JOptionPane.showMessageDialog(this, "Vui lòng chọn giảng viên.");
        return "";  
    }

}
