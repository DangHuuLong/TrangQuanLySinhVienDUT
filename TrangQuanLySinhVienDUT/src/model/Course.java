package model;

public class Course {
    private String departmentCode; 
    private String courseName; 
    private String courseCode; 
    private int totalCredits; 
    private int theoryCredits; 
    private int practiceCredits;
    private int internshipCredits; 
    private int projectCredits; 
    private String courseDescription; 

	// Getters and Setters
    public String getDepartmentCode() {return departmentCode;}
    public void setDepartmentCode(String departmentCode) {this.departmentCode = departmentCode;}

    public String getCourseName() {return courseName;}
    public void setCourseName(String courseName) {this.courseName = courseName;}

    public String getCourseCode() {return courseCode;}
    public void setCourseCode(String courseCode) {this.courseCode = courseCode;}

    public int getTotalCredits() {return totalCredits;}
    public void setTotalCredits(int totalCredits) {this.totalCredits = totalCredits;}

    public int getTheoryCredits() {return theoryCredits;}
    public void setTheoryCredits(int theoryCredits) {this.theoryCredits = theoryCredits;}

    public int getPracticeCredits() {return practiceCredits;}
    public void setPracticeCredits(int practiceCredits) {this.practiceCredits = practiceCredits;}

    public int getInternshipCredits() {return internshipCredits;}
    public void setInternshipCredits(int internshipCredits) {this.internshipCredits = internshipCredits;}

    public int getProjectCredits() {return projectCredits;}
    public void setProjectCredits(int projectCredits) {this.projectCredits = projectCredits;}

    public String getCourseDescription() {return courseDescription;}
    public void setCourseDescription(String courseDescription) {this.courseDescription = courseDescription;
    }
}
