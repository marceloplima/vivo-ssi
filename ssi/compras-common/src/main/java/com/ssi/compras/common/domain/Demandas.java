package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.ssi.kernel.model.Fornecedores;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Entity
@Table(name="Demandas", schema="compras")
public class Demandas implements Serializable {

	
	private static final long serialVersionUID = 4937005333701013521L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="iddemanda")
	private Long iddemanda;

	@ManyToOne
	@JoinColumn(name="idpessoasolicitante", referencedColumnName="idpessoa")
	private Pessoas autor;

	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Column(name="cnmnumero")
	private String cnmnumero;
	
	@Column(name="datainiciocontratacao")
	private Calendar datainiciocontratacao;
	
	@Column(name="datafimcontratacao")
	private Calendar datafimcontratacao;
	
	@Column(name="cnmtitulocompra")
	private String cnmtitulocompra;
	
	@Column(name="cnmresumodoescopo")
	private String cnmresumodoescopo;
	
	@Column(name="cnmssicontratacaoanterior")
	private String cnmssicontratacaoanterior;
	
	@Column(name="boolobjetodacompracondicionada")
	private Boolean boolobjetodacompracondicionada;
	
	@Column(name="cnmjustificativacondicionamento")
	private String cnmjustificativacondicionamento;
	
	@Column(name="cnmatacitoucdt")
	private String cnmatacitoucdt;
	
	@Column(name="dataaprovacaocitcdt")
	private Calendar dataaprovacaocitcdt;
	
	@Column(name="cnmmegaprojeto")
	private String cnmmegaprojeto;
	
	@Column(name="cnmacaoinversora")
	private String cnmacaoinversora;
	
	@Column(name="booladitivodecontrato")
	private Boolean booladitivodecontrato;
	
	@Column(name="nnrcontratosaseremaditivados")
	private Integer nnrcontratosaseremaditivados;
	
	@Column(name="cnmnumerodecontratosap")
	private String cnmnumerodecontratosap;
	
	@Column(name="cnmrelacionamentodeorcamentopxq")
	private String cnmrelacionamentodeorcamentopxq;
	
	@Column(name="cnmformadepagamento")
	private String cnmformadepagamento;
	
	@Column(name="cnmdeclinaram")
	private String cnmdeclinaram;
	
	@Column(name="cnmnaocotaram")
	private String cnmnaocotaram;
	
	@Column(name="cnmassuntoquestionamento")
	private String cnmassuntoquestionamento;
	
	@Column(name="cnmquestionamento")
	private String cnmquestionamento;
	
	@Column(name="dataquestionamento")
	private Calendar dataquestionamento;
	
	@Column(name="cnmanexoquestionamento")
	private String cnmanexoquestionamento;
	
	@Column(name="datacancelamento")
	private Calendar datacancelamento;
	
	@Column(name="cnmmotivocancelamento")
	private String cnmmotivocancelamento;
	
	@Column(name="cnmtelefonesolicitante")
	private String cnmtelefonesolicitante;
	
	@Column(name="cnmtelefoneresponsaveltecnico")
	private String cnmtelefoneresponsaveltecnico;

	@Column(name="nnrultimonumero")
	private Integer nnrultimonumero;		
	
	@ManyToOne
	@JoinColumn(name="idpessoacancelou", referencedColumnName="idpessoa")
	private Pessoas autorcancelamento;

	@ManyToOne
	@JoinColumn(name="idpessoacomprador", referencedColumnName="idpessoa")
	private Pessoas pessoacomprador;
	
	@ManyToOne
	@JoinColumn(name="idpessoaquestionamento", referencedColumnName="idpessoa")
	private Pessoas autorquestionamento;
	
	@ManyToOne
	@JoinColumn(name="idpessoaresponsaveltecnico", referencedColumnName="idpessoa")
	private Pessoas responsaveltecnico;	

	@ManyToOne
	@JoinColumn(name="idlp", referencedColumnName="idlp")
	private Lps lp;	
	
	@ManyToOne
	@JoinColumn(name="idfornecedor", referencedColumnName="idfornecedor")
	private Fornecedores fornecedor;	
		
	@ManyToOne
	@JoinColumn(name="idtipocondicionamento", referencedColumnName="idtipocondicionamento")	
	private TiposCondicionamento tipoCondicionamento;
	
	@ManyToOne
	@JoinColumn(name="idprioridade", referencedColumnName="idprioridade")	
	private Prioridades prioridade;	
	
	@ManyToOne
	@JoinColumn(name="idpessoaanalistadeaquisicaoresponsavel", referencedColumnName="idpessoa")
	private Pessoas analistaDeAquisicaoResponsavel;
		
	@ManyToOne
	@JoinColumn(name="idpessoaanalistadecontratoresponsavel", referencedColumnName="idpessoa")
	private Pessoas analistaDeContratoResponsavel;
	
	@ManyToOne
	@JoinColumn(name="idpessoacomquemesta", referencedColumnName="idpessoa")
	private Pessoas pessoacomquemesta;

	@ManyToOne
	@JoinColumn(name="idgrupomodulocomquemesta", referencedColumnName="idgrupomodulo")
	private GruposModulos grupocomquemesta;
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private Status status;

	@ManyToOne
	@JoinColumn(name="idstatusanterior", referencedColumnName="idstatus")
	private Status statusanterior;
	
	@OneToMany(mappedBy="demanda", cascade = CascadeType.ALL)
	private Set<Logs> logs;

	@OneToMany(mappedBy="demanda", cascade = CascadeType.ALL)
	private Set<RespostasQuestionamentos> respostasquestionamentos;
	
	@OneToMany(mappedBy="demanda", cascade = CascadeType.ALL)
	private Set<Agendamentos> agendamentos;	

	@OneToMany(mappedBy="demandas", cascade = CascadeType.ALL)
	private Set<Orcamentos> orcamentos;	

	@OneToMany(mappedBy="demandascronograma", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Cronogramas> cronogramas;
	
	@ManyToMany
	@JoinTable(name="compras.CapexOpex_Demandas",
			   joinColumns={@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")},
			   inverseJoinColumns={@JoinColumn(name="idcapexopex", referencedColumnName="idcapexopex")})
	private Set<CapexOpex> capexOpex;
	
	@ManyToMany
	@JoinTable(name="compras.TiposAditivos_Demandas",
			   joinColumns={@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")},
			   inverseJoinColumns={@JoinColumn(name="idtipoaditivo", referencedColumnName="idtipoaditivo")})
	private Set<TiposAditivo> tiposAditivo;
	
	@Column(name="boolanaliseminuta")
	private boolean boolanaliseminuta;
	
	@Version
	@Transient
	private Long version;
	
	public Demandas() {
		
		autor = new Pessoas();
		autorcancelamento = new Pessoas();
		autorquestionamento = new Pessoas();
		responsaveltecnico = new Pessoas();	
		lp = new Lps();	
		fornecedor = new Fornecedores();
		tipoCondicionamento = new TiposCondicionamento();
		prioridade = new Prioridades();	
		analistaDeAquisicaoResponsavel = new Pessoas();
		analistaDeContratoResponsavel = new Pessoas();
		pessoacomquemesta = new Pessoas();
		status = new Status();
		logs = new HashSet<Logs>();
		orcamentos = new HashSet<Orcamentos>();
		cronogramas = new HashSet<Cronogramas>();
		capexOpex = new HashSet<CapexOpex>();
		tiposAditivo = new HashSet<TiposAditivo>();
		agendamentos = new HashSet<Agendamentos>();
		
	}

	
	public Set<Orcamentos> getOrcamentos() {
		return orcamentos;
	}

	public void setOrcamentos(Set<Orcamentos> orcamentos) {
		this.orcamentos = orcamentos;
	}

	public Long getIddemanda() {
		return iddemanda;
	}

	public void setIddemanda(Long iddemanda) {
		this.iddemanda = iddemanda;
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

	public String getCnmnumero() {
		if(cnmnumero == null || cnmnumero.isEmpty())
			return "[ Sem N�mero ]";
		else
			return cnmnumero;
	}

	public void setCnmnumero(String cnmnumero) {
		this.cnmnumero = cnmnumero;
	}

	public Calendar getDatainiciocontratacao() {
		return datainiciocontratacao;
	}

	public void setDatainiciocontratacao(Calendar datainiciocontratacao) {
		this.datainiciocontratacao = datainiciocontratacao;
	}

	public Calendar getDatafimcontratacao() {
		return datafimcontratacao;
	}

	public void setDatafimcontratacao(Calendar datafimcontratacao) {
		this.datafimcontratacao = datafimcontratacao;
	}

	public String getCnmtitulocompra() {
		return cnmtitulocompra;
	}

	public void setCnmtitulocompra(String cnmtitulocompra) {
		this.cnmtitulocompra = cnmtitulocompra;
	}

	
	
	public String getCnmresumodoescopo() {
		return cnmresumodoescopo;
	}


	public void setCnmresumodoescopo(String cnmresumodoescopo) {
		this.cnmresumodoescopo = cnmresumodoescopo;
	}


	public String getCnmssicontratacaoanterior() {
		return cnmssicontratacaoanterior;
	}

	public void setCnmssicontratacaoanterior(String cnmssicontratacaoanterior) {
		this.cnmssicontratacaoanterior = cnmssicontratacaoanterior;
	}

	public Boolean getObjetodacompracondicionada() {
		return boolobjetodacompracondicionada;
	}
		
	public void setBoolobjetodacompracondicionada(
			Boolean boolobjetodacompracondicionada) {
		this.boolobjetodacompracondicionada = boolobjetodacompracondicionada;
	}

	public String getCnmjustificativacondicionamento() {
		return cnmjustificativacondicionamento;
	}

	public void setCnmjustificativacondicionamento(
			String cnmjustificativacondicionamento) {
		this.cnmjustificativacondicionamento = cnmjustificativacondicionamento;
	}

	public String getCnmatacitoucdt() {
		return cnmatacitoucdt;
	}

	public void setCnmatacitoucdt(String cnmatacitoucdt) {
		this.cnmatacitoucdt = cnmatacitoucdt;
	}

	public Calendar getDataaprovacaocitcdt() {
		return dataaprovacaocitcdt;
	}

	public void setDataaprovacaocitcdt(Calendar dataaprovacaocitcdt) {
		this.dataaprovacaocitcdt = dataaprovacaocitcdt;
	}

	public String getCnmmegaprojeto() {
		return cnmmegaprojeto;
	}

	public void setCnmmegaprojeto(String cnmmegaprojeto) {
		this.cnmmegaprojeto = cnmmegaprojeto;
	}

	public String getCnmacaoinversora() {
		return cnmacaoinversora;
	}

	public void setCnmacaoinversora(String cnmacaoinversora) {
		this.cnmacaoinversora = cnmacaoinversora;
	}

	public Boolean getBooladitivodecontrato() {
		return booladitivodecontrato;
	}

	public void setBooladitivodecontrato(Boolean booladitivodecontrato) {
		this.booladitivodecontrato = booladitivodecontrato;
	}

	public Integer getNnrcontratosaseremaditivados() {
		return nnrcontratosaseremaditivados;
	}

	public void setNnrcontratosaseremaditivados(Integer nnrcontratosaseremaditivados) {
		this.nnrcontratosaseremaditivados = nnrcontratosaseremaditivados;
	}

	public String getCnmnumerodecontratosap() {
		return cnmnumerodecontratosap;
	}

	public void setCnmnumerodecontratosap(String cnmnumerodecontratosap) {
		this.cnmnumerodecontratosap = cnmnumerodecontratosap;
	}

	public String getCnmrelacionamentodeorcamentopxq() {
		return cnmrelacionamentodeorcamentopxq;
	}

	public void setCnmrelacionamentodeorcamentopxq(
			String cnmrelacionamentodeorcamentopxq) {
		this.cnmrelacionamentodeorcamentopxq = cnmrelacionamentodeorcamentopxq;
	}

	public String getCnmformadepagamento() {
		return cnmformadepagamento;
	}


	public void setCnmformadepagamento(String cnmformadepagamento) {
		this.cnmformadepagamento = cnmformadepagamento;
	}


	public String getCnmdeclinaram() {
		return cnmdeclinaram;
	}


	public void setCnmdeclinaram(String cnmdeclinaram) {
		this.cnmdeclinaram = cnmdeclinaram;
	}


	public String getCnmnaocotaram() {
		return cnmnaocotaram;
	}


	public void setCnmnaocotaram(String cnmnaocotaram) {
		this.cnmnaocotaram = cnmnaocotaram;
	}


	public String getCnmassuntoquestionamento() {
		return cnmassuntoquestionamento;
	}


	public void setCnmassuntoquestionamento(String cnmassuntoquestionamento) {
		this.cnmassuntoquestionamento = cnmassuntoquestionamento;
	}


	public String getCnmquestionamento() {
		return cnmquestionamento;
	}


	public void setCnmquestionamento(String cnmquestionamento) {
		this.cnmquestionamento = cnmquestionamento;
	}


	public Calendar getDataquestionamento() {
		return dataquestionamento;
	}


	public void setDataquestionamento(Calendar dataquestionamento) {
		this.dataquestionamento = dataquestionamento;
	}


	public String getCnmanexoquestionamento() {
		return cnmanexoquestionamento;
	}


	public void setCnmanexoquestionamento(String cnmanexoquestionamento) {
		this.cnmanexoquestionamento = cnmanexoquestionamento;
	}


	public Calendar getDatacancelamento() {
		return datacancelamento;
	}


	public void setDatacancelamento(Calendar datacancelamento) {
		this.datacancelamento = datacancelamento;
	}


	public String getCnmmotivocancelamento() {
		return cnmmotivocancelamento;
	}


	public void setCnmmotivocancelamento(String cnmmotivocancelamento) {
		this.cnmmotivocancelamento = cnmmotivocancelamento;
	}


	public Pessoas getAutorcancelamento() {
		return autorcancelamento;
	}


	public void setAutorcancelamento(Pessoas autorcancelamento) {
		this.autorcancelamento = autorcancelamento;
	}


	public Integer getNnrultimonumero() {
		return nnrultimonumero;
	}


	public void setNnrultimonumero(Integer nnrultimonumero) {
		this.nnrultimonumero = nnrultimonumero;
	}


	public Pessoas getAutorquestionamento() {
		return autorquestionamento;
	}


	public void setAutorquestionamento(Pessoas autorquestionamento) {
		this.autorquestionamento = autorquestionamento;
	}


	public Pessoas getResponsaveltecnico() {
		return responsaveltecnico;
	}


	public void setResponsaveltecnico(Pessoas responsaveltecnico) {
		this.responsaveltecnico = responsaveltecnico;
	}


	public Lps getLp() {
		return lp;
	}


	public void setLp(Lps lp) {
		this.lp = lp;
	}


	public Fornecedores getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedores fornecedor) {
		this.fornecedor = fornecedor;
	}


	public TiposCondicionamento getTipoCondicionamento() {
		return tipoCondicionamento;
	}


	public void setTipoCondicionamento(TiposCondicionamento tipoCondicionamento) {
		this.tipoCondicionamento = tipoCondicionamento;
	}


	public Prioridades getPrioridade() {
		return prioridade;
	}


	public void setPrioridade(Prioridades prioridade) {
		this.prioridade = prioridade;
	}

	public Pessoas getAnalistaDeContratoResponsavel() {
		return analistaDeContratoResponsavel;
	}


	public void setAnalistaDeContratoResponsavel(
			Pessoas analistaDeContratoResponsavel) {
		this.analistaDeContratoResponsavel = analistaDeContratoResponsavel;
	}


	public Set<CapexOpex> getCapexOpex() {
		return capexOpex;
	}


	public void setCapexOpex(Set<CapexOpex> capexOpex) {
		this.capexOpex = capexOpex;
	}


	public Set<TiposAditivo> getTiposAditivo() {
		return tiposAditivo;
	}


	public void setTiposAditivo(Set<TiposAditivo> tiposAditivo) {
		this.tiposAditivo = tiposAditivo;
	}


	public Pessoas getPessoacomquemesta() {
		return pessoacomquemesta;
	}


	public void setPessoacomquemesta(Pessoas pessoacomquemesta) {
		this.pessoacomquemesta = pessoacomquemesta;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Set<Logs> getLogs() {
		return logs;
	}

	public void setLogs(Set<Logs> logs) {
		this.logs = logs;
	}
	
	public Boolean getBoolobjetodacompracondicionada() {
		return boolobjetodacompracondicionada;
	}
	
	public Set<Cronogramas> getCronogramas() {
		return cronogramas;
	}

	public void setCronogramas(Set<Cronogramas> cronogramas) {
		this.cronogramas = cronogramas;
	}
		
	public Pessoas getAnalistaDeAquisicaoResponsavel() {
		return analistaDeAquisicaoResponsavel;
	}


	public void setAnalistaDeAquisicaoResponsavel(
			Pessoas analistaDeAquisicaoResponsavel) {
		this.analistaDeAquisicaoResponsavel = analistaDeAquisicaoResponsavel;
	}
	
	public Status getStatusanterior() {
		return statusanterior;
	}


	public void setStatusanterior(Status statusanterior) {
		this.statusanterior = statusanterior;
	}

	public boolean isBoolanaliseminuta() {
		return boolanaliseminuta;
	}


	public void setBoolanaliseminuta(boolean boolanaliseminuta) {
		this.boolanaliseminuta = boolanaliseminuta;
	}
		
	public String getCnmtelefonesolicitante() {
		return cnmtelefonesolicitante;
	}


	public void setCnmtelefonesolicitante(String cnmtelefonesolicitante) {
		this.cnmtelefonesolicitante = cnmtelefonesolicitante;
	}


	public String getCnmtelefoneresponsaveltecnico() {
		return cnmtelefoneresponsaveltecnico;
	}


	public void setCnmtelefoneresponsaveltecnico(
			String cnmtelefoneresponsaveltecnico) {
		this.cnmtelefoneresponsaveltecnico = cnmtelefoneresponsaveltecnico;
	}
		
	public Set<Agendamentos> getAgendamentos() {
		return agendamentos;
	}


	public void setAgendamentos(Set<Agendamentos> agendamentos) {
		this.agendamentos = agendamentos;
	}
		
	public Set<RespostasQuestionamentos> getRespostasquestionamentos() {
		return respostasquestionamentos;
	}


	public void setRespostasquestionamentos(
			Set<RespostasQuestionamentos> respostasquestionamentos) {
		this.respostasquestionamentos = respostasquestionamentos;
	}
		
	public GruposModulos getGrupocomquemesta() {
		return grupocomquemesta;
	}


	public void setGrupocomquemesta(GruposModulos grupocomquemesta) {
		this.grupocomquemesta = grupocomquemesta;
	}


	public Pessoas getPessoacomprador() {
		return pessoacomprador;
	}


	public void setPessoacomprador(Pessoas pessoacomprador) {
		this.pessoacomprador = pessoacomprador;
	}


	@Override
	public int hashCode() {
		return this.getIddemanda() != null ? 
		this.getClass().hashCode() + this.getIddemanda().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Demandas objint = (Demandas)obj;
		
		if(this.getIddemanda() != null && objint.getIddemanda() != null){
			if(this.getIddemanda().equals(objint.getIddemanda())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
