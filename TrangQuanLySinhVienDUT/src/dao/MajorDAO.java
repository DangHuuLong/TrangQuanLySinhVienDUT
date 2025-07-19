package dao;

import model.Major;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {

    public List<Major> getAll() {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM majors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Major m = new Major();
                m.setMaNganh(rs.getString("ma_nganh"));
                m.setTenNganh(rs.getString("ten_nganh"));
                m.setMaKhoa(rs.getString("ma_khoa"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Major m) {
        String sql = "INSERT INTO majors (ma_nganh, ten_nganh, ma_khoa) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getMaNganh());
            stmt.setString(2, m.getTenNganh());
            stmt.setString(3, m.getMaKhoa());
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Major m) {
        String sql = "UPDATE majors SET ten_nganh = ?, ma_khoa = ? WHERE ma_nganh = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getTenNganh());
            stmt.setString(2, m.getMaKhoa());
            stmt.setString(3, m.getMaNganh());
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maNganh) {
        String sql = "DELETE FROM majors WHERE ma_nganh = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNganh);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
