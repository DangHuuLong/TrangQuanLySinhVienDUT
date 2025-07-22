package model;

public class Lecturer extends User{
    private String lectruerCode;
	private String fullName;
	private String maKhoa;   
	
	public Lecturer() {
        this.role = "lectruer"; 
    }
	

	public String getLecturerCode() { return lectruerCode; }
    public void setLecturerCode(String lectruerCode) { this.lectruerCode = lectruerCode; }
	
	public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getMaKhoa() {return maKhoa;}
    public void setMaKhoa(String maKhoa) {this.maKhoa = maKhoa;}
}
