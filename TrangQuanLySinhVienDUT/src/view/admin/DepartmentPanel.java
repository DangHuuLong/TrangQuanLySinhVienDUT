package view.admin;

import dao.DepartmentDAO;
import model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DepartmentDAO dao = new DepartmentDAO();

    public DepartmentPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"Mã Khoa", "Tên Khoa"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Thêm Khoa");
        JButton editBtn = new JButton("Sửa");
        JButton deleteBtn = new JButton("Xoá");
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(scroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadData();

        addBtn.addActionListener(e -> {
            JTextField maKhoaField = new JTextField();
            JTextField tenKhoaField = new JTextField();
            JPanel input = new JPanel(new GridLayout(2, 2));
            input.add(new JLabel("Mã khoa:"));
            input.add(maKhoaField);
            input.add(new JLabel("Tên khoa:"));
            input.add(tenKhoaField);

            int result = JOptionPane.showConfirmDialog(this, input, "Thêm khoa mới", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Department d = new Department();
                d.setMaKhoa(maKhoaField.getText().trim());
                d.setTenKhoa(tenKhoaField.getText().trim());
                if (!d.getMaKhoa().isEmpty() && !d.getTenKhoa().isEmpty()) {
                    dao.insert(d);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Không được để trống.");
                }
            }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khoa.");
                return;
            }
            String maKhoa = (String) model.getValueAt(row, 0);
            String currentTenKhoa = (String) model.getValueAt(row, 1);
            JTextField tenKhoaField = new JTextField(currentTenKhoa);

            int result = JOptionPane.showConfirmDialog(this, tenKhoaField, "Sửa tên khoa", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String newTenKhoa = tenKhoaField.getText().trim();
                if (!newTenKhoa.isEmpty()) {
                    dao.updateTenKhoa(maKhoa, newTenKhoa);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Tên khoa không được trống.");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khoa để xoá.");
                return;
            }
            String maKhoa = (String) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá khoa " + maKhoa + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.delete(maKhoa);
                loadData();
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        List<Department> list = dao.getAll();
        for (Department d : list) {
            model.addRow(new Object[]{d.getMaKhoa(), d.getTenKhoa()});
        }
    }
}
