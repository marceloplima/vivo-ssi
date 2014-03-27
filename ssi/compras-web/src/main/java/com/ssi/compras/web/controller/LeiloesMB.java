package com.ssi.compras.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.compras.common.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Leiloes;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Modalidades;
import com.ssi.kernel.compras.domain.Propostas;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.compras.domain.TiposLeiloes;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.LeiloesInt;
import com.ssi.compras.common.interfaces.ModalidadesInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.compras.common.interfaces.PropostasInt;
import com.ssi.compras.common.interfaces.TiposLeiloesInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.Mensageria;

@ManagedBean
@ViewScoped
public class LeiloesMB implements java.io.Serializable{

	private static final long serialVersionUID = 464433750521310893L;
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private PropostasInt propostaint;
	
	@EJB
	private ModalidadesInt modalidadeint;
	
	@EJB
	private TiposLeiloesInt tipoleilaoint;
	
	@EJB
	private LeiloesInt leilaoint;
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private PessoasInt pessoasint;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private boolean exibepopup;
	private boolean exibeconfirmacao;
	private boolean exibecompletarleilao;
	private boolean exibedetalhesleilao;
	private boolean exibeconfirmacaofinalizar;
	
	private Eventos evento;
	
	private Leiloes leilao;
	
	private List<Leiloes> leiloes;
	private List<Leiloes> leiloeschecked;
	
	private static final long idStatusLeilaoEmAberto = 42L;
	private static final long idStatusLeilaoFinalizado = 43L;
	
	private IndexMB indexmb;
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	
	@PostConstruct
	public void init(){
		setExibepopup(false);
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		if(evento == null){
			evento = new Eventos();
		}
		
		if(leilao == null){
			leilao = new Leiloes();
		}
		
		validadorcamposinvalidos = new ValidadorCamposInvalidos();
		
	}
	
	public void inicializarValidadores(){
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
    	msgserro = new ArrayList<String>();
	}
	
	public void selecionarCheckbox(Leiloes obj){
		if(leiloeschecked.contains(obj)){
			obj.setChecked(false);
			leiloeschecked.remove(obj);
		}else{
			obj.setChecked(true);
			leiloeschecked.add(obj);
		}
		
		List<Leiloes> jachecados = recuperarChecados();
		
		if(jachecados != null && jachecados.size()>=1){
			Iterator<Leiloes> it = jachecados.iterator();
			while(it.hasNext()){
				Leiloes leilao = it.next();
				leiloeschecked.add(leilao);
			}
		}
		
		// Armazena os checados em sess�o
		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessao.put("leiloeschecked", leiloeschecked);
	}
	
	public void destroiLeiloesChecked(){
		// Recupera a Sess�o para eliminar o list de checados caso exista
		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessao.remove("leiloeschecked");
	}
	
	@SuppressWarnings("unchecked")
	public List<Leiloes> recuperarChecados(){
		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		List<Leiloes>checados = (List<Leiloes>) sessao.get("leiloeschecked");
		return checados;
	}
	
	public List<Leiloes> recuperar(){
		leiloes = leilaoint.recuperar(getDemanda());
		return leiloes; 
	}
	
	public void preFinalizar(){
		exibeconfirmacaofinalizar = true;
	}
	
