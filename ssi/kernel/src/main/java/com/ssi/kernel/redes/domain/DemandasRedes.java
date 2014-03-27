package com.ssi.kernel.redes.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Regionais;

@Entity
@Table(name="DemandasRedes", schema="redes")
public class DemandasRedes implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3692723581738397857L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="iddemanda")
	private Long iddemanda;

	@ManyToOne
	@JoinColumn(name="idpessoasolicitante", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@ManyToOne
	@JoinColumn(name="idpessoacancelou", referencedColumnName="idpessoa")
	private Pessoas autorcancelamento;
	
	@ManyToOne
	@JoinColumn(name="idpessoacomquemesta", referencedColumnName="idpessoa")
	private Pessoas pessoacomquemesta;
	
	@ManyToOne
	@JoinColumn(name="idgrupomodulocomquemesta", referencedColumnName="idgrupomodulo")
	private GruposModulos grupocomquemesta;

	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Column(name="cnmnumero")
	private String cnmnumero;
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private StatusRedes status;

	@ManyToOne
	@JoinColumn(name="idstatusanterior", referencedColumnName="idstatus")
	private StatusRedes statusanterior;
	
	@ManyToOne
	@JoinColumn(name="idareasolicitante", referencedColumnName="idarea")
	private Areas areasolicitante;
	
	@ManyToOne
	@JoinColumn(name="idarearequisitada", referencedColumnName="idarea")
	private Areas arearequisitada;
	
	@ManyToOne
	@JoinColumn(name="idprioridade", referencedColumnName="idprioridade")
	private PrioridadesRedes prioridade;
	
	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(name="Demandas_Regionais", schema="redes",
			   joinColumns={@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")},
			   inverseJoinColumns={@JoinColumn(name="idregional", referencedColumnName="idregional")})
	private Set<Regionais> regionaisdemandas;
	
	@ManyToOne
	@JoinColumn(name="idatividade", referencedColumnName="idatividade")
	private Atividades atividade;
	
	@OneToMany(mappedBy="demanda", cascade = CascadeType.ALL)
	private Set<LogsRedes> logs;
	
	@ManyToOne
	@JoinColumn(name="idinstrucao", referencedColumnName="idinstrucao")
	private Instrucao instrucao;
	
	@OneToMany(mappedBy="demanda", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Rotas> rotas;
	
	@Column(name="datacancelamento")
	private Calendar datacancelamento;
	
	@Column(name="cnmmotivocancelamento")
	private String cnmmotivocancelamento;
	
	@Column(name="cnmtelefone")
	private String cnmtelefone;
	
	@Column(name="cnmmotivacao")
	private String cnmmotivacao;
	
	@Column(name="cnmreferencia")
	private String cnmreferencia;
	
	@Column(name="prazoexecucao")
	private Calendar prazoexecucao;
	
	@ManyToOne
	@JoinColumn(name="idpessoaresponsaveltecnico", referencedColumnName="idpessoa")
	private Pessoas responsaveltecnico;	
	
	@ManyToOne
	@JoinColumn(name="idgrupocopiado", referencedColumnName="idgrupomodulo")
	private GruposModulos grupocopiado;
	
	@Column(name="cdsobjetivo")
	private String cdsobjetivo;
	
	@Column(name="cdsareaaplicacao")
	private String cdsareaaplicacao;
	
	@Column(name="cdscenarioatual")
	private String cdscenarioatual;
	
	@Column(name="cdscenarioproposto")
	private String cdscenarioproposto;
	
	@Column(name="cdsatividadesexecucao")
	private String cdsatividadesexecucao;
	
	@Column(name="cdsrecursosnecessarios")
	private String cdsrecursosnecessarios;
	
	@Column(name="cdstestesrecomendados")
	private String cdstestesrecomendados;
	
	@Column(name="flagtarifacao")
	private Boolean flagtarifacao;
	
	@Column(name="cnmdetalhetarifacao")
	private String cnmdetalhetarifacao;
	
	@Column(name="flagsinalizacao")
	private Boolean flagsinalizacao;
	
	@Column(name="cnmdetalhesinalizacao")
	private String cnmdetalhesinalizacao;
	
	@Column(name="flagseguranca")
	private Boolean flagseguranca;
	
	@Column(name="cnmdetalheseguranca")
	private String cnmdetalheseguranca;
	
	@Column(name="flagalarmistica")
	private Boolean flagalarmistica;
	
	@Column(name="cnmdetalhealarmistica")
	private String cnmdetalhealarmistica;
	
	@Column(name="flagdadostrafego")
	private Boolean flagdadostrafego;
	
	@Column(name="cnmdadostrafego")
	private String cnmdadostrafego;
	
	@Column(name="flagavisoimprensa")
	private Boolean flagavisoimprensa;
	
	@Column(name="cnmdetalheavisoimprensa")
	private String cnmdetalheavisoimprensa;
	
	@Column(name="unid")
	private String unid;
	
	@Column(name="complexidade")
	private String complexidade;
	
	@Column(name="numeroassociado")
	private String numeroassociado;
	
	@Column(name="nnrultimonumero")
	private Integer nnrultimonumero;
	
	@ManyToOne
	@JoinColumn(name="idmotivacao", referencedColumnName="idmotivacao")
	private MotivacoesRedes motivacao;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="DemandasRedes_Copiados", schema="redes",
			   joinColumns={@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")},
			   inverseJoinColumns={@JoinColumn(name="idpessoa", referencedColumnName="idpessoa")})
	private Set<Pessoas> copiados;	
		
	@Version
	@Transient
	private Long version;
	
	@ManyToOne
	@JoinColumn(name="idaplicacaonotes", referencedColumnName="idaplicacaonotes")
	private AplicacaoNotes aplicacaoNotes;
	
	@Transient
	private boolean retornarInformacaoParaNotes=false;
	
	public DemandasRedes() {
		
		autor = new Pessoas();
		status = new StatusRedes();
		logs = new HashSet<LogsRedes>();
		responsaveltecnico = new Pessoas();
		copiados = new HashSet<Pessoas>();
		
	}
			
	public AplicacaoNotes getAplicacaoNotes() {
		return aplicacaoNotes;
	}

	public void setAplicacaoNotes(AplicacaoNotes aplicacaoNotes) {
		this.aplicacaoNotes = aplicacaoNotes;
	}

	public Set<Pessoas> getCopiados() {
		return copiados;
	}

	public void setCopiados(Set<Pessoas> copiados) {
		this.copiados = copiados;
	}

	public Integer getNnrultimonumero() {
		return nnrultimonumero;
	}

	public void setNnrultimonumero(Integer nnrultimonumero) {
		this.nnrultimonumero = nnrultimonumero;
	}

	public DemandasRedes(String unid) {
		this.unid = unid;
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

	public Pessoas getPessoacomquemesta() {
		return pessoacomquemesta;
	}

	public void setPessoacomquemesta(Pessoas pessoacomquemesta) {
		this.pessoacomquemesta = pessoacomquemesta;
	}

	public GruposModulos getGrupocomquemesta() {
		return grupocomquemesta;
	}

	public void setGrupocomquemesta(GruposModulos grupocomquemesta) {
		this.grupocomquemesta = grupocomquemesta;
	}

	public StatusRedes getStatus() {
		return status;
	}

	public void setStatus(StatusRedes status) {
		this.status = status;
	}

	public StatusRedes getStatusanterior() {
		return statusanterior;
	}

	public void setStatusanterior(StatusRedes statusanterior) {
		this.statusanterior = statusanterior;
	}

	public Set<LogsRedes> getLogs() {
		return logs;
	}

	public void setLogs(Set<LogsRedes> logs) {
		this.logs = logs;
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

	public Areas getAreasolicitante() {
		return areasolicitante;
	}

	public void setAreasolicitante(Areas areasolicitante) {
		this.areasolicitante = areasolicitante;
	}

	public Areas getArearequisitada() {
		return arearequisitada;
	}

	public void setArearequisitada(Areas arearequisitada) {
		this.arearequisitada = arearequisitada;
	}

	public PrioridadesRedes getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(PrioridadesRedes prioridade) {
		this.prioridade = prioridade;
	}

	public String getCnmtelefone() {
		return cnmtelefone;
	}

	public void setCnmtelefone(String cnmtelefone) {
		this.cnmtelefone = cnmtelefone;
	}

	public Atividades getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividades atividade) {
		this.atividade = atividade;
	}
	
	public Instrucao getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(Instrucao instrucao) {
		this.instrucao = instrucao;
	}
	
	public Pessoas getAutorcancelamento() {
		return autorcancelamento;
	}

	public void setAutorcancelamento(Pessoas autorcancelamento) {
		this.autorcancelamento = autorcancelamento;
	}

	public String getCnmmotivacao() {
		return cnmmotivacao;
	}

	public void setCnmmotivacao(String cnmmotivacao) {
		this.cnmmotivacao = cnmmotivacao;
	}
	
	public String getCnmreferencia() {
		return cnmreferencia;
	}

	public void setCnmreferencia(String cnmreferencia) {
		this.cnmreferencia = cnmreferencia;
	}
	
	public Calendar getPrazoexecucao() {
		return prazoexecucao;
	}

	public void setPrazoexecucao(Calendar prazoexecucao) {
		this.prazoexecucao = prazoexecucao;
	}

	public Pessoas getResponsaveltecnico() {
		return responsaveltecnico;
	}

	public void setResponsaveltecnico(Pessoas responsaveltecnico) {
		this.responsaveltecnico = responsaveltecnico;
	}

	public GruposModulos getGrupocopiado() {
		return grupocopiado;
	}

	public void setGrupocopiado(GruposModulos grupocopiado) {
		this.grupocopiado = grupocopiado;
	}
	
	public String getCdsobjetivo() {
		return cdsobjetivo;
	}

	public void setCdsobjetivo(String cdsobjetivo) {
		this.cdsobjetivo = cdsobjetivo;
	}

	public String getCdsareaaplicacao() {
		return cdsareaaplicacao;
	}

	public void setCdsareaaplicacao(String cdsareaaplicacao) {
		this.cdsareaaplicacao = cdsareaaplicacao;
	}

	public String getCdscenarioatual() {
		return cdscenarioatual;
	}

	public void setCdscenarioatual(String cdscenarioatual) {
		this.cdscenarioatual = cdscenarioatual;
	}

	public String getCdscenarioproposto() {
		return cdscenarioproposto;
	}

	public void setCdscenarioproposto(String cdscenarioproposto) {
		this.cdscenarioproposto = cdscenarioproposto;
	}

	public String getCdsatividadesexecucao() {
		return cdsatividadesexecucao;
	}

	public void setCdsatividadesexecucao(String cdsatividadesexecucao) {
		this.cdsatividadesexecucao = cdsatividadesexecucao;
	}

	public String getCdsrecursosnecessarios() {
		return cdsrecursosnecessarios;
	}

	public void setCdsrecursosnecessarios(String cdsrecursosnecessarios) {
		this.cdsrecursosnecessarios = cdsrecursosnecessarios;
	}

	public String getCdstestesrecomendados() {
		return cdstestesrecomendados;
	}

	public void setCdstestesrecomendados(String cdstestesrecomendados) {
		this.cdstestesrecomendados = cdstestesrecomendados;
	}

	public Boolean getFlagtarifacao() {
		return flagtarifacao;
	}

	public void setFlagtarifacao(Boolean flagtarifacao) {
		this.flagtarifacao = flagtarifacao;
	}

	public String getCnmdetalhetarifacao() {
		return cnmdetalhetarifacao;
	}

	public void setCnmdetalhetarifacao(String cnmdetalhetarifacao) {
		this.cnmdetalhetarifacao = cnmdetalhetarifacao;
	}

	public Boolean getFlagsinalizacao() {
		return flagsinalizacao;
	}

	public void setFlagsinalizacao(Boolean flagsinalizacao) {
		this.flagsinalizacao = flagsinalizacao;
	}

	public String getCnmdetalhesinalizacao() {
		return cnmdetalhesinalizacao;
	}

	public void setCnmdetalhesinalizacao(String cnmdetalhesinalizacao) {
		this.cnmdetalhesinalizacao = cnmdetalhesinalizacao;
	}

	public Boolean getFlagseguranca() {
		return flagseguranca;
	}

	public void setFlagseguranca(Boolean flagseguranca) {
		this.flagseguranca = flagseguranca;
	}

	public String getCnmdetalheseguranca() {
		return cnmdetalheseguranca;
	}

	public void setCnmdetalheseguranca(String cnmdetalheseguranca) {
		this.cnmdetalheseguranca = cnmdetalheseguranca;
	}

	public Boolean getFlagalarmistica() {
		return flagalarmistica;
	}

	public void setFlagalarmistica(Boolean flagalarmistica) {
		this.flagalarmistica = flagalarmistica;
	}

	public String getCnmdetalhealarmistica() {
		return cnmdetalhealarmistica;
	}

	public void setCnmdetalhealarmistica(String cnmdetalhealarmistica) {
		this.cnmdetalhealarmistica = cnmdetalhealarmistica;
	}

	public Boolean getFlagdadostrafego() {
		return flagdadostrafego;
	}

	public void setFlagdadostrafego(Boolean flagdadostrafego) {
		this.flagdadostrafego = flagdadostrafego;
	}

	public String getCnmdadostrafego() {
		return cnmdadostrafego;
	}

	public void setCnmdadostrafego(String cnmdadostrafego) {
		this.cnmdadostrafego = cnmdadostrafego;
	}

	public Boolean getFlagavisoimprensa() {
		return flagavisoimprensa;
	}

	public void setFlagavisoimprensa(Boolean flagavisoimprensa) {
		this.flagavisoimprensa = flagavisoimprensa;
	}

	public String getCnmdetalheavisoimprensa() {
		return cnmdetalheavisoimprensa;
	}

	public void setCnmdetalheavisoimprensa(String cnmdetalheavisoimprensa) {
		this.cnmdetalheavisoimprensa = cnmdetalheavisoimprensa;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}
	
	public String getComplexidade() {
		return complexidade;
	}

	public void setComplexidade(String complexidade) {
		this.complexidade = complexidade;
	}
	
	public String getNumeroassociado() {
		return numeroassociado;
	}

	public void setNumeroassociado(String numeroassociado) {
		this.numeroassociado = numeroassociado;
	}
	public List<Regionais> getRegionaisdemandas() {
		if(regionaisdemandas!=null && regionaisdemandas.size()>=1){
			return new ArrayList<Regionais>();
		}
		
		return new ArrayList<Regionais>(regionaisdemandas);
	}

	public void setRegionaisdemandas(Set<Regionais> regionaisdemandas) {
		this.regionaisdemandas = regionaisdemandas;
	}
	
	public List<Rotas> getRotas() {
		if(rotas!=null)
			return new LinkedList<Rotas>(rotas);
		else
			return new LinkedList<Rotas>();
	}

	public void setRotas(Set<Rotas> rotas) {
		this.rotas = rotas;
	}
	
	public MotivacoesRedes getMotivacao() {
		return motivacao;
	}

	public void setMotivacao(MotivacoesRedes motivacao) {
		this.motivacao = motivacao;
	}
				
	public boolean isRetornarInformacaoParaNotes() {
		return retornarInformacaoParaNotes;
	}

	public void setRetornarInformacaoParaNotes(boolean retornarInformacaoParaNotes) {
		this.retornarInformacaoParaNotes = retornarInformacaoParaNotes;
	}

	@Override
	public int hashCode() {
		return this.getIddemanda() != null ? 
		this.getClass().hashCode() + this.getIddemanda().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		DemandasRedes objint = (DemandasRedes)obj;
		
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
