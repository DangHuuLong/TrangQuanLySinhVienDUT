package model;

public class Student extends User {
    private String fullName;
    private String studentCode;
    private String dob;
    private String placeOfBirth;
    private String gender;
    private String ethnicity;
    private String nationality;
    private String religion;
    private String personalAccount;
    private String bankName;
    private String bhytCode;
    private String bhytValidUntil;
    private boolean freeBhyt;
    private String major;
    private String className;
    private String program;
    private String program2;
    private String office365Account;
    private String personalEmail;
    private String office365DefaultPass;
    private String facebookUrl;
    private String phone;
    private String address;
    private String addressNote;
    private String city;
    private String district;
    private String ward;
    private String avatarPath;

    public Student() {
        this.role = "student"; // mặc định là sinh viên
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getStudentCode() { return studentCode; }

    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }


    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getPlaceOfBirth() { return placeOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }

    public String getPersonalAccount() { return personalAccount; }
    public void setPersonalAccount(String personalAccount) { this.personalAccount = personalAccount; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBhytCode() { return bhytCode; }
    public void setBhytCode(String bhytCode) { this.bhytCode = bhytCode; }

    public String getBhytValidUntil() { return bhytValidUntil; }
    public void setBhytValidUntil(String bhytValidUntil) { this.bhytValidUntil = bhytValidUntil; }

    public boolean isFreeBhyt() { return freeBhyt; }
    public void setFreeBhyt(boolean freeBhyt) { this.freeBhyt = freeBhyt; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public String getProgram2() { return program2; }
    public void setProgram2(String program2) { this.program2 = program2; }

    public String getOffice365Account() { return office365Account; }
    public void setOffice365Account(String office365Account) { this.office365Account = office365Account; }

    public String getPersonalEmail() { return personalEmail; }
    public void setPersonalEmail(String personalEmail) { this.personalEmail = personalEmail; }

    public String getOffice365DefaultPass() { return office365DefaultPass; }
    public void setOffice365DefaultPass(String office365DefaultPass) { this.office365DefaultPass = office365DefaultPass; }

    public String getFacebookUrl() { return facebookUrl; }
    public void setFacebookUrl(String facebookUrl) { this.facebookUrl = facebookUrl; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAddressNote() { return addressNote; }
    public void setAddressNote(String addressNote) { this.addressNote = addressNote; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }
}