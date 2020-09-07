package com.example.demo.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "emp_detail")
public class EmpDetailEntity implements Serializable {

    private static final long serialVersionUID = -1524154181265304299L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long employeeCode;

    @Column
    private long cityPhoneCode;

    @Column
    private long departCode;

    @Column
    private long employeeReference;

    @Column
    private long adminFlag;

    @Column
    private long businessTypeCode;

    @Column
    private long canAdminLogin;

    @Column
    private long canDlCsv;

    @Column
    private long canUserPrint;

    @Column
    private long employeeOrder;

    @Column
    private long empSignFlag;

    @Column
    private long loginFailed;

    @Column
    private long postCode;

    @Column
    private long recognizerCode;

    @Column
    private long refixPerSon;

    @Column
    private long registPerson;

    @Column
    private long statusCode;

    @Column
    private long superiorCode;

    @Column
    private Timestamp accountStopDate;

    @Column
    private Timestamp refixDate;

    @Column
    private Timestamp registDate;

    @Column
    private int bsEditPermFlg;

    @Column
    private int cmsAccessPermFlg;

    @Column
    private int editRuleEngineMailFlg;

    @Column
    private int empAdminFlg;

    @Column
    private int idAuthFlg;

    @Column(length = 5)
    private String language;

    @Column
    private int license;

    @Column(length = 900)
    private String address;

    @Column(length = 30)
    private long bpDefaultPage;

    @Column(length = 50)
    private String closestSt;

    @Column(length = 100)
    private String employeeNumber;

    @Column(length = 100)
    private String empMailName;

    @Column(length = 1000)
    private String empSign;

    @Column(length = 50)
    private String eMail;

    @Column(length = 1000)
    private String mobilePhoneId;

    @Column(length = 60)
    private long nikkeiId;

    @Column(length = 60)
    private String nikkeiPw;

    @Column(length = 32)
    private String password;

    @Column(length = 30)
    private String photo;

    @Column(length = 50)
    private String returnEmail;

    @Column(length = 500)
    private String sendonEmail;

    @Column(length = 20)
    private String telNo;

    @Column(length = 16)
    private String zipCode;

    @Column
    private Timestamp updateStatus;

    public long getId() {
        return id;
    }

    public long getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(long employeeCode) {
        this.employeeCode = employeeCode;
    }

    public long getCityPhoneCode() {
        return cityPhoneCode;
    }

    public void setCityPhoneCode(long cityPhoneCode) {
        this.cityPhoneCode = cityPhoneCode;
    }

    public long getDepartCode() {
        return departCode;
    }

    public void setDepartCode(long departCode) {
        this.departCode = departCode;
    }

    public long getEmployeeReference() {
        return employeeReference;
    }

    public void setEmployeeReference(long employeeReference) {
        this.employeeReference = employeeReference;
    }

    public long getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(long adminFlag) {
        this.adminFlag = adminFlag;
    }

    public long getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(long businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public long getCanAdminLogin() {
        return canAdminLogin;
    }

    public void setCanAdminLogin(long canAdminLogin) {
        this.canAdminLogin = canAdminLogin;
    }

    public long getCanDlCsv() {
        return canDlCsv;
    }

    public void setCanDlCsv(long canDlCsv) {
        this.canDlCsv = canDlCsv;
    }

    public long getCanUserPrint() {
        return canUserPrint;
    }

    public void setCanUserPrint(long canUserPrint) {
        this.canUserPrint = canUserPrint;
    }

    public long getEmployeeOrder() {
        return employeeOrder;
    }

    public void setEmployeeOrder(long employeeOrder) {
        this.employeeOrder = employeeOrder;
    }

    public long getEmpSignFlag() {
        return empSignFlag;
    }

    public void setEmpSignFlag(long empSignFlag) {
        this.empSignFlag = empSignFlag;
    }

    public long getLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(long loginFailed) {
        this.loginFailed = loginFailed;
    }

    public long getPostCode() {
        return postCode;
    }

    public void setPostCode(long postCode) {
        this.postCode = postCode;
    }

    public long getRecognizerCode() {
        return recognizerCode;
    }

    public void setRecognizerCode(long recognizerCode) {
        this.recognizerCode = recognizerCode;
    }

    public long getRefixPerSon() {
        return refixPerSon;
    }

    public void setRefixPerSon(long refixPerSon) {
        this.refixPerSon = refixPerSon;
    }

    public long getRegistPerson() {
        return registPerson;
    }

    public void setRegistPerson(long registPerson) {
        this.registPerson = registPerson;
    }

    public long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }

