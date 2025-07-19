package view.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class StudentEditDialog extends JDialog {
    private final String[] editableFields = {
        "full_name", "dob", "place_of_birth", "gender", "ethnicity", "nationality",
        "religion", "personal_account", "bank_name", "bhyt_code", "bhyt_valid_until",
        "free_bhyt", "major", "class_name", "program", "program_2", "office365_account",
        "personal_email", "office365_default_pass", "facebook_url", "phone",
        "address", "address_note", "city", "district", "ward", "avatar_path"
    };

    private JComboBox<String> fieldCombo;
    private JTextField valueField;

    public StudentEditDialog(JFrame parent, int studentId, BiConsumer<String, Object> onSubmit) {
        super(parent, "Sửa thông tin sinh viên", true);
        setSize(500, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        form.add(new JLabel("Thuộc tính cần sửa:"));
        fieldCombo = new JComboBox<>(editableFields);
        form.add(fieldCombo);

        form.add(new JLabel("Giá trị mới:"));
        valueField = new JTextField();
        form.add(valueField);

        JButton saveBtn = new JButton("Lưu thay đổi");
        saveBtn.addActionListener(e -> {
            String field = (String) fieldCombo.getSelectedItem();
            String value = valueField.getText().trim();

            if (value.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Giá trị mới không được để trống.");
                return;
            }

            onSubmit.accept(field, value);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(saveBtn);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}