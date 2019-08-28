package com.wzcsoft.dzpjdy.domain;

/**
 * @author lyj
 * @date 2019/7/25 17:23
 */
public class User {

    private String patName;
    private String caseNo;
    private String patId;
    private String patAge;
    private String patSex;
    private String patBirthDate;
    private String certId;
    private String nationality;
    private String nation;
    private String maritalStatus;
    private String birthPlace;
    private String regAddress;
    private String patAddress;
    private String occupation;
    private String patTel;
    private String contacts;
    private String contactRel;
    private String contactTel;
    private String certType;
    private String patCardNo;
    private String remark;
    private String remark1;


    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getCertId() {
        return certId;
    }

    public String getPatName() {
        return patName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public String getPatId() {
        return patId;
    }


    public String getPatBirthDate() {
        return patBirthDate;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatAge() {
        return patAge;
    }

    public void setPatAge(String patAge) {
        this.patAge = patAge;
    }

    public String getPatSex() {
        return patSex;
    }

    public void setPatSex(String patSex) {
        this.patSex = patSex;
    }

    public void setPatBirthDate(String patBirthDate) {
        this.patBirthDate = patBirthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "patName='" + patName + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", patId='" + patId + '\'' +
                ", patAge=" + patAge +
                ", patSex=" + patSex +
                ", patBirthDate='" + patBirthDate + '\'' +
                ", certId='" + certId + '\'' +
                '}';
    }
}
