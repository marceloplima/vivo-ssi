package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.ssi.kernel.compras.domain.Status;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
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
public class SolicitaRevisaoAquisicaoMB implements Serializable {

	private static final long serialVersionUID = 8125658531268558312L;
	
	private static final long idRevisaoPeloAnalistaAquisicao = 27L;
	private static final int idEventosSolicitacaoRevisaoAquisicao = 50;
		
	private static final String tituloLog = "Solicita��o de revis�o � aquisi��o feita.";
				
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
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private String comentario;
	
	private boolean exibeconfirmasolicitarevisaoaquisicao=false;
	private boolean exibesolicitarevisaoaquisicao=false;
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
								
		evento = new Eventos(idEventosSolicitacaoRevisaoAquisicao,0);
						
	}
				
	
	public void preSolicitarRevisaoAquisicao(){
		if(validarCampos()){
			mostraConfirmacaoSolicitaRevisaoAquisicao();
		}
	}
	
	
	public void mostraTelaSolicitaRevisaoAquisicao(){
		exibesolicitarevisaoaquisicao=true;
	}
	
	public void mostraConfirmacaoSolicitaRevisaoAquisicao() {
		exibeconfirmasolicitarevisaoaquisicao=true;
		exibesolicitarevisaoaquisicao=false;		
	}
	
	public void fechaSolicitarRevisaoAquisicao(){
		exibeconfirmasolicitarevisaoaquisicao=false;
	}

	private void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
	}
	
	public void solicitarRevisaoAquisicao(){		
						
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.setCnmcomentario(comentario);
		evento.getEsolicitacaorevisaoaquisicao().setStatusdemandamomentosolicitacao(demanda.getStatus());
		evento.getEsolicitacaorevisaoaquisicao().setEventosolicitacaorevisaoaquisicao(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
			
		incluiLog();
		
		demanda.setStatusanterior(demanda.getStatus());
		demanda.setStatus(new Status(idRevisaoPeloAnalistaAquisicao));		
		demandaint.alterar(demanda);
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		//pessoa logada
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
							
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
		
		//Solicitante
		pessoasEnvio.add(demanda.getAutor());				
								
		List<Lps> lps = new ArrayList<Lps>();
		
		lps.add(demanda.getLp());
		
		//pessoasEnvio.addAll(setarGerentesParaEnvio(lps));
								
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
		String assunto = "SSI COMPRAS - Solicita��o de Revis�o de Aquisi��o";
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" foi enviado para revis�o do analista de aquisi��o.";
				
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;
													
		fechaSolicitarRevisaoAquisicao();

	}

	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}

//	private List<Pessoas> setarGerentesParaEnvio(List<Lps> lps) {
//		
//		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
//		
//		List<Participantes> gerentes = participanteInt.recuperarGerentesPorLP(lps);
//		
//		Set<GruposModulos> grupoModulo = new HashSet<GruposModulos>();
//		
//		for(Participantes participante:gerentes){
//			grupoModulo.add(participante.getComprador().getGrupoModulo());
//		}
//		
//		for(GruposModulos gm:grupoModulo){
//			pessoasEnvio.addAll(gruposmodulosint.recuperaPessoasDoGrupo(gm));
//		}
//		
//		return pessoasEnvio;
//		
//	}
			
	
	private boolean validarCampos() {	
		
		if(StringUtils.isBlank(comentario)){
			devolveErroParaTela("formsolicitarevisaoaquisicao:comentariosolicitacaoaquisicao","Coment�rio obrigat�rio.");
			return false;
		}
												
		return true;
	}

	public Demandas getDemanda() {
		return demanda;
	}

	public void setDemanda(Demandas demanda) {
		this.demanda = demanda;
	}

	public Eventos getEvento() {
		return evento;
	}

	public void setEvento(Eventos evento) {
		this.evento = evento;
	}

	public boolean isExibeconfirmasolicitarevisaoaquisicao() {
		return exibeconfirmasolicitarevisaoaquisicao;
	}

	public void setExibeconfirmasolicitarevisaoaquisicao(
			boolean exibeconfirmasolicitarevisaoaquisicao) {
		this.exibeconfirmasolicitarevisaoaquisicao = exibeconfirmasolicitarevisaoaquisicao;
	}

	public boolean isExibesolicitarevisaoaquisicao() {
		return exibesolicitarevisaoaquisicao;
	}

	public void setExibesolicitarevisaoaquisicao(
			boolean exibesolicitarevisaoaquisicao) {
		this.exibesolicitarevisaoaquisicao = exibesolicitarevisaoaquisicao;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	
}
