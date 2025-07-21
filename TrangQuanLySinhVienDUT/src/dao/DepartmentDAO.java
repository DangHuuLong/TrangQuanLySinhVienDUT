package dao;

import model.Department;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Department d = new Department();
                d.setMaKhoa(rs.getString("ma_khoa"));
                d.setTenKhoa(rs.getString("ten_khoa"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Department d) {
        String sql = "INSERT INTO departments (ma_khoa, ten_khoa) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getMaKhoa());
            stmt.setString(2, d.getTenKhoa());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Department d) {
        String sql = "UPDATE departments SET ten_khoa = ? WHERE ma_khoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getTenKhoa());
            stmt.setString(2, d.getMaKhoa());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maKhoa) {
        String sql = "DELETE FROM departments WHERE ma_khoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKhoa);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateTenKhoa(String maKhoa, String tenKhoa) {
        String sql = "UPDATE departments SET ten_khoa = ? WHERE ma_khoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenKhoa);
            stmt.setString(2, maKhoa);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String getDepartmentIdByName(String departmentName) throws Exception {
        String departmentId = null;
        String sql = "SELECT ma_khoa FROM departments WHERE ten_khoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, departmentName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                departmentId = rs.getString("ma_khoa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Database error occurred while retrieving department ID", e);  
        }
        return departmentId;
    }

}
