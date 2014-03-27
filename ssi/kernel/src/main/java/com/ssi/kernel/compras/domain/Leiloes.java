package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="Leiloes", schema="compras")
public class Leiloes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7426544193684287284L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idleilao")
	private Long idleilao;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToOne
	@JoinColumn(name="idmodalidade", referencedColumnName="idmodalidade")
	private Modalidades modalidade;
	
	@ManyToOne
	@JoinColumn(name="idtipoleilao", referencedColumnName="idtipoleilao")
	private TiposLeiloes tipoleilao;
	
	@Column(name="dataleilao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataleilao;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demandaleilao;
	
	@Column(name="horaleilao")
	private Integer horaleilao;
	
	@Column(name="minutoleilao")
	private Integer minutoleilao;
	
	@Column(name="fatorinicial")
	private BigDecimal fatorinicial;
	
	@Column(name="valorinicial")
	private BigDecimal valorinicial;
	
	@Column(name="salvaguarda")
	private BigDecimal salvaguarda;
	
	@Column(name="decresacresc")
	private BigDecimal decresacresc;
	
	@Column(name="lancevencedor")
	private BigDecimal lancevencedor;
	
	@Column(name="comentario")
	private String comentario;
	
	@ManyToMany
	@JoinTable(name="compras.Leiloes_Proppart",
			   joinColumns={@JoinColumn(name="idleilao", referencedColumnName="idleilao")},
			   inverseJoinColumns={@JoinColumn(name="idproposta", referencedColumnName="idproposta")})
	private List<Propostas> proppart = new ArrayList<Propostas>();
	
	@ManyToMany
	@JoinTable(name="compras.Leiloes_Propvenc",
			   joinColumns={@JoinColumn(name="idleilao", referencedColumnName="idleilao")},
			   inverseJoinColumns={@JoinColumn(name="idproposta", referencedColumnName="idproposta")})
	private List<Propostas> propvenc = new ArrayList<Propostas>();
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private Status status;
	
	@Transient
	private boolean checked;
	
	public Leiloes(){
		
	}
	
	public Long getIdleilao() {
		return idleilao;
	}
	public void setIdleilao(Long idleilao) {
		this.idleilao = idleilao;
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
	public Modalidades getModalidade() {
		return modalidade;
	}
	public void setModalidade(Modalidades modalidade) {
		this.modalidade = modalidade;
	}
	public TiposLeiloes getTipoleilao() {
		return tipoleilao;
	}
	public void setTipoleilao(TiposLeiloes tipoleilao) {
		this.tipoleilao = tipoleilao;
	}
	public Date getDataleilao() {
		return dataleilao;
	}
	public void setDataleilao(Date dataleilao) {
		this.dataleilao = dataleilao;
	}
	public Integer getHoraleilao() {
		return horaleilao;
	}
	public void setHoraleilao(Integer horaleilao) {
		this.horaleilao = horaleilao;
	}
	public Integer getMinutoleilao() {
		return minutoleilao;
	}
	public void setMinutoleilao(Integer minutoleilao) {
		this.minutoleilao = minutoleilao;
	}
	public BigDecimal getFatorinicial() {
		return fatorinicial;
	}
	public void setFatorinicial(BigDecimal fatorinicial) {
		this.fatorinicial = fatorinicial;
	}
	public BigDecimal getValorinicial() {
		return valorinicial;
	}
	public void setValorinicial(BigDecimal valorinicial) {
		this.valorinicial = valorinicial;
	}
	public BigDecimal getSalvaguarda() {
		return salvaguarda;
	}
	public void setSalvaguarda(BigDecimal salvaguarda) {
		this.salvaguarda = salvaguarda;
	}
	public BigDecimal getDecresacresc() {
		return decresacresc;
	}
	public void setDecresacresc(BigDecimal decresacresc) {
		this.decresacresc = decresacresc;
	}
	public BigDecimal getLancevencedor() {
		return lancevencedor;
	}
	public void setLancevencedor(BigDecimal lancevencedor) {
		this.lancevencedor = lancevencedor;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

	public Demandas getDemandaleilao() {
		return demandaleilao;
	}

	public void setDemandaleilao(Demandas demandaleilao) {
		this.demandaleilao = demandaleilao;
	}
	
	public List<Propostas> getProppart() {
		return new ArrayList<Propostas>(proppart);
	}

	public void setProppart(List<Propostas> proppart) {
		this.proppart = proppart;
	}

	public List<Propostas> getPropvenc() {
		return new ArrayList<Propostas>(propvenc);
	}

	public void setPropvenc(List<Propostas> propvenc) {
		this.propvenc = propvenc;
	}
	
	@Override
	public int hashCode() {
		return this.getIdleilao() != null ? 
		this.getClass().hashCode() + this.getIdleilao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Leiloes objint = (Leiloes)obj;
		
		if(this.getIdleilao() != null && objint.getIdleilao() != null){
			if(this.getIdleilao().equals(objint.getIdleilao())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
