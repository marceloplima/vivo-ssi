package com.ssi.kernel.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Dominios")
public class Dominios implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7578284859981237543L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddominio")
	private Integer iddominio;
	
	@Column(name="cnmdominio")
	private String cnmdominio;
	
	@Column(name="cnmip")
	private String cnmip;
	
	public Integer getIddominio() {
		return iddominio;
	}
	public void setIddominio(Integer iddominio) {
		this.iddominio = iddominio;
	}
	public String getCnmdominio() {
		return cnmdominio;
	}
	public void setCnmdominio(String cnmdominio) {
		this.cnmdominio = cnmdominio;
	}
	public String getCnmip() {
		return cnmip;
	}
	public void setCnmip(String cnmip) {
		this.cnmip = cnmip;
	}
	
	public Dominios getByid(Integer id_dominio){
		Dominios dom = new Dominios();
		dom.setIddominio(id_dominio);
		return dom;
	}
	
	@Override
	public int hashCode() {
		return this.iddominio != null ? 
		this.getClass().hashCode() + this.iddominio.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Dominios objint = (Dominios)obj;
		
		if(this.getIddominio() != null && objint.getIddominio() != null){
			if(this.getIddominio().equals(objint.getIddominio())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
}
