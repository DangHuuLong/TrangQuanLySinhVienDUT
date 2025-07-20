package controller;

import dao.StudentDAO;
import model.Student;
import view.admin.StudentAddDialog;
import view.admin.StudentEditDialog;

import javax.swing.*;

import java.awt.Window;
import java.util.List;
import java.util.function.Consumer;

public class StudentController {
    private final StudentDAO dao = new StudentDAO();

    public List<Student> getAllStudents() {
        return dao.getAll();
    }

    public void showAddStudentDialog(Window parent, Runnable onSuccess) {
        new StudentAddDialog(parent, student -> {
            dao.insertBasic(student);
            onSuccess.run();
        });
    }

    public void showEditStudentDialog(Window parent, int id, Runnable onSuccess) {
        if (id <= 0) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn sinh viên để sửa.");
            return;
        }
        new StudentEditDialog(parent, id, () -> {
            getAllStudents();
        });

    }

    public void deleteStudent(Window parent, int id, Runnable onSuccess) {
        if (id <= 0) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn sinh viên để xoá.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(parent, "Xác nhận xoá sinh viên?", "Xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = dao.deleteById(id);
            if (deleted) {
                onSuccess.run();
            } else {
                JOptionPane.showMessageDialog(parent, "Không thể xoá sinh viên.");
            }
        }
    }
    
    public boolean insertStudent(Student student) {
        return dao.insertBasic(student);
    }
    
    public int getIdByStudentCode(String studentCode) {
        return dao.getIdByStudentCode(studentCode);
    }


}
