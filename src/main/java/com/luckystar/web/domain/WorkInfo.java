package com.luckystar.web.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A WorkInfo.
 */
@Entity
@Table(name = "work_info")
public class WorkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 繁星id
     */
    @NotNull
    @ApiModelProperty(value = "繁星id", required = true)
    @Column(name = "star_id", nullable = false)
    private Long starId;

    /**
     * 繁星等级
     */
    @ApiModelProperty(value = "繁星等级")
    @Column(name = "star_level")
    private Integer starLevel;

    /**
     * 繁星等级名称
     */
    @Size(max = 20)
    @ApiModelProperty(value = "繁星等级名称")
    @Column(name = "star_name", length = 20)
    private String starName;

    /**
     * 财富等级
     */
    @ApiModelProperty(value = "财富等级")
    @Column(name = "rich_level")
    private Integer richLevel;

    /**
     * 财富等级名称
     */
    @Size(max = 20)
    @ApiModelProperty(value = "财富等级名称")
    @Column(name = "rich_name", length = 20)
    private String richName;

    /**
     * 当天初始星豆数
     */
    @ApiModelProperty(value = "当天初始星豆数")
    @Column(name = "fisrt_bean")
    private Float fisrtBean;

    /**
     * 星豆总数
     */
    @ApiModelProperty(value = "星豆总数")
    @Column(name = "bean_total")
    private Float beanTotal;

    /**
     * 星币数
     */
    @ApiModelProperty(value = "星币数")
    @Column(name = "coin")
    private Float coin;

    /**
     * 星币总数
     */
    @ApiModelProperty(value = "星币总数")
    @Column(name = "coin_total")
    private Float coinTotal;

    /**
     * 被关注数
     */
    @ApiModelProperty(value = "被关注数")
    @Column(name = "fans_count")
    private Integer fansCount;

    /**
     * 关注数
     */
    @ApiModelProperty(value = "关注数")
    @Column(name = "follow_count")
    private Integer followCount;

    /**
     * 经验值
     */
    @ApiModelProperty(value = "经验值")
    @Column(name = "experience")
    private Float experience;

    /**
     * 工作时长
     */
    @ApiModelProperty(value = "工作时长")
    @Column(name = "work_time")
    private Integer workTime;

    /**
     * 当前月份
     */
    @NotNull
    @ApiModelProperty(value = "当前月份", required = true)
    @Column(name = "cur_month", nullable = false)
    private Integer curMonth;

    /**
     * 当前天
     */
    @NotNull
    @ApiModelProperty(value = "当前天", required = true)
    @Column(name = "cur_day", nullable = false)
    private LocalDate curDay;

    /**
     * 最后更新时间
     */
    @NotNull
    @ApiModelProperty(value = "最后更新时间", required = true)
    @Column(name = "last_time", nullable = false)
    private ZonedDateTime lastTime;

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    @Column(name = "online_status", nullable = false)
    private Integer onlineStatus;

    @ManyToOne
    private TaskInfo taskInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStarId() {
        return starId;
    }

    public WorkInfo starId(Long starId) {
        this.starId = starId;
        return this;
    }

    public void setStarId(Long starId) {
        this.starId = starId;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public WorkInfo starLevel(Integer starLevel) {
        this.starLevel = starLevel;
        return this;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public String getStarName() {
        return starName;
    }

    public WorkInfo starName(String starName) {
        this.starName = starName;
        return this;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public Integer getRichLevel() {
        return richLevel;
    }

    public WorkInfo richLevel(Integer richLevel) {
        this.richLevel = richLevel;
        return this;
    }

    public void setRichLevel(Integer richLevel) {
        this.richLevel = richLevel;
    }

    public String getRichName() {
        return richName;
    }

    public WorkInfo richName(String richName) {
        this.richName = richName;
        return this;
    }

    public void setRichName(String richName) {
        this.richName = richName;
    }

    public Float getFisrtBean() {
        return fisrtBean;
    }

    public WorkInfo fisrtBean(Float fisrtBean) {
        this.fisrtBean = fisrtBean;
        return this;
    }

    public void setFisrtBean(Float fisrtBean) {
        this.fisrtBean = fisrtBean;
    }

    public Float getBeanTotal() {
        return beanTotal;
    }

    public WorkInfo beanTotal(Float beanTotal) {
        this.beanTotal = beanTotal;
        return this;
    }

    public void setBeanTotal(Float beanTotal) {
        this.beanTotal = beanTotal;
    }

    public Float getCoin() {
        return coin;
    }

    public WorkInfo coin(Float coin) {
        this.coin = coin;
        return this;
    }

    public void setCoin(Float coin) {
        this.coin = coin;
    }

    public Float getCoinTotal() {
        return coinTotal;
    }

    public WorkInfo coinTotal(Float coinTotal) {
        this.coinTotal = coinTotal;
        return this;
    }

    public void setCoinTotal(Float coinTotal) {
        this.coinTotal = coinTotal;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public WorkInfo fansCount(Integer fansCount) {
        this.fansCount = fansCount;
        return this;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public WorkInfo followCount(Integer followCount) {
        this.followCount = followCount;
        return this;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Float getExperience() {
        return experience;
    }

    public WorkInfo experience(Float experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Float experience) {
        this.experience = experience;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public WorkInfo workTime(Integer workTime) {
        this.workTime = workTime;
        return this;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public Integer getCurMonth() {
        return curMonth;
    }

    public WorkInfo curMonth(Integer curMonth) {
        this.curMonth = curMonth;
        return this;
    }

    public void setCurMonth(Integer curMonth) {
        this.curMonth = curMonth;
    }

    public LocalDate getCurDay() {
        return curDay;
    }

    public WorkInfo curDay(LocalDate curDay) {
        this.curDay = curDay;
        return this;
    }

    public void setCurDay(LocalDate curDay) {
        this.curDay = curDay;
    }

    public ZonedDateTime getLastTime() {
        return lastTime;
    }

    public WorkInfo lastTime(ZonedDateTime lastTime) {
        this.lastTime = lastTime;
        return this;
    }

    public void setLastTime(ZonedDateTime lastTime) {
        this.lastTime = lastTime;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public WorkInfo taskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        return this;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkInfo workInfo = (WorkInfo) o;
        if (workInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkInfo{" +
            "id=" + getId() +
            ", starId='" + getStarId() + "'" +
            ", starLevel='" + getStarLevel() + "'" +
            ", starName='" + getStarName() + "'" +
            ", richLevel='" + getRichLevel() + "'" +
            ", richName='" + getRichName() + "'" +
            ", fisrtBean='" + getFisrtBean() + "'" +
            ", beanTotal='" + getBeanTotal() + "'" +
            ", coin='" + getCoin() + "'" +
            ", coinTotal='" + getCoinTotal() + "'" +
            ", fansCount='" + getFansCount() + "'" +
            ", followCount='" + getFollowCount() + "'" +
            ", experience='" + getExperience() + "'" +
            ", workTime='" + getWorkTime() + "'" +
            ", curMonth='" + getCurMonth() + "'" +
            ", curDay='" + getCurDay() + "'" +
            ", lastTime='" + getLastTime() + "'" +
            "}";
    }
}
