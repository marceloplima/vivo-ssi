package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;

import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.DemandasServiceInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.PareceresInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.compras.domain.Pareceres;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.TiposTelefone;

@ManagedBean
@ViewScoped
public class AnalisePeloGerenteDeCompraMB implements Serializable {

	private static final long serialVersionUID = 8125658531268558312L;				
	
	private static final String tituloLog = "An�lise Pelo Gerente de Compras";
			
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
		
	private List<Pareceres> pareceresAnalisePeloGerenteDeCompra = new ArrayList<Pareceres>();
	private List<Pessoas> participantesCompradores = new ArrayList<Pessoas>();
		
	private Lps lp;
	
	private String email;
	private String celular;
	private String localizacao;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibeconfirmaanalisepelogerentedecompras;
	private boolean exibeanalisepelogerentedecompras;
	private boolean exibelp;
	private boolean exibecomprador;
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		pareceresAnalisePeloGerenteDeCompra = pareceresint.recuperarPareceresPorStatus(new Status(Eventos.ID_ANALISE_PELO_GERENTE));
				
		evento = new Eventos((int) Eventos.ID_ANALISE_PELO_GERENTE,0);		
			
		if(demanda.getLp()!=null){
							
			evento.getAgc().setLp(demanda.getLp());
			
			List<Lps> lpsBusca = new ArrayList<Lps>();				
			lpsBusca.add(demanda.getLp());
					
			atualizaCompradores(lpsBusca);			
		}
		
