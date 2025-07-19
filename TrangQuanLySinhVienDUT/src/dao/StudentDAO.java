package dao;

import model.Student;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students JOIN users ON students.id = users.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setRole(rs.getString("role"));
                s.setStudentCode(rs.getString("student_code"));
                s.setFullName(rs.getString("full_name"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertBasic(Student s) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insert vào users
            String userInsert = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement uStmt = conn.prepareStatement(userInsert, Statement.RETURN_GENERATED_KEYS)) {
                uStmt.setString(1, s.getUsername());
                uStmt.setString(2, s.getPassword());
                uStmt.setString(3, s.getRole());
                uStmt.executeUpdate();

                ResultSet rs = uStmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    s.setId(userId); 
                }
            }


            // Insert vào students
            String studentInsert = "INSERT INTO students (id, student_code, full_name) VALUES (?, ?, ?)";
            try (PreparedStatement sStmt = conn.prepareStatement(studentInsert)) {
                sStmt.setInt(1, s.getId());
                sStmt.setString(2, s.getStudentCode());
                sStmt.setString(3, s.getFullName());
                sStmt.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateField(int id, String field, Object value) {
        String sql = "UPDATE students SET " + field + " = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, value);
            stmt.setInt(2, id);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteById(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();

            // Xoá luôn user nếu có
            try (PreparedStatement userStmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
                userStmt.setInt(1, id);
                userStmt.executeUpdate();
            }

            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}