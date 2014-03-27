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
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.EventosSolicitacaoRevisaoAquisicao;
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
public class RevisaoPeloAnalistaAquisicaoMB implements Serializable {

	private static final long serialVersionUID = 8125658531268558312L;
		
	private static final int idParecerRevisaoPeloEmissor = 4;
	private static final int idParecerDevolverAoComprador = 7;
	
	private static final long idStatusDemandaRevisaoPeloEmissor = 7L;
	private static final long idStatusDemandaEnvioAoMercado = 12L;
	private static final long idStatusDemandaAguardandoPropostas = 28L;	
	
	private static final String tituloLog = "Revis�o pelo analista de aquisi��o";
			
	@EJB
	private PareceresInt pareceresint;
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private PessoasInt pessoasInt;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ModulosInt modulosint;
		
	private List<Pareceres> pareceresRevisao = new ArrayList<Pareceres>();
			
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibeconfirmarevisaopeloanalista;
	private boolean exiberevisaoanalista;
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		pareceresRevisao = pareceresint.recuperarPareceresPorStatus(demanda.getStatus());
				
		evento = new Eventos();		
						
	}
	
	public void setaParecer(ValueChangeEvent event){
		
		Pareceres parecer = (Pareceres) event.getNewValue();		
		
		evento.getEgenerico().setParecer(parecer);
		
	}
			
			
	public void preRevisaoPeloAnalista(){
		if(validarCampos()){
			mostraConfirmacaoRevisao();
		}
	}
	
	public void mostraTelaRevisao(){
		exiberevisaoanalista = true;
	}	
	
	public void mostraConfirmacaoRevisao(){
		fecharTelaRevisao();
		exibeconfirmarevisaopeloanalista = true;
	}
	
	public void fechaTelaRevisaoConfirmacao(){
		exibeconfirmarevisaopeloanalista = false;
	}
	
	private void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
	}
	
	public void efetuarRevisao(){		
						
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.getEgenerico().setEventogenerico(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		demanda.setStatusanterior(demanda.getStatus());
							
		switch(evento.getEgenerico().getParecer().getIdparecer().intValue()){
		
		case idParecerRevisaoPeloEmissor:
											
			setaStatusDemanda(idStatusDemandaRevisaoPeloEmissor);
															
			break;
			
		case idParecerDevolverAoComprador:
			
			EventosSolicitacaoRevisaoAquisicao eventosSolicitacaoRevisaoAquisicao = eventosint.recuperaUltimaSolicitacaoRevisaoAquisicao(demanda);
			
			if(eventosSolicitacaoRevisaoAquisicao !=null){
				
				if(eventosSolicitacaoRevisaoAquisicao.getStatusdemandamomentosolicitacao().getIdstatus() == idStatusDemandaEnvioAoMercado){
					setaStatusDemanda(idStatusDemandaEnvioAoMercado);		
				}else{
					setaStatusDemanda(idStatusDemandaAguardandoPropostas);
				}
												
			}
			
			break;
								
		}					
				
		incluiLog();
		
		demandaint.alterar(demanda);
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
		
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}

		List<Lps> lps = new ArrayList<Lps>();
		
		lps.add(demanda.getLp());
		
		//pessoasEnvio.addAll(setarGerentesParaEnvio(lps));
		
		//TODO Falta gerente de aquisi��o. Os gerentes de aquisi��o est�o ligados � UF e n�o sabia como pegar essas UFs. N�o sei se as UFs vem dos or�amentos
		//uma vez que n�o existe UF na compra.
		
		//TODO Falta incluir gerente de contratos. N�o sei que gerente de contrato � esse que est� sendo falado na documenta��o
		
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}
		
				
		if(demanda.getAnalistaDeContratoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeContratoResponsavel());	
		}
		
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
		String assunto = "SSI COMPRAS - Revisada pelo Analista de Aquisi��o";
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" foi revisada pelo Analista de Aquisi��o.";
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;			
												
		fecharTelaRevisao();
		fechaTelaRevisaoConfirmacao();

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
			
	
	public void fecharTelaRevisao(){
		exiberevisaoanalista = false;
	}
	
	
	private void setaStatusDemanda(long idstatus) {		
		demanda.setStatus(new Status(idstatus));		
	}

	private boolean validarCampos() {	
		
		if(StringUtils.isBlank(evento.getCnmcomentario())){
			devolveErroParaTela("formrevisaopeloanalista:comentariorevisaoanalistaaquisicao","Coment�rio � obrigat�rio.");
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

	public List<Pareceres> getPareceresRevisao() {
		return pareceresRevisao;
	}

	public void setPareceresRevisao(List<Pareceres> pareceresRevisao) {
		this.pareceresRevisao = pareceresRevisao;
	}

	public boolean isExibeconfirmarevisaopeloanalista() {
		return exibeconfirmarevisaopeloanalista;
	}

	public void setExibeconfirmarevisaopeloanalista(
			boolean exibeconfirmarevisaopeloanalista) {
		this.exibeconfirmarevisaopeloanalista = exibeconfirmarevisaopeloanalista;
	}

	public boolean isExiberevisaoanalista() {
		return exiberevisaoanalista;
	}

	public void setExiberevisaoanalista(boolean exiberevisaoanalista) {
		this.exiberevisaoanalista = exiberevisaoanalista;
	}

	
	

}
