package com.zhita.model.manage;

public class UserAttestation {
    private Integer id;

    private Integer userid;

    private String truename;

    private String idcardNumber;

    private String birthday;

    private String householdregister;

    private String homeaddresslongitude;

    private String homeaddresslatitude;

    private String detailaddress;

    private String headurl;

    private String nationalemblemurl;

    private String linkmanonerelation;

    private String linkmanonename;

    private String linkmanonephone;

    private String linkmantworelation;

    private String linkmantwoname;

    private String linkmantwophone;

    private String facebiztoken;

    private String idcardbiztoken;

    private String sign;

    private String gender;

    private String nationality;

    private String birthYear;

    private String birthMonth;

    private String birthDay;

    private String address;

    private String issuedBy;

    private String validDateStart;

    private String validDateEnd;
    
    private String attestationStatus;

    public UserAttestation(Integer id, Integer userid, String truename, String idcardNumber, String birthday, String householdregister, String homeaddresslongitude, String homeaddresslatitude, String detailaddress, String headurl, String nationalemblemurl, String linkmanonerelation, String linkmanonename, String linkmanonephone, String linkmantworelation, String linkmantwoname, String linkmantwophone, String facebiztoken, String idcardbiztoken, String sign, String gender, String nationality, String birthYear, String birthMonth, String birthDay, String address, String issuedBy, String validDateStart, String validDateEnd,String attestationStatus) {
        this.id = id;
        this.userid = userid;
        this.truename = truename;
        this.idcardNumber = idcardNumber;
        this.birthday = birthday;
        this.householdregister = householdregister;
        this.homeaddresslongitude = homeaddresslongitude;
        this.homeaddresslatitude = homeaddresslatitude;
        this.detailaddress = detailaddress;
        this.headurl = headurl;
        this.nationalemblemurl = nationalemblemurl;
        this.linkmanonerelation = linkmanonerelation;
        this.linkmanonename = linkmanonename;
        this.linkmanonephone = linkmanonephone;
        this.linkmantworelation = linkmantworelation;
        this.linkmantwoname = linkmantwoname;
        this.linkmantwophone = linkmantwophone;
        this.facebiztoken = facebiztoken;
        this.idcardbiztoken = idcardbiztoken;
        this.sign = sign;
        this.gender = gender;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.address = address;
        this.issuedBy = issuedBy;
        this.validDateStart = validDateStart;
        this.validDateEnd = validDateEnd;
        this.attestationStatus = attestationStatus;
    }

    public UserAttestation() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber == null ? null : idcardNumber.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getHouseholdregister() {
        return householdregister;
    }

    public void setHouseholdregister(String householdregister) {
        this.householdregister = householdregister == null ? null : householdregister.trim();
    }

    public String getHomeaddresslongitude() {
        return homeaddresslongitude;
    }

    public void setHomeaddresslongitude(String homeaddresslongitude) {
        this.homeaddresslongitude = homeaddresslongitude == null ? null : homeaddresslongitude.trim();
    }

    public String getHomeaddresslatitude() {
        return homeaddresslatitude;
    }

    public void setHomeaddresslatitude(String homeaddresslatitude) {
        this.homeaddresslatitude = homeaddresslatitude == null ? null : homeaddresslatitude.trim();
    }

    public String getDetailaddress() {
        return detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress == null ? null : detailaddress.trim();
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl == null ? null : headurl.trim();
    }

    public String getNationalemblemurl() {
        return nationalemblemurl;
    }

    public void setNationalemblemurl(String nationalemblemurl) {
        this.nationalemblemurl = nationalemblemurl == null ? null : nationalemblemurl.trim();
    }

    public String getLinkmanonerelation() {
        return linkmanonerelation;
    }

    public void setLinkmanonerelation(String linkmanonerelation) {
        this.linkmanonerelation = linkmanonerelation == null ? null : linkmanonerelation.trim();
    }

    public String getLinkmanonename() {
        return linkmanonename;
    }

    public void setLinkmanonename(String linkmanonename) {
        this.linkmanonename = linkmanonename == null ? null : linkmanonename.trim();
    }

    public String getLinkmanonephone() {
        return linkmanonephone;
    }

    public void setLinkmanonephone(String linkmanonephone) {
        this.linkmanonephone = linkmanonephone == null ? null : linkmanonephone.trim();
    }

    public String getLinkmantworelation() {
        return linkmantworelation;
    }

    public void setLinkmantworelation(String linkmantworelation) {
        this.linkmantworelation = linkmantworelation == null ? null : linkmantworelation.trim();
    }

    public String getLinkmantwoname() {
        return linkmantwoname;
    }

    public void setLinkmantwoname(String linkmantwoname) {
        this.linkmantwoname = linkmantwoname == null ? null : linkmantwoname.trim();
    }

    public String getLinkmantwophone() {
        return linkmantwophone;
    }

    public void setLinkmantwophone(String linkmantwophone) {
        this.linkmantwophone = linkmantwophone == null ? null : linkmantwophone.trim();
    }

    public String getFacebiztoken() {
        return facebiztoken;
    }

    public void setFacebiztoken(String facebiztoken) {
        this.facebiztoken = facebiztoken == null ? null : facebiztoken.trim();
    }

    public String getIdcardbiztoken() {
        return idcardbiztoken;
    }

    public void setIdcardbiztoken(String idcardbiztoken) {
        this.idcardbiztoken = idcardbiztoken == null ? null : idcardbiztoken.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear == null ? null : birthYear.trim();
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth == null ? null : birthMonth.trim();
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay == null ? null : birthDay.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy == null ? null : issuedBy.trim();
    }

    public String getValidDateStart() {
        return validDateStart;
    }

    public void setValidDateStart(String validDateStart) {
        this.validDateStart = validDateStart == null ? null : validDateStart.trim();
    }

    public String getValidDateEnd() {
        return validDateEnd;
    }

    public void setValidDateEnd(String validDateEnd) {
        this.validDateEnd = validDateEnd == null ? null : validDateEnd.trim();
    }
    
    public String getAttestationStatus() {
        return attestationStatus;
    }

    public void setAttestationStatus(String attestationStatus) {
        this.attestationStatus = attestationStatus == null ? null : attestationStatus.trim();
    }
}