	public void finalizar(){
		Status status = new Status();
		status.setIdstatus(idStatusLeilaoFinalizado);
		status.setCnmstatus("Finalizado");
		leilao.setStatus(status);
		leilaoint.registrar(leilao);
		
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
						
		evento.setStatus(getDemanda().getStatus()); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.setStatusanterior(getDemanda().getStatus()); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.getEgenerico().setEventogenerico(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		
		// Cria o Log
		String descricaoLog;
		descricaoLog = "Finalizar Leil�o";
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
						
		demanda = demandaint.alterar(demanda);
			
		desabilitaModal();
		desabilitaConfirmacao();
		desabilitaDetalhes();
		desabilitaCompletar();
		desabilitaConfirmacaoFinalizar();
		
		exibedetalhesleilao = false;
		
		// Manda os emails
		Modulos modulo = modulosint.recuperarUnico(336L);
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
				
		// Envia Email
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
				
		// Gerentes por LP
		try{
			List<Lps> lps = new ArrayList<Lps>();
					
			lps.add(demanda.getLp());
					
			pessoasEnvio.addAll(participanteInt.recuperarGerentesPorLp(lps));
		}
		catch(Exception e){
			e.printStackTrace();
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
				
		Mensageria mensageria = new Mensageria();
		String assunto = "SSI COMPRAS - Finaliza��o de Leil�o";
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" teve um leil�o finalizado.";
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
				
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;
		
		evento = new Eventos();
		leilao = new Leiloes();
				
		demandasmb.setDemandas(getDemanda());
	}
	
	
	public void preRegistrar(){
		
		inicializarValidadores();
    	
    	// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	if(leilao.getModalidade()!=null)
    		camposvalidar.put("Modalidade", leilao.getModalidade().getIdmodalidade().toString());
    	else
    		camposvalidar.put("Modalidade", "");
    	
    	if(leilao.getDataleilao()!=null)
    		camposvalidar.put("Data do Leil�o", "OK");
    	else
    		camposvalidar.put("Data do Leil�o", "");
    	
    	if(leilao.getProppart()!=null && leilao.getProppart().size()>=1){
    		camposvalidar.put("Participantes", "OK");
    	}
    	else{
    		camposvalidar.put("Participantes", "");
    	}
		
    	if(leilao.getTipoleilao()!=null)
    		camposvalidar.put("Tipo do Leil�o", leilao.getTipoleilao().getIdtipoleilao().toString());
    	else
    		camposvalidar.put("Tipo do Leil�o", "");
    	
    	camposvalidar.put("Valor Fator Inicial", leilao.getFatorinicial().toString());
    	camposvalidar.put("Valor Inicial", leilao.getValorinicial().toString());
    	camposvalidar.put("Salvaguarda", leilao.getSalvaguarda().toString());
    	camposvalidar.put("Decr�scimo/Acr�scimo", leilao.getDecresacresc().toString());
    	
    	// S� valida abaixo caso o leil�o esteja sendo completado (idleilao>=1)
    	if(leilao!=null && leilao.getIdleilao()!=null){
    		if(leilao.getPropvenc()!=null && leilao.getPropvenc().size()>=1){
        		camposvalidar.put("Vencedores", "OK");
        	}
        	else{
        		camposvalidar.put("Vencedores", "");
        	}
    		
    		camposvalidar.put("Lance Vencedor", leilao.getLancevencedor().toString());
    		camposvalidar.put("Coment�rio", leilao.getComentario());
    	}
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    	
    	if(msgserro.size()==0){
    		exibeconfirmacao = true;
    	}
    	else{
	    	indexmb.setPanelexibeerro(true);
			indexmb.setRenderizalistadeerros(true);
			indexmb.setMsgspanel(msgserro);
    	}
	}
	
	public void registrar(){
		leilao.setAutor(demandasmb.recuperarPessoaLogada());
		leilao.setDemandaleilao(getDemanda());
		Status status = new Status();
		status.setIdstatus(idStatusLeilaoEmAberto);
		status.setCnmstatus("Em Aberto");
		leilao.setStatus(status);
		leilaoint.registrar(leilao);
		
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
				
		evento.setStatus(getDemanda().getStatus()); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.setStatusanterior(getDemanda().getStatus()); // Nesse caso, n�o altera o status da demanda, apenas registra em eventos
		evento.getEgenerico().setEventogenerico(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
				
		// Cria o Log
		String descricaoLog;
		descricaoLog = "Registrar Leil�o";
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
				
		demanda = demandaint.alterar(demanda);
		
		// Manda os emails
		Modulos modulo = modulosint.recuperarUnico(336L);
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		
		// Envia Email
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
		
		// Gerentes por LP
		try{
			List<Lps> lps = new ArrayList<Lps>();
			
			lps.add(demanda.getLp());
			
			pessoasEnvio.addAll(participanteInt.recuperarGerentesPorLp(lps));
		}
		catch(Exception e){
			e.printStackTrace();
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
		
		Mensageria mensageria = new Mensageria();
		String assunto = "SSI COMPRAS - Registro de Leil�o";
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" teve um leil�o registrado.";
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;
		
		desabilitaModal();
		desabilitaConfirmacao();
		desabilitaDetalhes();
		desabilitaCompletar();
		
		exibepopup = false;
		evento = new Eventos();
		leilao = new Leiloes();
		
		demandasmb.setDemandas(getDemanda());
	}
	
	
	public List<Propostas> recuperarPropostasAprovadas(){
		return propostaint.recuperarPropostasAprovadas(demanda);
	}
	
	public List<Modalidades> recuperarModalidades(){
		return modalidadeint.recuperar();
	}
	
	public List<TiposLeiloes> recuperarTiposLeilao(){
		return tipoleilaoint.recuperar();
	}
	
	public void habilitaDetalhes(Leiloes leilao){
		
		List<Propostas> proppart = leilaoint.recuperarPropostasPart(leilao);
		List<Propostas> propvenc = leilaoint.recuperarPropostasVenc(leilao);
		
		leilao.setProppart(proppart);
		leilao.setPropvenc(propvenc);
		
		this.leilao = leilao;
		setExibedetalhesleilao(true);
	}
	
	public void desabilitaDetalhes(){
		setExibedetalhesleilao(false);
	}
	
	public void habilitaCompletar(Leiloes leilao){
		
		List<Propostas> proppart = leilaoint.recuperarPropostasPart(leilao);
		List<Propostas> propvenc = leilaoint.recuperarPropostasVenc(leilao);
		
		leilao.setProppart(proppart);
		leilao.setPropvenc(propvenc);
		
		this.leilao = leilao;
		
		this.leilao = leilao;
		setExibecompletarleilao(true);
	}
	
	public void desabilitaCompletar(){
		setExibecompletarleilao(false);
	}
	
	public void habilitaModal(){
		setExibepopup(true);
	}
	
	public void desabilitaModal(){
		setExibepopup(false);
	}
	
	public void desabilitaConfirmacao(){
		setExibeconfirmacao(false);
	}
	
	public void desabilitaConfirmacaoFinalizar(){
		setExibedetalhesleilao(true);
		setExibeconfirmacaofinalizar(false);
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
	
	public boolean isExibecompletarleilao() {
		return exibecompletarleilao;
	}

	public void setExibecompletarleilao(boolean exibecompletarleilao) {
		this.exibecompletarleilao = exibecompletarleilao;
	}
	
	public boolean isExibedetalhesleilao() {
		return exibedetalhesleilao;
	}

	public void setExibedetalhesleilao(boolean exibedetalhesleilao) {
		this.exibedetalhesleilao = exibedetalhesleilao;
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

	public Leiloes getLeilao() {
		return leilao;
	}

	public void setLeilao(Leiloes leilao) {
		this.leilao = leilao;
	}



	public List<Leiloes> getLeiloes() {
		return leiloes;
	}



	public void setLeiloes(List<Leiloes> leiloes) {
		this.leiloes = leiloes;
	}



	public List<Leiloes> getLeiloeschecked() {
		return leiloeschecked;
	}



	public void setLeiloeschecked(List<Leiloes> leiloeschecked) {
		this.leiloeschecked = leiloeschecked;
	}

	public boolean isExibeconfirmacaofinalizar() {
		return exibeconfirmacaofinalizar;
	}

	public void setExibeconfirmacaofinalizar(boolean exibeconfirmacaofinalizar) {
		this.exibeconfirmacaofinalizar = exibeconfirmacaofinalizar;
	}

	public IndexMB getIndexmb() {
		return indexmb;
	}

	public void setIndexmb(IndexMB indexmb) {
		this.indexmb = indexmb;
	}

	public ValidadorCamposInvalidos getValidadorcamposinvalidos() {
		return validadorcamposinvalidos;
	}

	public void setValidadorcamposinvalidos(ValidadorCamposInvalidos validadorcamposinvalidos) {
		this.validadorcamposinvalidos = validadorcamposinvalidos;
	}

	public List<String> getMsgserro() {
		return msgserro;
	}

	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
	}
	
}
