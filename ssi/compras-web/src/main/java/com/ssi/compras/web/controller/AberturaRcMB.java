package com.ssi.compras.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Orcamentos;
import com.ssi.kernel.compras.domain.Status;

@ManagedBean
@ViewScoped
public class AberturaRcMB implements Serializable {

	private static final long serialVersionUID = -8730007331094099712L;
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private boolean exibepopup;
	private boolean exibeconfirmacao;
	
	private Eventos evento;
	private Orcamentos orcamentoSelecionado;

	@PostConstruct
	public void init(){
		exibepopup = false;
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}		
		
		if(evento == null){
									
			//evento = new Eventos(demanda.getStatus().getIdstatus().intValue(),2);
			evento = new Eventos(9,2);
		}
		
	}
	
	public void habilitaModal(Orcamentos orcamento){
		
		orcamentoSelecionado = orcamento;
				
		if(orcamento.getCnmrc()!=null){
			evento.getArc().setCnmrc(orcamento.getCnmrc());;	
		}		
		exibepopup = true;
	}
	
	public void desabilitaModal(){
		exibepopup = false;
	}
	
	public void desabilitaConfirmacao(){
		exibepopup = false;
		exibeconfirmacao = false;
	}
	
	public void preGravar(){
		if(validaCampo()){
			exibeconfirmacao = true;
			exibepopup = false;
			orcamentoSelecionado.setCnmrc(evento.getArc().getCnmrc());
		}
	}
	
	private boolean validaCampo() {

		if(StringUtils.isBlank(evento.getArc().getCnmrc())){
			
			devolveErroParaTela("demandaform:rc","Campo RC � de preenchimento obrigat�rio.");
			return false;
			
		}
		
		return true;
	}
	
	private void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
	}

	public void gravar(){
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		
		Status abrerc = new Status();
		abrerc.setIdstatus(9L);
		evento.setStatus(abrerc); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.setStatusanterior(abrerc); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.getArc().setEventoarc(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
				
		desabilitaModal();
		desabilitaConfirmacao();
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

	public Orcamentos getOrcamentoSelecionado() {
		return orcamentoSelecionado;
	}

	public void setOrcamentoSelecionado(Orcamentos orcamentoSelecionado) {
		this.orcamentoSelecionado = orcamentoSelecionado;
	}
	
	

}
