package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.compras.common.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.compras.domain.AtividadesCronogramas;
import com.ssi.kernel.compras.domain.CapexOpex;
import com.ssi.kernel.compras.domain.Cronogramas;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Orcamentos;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.compras.domain.Prioridades;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.compras.domain.TiposAditivo;
import com.ssi.kernel.compras.domain.TiposCondicionamento;
import com.ssi.kernel.compras.domain.TiposLog;
import com.ssi.compras.common.interfaces.CapexOpexInt;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.LogsInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.compras.common.interfaces.StatusInt;
import com.ssi.compras.common.interfaces.TipoAditivoInt;
import com.ssi.compras.common.interfaces.TipoCondicionamentoInt;
import com.ssi.compras.common.interfaces.TiposLogInt;
import com.ssi.compras.common.interfaces.DemandasServiceInt;
import com.ssi.compras.common.interfaces.FeriadosServiceInt;
import com.ssi.compras.common.interfaces.utils.DataUtilsInt;
import com.ssi.kernel.controller.interfaces.FeriadosInt;
import com.ssi.kernel.controller.interfaces.FornecedoresInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.OrcamentosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.controller.interfaces.UfsInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Feriados;
import com.ssi.kernel.model.Fornecedores;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Telefones;
import com.ssi.kernel.model.Ufs;
import com.ssi.kernel.utils.Mensageria;

@ManagedBean
@ViewScoped
public class DemandasMB implements Serializable {

	private static final long serialVersionUID = 4468503734082103168L;
	
	private static final long idTipoTelefoneCelular = 3L;

	private static final long idPrioridadeCritica = 10L;
	private static final long idTipoCondicionamentoTecnologia = 1L;

	private static final long idTipoLogDemanda = 1L;
	private static final long idTipoLogOrcamento = 2L;
	
	//ID ENVIOS DE E-MAIL
	private static final int idEnvioEmailCanecelamento = 1;
	private static final int idEnvioEmailIndicaAlteraAnalistaContrato = 2;
	private static final int idEnvioEmailEmissorEncaminhaPrimeiraVez = 3;
		
	private static final String mensagemLogEncaminhado = "Documento encaminhado";
	private static final String mensagemLogSalvoComoRascunho = "Documento salvo como rascunho";
	private static final String mensagemOrcamentoExcluidoComSucesso = "Compra exclu�da com sucesso !";
	private static final String mensagemCompraSalvaComoRascunhoComSucesso = "Compra salva com sucesso !";
	private static final String mensagemUFJaIncluida = "Essa UF j� foi inclu�da, n�o pode ser inclu�da novamente.";
	private static final String mensagemUFDeveSerPreenchida = "A UF deve ser preenchida.";
	private static final String mensagemRcCadastrada = "RC cadastrada com sucesso";
	private static final String mensagemCompraEncaminhada = "Compra encaminhada com sucesso";
	
	private static final String mensagemLogSSICancelada = "Compra cancelada";
	private static final String analistaDeOrcamentoIndicadoComSucesso = "Analista de or�amento indicado com sucesso";
	
	@EJB
	private StatusInt statusInt;

	@EJB
	private LogsInt logsInt;
	
	@EJB
	private TipoCondicionamentoInt tipoCondicionamentoInt;

	@EJB
	private PessoasInt pessoasInt;

	@EJB
	private DemandaInt demandaInt;

	@EJB
	private CapexOpexInt capexpexint;

	@EJB
	private TipoAditivoInt tipoAditivoInt;

	@EJB
	private UfsInt ufsint;

	@EJB
	private TiposLogInt tiposLogInt;

	@EJB
	private FeriadosInt feriadosInt;
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private GruposModulosInt grupoModulosInt;	

	@EJB
	private OrcamentosInt orcamentosInt;	
	
	@EJB
	private EventosInt eventosint;
			
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private FornecedoresInt fornecedoresint;
	
	@EJB
	private FeriadosServiceInt feriadosService;

	@EJB
	private DataUtilsInt dataUtilsInt;

	@EJB
	private DemandasServiceInt demandasService;	
	
	private List<Demandas> demandaspessoa;	
	
	private PessoasMB pessoasMb;
	
	private String labelMenuIndicacaoAlteracaoAnalistaContrato;
	private Pessoas analistaContratoAnterior;
	
	private String ano1;
	private String ano2;
	private String ano3;
	private String ano4;
	
	private String comQuemEsta;
	
	private GruposModulos gm;
	private Modulos modulo;
	private Mensageria mensageria = new Mensageria();
	
	private Demandas demandas;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private List<CapexOpex> capexOpex = new ArrayList<CapexOpex>();
	private List<TiposAditivo> tiposAditivos = new ArrayList<TiposAditivo>();
	private List<Orcamentos> orcamentosOpex = new ArrayList<Orcamentos>();
	private List<Orcamentos> orcamentoOpexNaoAtivos = new ArrayList<Orcamentos>();
	private List<Cronogramas> cronogramas = new ArrayList<Cronogramas>();

	private List<TiposCondicionamento> tiposCondicionamentos;
	
	private List<Logs> logsDemanda;
	
	private List<Pessoas> analistasDeContrato;

	private String comentarioJustificativa; //campo utilizado para os coment�rios de diversos modais uma vez que esse coment�rio depois vai para evento

	private Ufs ufSelecionada;
	private Orcamentos orcamentoSelecionado;
	
	private Orcamentos orcamentoCapex = new Orcamentos();
	
	private Boolean showCancelar = false;
	private Boolean showConfirmacaoCancelar = false;
	private Boolean showConfirmaIndicacaoAnalistaDeContrato = false;
	
	private boolean showConfirmaEncaminhar = false;
	
	private BigDecimal totalDaCompra;
	
	private Boolean editandoOrcamento = false;
	private Boolean podeEditarRC = false;

	// Controles de popup
	private Boolean confirmaSalvar = false;
	private Boolean salvoComSucesso = false;
	private Boolean mostraOrcamento = false;
	private Boolean mostraOrcamentoCapex = false;
	private Boolean mostraConfirmacaoExclusaoOrcamento = false;
	private Boolean mostraConfirmacaoExclusaoOrcamentoCapex = false;
	private Boolean mostraIndicaAnalistaContrato = false;	

	private ValidadorCamposInvalidos validadorcamposinvalidos;

	private IndexMB indexmb;
	private List<String> msgserro;

	private boolean exibelistagem;
	
	// Usado na sele��o pelo autocomplete
	private String responsaveltecnicostr;
	private String fornecedorstr;
	
	// Usado na busca
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	// Filtros
	private String iddemanda;
	private String cnmnumero;
	private String autor;
	private Calendar datainicial;
	private Calendar datafinal;
	private Status filtrostatus;
		
	public DemandasMB() {}
	
