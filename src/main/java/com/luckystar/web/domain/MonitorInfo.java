package com.luckystar.web.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by coldvmoon on 09/12/2017.
 */
@Entity
public class MonitorInfo implements Serializable {
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

    @Column(name = "online_status", nullable = false)
    private Integer onlineStatus;

    @Column(name = "room_id")
    private Long roomId;
    @Column(name = "star_id")
    private Long starId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getStarId() {
        return starId;
    }

    public void setStarId(Long starId) {
        this.starId = starId;
    }
}
