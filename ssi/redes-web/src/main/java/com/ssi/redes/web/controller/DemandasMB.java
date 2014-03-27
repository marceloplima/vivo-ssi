package com.ssi.redes.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;

import com.ssi.kernel.controller.interfaces.AtividadesInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.controller.interfaces.RegionaisInt;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Regionais;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.LogsRedes;
import com.ssi.kernel.redes.domain.Rotas;
import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.domain.TiposLogRedes;
import com.ssi.kernel.redes.interfaces.CopiadosInt;
import com.ssi.kernel.redes.interfaces.DemandasRedesInt;
import com.ssi.kernel.redes.interfaces.EventosRedesInt;
import com.ssi.kernel.redes.interfaces.LogsRedesInt;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;
import com.ssi.kernel.redes.interfaces.RotasInt;
import com.ssi.kernel.redes.interfaces.StatusRedesInt;
import com.ssi.kernel.redes.interfaces.TiposLogRedesInt;
import com.ssi.kernel.redes.interfaces.UfsRedesInt;
import com.ssi.kernel.redes.interfaces.service.DemandasRedesServiceInt;
import com.ssi.kernel.utils.Mensageria;
import com.ssi.redes.common.utils.ValidadorCamposInvalidos;

@ManagedBean
@ViewScoped
public class DemandasMB implements Serializable {

	private static final long serialVersionUID = 4468503734082103168L;
	
	//private static final long idTipoTelefoneCelular = 3L;
	
	private static final long idTipoLogDemanda = 1L;
	
	private Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
	//ID ENVIOS DE E-MAIL
	private static final int idEnvioEmailCanecelamento = 1;
	private static final int idEnvioEmailEmissorEncaminhaPrimeiraVez = 3;

	@EJB
	private StatusRedesInt statusInt;

	@EJB
	private LogsRedesInt logsInt;
	
	@EJB
	private PessoasInt pessoasInt;
	
	@EJB
	private DemandasRedesInt demandaInt;
	
	@EJB
	private UfsRedesInt ufsint;
	
	@EJB
	private TiposLogRedesInt tiposLogInt;
	
	@EJB
	private ParticipantesRedesInt participanteInt;
	
	@EJB
	private GruposModulosInt grupoModulosInt;
	
	@EJB
	private EventosRedesInt eventosint;
			
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private DemandasRedesServiceInt demandasService;
	
	@EJB
	private RegionaisInt regionalint;
	
	@EJB
	private RotasInt rotasint;
	
	@EJB
	private CopiadosInt copiadosInt;
	
	@EJB
	private AtividadesInt atividadeint;
		
	private List<DemandasRedes> demandaspessoa;
	private List<LogsRedes> logsDemanda;
	private PessoasMB pessoasMb;
	private String comQuemEsta;
	private GruposModulos gm;
	private Modulos modulo;
	private Mensageria mensageria = new Mensageria();
	private DemandasRedes demandas;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private List<LogsRedes> logs;
	private String comentarioJustificativa; //campo utilizado para os coment�rios de diversos modais uma vez que esse coment�rio depois vai para evento
	private Boolean showCancelar = false;
	private Boolean showConfirmacaoCancelar = false;
	private boolean showConfirmaEncaminhar = false;
		
	// Controles de popup
	private Boolean confirmaSalvar = false;
	private Boolean salvoComSucesso = false;
	
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	
	private IndexMB indexmb;
	private List<String> msgserro;

	private boolean exibelistagem;

	// Usado na busca
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	// Filtros
	private String iddemanda;
	private String cnmnumero;
	private String autor;
	private Calendar datainicial;
	private Calendar datafinal;
	private StatusRedes filtrostatus;
	
	// Mensagens
	private static final String mensagemLogSSICancelada = "SSI cancelada";
	private static final String mensagemLogSalvoComoRascunho = "Documento salvo como rascunho";
	private static final String mensagemSSISalvaComoRascunhoComSucesso = "SSI salva com sucesso !";
	
	private List<Regionais> regionaischecked;
	private List<Pessoas> pessoasJaCopiadas = new ArrayList<Pessoas>();
	
	// Usado na sele��o pelo autocomplete
	private String responsaveltecnicostr;
	
	private String cnmtelefonesolicitante;
	
	private Atividades atividadeselecionada;
	
	public DemandasMB() {}
	