	@PostConstruct
	public void init() {				
		
		modulo = modulosint.recuperarUnico(336L);
		
		gm = new GruposModulos();
		gm.setIdgrupomodulo(8L);
		gm.setModulo(modulo);
		
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
		
		capexOpex = capexpexint.recuperar();
		tiposAditivos = tipoAditivoInt.recuperar();
		
		Calendar diaCorrente = Calendar.getInstance();
		Integer anoCorrente = diaCorrente.get(Calendar.YEAR);
		
		ano1 = Integer.toString(anoCorrente);
		ano2 = Integer.toString(anoCorrente+1);
		ano3 = Integer.toString(anoCorrente+2);
		ano4 = Integer.toString(anoCorrente+3);
						
		if (demandas == null) {
			demandas = new Demandas();
			demandas.setStatus(statusInt.recuperarUnico(new Status(Status.ID_DEMANDA_RASCUNHO)));
			demandas.setAutor(recuperarPessoaLogada());
			Calendar cal = Calendar.getInstance();
			demandas.setDatacadastro(cal);
			demandas.setPrioridade(new Prioridades(idPrioridadeCritica));
			demandas.setTipoCondicionamento(new TiposCondicionamento(idTipoCondicionamentoTecnologia));
			criarOrcamentoCapex();
			demandas.setAutorcancelamento(null);
			demandas.setAutorquestionamento(null);
			demandas.setAnalistaDeAquisicaoResponsavel(null);
			demandas.setAnalistaDeContratoResponsavel(null);
			demandas.setResponsaveltecnico(null);
			demandas.setFornecedor(null);
			demandas.setLp(null);
			demandas.setBooladitivodecontrato(false);
			recuperarTelefoneSolicitante();
			demandas.setBoolobjetodacompracondicionada(true);
			demandas.setPessoacomquemesta(recuperarPessoaLogada());
			 					
		}else{
			boolean existeorcamento = false;
			if(demandas.getOrcamentos()!=null){
				
				boolean anoPreenchido = false;
				
				for(Orcamentos orcamento:recuperarOrcamentosSalvos("1")){
					
					if(anoPreenchido==false){
						ano1 = orcamento.getCnmano1();
						ano2 = orcamento.getCnmano2();
						ano3 = orcamento.getCnmano3();
						ano4 = orcamento.getCnmano4();
						anoPreenchido = true;
					}
					
					if(orcamento.getUfs()==null){
						orcamentoCapex = orcamento; 
					}else{												
						orcamentosOpex.add(orcamento);
					}
					
					existeorcamento = true;
				}
				
				somarValoresCapexOpex();
				
				setaOrcamentosInativosParaSeremPersistidosAoSalvar();
				
				setaChecksSalvosCapexOpex();
				
				setaChecksTiposAditivos();
				
				if(demandas.getResponsaveltecnico()!=null){					
					responsaveltecnicostr = demandas.getResponsaveltecnico().getCnmnome();	
				}
				
				if(demandas.getFornecedor()!=null){
					fornecedorstr = demandas.getFornecedor().getCnmfornecedor();	
				}
				
			}
			
			if(!existeorcamento){
				criarOrcamentoCapex();
			}
			
		}
				
		if(demandaspessoa == null){
			demandaspessoa = new ArrayList<Demandas>();
		}
		
		if(demandas.getIddemanda() != null){
			logsDemanda = recuperaLog(demandas);	
		}
		
		ufSelecionada = new Ufs();
		orcamentoSelecionado = new Orcamentos();
		validadorcamposinvalidos = new ValidadorCamposInvalidos();
		analistasDeContrato = new ArrayList<Pessoas>();
		
		setaLabelMenu();
		setarComQuemEsta();
		
		// Recupera a Sess�o para eliminar o list de propostas checadas caso exista
		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessao.remove("propostaschecked");

	}
	
	private void recuperarTelefoneSolicitante() {
		demandas.setCnmtelefonesolicitante(recuperarTelefonePrincipal(pessoasInt.retornarTelefonesPessoa(demandas.getAutor()))); 
	}
	
	public String mostrarMinhasPendencias(){
		return "/minhaspendencias?faces-redirect=true";
	}

	private void setaChecksTiposAditivos() {
		
		for(TiposAditivo tipo:demandaInt.recuperaTiposAditivos(demandas)){
			for(TiposAditivo tipoAditivo:tiposAditivos){
				if(tipo.equals(tipoAditivo)){
					tipoAditivo.setChecked(true);
				}
			}
		}		
	}

	private void setaChecksSalvosCapexOpex() {
		for(CapexOpex capexOpexDemanda:demandaInt.recuperarCapexOpex(demandas)){
			for(CapexOpex capOpex:capexOpex){
				if(capexOpexDemanda.equals(capOpex)){
					capOpex.setChecked(true);
				}
			}
		}
	}
	
	private void setaLabelMenu() {
		analistaContratoAnterior = new Pessoas();
		labelMenuIndicacaoAlteracaoAnalistaContrato = "Indicar Analista de Contrato";
		if(demandas.getAnalistaDeContratoResponsavel()!=null){
			analistaContratoAnterior = demandas.getAnalistaDeContratoResponsavel(); 
			labelMenuIndicacaoAlteracaoAnalistaContrato = "Alterar Analista de Contrato";	
		}		
	}

	public void alteraOrcamentoComRC(Orcamentos orcamentoComRC){
		  
		if(orcamentoComRC.getUfs()==null){
			
			orcamentoCapex.setCnmrc(orcamentoComRC.getCnmrc());
			
		}else{
			for(Orcamentos orcamentoOpex:orcamentosOpex){			
				if(orcamentoOpex.getUfs().equals(orcamentoComRC.getUfs())){
					orcamentoOpex.setCnmrc(orcamentoComRC.getCnmrc());
				}			
			}
			
			Logs novoLog = criaLog(idTipoLogDemanda,recuperarPessoaLogada(), mensagemRcCadastrada);
			
			novoLog.setDemanda(demandas);
			demandas.setLogs(adcionaLogDemandas(demandas,novoLog));
				
		}
				
		salvar();
				  			  			 
	}
	
	
	private void setaOrcamentosInativosParaSeremPersistidosAoSalvar() {
		orcamentoOpexNaoAtivos = recuperarOrcamentosSalvos("0");		
	}

	private List<Orcamentos> recuperarOrcamentosSalvos(String ativoInativo){
		return orcamentosInt.recuperarOrcamentosDaDemanda(demandas, ativoInativo);
	}
	
	public String buscarBandeirinhaAutor(){
		return "/minhasdemandas?bandeiraautor=1&faces-redirect=true";
	}
	
