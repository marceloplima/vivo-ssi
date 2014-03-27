package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Pareceres;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.PareceresInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class ValidacaoSegundoNivelMB implements Serializable {

	private static final long serialVersionUID = 8125658531268558312L;		
	
	private static final String tituloLog = "Valida��o de Segundo N�vel";
			
	@EJB
	private PareceresInt pareceresint;
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ModulosInt modulosint;
		
	private List<Pareceres> pareceresValidacaoSegundoNivel = new ArrayList<Pareceres>();
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibeconfirmavalidacaosegundonivel;
	private boolean exibevalidacaosegundonivel;
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		pareceresValidacaoSegundoNivel = pareceresint.recuperarPareceresPorStatus(new Status(Status.ID_DEMANDA_VALIDACAO_SEGUNDO_NIVEL));
										
		evento = new Eventos();						
				
	}

			
	public void setaParecer(ValueChangeEvent event){
		
		Pareceres parecer = (Pareceres) event.getNewValue();
		
		evento.getEgenerico().setParecer(parecer);
		
	}
					
	public void preValidacaoSegundoNivel(){
		
		mostraConfirmacaoValidacaoSegundoNivel();
		
	}
	
	public void mostraTelaValidacaoSegundoNivel(){
		exibevalidacaosegundonivel = true;
	}	
	
	public void mostraConfirmacaoValidacaoSegundoNivel(){
		exibeconfirmavalidacaosegundonivel = true;
	}
	
	public void fechaTelaConfirmacaoValidacaoLaudoTecnico(){
		exibeconfirmavalidacaosegundonivel = false;
	}
	
	public void efetuarValidacaoSegundoNivel(){		
						
		registrarEvento();
		
		demanda.setStatusanterior(demanda.getStatus());
		
		setaStatusDemanda(Status.ID_DEMANDA_VALIDACAO_LAUDO_TECNICO);
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
				
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}

		if(demanda.getLp()!=null){
			List<Lps> lps = new ArrayList<Lps>();
			
			lps.add(demanda.getLp());

			pessoasEnvio.addAll(participanteInt.recuperarGerentesPorLp(lps));	
		}
		
		
		incluiLog();		
		
		demandasmb.setDemandas(demandaint.alterar(demanda));
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
						
		String assunto = "SSI COMPRAS - Valida��o de Segundo N�vel";
		String mensagem = "A Valida��o de Segundo N�vel foi efetuada para a SSI de registro "+demanda.getCnmnumero()+".";
		
		demandasmb.envioEMail(pessoasEnvio, assunto, mensagem);
											
		fecharTelaValidacaoLaudoTecnico();
		fechaTelaConfirmacaoValidacaoLaudoTecnico();

	}


	private void registrarEvento() {
		evento.setStatusanterior(demanda.getStatus());
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.getEgenerico().setEventogenerico(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
	}

	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}
					
	public void fecharTelaValidacaoLaudoTecnico(){
		exibevalidacaosegundonivel = false;
	}
	
	
	private void setaStatusDemanda(long idstatus) {		
		demanda.setStatus(new Status(idstatus));		
	}

	public boolean isExibeconfirmavalidacaosegundonivel() {
		return exibeconfirmavalidacaosegundonivel;
	}

	public void setExibeconfirmavalidacaosegundonivel(
			boolean exibeconfirmavalidacaosegundonivel) {
		this.exibeconfirmavalidacaosegundonivel = exibeconfirmavalidacaosegundonivel;
	}

	public boolean isExibevalidacaosegundonivel() {
		return exibevalidacaosegundonivel;
	}

	public void setExibevalidacaosegundonivel(boolean exibevalidacaosegundonivel) {
		this.exibevalidacaosegundonivel = exibevalidacaosegundonivel;
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

	public List<Pareceres> getPareceresValidacaoSegundoNivel() {
		return pareceresValidacaoSegundoNivel;
	}

	public void setPareceresValidacaoSegundoNivel(
			List<Pareceres> pareceresValidacaoSegundoNivel) {
		this.pareceresValidacaoSegundoNivel = pareceresValidacaoSegundoNivel;
	}
	
}
