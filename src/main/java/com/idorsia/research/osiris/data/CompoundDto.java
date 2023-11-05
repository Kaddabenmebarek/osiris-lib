package com.idorsia.research.osiris.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CompoundDto {

	private Integer compoundId;
	private String actNo;
	private String chemLabJournal;

	public Integer getCompoundId() {
		return compoundId;
	}

	public void setCompoundId(Integer compoundId) {
		this.compoundId = compoundId;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getChemLabJournal() {
		return chemLabJournal;
	}

	public void setChemLabJournal(String chemLabJournal) {
		this.chemLabJournal = chemLabJournal;
	}

}