	@PostConstruct
	public void init() {	
		modulo = modulosint.recuperarUnico(337L);
		
		// In�cio da constru��o do objeto por um par�metro passado da view de busca (se houver)
		// Caso n�o haja, um novo Demandas ser� criado (como rascunho)
		String iddemandaget = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("iddemanda");
				
		if(iddemandaget !=null){
			Integer iddemandaeditar = Integer.valueOf(iddemandaget);       
				
			if(iddemandaeditar != null && iddemandaeditar >= 0){
				demandas = demandaInt.recuperarDemandaEspecifica(new Long(iddemandaeditar));
			}
		}		
		// Fim da constru��o do objeto passado por GET da view de busca
				
		// In�cio da constru��o do objeto por um par�metro passado da view de busca (se houver)
		// Caso n�o haja, um novo Demandas ser� criado (como rascunho)
		String flagbandeiraautor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bandeiraautor");
						
		if(flagbandeiraautor !=null){
			Map<String,Object> filtros = new LinkedHashMap<String,Object>();
			filtros.put("pessoa", recuperarPessoaLogada());
					
			this.filtros = filtros;
		}
		// Fim da constru��o do objeto passado por GET da view de busca
		
		if (demandas == null) {
			demandas = new DemandasRedes();
			demandas.setStatus(statusInt.recuperarUnico(new StatusRedes(StatusRedes.ID_DEMANDA_RASCUNHO)));
			demandas.setAutor(recuperarPessoaLogada());
			Calendar cal = Calendar.getInstance();
			demandas.setDatacadastro(cal);
			demandas.setAutorcancelamento(null);
			//recuperarTelefoneSolicitante();
			demandas.setPessoacomquemesta(recuperarPessoaLogada());
			demandas.setResponsaveltecnico(null);
			demandas.setFlagtarifacao(false);
			demandas.setFlagalarmistica(false);
			demandas.setFlagavisoimprensa(false);
			demandas.setFlagdadostrafego(false);
			demandas.setFlagseguranca(false);
			demandas.setFlagsinalizacao(false);
			regionaischecked = new ArrayList<Regionais>();
			demandas.setRegionaisdemandas(new HashSet<Regionais>(regionaischecked));
			demandas.setCopiados(new HashSet<Pessoas>(pessoasJaCopiadas));
		}
		else{
			// Recupera as regionais da Demanda
			regionaischecked = demandaInt.recuperarRegionais(demandas);
			
			// Seta as regionais recuperadas na demanda
			demandas.setRegionaisdemandas(new HashSet<Regionais>(regionaischecked));
			
			// Recupera as Rotas da Demanda
			demandas.setRotas(new LinkedHashSet<Rotas>(rotasint.recuperarRotasDemanda(demandas)));
			
			demandas.setCopiados(new HashSet<Pessoas>(copiadosInt.recuperarCopiadosDemanda(demandas)));
			
			pessoasJaCopiadas = new ArrayList<Pessoas>(demandas.getCopiados());
			
			setCnmtelefonesolicitante(demandas.getCnmtelefone());
			
		}
				
		if(demandaspessoa == null){
			demandaspessoa = new ArrayList<DemandasRedes>();
		}
		
		if(demandas.getIddemanda() != null){
			logsDemanda = recuperaLog(demandas);	
		}
		
		if(demandas.getResponsaveltecnico()!=null){					
			responsaveltecnicostr = demandas.getResponsaveltecnico().getCnmnome();	
		}
		
		if (verificarDemandaNovaVindaDoLotusNotes()){

			String id = (String) sessao.get("id");			
			String universalId = (String) sessao.get("universalId");						
			
			if(id == null){			
				
				DemandasRedes demandaRedeAreaTransferencia = demandaInt.recuperaRegistroDaAreaDeTransferencia(universalId);
				
				if(demandaRedeAreaTransferencia != null){
										
					demandas.setArearequisitada(demandaRedeAreaTransferencia.getArearequisitada());
					demandas.setAreasolicitante(demandaRedeAreaTransferencia.getAreasolicitante());
					demandas.setInstrucao(demandaRedeAreaTransferencia.getInstrucao());
					demandas.setNumeroassociado(demandaRedeAreaTransferencia.getNumeroassociado());
					demandas.setUnid(demandaRedeAreaTransferencia.getUnid());
					demandas.setRetornarInformacaoParaNotes(true);
					demandas.setAplicacaoNotes(demandaRedeAreaTransferencia.getAplicacaoNotes());
									
				}
				
			}else{
					
				
					demandas = demandaInt.recuperarDemandaEspecifica(Long.valueOf(id));				
				
				}
			
			sessao.remove("universalId");
								
		}
						
		
		if (verificaConsultaPorNumeroSSI()){
			
			String numeroSSI = (String) sessao.get("numerossi");			
			demandas = demandaInt.recuperaDemandaPeloNumeroSSINotes(numeroSSI);
			sessao.remove("numerossi");
			
		}
		
		if(verificarConsultaPorIdDemanda()){
			String idDemanda = (String) sessao.get("iddemanda");			
			demandas = demandaInt.recuperarDemandaEspecifica(Long.parseLong(idDemanda));			
			sessao.remove("iddemanda");			
		}
		
		setarComQuemEsta();
				
	}
	
