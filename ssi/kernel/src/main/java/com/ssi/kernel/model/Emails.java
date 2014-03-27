package com.ssi.kernel.model;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/** 
 * Classe que centraliza os e-mail dos funcionarios
 */
@Entity
@Cacheable
@Table(name="Emails")
public class Emails implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6546329662073079021L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idemail")
	private Long idemail;
	
	@Column(name="cnmemail")
	private String cnmemail;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToOne
	@JoinColumn(name="idpessoa", referencedColumnName="idpessoa")
	private Pessoas emailspessoa;
	
	@Column(name="flagativo", columnDefinition="default true")
	private boolean flagativo;
	
	@Transient
	private boolean checked;
	
	public Long getIdemail() {
		return idemail;
	}
	public void setIdemail(Long idemail) {
		this.idemail = idemail;
	}
	public String getCnmemail() {
		return cnmemail;
	}
	public void setCnmemail(String cnmemail) {
		this.cnmemail = cnmemail;
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	@Override
	public int hashCode() {
		return this.idemail != null ? 
		this.getClass().hashCode() + this.idemail.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Emails objint = (Emails)obj;
		
		if(this.getIdemail() != null && objint.getIdemail() != null){
			if(this.getIdemail().equals(objint.getIdemail())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	public Pessoas getEmailspessoa() {
		return emailspessoa;
	}
	public void setEmailspessoa(Pessoas emailspessoa) {
		this.emailspessoa = emailspessoa;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isFlagativo() {
		return flagativo;
	}
	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}
	
}
