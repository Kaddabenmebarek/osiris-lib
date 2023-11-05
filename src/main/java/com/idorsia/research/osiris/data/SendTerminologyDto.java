package com.idorsia.research.osiris.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SendTerminologyDto {

	private Integer id;
	private String code;
	private String codeListCode;
	private String codeListExtensible;
	private String codeListName;
	private String cdiscSubmissionValue;
	private String cdiscSynonym;
	private String cdiscDefinition;
	private String nciPreferedTerm;
	private Date ctVersion;

	public SendTerminologyDto() {
		super();
	}

	public SendTerminologyDto(Integer id, String code, String codeListCode, String codeListExtensible,
			String codeListName, String cdiscSubmissionValue, String cdiscSynonym, String cdiscDefinition,
			String nciPreferedTerm, Date ctVersion) {
		super();
		this.id = id;
		this.code = code;
		this.codeListCode = codeListCode;
		this.codeListExtensible = codeListExtensible;
		this.codeListName = codeListName;
		this.cdiscSubmissionValue = cdiscSubmissionValue;
		this.cdiscSynonym = cdiscSynonym;
		this.cdiscDefinition = cdiscDefinition;
		this.nciPreferedTerm = nciPreferedTerm;
		this.ctVersion = ctVersion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeListCode() {
		return codeListCode;
	}

	public void setCodeListCode(String codeListCode) {
		this.codeListCode = codeListCode;
	}

	public String getCodeListExtensible() {
		return codeListExtensible;
	}

	public void setCodeListExtensible(String codeListExtensible) {
		this.codeListExtensible = codeListExtensible;
	}

	public String getCodeListName() {
		return codeListName;
	}

	public void setCodeListName(String codeListName) {
		this.codeListName = codeListName;
	}

	public String getCdiscSubmissionValue() {
		return cdiscSubmissionValue;
	}

	public void setCdiscSubmissionValue(String cdiscSubmissionValue) {
		this.cdiscSubmissionValue = cdiscSubmissionValue;
	}

	public String getCdiscSynonym() {
		return cdiscSynonym;
	}

	public void setCdiscSynonym(String cdiscSynonym) {
		this.cdiscSynonym = cdiscSynonym;
	}

	public String getCdiscDefinition() {
		return cdiscDefinition;
	}

	public void setCdiscDefinition(String cdiscDefinition) {
		this.cdiscDefinition = cdiscDefinition;
	}

	public String getNciPreferedTerm() {
		return nciPreferedTerm;
	}

	public void setNciPreferedTerm(String nciPreferedTerm) {
		this.nciPreferedTerm = nciPreferedTerm;
	}

	public Date getCtVersion() {
		return ctVersion;
	}

	public void setCtVersion(Date ctVersion) {
		this.ctVersion = ctVersion;
	}

}
