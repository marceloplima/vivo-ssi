package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Cacheable
@Table(name="EventosAcionaRequisicoesCompras", schema="compras")
public class EventosAcionaRequisicoesCompras implements Serializable{

	private static final long serialVersionUID = -2448914569116444479L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventosacionarequisicaocompra")
	private Long ideventosacionarequisicaocompra;
	
	@OneToOne
	@JoinColumn(name="idevento")
	private Eventos acreqcomp;
	
	@ManyToOne
	@JoinColumn(name="idtipocompra", referencedColumnName="idtipocompra")
	private TiposCompras tipocompra;
	
	@Column(name="cnmgrupodeproduto")
	private String cnmgrupodeproduto;
	
	@Column(name="nnrvalor")
	private BigDecimal nnrvalor;
	
	@Column(name="dataenviorfpaomercado")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataenviorfpaomercado;
	
	public Long getIdeventosacionarequisicaocompra() {
		return ideventosacionarequisicaocompra;
	}

	public void setIdeventosacionarequisicaocompra(
			Long ideventosacionarequisicaocompra) {
		this.ideventosacionarequisicaocompra = ideventosacionarequisicaocompra;
	}
	
	public String getCnmgrupodeproduto() {
		return cnmgrupodeproduto;
	}

	public void setCnmgrupodeproduto(String cnmgrupodeproduto) {
		this.cnmgrupodeproduto = cnmgrupodeproduto;
	}
	
	public Eventos getAcreqcomp() {
		return acreqcomp;
	}

	public void setAcreqcomp(Eventos acreqcomp) {
		this.acreqcomp = acreqcomp;
	}
	
	public TiposCompras getTipocompra() {
		return tipocompra;
	}

	public void setTipocompra(TiposCompras tipocompra) {
		this.tipocompra = tipocompra;
	}
	
	public BigDecimal getNnrvalor() {
		return nnrvalor;
	}

	public void setNnrvalor(BigDecimal nnrvalor) {
		this.nnrvalor = nnrvalor;
	}

	public Date getDataenviorfpaomercado() {
		return dataenviorfpaomercado;
	}

	public void setDataenviorfpaomercado(Date dataenviorfpaomercado) {
		this.dataenviorfpaomercado = dataenviorfpaomercado;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventosacionarequisicaocompra() != null ? 
		this.getClass().hashCode() + this.getIdeventosacionarequisicaocompra().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosAcionaRequisicoesCompras objint = (EventosAcionaRequisicoesCompras)obj;
		
		if(this.getIdeventosacionarequisicaocompra() != null && objint.getIdeventosacionarequisicaocompra() != null){
			if(this.getIdeventosacionarequisicaocompra().equals(objint.getIdeventosacionarequisicaocompra())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
}
