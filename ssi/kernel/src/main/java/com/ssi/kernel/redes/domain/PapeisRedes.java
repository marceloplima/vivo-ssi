package com.ssi.kernel.redes.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="PapeisRedes", schema="redes")
public class PapeisRedes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2788132504071473468L;

	public static final int ID_EMISSOR = 1;
	public static final int ID_REQUISITADO = 2;
	public static final int ID_COPIADO = 3;
	public static final int ID_RESPONSAVELTECNICO = 4;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpapel")
	private Long idpapel;
	
	@Column(name="cnmpapel")
	private String cnmpapel;

	public Long getIdpapel() {
		return idpapel;
	}

	public void setIdpapel(Long idpapel) {
		this.idpapel = idpapel;
	}

	public String getCnmpapel() {
		return cnmpapel;
	}

	public void setCnmpapel(String cnmpapel) {
		this.cnmpapel = cnmpapel;
	}
	
	@Override
	public int hashCode() {
		return this.getIdpapel() != null ? 
		this.getClass().hashCode() + this.getIdpapel().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		PapeisRedes objint = (PapeisRedes)obj;
		
		if(this.getIdpapel() != null && objint.getIdpapel() != null){
			if(this.getIdpapel().equals(objint.getIdpapel())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
