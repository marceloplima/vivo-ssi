package com.ssi.regulatorio.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.redes.domain.EventosRedes;
import com.ssi.kernel.redes.interfaces.EventosRedesInt;


@ManagedBean
@ViewScoped
public class EventosMB implements java.io.Serializable{

	private static final long serialVersionUID = -4717357800384649130L;

	@EJB
	private EventosRedesInt eventosint;
	
	private EventosRedes evento;
	
	private List<EventosRedes> eventos;
	private List<EventosRedes> eventoschecked = new ArrayList<EventosRedes>();
	
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	//private boolean exibepopupdetev_00 = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		setEvento(new EventosRedes());
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setEventos(new ArrayList<EventosRedes>());
		setEventoschecked(new ArrayList<EventosRedes>());
    }
	
	public void selecionarCheckbox(EventosRedes obj){
		if(eventoschecked.contains(obj)){
			obj.setChecked(false);
			eventoschecked.remove(obj);
		}else{
			obj.setChecked(true);
			eventoschecked.add(obj);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		setOcultaacoes(true);
		if(eventoschecked.size() == 1){
			setOcultaacoes(false);
		}
	}
	
//	public void fecharPopups(){
//		setExibepopupdetev_00(false);
//	}
	
	public EventosDataModel getDataModel() {
		setOcultaacoes(true);
		acao = "0"; // reseta o combobox de a��es
		eventoschecked = new ArrayList<EventosRedes>();
		
        return new EventosDataModel(eventosint, demandasmb.getDemandas());
    }
	
//	public void abrirPopups(EventosRedes evento){
//		
//		// Primeiro seta todos os popups pra fechados (rendered=false)
//		//fecharPopups();
//		
//			
//		exibepopupdetev_00 = true;
//		evento.setEgenerico(eventosint.recuperarEventosGenericos(evento));
//		
//		setEvento(evento);
//	}

	public boolean isOcultaacoes() {
		return ocultaacoes;
	}

	public void setOcultaacoes(boolean ocultaacoes) {
		this.ocultaacoes = ocultaacoes;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public List<EventosRedes> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventosRedes> eventos) {
		this.eventos = eventos;
	}

	public List<EventosRedes> getEventoschecked() {
		return eventoschecked;
	}

	public void setEventoschecked(List<EventosRedes> eventoschecked) {
		this.eventoschecked = eventoschecked;
	}

	public EventosRedes getEvento() {
		
		return evento;
	}

	public void setEvento(EventosRedes evento) {
			this.evento = evento;
	}

	public DemandasMB getDemandasmb() {
		return demandasmb;
	}

	public void setDemandasmb(DemandasMB demandasmb) {
		this.demandasmb = demandasmb;
	}

//	public boolean isExibepopupdetev_00() {
//		return exibepopupdetev_00;
//	}
//
//	public void setExibepopupdetev_00(boolean exibepopupdetev_00) {
//		this.exibepopupdetev_00 = exibepopupdetev_00;
//	}

}
