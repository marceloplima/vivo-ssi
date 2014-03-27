package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Atividades", schema="compras")
public class AtividadesCronogramas implements Serializable {
	
	private static final long serialVersionUID = 4794769345894659908L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idatividade")
	private Long idatividade;
	
	@Column(name="cnmatividade")
	private String cnmatividade;
	
	@OneToMany(mappedBy="atividade", cascade = CascadeType.ALL)
	private List<Cronogramas> cronogramas;

	public AtividadesCronogramas() {}

	public AtividadesCronogramas(Long idatividade) {
		super();
		this.idatividade = idatividade;
	}

	public Long getIdatividade() {
		return idatividade;
	}

	public void setIdatividade(Long idatividade) {
		this.idatividade = idatividade;
	}

	public String getCnmatividade() {
		return cnmatividade;
	}

	public void setCnmatividade(String cnmatividade) {
		this.cnmatividade = cnmatividade;
	}

	public List<Cronogramas> getCronogramas() {
		return cronogramas;
	}

	public void setCronogramas(List<Cronogramas> cronogramas) {
		this.cronogramas = cronogramas;
	}

	@Override
	public int hashCode() {
		return this.getIdatividade() != null ? 
		this.getClass().hashCode() + this.getIdatividade().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		AtividadesCronogramas objint = (AtividadesCronogramas)obj;
		
		if(this.getIdatividade() != null && objint.getIdatividade() != null){
			if(this.getIdatividade().equals(objint.getIdatividade())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}	

}
