package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.compras.common.interfaces.DemandasServiceInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.Mensageria;

@ManagedBean
@ViewScoped
public class EnvioAoMercadoMB implements Serializable {

	private static final long serialVersionUID = 8125658531268558312L;
	
	private static final int idStatusEnvioAoMercado = 12;
	private static final long idAguardandoProposta = 28L;
	
	private static final String tituloLog = "Envio ao mercado";
				
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
	
	@EJB
	private DemandasServiceInt demandasService;
							
	private Lps lp;
			
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibeconfirmaenvioaomercado=false;
	private boolean exibeenvioaomercado=false;
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
								
		evento = new Eventos(idStatusEnvioAoMercado,0);
						
	}
			
	public void preEnviaAoMercado(){
		
		if(validarCampos()){
			mostraConfirmacaoEnvioAoMercado();	
		}		
	}
	
	
	public void mostraTelaEnvioAoMercado(){
		exibeconfirmaenvioaomercado=false;
		exibeenvioaomercado=true;		
	}
	
	public void mostraConfirmacaoEnvioAoMercado() {
		exibeconfirmaenvioaomercado=true;
	}
	
	public void fechaConfirmacaoEnvioAoMercado(){
		exibeconfirmaenvioaomercado=false;
	}
	
	public void fechaEnvioAoMercado(){
		exibeenvioaomercado=false;	
	}
	
	private void setaStatusDemanda(long idstatus) {		
		demanda.setStatus(new Status(idstatus));		
	}
	
	public void efetuarEnvioAoMercado(){		
						
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setStatusanterior(demanda.getStatus());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.getEm().setEventoem(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		demanda = demandasService.setarAtividadeCronograma(demanda,new Atividades(Atividades.ID_ENVIO_RFP_AO_MERCADO),demandasmb.recuperarPessoaLogada());
								
		incluiLog();
		
		demanda.setStatusanterior(demanda.getStatus());
		setaStatusDemanda(idAguardandoProposta);
		
		demandaint.alterar(demanda);
					
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
		
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
		
		//pessoasEnvio.addAll(demandasmb.setarGerentesParaEnvio(lps));
		
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
		String assunto = "SSI COMPRAS - Envio ao Mercado";
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" foi enviada ao mercado.";
				
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;
													
		fechaConfirmacaoEnvioAoMercado();
		fechaEnvioAoMercado();
		

	}

	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}

	
	
	private boolean validarCampos() {	
										
		if(evento.getEm().getDataenvio()==null){
			devolveErroParaTela("formenvioaomercado:dataenvio","Data de envio obrigat�ria.");
			return false;
		}
		
		Calendar cal = Calendar.getInstance();
		
		if(evento.getEm().getDataenvio().after(cal)){
			devolveErroParaTela("formenvioaomercado:dataenvio","A data de envio n�o pode ser maior que a data atual.");
			return false;			
		}
		
		return true;
		
	}
	
	private void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
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

	public boolean isExibeconfirmaenvioaomercado() {
		return exibeconfirmaenvioaomercado;
	}

	public void setExibeconfirmaenvioaomercado(boolean exibeconfirmaenvioaomercado) {
		this.exibeconfirmaenvioaomercado = exibeconfirmaenvioaomercado;
	}

	public boolean isExibeenvioaomercado() {
		return exibeenvioaomercado;
	}

	public void setExibeenvioaomercado(boolean exibeenvioaomercado) {
		this.exibeenvioaomercado = exibeenvioaomercado;
	}

	public Lps getLp() {
		return lp;
	}



	public void setLp(Lps lp) {
		this.lp = lp;
	}

}
