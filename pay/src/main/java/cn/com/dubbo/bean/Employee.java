package cn.com.dubbo.bean;

import java.io.Serializable;

public class Employee implements Serializable{
    private Long empid;

    private String empaddress;

    private String empage;

    private String empmail;

    private String empname;

    private String empphone;

    private String empsex;

    private String emppass;

    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public String getEmpaddress() {
        return empaddress;
    }

    public void setEmpaddress(String empaddress) {
        this.empaddress = empaddress == null ? null : empaddress.trim();
    }

    public String getEmpage() {
        return empage;
    }

    public void setEmpage(String empage) {
        this.empage = empage == null ? null : empage.trim();
    }

    public String getEmpmail() {
        return empmail;
    }

    public void setEmpmail(String empmail) {
        this.empmail = empmail == null ? null : empmail.trim();
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public String getEmpphone() {
        return empphone;
    }

    public void setEmpphone(String empphone) {
        this.empphone = empphone == null ? null : empphone.trim();
    }

    public String getEmpsex() {
        return empsex;
    }

    public void setEmpsex(String empsex) {
        this.empsex = empsex == null ? null : empsex.trim();
    }

    public String getEmppass() {
        return emppass;
    }

    public void setEmppass(String emppass) {
        this.emppass = emppass == null ? null : emppass.trim();
    }
}