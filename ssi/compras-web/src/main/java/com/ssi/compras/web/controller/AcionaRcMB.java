package com.ssi.compras.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Status;

@ManagedBean
@ViewScoped
public class AcionaRcMB implements Serializable {

	
	private static final long serialVersionUID = 5690549715044686008L;
	
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
		exibepopup = false;
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		if(evento==null){
			evento = new Eventos(25,2);
		}
		
	}
	
	public void habilitaModal(){
		exibepopup = true;
	}
	
	public void desabilitaModal(){
		exibepopup = false;
	}
	
	public void desabilitaConfirmacao(){
		exibepopup = true;
		exibeconfirmacao = false;
	}
	
	public void preGravar(){
		exibeconfirmacao = true;
	}
	
	public void gravar(){
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		
		Status acionarc = new Status();
		acionarc.setIdstatus(13L);
		evento.setStatus(acionarc); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.setStatusanterior(acionarc); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.getAcionareqcomp().setAcreqcomp(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		
		// Cria o Log
		String descricaoLog;
		descricaoLog = "Aciona Cria��o de RC";
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
		
		demandaint.alterar(demanda);
		
		desabilitaModal();
		desabilitaConfirmacao();
		exibepopup = false;
		evento = new Eventos(demanda.getStatus().getIdstatus().intValue(),2);
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

	public Eventos getEvento() {
		return evento;
	}

	public void setEvento(Eventos evento) {
		this.evento = evento;
	}

}
