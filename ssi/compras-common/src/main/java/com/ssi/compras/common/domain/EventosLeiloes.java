package com.ssi.compras.common.domain;


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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="EventosLeiloes", schema="compras")
public class EventosLeiloes implements Serializable{

	private static final long serialVersionUID = -2268354576792801931L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoleilao")
	private Long ideventoleilao;
	
	@OneToOne
	@JoinColumn(name="idevento")
	private Eventos eleilao;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name="idmodalidade", referencedColumnName="idmodalidade")
	private Modalidades modalidade;
	
	@Column(name="dataleilao")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataleilao;
	
	@Column(name="cnmhoraleilao")
	private String cnmhoraleilao;

	@ManyToOne
	@JoinColumn(name="idtipoleilao", referencedColumnName="idtipoleilao")
	private TiposLeiloes tipoleilao;
	
	@Column(name="nnrvalorfatorinicial")
	private String nnrvalorfatorinicial;
	
	@Column(name="nnrvalorinicial")
	private String nnrvalorinicial;
	
	@Column(name="nnrsalvaguarda")
	private String nnrsalvaguarda;
	
	@Column(name="nnrdecrescimoacrescimo")
	private String nnrdecrescimoacrescimo;
	
	@Column(name="nnrlancevencedor")
	private String nnrlancevencedor;
	
	@Column(name="cnmcomentario")
	private String cnmcomentario;
	
	public Long getIdeventoleilao() {
		return ideventoleilao;
	}

	public void setIdeventoleilao(Long ideventoleilao) {
		this.ideventoleilao = ideventoleilao;
	}

	public Eventos getEleilao() {
		return eleilao;
	}

	public void setEleilao(Eventos eleilao) {
		this.eleilao = eleilao;
	}

	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Modalidades getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidades modalidade) {
		this.modalidade = modalidade;
	}

	public Calendar getDataleilao() {
		return dataleilao;
	}

	public void setDataleilao(Calendar dataleilao) {
		this.dataleilao = dataleilao;
	}

	public String getCnmhoraleilao() {
		return cnmhoraleilao;
	}

	public void setCnmhoraleilao(String cnmhoraleilao) {
		this.cnmhoraleilao = cnmhoraleilao;
	}

	public TiposLeiloes getTipoleilao() {
		return tipoleilao;
	}

	public void setTipoleilao(TiposLeiloes tipoleilao) {
		this.tipoleilao = tipoleilao;
	}

	public String getNnrvalorfatorinicial() {
		return nnrvalorfatorinicial;
	}

	public void setNnrvalorfatorinicial(String nnrvalorfatorinicial) {
		this.nnrvalorfatorinicial = nnrvalorfatorinicial;
	}

	public String getNnrvalorinicial() {
		return nnrvalorinicial;
	}

	public void setNnrvalorinicial(String nnrvalorinicial) {
		this.nnrvalorinicial = nnrvalorinicial;
	}

	public String getNnrsalvaguarda() {
		return nnrsalvaguarda;
	}

	public void setNnrsalvaguarda(String nnrsalvaguarda) {
		this.nnrsalvaguarda = nnrsalvaguarda;
	}

	public String getNnrdecrescimoacrescimo() {
		return nnrdecrescimoacrescimo;
	}

	public void setNnrdecrescimoacrescimo(String nnrdecrescimoacrescimo) {
		this.nnrdecrescimoacrescimo = nnrdecrescimoacrescimo;
	}

	public String getNnrlancevencedor() {
		return nnrlancevencedor;
	}

	public void setNnrlancevencedor(String nnrlancevencedor) {
		this.nnrlancevencedor = nnrlancevencedor;
	}

	public String getCnmcomentario() {
		return cnmcomentario;
	}

	public void setCnmcomentario(String cnmcomentario) {
		this.cnmcomentario = cnmcomentario;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventoleilao() != null ? 
		this.getClass().hashCode() + this.getIdeventoleilao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosLeiloes objint = (EventosLeiloes)obj;
		
		if(this.getIdeventoleilao() != null && objint.getIdeventoleilao() != null){
			if(this.getIdeventoleilao().equals(objint.getIdeventoleilao())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
