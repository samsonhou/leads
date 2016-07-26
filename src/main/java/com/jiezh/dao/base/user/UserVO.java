package com.jiezh.dao.base.user;

import java.util.Date;
import java.util.List;

import com.jiezh.content.base.pub.web.GeneralBean;

public class UserVO extends GeneralBean {
    private long userId;

    private String userCode;

    private String userName;

    private String realName;

    private String jzCode;

    private String organId;

    private String password;

    private String status;

    private Date createDate;

    private String organCode;

    private String tel;

    private String email;

    private String isPass;

    private String role = "ROLE_USER";

    private List<RoleVO> temproles = null;
    /*
     * 当前用户选择了的角色
     */
    private List<RoleVO> selectrole = null;

    private String organName;

    private String organCompanyName;

    private String roleId;

    private String roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 锁定时间
     * add by houjiq start
     */
    private Date lockTime;
    /** 锁定次数 */
    private String lockTimes;

    /**
     * end
     */

    public String getIsPass() {
        return isPass;
    }

    /**
     * @return the lockTime String
     */
    public Date getLockTime() {
        return lockTime;
    }

    /**
     * @param lockTime the lockTime to set
     */
    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    /**
     * @return the lockTimes String
     */
    public String getLockTimes() {
        return lockTimes;
    }

    /**
     * @param lockTimes the lockTimes to set
     */
    public void setLockTimes(String lockTimes) {
        this.lockTimes = lockTimes;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    /**
     * @return the temproles
     */
    public List<RoleVO> getTemproles() {
        return temproles;
    }

    /**
     * @param temproles the temproles to set
     */
    public void setTemproles(List<RoleVO> temproles) {
        this.temproles = temproles;
    }

    /**
     * @return the organName
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * @param organName the organName to set
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * @return the organCompanyName
     */
    public String getOrganCompanyName() {
        return organCompanyName;
    }

    /**
     * @param organCompanyName the organCompanyName to set
     */
    public void setOrganCompanyName(String organCompanyName) {
        this.organCompanyName = organCompanyName;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getJzCode() {
        return jzCode;
    }

    public void setJzCode(String jzCode) {
        this.jzCode = jzCode;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserVO [userId=" + userId + ", userCode=" + userCode + ", userName=" + userName + ", realName=" + realName + ", jzCode=" + jzCode
            + ", organId=" + organId + ", password=" + password + ", status=" + status + ", createDate=" + createDate + ", organCode=" + organCode
            + ", tel=" + tel + ", email=" + email + ", role=" + role + ", temproles=" + temproles + ", selectrole=" + selectrole + ", organName="
            + organName + ", organCompanyName=" + organCompanyName + "]";
    }

}
