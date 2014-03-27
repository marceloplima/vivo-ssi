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
import com.ssi.compras.common.interfaces.DemandasServiceInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.PareceresInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Pareceres;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.compras.domain.TiposLog;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class AnaliseAquisicaoMB implements Serializable {

	private static final String MSG_LOG_ANALISE_AQUISICAO = "An�lise Aquisi��o ";

	private static final long serialVersionUID = -299184851415696608L;
	
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
	
	@EJB
	private DemandasServiceInt demandasService;		
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibepopupanaliseaquisicao;
	private boolean exibeconfirmacaoanaliseaquisicao;
	
	private List<Pessoas> analistasAquisicao;
	
	@PostConstruct
	public void init(){
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		evento = new Eventos((int) Status.ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO,0);
		
		analistasAquisicao = participanteInt.recuperarAnalistasAquisicaoes();
	}
	
	public void preAnaliseAquisicao(){
		if(validaCampos()){
			exibeconfirmacaoanaliseaquisicao = true;	
		}
	}
	
	private boolean validaCampos() {
		
		switch (evento.getEgenerico().getParecer().getIdparecer().intValue()) {

			case (int) Pareceres.ID_PROCEDE:
				
				return isAnalistaAquisicaoObrigatorioOk();												
							
			case (int) Pareceres.ID_PROCEDE_COM_RESSALVA:
				
				return isAnalistaAquisicaoObrigatorioOk() && isComentarioObrigatorioOk();							
		
			case (int) Pareceres.ID_IMPROCEDENTE:
				
				return isComentarioObrigatorioOk() && isComentarioObrigatorioOk();
						
			case (int) Pareceres.ID_REVISAO_PELO_EMISSOR:
				
				return isAnalistaAquisicaoObrigatorioOk() && isComentarioObrigatorioOk();
			
		}
		
		return true;
	}

	private boolean isComentarioObrigatorioOk() {
		
		if(StringUtils.isBlank(evento.getCnmcomentario())){
			demandasmb.devolveErroParaTela("formanaliseaquisicao:idcomentarioanaliseaquisicao","Coment�rio obrigat�rio.");
			return false;
		}
		
		return true;
	}

	private boolean isAnalistaAquisicaoObrigatorioOk() {
		if(demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel()==null){
			demandasmb.devolveErroParaTela("formanaliseaquisicao:analistaaquisicao","Analista de aquisi��o obrigat�rio.");
			return false;
		}
		return true;
	}

	public void efetuarAnaliseAquisicao(){
		
		Modulos modulo = modulosint.recuperarUnico(336L);

		Logs log = new Logs();
		
		evento.setStatusanterior(demanda.getStatus());				
		demanda.setStatusanterior(demanda.getStatus());
		
		GruposModulos gm = new GruposModulos();
		gm.setIdgrupomodulo(8L);
		gm.setModulo(modulo);
		
		switch(evento.getEgenerico().getParecer().getIdparecer().intValue()){
			case (int) Pareceres.ID_PROCEDE:
				
				demanda.setStatus(new Status(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA));			
				demanda = demandasService.setaPessoaComQuemEsta(demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel(), demanda);
				log = demandasmb.criaLog(TiposLog.ID_DEMANDA, demandasmb.recuperarPessoaLogada(), MSG_LOG_ANALISE_AQUISICAO + "(Procede)");
				
				registrarEvento();
								
				break;
			
			case (int) Pareceres.ID_PROCEDE_COM_RESSALVA:
				
				demanda.setStatus(new Status(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA));		
				demanda = demandasService.setaPessoaComQuemEsta(demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel(), demanda);
				log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), MSG_LOG_ANALISE_AQUISICAO + "(Procede com ressalva)");
												
				registrarEvento();
								
				break;
			
			case (int) Pareceres.ID_IMPROCEDENTE:
				
				demanda.setStatus(new Status(Status.ID_DEMANDA_CANCELADA));
				demanda = demandasmb.eliminaGrupoEPessoaComQuemEsta(demanda);
				log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), MSG_LOG_ANALISE_AQUISICAO + "(Cancelada)");
				
				registrarEvento();
								
				break;
			
			case (int) Pareceres.ID_REVISAO_PELO_EMISSOR:
								
				demanda.setStatus(new Status(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR));
				demanda = demandasService.setaPessoaComQuemEsta(demandasmb.getDemandas().getAutor(), demanda);
				log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), MSG_LOG_ANALISE_AQUISICAO + "(Revis�o Pelo Emissor)");
				
				registrarEvento();				

				break;
		}
		
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
		demandaint.alterar(demanda);
		
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
				
		String assunto;
		String mensagem;
		
		assunto = "SSI COMPRAS - An�lise de Aquisi��o";
		mensagem = "Foi efetuada a An�lise de Aquisi��o para a SSI de registro "+demanda.getCnmnumero()+".";
						
		demandasmb.envioEMail(recuperarPessoasParaEnvioEmail(), assunto, mensagem); 
		
		exibepopupanaliseaquisicao = false;
		exibeconfirmacaoanaliseaquisicao = false;
	}

	private void registrarEvento() {
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.getEgenerico().setEventogenerico(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
	}
	
	private List<Pessoas> recuperarPessoasParaEnvioEmail(){
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		
		pessoasEnvio.add(demanda.getAutor());
		if(demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel());	
		}
		for(Pessoas pessoa:participanteInt.recuperarGerentesContratos()){
			pessoasEnvio.add(pessoa);
		}
				
		return pessoasEnvio;
		
	}
	
	public List<Pareceres> recuperarPareceres(){
		return pareceresint.recuperarPareceresPorStatus(new Status(Status.ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO));
	}
	
	public void habilitaAnaliseAquisicao(){
		exibepopupanaliseaquisicao = true;
	}
	
	public void desabilitaAnaliseAquisicao(){
		exibepopupanaliseaquisicao = false;
	}
	
	public void desabilitaConfirmaAnaliseAquisicao(){
		exibepopupanaliseaquisicao = true;
		exibeconfirmacaoanaliseaquisicao = false;
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

	public boolean isExibepopupanaliseaquisicao() {
		return exibepopupanaliseaquisicao;
	}

	public void setExibepopupanaliseaquisicao(boolean exibepopupanaliseaquisicao) {
		this.exibepopupanaliseaquisicao = exibepopupanaliseaquisicao;
	}

	public Eventos getEvento() {
		return evento;
	}

	public void setEvento(Eventos evento) {
		this.evento = evento;
	}

	public boolean isExibeconfirmacaoanaliseaquisicao() {
		return exibeconfirmacaoanaliseaquisicao;
	}

	public void setExibeconfirmacaoanaliseaquisicao(
			boolean exibeconfirmacaoanaliseaquisicao) {
		this.exibeconfirmacaoanaliseaquisicao = exibeconfirmacaoanaliseaquisicao;
	}

	public List<Pessoas> getAnalistasAquisicao() {
		return analistasAquisicao;
	}

	public void setAnalistasAquisicao(List<Pessoas> analistasAquisicao) {
		this.analistasAquisicao = analistasAquisicao;
	}
	
	

}