    public long getSuperiorCode() {
        return superiorCode;
    }

    public void setSuperiorCode(long superiorCode) {
        this.superiorCode = superiorCode;
    }

    public Timestamp getAccountStopDate() {
        return accountStopDate;
    }

    public void setAccountStopDate(Timestamp accountStopDate) {
        this.accountStopDate = accountStopDate;
    }

    public Timestamp getRefixDate() {
        return refixDate;
    }

    public void setRefixDate(Timestamp refixDate) {
        this.refixDate = refixDate;
    }

    public Timestamp getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Timestamp registDate) {
        this.registDate = registDate;
    }

    public int getBsEditPermFlg() {
        return bsEditPermFlg;
    }

    public void setBsEditPermFlg(int bsEditPermFlg) {
        this.bsEditPermFlg = bsEditPermFlg;
    }

    public int getCmsAccessPermFlg() {
        return cmsAccessPermFlg;
    }

    public void setCmsAccessPermFlg(int cmsAccessPermFlg) {
        this.cmsAccessPermFlg = cmsAccessPermFlg;
    }

    public int getEditRuleEngineMailFlg() {
        return editRuleEngineMailFlg;
    }

    public void setEditRuleEngineMailFlg(int editRuleEngineMailFlg) {
        this.editRuleEngineMailFlg = editRuleEngineMailFlg;
    }

    public int getEmpAdminFlg() {
        return empAdminFlg;
    }

    public void setEmpAdminFlg(int empAdminFlg) {
        this.empAdminFlg = empAdminFlg;
    }

    public int getIdAuthFlg() {
        return idAuthFlg;
    }

    public void setIdAuthFlg(int idAuthFlg) {
        this.idAuthFlg = idAuthFlg;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLicense() {
        return license;
    }

    public void setLicense(int license) {
        this.license = license;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBpDefaultPage() {
        return bpDefaultPage;
    }

    public void setBpDefaultPage(long bpDefaultPage) {
        this.bpDefaultPage = bpDefaultPage;
    }

    public String getClosestSt() {
        return closestSt;
    }

    public void setClosestSt(String closestSt) {
        this.closestSt = closestSt;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmpMailName() {
        return empMailName;
    }

    public void setEmpMailName(String empMailName) {
        this.empMailName = empMailName;
    }

    public String getEmpSign() {
        return empSign;
    }

    public void setEmpSign(String empSign) {
        this.empSign = empSign;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getMobilePhoneId() {
        return mobilePhoneId;
    }

    public void setMobilePhoneId(String mobilePhoneId) {
        this.mobilePhoneId = mobilePhoneId;
    }

    public long getNikkeiId() {
        return nikkeiId;
    }

    public void setNikkeiId(long nikkeiId) {
        this.nikkeiId = nikkeiId;
    }

    public String getNikkeiPw() {
        return nikkeiPw;
    }

    public void setNikkeiPw(String nikkeiPw) {
        this.nikkeiPw = nikkeiPw;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getReturnEmail() {
        return returnEmail;
    }

    public void setReturnEmail(String returnEmail) {
        this.returnEmail = returnEmail;
    }

    public String getSendonEmail() {
        return sendonEmail;
    }

    public void setSendonEmail(String sendonEmail) {
        this.sendonEmail = sendonEmail;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Timestamp getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Timestamp updateStatus) {
        this.updateStatus = updateStatus;
    }
}