	// Recupera a atividade (T�tulo) no cadssi.xhtml no momento da sele��o e passa para os managed beans que precisam
	public void setarAtividade(ValueChangeEvent event){
		atividadeselecionada = (Atividades)event.getNewValue();
		atividadeselecionada = atividadeint.recuperarUnico(atividadeselecionada);
		
		// Distribui para os mb que precisam
		RotasMB rotasmb = RecuperadorInstanciasBean.recuperarInstanciaRotasBean();
		DemandasCopiadosMB dcmb = RecuperadorInstanciasBean.recuperarInstanciaCopiadosBean();
		
		rotasmb.setarAtividade(atividadeselecionada);
		dcmb.setarAtividade(atividadeselecionada);
		
	}

	private boolean verificarConsultaPorIdDemanda() {
		
		if(sessao.get("iddemanda") != null){
			return true;
		}
		
		return false;
	}

	private boolean verificaConsultaPorNumeroSSI() {				
		
		if(sessao.get("numerossi") != null){
			return true;
		}
		
		return false;

	}

	private boolean verificarDemandaNovaVindaDoLotusNotes() {
		
		if(sessao.get("universalId") != null){	
			return true;	
		}
		
		return false;
		
	}

	public DemandasRedes getDemandasRedes() {
		return demandas;
	}
	public void setDemandasRedes(DemandasRedes demandas) {
		this.demandas = demandas;
	}

	public void inicializarValidadores() {
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
		msgserro = new ArrayList<String>();
		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	public String buscarBandeirinhaAutor(){
		return "/minhasdemandas?bandeiraautor=1&faces-redirect=true";
	}
	
	public void buscar(){
		DemandasRedes demandafiltro = new DemandasRedes();

		if(!getCnmnumero().contains("[")){
			demandafiltro.setCnmnumero(getCnmnumero().trim());
		}
		if(getAutor()!= null && getAutor().trim().length()>=1){
			Pessoas p = new Pessoas();
			p.setCnmnome(getAutor().trim());
			demandafiltro.setAutor(p);
		}
		Map<String,Object> filtros = new LinkedHashMap<String,Object>();
		filtros.put("demanda", demandafiltro);
		filtros.put("datainicial", getDatainicial());
		filtros.put("datafinal", getDatafinal());
		filtros.put("filtrostatus", getFiltrostatus());
		
		this.filtros = filtros;
		exibelistagem = true;
	}
	
	public DemandasDataModel getDataModel(){
		return new DemandasDataModel(demandaInt, filtros);
	}
	
	// Recupera uma inst�ncia da Pessoa que autenticou no sistema
	public Pessoas recuperarPessoaLogada(){
		LoginMB lmb = RecuperadorInstanciasBean.recuperarInstanciaLoginBean();
		Pessoas pessoa = lmb.recuperarPessoaLogado();
		return pessoa;
	}
		
	private List<LogsRedes> recuperaLog(Object classeQueSeDesejaPegarLogs){
		
		List<LogsRedes> logs = new ArrayList<LogsRedes>();
		
		if(classeQueSeDesejaPegarLogs instanceof DemandasRedes){
		
			logs = logsInt.recuperarLogsDemanda(demandas);			
		}
											
		return logs;
		
	}
	
	public void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
	}
	
	public void mostraTelaCancelar(){
		showCancelar = true;
	}
	
	public void escondeTelaCancelar(){
		showCancelar = false;
	}
	
	public String dataHoraCorrente(){
		Calendar dataAux = Calendar.getInstance();
		
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dt.format(dataAux.getTime());	
	}
	
