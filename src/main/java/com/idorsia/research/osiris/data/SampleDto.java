package com.idorsia.research.osiris.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SampleDto {

	private Integer sampleId;;
	private String actNo;
	private String extReferfence;
	private String cmnt;
	private String userId;
	private Date updDate;
	private String location;
	private String supplier;
	private String catalogNo;
	private String primaryPurpose;
	private String loNo;

	public Integer getSampleId() {
		return sampleId;
	}

	public void setSampleId(Integer sampleId) {
		this.sampleId = sampleId;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getExtReferfence() {
		return extReferfence;
	}

	public void setExtReferfence(String extReferfence) {
		this.extReferfence = extReferfence;
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getCatalogNo() {
		return catalogNo;
	}

	public void setCatalogNo(String catalogNo) {
		this.catalogNo = catalogNo;
	}

	public String getPrimaryPurpose() {
		return primaryPurpose;
	}

	public void setPrimaryPurpose(String primaryPurpose) {
		this.primaryPurpose = primaryPurpose;
	}

	public String getLoNo() {
		return loNo;
	}

	public void setLoNo(String loNo) {
		this.loNo = loNo;
	}

}
