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

@ManagedBean
@ViewScoped
public class InformacoesComplementaresMB implements Serializable {

	private static final long serialVersionUID = -950583911389243641L;

	@EJB
	private PareceresRedesInt pareceresController;
	
	@EJB
	private DemandasRedesInt demandaInt;
	
	@EJB
	private EventosRedesInt eventosint;	
		
	@EJB
	private LogsRedesInt logsInt;
				
	private IndexMB indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
	
	private DemandasRedes demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private EventosRedes evento;
	private List<PareceresRedes> pareceresRedes = new ArrayList<PareceresRedes>();
	
	private boolean mostraTelaInformacoesComplementares;
	private boolean mostraTelaConfirmaInformacoesComplementares;

	@PostConstruct
	public void init(){
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}		
		
		if(evento == null){
			evento = new EventosRedes();
		}
		
		pareceresRedes = pareceresController.recuperarPorStatus(new StatusRedes(StatusRedes.ID_DEMANDA_AGUARDANDO_INFORMACOES));		
		
	}
		
	public void preInformacaoComplementar(){
		if(validarCamposObrigatorios()){
			mostraTelaConfirmacaoInfoComplementares();
		}		
	}
	
	public void efetuarInformacaoComplementar(){
		salvarDemanda();
		enviarEmail();		
	}
	
	private void enviarEmail() {
		String msg = "";
		String assunto = "";
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
				
		pessoasEnvio.add(demanda.getAutor());
		
		switch (evento.getParecer().getIdparecer().intValue())
		
		{
		
		case (int) PareceresRedes.ID_IMPROCEDENTE:
			msg = "Improcedente";
			assunto = "Improcedente: " + evento.getCnmcomentario();			
			break;
		
		default:
			msg = "Demanda em execu��o";
			assunto = "Demanda em execu��o";			
			break;
		}
		
		demandasmb.envioEMail(pessoasEnvio, assunto, msg);
						
	}

	private void salvaEventoInformacoesComplementares() {
		
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(demanda);
		evento.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_AGUARDANDO_INFORMACOES));
		eventosint.registrar(evento);
						
	}	
	
	private void salvarDemanda() {

		demanda.setPessoacomquemesta(demanda.getResponsaveltecnico());
		demanda.setStatusanterior(demanda.getStatus());
		
		switch (evento.getParecer().getIdparecer().intValue())
		
			{
			
			case (int) PareceresRedes.ID_IMPROCEDENTE:
				demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_CANCELADA));
				break;
			
			default:
				demanda.setPessoacomquemesta(demanda.getResponsaveltecnico());
				demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_ANALISE_PELA_EXECUTORA));
				break;
			}
		
		
		incluirLog();
		salvaEventoInformacoesComplementares();
		demanda = demandaInt.alterar(demanda);	
		
		demanda.setLogs(new LinkedHashSet<LogsRedes>(logsInt.recuperarLogsDemanda(demanda)));
		
		demandasmb.setDemandas(demandaInt.recuperarDemandaEspecifica(demanda.getIddemanda()));		
				
		indexmb.setMsgpanel("An�lise efetuada com sucesso");
		indexmb.setPanelexibesucesso(true);
		
		fechaTelaInformacoesComplementares();
		fechaTelaConfirmacaoInformacoesComplementares();
		
	}
		

	private void incluirLog() {
		
		LogsRedes novoLog = demandasmb.criaLog(TiposLogRedes.ID_DEMANDA,demandasmb.recuperarPessoaLogada(), recuperarTextoParaLog(),demanda);
		
		demanda.setLogs(demandasmb.adicionaLogDemandas(demanda,novoLog));
		
	}

	private boolean validarCamposObrigatorios(){
		
		if(evento.getParecer() == null){
			demandasmb.devolveErroParaTela("forminformacoescomplementares:parecerconclusao","O parecer deve ser preenchido");
			return false;
		}
		
		//TODO Simplificar o switch. Est� dessa forma pois n�o sei quando coment�rio � obrigat�rio
		
		switch (evento.getParecer().getIdparecer().intValue())
		
		{
		case (int) PareceresRedes.ID_PROCEDE:
			
			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("forminformacoescomplementares:comentarioinformacaocomplementar","O campo coment�rio � obrigat�rio");
				return false;
			}
										
			break;
		
		case (int) PareceresRedes.ID_IMPROCEDENTE:
			
			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("forminformacoescomplementares:comentarioinformacaocomplementar","O campo coment�rio � obrigat�rio");
				return false;
			}			
			
			break;
				
		}		
		
		return true;
	}
	
	private String recuperarTextoParaLog(){
				
		switch (evento.getParecer().getIdparecer().intValue())
		
		{		
		case (int) PareceresRedes.ID_IMPROCEDENTE:
			return "Solicita��o cancelada";
		
		case (int) PareceresRedes.ID_PROCEDE:
			return "Solicita��o procede";
			
		}
		
		return "";
		
	}

	/*Getters e Setters*/	
	
	public void mostraTelaInfoComplementares(){
		mostraTelaInformacoesComplementares = true;
	}
	
	public void mostraTelaConfirmacaoInfoComplementares(){
		mostraTelaConfirmaInformacoesComplementares = true;
	}
	
	public void fechaTelaInformacoesComplementares(){
		mostraTelaInformacoesComplementares = false;
	}
	
	public void fechaTelaConfirmacaoInformacoesComplementares(){
		mostraTelaConfirmaInformacoesComplementares = false;
	}
			
	public DemandasRedes getDemanda() {
		return demanda;
	}

	public boolean isMostraTelaInformacoesComplementares() {
		return mostraTelaInformacoesComplementares;
	}


	public void setMostraTelaInformacoesComplementares(
			boolean mostraTelaInformacoesComplementares) {
		this.mostraTelaInformacoesComplementares = mostraTelaInformacoesComplementares;
	}


	public boolean isMostraTelaConfirmaInformacoesComplementares() {
		return mostraTelaConfirmaInformacoesComplementares;
	}


	public void setMostraTelaConfirmaInformacoesComplementares(
			boolean mostraTelaConfirmaInformacoesComplementares) {
		this.mostraTelaConfirmaInformacoesComplementares = mostraTelaConfirmaInformacoesComplementares;
	}


	public void setDemanda(DemandasRedes demanda) {
		this.demanda = demanda;
	}

	public List<PareceresRedes> getPareceresRedes() {
		return pareceresRedes;
	}

	public void setPareceresRedes(List<PareceresRedes> pareceresRedes) {
		this.pareceresRedes = pareceresRedes;
	}

	public EventosRedes getEvento() {
		return evento;
	}

	public void setEvento(EventosRedes evento) {
		this.evento = evento;
	}

	
}
