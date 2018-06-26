package com.luckystar.web.domain;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WorkTimeBoard  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 真名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 艺名
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 繁星id
     */
    @Column(name = "star_id")
    private Long starId;

	/**
	 * 按月统计的工作时长
	 */
	@Column(name = "worktime_by_month")
	private Long workTimeByMonth;

	/**
	 * 按界定标准划分后的合格天数
	 */
	@Column(name = "judge_by_month")
	private String judgeTimeByMonth;

	/**
	 * 按天统计的工作时长
	 */
    @Column(name = "work_time")
    private Long workTime;

    /**
     * 具体日期
     */
    @Column(name = "cur_day")
    private LocalDate curDay;

    @Column(name = "boundary_value")
    private Long boundaryValue;


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


	public Long getStarId() {
		return starId;
	}


	public void setStarId(Long starId) {
		this.starId = starId;
	}


	public Long getWorkTimeByMonth() {
		return workTimeByMonth;
	}


	public void setWorkTimeByMonth(Long workTimeByMonth) {
		this.workTimeByMonth = workTimeByMonth;
	}


	public String getJudgeTimeByMonth() {
		return judgeTimeByMonth;
	}


	public void setJudgeTimeByMonth(String judgeTimeByMonth) {
		this.judgeTimeByMonth = judgeTimeByMonth;
	}


	public Long getWorkTime() {
		return workTime;
	}


	public void setWorkTime(Long workTime) {
		this.workTime = workTime;
	}


	public LocalDate getCurDay() {
		return curDay;
	}


	public void setCurDay(LocalDate curDay) {
		this.curDay = curDay;
	}


    public Long getBoundaryValue() {
        return boundaryValue;
    }

    public void setBoundaryValue(Long boundaryValue) {
        this.boundaryValue = boundaryValue;
    }
}
