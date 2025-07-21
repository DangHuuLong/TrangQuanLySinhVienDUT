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
	            s.setAvatarPath(rs.getString("avatar_path"));
	            s.setFullName(rs.getString("full_name"));
	            s.setDob(rs.getString("dob"));
	            s.setPlaceOfBirth(rs.getString("place_of_birth"));
	            s.setGender(rs.getString("gender"));
	            s.setEthnicity(rs.getString("ethnicity"));
	            s.setNationality(rs.getString("nationality"));
	            s.setReligion(rs.getString("religion"));
	            s.setPersonalAccount(rs.getString("personal_account"));
	            s.setBankName(rs.getString("bank_name"));
	            s.setBhytCode(rs.getString("bhyt_code"));
	            s.setBhytValidUntil(rs.getString("bhyt_valid_until"));
	            s.setFreeBhyt(rs.getBoolean("free_bhyt"));
	            s.setProgram(s.getProgram());
	            s.setProgram2(rs.getString("program_2"));
	            s.setOffice365Account(rs.getString("office365_account"));
	            s.setPersonalEmail(rs.getString("personal_email"));
	            s.setOffice365DefaultPass(rs.getString("office365_default_pass"));
	            s.setFacebookUrl(rs.getString("facebook_url"));
	            s.setPhone(rs.getString("phone"));
	            s.setAddress(rs.getString("address"));
	            s.setAddressNote(rs.getString("address_note"));
	            s.setCity(rs.getString("city"));
	            s.setDistrict(rs.getString("district"));
	            s.setWard(rs.getString("ward"));
	            s.setMaKhoa(rs.getString("ma_khoa"));
	            s.setMaNganh(rs.getString("ma_nganh"));
	            s.setClassName(rs.getString("class_name")); // dòng bạn cần

	            list.add(s);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


    public boolean insertBasic(Student s) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String userInsert = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement uStmt = conn.prepareStatement(userInsert, Statement.RETURN_GENERATED_KEYS)) {
                uStmt.setString(1, s.getUsername());
                uStmt.setString(2, s.getPassword());
                uStmt.setString(3, s.getRole());
                uStmt.executeUpdate();

                ResultSet rs = uStmt.getGeneratedKeys();
                if (rs.next()) {
                    s.setId(rs.getInt(1));
                }
            }

            String sql = """ 
                INSERT INTO students (
                    id, student_code, full_name, ma_khoa, ma_nganh,
                    dob, place_of_birth, gender, ethnicity, nationality, religion,
                    personal_account, bank_name, bhyt_code, bhyt_valid_until,
                    free_bhyt, program, program_2, office365_account,
                    personal_email, office365_default_pass, facebook_url,
                    phone, address, address_note, city, district, ward, avatar_path, class_name
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, s.getId());
                stmt.setString(2, s.getStudentCode());
                stmt.setString(3, s.getFullName());
                stmt.setString(4, s.getMaKhoa());
                stmt.setString(5, s.getMaNganh());

                if (s.getDob() != null && !s.getDob().isEmpty()) {
                    stmt.setDate(6, Date.valueOf(s.getDob()));
                } else {
                    stmt.setNull(6, Types.DATE);
                }

                stmt.setString(7, s.getPlaceOfBirth());
                stmt.setString(8, s.getGender());
                stmt.setString(9, s.getEthnicity());
                stmt.setString(10, s.getNationality());
                stmt.setString(11, s.getReligion());
                stmt.setString(12, s.getPersonalAccount());
                stmt.setString(13, s.getBankName());
                stmt.setString(14, s.getBhytCode());

                if (s.getBhytValidUntil() != null && !s.getBhytValidUntil().isEmpty()) {
                    stmt.setDate(15, Date.valueOf(s.getBhytValidUntil()));
                } else {
                    stmt.setNull(15, Types.DATE);
                }

                stmt.setBoolean(16, s.isFreeBhyt());
                stmt.setString(17, s.getProgram());
                stmt.setString(18, s.getProgram2());
                stmt.setString(19, s.getOffice365Account());
                stmt.setString(20, s.getPersonalEmail());
                stmt.setString(21, s.getOffice365DefaultPass());
                stmt.setString(22, s.getFacebookUrl());
                stmt.setString(23, s.getPhone());
                stmt.setString(24, s.getAddress());
                stmt.setString(25, s.getAddressNote());
                stmt.setString(26, s.getCity());
                stmt.setString(27, s.getDistrict());
                stmt.setString(28, s.getWard());
                stmt.setString(29, s.getAvatarPath());
                stmt.setString(30, s.getClassName());

                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            System.err.println("Lỗi khi thêm sinh viên: " + e.getMessage());
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            return false;
        }
    }

    public boolean updateField(int id, String field, Object value) {
        String sql = "UPDATE students SET " + field + " = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (value != null) {
                stmt.setObject(1, value);
            } else {
                stmt.setNull(1, Types.VARCHAR); 
            }

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
    
    public int getIdByStudentCode(String studentCode) {
        String sql = "SELECT id FROM students WHERE student_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public Student getById(int id) {
        String sql = "SELECT * FROM students JOIN users ON students.id = users.id WHERE students.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setRole(rs.getString("role"));
                s.setStudentCode(rs.getString("student_code"));
                s.setAvatarPath(rs.getString("avatar_path"));
                s.setFullName(rs.getString("full_name"));
                s.setDob(rs.getString("dob"));
                s.setPlaceOfBirth(rs.getString("place_of_birth"));
                s.setGender(rs.getString("gender"));
                s.setEthnicity(rs.getString("ethnicity"));
                s.setNationality(rs.getString("nationality"));
                s.setReligion(rs.getString("religion"));
                s.setPersonalAccount(rs.getString("personal_account"));
                s.setBankName(rs.getString("bank_name"));
                s.setBhytCode(rs.getString("bhyt_code"));
                s.setBhytValidUntil(rs.getString("bhyt_valid_until"));
                s.setFreeBhyt(rs.getBoolean("free_bhyt"));
                s.setProgram(rs.getString("program"));
                s.setProgram2(rs.getString("program_2"));
                s.setOffice365Account(rs.getString("office365_account"));
                s.setPersonalEmail(rs.getString("personal_email"));
                s.setOffice365DefaultPass(rs.getString("office365_default_pass"));
                s.setFacebookUrl(rs.getString("facebook_url"));
                s.setPhone(rs.getString("phone"));
                s.setAddress(rs.getString("address"));
                s.setAddressNote(rs.getString("address_note"));
                s.setCity(rs.getString("city"));
                s.setDistrict(rs.getString("district"));
                s.setWard(rs.getString("ward"));
                s.setMaKhoa(rs.getString("ma_khoa"));
                s.setMaNganh(rs.getString("ma_nganh"));
                s.setClassName(rs.getString("class_name"));

                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}