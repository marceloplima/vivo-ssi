package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="Eventos", schema="compras")
public class Eventos implements Serializable {

	private static final long serialVersionUID = 4771331429500488005L;
	
	public static final long ID_ANALISE_PELO_GERENTE = 10L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idevento")
	private Long idevento;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name="idstatusanterior", referencedColumnName="idstatus")
	private Status statusanterior;
	
	@Column(name="cnmcomentario")
	private String cnmcomentario;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demanda;

	@OneToOne(mappedBy="eventoarc", cascade=CascadeType.ALL)
	private EventosAberturasRequisicoesCompras arc;
	
	@OneToOne(mappedBy="eventoagc", cascade=CascadeType.ALL)
	private EventosAnaliseGerenteCompra agc;
	
	@OneToOne(mappedBy="eventoam", cascade=CascadeType.ALL)
	private EventosAnaliseMinuta am;
	
	@OneToOne(mappedBy="eventoem", cascade=CascadeType.ALL)
	private EventosEnvioAoMercado em;
	
	@OneToOne(mappedBy="acreqcomp", cascade=CascadeType.ALL)
	private EventosAcionaRequisicoesCompras acionareqcomp;
	
	@OneToOne(mappedBy="indicanovocomp", cascade=CascadeType.ALL)
	private EventosIndicaNovoCompradores indicanovocomp;
	
	@OneToOne(mappedBy="eleilao", cascade=CascadeType.ALL)
	private EventosLeiloes eleilao;
	
	@OneToOne(mappedBy="evaltec", cascade=CascadeType.ALL)
	private EventosValidacoesTecnicas evaltec;
	
	@OneToOne(mappedBy="eventoindicaanalistacontrato", cascade=CascadeType.ALL)
	private EventosIndicaAnalistaContrato eindanalistacontrato;

	@OneToOne(mappedBy="eventosolicitacaorevisaoaquisicao", cascade=CascadeType.ALL)
	private EventosSolicitacaoRevisaoAquisicao esolicitacaorevisaoaquisicao;
	
	@OneToOne(mappedBy="eventogenerico", cascade=CascadeType.ALL)
	private EventosGenericos egenerico;
	
	@OneToOne(mappedBy="eventoAlteraCronograma", cascade=CascadeType.ALL)
	private EventosAlteraCronograma eventosAlteraCronograma;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Transient
	private boolean checked;
	
	public Eventos(){
		egenerico = new EventosGenericos();
	}
	
	public Eventos(int idstatus, int identificador){
		switch(idstatus){
			case 9:
				arc = new EventosAberturasRequisicoesCompras();
			break;
			
			case 10:
				agc = new EventosAnaliseGerenteCompra();
			break;
			
			case 11:
				am = new EventosAnaliseMinuta();
			break;
			
			case 12:
				em = new EventosEnvioAoMercado();
			break;
			
			case 13:
				acionareqcomp = new EventosAcionaRequisicoesCompras();
			break;
			
			case 14:
				indicanovocomp = new EventosIndicaNovoCompradores();
			break;
			
			case 15:
				eleilao = new EventosLeiloes(); // Registrar Leil�o
			break;
			
			case 16:
				eleilao = new EventosLeiloes(); // Finalizar Leil�o
			break;
			
			case 25:
				
				if(identificador == 1)
					evaltec = new EventosValidacoesTecnicas();
				else if(identificador == 2){
					acionareqcomp = new EventosAcionaRequisicoesCompras();
				}
			break;
			
			case 50:
				esolicitacaorevisaoaquisicao = new EventosSolicitacaoRevisaoAquisicao();
				break;

			case 51:
				eindanalistacontrato = new EventosIndicaAnalistaContrato();
				break;	
				
			case 52:
				eventosAlteraCronograma = new EventosAlteraCronograma();
				break;
				
			default:
				egenerico = new EventosGenericos();
		}
	}
	
	public Long getIdevento() {
		return idevento;
	}

	public void setIdevento(Long idevento) {
		this.idevento = idevento;
	}
	
	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Demandas getDemanda() {
		return demanda;
	}

	public void setDemanda(Demandas demanda) {
		this.demanda = demanda;
	}
	
	public EventosAberturasRequisicoesCompras getArc() {
		return arc;
	}

	public void setArc(EventosAberturasRequisicoesCompras arc) {
		this.arc = arc;
	}
	
	public EventosAnaliseGerenteCompra getAgc() {
		return agc;
	}

	public void setAgc(EventosAnaliseGerenteCompra agc) {
		this.agc = agc;
	}
	
	public EventosAnaliseMinuta getAm() {
		return am;
	}

	public void setAm(EventosAnaliseMinuta am) {
		this.am = am;
	}
	
	public EventosEnvioAoMercado getEm() {
		return em;
	}

	public void setEm(EventosEnvioAoMercado em) {
		this.em = em;
	}
	
	public EventosAcionaRequisicoesCompras getAcionareqcomp() {
		return acionareqcomp;
	}

	public void setAcionareqcomp(EventosAcionaRequisicoesCompras acionareqcomp) {
		this.acionareqcomp = acionareqcomp;
	}
	
	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	public EventosIndicaNovoCompradores getIndicanovocomp() {
		return indicanovocomp;
	}
	public void setIndicanovocomp(EventosIndicaNovoCompradores indicanovocomp) {
		this.indicanovocomp = indicanovocomp;
	}
	
	public EventosLeiloes getEleilao() {
		return eleilao;
	}

	public void setEleilao(EventosLeiloes eleilao) {
		this.eleilao = eleilao;
	}
	
	public EventosValidacoesTecnicas getEvaltec() {
		return evaltec;
	}

	public void setEvaltec(EventosValidacoesTecnicas evaltec) {
		this.evaltec = evaltec;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public EventosGenericos getEgenerico() {
		return egenerico;
	}

	public void setEgenerico(EventosGenericos egenerico) {
		this.egenerico = egenerico;
	}

	public String getCnmcomentario() {
		return cnmcomentario;
	}

	public void setCnmcomentario(String cnmcomentario) {
		this.cnmcomentario = cnmcomentario;
	}
				
	public EventosIndicaAnalistaContrato getEindanalistacontrato() {
		return eindanalistacontrato;
	}

	public void setEindanalistacontrato(
			EventosIndicaAnalistaContrato eindanalistacontrato) {
		this.eindanalistacontrato = eindanalistacontrato;
	}

	public EventosSolicitacaoRevisaoAquisicao getEsolicitacaorevisaoaquisicao() {
		return esolicitacaorevisaoaquisicao;
	}

	public void setEsolicitacaorevisaoaquisicao(
			EventosSolicitacaoRevisaoAquisicao esolicitacaorevisaoaquisicao) {
		this.esolicitacaorevisaoaquisicao = esolicitacaorevisaoaquisicao;
	}
		
	public EventosAlteraCronograma getEventosAlteraCronograma() {
		return eventosAlteraCronograma;
	}

	public void setEventosAlteraCronograma(
			EventosAlteraCronograma eventosAlteraCronograma) {
		this.eventosAlteraCronograma = eventosAlteraCronograma;
	}

	@Override
	public int hashCode() {
		return this.getIdevento() != null ? 
		this.getClass().hashCode() + this.getIdevento().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Eventos objint = (Eventos)obj;
		
		if(this.getIdevento() != null && objint.getIdevento() != null){
			if(this.getIdevento().equals(objint.getIdevento())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public Status getStatusanterior() {
		return statusanterior;
	}

	public void setStatusanterior(Status statusanterior) {
		this.statusanterior = statusanterior;
	}

}
