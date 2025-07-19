package view.admin;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class StudentAddDialog extends JDialog {
    private JTextField codeField, nameField;

    public StudentAddDialog(Window parent, Consumer<Student> onSubmit) {
        super(parent instanceof Frame ? (Frame) parent : null, "Thêm sinh viên mới", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        form.add(new JLabel("Mã sinh viên:"));
        codeField = new JTextField();
        form.add(codeField);

        form.add(new JLabel("Họ tên:"));
        nameField = new JTextField();
        form.add(nameField);

        JButton addBtn = new JButton("Thêm");
        addBtn.addActionListener(e -> {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();

            if (code.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            Student student = new Student();
            student.setStudentCode(code);
            student.setFullName(name);
            student.setUsername(code);   
            student.setPassword(code);   
            student.setRole("student");

            onSubmit.accept(student);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(addBtn);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
