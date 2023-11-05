package com.idorsia.research.osiris.data;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SubstanceDto {

	private String actNo;
	private String formula;
	private BigDecimal molWeight;
	private BigDecimal absWeight;
	private String systematicName;
	private Character known;
	private String knownSource;
	private String substanceClass;
	private String molFile;
	private String cmnt;
	private String userId;
	private Date updDate;
	private BigDecimal clogp;
	private String idCode;
	private String coordinates;
	private Long tautomer;
	private Integer flag;

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public BigDecimal getMolWeight() {
		return molWeight;
	}

	public void setMolWeight(BigDecimal molWeight) {
		this.molWeight = molWeight;
	}

	public BigDecimal getAbsWeight() {
		return absWeight;
	}

	public void setAbsWeight(BigDecimal absWeight) {
		this.absWeight = absWeight;
	}

	public String getSystematicName() {
		return systematicName;
	}

	public void setSystematicName(String systematicName) {
		this.systematicName = systematicName;
	}

	public Character getKnown() {
		return known;
	}

	public void setKnown(Character known) {
		this.known = known;
	}

	public String getKnownSource() {
		return knownSource;
	}

	public void setKnownSource(String knownSource) {
		this.knownSource = knownSource;
	}

	public String getSubstanceClass() {
		return substanceClass;
	}

	public void setSubstanceClass(String substanceClass) {
		this.substanceClass = substanceClass;
	}

	public String getMolFile() {
		return molFile;
	}

	public void setMolFile(String molFile) {
		this.molFile = molFile;
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

	public BigDecimal getClogp() {
		return clogp;
	}

	public void setClogp(BigDecimal clogp) {
		this.clogp = clogp;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public Long getTautomer() {
		return tautomer;
	}

	public void setTautomer(Long tautomer) {
		this.tautomer = tautomer;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