	public void salvarGenerico(){
		if(validarCamposObrigatorios()){
			salvar();
		}
	}
	
	public void preEncaminhar() {
		if (validarCamposObrigatorios()) {
			showConfirmaEncaminhar = true;
		}
	}
	
	public void preCancelar(){
		if(StringUtils.isBlank(demandas.getCnmmotivocancelamento())){
			devolveErroParaTela("formcancelademanda:idmotivocancelamento","Campo motivo obrigat�rio.");
		}else{
			showConfirmacaoCancelar = true;
			showCancelar = false;
		}	
	}
	
	public void cancelarSalvar() {
		confirmaSalvar = false;
	}
	
	public void fecharCancelar(){
		showConfirmacaoCancelar = false;
		showCancelar = false;					
	}
	
	public void fecharConfirmaCancelar(){
		showConfirmacaoCancelar = false;
	}	
	
	public void fecharConfirmaEncaminhar(){
		showConfirmaEncaminhar = false;
	}
	
//	public void showRotas(){
//		mostraRotas = true;
//	}
//
//	public void hideRotas() {
//		mostraRotas = false;
//	}
//	
//	public void cancelarExcluirRota() {
//		//orcamentoSelecionado = new Orcamentos();
//		mostraConfirmacaoExclusaoRotas = false;
//	}
	
	public LogsRedes criaLog(Long idTipoLog, Pessoas autor, String descricao,DemandasRedes demanda) {

		LogsRedes log = new LogsRedes();
		log.setAutor(autor);
		log.setDatacadastro(Calendar.getInstance());
		log.setCnmdescricao(descricao);
		log.setTipoLog(new TiposLogRedes(idTipoLog));
		log.setDemanda(demanda);

		return log;
	}
	
	public Set<LogsRedes> adicionaLogDemandas(DemandasRedes demanda, LogsRedes log) {

		Set<LogsRedes> logsSet = new HashSet<LogsRedes>();
		logsSet.add(log);

		if (logsDemanda == null) {
			return logsSet;
		}

		for (LogsRedes logFor : logsDemanda) {
			logsSet.add(logFor);
		}

		return logsSet;
	}
	
