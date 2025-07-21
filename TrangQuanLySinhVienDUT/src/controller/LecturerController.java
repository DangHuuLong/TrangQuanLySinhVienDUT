package controller;

import dao.LecturerDAO;
import model.Lecturer;
import view.admin.LecturerAddDialog;
import view.admin.LecturerEditDialog;

import javax.swing.*;
import java.awt.Window;
import java.util.List;
import java.util.function.Consumer;

public class LecturerController {
    private final LecturerDAO dao = new LecturerDAO();

    public List<Lecturer> getAllLecturers() {
        return dao.getAll();
    }

    public void showAddLecturerDialog(Window parent, Runnable onSuccess) {
        new LecturerAddDialog(parent, lecturer -> {
            try {
				insertLecturer(lecturer);
			} catch (Exception e) {
				e.printStackTrace();
			}  
            onSuccess.run();
        });
    }
    
    public void showEditLecturerDialog(Window parent, String lecturerCode, Runnable onSuccess) {
        if (lecturerCode == null || lecturerCode.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn giảng viên để sửa.");
            return;
        }

        Lecturer lecturer = dao.getLecturerByCode(lecturerCode);
        
        if (lecturer == null) {
            JOptionPane.showMessageDialog(parent, "Không tìm thấy giảng viên với mã " + lecturerCode, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new LecturerEditDialog(parent, lecturer, onSuccess);
    }


    public void deleteLecturer(Window parent, String lecturerCode, Runnable onSuccess) throws Exception {
        if (lecturerCode == null || lecturerCode.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn giảng viên để xoá.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(parent, "Xác nhận xoá giảng viên?", "Xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = dao.delete(lecturerCode);
            if (deleted) {
                onSuccess.run();  
            } else {
                JOptionPane.showMessageDialog(parent, "Không thể xoá giảng viên.");
            }
        }
    }

    public boolean insertLecturer(Lecturer lecturer) throws Exception {
        return dao.insertLecturer(lecturer);  
    }

    public boolean updateLecturer(Lecturer lecturer) throws Exception {
        return dao.update(lecturer);  
    }

    public Lecturer getLecturerByCode(String lecturerCode) {
        return dao.getLecturerByCode(lecturerCode);
    }
}
