package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="TiposDocumentos", schema="compras")
public class TiposDocumentos implements Serializable {

	private static final long serialVersionUID = 4361750971050659551L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipodocumento")
	private Long idtipodocumento;
	
	@Column(name="cnmtipostatus")
	private String cnmtipostatus;
	
	@OneToMany(mappedBy="tipoDocumento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Status> status;

	
	public TiposDocumentos() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Long getIdtipodocumento() {
		return idtipodocumento;
	}



	public void setIdtipodocumento(Long idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}



	public String getCnmtipostatus() {
		return cnmtipostatus;
	}



	public void setCnmtipostatus(String cnmtipostatus) {
		this.cnmtipostatus = cnmtipostatus;
	}



	public Set<Status> getStatus() {
		return status;
	}



	public void setStatus(Set<Status> status) {
		this.status = status;
	}



	@Override
	public int hashCode() {
		return this.getIdtipodocumento() != null ? 
		this.getClass().hashCode() + this.getIdtipodocumento().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposDocumentos objint = (TiposDocumentos)obj;
		
		if(this.getIdtipodocumento() != null && objint.getIdtipodocumento() != null){
			if(this.getIdtipodocumento().equals(objint.getIdtipodocumento())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
		

}
