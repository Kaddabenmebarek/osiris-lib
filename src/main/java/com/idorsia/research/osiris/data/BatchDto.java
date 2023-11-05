package com.idorsia.research.osiris.data;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BatchDto {

	private String chemLabJournal;
	private String actNo;
	private String chemist;
	private String producer;
	private String projectName;
	private Date preparationDate;
	private String preparationDesc;
	private String purification;
	private Integer meltingPoint;
	private Integer boilingPoint;
	private String color;
	private BigDecimal amount;
	private BigDecimal yield;
	private String cmnt;
	private String userId;
	private Date updDate;
	private String location;

	public String getChemLabJournal() {
		return chemLabJournal;
	}

	public void setChemLabJournal(String chemLabJournal) {
		this.chemLabJournal = chemLabJournal;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getChemist() {
		return chemist;
	}

	public void setChemist(String chemist) {
		this.chemist = chemist;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getPreparationDate() {
		return preparationDate;
	}

	public void setPreparationDate(Date preparationDate) {
		this.preparationDate = preparationDate;
	}

	public String getPreparationDesc() {
		return preparationDesc;
	}

	public void setPreparationDesc(String preparationDesc) {
		this.preparationDesc = preparationDesc;
	}

	public String getPurification() {
		return purification;
	}

	public void setPurification(String purification) {
		this.purification = purification;
	}

	public Integer getMeltingPoint() {
		return meltingPoint;
	}

	public void setMeltingPoint(Integer meltingPoint) {
		this.meltingPoint = meltingPoint;
	}

	public Integer getBoilingPoint() {
		return boilingPoint;
	}

	public void setBoilingPoint(Integer boilingPoint) {
		this.boilingPoint = boilingPoint;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getYield() {
		return yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

	public String getCmnt() {
		return cmnt;
	}

	public void setCmnt(String cmnt) {
		this.cmnt = cmnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
