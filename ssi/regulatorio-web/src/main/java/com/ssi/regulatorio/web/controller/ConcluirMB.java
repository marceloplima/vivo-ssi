package com.ssi.regulatorio.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.EventosRedes;
import com.ssi.kernel.redes.domain.LogsRedes;
import com.ssi.kernel.redes.domain.PareceresRedes;
import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.domain.TiposLogRedes;
import com.ssi.kernel.redes.interfaces.DemandasRedesInt;
import com.ssi.kernel.redes.interfaces.EventosRedesInt;
import com.ssi.kernel.redes.interfaces.LogsRedesInt;
import com.ssi.kernel.redes.interfaces.PareceresRedesInt;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;

@ManagedBean
@ViewScoped
public class ConcluirMB implements Serializable {

	private static final long serialVersionUID = 8719583482839210489L;

	private static final String tituloLog = "Demanda conclu�da";
	
	@EJB
	private DemandasRedesInt demandaInt;
	
	@EJB
	private EventosRedesInt eventosint;
	
	@EJB
	private ParticipantesRedesInt participanteInt;
	
	@EJB
	private PareceresRedesInt pareceresController;
	
	@EJB
	private LogsRedesInt logsInt;
	
	@EJB
	private ModulosInt modulosint;
	
	private DemandasRedes demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private EventosRedes evento;
		
	private List<PareceresRedes> pareceresRedes = new ArrayList<PareceresRedes>();
	
	private IndexMB indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
	
	private boolean mostrarConcluirSSI = false;
	private boolean mostrarConfirmaConcluirSSI = false;

	@PostConstruct
	public void init(){
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}		
		
		if(evento == null){
			evento = new EventosRedes();
		}
		
		pareceresRedes = pareceresController.recuperarPorStatus(new StatusRedes(StatusRedes.ID_DEMANDA_EM_EXECUCAO));
		
	}
	
	public void preConcluir(){
		if(validaDadosConcluirSSI()){
			mostrarTelaConfirmarSSI();	
		}
				
	}
	
	public void mostrarTelaConfirmarSSI(){
		mostrarConfirmaConcluirSSI=true;
	}
	
	public void fecharTelaConfirmarConclusaoSSI(){
		mostrarConfirmaConcluirSSI=false;
	}
	
	public void efetuarConclusao(){
		concluirSSI();
		enviarEmail();
		
	}
	
	private void concluirSSI(){

		demanda.setPessoacomquemesta(null);
		demanda.setStatusanterior(demanda.getStatus());
		
		demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_CONCLUIDA));
		
		incluirLog();
		salvaEventoConcluir();
		
		demanda = demandaInt.alterar(demanda);	
		
		demanda.setLogs(new LinkedHashSet<LogsRedes>(logsInt.recuperarLogsDemanda(demanda)));
		
		demandasmb.setDemandas(demandaInt.recuperarDemandaEspecifica(demanda.getIddemanda()));		
				
		indexmb.setMsgpanel("Conclu�do com sucesso");
		indexmb.setPanelexibesucesso(true);
		
		fecharTelaConfirmarConclusaoSSI();
		fecharConcluirSSI();
	}
	
	private void incluirLog() {
		
		LogsRedes novoLog = demandasmb.criaLog(TiposLogRedes.ID_DEMANDA,demandasmb.recuperarPessoaLogada(), tituloLog,demanda);
		
		demanda.setLogs(demandasmb.adicionaLogDemandas(demanda,novoLog));		
		
	}
	
	private void enviarEmail() {
		String msg = "";
		String assunto = "";
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
				
		pessoasEnvio.add(demanda.getAutor());
		
		msg = "Demanda conclu�da";
		assunto = "Demanda conclu�da";
		
		demandasmb.envioEMail(pessoasEnvio, assunto, msg);
						
	}

	private void salvaEventoConcluir() {
		
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(demanda);
		evento.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_CONCLUIDA));
		eventosint.registrar(evento);
						
	}	

	private boolean validaDadosConcluirSSI() {	
			
		if(evento.getParecer() == null){
			demandasmb.devolveErroParaTela("formconcluir:parecerconclusao","O parecer deve ser preenchido");
			return false;
		}
		
		if(evento.getParecer().getIdparecer().equals(PareceresRedes.ID_EXECUCAO_PARCIAL)){
			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("formconcluir:comentarioconclusao","O campo coment�rio � obrigat�rio");
				return false;
			}	
		}
				
		return true;
	}
	
	
	public void mostrarConcluirSSI(){
		mostrarConcluirSSI = true;
	}
	
	public void fecharConcluirSSI(){
		mostrarConcluirSSI = false;
	}
	
	/*Getter e Setter*/		
	
	
	
	public boolean isMostrarConfirmaConcluirSSI() {
		return mostrarConfirmaConcluirSSI;
	}

	public EventosRedes getEvento() {
		return evento;
	}

	public void setEvento(EventosRedes evento) {
		this.evento = evento;
	}

	public List<PareceresRedes> getPareceresRedes() {
		return pareceresRedes;
	}

	public void setPareceresRedes(List<PareceresRedes> pareceresRedes) {
		this.pareceresRedes = pareceresRedes;
	}

	public boolean isMostrarConcluirSSI() {
		return mostrarConcluirSSI;
	}

	public void setMostrarConcluirSSI(boolean mostrarConcluirSSI) {
		this.mostrarConcluirSSI = mostrarConcluirSSI;
	}

	public void setMostrarConfirmaConcluirSSI(boolean mostrarConfirmaConcluirSSI) {
		this.mostrarConfirmaConcluirSSI = mostrarConfirmaConcluirSSI;
	}
	
}
