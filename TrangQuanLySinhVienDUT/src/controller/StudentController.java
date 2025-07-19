package controller;

import dao.StudentDAO;
import model.Student;
import view.dialog.StudentAddDialog;
import view.dialog.StudentEditDialog;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class StudentController {
    private final StudentDAO dao = new StudentDAO();

    public List<Student> getAllStudents() {
        return dao.getAll();
    }

    public void showAddStudentDialog(JFrame parent, Runnable onSuccess) {
        new StudentAddDialog(parent, student -> {
            dao.insertBasic(student);
            onSuccess.run();
        });
    }

    public void showEditStudentDialog(JFrame parent, int id, Runnable onSuccess) {
        if (id <= 0) return;
        new StudentEditDialog(parent, id, (field, value) -> {
            dao.updateField(id, field, value);
            onSuccess.run();
        });
    }

    public void deleteStudent(JFrame parent, int id, Runnable onSuccess) {
        if (id <= 0) return;
        int confirm = JOptionPane.showConfirmDialog(parent, "Xác nhận xoá sinh viên?", "Xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.deleteById(id);
            onSuccess.run();
        }
    }
}
