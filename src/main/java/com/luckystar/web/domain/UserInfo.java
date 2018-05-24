package com.luckystar.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.luckystar.web.domain.enumeration.State;

/**
 * A UserInfo.
 */
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 真名
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "真名", required = true)
    @Column(name = "user_name", length = 10, nullable = false)
    private String userName;

    /**
     * 艺名
     */
    @Size(max = 50)
    @ApiModelProperty(value = "艺名")
    @Column(name = "nick_name", length = 50)
    private String nickName;

    /**
     * 手机号
     */
    @Size(max = 50)
    @ApiModelProperty(value = "手机号")
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Column(name = "room_id")
    private Long roomId;
    /**
     * 手机号
     */
    @Size(max = 50)
    @ApiModelProperty(value = "手机号")
    @Column(name = "qq", length = 50)
    private String qq;

    /**
     * 微信号
     */
    @Size(max = 50)
    @ApiModelProperty(value = "微信号")
    @Column(name = "wei_chat", length = 50)
    private String weiChat;

    /**
     * 繁星id
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "繁星id", required = true)
    @Column(name = "star_id", length = 50, nullable = false)
    private String starId;

    /**
     * 注册时间
     */
    @NotNull
    @ApiModelProperty(value = "注册时间", required = true)
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    /**
     * 繁星登录名
     */
    @Size(max = 50)
    @ApiModelProperty(value = "繁星登录名")
    @Column(name = "login_name", length = 50)
    private String loginName;

    /**
     * 繁星登录密码
     */
    @Size(max = 500)
    @ApiModelProperty(value = "繁星登录密码")
    @Column(name = "jhi_password", length = 1024)
    private String password;

    /**
     * 登录后的cookie信息，需要人工定期维护
     */
    @Size(max = 10480)
    @ApiModelProperty(value = "登录后的cookie信息，需要人工定期维护")
    @Column(name = "cookie", length = 10480)
    private String cookie;

    /**
     * 考勤倍率
     */
//    @NotNull
    @ApiModelProperty(value = "考勤倍率", required = true)
    @Column(name = "time_rate", nullable = true)
    private Float timeRate;

    /**
     * 星豆倍率
     */
//    @NotNull
    @ApiModelProperty(value = "星豆倍率", required = true)
    @Column(name = "bean_rate", nullable = true)
    private Float beanRate;

    /**
     * 上次一维护cookie时间
     */
    @ApiModelProperty(value = "上次一维护cookie时间")
    @Column(name = "last_maintain")
    private LocalDate lastMaintain;

    /**
     * 0：停用 1：在用
     */
    @NotNull
    @ApiModelProperty(value = "0：停用 1：在用", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @OneToMany(mappedBy = "userInfo")
    @JsonIgnore
    private Set<TaskInfo> taskInfos = new HashSet<>();

    @ManyToOne
    private LaborUnion laborUnion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public UserInfo userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public UserInfo nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserInfo phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQq() {
        return qq;
    }

    public UserInfo qq(String qq) {
        this.qq = qq;
        return this;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeiChat() {
        return weiChat;
    }

    public UserInfo weiChat(String weiChat) {
        this.weiChat = weiChat;
        return this;
    }

    public void setWeiChat(String weiChat) {
        this.weiChat = weiChat;
    }

    public String getStarId() {
        return starId;
    }

    public UserInfo starId(String starId) {
        this.starId = starId;
        return this;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public UserInfo regDate(LocalDate regDate) {
        this.regDate = regDate;
        return this;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public UserInfo loginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCookie() {
        return cookie;
    }

    public UserInfo cookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Float getTimeRate() {
        return timeRate;
    }

    public UserInfo timeRate(Float timeRate) {
        this.timeRate = timeRate;
        return this;
    }

    public void setTimeRate(Float timeRate) {
        this.timeRate = timeRate;
    }

    public Float getBeanRate() {
        return beanRate;
    }

    public UserInfo beanRate(Float beanRate) {
        this.beanRate = beanRate;
        return this;
    }

    public void setBeanRate(Float beanRate) {
        this.beanRate = beanRate;
    }

    public LocalDate getLastMaintain() {
        return lastMaintain;
    }

    public UserInfo lastMaintain(LocalDate lastMaintain) {
        this.lastMaintain = lastMaintain;
        return this;
    }

    public void setLastMaintain(LocalDate lastMaintain) {
        this.lastMaintain = lastMaintain;
    }

    public State getState() {
        return state;
    }

    public UserInfo state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<TaskInfo> getTaskInfos() {
        return taskInfos;
    }

    public UserInfo taskInfos(Set<TaskInfo> taskInfos) {
        this.taskInfos = taskInfos;
        return this;
    }

    public UserInfo addTaskInfo(TaskInfo taskInfo) {
        this.taskInfos.add(taskInfo);
        taskInfo.setUserInfo(this);
        return this;
    }

    public UserInfo removeTaskInfo(TaskInfo taskInfo) {
        this.taskInfos.remove(taskInfo);
        taskInfo.setUserInfo(null);
        return this;
    }

    public void setTaskInfos(Set<TaskInfo> taskInfos) {
        this.taskInfos = taskInfos;
    }

    public LaborUnion getLaborUnion() {
        return laborUnion;
    }

    public UserInfo laborUnion(LaborUnion laborUnion) {
        this.laborUnion = laborUnion;
        return this;
    }

    public void setLaborUnion(LaborUnion laborUnion) {
        this.laborUnion = laborUnion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        if (userInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", qq='" + getQq() + "'" +
            ", weiChat='" + getWeiChat() + "'" +
            ", starId='" + getStarId() + "'" +
            ", regDate='" + getRegDate() + "'" +
            ", loginName='" + getLoginName() + "'" +
            ", password='" + getPassword() + "'" +
            ", cookie='" + getCookie() + "'" +
            ", timeRate='" + getTimeRate() + "'" +
            ", beanRate='" + getBeanRate() + "'" +
            ", lastMaintain='" + getLastMaintain() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
