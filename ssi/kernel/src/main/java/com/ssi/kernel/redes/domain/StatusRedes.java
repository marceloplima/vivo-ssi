package com.ssi.kernel.redes.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="StatusRedes", schema="redes")
public class StatusRedes implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4113548436698521523L;
	
	//Demanda
	public static final long ID_DEMANDA_INICIAL = 0L;
	public static final long ID_DEMANDA_RASCUNHO = 1L;
	public static final long ID_DEMANDA_ANALISE_PELA_EXECUTORA = 2L;	
	public static final long ID_DEMANDA_AGUARDANDO_INFORMACOES = 3L;
	public static final long ID_DEMANDA_EM_EXECUCAO = 5L;
	public static final long ID_DEMANDA_CONCLUIDA = 6L;	
	public static final long ID_DEMANDA_CANCELADA = 7L;
	public static final long ID_DEMANDA_REVISAO_PELO_EMISSOR = 8L;
			
			
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idstatus")
	private Long idstatus;
	
	@Column(name="cnmstatus")
	private String cnmstatus;
	
	@Column(name="boolsinalizador")
	private Boolean boolsinalizador;
	
	@ManyToOne
	@JoinColumn(name="idtipodocumento", referencedColumnName="idtipodocumento")
	private TiposDocumentosRedes tipoDocumento;
	
	@OneToMany(mappedBy="status", cascade = CascadeType.ALL)
	private Set<DemandasRedes> demandas;

	public StatusRedes() {}
		
	public StatusRedes(Long idstatus) {
		super();
		this.idstatus = idstatus;
	}

	public Long getIdstatus() {
		return idstatus;
	}

	public void setIdstatus(Long idstatus) {
		this.idstatus = idstatus;
	}

	public String getCnmstatus() {
		return cnmstatus;
	}

	public void setCnmstatus(String cnmstatus) {
		this.cnmstatus = cnmstatus;
	}

	public TiposDocumentosRedes getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TiposDocumentosRedes tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	
	public Boolean getBoolsinalizador() {
		return boolsinalizador;
	}

	public void setBoolsinalizador(Boolean boolsinalizador) {
		this.boolsinalizador = boolsinalizador;
	}
	
	
	public Set<DemandasRedes> getDemandas() {
		return demandas;
	}

	public void setDemandas(Set<DemandasRedes> demandas) {
		this.demandas = demandas;
	}


	@Override
	public int hashCode() {
		return this.getIdstatus() != null ? 
		this.getClass().hashCode() + this.getIdstatus().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		StatusRedes objint = (StatusRedes)obj;
		
		if(this.getIdstatus() != null && objint.getIdstatus() != null){
			if(this.getIdstatus().equals(objint.getIdstatus())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
	

}