	public void buscar(){
		Demandas demandafiltro = new Demandas();

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
	
	public boolean isCapex() {
		return validaCapexOpexChecked(CapexOpex.ID_CAPEX);
	}

	public boolean isOpex() {
		return validaCapexOpexChecked(CapexOpex.ID_OPEX);
	}

	private boolean validaCapexOpexChecked(Long idCapexOpex) {
		if (capexOpex.isEmpty()) {
			return false;
		}

		for (CapexOpex capOp : capexOpex) {						
			if (capOp.getChecked()
					&& capOp.getIdcapexopex().equals(idCapexOpex)) {
				return true;
			}
		}
		return false;
	}

	private boolean validaTipoDeAditivoChecked() {

		for (TiposAditivo tiposAditivo : tiposAditivos) {
			if (tiposAditivo != null && tiposAditivo.getChecked() != null && tiposAditivo.getChecked()) {
				return true;
			}
		}

		return false;
	}

	public void criarOrcamentoCapex() {
		orcamentoCapex = new Orcamentos();

		orcamentoCapex.setUfs(null);

		orcamentoCapex.setAutor(recuperarPessoaLogada());
		orcamentoCapex.setDatacadastro(Calendar.getInstance());
		orcamentoCapex.setCapexOpex(new CapexOpex(CapexOpex.ID_CAPEX));

		orcamentoCapex.setDemandas(demandas);
		
		orcamentoCapex.setCnmano1(ano1);
		orcamentoCapex.setCnmano2(ano2);
		orcamentoCapex.setCnmano3(ano3);
		orcamentoCapex.setCnmano4(ano4);
		
		orcamentoCapex.setNnrano1(new BigDecimal(0));
		orcamentoCapex.setNnrano2(new BigDecimal(0));
		orcamentoCapex.setNnrano3(new BigDecimal(0));
		orcamentoCapex.setNnrano4(new BigDecimal(0));
		orcamentoCapex.setNnrdemaisanos(new BigDecimal(0));
		
		orcamentoCapex.setFlagativo("1");

	}

	public void inicializarValidadores() {
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
		msgserro = new ArrayList<String>();
	}

	public void preExcluirOrcamento(Orcamentos orcamento) {
		orcamentoSelecionado = orcamento;
		mostraConfirmacaoExclusaoOrcamento = true;
	}

	public void cancelarExcluirOrcamento() {
		orcamentoSelecionado = new Orcamentos();
		mostraConfirmacaoExclusaoOrcamento = false;
	}

	public void hideExcluirOrcamento() {
		mostraConfirmacaoExclusaoOrcamento = false;
	}

	public void cancelarSalvar() {
		confirmaSalvar = false;
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
		
	public void preCancelar(){
		
		if(StringUtils.isBlank(demandas.getCnmmotivocancelamento())){
			
			devolveErroParaTela("formcancelademanda:idmotivocancelamento","Campo motivo obrigat�rio.");
			
		}else{
			showConfirmacaoCancelar = true;
			showCancelar = false;
		}
				
	}
	
	public void preIndicarAnalistaDeContrato(){
		
		if(demandas.getAnalistaDeContratoResponsavel()==null){		
			
			devolveErroParaTela("demandaform:analistadecontrato","Analista � obrigat�rio.");
			
		}else if(StringUtils.isBlank(comentarioJustificativa)){
			
			devolveErroParaTela("demandaform:idcomentarioanalistaorcamento","Coment�rio � obrigat�rio.");
			
		}else{
			
			demandas.setAnalistaDeContratoResponsavel(pessoasInt.recuperarUnico(demandas.getAnalistaDeContratoResponsavel().getIdpessoa()));			
						
			fecharTelaIndicaAnalistaDeContrato();
			showConfirmaIndicacaoAnalistaDeContrato = true;
		}		
		
	}
	
	public void indicarAnalistaDeContrato(){
		
		Logs novoLog = criaLog(idTipoLogDemanda,recuperarPessoaLogada(), analistaDeOrcamentoIndicadoComSucesso);
		
		novoLog.setDemanda(demandas);
		demandas.setLogs(adcionaLogDemandas(demandas,novoLog));
				
		Eventos eventoIndicaAnalistaContrato = new Eventos(51,1);
		
		eventoIndicaAnalistaContrato.setAutor(recuperarPessoaLogada());
		eventoIndicaAnalistaContrato.setDemanda(demandas);
		eventoIndicaAnalistaContrato.setStatus(demandas.getStatus());
		eventoIndicaAnalistaContrato.setStatusanterior(demandas.getStatus());
		eventoIndicaAnalistaContrato.getEindanalistacontrato().setEventoindicaanalistacontrato(eventoIndicaAnalistaContrato); // Faz o relacionamento inverso pro cascade funcionar
		eventoIndicaAnalistaContrato.getEindanalistacontrato().setPessoaindicada(demandas.getAnalistaDeContratoResponsavel());
		eventosint.registrar(eventoIndicaAnalistaContrato);
				
		constroirEMailEEnviar(idEnvioEmailIndicaAlteraAnalistaContrato);
		
		demandaInt.alterar(demandas);
		
		logsDemanda = recuperaLog(demandas);
				
		setaLabelMenu();
		
		fecharTelaIndicaAnalistaDeContrato();
				
		inicializarValidadores();
		
		indexmb.setMsgpanel("Analista indicado com sucesso !");
		indexmb.setPanelexibesucesso(true);		
		
	}

	public void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
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
	
	public void fecharTelaIndicaAnalistaDeContrato(){
		mostraIndicaAnalistaContrato = false;
		showConfirmaIndicacaoAnalistaDeContrato = false;
		
	}
		
	public void cancelarSSI(){
				
		if(demandas.getIddemanda()==null){
			salvar();
		}
		
		demandas.setPessoacomquemesta(null);
		demandas.setStatus(new Status(Status.ID_DEMANDA_CANCELADA));
		demandas.setAutorcancelamento(recuperarPessoaLogada());
		demandas.setDatacancelamento(Calendar.getInstance());
		
		Logs novoLog = criaLog(idTipoLogDemanda,recuperarPessoaLogada(), mensagemLogSSICancelada);
		
		novoLog.setDemanda(demandas);
		demandas.setLogs(adcionaLogDemandas(demandas,novoLog));
				
		demandaInt.alterar(demandas);
		
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
			
			assunto = "SSI COMPRAS - SSI " + demandas.getCnmnumero() + " cancelada";
			mensagem = "A SSI de registro "+ demandas.getCnmnumero() + " foi cancelada por " + recuperarPessoaLogada().getCnmnome() + ". Justificativa: " + demandas.getCnmmotivocancelamento();
			
			pessoasEnvio.add(recuperarPessoaLogada());
			if(demandas.getAnalistaDeAquisicaoResponsavel()!=null){
				pessoasEnvio.add(demandas.getAnalistaDeAquisicaoResponsavel());	
			}
			if(demandas.getResponsaveltecnico()!=null){
				pessoasEnvio.add(demandas.getResponsaveltecnico());	
			}
			
			pessoasEnvio.add(demandas.getAutor());
			
			if(demandas.getAnalistaDeContratoResponsavel()!=null){
				pessoasEnvio.add(demandas.getAnalistaDeContratoResponsavel());	
			}
									
			envioEMail(pessoasEnvio,assunto,mensagem);
			
			break;

		case idEnvioEmailIndicaAlteraAnalistaContrato:
			
			if(analistaContratoAnterior != null){
				assunto = "SSI COMPRAS - SSI " + demandas.getCnmnumero() + " - Altera��o de Analista de Contrato";
				mensagem = "A SSI de registro "+ demandas.getCnmnumero() + " teve a indica��o do analista de contrato alterada por " + recuperarPessoaLogada().getCnmnome() + "."
						+ "Analista anterior: " + analistaContratoAnterior.getCnmnome() + "."
						+ "Analista atual: " + demandas.getAnalistaDeContratoResponsavel().getCnmnome();				
			}else{
				assunto = "SSI COMPRAS - SSI " + demandas.getCnmnumero() + " - Indica��o de Analista de Contrato";
				mensagem = "A SSI de registro "+ demandas.getCnmnumero() + " teve a indica��o do analista de contrato conclu�da por " + recuperarPessoaLogada().getCnmnome() + "."
						+ "Analista indicado: " + demandas.getAnalistaDeContratoResponsavel().getCnmnome();
			}
						
			pessoasEnvio.add(recuperarPessoaLogada());
			pessoasEnvio.add(demandas.getAnalistaDeContratoResponsavel());	
						
			pessoasEnvio.add(demandas.getAutor());
			
			if(demandas.getAnalistaDeAquisicaoResponsavel()!=null){
				pessoasEnvio.add(demandas.getAnalistaDeAquisicaoResponsavel());	
			}
			
			if(analistaContratoAnterior != null){
				pessoasEnvio.add(analistaContratoAnterior);
			}
									
			envioEMail(pessoasEnvio,assunto,mensagem);			
			
			break;
			
		case idEnvioEmailEmissorEncaminhaPrimeiraVez:
			
			assunto = "SSI COMPRAS - SSI " + demandas.getCnmnumero() + " encaminhada";
			mensagem = "A SSI de registro "+ demandas.getCnmnumero() + " foi encaminhada por " + recuperarPessoaLogada().getCnmnome() + ".";
			
			pessoasEnvio.addAll(participanteInt.recuperarGerentesContratos());
			pessoasEnvio.add(recuperarPessoaLogada());
			
			if(demandas.getAnalistaDeAquisicaoResponsavel()!=null){
				pessoasEnvio.add(demandas.getAnalistaDeAquisicaoResponsavel());	
			}
			if(demandas.getResponsaveltecnico()!=null){
				pessoasEnvio.add(demandas.getResponsaveltecnico());	
			}
			
			if(demandas.getAnalistaDeContratoResponsavel()!=null){
				pessoasEnvio.add(demandas.getAnalistaDeContratoResponsavel());	
			}
									
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
		
	public void mostarTelaIndicaAnalistaDeContrato(){
		preencheAnalistaDeContrato();
		mostraIndicaAnalistaContrato = true;
	}
	
	private void preencheAnalistaDeContrato() {
				
		analistasDeContrato = participanteInt.recupearAnalistasContratos();			
		
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
	
	public void preEncaminhar() {
		if (validarCamposObrigatorios()) {
			showConfirmaEncaminhar = true;
		}
	}

	public void encaminhar(){
				
		Calendar cal = Calendar.getInstance();

		if(demandas.getStatus().getIdstatus().equals(Status.ID_DEMANDA_RASCUNHO)){
						 
			 Demandas ultimaDemandaComNumero = demandaInt.recuperaUltimaDemandaComNumero();
			
			if(ultimaDemandaComNumero == null){
				ultimaDemandaComNumero = new Demandas();			
				ultimaDemandaComNumero.setNnrultimonumero(1);	
			}
			
			demandas.setCnmnumero("C"
					+ StringUtils.leftPad(ultimaDemandaComNumero.getNnrultimonumero().toString(), 6, '0')
					+ "/"
					+ StringUtils.leftPad(Integer.toString(cal.get(Calendar.MONTH)+1), 2, '0')
					+ StringUtils.right(Integer.toString(cal.get(Calendar.YEAR)), 2));

			demandas.setNnrultimonumero(ultimaDemandaComNumero.getNnrultimonumero() + 1);
			demandas.setCronogramas(criaCronograma());
		}
		
		if(demandas.getStatusanterior()==null){
			demandas.setStatus(new Status(Status.ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO));	
		}else{
			Status statusCorrente = new Status(demandas.getStatus().getIdstatus());
			demandas.setStatus(demandas.getStatusanterior());
			demandas.setStatusanterior(statusCorrente);
		}	

		Logs novoLog = criaLog(idTipoLogDemanda,recuperarPessoaLogada(), mensagemLogEncaminhado);
		
		novoLog.setDemanda(demandas);
		demandas.setLogs(adcionaLogDemandas(demandas,novoLog));						
		
		demandas = demandasService.setaGrupoComQuemEsta(new Papeis(Papeis.ID_GERENTE_AQUISICAO),demandas,null);
		
		salvar();
		
		demandas = demandaInt.recuperarDemandaEspecifica(demandas.getIddemanda());
		demandas.setLogs(new HashSet<Logs>(logsInt.recuperarLogsDemanda(demandas)));
		logsDemanda = new ArrayList<Logs>(demandas.getLogs());
				
		showConfirmaEncaminhar = false;
		
		constroirEMailEEnviar(idEnvioEmailEmissorEncaminhaPrimeiraVez);
		
		inicializarValidadores();
		
		setarComQuemEsta();
		
		pessoasMb = RecuperadorInstanciasBean.recuperarInstanciaPessoasBean();		
		pessoasMb.atualizarDemandasComigo(recuperarPessoaLogada());
		pessoasMb.atualizarDemandasMinhas(recuperarPessoaLogada());
		
		indexmb.setPanelexibesucesso(true);
		indexmb.setMsgpanel(mensagemCompraEncaminhada);
		
	}
		
	public Demandas eliminaGrupoEPessoaComQuemEsta(Demandas demanda){
		demanda.setGrupocomquemesta(null);
		demanda.setPessoacomquemesta(null);
		return demanda;
	}
	
	public Set<Logs> adcionaLogDemandas(Demandas demanda, Logs log) {

		Set<Logs> logsSet = new HashSet<Logs>();
		logsSet.add(log);

		if (logsDemanda == null) {
			return logsSet;
		}

		for (Logs logFor : logsDemanda) {
			logsSet.add(logFor);
		}

		return logsSet;
	}

	private List<Logs> recuperaLog(Object classeQueSeDesejaPegarLogs){
		
		List<Logs> logs = new ArrayList<Logs>();
		
		if(classeQueSeDesejaPegarLogs instanceof Demandas){
		
			logs = logsInt.recuperarLogsDemanda(demandas);			
		}
											
		return logs;
		
	}
	
	private Set<Cronogramas> criaCronograma() {

		/*
		 * Emiss�o SSI - D Valida��o Aquisi��es - D+3 Envio ao Mercado - D+6
		 * Recebimento Propostas - D+15 Emiss�o Laudo/Ditame - D+28 Valida��o
		 * Laudo/Ditame - D+30 Adjudica��o - D+45
		 * 
		 * Os prazos acima est�o sendo calculados conforme a data final de cada
		 * entrada no cronograma. Se temos uma entrada onde � D+6 e a entrada
		 * anterior era D+3, a entrada D+6 n�o ser� crescida de D+6 e sim D+3
		 * pois � o que falta para completar os 6 dias.
		 */

		Set<Cronogramas> cronogramas = new HashSet<Cronogramas>();

		Cronogramas cronograma;
		Calendar dataInicial = Calendar.getInstance();
		Calendar dataFinal = Calendar.getInstance();
		Calendar dataFinalAuxAdjudicacao = Calendar.getInstance();

		List<Ufs> ufs = new ArrayList<Ufs>();
		ufs.add(new Ufs(Feriados.ID_CIDADE_FERIADO_LOCAL_PADRAO));
		
		List<Feriados> feriados = feriadosService.retornaFeriadosNacionaisELocais(7, ufs);

		for (Long i = 1L; i <= 7; i++) {
			cronograma = new Cronogramas();
			cronograma.setDemandascronograma(demandas);

			switch (Integer.parseInt(i.toString())) {
			case 1:
				cronograma.setDatarealizacao(Calendar.getInstance());
				dataFinal.setTime(dataInicial.getTime());
				cronograma = incluirDatasEAtividades(cronograma, dataFinal,dataFinal, Orcamentos.ID_EMISSAO_SSI);
				cronograma.setResponsavel(demandas.getAutor());
				break;
			case 2:				
				dataFinal.setTime(retornaDataFinalAtividadeCronograma(feriados,dataFinal, 3).getTime());
				cronograma = incluirDatasEAtividades(cronograma, dataFinal,null, Orcamentos.ID_VALIDACAO_SSI);
				cronograma.setDataprevisaoatual(cronograma.getDataprevisaooriginal());
				break;
			case 3:				
				dataFinal.setTime(retornaDataFinalAtividadeCronograma(feriados,dataFinal, 3).getTime());
				cronograma = incluirDatasEAtividades(cronograma, dataFinal,null, Orcamentos.ID_ENVIO_RFP_MERCADO);
				cronograma.setDataprevisaoatual(cronograma.getDataprevisaooriginal());
				break;
			case 4:
				dataFinal.setTime(retornaDataFinalAtividadeCronograma(feriados,dataFinal, 7).getTime());
				cronograma = incluirDatasEAtividades(cronograma, dataFinal,null, Orcamentos.ID_RECEBIMENTO_PROPOSTAS);
				cronograma.setDataprevisaoatual(cronograma.getDataprevisaooriginal());
				break;
			case 5:
				dataFinal.setTime(retornaDataFinalAtividadeCronograma(feriados,dataFinal, 13).getTime());
				cronograma = incluirDatasEAtividades(cronograma, dataFinal,null, Orcamentos.ID_EMISSAO_LAUDO_TECNICO);
				cronograma.setDataprevisaoatual(cronograma.getDataprevisaooriginal());
				break;
			case 6:
				dataFinal.setTime(retornaDataFinalAtividadeCronograma(feriados,dataFinal, 2).getTime());
				cronograma = incluirDatasEAtividades(cronograma, dataFinal,null, Orcamentos.ID_VALIDACAO_LAUDO_TECNICO);
				cronograma.setDataprevisaoatual(cronograma.getDataprevisaooriginal());
				break;
			case 7:
				dataFinalAuxAdjudicacao.setTime(retornaDataFinalAtividadeCronograma(feriados,dataFinal, 15).getTime());
				dataFinal.setTime(retornaProximoDiaUtilDoDiaDaSemana(feriados,dataFinalAuxAdjudicacao,new int[]{Calendar.TUESDAY,Calendar.THURSDAY}).getTime());
				cronograma = incluirDatasEAtividades(cronograma, dataFinal,null, Orcamentos.ID_ADJUDICACAO);
				cronograma.setDataprevisaoatual(cronograma.getDataprevisaooriginal());
				break;
			}

			cronogramas.add(cronograma);
		}

		return cronogramas;
	}

	private Calendar retornaDataFinalAtividadeCronograma(
			List<Feriados> feriados, Calendar dataInicio, int qtdDias) {

		int contadorDia = 1;
		Calendar dataAux = Calendar.getInstance();
		dataAux.setTime(dataInicio.getTime());
		while (contadorDia <= qtdDias) {

			if (feriadosService.isDataFeriado(feriados, dataAux) == false
					&& dataUtilsInt.isFimDeSemana(dataAux) == false) {
				contadorDia++;
			}

			dataAux.setTime(dataUtilsInt.adicionaDias(dataAux.getTime(), 1));
		}

		if (feriadosService.isDataFeriado(feriados, dataAux) == false
				&& dataUtilsInt.isFimDeSemana(dataAux) == false) {
			return dataAux;
		} else {
			return retornaProximoDiaUtil(feriados, dataAux);
		}

	}

	private Calendar retornaProximoDiaUtil(List<Feriados> feriados,
			Calendar data) {
								
		while (feriadosService.isDataFeriado(feriados, data) || dataUtilsInt.isFimDeSemana(data)) {
			data.setTime(dataUtilsInt.adicionaDias(data.getTime(), 1));
		}

		return data;

	}
	
	private Calendar retornaProximoDiaUtilDoDiaDaSemana(List<Feriados> feriados, Calendar data,int... diasDaSemanaUteis){
		
		int resultadoComparacaoDiaSemana;				
		boolean dataEncontrada = false;
		
		while (dataEncontrada!=true) {
			
			for (int i = 0; i < diasDaSemanaUteis.length; i++) {
				
				resultadoComparacaoDiaSemana = Integer.compare(diasDaSemanaUteis[i],data.get(Calendar.DAY_OF_WEEK));
				
				if(resultadoComparacaoDiaSemana==0){
					if(feriadosService.isDataFeriado(feriados, data)==false && dataUtilsInt.isFimDeSemana(data)==false){
						dataEncontrada = true;
						break;
					}else{
						data.setTime(dataUtilsInt.adicionaDias(data.getTime(), 1));
					}
				}else{
					data.setTime(dataUtilsInt.adicionaDias(data.getTime(), 1));	
				}
													
			}			
		}
		
		return data;		
		
	}

	private Cronogramas incluirDatasEAtividades(Cronogramas cronograma,
			Calendar dataInicial, Calendar dataFinal, Long idAtividade) {

		cronograma.setAtividade(new AtividadesCronogramas(idAtividade));

		Calendar dataAux = Calendar.getInstance();
		Calendar dataAux2 = Calendar.getInstance();

		dataAux.setTime(dataInicial.getTime());
		cronograma.setDataprevisaooriginal(dataAux);
		if(dataFinal!=null){
			dataAux2.setTime(dataFinal.getTime());
			cronograma.setDataprevisaoatual(dataAux2);			
		}

		return cronograma;
	}
	
	
	private boolean validarCamposObrigatorios() {

		inicializarValidadores();

		Map<String, Object> camposvalidar = new HashMap<String, Object>();

		camposvalidar.put("Data inicial de contrata��o",demandas.getDatainiciocontratacao());
		camposvalidar.put("Data fim de contrata��o",demandas.getDatafimcontratacao());
		camposvalidar.put("Telefone do solicitante", demandas.getCnmtelefonesolicitante());
		camposvalidar.put("Telefone do respons�vel t�cnico",demandas.getCnmtelefoneresponsaveltecnico());
		//camposvalidar.put("Contrata��o anterior",demandas.getCnmssicontratacaoanterior());
		camposvalidar.put("Resumo do escopo/justificativa",demandas.getCnmresumodoescopo());
		camposvalidar.put("Data de aprova��o da CIT e/ou CDT",demandas.getDataaprovacaocitcdt());
		
		if (!isCapex() && !isOpex()) {
			msgserro.add("Capex, Opex ou os dois");
		}

		if (isCapex()) {
			/* SOMENTE PARA CAPEX */
			camposvalidar.put("Mega projeto", demandas.getCnmmegaprojeto());
			camposvalidar.put("A��o inversora", demandas.getCnmacaoinversora());

		}

		if (demandas.getResponsaveltecnico() == null) {
			msgserro.add("Respons�vel t�cnico");
		}

		if (demandas.getDatainiciocontratacao() != null && demandas.getDatafimcontratacao() != null) {
			if (demandas.getDatainiciocontratacao().after(demandas.getDatafimcontratacao())) {
				msgserro.add("A data de in�cio de contrata��o deve ser maior que a data de fim de contrata��o e");
			}
		}

		if (demandas.getFornecedor() == null) {
			msgserro.add("Fornecedor");
		}

		if (demandas.getBooladitivodecontrato()) {
			/* SOMENTE PARA ADITIVO DE CONTRATO */
			camposvalidar.put("Quantidade de contratos a serem aditivados",demandas.getNnrcontratosaseremaditivados());

			if (!validaTipoDeAditivoChecked()) {
				msgserro.add("Aditivo de Contrato");
			}

			camposvalidar.put("N�mero de contrato SAP",demandas.getCnmnumerodecontratosap());
		}

		if (camposvalidar.size() >= 1 && camposvalidar != null)
			msgserro.addAll(validadorcamposinvalidos.validarCamposVaziosObjetos(camposvalidar));

		if (msgserro.size() != 0) {
			indexmb.setPanelexibeerro(true);
			indexmb.setRenderizalistadeerros(true);
			indexmb.setMsgspanel(msgserro);
			return false;
		}

		return true;
	}

	public void salvoComSucesso() {
		confirmaSalvar = false;
	}

	public void fecharSalvoComSucesso() {
		salvoComSucesso = false;
	}

	public Boolean getEditandoOrcamento() {
		return editandoOrcamento;
	}

	public void setEditandoOrcamento(Boolean editandoOrcamento) {
		this.editandoOrcamento = editandoOrcamento;
	}
		
	public Boolean getPodeEditarRC() {
		return podeEditarRC;
	}

	public void setPodeEditarRC(Boolean podeEditarRC) {
		this.podeEditarRC = podeEditarRC;
	}

	public Boolean getMostraOrcamento() {
		return mostraOrcamento;
	}

	public void setMostraOrcamento(Boolean mostraOrcamento) {
		this.mostraOrcamento = mostraOrcamento;
	}

	public Boolean getMostraConfirmacaoExclusaoOrcamento() {
		return mostraConfirmacaoExclusaoOrcamento;
	}

	public void setMostraConfirmacaoExclusaoOrcamento(
			Boolean mostraConfirmacaoExclusaoOrcamento) {
		this.mostraConfirmacaoExclusaoOrcamento = mostraConfirmacaoExclusaoOrcamento;
	}
		
	public Boolean getMostraIndicaAnalistaContrato() {
		return mostraIndicaAnalistaContrato;
	}

	public void setMostraIndicaAnalistaContrato(Boolean mostraIndicaAnalistaContrato) {
		this.mostraIndicaAnalistaContrato = mostraIndicaAnalistaContrato;
	}

	public void preIncluirOrcamento(){
		
		mostraOrcamento = false;		
		
		if (validaInclusaoUfOrcamento()) {
			orcamentoSelecionado = new Orcamentos();
			incluirOrcamento();
		}
						
	}
	
	public void setaUf(ValueChangeEvent event){		
		
		ufSelecionada = (Ufs) event.getNewValue();
		
	}
		
	public void incluirOrcamento() {
		
		editandoOrcamento = false;
		orcamentoSelecionado = new Orcamentos();

		if (ufSelecionada.getCnmuf() == null) {
			orcamentoSelecionado.setUfs(ufsint.recuperaUnico(ufSelecionada));
		} else {
			orcamentoSelecionado.setUfs(ufSelecionada);
		}

		orcamentoSelecionado.setAutor(recuperarPessoaLogada());
		orcamentoSelecionado.setDatacadastro(Calendar.getInstance());
		orcamentoSelecionado.setCapexOpex(new CapexOpex(CapexOpex.ID_OPEX));
		orcamentoSelecionado.setDemandas(demandas);
		
		orcamentoSelecionado.setCnmano1(ano1);
		orcamentoSelecionado.setCnmano2(ano2);
		orcamentoSelecionado.setCnmano3(ano3);
		orcamentoSelecionado.setCnmano4(ano4);
		orcamentoSelecionado.setNnrano1(new BigDecimal(0));
		orcamentoSelecionado.setNnrano2(new BigDecimal(0));
		orcamentoSelecionado.setNnrano3(new BigDecimal(0));
		orcamentoSelecionado.setNnrano4(new BigDecimal(0));
		orcamentoSelecionado.setNnrdemaisanos(new BigDecimal(0));
		orcamentoSelecionado.setFlagativo("1");
		
		mostraOrcamento = true;

	}

	private boolean validaInclusaoUfOrcamento() {
		
		if (ufSelecionada == null) {
			devolveErroParaTela("demandaform:ufs",mensagemUFDeveSerPreenchida);
			return false;
		}

		for (Orcamentos orcamento : orcamentosOpex) {
			if (ufSelecionada.equals(orcamento.getUfs()) && orcamento.isFlagativo()) {
				devolveErroParaTela("demandaform:ufs",mensagemUFJaIncluida);
				return false;
			}
		}

		return true;
	}

	public void somarValoresCapexOpex(){
		
		BigDecimal valorOpex = new BigDecimal(0);
		BigDecimal valorCapex = new BigDecimal(0);

		if (orcamentosOpex != null) {
			for (Orcamentos orcamento : orcamentosOpex) {
				if(orcamento.isFlagativo()){
					valorOpex = valorOpex.add(retornarValoresCapexOpexSomados(orcamento));	
				}
			}
		}
		
		if(orcamentoCapex != null){
			valorCapex = retornarValoresCapexOpexSomados(orcamentoCapex);						
		}
		
		totalDaCompra = valorOpex.add(valorCapex);

	}

	private BigDecimal retornarValoresCapexOpexSomados(Orcamentos orcamento) {
		BigDecimal valorInicial = new BigDecimal(0);
		BigDecimal valor1 = new BigDecimal(0);
		BigDecimal valor2 = new BigDecimal(0);
		BigDecimal valor3 = new BigDecimal(0);
		BigDecimal valor4 = new BigDecimal(0);
		BigDecimal valor5 = new BigDecimal(0);
		
		if(orcamento!= null){
			if(orcamento.getNnrano1()!=null){
				valor1 = new BigDecimal(orcamento.getNnrano1().toString());	
			}
			if(orcamento.getNnrano1()!=null){
				valor2 = new BigDecimal(orcamento.getNnrano2().toString());	
			}
			if(orcamento.getNnrano1()!=null){
				valor3 = new BigDecimal(orcamento.getNnrano3().toString());	
			}
			
			if(orcamento.getNnrano1()!=null){
				valor4 = new BigDecimal(orcamento.getNnrano4().toString());	
			}
			if(orcamento.getNnrdemaisanos()!=null){
				valor5 = new BigDecimal(orcamento.getNnrdemaisanos().toString());	
			}			
			
			valorInicial = valor1.add(valor2);
			valorInicial = valorInicial.add(valor3);
			valorInicial = valorInicial.add(valor4);
			valorInicial = valorInicial.add(valor5);
		}

		return valorInicial;
	}
	
	public BigDecimal getTotalDaCompra() {
		return totalDaCompra;
	}

	public void setTotalDaCompra(BigDecimal totalDaCompra) {
		this.totalDaCompra = totalDaCompra;
	}

	public void showOrcamento() {
		mostraOrcamento = true;
	}
	
	public void showOrcamentoCapex(){
		mostraOrcamentoCapex = true;
	}

	public void hideOrcamento() {
		mostraOrcamento = false;
	}
	
	public void hideOrcamentoCapex() {
		mostraOrcamentoCapex = false;
	}

	//TODO Falta pegar o autor e hora da �ltima altera��o
	public String autorUltimaAlteracaoOrcamento() {
		return "";
	}

	public String dataUltimaAlteracaoOrcamento() {
		return "";
	}
	
	public void salvarComoRascunho(){
		
		Logs novoLog = criaLog(idTipoLogDemanda,recuperarPessoaLogada(), mensagemLogSalvoComoRascunho);
		
		novoLog.setDemanda(demandas);
		//demandas.setLogs(adcionaLogDemandas(demandas,novoLog));
		
		demandas.setPessoacomquemesta(demandas.getAutor());
		
		salvar();
		
		if (demandas.getIddemanda() != null) {
			salvoComSucesso();
		}
		
		logsDemanda = recuperaLog(demandas);
		
		inicializarValidadores();
		
		indexmb.setMsgpanel(mensagemCompraSalvaComoRascunhoComSucesso);
		indexmb.setPanelexibesucesso(true);	
		
	}
	
	public void salvarGenerico(){
		if(validarCamposObrigatorios()){
			salvar();
		}
	}

	public void salvar() {

		demandas.setTiposAditivo(pegarSelecionadosTiposAditivos(tiposAditivos));		
		
		if(demandas.getAutorquestionamento()==null){
			demandas.setAutorquestionamento(null);
		}
		
		if(demandas.getAnalistaDeAquisicaoResponsavel()==null){
			
			demandas.setAnalistaDeAquisicaoResponsavel(null);
		}
		
		if(demandas.getAnalistaDeContratoResponsavel()==null){
			demandas.setAnalistaDeContratoResponsavel(null);
		}
		
		if(demandas.getAutorcancelamento()==null){
			demandas.setAutorcancelamento(null);
		}
		
		if(demandas.getResponsaveltecnico()==null){
			demandas.setResponsaveltecnico(null);
		}
		
		if(demandas.getLp()==null){
			demandas.setLp(null);
		}
						
		if(demandas.getFornecedor()==null){
			demandas.setFornecedor(null);
		}
		
		if(demandas.getPessoacomquemesta()==null){
			demandas.setPessoacomquemesta(null);
		}
		
		demandas.setCapexOpex(pegarSelecionadosCapexOpex());

		Set<Orcamentos> orcamentosParaSalvar = new HashSet<Orcamentos>();

		// Pegando os or�amentos Opex
		if (isOpex()) {
			desabilitandoOpexAlteradas();
			for (Orcamentos orcamento : orcamentosOpex) {
				orcamentosParaSalvar.add(orcamento);
			}
			
			for (Orcamentos orcamentoNaoAtivo:orcamentoOpexNaoAtivos){
				orcamentosParaSalvar.add(orcamentoNaoAtivo);
			}
		}

		// Pegando o or�amento Capex
		if (isCapex()) {
			orcamentosParaSalvar.add(orcamentoCapex);
		}
	
		if(orcamentosParaSalvar.isEmpty()){
			demandas.setOrcamentos(null);
		}else{
			demandas.setOrcamentos(orcamentosParaSalvar);	
		}
		
		if (demandas.getIddemanda() == null) {
			demandas = demandaInt.incluir(demandas);
		} else {
			demandaInt.alterar(demandas);
		}
		
		atualizaOpexAtivasParaTela();
		setaOrcamentosInativosParaSeremPersistidosAoSalvar();

	}

	private void atualizaOpexAtivasParaTela() {
		orcamentosOpex = new ArrayList<Orcamentos>();
		for(Orcamentos orcamento:recuperarOrcamentosSalvos("1")){						
			if(orcamento.getUfs()!=null){							
				orcamentosOpex.add(orcamento);
			}
		}
		
	}

	private void desabilitandoOpexAlteradas() {
		
		if(demandas.getIddemanda()!=null){
		
			List<Orcamentos> orcamentosSalvos = orcamentosInt.recuperarOrcamentosDaDemanda(demandas,"1");
			
			List<Orcamentos> novosParaSalvar = new ArrayList<Orcamentos>();
			
			for(Orcamentos orcamentoParaSalvar:orcamentosOpex){
				for(Orcamentos orcamentosSalvo:orcamentosSalvos){
					if(orcamentosSalvo.getUfs()!=null && orcamentoParaSalvar.getUfs()!=null){
						if(orcamentosSalvo.getUfs().equals(orcamentoParaSalvar.getUfs())
								&& isOrcamentosIguais(orcamentosSalvo,orcamentoParaSalvar)==false){
												
								Orcamentos orcamentoAntigo = new Orcamentos(orcamentosSalvo);
								orcamentoAntigo.setFlagativo("0");
								novosParaSalvar.add(orcamentoAntigo);										
						}	
					}
				}	
			}				
			
			orcamentosOpex.addAll(novosParaSalvar);			
			
		}
				
	}
	
	private boolean isOrcamentosIguais(Orcamentos orcamentoSalvo,Orcamentos orcamentoParaSalvar){
		
		if(orcamentoSalvo.getNnrano1().equals(orcamentoParaSalvar.getNnrano1())
			&& orcamentoSalvo.getNnrano2().equals(orcamentoParaSalvar.getNnrano2())
			&& orcamentoSalvo.getNnrano3().equals(orcamentoParaSalvar.getNnrano3())
			&& orcamentoSalvo.getNnrano4().equals(orcamentoParaSalvar.getNnrano4())
			&& orcamentoSalvo.getNnrdemaisanos().equals(orcamentoParaSalvar.getNnrdemaisanos())
			){
			return true;
		}
						
		return false;
	}

	public void alterarOrcamento(Orcamentos orcamento) {				
		editandoOrcamento = true;		
		mostraOrcamento = true;
		orcamentoSelecionado = orcamento;			
	}
	
	public void excluirOrcamento() {

		List<Orcamentos> novaListaOrcamentos = new ArrayList<Orcamentos>();

		for (Orcamentos orcamento : orcamentosOpex) {
			if (!orcamento.equals(orcamentoSelecionado)) {
				novaListaOrcamentos.add(orcamento);
			}
		}

		orcamentosOpex.clear();
		orcamentosOpex = novaListaOrcamentos;
		mostraConfirmacaoExclusaoOrcamento = false;
		somarValoresCapexOpex();
		
		inicializarValidadores();
		
		indexmb.setMsgpanel(mensagemOrcamentoExcluidoComSucesso);
		indexmb.setPanelexibesucesso(true);

	}

	public void salvarOrcamentoCapex(){
		
		somarValoresCapexOpex();
		
		hideOrcamentoCapex();
		
		orcamentoSelecionado = new Orcamentos();
		
		
	}
	
	public void salvarOrcamento() {
		
		if(validaCamposOrcamento()){			
			
			String descricaoLog;
			descricaoLog = "Or�amento criado";
			if (editandoOrcamento) {
				descricaoLog = "Or�amento alterado";
			}

			Logs log = criaLog(idTipoLogOrcamento, demandas.getAutor(), descricaoLog);
			orcamentoSelecionado.setLogs(adcionaLogOrcamento(orcamentoSelecionado,log));
			
			if(orcamentoSelecionado.getCnmrc()==null){
				orcamentoSelecionado.setCnmrc("");
			}

			if (editandoOrcamento == false) {
				orcamentosOpex.add(orcamentoSelecionado);
			}
			
			editandoOrcamento = false;
			
			hideOrcamento();
			
			somarValoresCapexOpex();			
						
		}
		
	}
	
	private boolean validaCamposOrcamento() {		
		
		if(StringUtils.isBlank(orcamentoSelecionado.getCnmcontabil())){
			devolveErroParaTela("formorcamentoopex:idcustocontabil","Custo cont�bil tem que ser preenchido");
			return false;
		}

		if(StringUtils.isBlank(orcamentoSelecionado.getCnmcusto())){
			devolveErroParaTela("formorcamentoopex:idcusto","Custo deve ser preenchido");
			return false;
		}		
		
		return true;
	}
	
	public Logs criaLog(Long idTipoLog, Pessoas autor, String descricao) {

		Logs log = new Logs();
		log.setAutor(autor);
		log.setDatacadastro(Calendar.getInstance());
		log.setCnmdescricao(descricao);
		log.setTipoLog(new TiposLog(idTipoLog));

		return log;
	}

	public void buscatelefoneresponsaveltecnico() {

		demandas.setCnmtelefoneresponsaveltecnico("");

		if (demandas.getResponsaveltecnico() != null) {

			demandas.setCnmtelefoneresponsaveltecnico(recuperarTelefonePrincipal(pessoasInt.retornarTelefonesPessoa(demandas.getResponsaveltecnico())));

		}

	}

	private Set<Logs> adcionaLogOrcamento(Orcamentos orcamento, Logs log) {

		Set<Logs> logsSet = new HashSet<Logs>();
		logsSet.add(log);

		if (orcamento.getLogs() == null) {
			return logsSet;
		}

		for (Logs logFor : orcamento.getLogs()) {
			logsSet.add(logFor);
		}

		return logsSet;
	}


	public List<Logs> getLogsOrcamento() {

		List<Logs> logs = new ArrayList<Logs>();

		if (orcamentoSelecionado.getLogs() == null) {
			return logs;
		}

		for (Logs log : orcamentoSelecionado.getLogs()) {
			logs.add(log);
		}
		return logs;

	}

	private Set<CapexOpex> pegarSelecionadosCapexOpex() {
		Set<CapexOpex> capexOpexSelecionado = new HashSet<CapexOpex>();
		
		if (isCapex()) {
			capexOpexSelecionado.add(capexpexint.recuperarPorId(CapexOpex.ID_CAPEX));
		}

		if (isOpex()) {
			capexOpexSelecionado.add(capexpexint.recuperarPorId(CapexOpex.ID_OPEX));
		}

		return capexOpexSelecionado;
	}
	
	private Set<TiposAditivo> pegarSelecionadosTiposAditivos(
			List<TiposAditivo> listagemTiposAditivos) {
		Set<TiposAditivo> tiposAditivosSelecionado = new HashSet<TiposAditivo>();

		for (TiposAditivo tipoAditivo : listagemTiposAditivos) {
			if(tipoAditivo.getChecked()!=null){
				if (tipoAditivo.getChecked().equals(true)) {
					tiposAditivosSelecionado.add(tipoAditivo);
				}	
			}
		}

		return tiposAditivosSelecionado;
	}

	public Demandas getDemandas() {
		return demandas;
	}

	public void setDemandas(Demandas demandas) {
		this.demandas = demandas;
	}

	public List<Cronogramas> getCronogramas() {

		if (cronogramas.isEmpty() && demandas.getCronogramas() != null) {
			for (Cronogramas cronograma : demandas.getCronogramas()) {
				cronogramas.add(cronograma);
			}

			Collections.sort(cronogramas, new Comparator<Cronogramas>() {
				public int compare(Cronogramas c1, Cronogramas c2) {
					return c1.getAtividade().getIdatividade()
							.compareTo(c2.getAtividade().getIdatividade());
				}
			});
		}

		return cronogramas;
	}

	public void setCronogramas(List<Cronogramas> cronogramas) {
		this.cronogramas = cronogramas;
	}

	public List<TiposCondicionamento> getTiposCondicionamentos() {
		if (tiposCondicionamentos == null) {
			tiposCondicionamentos = tipoCondicionamentoInt.recuperar();
		}
		return tiposCondicionamentos;
	}

	public void setTiposCondicionamentos(
			List<TiposCondicionamento> tiposCondicionamentos) {
		this.tiposCondicionamentos = tiposCondicionamentos;
	}

	public Boolean getSinalizador() {
		if (demandas.getIddemanda() == null) {
			return false;
		}
		return demandas.getStatus().getBoolsinalizador();
	}

	public String getDataHoraCriacaoOrcamento() {
		if (orcamentoSelecionado.getIdorcamento() == null) {
			Calendar cal = Calendar.getInstance();
			return dateFormat.format(cal.getTime());
		} else {
			Calendar cal = orcamentoSelecionado.getDatacadastro();
			return dateFormat.format(cal.getTime());
		}
	}

	private String recuperarTelefonePrincipal(List<Telefones> telefones) {
		
		return pessoasInt.retornaTelefone(telefones,idTipoTelefoneCelular);

	}

	public void setarComQuemEsta(){
		comQuemEsta = "";
		if(demandas.getPessoacomquemesta()!=null){
			comQuemEsta = demandas.getPessoacomquemesta().getCnmnome(); 
		}else if(demandas.getGrupocomquemesta()!=null){
			comQuemEsta = demandas.getGrupocomquemesta().getCnmgrupomodulo();
		}		
		
	}
	
	public List<CapexOpex> getCapexOpex() {
		if (capexOpex.isEmpty()) {
			capexOpex = capexpexint.recuperar();
		}

		return capexOpex;
	}

	public void setCapexOpex(List<CapexOpex> capexOpex) {
		this.capexOpex = capexOpex;
	}

	public List<TiposAditivo> getTiposAditivos() {
		return tiposAditivos;
	}

	public void setTiposAditivos(List<TiposAditivo> tiposAditivos) {
		this.tiposAditivos = tiposAditivos;
	}

	public List<String> getMsgserro() {
		return msgserro;
	}

	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
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

	public Ufs getUfSelecionada() {
		return ufSelecionada;
	}

	public void setUfSelecionada(Ufs ufSelecionada) {
		this.ufSelecionada = ufSelecionada;
	}

	public List<Orcamentos> getOrcamentosOpex() {
		return orcamentosOpex;
	}

	public void setOrcamentosOpex(List<Orcamentos> orcamentos) {
		this.orcamentosOpex = orcamentos;
	}

	public Orcamentos getOrcamentoSelecionado() {
		return orcamentoSelecionado;
	}

	public void setOrcamentoSelecionado(Orcamentos orcamentoSelecionado) {
		this.orcamentoSelecionado = orcamentoSelecionado;
	}

	public Orcamentos getOrcamentoCapex() {
		return orcamentoCapex;
	}

	public void setOrcamentoCapex(Orcamentos orcamentoCapex) {
		this.orcamentoCapex = orcamentoCapex;
	}

	public ValidadorCamposInvalidos getValidadorcamposinvalidos() {
		return validadorcamposinvalidos;
	}

	public void setValidadorcamposinvalidos(
			ValidadorCamposInvalidos validadorcamposinvalidos) {
		this.validadorcamposinvalidos = validadorcamposinvalidos;
	}

	public List<Logs> getLogsDemanda() {
		if(logsDemanda == null || logsDemanda.size()<=0)
			logsDemanda = recuperaLog(demandas);
		return logsDemanda;
	}

	public void setLogsDemanda(List<Logs> logsDemanda) {
		this.logsDemanda = logsDemanda;
	}
	
	public List<Demandas> getDemandaspessoa() {
		return demandaspessoa;
	}

	public void setDemandaspessoa(List<Demandas> demandaspessoa) {
		this.demandaspessoa = demandaspessoa;
	}

	public Map<String,Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}

	public String getIddemanda() {
		return iddemanda;
	}

	public void setIddemanda(String iddemanda) {
		this.iddemanda = iddemanda;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public String getComentarioJustificativa() {
		return comentarioJustificativa;
	}

	public void setComentarioJustificativa(String comentarioJustificativa) {
		this.comentarioJustificativa = comentarioJustificativa;
	}

	public Boolean getShowConfirmaIndicacaoAnalistaDeContrato() {
		return showConfirmaIndicacaoAnalistaDeContrato;
	}

	public void setShowConfirmaIndicacaoAnalistaDeContrato(
			Boolean showConfirmaIndicacaoAnalistaDeContrato) {
		this.showConfirmaIndicacaoAnalistaDeContrato = showConfirmaIndicacaoAnalistaDeContrato;
	}

	public List<Orcamentos> getOrcamentoOpexNaoAtivos() {
		return orcamentoOpexNaoAtivos;
	}

	public void setOrcamentoOpexNaoAtivos(List<Orcamentos> orcamentoOpexNaoAtivos) {
		this.orcamentoOpexNaoAtivos = orcamentoOpexNaoAtivos;
	}
		
	public List<Pessoas> getAnalistasDeContrato() {
		return analistasDeContrato;
	}

	public void setAnalistasDeContrato(List<Pessoas> analistasDeContrato) {
		this.analistasDeContrato = analistasDeContrato;
	}

	public String getAno1() {
		return ano1;
	}

	public void setAno1(String ano1) {
		this.ano1 = ano1;
	}

	public String getAno2() {
		return ano2;
	}

	public void setAno2(String ano2) {
		this.ano2 = ano2;
	}

	public String getAno3() {
		return ano3;
	}

	public void setAno3(String ano3) {
		this.ano3 = ano3;
	}

	public String getAno4() {
		return ano4;
	}

	public void setAno4(String ano4) {
		this.ano4 = ano4;
	}

	public boolean isShowConfirmaEncaminhar() {
		return showConfirmaEncaminhar;
	}

	public void setShowConfirmaEncaminhar(boolean showConfirmaEncaminhar) {
		this.showConfirmaEncaminhar = showConfirmaEncaminhar;
	}

	public String getLabelMenuIndicacaoAlteracaoAnalistaContrato() {
		return labelMenuIndicacaoAlteracaoAnalistaContrato;
	}

	public void setLabelMenuIndicacaoAlteracaoAnalistaContrato(
			String labelMenuIndicacaoAlteracaoAnalistaContrato) {
		this.labelMenuIndicacaoAlteracaoAnalistaContrato = labelMenuIndicacaoAlteracaoAnalistaContrato;
	}
	
	public Boolean getMostraOrcamentoCapex() {
		return mostraOrcamentoCapex;
	}

	public void setMostraOrcamentoCapex(Boolean mostraOrcamentoCapex) {
		this.mostraOrcamentoCapex = mostraOrcamentoCapex;
	}

	public Boolean getMostraConfirmacaoExclusaoOrcamentoCapex() {
		return mostraConfirmacaoExclusaoOrcamentoCapex;
	}

	public void setMostraConfirmacaoExclusaoOrcamentoCapex(
			Boolean mostraConfirmacaoExclusaoOrcamentoCapex) {
		this.mostraConfirmacaoExclusaoOrcamentoCapex = mostraConfirmacaoExclusaoOrcamentoCapex;
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

	public Status getFiltrostatus() {
		return filtrostatus;
	}

	public void setFiltrostatus(Status filtrostatus) {
		this.filtrostatus = filtrostatus;
	}

	public String getComQuemEsta() {
		return comQuemEsta;
	}

	public void setComQuemEsta(String comQuemEsta) {
		this.comQuemEsta = comQuemEsta;
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

	public String getFornecedorstr() {
		return fornecedorstr;
	}

	public void setFornecedorstr(String fornecedorstr) {
		this.fornecedorstr = fornecedorstr;
		if(fornecedorstr.trim().length()>=3){
			Fornecedores fornrec = fornecedoresint.recuperarPorNome(fornecedorstr);
			if(fornrec!=null && fornrec.getIdfornecedor()!=null && fornrec.getIdfornecedor()>=1)
				this.demandas.setFornecedor(fornrec);
		}
	}
	
	
			
}