		exibecomprador = true;
				
	}
	
	public void setaParecer(ValueChangeEvent event){
		exibelp = false;
		Pareceres parecer = (Pareceres) event.getNewValue();
		
		evento.getAgc().setParecer(parecer);
		
		if(parecer.getIdparecer() == Pareceres.ID_PARECER_REVISAO_LP){
			exibelp = true;
			exibecomprador = false;
		}
		else{
			exibecomprador = true;
		}
		
	}
			
	public void setaCompradores(ValueChangeEvent event){
		
		email="";
		localizacao="";
		celular="";
		
		Lps lp = (Lps) event.getNewValue();
		
		List<Lps> lpsBusca = new ArrayList<Lps>();
						
		lpsBusca.add(lp);
		evento.getAgc().setLp(lp);
		
		atualizaCompradores(lpsBusca);
				
	}
	
	public void atualizaCompradores(List<Lps> lpsBusca){

		participantesCompradores = new ArrayList<Pessoas>();
		
		if(lpsBusca!=null){
		
			participantesCompradores = participanteInt.recuperarCompradoresPorLp(lpsBusca);
			
		}
						
	}
	
	public void recuperaInformacoesDoComprador(ValueChangeEvent event){
				
		email="";
		localizacao="";
		celular="";
						
		Pessoas compradorSelecionado = (Pessoas) event.getNewValue();
						
		if(compradorSelecionado != null){
			
			evento.getAgc().setComprador(compradorSelecionado);
			
			Pessoas pessoaComprador = pessoasint.recuperarUnico(compradorSelecionado.getIdpessoa());		
			
			for(Emails em:pessoasint.retornarEmailsPessoa(compradorSelecionado)){
				email = em.getCnmemail();
				break;
			}
			
			celular = pessoasint.retornaTelefone(pessoasint.retornarTelefonesPessoa(compradorSelecionado), TiposTelefone.ID_CELULAR);
			localizacao = pessoaComprador.getCnmlocalizacao();
		}
				
	}
	
	public void preAnaliseGerenteDeCompras(){
		if(validarCampos()){
			mostraConfirmacaoPeloGerenteCompra();
		}
	}
	
	public void mostraTelaAnaliseGerenteDeCompras(){
		exibeanalisepelogerentedecompras = true;
	}	
	
	public void mostraConfirmacaoPeloGerenteCompra(){
		exibeconfirmaanalisepelogerentedecompras = true;
	}
	
	public void fechaTelaConfirmacaoPeloGerenteDeCompra(){
		exibeconfirmaanalisepelogerentedecompras = false;
	}
		
	public void efetuarAnaliseGerenteDeCompras(){		
						
		registraEvento();
					
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());				
		pessoasEnvio.add(demanda.getAutor());	
				
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}
		
		if(demanda.getLp()!=null){
			List<Lps> lps = new ArrayList<Lps>();
			
			lps.add(demanda.getLp());

			pessoasEnvio.addAll(participanteInt.recuperarGerentesPorLp(lps));	
		}

		demanda.setStatusanterior(demanda.getStatus());
		demanda.setPessoacomprador(evento.getAgc().getComprador());
		
		switch(evento.getAgc().getParecer().getIdparecer().intValue()){
		
		case (int) Pareceres.ID_PROCEDE:
					
			setaStatusDemanda(Status.ID_DEMANDA_ENVIO_AO_MERCADO);

			if(demanda.getResponsaveltecnico()!=null){
				pessoasEnvio.add(demanda.getResponsaveltecnico());	
			}
			
			demanda = demandasService.setaGrupoComQuemEsta(new Papeis(Papeis.ID_COMPRADOR),demanda,null);
				
			pessoasEnvio.add(demanda.getPessoacomprador());						
											
			break;
			
		case (int) Pareceres.ID_DEVOLVER:
			
			setaStatusDemanda(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA);
		
			demanda = demandasService.setaGrupoComQuemEsta(new Papeis(Papeis.ID_ANALISTA_AQUISICAO),demanda,null);

			break;
					
		case (int) Pareceres.ID_PARECER_REVISAO_LP:

			if(demanda.getAutor()!=null){
				pessoasEnvio.add(demanda.getAutor());	
			}				
			
			setaStatusDemanda(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA);
			
			break;
			
		}					
		
		demanda.setLp(evento.getAgc().getLp());
		incluiLog();
		
		demandaint.alterar(demanda);
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
		
		String assunto = "SSI COMPRAS - An�lise pelo Gerente de Compras";
		String mensagem = "Foi efetuada a An�lise pelo Gerente de Compras para a SSI de registro "+demanda.getCnmnumero()+".";
		
		demandasmb.envioEMail(pessoasEnvio, assunto, mensagem);
														
		fecharTelaAnalisePeloGerenteDeCompra();
		fechaTelaConfirmacaoPeloGerenteDeCompra();
		exibelp = false;

	}

	private void registraEvento() {
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(getDemanda().getStatus());
		evento.setStatusanterior(demanda.getStatus());
		evento.getAgc().setEventoagc(evento); // Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
	}

	private void incluiLog() {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}
	
	public void fecharTelaAnalisePeloGerenteDeCompra(){
		exibeanalisepelogerentedecompras = false;
	}
	
	
	private void setaStatusDemanda(long idstatus) {		
		demanda.setStatus(new Status(idstatus));		
	}

	private boolean validarCampos() {	
		
		switch(evento.getAgc().getParecer().getIdparecer().intValue()){
		
		case (int) Pareceres.ID_PROCEDE:
						
			if(evento.getAgc().getComprador().getIdpessoa()==null){
				demandasmb.devolveErroParaTela("formanalisegerentecompra:compradoranalisepelogerentedecompra","Comprador obrigat�rio.");
				return false;
			}
					
			break;
			
		case (int) Pareceres.ID_DEVOLVER:

			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("formanalisegerentecompra:comentario","Coment�rio � obrigat�rio.");
				return false;
			}			
			
			break;
						
		}				
		
		return true;
	}

	//Setter e Getter
	
	public Lps getLp() {
		return lp;
	}

	public void setLp(Lps lp) {
		this.lp = lp;
	}

	public boolean isExibeconfirmaanalisepelogerentedecompras() {
		return exibeconfirmaanalisepelogerentedecompras;
	}

	public void setExibeconfirmaanalisepelogerentedecompras(
			boolean exibeconfirmaanalisepelogerentedecompras) {
		this.exibeconfirmaanalisepelogerentedecompras = exibeconfirmaanalisepelogerentedecompras;
	}

	public boolean isExibeanalisepelogerentedecompras() {
		return exibeanalisepelogerentedecompras;
	}

	public void setExibeanalisepelogerentedecompras(
			boolean exibeanalisepelogerentedecompras) {
		this.exibeanalisepelogerentedecompras = exibeanalisepelogerentedecompras;
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

	public List<Pareceres> getPareceresAnalisePeloGerenteDeCompra() {
		return pareceresAnalisePeloGerenteDeCompra;
	}

	public void setPareceresAnalisePeloGerenteDeCompra(
			List<Pareceres> pareceresAnalisePeloGerenteDeCompra) {
		this.pareceresAnalisePeloGerenteDeCompra = pareceresAnalisePeloGerenteDeCompra;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public List<Pessoas> getParticipantesCompradores() {
		return participantesCompradores;
	}

	public void setParticipantesCompradores(List<Pessoas> participantesCompradores) {
		this.participantesCompradores = participantesCompradores;
	}

	public boolean isExibelp() {
		return exibelp;
	}

	public void setExibelp(boolean exibelp) {
		this.exibelp = exibelp;
	}

	public boolean isExibecomprador() {
		return exibecomprador;
	}

	public void setExibecomprador(boolean exibecomprador) {
		this.exibecomprador = exibecomprador;
	}
	
	

}
