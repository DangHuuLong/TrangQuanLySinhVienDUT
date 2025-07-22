package dao;

import model.Course;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Course> getAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses"; // Assume the table is named 'courses'
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setCourseDescription(rs.getString("course_description"));
                course.setDepartmentCode(rs.getString("department_code"));
                course.setTotalCredits(Integer.parseInt(rs.getString("total_credits")));
                course.setTheoryCredits(Integer.parseInt(rs.getString("theory_credits")));
                course.setPracticeCredits(Integer.parseInt(rs.getString("practice_credits")));
                course.setProjectCredits(Integer.parseInt(rs.getString("project_credits")));
                course.setInternshipCredits(Integer.parseInt(rs.getString("internship_credits")));
                list.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Course getByCode(String courseCode) {
        Course course = null;
        String sql = "SELECT * FROM courses WHERE course_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setCourseDescription(rs.getString("course_description"));
                course.setDepartmentCode(rs.getString("department_code"));
                course.setTotalCredits(Integer.parseInt(rs.getString("total_credits")));
                course.setTheoryCredits(Integer.parseInt(rs.getString("theory_credits")));
                course.setPracticeCredits(Integer.parseInt(rs.getString("practice_credits")));
                course.setProjectCredits(Integer.parseInt(rs.getString("project_credits")));
                course.setInternshipCredits(Integer.parseInt(rs.getString("internship_credits")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;
    }

    public boolean insertCourse(Course course) {
        String sql = "INSERT INTO courses (department_code, course_name, course_code, total_credits, theory_credits, " +
                     "practice_credits, internship_credits, project_credits, course_description) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getDepartmentCode());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getCourseCode());
            stmt.setInt(4, course.getTotalCredits());
            stmt.setInt(5, course.getTheoryCredits());
            stmt.setInt(6, course.getPracticeCredits());
            stmt.setInt(7, course.getInternshipCredits());
            stmt.setInt(8, course.getProjectCredits());
            stmt.setString(9, course.getCourseDescription());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET course_name = ?, total_credits = ?, theory_credits = ?, " +
                     "practice_credits = ?, internship_credits = ?, project_credits = ?, course_description = ?, department_code = ? " +
                     "WHERE course_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getTotalCredits());
            stmt.setInt(3, course.getTheoryCredits());
            stmt.setInt(4, course.getPracticeCredits());
            stmt.setInt(5, course.getInternshipCredits());
            stmt.setInt(6, course.getProjectCredits());
            stmt.setString(7, course.getCourseDescription());
            stmt.setString(8, course.getDepartmentCode());
            stmt.setString(9, course.getCourseCode());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return false;
    }
    
    public boolean delete(String courseCode){
        String sql = "DELETE FROM courses WHERE course_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseCode);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
