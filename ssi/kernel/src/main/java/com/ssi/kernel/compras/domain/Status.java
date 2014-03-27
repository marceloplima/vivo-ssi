package com.ssi.kernel.compras.domain;

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
@Table(name="Status", schema="compras")
public class Status implements Serializable {

	private static final long serialVersionUID = -7419860084290226891L;
	
	//Demanda
	public static final long ID_DEMANDA_INICIAL = 0L;
	public static final long ID_DEMANDA_RASCUNHO = 1L;
	public static final long ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO = 6L;	
	public static final long ID_DEMANDA_REVISAO_PELO_EMISSOR = 7L;
	public static final long ID_DEMANDA_PROPOSTA_EM_ANALISE = 8L;
	public static final long ID_DEMANDA_ABERTURA_REQUISICAO_COMPRA = 9L;	
	public static final long ID_DEMANDA_ANALISE_GERENTE_COMPRA = 10L;
	public static final long ID_DEMANDA_ENVIO_AO_MERCADO = 12L;
	public static final long ID_DEMANDA_ACIONA_CRIACAO_RC = 13L;
	public static final long ID_DEMANDA_INDICA_NOVO_COMPRADOR = 14L;
	public static final long ID_DEMANDA_REGISTRA_LEILAO = 15L;
	public static final long ID_DEMANDA_FINALIZA_LEILAO = 16L;
	public static final long ID_DEMANDA_VALIDACAO_TECNICA = 17L;
	public static final long ID_DEMANDA_CANCELADA = 18L;
	public static final long ID_DEMANDA_VALIDACAO_PELO_ANALISTA = 25L;
	public static final long ID_DEMANDA_REVISAO_PELO_ANALISTA_AQUISICAO = 27L;
	public static final long ID_DEMANDA_AGUARDANDO_PROPOSTA = 28L;
	public static final long ID_DEMANDA_VALIDACAO_LAUDO_TECNICO = 32L;	
	public static final long ID_DEMANDA_AGENDAMENTO_MESA_COMPRA = 36L;
	public static final long ID_DEMANDA_VALIDACAO_SEGUNDO_NIVEL = 37L;
	public static final long ID_DEMANDA_AGUARDANDO_RESULTADO_MESA_COMPRA = 44L;	
	public static final long ID_DEMANDA_COMPRA_ADJUDICADA = 45L;
	public static final long ID_DEMANDA_CONCLUIDA = 47L;
	
	//Proposta
	public static final long ID_PROPOSTA_A_ENCAMINHAR = 29L;
	public static final long ID_PROPOSTA_APROVADA = 33L;
	public static final long ID_PROPOSTA_REPROVADA = 34L;
		
	//Agendamento
	public static final long ID_AGENDAMENTO_NOVO = 39L;
	public static final long ID_AGENDAMENTO_AGENDADO = 40L;
	public static final long ID_AGENDAMENTO_CONCLUIDO = 41L;
	public static final long ID_AGENDAMENTO_NAO_ADJUDICADO = 46L;
	
	//Leil�o
	public static final long ID_LEILAO_EM_ABERTO = 42L;
	public static final long ID_LEILAO_FINALIZADO = 43L;
	
	//Minuta
	public static final long ID_MINUTA_ANALISE = 11L;
	
			
			
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
	private TiposDocumentos tipoDocumento;
	
	@OneToMany(mappedBy="status", cascade = CascadeType.ALL)
	private Set<Demandas> demandas;

	@OneToMany(mappedBy="status", cascade = CascadeType.ALL)
	private Set<Agendamentos> agendamentos;
	
	@OneToMany(mappedBy="statusdemandamomentosolicitacao", cascade = CascadeType.ALL)
	private Set<EventosSolicitacaoRevisaoAquisicao> eventossolicitacaorevisaoaquisicao;

	public Status() {}
		
	public Status(Long idstatus) {
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

	public TiposDocumentos getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TiposDocumentos tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	
	public Boolean getBoolsinalizador() {
		return boolsinalizador;
	}

	public void setBoolsinalizador(Boolean boolsinalizador) {
		this.boolsinalizador = boolsinalizador;
	}
	
	

	public Set<EventosSolicitacaoRevisaoAquisicao> getEventossolicitacaorevisaoaquisicao() {
		return eventossolicitacaorevisaoaquisicao;
	}

	public void setEventossolicitacaorevisaoaquisicao(
			Set<EventosSolicitacaoRevisaoAquisicao> eventossolicitacaorevisaoaquisicao) {
		this.eventossolicitacaorevisaoaquisicao = eventossolicitacaorevisaoaquisicao;
	}
	
	public Set<Demandas> getDemandas() {
		return demandas;
	}

	public void setDemandas(Set<Demandas> demandas) {
		this.demandas = demandas;
	}

	public Set<Agendamentos> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(Set<Agendamentos> agendamentos) {
		this.agendamentos = agendamentos;
	}

	@Override
	public int hashCode() {
		return this.getIdstatus() != null ? 
		this.getClass().hashCode() + this.getIdstatus().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Status objint = (Status)obj;
		
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