	public void cancelarSSI(){
		
		if(demandas.getIddemanda()==null){
			salvar();
		}
		
		demandas.setPessoacomquemesta(null);
		demandas.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_CANCELADA));
		demandas.setAutorcancelamento(recuperarPessoaLogada());
		demandas.setDatacancelamento(Calendar.getInstance());
		
		LogsRedes novoLog = criaLog(idTipoLogDemanda,recuperarPessoaLogada(), mensagemLogSSICancelada,demandas);
		
		novoLog.setDemanda(demandas);
		demandas.setLogs(adicionaLogDemandas(demandas,novoLog));
				
		demandaInt.persistir(demandas);
		
		logsDemanda = recuperaLog(demandas);
		
		fecharConfirmaCancelar();
		
		inicializarValidadores();
		
		indexmb.setMsgpanel("SSI Cancelada com sucesso !");
		indexmb.setPanelexibesucesso(true);
		
		constroirEMailEEnviar(idEnvioEmailCanecelamento);
		
	}
	
	private void constroirEMailEEnviar(int codConstrucao){
		
		String assunto;
		String mensagem;
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		
		switch (codConstrucao) {
		
			case idEnvioEmailCanecelamento:
				
				assunto = "SSI REDES - SSI " + demandas.getCnmnumero() + " cancelada";
				mensagem = "A SSI de registro "+ demandas.getCnmnumero() + " foi cancelada por " + recuperarPessoaLogada().getCnmnome() + ". Justificativa: " + demandas.getCnmmotivocancelamento();
				
				pessoasEnvio.add(recuperarPessoaLogada());
				
				pessoasEnvio.add(demandas.getAutor());
							
				if(demandas.getResponsaveltecnico()!=null){
					pessoasEnvio.add(demandas.getResponsaveltecnico());	
				}
				
				envioEMail(pessoasEnvio,assunto,mensagem);
				
			break;

		
			
		case idEnvioEmailEmissorEncaminhaPrimeiraVez:
			
			assunto = "SSI REDES - SSI " + demandas.getCnmnumero() + " encaminhada";
			mensagem = "A SSI de registro "+ demandas.getCnmnumero() + " foi encaminhada por " + recuperarPessoaLogada().getCnmnome() + ".";
			
			//pessoasEnvio.addAll(participanteInt.recuperarGerentesContratos());
			//pessoasEnvio.add(recuperarPessoaLogada());
									
			envioEMail(pessoasEnvio,assunto,mensagem);			
			
			break;
		}
		
	}
	
	public void envioEMail(List<Pessoas> pessoasEnvio, String assunto, String mensagem){
		
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
		mensageria.enviarMensagem(mensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), 
				demandas.getCnmnumero()!=null ? demandas.getCnmnumero() : "");
							
	}
	
	public void salvar() {
		
		if(demandas.getAutorcancelamento()==null){
			demandas.setAutorcancelamento(null);
		}
		
		if(demandas.getPessoacomquemesta()==null){
			demandas.setPessoacomquemesta(null);
		}
		
		demandas.setCnmtelefone(getCnmtelefonesolicitante());
		
		demandas = demandaInt.persistir(demandas);
		
	}
	
	public void exibeMensagemSucesso(String mensagem){
		inicializarValidadores();
		
		indexmb.setMsgpanel(mensagem);
		indexmb.setPanelexibesucesso(true);
	}
	
	public void salvarComoRascunho(){
		demandas.setRegionaisdemandas(new HashSet<Regionais>(regionaischecked));
		
		LogsRedes novoLog = criaLog(idTipoLogDemanda,recuperarPessoaLogada(), mensagemLogSalvoComoRascunho,demandas);
		
		novoLog.setDemanda(demandas);
		demandas.setLogs(adicionaLogDemandas(demandas,novoLog));
		
		demandas.setPessoacomquemesta(demandas.getAutor());
		
		salvar();
		
		if (demandas.getIddemanda() != null) {
			salvoComSucesso();
		}
		
		demandas.setRotas(new LinkedHashSet<Rotas>(rotasint.recuperarRotasDemanda(demandas)));
		
		logsDemanda = recuperaLog(demandas);
		
		exibeMensagemSucesso(mensagemSSISalvaComoRascunhoComSucesso);

		System.out.println("RETORNA PARA NOTES: " + demandas.isRetornarInformacaoParaNotes());
		//System.out.println("CAMINHO BASE: " + demandas.getAplicacaoNotes().getCaminhobase());
		//System.out.println("NOME AGENTE: " + demandas.getAplicacaoNotes().getNomeagente());
		System.out.println("UNID: " + demandas.getUnid());
		
		if(demandas.isRetornarInformacaoParaNotes()){
			redirecionaParaNotes();			
		}
				
	}

	public void redirecionaParaNotes() {
		if(demandas!= null && demandas.getAplicacaoNotes()!=null && demandas.getAplicacaoNotes().getIdaplicacaonotes() != null){
		
			if(demandas.getAplicacaoNotes().getCaminhobase()!=null && 
					!StringUtils.isBlank(demandas.getAplicacaoNotes().getNomeagente()) &&
					!StringUtils.isBlank(demandas.getUnid())){
				
				String caminho = demandas.getAplicacaoNotes().getCaminhobase() + "/" + demandas.getAplicacaoNotes().getNomeagente() + "?openagent&unid=" + demandas.getUnid();
								
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(caminho);
				} catch (IOException e) {
					System.out.println("---> N�o foi poss�vel voltar para o notes");
					e.printStackTrace();
				}
				
			}				
			
		}
	}
	
	public void salvoComSucesso() {
		confirmaSalvar = false;
	}

	public void fecharSalvoComSucesso() {
		salvoComSucesso = false;
	}
	
	public void setarComQuemEsta(){
		comQuemEsta = "";
		if(demandas.getPessoacomquemesta()!=null){
			comQuemEsta = demandas.getPessoacomquemesta().getCnmnome(); 
		}else if(demandas.getGrupocomquemesta()!=null){
			comQuemEsta = demandas.getGrupocomquemesta().getCnmgrupomodulo();
		}		
		
	}
	
	public boolean validarCamposObrigatorios() {
	
		inicializarValidadores();

		Map<String, Object> camposvalidar = new HashMap<String, Object>();

		if(validarSeRegionaisPreenchidas()){
			msgserro.add("Uma ou mais regional(is)");	
		}
		
		if (demandas.getAreasolicitante() == null) {
			msgserro.add("�rea solicitante");
		}		
		
		if (demandas.getArearequisitada() == null) {
			msgserro.add("�rea requisitada");
		}
		
		if (demandas.getAtividade() == null) {
			msgserro.add("Atividade");
		}
		
		if(demandas.getMotivacao()==null){
			msgserro.add("Motiva��o");
		}
				
		camposvalidar.put("Prazo para execu��o",demandas.getPrazoexecucao());
		camposvalidar.put("Telefone",demandas.getCnmtelefone());
		camposvalidar.put("Objetivo",demandas.getCdsobjetivo());
		camposvalidar.put("�rea de aplica��o",demandas.getCdsareaaplicacao());
		camposvalidar.put("Cen�rio atual",demandas.getCdscenarioatual());
		camposvalidar.put("Cen�rio proposto",demandas.getCdscenarioproposto());
		camposvalidar.put("Atividade para execu��o",demandas.getCdsatividadesexecucao());
		camposvalidar.put("Recursos necess�rios",demandas.getCdsrecursosnecessarios());
		camposvalidar.put("Testes recomentados",demandas.getCdstestesrecomendados());
		
		if(demandas.getFlagtarifacao() && StringUtils.isEmpty(demandas.getCnmdetalhetarifacao())){
			msgserro.add("Detalhes tarifa��o obrigat�rio");
		}

		if(demandas.getFlagsinalizacao() && StringUtils.isEmpty(demandas.getCnmdetalhesinalizacao())){
			msgserro.add("Detalhes sinaliza��o obrigat�rio");
		}
		
		if(demandas.getFlagseguranca() && StringUtils.isEmpty(demandas.getCnmdetalheseguranca())){
			msgserro.add("Detalhes seguran�a obrigat�rio");
		}

		if(demandas.getFlagalarmistica() && StringUtils.isEmpty(demandas.getCnmdetalhealarmistica())){
			msgserro.add("Detalhes alarm�stica obrigat�rio");
		}		

		if(demandas.getFlagdadostrafego() && StringUtils.isEmpty(demandas.getCnmdadostrafego())){
			msgserro.add("Detalhes dados trafego obrigat�rio");
		}		
		
		if(demandas.getFlagavisoimprensa() && StringUtils.isEmpty(demandas.getCnmdetalheavisoimprensa())){
			msgserro.add("Detalhes imprensa obrigat�rio");
		}		
		
		if (camposvalidar.size() >= 1 && camposvalidar != null)
			msgserro.addAll(validadorcamposinvalidos.validarCamposVaziosObjetos(camposvalidar));
		
		if (msgserro.size() != 0) {
			indexmb.setPanelexibeerro(true);
			indexmb.setRenderizalistadeerros(true);
			indexmb.setMsgspanel(msgserro);
			return false;
		}

		System.out.println("VOU RETORNAR TRUE");
		
		return true;
	}

	private boolean validarSeRegionaisPreenchidas() {
		
		for(Regionais regional:demandas.getRegionaisdemandas()){
			if(regional.isChecked()){
				return true;	
			}						
		}
		
		return false;
	}

	public List<DemandasRedes> getDemandaspessoa() {
		return demandaspessoa;
	}

	public void setDemandaspessoa(List<DemandasRedes> demandaspessoa) {
		this.demandaspessoa = demandaspessoa;
	}

	public List<LogsRedes> getLogsDemanda() {
		if(logsDemanda == null || logsDemanda.size()<=0)
			logsDemanda = recuperaLog(demandas);
		return logsDemanda;
	}

	public void setLogsDemanda(List<LogsRedes> logsDemanda) {
		this.logsDemanda = logsDemanda;
	}

	public PessoasMB getPessoasMb() {
		return pessoasMb;
	}

	public void setPessoasMb(PessoasMB pessoasMb) {
		this.pessoasMb = pessoasMb;
	}

	public String getComQuemEsta() {
		return comQuemEsta;
	}

	public void setComQuemEsta(String comQuemEsta) {
		this.comQuemEsta = comQuemEsta;
	}

	public GruposModulos getGm() {
		return gm;
	}

	public void setGm(GruposModulos gm) {
		this.gm = gm;
	}

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}

	public Mensageria getMensageria() {
		return mensageria;
	}

	public void setMensageria(Mensageria mensageria) {
		this.mensageria = mensageria;
	}

	public DemandasRedes getDemandas() {
		return demandas;
	}

	public void setDemandas(DemandasRedes demandas) {
		this.demandas = demandas;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public List<LogsRedes> getLogs() {
		return logs;
	}

	public void setLogs(List<LogsRedes> logs) {
		this.logs = logs;
	}

	public String getComentarioJustificativa() {
		return comentarioJustificativa;
	}

	public void setComentarioJustificativa(String comentarioJustificativa) {
		this.comentarioJustificativa = comentarioJustificativa;
	}

	public Boolean getShowCancelar() {
		return showCancelar;
	}

	public void setShowCancelar(Boolean showCancelar) {
		this.showCancelar = showCancelar;
	}

	public Boolean getShowConfirmacaoCancelar() {
		return showConfirmacaoCancelar;
	}

	public void setShowConfirmacaoCancelar(Boolean showConfirmacaoCancelar) {
		this.showConfirmacaoCancelar = showConfirmacaoCancelar;
	}

	public boolean isShowConfirmaEncaminhar() {
		return showConfirmaEncaminhar;
	}

	public void setShowConfirmaEncaminhar(boolean showConfirmaEncaminhar) {
		this.showConfirmaEncaminhar = showConfirmaEncaminhar;
	}

	public Boolean getConfirmaSalvar() {
		return confirmaSalvar;
	}

	public void setConfirmaSalvar(Boolean confirmaSalvar) {
		this.confirmaSalvar = confirmaSalvar;
	}

	public Boolean getSalvoComSucesso() {
		return salvoComSucesso;
	}

	public void setSalvoComSucesso(Boolean salvoComSucesso) {
		this.salvoComSucesso = salvoComSucesso;
	}

	public ValidadorCamposInvalidos getValidadorcamposinvalidos() {
		return validadorcamposinvalidos;
	}

	public void setValidadorcamposinvalidos(
			ValidadorCamposInvalidos validadorcamposinvalidos) {
		this.validadorcamposinvalidos = validadorcamposinvalidos;
	}

	public IndexMB getIndexmb() {
		return indexmb;
	}

	public void setIndexmb(IndexMB indexmb) {
		this.indexmb = indexmb;
	}

	public List<String> getMsgserro() {
		return msgserro;
	}

	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public Map<String, Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String, Object> filtros) {
		this.filtros = filtros;
	}

	public String getIddemanda() {
		return iddemanda;
	}

	public void setIddemanda(String iddemanda) {
		this.iddemanda = iddemanda;
	}

	public String getCnmnumero() {
		return cnmnumero;
	}

	public void setCnmnumero(String cnmnumero) {
		this.cnmnumero = cnmnumero;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Calendar getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Calendar datainicial) {
		this.datainicial = datainicial;
	}

	public Calendar getDatafinal() {
		return datafinal;
	}

	public void setDatafinal(Calendar datafinal) {
		this.datafinal = datafinal;
	}

	public StatusRedes getFiltrostatus() {
		return filtrostatus;
	}

	public void setFiltrostatus(StatusRedes filtrostatus) {
		this.filtrostatus = filtrostatus;
	}

	public List<Regionais> getRegionaischecked() {
		return regionaischecked;
	}

	public void setRegionaischecked(List<Regionais> regionaischecked) {
		this.regionaischecked = regionaischecked;
	}

	public String getResponsaveltecnicostr() {
		return responsaveltecnicostr;
	}

	public void setResponsaveltecnicostr(String responsaveltecnicostr) {
		this.responsaveltecnicostr = responsaveltecnicostr;
		if(responsaveltecnicostr.trim().length()>=3){
			Pessoas prec = pessoasint.recuperarPorNome(responsaveltecnicostr);
			if(prec!=null && prec.getIdpessoa()!=null)
				this.demandas.setResponsaveltecnico(prec);
		}
	}

	public List<Pessoas> getPessoasJaCopiadas() {
		return pessoasJaCopiadas;
	}

	public void setPessoasJaCopiadas(List<Pessoas> pessoasJaCopiadas) {
		this.pessoasJaCopiadas = pessoasJaCopiadas;
	}

	public String getCnmtelefonesolicitante() {
		return cnmtelefonesolicitante;
	}

	public void setCnmtelefonesolicitante(String cnmtelefonesolicitante) {
		this.cnmtelefonesolicitante = cnmtelefonesolicitante;
	}

	public Atividades getAtividadeselecionada() {
		return atividadeselecionada;
	}

	public void setAtividadeselecionada(Atividades atividadeselecionada) {
		this.atividadeselecionada = atividadeselecionada;
	}
	
	
		
}
