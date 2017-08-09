package com.luckystar.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.luckystar.web.domain.enumeration.State;

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

    @NotNull
    @Column(name = "l_id", nullable = false)
    private Integer lId;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @ManyToMany
    @JoinTable(name = "labor_union_user",
               joinColumns = @JoinColumn(name="labor_unions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();

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

    public Set<User> getUsers() {
        return users;
    }

    public LaborUnion users(Set<User> users) {
        this.users = users;
        return this;
    }

//    public LaborUnion addUser(User user) {
//        this.users.add(user);
//        user.getLaborUnions().add(this);
//        return this;
//    }
//
//    public LaborUnion removeUser(User user) {
//        this.users.remove(user);
//        user.getLaborUnions().remove(this);
//        return this;
//    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

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
            "}";
    }
}
