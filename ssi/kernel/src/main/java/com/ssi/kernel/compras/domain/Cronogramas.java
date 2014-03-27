package com.ssi.kernel.compras.domain;

import java.io.Serializable;
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

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="Cronogramas", schema="compras")
public class Cronogramas implements Serializable {
	
	private static final long serialVersionUID = 1218893684523971257L;
			
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcronograma")	
	private Long idcronograma;
	
	@Column(name="datarealizacao")	
	private Calendar datarealizacao;
	
	@Column(name="dataprevisaooriginal")	
	private Calendar dataprevisaooriginal;
	
	@Column(name="dataprevisaoatual")
	private Calendar dataprevisaoatual;
	
	@ManyToOne
	@JoinColumn(name="idatividade", referencedColumnName="idatividade")	
	private AtividadesCronogramas atividade;
	
	@ManyToOne
	@JoinColumn(name="idpessoaresponsavel", referencedColumnName="idpessoa")	
	private Pessoas responsavel;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demandascronograma;
	
	public Cronogramas() {}
	
	public Long getIdcronograma() {
		return idcronograma;
	}

	public void setIdcronograma(Long idcronograma) {
		this.idcronograma = idcronograma;
	}

	public Calendar getDatarealizacao() {
		return datarealizacao;
	}

	public void setDatarealizacao(Calendar datarealizacao) {
		this.datarealizacao = datarealizacao;
	}

	public Calendar getDataprevisaooriginal() {
		return dataprevisaooriginal;
	}

	public void setDataprevisaooriginal(Calendar dataprevisaooriginal) {
		this.dataprevisaooriginal = dataprevisaooriginal;
	}

	public Calendar getDataprevisaoatual() {
		return dataprevisaoatual;
	}

	public void setDataprevisaoatual(Calendar dataprevisaoatual) {
		this.dataprevisaoatual = dataprevisaoatual;
	}

	public AtividadesCronogramas getAtividade() {
		return atividade;
	}

	public void setAtividade(AtividadesCronogramas atividade) {
		this.atividade = atividade;
	}

	public Pessoas getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoas responsavel) {
		this.responsavel = responsavel;
	}
		
	public Demandas getDemandascronograma() {
		return demandascronograma;
	}

	public void setDemandascronograma(Demandas demandascronograma) {
		this.demandascronograma = demandascronograma;
	}

	@Override
	public int hashCode() {
		return this.getIdcronograma() != null ? 
		this.getClass().hashCode() + this.getIdcronograma().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Cronogramas objint = (Cronogramas)obj;
		
		if(this.getIdcronograma() != null && objint.getIdcronograma() != null){
			if(this.getIdcronograma().equals(objint.getIdcronograma())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}	



}
