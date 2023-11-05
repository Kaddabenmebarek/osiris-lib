package com.idorsia.research.osiris.data;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EmployeeGroupDto {

	
	private int groupId;
	private Integer groupParent;
	private String groupName;
	private String wikiName;
	private String comments;
	private BigDecimal disabled;
	private String upduser;
	private Date upddate;
	private Integer functional;
	private Integer nuboId;

	
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Integer getGroupParent() {
		return groupParent;
	}

	public void setGroupParent(Integer groupParent) {
		this.groupParent = groupParent;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getWikiName() {
		return wikiName;
	}

	public void setWikiName(String wikiName) {
		this.wikiName = wikiName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUpduser() {
		return upduser;
	}

	public void setUpduser(String upduser) {
		this.upduser = upduser;
	}

	public Date getUpddate() {
		return upddate;
	}

	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}

	public BigDecimal getDisabled() {
		return disabled;
	}

	public void setDisabled(BigDecimal disabled) {
		this.disabled = disabled;
	}

	public Integer getFunctional() {
		return functional;
	}

	public void setFunctional(Integer functional) {
		this.functional = functional;
	}

	public Integer getNuboId() {
		return nuboId;
	}

	public void setNuboId(Integer nuboId) {
		this.nuboId = nuboId;
	}

}
