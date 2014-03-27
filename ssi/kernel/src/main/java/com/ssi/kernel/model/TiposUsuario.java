package com.ssi.kernel.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 
 * Classe que representa os tipos de usu�rios que utilizam os sistemas SSI
 */

@Entity
@Cacheable
@Table(name="TiposUsuario")
public class TiposUsuario implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2921798134468891536L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipousuario")
	private Long idtipousuario;
	
	@Column(name="cnmtipousuario")
	private String cnmtipousuario;
	
	@Column(name="flagativo")
	private boolean flagativo;
	
	@Transient
	private boolean checked;
	
	public Long getIdtipousuario() {
		return idtipousuario;
	}

	public void setIdtipousuario(Long idtipousuario) {
		this.idtipousuario = idtipousuario;
	}

	public String getCnmtipousuario() {
		return cnmtipousuario;
	}

	public void setCnmtipousuario(String cnmtipousuario) {
		this.cnmtipousuario = cnmtipousuario;
	}
	
	public TiposUsuario getByid(Long id){
		TiposUsuario obj = new TiposUsuario();
		obj.setIdtipousuario(id);
		return obj;
	}
	
	public boolean isFlagativo() {
		return flagativo;
	}

	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public int hashCode() {
		return this.idtipousuario != null ? 
		this.getClass().hashCode() + this.idtipousuario.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposUsuario objint = (TiposUsuario)obj;
		
		if(this.getIdtipousuario() != null && objint.getIdtipousuario() != null){
			if(this.getIdtipousuario().equals(objint.getIdtipousuario())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
