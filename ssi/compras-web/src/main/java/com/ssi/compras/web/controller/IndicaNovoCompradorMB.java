package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.compras.common.interfaces.EmailServiceInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class IndicaNovoCompradorMB implements Serializable {

	private static final long serialVersionUID = 8125658531268558312L;
	
	private static final long idTipoTelefoneCelular = 3L;
	private static final int idIndicaNovoComprador = 14;
	
	private static final String tituloLog = "Novo comprador indicado";
				
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private EmailServiceInt emailService;
				
	private List<Pessoas> participantesCompradores = new ArrayList<Pessoas>();
		
	private Lps lp;
		
	private String email;
	private String celular;
	private String localizacao;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibeconfirmaindicacaonovocomprador=false;
	private boolean exibeindicanovocomprador=false;
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
								
		evento = new Eventos(idIndicaNovoComprador,0);
						
	}
	
	  public void recebeLp(ActionEvent event){
		  
		  	lp = (Lps)event.getComponent().getAttributes().get("lpcorrente");
		  	
		  	if(lp!=null){
				List<Lps> lpsBusca = new ArrayList<Lps>();				
				lpsBusca.add(lp);
						
				atualizaCompradores(lpsBusca);	
		  	}
		  			  			 
	  }
	
	
	
	public void atualizaCompradores(List<Lps> lpsBusca){

		participantesCompradores = new ArrayList<Pessoas>();
		
		if(lpsBusca!=null){
		
			participantesCompradores = participanteInt.recuperarCompradoresPorLp(lpsBusca);
			
		}
								
	}
	
	public void recuperaInformacoesDoComprador(ValueChangeEvent event){
				
		email="";
		localizacao="";
		celular="";
						
		Pessoas compradorSelecionado = (Pessoas) event.getNewValue();
						
		if(compradorSelecionado != null){
			
			evento.getIndicanovocomp().setNovocomprador(compradorSelecionado);
			
			Pessoas pessoaComprador = pessoasint.recuperarUnico(compradorSelecionado.getIdpessoa());		
			
			for(Emails em:pessoasint.retornarEmailsPessoa(compradorSelecionado)){
				email = em.getCnmemail();
				break;
			}
			
			celular = pessoasint.retornaTelefone(pessoasint.retornarTelefonesPessoa(compradorSelecionado),idTipoTelefoneCelular);
			localizacao = pessoaComprador.getCnmlocalizacao();
		}
				
	}
	
	public void preIndicaNovosCompradores(){
		if(validarCampos()){
			mostraConfirmacaoIndicacaoNovoComprador();
		}
	}
	
	
	public void mostraTelaIndicaNovoComprador(){
		exibeconfirmaindicacaonovocomprador=false;
		exibeindicanovocomprador=true;		
	}
	
	public void mostraConfirmacaoIndicacaoNovoComprador() {
		exibeconfirmaindicacaonovocomprador=true;
	}
	
	public void fechaMostraIndicaNovoComprador(){
		exibeindicanovocomprador=false;
	}
	
	public void fechaConfirmacaoIndicaNovoComprador(){
		exibeconfirmaindicacaonovocomprador=false;
	}
	
	public void efetuarIndicacaoNovoComprador(){		
						
		criarEvento();
		incluiLog();
		
		Pessoas compradorAnterior = demanda.getPessoacomprador();
		
		demanda.setPessoacomprador(evento.getIndicanovocomp().getNovocomprador());		
		
		demandaint.alterar(demanda);
		
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
				
		enviarEmail(compradorAnterior);
																
		fechaConfirmacaoIndicaNovoComprador();
		fechaMostraIndicaNovoComprador();

	}

	private void enviarEmail(Pessoas compradorAnterior) {
		List<Long> papeis = new ArrayList<Long>();
		papeis.add(Papeis.ID_EMISSOR);
		papeis.add(Papeis.ID_ANALISTA_AQUISICAO);
		papeis.add(Papeis.ID_GERENTE_AQUISICAO);
		papeis.add(Papeis.ID_ANALISTA_CONTRATO);
				
		String assunto = "SSI COMPRAS - Indica��o de novo comprador";
		String mensagem = "A SSI de registro "+demanda.getCnmnumero()+" teve um novo comprador indicado."
				+ "Comprador anterior: " + compradorAnterior.getCnmnome() + ""
				+ "Comprador novo: " + demanda.getPessoacomprador();		
		
		List<Pessoas> outrasPessoasEnvio = new ArrayList<Pessoas>();
		outrasPessoasEnvio.add(compradorAnterior);
				
		emailService.enviarEMail(papeis, demanda, mensagem, assunto,outrasPessoasEnvio);
	}

	private void criarEvento() {
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setStatusanterior(demanda.getStatus());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.getIndicanovocomp().setIndicanovocomp(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
	}

	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}
				
	private boolean validarCampos() {	
								
		if(evento.getIndicanovocomp().getNovocomprador().getIdpessoa()==null){
			demandasmb.devolveErroParaTela("formindicanovocomprador:novocomprador","Comprador obrigat�rio.");
			return false;
		}
												
		return true;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCelular() {
		return celular;
	}



	public void setCelular(String celular) {
		this.celular = celular;
	}



	public String getLocalizacao() {
		return localizacao;
	}



	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}



	public Demandas getDemanda() {
		return demanda;
	}



	public void setDemanda(Demandas demanda) {
		this.demanda = demanda;
	}



	public Eventos getEvento() {
		return evento;
	}



	public void setEvento(Eventos evento) {
		this.evento = evento;
	}



	public boolean isExibeconfirmaindicacaonovocomprador() {
		return exibeconfirmaindicacaonovocomprador;
	}



	public void setExibeconfirmaindicacaonovocomprador(
			boolean exibeconfirmaindicacaonovocomprador) {
		this.exibeconfirmaindicacaonovocomprador = exibeconfirmaindicacaonovocomprador;
	}



	public boolean isExibeindicanovocomprador() {
		return exibeindicanovocomprador;
	}



	public void setExibeindicanovocomprador(boolean exibeindicanovocomprador) {
		this.exibeindicanovocomprador = exibeindicanovocomprador;
	}

	public Lps getLp() {
		return lp;
	}



	public void setLp(Lps lp) {
		this.lp = lp;
	}

	public List<Pessoas> getParticipantesCompradores() {
		return participantesCompradores;
	}

	public void setParticipantesCompradores(List<Pessoas> participantesCompradores) {
		this.participantesCompradores = participantesCompradores;
	}	

	
	

}
