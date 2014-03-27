package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.LeiloesInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Leiloes;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Orcamentos;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.OrcamentosInt;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class ConcluirMB implements Serializable {

	private static final long serialVersionUID = 226454242613428750L;
	
	private static final String tituloLog = "Demanda conclu�da";
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private LeiloesInt leilaoint;
	
	@EJB
	private OrcamentosInt orcamentosInt;	
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private ModulosInt modulosint;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	private IndexMB indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
	
	private List<String> msgserro = new ArrayList<String>();
	
	private boolean mostrarConfirmaConcluirSSI = false;

	@PostConstruct
	public void init(){
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}		
		
		if(evento == null){
			evento = new Eventos();
		}
		
	}
	
	public void preConcluir(){
		if(validaDadosConcluirSSI()){
			mostrarTelaConcluirSSI();	
		}
				
	}
	
	public void mostrarTelaConcluirSSI(){
		mostrarConfirmaConcluirSSI=true;
	}
	
	public void fecharTelaConcluirSSI(){
		mostrarConfirmaConcluirSSI=false;
	}
	
	public void concluirSSI(){

		criaEventoConcluir();
		
		incluiLog();
		
		demanda.setStatus(new Status(Status.ID_DEMANDA_CONCLUIDA));
		demanda = demandaint.alterar(demanda);
		
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
		
		enviaEMailConcluir();
		
		fecharTelaConcluirSSI();
	}
	
	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}
	
	private void enviaEMailConcluir() {
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		
		//pessoa logada
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
		
		//Solicitante
		pessoasEnvio.add(demanda.getAutor());				
				
		//analista de aquisi��o
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}

		//respons�vel t�cnico
		if(demanda.getResponsaveltecnico()!=null){
			pessoasEnvio.add(demanda.getResponsaveltecnico());	
		}		
		
		//analista de contrato
		if(demanda.getAnalistaDeContratoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeContratoResponsavel());	
		}		
		
		//Gerente de contratos		
		pessoasEnvio.addAll(participanteInt.recuperarGerentesContratos());
		
		//Gerente de aquisi��o
		pessoasEnvio.addAll(participanteInt.recuperarGerentesAquisicoes());		
				
		String assunto = "SSI COMPRAS - Conclu�da";
		String strmensagem = "A SSI de registro "+ demanda.getCnmnumero() + " foi conclu�da por " + demandasmb.recuperarPessoaLogada().getCnmnome();
		
		demandasmb.envioEMail(pessoasEnvio,assunto,strmensagem);				
		
	}

	private void criaEventoConcluir() {
		
		Eventos evento = new Eventos();
		
		evento.setStatus(new Status(Status.ID_DEMANDA_CONCLUIDA));
		evento.setStatusanterior(demanda.getStatus());
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(demanda);
		evento.getEgenerico().setEventogenerico(evento);
		eventosint.registrar(evento);
						
	}	

	private boolean validaDadosConcluirSSI() {	
	
		if(!verificarTodosLeiloesFinalizados()){
			msgserro.add("Existen leil�es n�o finalizados.");
		}
		
		if(!verificarTodasRCsPreechidas()){
			msgserro.add("Existen RCs n�o preenchidas.");
		}		

		if (msgserro.size() != 0) {
			indexmb.setPanelexibeerro(true);
			indexmb.setRenderizalistadeerros(true);
			indexmb.setMsgspanel(msgserro);
			return false;
		}
		
		return true;
	}

	private boolean verificarTodasRCsPreechidas() {
		for(Orcamentos orcamento:orcamentosInt.recuperarOrcamentosDaDemanda(demanda, "1"))
		{
			if(orcamento.getUfs() != null){
				if(StringUtils.isBlank(orcamento.getCnmrc())){
					return false;	
				}
			}
		}
		return true;
	}

	private boolean verificarTodosLeiloesFinalizados() {
		for(Leiloes leilao:leilaoint.recuperar(demanda))
		{
			if(!leilao.getStatus().getIdstatus().equals(Status.ID_LEILAO_FINALIZADO)){
				return false;
			}
		}
		return true;
	}	

	public boolean isMostrarConfirmaConcluirSSI() {
		return mostrarConfirmaConcluirSSI;
	}

	public void setMostrarConfirmaConcluirSSI(boolean mostrarConfirmaConcluirSSI) {
		this.mostrarConfirmaConcluirSSI = mostrarConfirmaConcluirSSI;
	}
	
}
