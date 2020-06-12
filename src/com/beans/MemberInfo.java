package com.beans;

import java.util.Date;
/***
 * author zhangchao
 * 会员实体类
 */
public class MemberInfo {
    private int id; //会员id
    private String memberNo; //会员账号
    private String memberName;//会员姓名
    private String phone; //会员电话号
    private String email;//会员邮箱
    private Date registerDate;//注册日期
    private String memberLevel;//会员等级
    private String idCard;//身份证号
    private int loginCounts;//登陆次数
    private Date lastLoginDate;//最后登录时间
    private String ip;//常用ip

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getLoginCounts() {
        return loginCounts;
    }

    public void setLoginCounts(int loginCounts) {
        this.loginCounts = loginCounts;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }
}