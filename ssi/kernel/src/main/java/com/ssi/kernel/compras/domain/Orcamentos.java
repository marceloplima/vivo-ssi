package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Ufs;

@Entity
@Cacheable
@Table(name="Orcamentos", schema="compras")
public class Orcamentos implements Serializable {

	private static final long serialVersionUID = -1624646087338877338L;
	
	public static final long ID_EMISSAO_SSI = 1L;
	public static final long ID_VALIDACAO_SSI = 2L;
	public static final long ID_ENVIO_RFP_MERCADO = 3L;
	public static final long ID_RECEBIMENTO_PROPOSTAS = 4L;
	public static final long ID_EMISSAO_LAUDO_TECNICO = 5L;
	public static final long ID_VALIDACAO_LAUDO_TECNICO = 6L;
	public static final long ID_ADJUDICACAO = 10L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idorcamento")	
	private Long idorcamento;
	
	@ManyToOne
	@JoinColumn(name="idpessoasautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Column(name="cnmano1")
	private String cnmano1;
	
	@Column(name="nnrano1")
	private BigDecimal nnrano1;
	
	@Column(name="cnmano2")
	private String cnmano2;
	
	@Column(name="nnrano2")
	private BigDecimal nnrano2;
	
	@Column(name="cnmano3")
	private String cnmano3;
	
	@Column(name="nnrano3")
	private BigDecimal nnrano3;
	
	@Column(name="cnmano4")
	private String cnmano4;
	
	@Column(name="nnrano4")
	private BigDecimal nnrano4;

	@Column(name="nnrdemaisvalores")
	private BigDecimal nnrdemaisanos;
	
	@Column(name="cnmcontabil")
	private String cnmcontabil;
	
	@Column(name="cnmcusto")
	private String cnmcusto;
	
	@Column(name="cnmrc")
	private String cnmrc;
	
	@Column(name="flagativo")
	private String flagativo;
	
	@ManyToOne
	@JoinColumn(name="iduf", referencedColumnName="iduf")	
	private Ufs ufs; 
	
	@OneToMany(mappedBy="orcamento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Logs> logs;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demandas;
	
	@ManyToOne
	@JoinColumn(name="idcapexopex", referencedColumnName="idcapexopex")	
	private CapexOpex capexopex;
			
	public Orcamentos() {
		autor = new Pessoas();
		ufs = new Ufs();
		logs = new HashSet<Logs>();
		demandas = new Demandas();
		capexopex = new CapexOpex();
		flagativo = "1";
	}
	
	public Orcamentos(Orcamentos orcamento) {

		this.autor = orcamento.getAutor();
		this.datacadastro = orcamento.getDatacadastro();
		this.cnmano1 = orcamento.getCnmano1();
		this.nnrano1 = orcamento.getNnrano1();
		this.cnmano2 = orcamento.getCnmano2();
		this.nnrano2 = orcamento.getNnrano2();
		this.cnmano3 = orcamento.getCnmano3();
		this.nnrano3 = orcamento.getNnrano3();
		this.cnmano4 = orcamento.getCnmano4();
		this.nnrano4 = orcamento.getNnrano4();
		this.nnrdemaisanos = orcamento.getNnrdemaisanos();
		this.cnmcontabil = orcamento.getCnmcontabil();
		this.cnmcusto = orcamento.getCnmcusto();
		this.cnmrc = orcamento.getCnmrc();
		this.flagativo = orcamento.getFlagativo();
		this.ufs = orcamento.getUfs();
		this.logs = orcamento.getLogs();
		this.demandas = orcamento.getDemandas();
		this.capexopex = orcamento.getCapexOpex();		
	}


	public Demandas getDemandas() {
		return demandas;
	}

	public void setDemandas(Demandas demandas) {
		this.demandas = demandas;
	}

	public Long getIdorcamento() {
		return idorcamento;
	}
	public void setIdorcamento(Long idorcamento) {
		this.idorcamento = idorcamento;
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
	public String getCnmano1() {
		return cnmano1;
	}
	public void setCnmano1(String cnmano1) {
		this.cnmano1 = cnmano1;
	}
	public BigDecimal getNnrano1() {
		return nnrano1;
	}
	public void setNnrano1(BigDecimal nnrano1) {
		this.nnrano1 = nnrano1;
	}
	public String getCnmano2() {
		return cnmano2;
	}
	public void setCnmano2(String cnmano2) {
		this.cnmano2 = cnmano2;
	}
	public BigDecimal getNnrano2() {
		return nnrano2;
	}
	public void setNnrano2(BigDecimal nnrano2) {
		this.nnrano2 = nnrano2;
	}
	public String getCnmano3() {
		return cnmano3;
	}
	public void setCnmano3(String cnmano3) {
		this.cnmano3 = cnmano3;
	}
	public BigDecimal getNnrano3() {
		return nnrano3;
	}
	public void setNnrano3(BigDecimal nnrano3) {
		this.nnrano3 = nnrano3;
	}
	public String getCnmano4() {
		return cnmano4;
	}
	public void setCnmano4(String cnmano4) {
		this.cnmano4 = cnmano4;
	}
	public BigDecimal getNnrano4() {
		return nnrano4;
	}
	public void setNnrano4(BigDecimal nnrano4) {
		this.nnrano4 = nnrano4;
	}
	public String getCnmcontabil() {
		return cnmcontabil;
	}
	public void setCnmcontabil(String cnmcontabil) {
		this.cnmcontabil = cnmcontabil;
	}
	public String getCnmcusto() {
		return cnmcusto;
	}
	public void setCnmcusto(String cnmcusto) {
		this.cnmcusto = cnmcusto;
	}
	public String getCnmrc() {
		return cnmrc;
	}
	public void setCnmrc(String cnmrc) {
		this.cnmrc = cnmrc;
	}
	
	public Set<Logs> getLogs() {
		return logs;
	}

	public void setLogs(Set<Logs> logs) {
		this.logs = logs;
	}
	
	public Ufs getUfs() {
		return ufs;
	}

	public void setUfs(Ufs ufs) {
		this.ufs = ufs;
	}
		
	public CapexOpex getCapexOpex() {
		return capexopex;
	}

	public void setCapexOpex(CapexOpex capexOpex) {
		this.capexopex = capexOpex;
	}
		
	public BigDecimal getNnrdemaisanos() {
		return nnrdemaisanos;
	}

	public void setNnrdemaisanos(BigDecimal nnrdemaisanos) {
		this.nnrdemaisanos = nnrdemaisanos;
	}
		
	public boolean isFlagativo() {
		return "1".equals(getFlagativo()) ? true : false; 
	}

	public String getFlagativo() {
		return flagativo;
	}
	
	public void setFlagativo(String flagativo) {
		this.flagativo = flagativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idorcamento == null) ? 0 : idorcamento.hashCode());
		result = prime * result + ((ufs == null) ? 0 : ufs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orcamentos other = (Orcamentos) obj;
		if (idorcamento == null) {
			if (other.idorcamento != null)
				return false;
		} else if (!idorcamento.equals(other.idorcamento))
			return false;
		if (ufs == null) {
			if (other.ufs != null)
				return false;
		} else if (!ufs.equals(other.ufs))
			return false;
		return true;
	}
	
}
