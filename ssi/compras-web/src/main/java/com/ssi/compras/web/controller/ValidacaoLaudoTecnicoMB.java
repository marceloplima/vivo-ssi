package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

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
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.Mensageria;

@ManagedBean
@ViewScoped
public class ValidacaoLaudoTecnicoMB implements Serializable {

	private static final long serialVersionUID = 8125658531268558312L;
	
	private static final int idParecerProcede = 1;
	private static final int idParecerAcionaDiretor = 9;
	private static final int idParecerDevolver = 5;
	
	private static final int idParecerImprocedente = 3;
	
	private static final long idStatusAnaliseLaudoTecnico = 32L;
	
	private static final long idStatusAgendamentoMesaCompra = 36L;
	private static final long idStatusValidacaoDeSegundoNivel = 37L;
	private static final long idStatusPropostaEmAnalise = 8L;	
	
	private static final String tituloLog = "Valida��o de Laudo T�cnico";
			
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
		
	private List<Pareceres> pareceresValidacaoLaudoTecnico = new ArrayList<Pareceres>();
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibeconfirmavalidacaolaudotecnico;
	private boolean exibevalidacaolaudotecnico;
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		retiraParecerNaoValidoParaValidarLaudo();
		
		ordenaPareceres();
								
		evento = new Eventos();		
				
				
	}

	private void ordenaPareceres() {
		Collections.sort(pareceresValidacaoLaudoTecnico, new Comparator<Pareceres>() {
			public int compare(Pareceres c1, Pareceres c2) {
				return c1.getCnmparecer()
						.compareTo(c2.getCnmparecer());
			}
		});
	}

	private void retiraParecerNaoValidoParaValidarLaudo() {
		for(Pareceres parecer:pareceresint.recuperarPareceresPorStatus(new Status(idStatusAnaliseLaudoTecnico))){
			if(!parecer.getIdparecer().equals(idParecerImprocedente)){
				pareceresValidacaoLaudoTecnico.add(parecer);	
			}			
		}
	}
		
	public void preValidacaoLaudoTecnico(){		
		
		if(validarCampos()){
			mostraConfirmacaoValidacaoLaudoTecnico();
		}
	}
	
	public void mostraTelaValidacaoLaudoTecnico(){
		exibevalidacaolaudotecnico = true;
	}	
	
	public void mostraConfirmacaoValidacaoLaudoTecnico(){
		exibeconfirmavalidacaolaudotecnico = true;
	}
	
	public void fechaTelaConfirmacaoValidacaoLaudoTecnico(){
		exibeconfirmavalidacaolaudotecnico = false;
	}
	
	private void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
	}
	
	public void efetuarLaudoTecnico(){		
						
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.getEgenerico().setEventogenerico(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		
		demanda.setStatusanterior(demanda.getStatus());
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
		
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}

		if(demanda.getLp()!=null){
			List<Lps> lps = new ArrayList<Lps>();
			
			lps.add(demanda.getLp());

			//pessoasEnvio.addAll(setarGerentesParaEnvio(lps));	
		}
		
		switch(evento.getEgenerico().getParecer().getIdparecer().intValue()){
		
		case idParecerProcede:
					
			setaStatusDemanda(idStatusAgendamentoMesaCompra);

			if(demanda.getResponsaveltecnico()!=null){
				pessoasEnvio.add(demanda.getResponsaveltecnico());	
			}

			if(demanda.getAutor()!=null){
				pessoasEnvio.add(demanda.getAutor());	
			}				
																			
			break;
			
		case idParecerAcionaDiretor:

			setaStatusDemanda(idStatusValidacaoDeSegundoNivel);

			break;
					
		case idParecerDevolver:

			setaStatusDemanda(idStatusPropostaEmAnalise);
			
			if(demanda.getAutor()!=null){
				pessoasEnvio.add(demanda.getAutor());	
			}				
			
			break;
			
		}					
		
		incluiLog();
		
		demandaint.alterar(demanda);
		
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
					
		Map<String,String>listaemails = new HashMap<String,String>();
		
		List<Emails> emails;
		
		for(Pessoas pessoa:pessoasEnvio){
			emails = new ArrayList<Emails>();
			emails = pessoasint.retornarEmailsPessoa(pessoa);
			for(Emails email:emails){
				listaemails.put(pessoa.getCnmnome(),email.getCnmemail());	 
			}
			
		}
					
		//Envio do email
		Modulos modulo = modulosint.recuperarUnico(336L);
		
		GruposModulos gm = new GruposModulos();
		gm.setIdgrupomodulo(8L);
		gm.setModulo(modulo);
		
		Mensageria mensageria = new Mensageria();
		String assunto = "SSI COMPRAS - Valida��o de Laudo T�cnico";
		String strmensagem = "Foi efetuada a Valida��o de Laudo T�cnico para a SSI de registro "+demanda.getCnmnumero();
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;			
												
		fecharTelaValidacaoLaudoTecnico();
		fechaTelaConfirmacaoValidacaoLaudoTecnico();

	}

	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}
	
	public void fecharTelaValidacaoLaudoTecnico(){
		exibevalidacaolaudotecnico = false;
	}
	
	
	private void setaStatusDemanda(long idstatus) {		
		demanda.setStatus(new Status(idstatus));		
	}

	private boolean validarCampos() {	
		
		if(StringUtils.isBlank(evento.getCnmcomentario())){
			devolveErroParaTela("formvalidacaolaudotecnico:comentario","Coment�rio � obrigat�rio.");
			return false;
		}			
		
		return true;
	}

	
	
	public boolean isExibeconfirmavalidacaolaudotecnico() {
		return exibeconfirmavalidacaolaudotecnico;
	}

	public void setExibeconfirmavalidacaolaudotecnico(
			boolean exibeconfirmavalidacaolaudotecnico) {
		this.exibeconfirmavalidacaolaudotecnico = exibeconfirmavalidacaolaudotecnico;
	}

	public boolean isExibevalidacaolaudotecnico() {
		return exibevalidacaolaudotecnico;
	}

	public void setExibevalidacaolaudotecnico(boolean exibevalidacaolaudotecnico) {
		this.exibevalidacaolaudotecnico = exibevalidacaolaudotecnico;
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

	public List<Pareceres> getPareceresValidacaoLaudoTecnico() {
		return pareceresValidacaoLaudoTecnico;
	}

	public void setPareceresValidacaoLaudoTecnico(
			List<Pareceres> pareceresValidacaoLaudoTecnico) {
		this.pareceresValidacaoLaudoTecnico = pareceresValidacaoLaudoTecnico;
	}

	
	
}
