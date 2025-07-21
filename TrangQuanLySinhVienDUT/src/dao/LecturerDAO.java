package dao;

import model.Lecturer;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class LecturerDAO {

    public List<Lecturer> getAll() {
        List<Lecturer> list = new ArrayList<>();
        String sql = "SELECT * FROM lecturers JOIN users ON lecturers.id = users.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Lecturer lecturer = new Lecturer();
                lecturer.setId(rs.getInt("id"));
                lecturer.setLecturerCode(rs.getString("lecturer_code"));
                lecturer.setFullName(rs.getString("full_name"));
                lecturer.setMaKhoa(rs.getString("ma_khoa"));
                lecturer.setUsername(rs.getString("username"));
                lecturer.setPassword(rs.getString("password"));
                lecturer.setRole(rs.getString("role"));
                list.add(lecturer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertLecturer(Lecturer lecturer) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  

            String userInsert = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement uStmt = conn.prepareStatement(userInsert, Statement.RETURN_GENERATED_KEYS)) {
                uStmt.setString(1, lecturer.getUsername());
                uStmt.setString(2, lecturer.getPassword());
                uStmt.setString(3, lecturer.getRole());
                uStmt.executeUpdate();

                ResultSet rs = uStmt.getGeneratedKeys();
                if (rs.next()) {
                    lecturer.setId(rs.getInt(1)); 
                } else {
                    throw new SQLException("Không lấy được ID người dùng từ bảng users.");
                }
            }

            String lecturerInsert = """ 
                INSERT INTO lecturers (id, lecturer_code, full_name, ma_khoa)
                VALUES (?, ?, ?, ?)
            """;
            try (PreparedStatement stmt = conn.prepareStatement(lecturerInsert)) {
                stmt.setInt(1, lecturer.getId());  
                stmt.setString(2, lecturer.getLecturerCode());
                stmt.setString(3, lecturer.getFullName());
                stmt.setString(4, lecturer.getMaKhoa());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Không thể thêm giảng viên vào bảng lecturers");
                }
            }

            conn.commit();  
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();  
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);  
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean update(Lecturer lecturer) throws Exception {
        String sql = "UPDATE lecturers SET lecturer_code = ?, full_name = ?, ma_khoa = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturer.getLecturerCode());
            stmt.setString(2, lecturer.getFullName());
            stmt.setString(3, lecturer.getMaKhoa());
            stmt.setInt(4, lecturer.getId()); 

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String lecturerCode) throws Exception {
        String sql = "DELETE FROM lecturers WHERE lecturer_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturerCode);
            return stmt.executeUpdate() > 0;  
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Lecturer getLecturerByCode(String lecturerCode) {
        Lecturer lecturer = null;
        String sql = "SELECT * FROM lecturers WHERE lecturer_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lecturerCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                lecturer = new Lecturer();
                lecturer.setId(rs.getInt("id"));
                lecturer.setLecturerCode(rs.getString("lecturer_code"));
                lecturer.setFullName(rs.getString("full_name"));
                lecturer.setMaKhoa(rs.getString("ma_khoa"));
                lecturer.setUsername(rs.getString("username"));
                lecturer.setPassword(rs.getString("password"));
                lecturer.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return lecturer;
    }
    
    public Lecturer getLecturerById(int id) {
        String sql = "SELECT * FROM lecturers JOIN users ON lecturers.id = users.id WHERE lecturers.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Lecturer lecturer = new Lecturer();
                lecturer.setId(rs.getInt("id"));
                lecturer.setUsername(rs.getString("username"));
                lecturer.setPassword(rs.getString("password"));
                lecturer.setRole(rs.getString("role"));
                lecturer.setLecturerCode(rs.getString("lecturer_code"));
                lecturer.setFullName(rs.getString("full_name"));
                lecturer.setMaKhoa(rs.getString("ma_khoa"));
                
                return lecturer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
