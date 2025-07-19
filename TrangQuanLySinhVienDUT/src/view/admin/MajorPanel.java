package view.admin;

import dao.DepartmentDAO;
import dao.MajorDAO;
import model.Department;
import model.Major;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MajorPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private MajorDAO majorDAO = new MajorDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();

    public MajorPanel() {
        setLayout(new BorderLayout());

        // Bảng
        tableModel = new DefaultTableModel(new String[]{"Mã ngành", "Tên ngành", "Mã khoa"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Nút
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Thêm ngành");
        JButton editBtn = new JButton("Sửa");
        JButton deleteBtn = new JButton("Xoá");
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> openMajorForm(null));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngành.");
                return;
            }
            String maNganh = (String) tableModel.getValueAt(row, 0);
            String tenNganh = (String) tableModel.getValueAt(row, 1);
            String maKhoa = (String) tableModel.getValueAt(row, 2);

            Major m = new Major();
            m.setMaNganh(maNganh);
            m.setTenNganh(tenNganh);
            m.setMaKhoa(maKhoa);
            openMajorForm(m);
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            String maNganh = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá ngành này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                majorDAO.delete(maNganh);
                loadMajors();
            }
        });

        loadMajors();
    }

    private void loadMajors() {
        tableModel.setRowCount(0);
        List<Major> majors = majorDAO.getAll();
        for (Major m : majors) {
            tableModel.addRow(new Object[]{m.getMaNganh(), m.getTenNganh(), m.getMaKhoa()});
        }
    }

    private void openMajorForm(Major major) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setTitle(major == null ? "Thêm ngành" : "Sửa ngành");
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        JTextField maField = new JTextField();
        JTextField tenField = new JTextField();
        JComboBox<String> khoaCombo = new JComboBox<>();

        // Load khoa từ DB
        List<Department> depts = departmentDAO.getAll();
        for (Department d : depts) {
            khoaCombo.addItem(d.getMaKhoa() + " - " + d.getTenKhoa());
        }

        if (major != null) {
            maField.setText(major.getMaNganh());
            maField.setEnabled(false);
            tenField.setText(major.getTenNganh());

            // chọn đúng khoa hiện tại
            for (int i = 0; i < khoaCombo.getItemCount(); i++) {
                if (khoaCombo.getItemAt(i).startsWith(major.getMaKhoa() + " -")) {
                    khoaCombo.setSelectedIndex(i);
                    break;
                }
            }
        }

        dialog.add(new JLabel("Mã ngành:"));
        dialog.add(maField);
        dialog.add(new JLabel("Tên ngành:"));
        dialog.add(tenField);
        dialog.add(new JLabel("Thuộc khoa:"));
        dialog.add(khoaCombo);

        JButton saveBtn = new JButton("Lưu");
        saveBtn.addActionListener(e -> {
            String ma = maField.getText().trim();
            String ten = tenField.getText().trim();
            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Không được để trống.");
                return;
            }

            String maKhoa = khoaCombo.getSelectedItem().toString().split(" - ")[0];
            Major m = new Major();
            m.setMaNganh(ma);
            m.setTenNganh(ten);
            m.setMaKhoa(maKhoa);

            if (major == null) majorDAO.insert(m);
            else majorDAO.update(m);

            loadMajors();
            dialog.dispose();
        });

        dialog.add(new JLabel()); // khoảng trắng
        dialog.add(saveBtn);
        dialog.setVisible(true);
    }
}
