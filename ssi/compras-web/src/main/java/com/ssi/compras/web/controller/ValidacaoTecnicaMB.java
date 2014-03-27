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
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.compras.domain.Pareceres;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.compras.common.interfaces.CronogramaInt;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.LpsInt;
import com.ssi.compras.common.interfaces.PareceresInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.compras.common.interfaces.DemandasServiceInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class ValidacaoTecnicaMB implements Serializable {

	private static final long serialVersionUID = -1428942142501297767L;
	
	private static final String logValidacaoTecnica = "Validac�o T�cnica";
	
	@EJB
	private PareceresInt pareceresint;
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private LpsInt lpint;
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private DemandasServiceInt demandasService;	
	
	@EJB
	private CronogramaInt cronogramaint;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibepopup;
	private boolean exibeconfirmacao;
	private boolean exibelp;
	
	@PostConstruct
	public void init(){
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		if(evento==null){
			evento = new Eventos(25,1);
		}
	}
	
	public void habilitarlp(ValueChangeEvent event){
		exibelp = false;
		Pareceres p = (Pareceres) event.getNewValue();		
		if(p.getIdparecer() == 1 || p.getIdparecer() == 2){
			exibelp = true;
		}
	}
	
	public void habilitaModal(){
		exibepopup = true;
	}
	
	public void desabilitaModal(){
		exibepopup = false;
	}
	
	public void desabilitaConfirmacao(){
		exibepopup = true;
		exibeconfirmacao = false;
	}
	
	public List<Pareceres> recuperarPareceres(){
		return pareceresint.recuperarPareceresPorStatus(new Status(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA));
	}
	
	public List<Lps> recuperarLps(){
		return lpint.recuperar();
	}
	
	public void preGravar(){
		exibeconfirmacao = true;
	}
	
	public void gravar(){
				
		List<Lps> lps = new ArrayList<Lps>();
		lps.add(evento.getEvaltec().getLp());
				
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
		pessoasEnvio.add(demanda.getAutor());
		
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}
				
		pessoasEnvio.addAll(participanteInt.recuperarGerentesAquisicoes());	
						
		demanda.setStatusanterior(demanda.getStatus());
		evento.setStatusanterior(demanda.getStatus());
		
		switch(evento.getEvaltec().getParecer().getIdparecer().intValue()){
			case (int) Pareceres.ID_PROCEDE:
				
				demanda.setStatus(new Status(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA));			
				demanda.setLp(evento.getEvaltec().getLp());
				demanda = demandasService.setaGrupoComQuemEsta(new Papeis(Papeis.ID_GERENTE_COMPRA),demanda,lps);
				demanda = demandasService.setarAtividadeCronograma(demanda,new Atividades(Atividades.ID_VALIDACAO_SSI),demandasmb.recuperarPessoaLogada());
				
				break;
			
			case (int) Pareceres.ID_PROCEDE_COM_RESSALVA:
				
				demanda.setStatus(new Status(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA));
				demanda.setLp(evento.getEvaltec().getLp());
				demanda = demandasService.setaGrupoComQuemEsta(new Papeis(Papeis.ID_GERENTE_COMPRA),demanda,lps);
				demanda = demandasService.setarAtividadeCronograma(demanda,new Atividades(Atividades.ID_VALIDACAO_SSI),demandasmb.recuperarPessoaLogada());
				
				break;
			
			case (int) Pareceres.ID_IMPROCEDENTE:
				
				demanda.setStatus(new Status(Status.ID_DEMANDA_CANCELADA));		
				demanda = demandasService.setaPessoaComQuemEsta(null, demanda);
				demanda = demandasService.setarAtividadeCronograma(demanda,new Atividades(Atividades.ID_VALIDACAO_SSI),demandasmb.recuperarPessoaLogada());
				
				if(demanda.getAnalistaDeContratoResponsavel()!=null){
					pessoasEnvio.add(demanda.getAnalistaDeContratoResponsavel());	
				}				
												
				break;
			
			case (int) Pareceres.ID_REVISAO_PELO_EMISSOR:
				
				demanda.setStatus(new Status(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR));
				demanda = demandasService.setaPessoaComQuemEsta(demanda.getAutor(), demanda);
			
				if(demanda.getResponsaveltecnico()!=null){
					pessoasEnvio.add(demanda.getResponsaveltecnico());	
				}
				
				break;			
		}

		
		incluiLog();							
		registraEvento();
		
		demandaint.alterar(demanda);
		
		String assunto = "SSI COMPRAS - Valida��o T�cnica";
		String mensagem = "Foi efetuada a Valida��o T�cnica para a SSI de registro "+demanda.getCnmnumero()+".";
		
		demandasmb.envioEMail(pessoasEnvio, assunto, mensagem);
		
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
		evento = new Eventos(25,1);
		
		exibepopup = false;
		exibeconfirmacao = false;
		exibelp = false;
		
	}
	
	private void registraEvento() {
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.getEvaltec().setEvaltec(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
	}
	
	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), logValidacaoTecnica);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
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

	public boolean isExibepopup() {
		return exibepopup;
	}

	public void setExibepopup(boolean exibepopup) {
		this.exibepopup = exibepopup;
	}

	public boolean isExibeconfirmacao() {
		return exibeconfirmacao;
	}

	public void setExibeconfirmacao(boolean exibeconfirmacao) {
		this.exibeconfirmacao = exibeconfirmacao;
	}

	public boolean isExibelp() {
		return exibelp;
	}

	public void setExibelp(boolean exibelp) {
		this.exibelp = exibelp;
	}

}
