package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.PareceresInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Pareceres;

@ManagedBean
@ViewScoped
public class AnaliseMinutaMB implements Serializable {

	private static final long serialVersionUID = -7722048673575988793L;
	
	@EJB
	private PareceresInt pareceresint;
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private boolean exibepopup;
	private boolean exibeconfirmacao;
	
	private Eventos evento;
	
	@PostConstruct
	public void init(){
		setExibepopup(false);
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		if(evento == null){
			evento = new Eventos();
		}
		
	}
	
	public List<Pareceres> recuperarPareceres(){
		return pareceresint.recuperarPareceresPorStatus(demanda.getStatus());
	}
	
	public void habilitaModal(){
		setExibepopup(true);
	}
	
	public void desabilitaModal(){
		setExibepopup(false);
	}
	
	public void desabilitaConfirmacao(){
		setExibepopup(true);
		setExibeconfirmacao(false);
	}
	
	public void preGravar(){
		setExibeconfirmacao(true);
	}
	
	public void gravar(){
		
		demanda.setBoolanaliseminuta(true);
		
		evento.setStatus(demanda.getStatus());
		evento.setStatusanterior(demanda.getStatus());
		
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.getEgenerico().setEventogenerico(evento);
		eventosint.registrar(getEvento());
		
		// Cria o Log
		String descricaoLog;
		descricaoLog = "An�lise da Minuta";
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
		
		demandaint.alterar(demanda);
		
		desabilitaModal();
		desabilitaConfirmacao();
		exibepopup = false;
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
		evento = new Eventos();
	}

	public boolean isExibepopup() {
		return exibepopup;
	}

	public void setExibepopup(boolean exibepopup) {
		this.exibepopup = exibepopup;
	}

	public boolean isExibeconfirmacao() {
		return exibeconfirmacao;
	}

	public void setExibeconfirmacao(boolean exibeconfirmacao) {
		this.exibeconfirmacao = exibeconfirmacao;
	}

	public Demandas getDemanda() {
		return demanda;
	}

	public void setDemanda(Demandas demanda) {
		this.demanda = demanda;
	}

	public DemandasMB getDemandasmb() {
		return demandasmb;
	}

	public void setDemandasmb(DemandasMB demandasmb) {
		this.demandasmb = demandasmb;
	}

	public Eventos getEvento() {
		return evento;
	}

	public void setEvento(Eventos evento) {
		this.evento = evento;
	}

}
