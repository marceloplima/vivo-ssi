package com.ssi.kernel.redes.domain;

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
@Table(name="TiposDocumentosRedes", schema="redes")
public class TiposDocumentosRedes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4795881603952022123L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipodocumento")
	private Long idtipodocumento;
	
	@Column(name="cnmtipodocumento")
	private String cnmtipodocumento;
	
	@OneToMany(mappedBy="tipoDocumento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<StatusRedes> status;

	
	public TiposDocumentosRedes() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Long getIdtipodocumento() {
		return idtipodocumento;
	}



	public void setIdtipodocumento(Long idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}



	public Set<StatusRedes> getStatus() {
		return status;
	}



	public void setStatus(Set<StatusRedes> status) {
		this.status = status;
	}

	public String getCnmtipodocumento() {
		return cnmtipodocumento;
	}

	public void setCnmtipodocumento(String cnmtipodocumento) {
		this.cnmtipodocumento = cnmtipodocumento;
	}

	@Override
	public int hashCode() {
		return this.getIdtipodocumento() != null ? 
		this.getClass().hashCode() + this.getIdtipodocumento().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposDocumentosRedes objint = (TiposDocumentosRedes)obj;
		
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
