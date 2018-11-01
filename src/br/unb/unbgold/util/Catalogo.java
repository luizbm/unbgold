package br.unb.unbgold.util;

import java.util.List;

public class Catalogo {

	String iri;
	String identifier;
	String title;
	String description;
	String Organization;
	List<String> VocabularyEncodingScheme;
	String source;
	List<String> FileFormat;
	List<String> subject;
	String frequency;
	String type;
	String date;
	String created;
	String language;
	String Dataset;
	
	
	public String getIri() {
		return iri;
	}
	public void setIri(String iri) {
		this.iri = iri;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
	public List<String> getVocabularyEncodingScheme() {
		return VocabularyEncodingScheme;
	}
	public void setVocabularyEncodingScheme(List<String> vocabularyEncodingScheme) {
		VocabularyEncodingScheme = vocabularyEncodingScheme;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getFileFormat() {
		return FileFormat;
	}
	public void setFileFormat(List<String> fileFormat) {
		FileFormat = fileFormat;
	}
	public List<String> getSubject() {
		return subject;
	}
	public void setSubject(List<String> subject) {
		this.subject = subject;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDataset() {
		return Dataset;
	}
	public void setDataset(String dataset) {
		Dataset = dataset;
	}

	
}
