package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.common.interfaces.CronogramaInt;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.FeriadosServiceInt;
import com.ssi.compras.common.interfaces.LogsInt;
import com.ssi.kernel.compras.domain.Cronogramas;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.model.Feriados;
import com.ssi.kernel.model.Ufs;
import com.ssi.kernel.redes.interfaces.utils.DataUtilsInt;

@ManagedBean
@ViewScoped
public class CronogramaMB implements Serializable {

	private static final long serialVersionUID = 8879094841345180603L;
	
	@EJB
	private CronogramaInt cronogramaint;
	
	@EJB
	private FeriadosServiceInt feriadosService;

	@EJB
	private DataUtilsInt dataUtilsInt;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private LogsInt logsInt;
	
	@EJB
	private DemandaInt demandaint;
	
	private static final String tituloLog = "Cronograma Alterado";
	
	private Cronogramas cronogramaSelecionado;
	private boolean mostraTelaAlteraCronograma = false;
	private boolean mostraTelaConfirmacaoAlteraCronograma = false;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;

	public CronogramaMB() {}
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
								
		evento = new Eventos(52,0);
						
	}
	
	public void alteraCronograma(Cronogramas cronograma){
		cronogramaSelecionado = cronograma;				
		mostrarTela();
	}
	
	public void preSalvar(){
		if(validaDataSelecionada()){
			demandasmb.devolveErroParaTela("formalteracronograma:datadeprevisaoatual","A data n�o pode ser nem fim de semana nem feriado.");
		}else{			
			mostraTelaConfirmacao();					
		}
	}
	
	private boolean validaDataSelecionada(){
		
		List<Ufs> ufs = new ArrayList<Ufs>();
		ufs.add(new Ufs(Feriados.ID_CIDADE_FERIADO_LOCAL_PADRAO));
		
		List<Feriados> feriados = feriadosService.retornaFeriadosNacionaisELocais(7, ufs);
		
		return feriadosService.isDataFeriado(feriados, cronogramaSelecionado.getDataprevisaoatual()) || 
				dataUtilsInt.isFimDeSemana(cronogramaSelecionado.getDataprevisaoatual());
				
	}
	
	private void criarEvento() {
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setStatusanterior(demanda.getStatus());
		evento.setDemanda(demanda);
		evento.setStatus(demanda.getStatus());
		evento.getEventosAlteraCronograma().setEventoAlteraCronograma(evento); // Faz o relacionamento inverso pro cascade funcionar
		evento.getEventosAlteraCronograma().setDataanterior(cronogramaSelecionado.getDataprevisaoatual());
		eventosint.registrar(evento);
	}
	
	public void salvar(){
				
		incluiLog();
		
		criarEvento();		
		
		cronogramaint.atualizar(cronogramaSelecionado);
				
		demandaint.alterar(demanda);
		
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
		
		demanda.setLogs(new HashSet<Logs>(logsInt.recuperarLogsDemanda(demanda)));
		demandasmb.setLogsDemanda(new ArrayList<Logs>(demanda.getLogs()));				
		
		fecharTela();
		fechaTelaConfirmacao();
	}
	
	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}
	
	public void mostrarTela(){
		mostraTelaAlteraCronograma = true;
	}
	
	public void fecharTela(){
		mostraTelaAlteraCronograma = false;
	}
	
	public void mostraTelaConfirmacao(){
		mostraTelaConfirmacaoAlteraCronograma = true;
	}
	
	public void fechaTelaConfirmacao(){
		mostraTelaConfirmacaoAlteraCronograma = false;
	}
	
			
	//Getters e Setters

	public Cronogramas getCronogramaSelecionado() {
		return cronogramaSelecionado;
	}

	public void setCronogramaSelecionado(Cronogramas cronogramaSelecionado) {
		this.cronogramaSelecionado = cronogramaSelecionado;
	}

	public boolean isMostraTelaAlteraCronograma() {
		return mostraTelaAlteraCronograma;
	}

	public void setMostraTelaAlteraCronograma(boolean mostraTelaAlteraCronograma) {
		this.mostraTelaAlteraCronograma = mostraTelaAlteraCronograma;
	}

	public boolean isMostraTelaConfirmacaoAlteraCronograma() {
		return mostraTelaConfirmacaoAlteraCronograma;
	}

	public void setMostraTelaConfirmacaoAlteraCronograma(
			boolean mostraTelaConfirmacaoAlteraCronograma) {
		this.mostraTelaConfirmacaoAlteraCronograma = mostraTelaConfirmacaoAlteraCronograma;
	}	
	
	
	
}
