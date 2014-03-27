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
 * Representa os tipos de telefone (celular, comercial, etc) para cada telefone
 * 
 */

@Entity
@Cacheable
@Table(name="TiposTelefone")
public class TiposTelefone implements java.io.Serializable{
		
	private static final long serialVersionUID = -8200650060533830953L;
	
	public static final long ID_CELULAR = 3L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipotelefone")
	private Long idtipotelefone;
	
	@Column(name="cnmtipotelefone")
	private String cnmtipotelefone;
	
	@Column(name="flagativo")
	private boolean flagativo;
	
	@Transient
	private boolean checked;
	
	public Long getIdtipotelefone() {
		return idtipotelefone;
	}
	public void setIdtipotelefone(Long idtipotelefone) {
		this.idtipotelefone = idtipotelefone;
	}
	public String getCnmtipotelefone() {
		return cnmtipotelefone;
	}
	public void setCnmtipotelefone(String cnmtipotelefone) {
		this.cnmtipotelefone = cnmtipotelefone;
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
	
	public TiposTelefone getByid(Long id){
		TiposTelefone obj = new TiposTelefone();
		obj.setIdtipotelefone(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.idtipotelefone != null ? 
		this.getClass().hashCode() + this.idtipotelefone.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposTelefone objint = (TiposTelefone)obj;
		
		if(this.getIdtipotelefone() != null && objint.getIdtipotelefone() != null){
			if(this.getIdtipotelefone().equals(objint.getIdtipotelefone())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
