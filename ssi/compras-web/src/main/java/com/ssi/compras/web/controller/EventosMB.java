package com.ssi.compras.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.compras.common.interfaces.EventosInt;


@ManagedBean
@ViewScoped
public class EventosMB implements java.io.Serializable{

	private static final long serialVersionUID = -4717357800384649130L;

	@EJB
	private EventosInt eventosint;
	
	private Eventos evento;
	
	private List<Eventos> eventos;
	private List<Eventos> eventoschecked = new ArrayList<Eventos>();
	
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private boolean exibepopupdetev_9  = false;
	private boolean exibepopupdetev_10 = false;
	private boolean exibepopupdetev_11 = false;
	private boolean exibepopupdetev_12 = false;
	private boolean exibepopupdetev_13 = false;
	private boolean exibepopupdetev_14 = false;
	private boolean exibepopupdetev_15 = false;
	private boolean exibepopupdetev_16 = false;
	private boolean exibepopupdetev_25 = false;
	private boolean exibepopupdetev_00 = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		setEvento(new Eventos());
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setEventos(new ArrayList<Eventos>());
		setEventoschecked(new ArrayList<Eventos>());
    }
	
	public void selecionarCheckbox(Eventos obj){
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
	
	public void fecharPopups(){
		setExibepopupdetev_9(false);
		setExibepopupdetev_10(false);
		setExibepopupdetev_11(false);
		setExibepopupdetev_12(false);
		setExibepopupdetev_13(false);
		setExibepopupdetev_14(false);
		setExibepopupdetev_15(false);
		setExibepopupdetev_16(false);
		setExibepopupdetev_25(false);
		setExibepopupdetev_00(false);
	}
	
	public EventosDataModel getDataModel() {
		setOcultaacoes(true);
		acao = "0"; // reseta o combobox de a��es
		eventoschecked = new ArrayList<Eventos>();
		
        return new EventosDataModel(eventosint, demandasmb.getDemandas());
    }
	
	public void abrirPopups(Eventos evento){
		
		// Primeiro seta todos os popups pra fechados (rendered=false)
		fecharPopups();
		
		switch(evento.getStatusanterior().getIdstatus().intValue()){
			case 9:
				exibepopupdetev_9 = true;
				evento.setArc(eventosint.recuperarEventosAberturasRequisicoesCompras(evento));
			break;
			
			case 10:
				exibepopupdetev_10 = true;
				evento.setAgc(eventosint.recuperarEventosAnaliseGerenteCompra(evento));
			break;
			
			case 11:
				exibepopupdetev_11 = true;
				evento.setAm(eventosint.recuperarEventosAnaliseMinuta(evento));
			break;
			
			case 12:
				exibepopupdetev_12 = true;
				evento.setEm(eventosint.recuperarEventosEnvioAoMercado(evento));
			break;
			
			case 13:
				exibepopupdetev_13 = true;
				evento.setAcionareqcomp(eventosint.recuperarEventosAcionaRequisicoesCompras(evento));
			break;
			
			case 14:
				exibepopupdetev_14 = true;
				evento.setIndicanovocomp(eventosint.recuperarEventosIndicaNovoCompradores(evento));
			break;
			
			case 15:
				exibepopupdetev_15 = true;
				evento.setEleilao(eventosint.recuperarEventosLeiloes(evento));
			break;
			
			case 16:
				exibepopupdetev_16 = true;
				evento.setEleilao(eventosint.recuperarEventosLeiloes(evento));
			break;
			
			case 25:
				exibepopupdetev_25 = true;
				evento.setEvaltec(eventosint.recuperarEventosValidacoesTecnicas(evento));
//				System.out.println(">>>aqui<<<<");
//				System.out.println(">>>"+evento.getEvaltec());
			break;
			
			default:
				exibepopupdetev_00 = true;
				evento.setEgenerico(eventosint.recuperarEventosGenericos(evento));
		}
		
		setEvento(evento);
	}

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

	public List<Eventos> getEventos() {
		return eventos;
	}

	public void setEventos(List<Eventos> eventos) {
		this.eventos = eventos;
	}

	public List<Eventos> getEventoschecked() {
		return eventoschecked;
	}

	public void setEventoschecked(List<Eventos> eventoschecked) {
		this.eventoschecked = eventoschecked;
	}

	public Eventos getEvento() {
		
		return evento;
	}

	public void setEvento(Eventos evento) {
			this.evento = evento;
	}

	public boolean isExibepopupdetev_9() {
		return exibepopupdetev_9;
	}

	public void setExibepopupdetev_9(boolean exibepopupdetev_9) {
		this.exibepopupdetev_9 = exibepopupdetev_9;
	}

	public boolean isExibepopupdetev_10() {
		return exibepopupdetev_10;
	}

	public void setExibepopupdetev_10(boolean exibepopupdetev_10) {
		this.exibepopupdetev_10 = exibepopupdetev_10;
	}

	public boolean isExibepopupdetev_11() {
		return exibepopupdetev_11;
	}

	public void setExibepopupdetev_11(boolean exibepopupdetev_11) {
		this.exibepopupdetev_11 = exibepopupdetev_11;
	}

	public boolean isExibepopupdetev_12() {
		return exibepopupdetev_12;
	}

	public void setExibepopupdetev_12(boolean exibepopupdetev_12) {
		this.exibepopupdetev_12 = exibepopupdetev_12;
	}

	public boolean isExibepopupdetev_13() {
		return exibepopupdetev_13;
	}

	public void setExibepopupdetev_13(boolean exibepopupdetev_13) {
		this.exibepopupdetev_13 = exibepopupdetev_13;
	}

	public boolean isExibepopupdetev_14() {
		return exibepopupdetev_14;
	}

	public void setExibepopupdetev_14(boolean exibepopupdetev_14) {
		this.exibepopupdetev_14 = exibepopupdetev_14;
	}

	public boolean isExibepopupdetev_15() {
		return exibepopupdetev_15;
	}

	public void setExibepopupdetev_15(boolean exibepopupdetev_15) {
		this.exibepopupdetev_15 = exibepopupdetev_15;
	}

	public boolean isExibepopupdetev_16() {
		return exibepopupdetev_16;
	}

	public void setExibepopupdetev_16(boolean exibepopupdetev_16) {
		this.exibepopupdetev_16 = exibepopupdetev_16;
	}

	public boolean isExibepopupdetev_25() {
		return exibepopupdetev_25;
	}

	public void setExibepopupdetev_25(boolean exibepopupdetev_25) {
		this.exibepopupdetev_25 = exibepopupdetev_25;
	}

	public DemandasMB getDemandasmb() {
		return demandasmb;
	}

	public void setDemandasmb(DemandasMB demandasmb) {
		this.demandasmb = demandasmb;
	}

	public boolean isExibepopupdetev_00() {
		return exibepopupdetev_00;
	}

	public void setExibepopupdetev_00(boolean exibepopupdetev_00) {
		this.exibepopupdetev_00 = exibepopupdetev_00;
	}

}
