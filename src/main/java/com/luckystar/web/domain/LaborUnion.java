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

import com.luckystar.web.domain.enumeration.Source;

/**
 * A LaborUnion.
 */
@Entity
@Table(name = "labor_union")
public class LaborUnion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公会id
     */
    @NotNull
    @ApiModelProperty(value = "公会id", required = true)
    @Column(name = "l_id", nullable = false)
    private Integer lId;

    /**
     * 公会名称
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "公会名称", required = true)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 注册时间
     */
    @NotNull
    @ApiModelProperty(value = "注册时间", required = true)
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    /**
     * 0：停用 1：在用
     */
    @NotNull
    @ApiModelProperty(value = "0：停用 1：在用", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "max_member")
    private Long maxMember;
    
    @Column(name = "auto_exchange")
    private boolean autoExchange;

    /**
     * 数据采集来源 0：繁星
     */
    @NotNull
    @ApiModelProperty(value = "数据采集来源 0：繁星", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private Source type;

    @OneToMany(mappedBy = "laborUnion")
    @JsonIgnore
    private Set<UserInfo> userInfos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "labor_union_user",
               joinColumns = @JoinColumn(name="labor_unions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();

//    @ManyToOne
//    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getlId() {
        return lId;
    }

    public LaborUnion lId(Integer lId) {
        this.lId = lId;
        return this;
    }

    public void setlId(Integer lId) {
        this.lId = lId;
    }

    public String getName() {
        return name;
    }

    public LaborUnion name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public LaborUnion regDate(LocalDate regDate) {
        this.regDate = regDate;
        return this;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public State getState() {
        return state;
    }

    public LaborUnion state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Source getType() {
        return type;
    }

    public LaborUnion type(Source type) {
        this.type = type;
        return this;
    }

    public void setType(Source type) {
        this.type = type;
    }

    public Set<UserInfo> getUserInfos() {
        return userInfos;
    }

    public LaborUnion userInfos(Set<UserInfo> userInfos) {
        this.userInfos = userInfos;
        return this;
    }

    public LaborUnion addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.setLaborUnion(this);
        return this;
    }

    public LaborUnion removeUserInfo(UserInfo userInfo) {
        this.userInfos.remove(userInfo);
        userInfo.setLaborUnion(null);
        return this;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Set<User> getUsers() {
        return users;
    }

    public LaborUnion users(Set<User> users) {
        this.users = users;
        return this;
    }

    public LaborUnion addUser(User user) {
        this.users.add(user);
//        user.getLaborUnions().add(this);
        return this;
    }

    public LaborUnion removeUser(User user) {
        this.users.remove(user);
//        user.getLaborUnions().remove(this);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public LaborUnion user(User user) {
//        this.user = user;
//        return this;
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LaborUnion laborUnion = (LaborUnion) o;
        if (laborUnion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), laborUnion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LaborUnion{" +
            "id=" + getId() +
            ", lId='" + getlId() + "'" +
            ", name='" + getName() + "'" +
            ", regDate='" + getRegDate() + "'" +
            ", state='" + getState() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

    public Long getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Long maxMember) {
        this.maxMember = maxMember;
    }

	public boolean isAutoExchange() {
		return autoExchange;
	}

	public void setAutoExchange(boolean autoExchange) {
		this.autoExchange = autoExchange;
	}

}
