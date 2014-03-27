package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.ssi.compras.common.interfaces.AgendamentosInt;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.FornecedoresAgendamentosInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.compras.common.interfaces.PropostasInt;
import com.ssi.compras.common.interfaces.StatusInt;
import com.ssi.kernel.compras.domain.Agendamentos;
import com.ssi.kernel.compras.domain.Cronogramas;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.EventosAnaliseGerenteCompra;
import com.ssi.kernel.compras.domain.FornecedoresAgendamentos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Propostas;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Fornecedores;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.Mensageria;


@ManagedBean
@ViewScoped
public class AgendamentosMB implements Serializable {

	private static final long serialVersionUID = -3143758020583021795L;

	@EJB
	private DemandaInt demandaint;
		
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private StatusInt statusInt;
	
	@EJB	
	private PropostasInt propostaint;	
	
	@EJB
	private EventosInt eventosint;

	@EJB	
	private FornecedoresAgendamentosInt fornecedorAgendamentoInt;	
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private AgendamentosInt agendamentosInt;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private static final long idStatusNovo = 39L;
	private static final long idStatusAgendado = 40L;
	private static final long idStatusConcluido = 41L;
	private static final long idStatusNaoAdjudicado = 46L;
	
	private static final long idTipoLogDemanda = 1L;
	
	private static final long idStatusCompraAdjudicada=45L;
	
	private static final long idAtividadeAdjudicacao = 10L;	
	
	private static final String msgLogMesaDeCompraAgendada = "Mesa de compra agendada";
	private static final String msgLogMesaDeCompraAdjudicada = "Mesa de compra adjudicada";
	private static final String msgLogMesaDeCompraConcluida = "Mesa de compra conclu�da";
	
	private String mensagemTelaConfirmacao;
		
	private boolean exibeMesaDeCompra = false;
	private boolean exibeConfirmacaoMesaDeCompra = false;
	
	private Agendamentos agendamentoSelecionado;
	private boolean agendamentoMesaCompra = false;
	
	private List<Agendamentos> agendamentos = new ArrayList<Agendamentos>();
	private List<Fornecedores> fornecedoresAprovados = new ArrayList<Fornecedores>();
	
	private List<FornecedoresAgendamentos> fornecedoresAgendamentos = new ArrayList<FornecedoresAgendamentos>();
	
	@PostConstruct
	public void init(){
				
		if(demanda ==null){
			demanda = demandasmb.getDemandas();	
		}
		
		recuperaAgendamentos();	
		recuperarAgendamentosFornecedores();
		
		recuperarFornecedoresAprovados();
		preencherFornecedoresAgendamentos();
						
	}

	private void recuperarFornecedoresAprovados() {
		
		for(Propostas proposta:propostaint.recuperarPropostasAprovadas(demanda)){
			fornecedoresAprovados.add(proposta.getFornecedorprop());
		}
		
	}
	private void recuperarAgendamentosFornecedores() {
		
		for(Agendamentos agendamento:agendamentos){
			agendamento.setAgendamentosfornecedores(new HashSet<FornecedoresAgendamentos>(fornecedorAgendamentoInt.recuperarFornecedoresAgendamentos(agendamento)));			
		}
		
	}
	
	public int getQtdFornecedoresSelecionados(){
		int qtd=0;
		
		for(FornecedoresAgendamentos fornAgendamentos:fornecedoresAgendamentos){
			if(fornAgendamentos.isChecked()){
				qtd++;
			}
		}			
		
		
		return qtd;
	}
	
	private void recuperaAgendamentos() {
		agendamentos = agendamentosInt.recuperarAgendamentos(demanda);		
	}
				
	private void exibeTela(){
		exibeMesaDeCompra = true;
	}
	
	public void fechaTela(){
		exibeMesaDeCompra = false;
	}
	
	public void exibeTelaConfirmacao(){
		exibeConfirmacaoMesaDeCompra = true;
	}
	
	public void fechaTelaConfirmacao(){
		exibeConfirmacaoMesaDeCompra = false;
	}
	
	public boolean isPodeAgendar(){
		if(agendamentoSelecionado!=null){
			if(agendamentoSelecionado.getStatus().getIdstatus().equals(idStatusNovo)){
				return true;
			}	
		}
		
		return false;
	}	
	
	public boolean isPodeConcluir(){
		if(agendamentoSelecionado!=null){
			if(agendamentoSelecionado.getStatus().getIdstatus().equals(idStatusAgendado)){
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean isPodeSalvar(){
		if(agendamentoSelecionado!=null){
			if(agendamentoSelecionado.getStatus().getIdstatus().equals(idStatusConcluido)){
				return false;
			}	
		}
		
		return true;
	}	
	
	public void agendarConcluir(boolean agendar){
						
			if(agendar){
				if(validaCamposSalvar()){
					setaStatus(idStatusAgendado);
					mensagemTelaConfirmacao = "Confirma o agendamento ?";
				}else{
					return;
				}
			}else{
				if(validarCamposConcluir()){
					if(isAdjudicacaoTotal()){
						setaStatusDemanda(idStatusCompraAdjudicada);
						registrarDataAdjudicacaoCronograma();
					}
						
					setaStatus(idStatusConcluido);
					mensagemTelaConfirmacao = "Confirma a conclus�o ?";
															
				}else{
					return;
				}
			}
						
			fechaTela();
			exibeTelaConfirmacao();
	}
	
	

	private void preencherFornecedoresAgendamentos() {

		fornecedoresAgendamentos = new ArrayList<FornecedoresAgendamentos>();
		
		boolean fornecedorEncontrado;
		for(Fornecedores fornecedor:fornecedoresAprovados){
			fornecedorEncontrado = false;
			if(agendamentoSelecionado!=null){
				for(FornecedoresAgendamentos fornAgendamentos:agendamentoSelecionado.getAgendamentosfornecedores()){
					if(fornAgendamentos.getFornecedores().equals(fornecedor)){
						fornecedorEncontrado = true;
						fornecedoresAgendamentos.add(new FornecedoresAgendamentos(fornecedor, agendamentoSelecionado, fornAgendamentos.getNnrvaloradjudicado(),true));	
					}
				}				
			}
			
			if(fornecedorEncontrado==false){
				fornecedoresAgendamentos.add(new FornecedoresAgendamentos(fornecedor, agendamentoSelecionado, new BigDecimal(0),false));
			}			
			
		}			
				
	}
	private void registrarDataAdjudicacaoCronograma() {
		
		Calendar cal = Calendar.getInstance();
		
		for(Cronogramas cronograma:demanda.getCronogramas()){
			if(cronograma.getAtividade().getIdatividade().equals(idAtividadeAdjudicacao)){
				cronograma.setDatarealizacao(cal);
				cronograma.setResponsavel(demandasmb.recuperarPessoaLogada());				
			}
		}
	}

	private boolean isAdjudicacaoTotal() {
		if(agendamentoSelecionado.getBooladjudicacao()){
			return true;
		}
		return false;
	}

	public void cadastrarAlterar(Agendamentos agendamento){
		
		if(agendamento == null){
			agendamentoMesaCompra = true;
			iniciaAgendamento();
		}else{
			agendamentoMesaCompra = false;
			agendamentoSelecionado = agendamento;
			preencherFornecedoresAgendamentos();
		}
		
		exibeTela();		
	}
	
	public void consultaAgendamento(Agendamentos agendamento){
		agendamentoSelecionado = agendamento;
		preencherFornecedoresAgendamentos();
		exibeTela();		
	}
	
	private void iniciaAgendamento() {
		agendamentoSelecionado = new Agendamentos();
		agendamentoSelecionado.setAutor(demandasmb.recuperarPessoaLogada());
		Calendar cal = Calendar.getInstance();
		agendamentoSelecionado.setDatacadastro(cal);
		agendamentoSelecionado.setBooladjudicacao(false);
		agendamentoSelecionado.setBoolhouveadjudicacao(false);
		agendamentoSelecionado.setBoolmesarealizada(false);
		agendamentoSelecionado.setNnrhoramesa(1);
		agendamentoSelecionado.setNnrminutomesa(1);
		agendamentoSelecionado.setDemanda(demanda);
		agendamentoSelecionado.setAgendamentosfornecedores(new HashSet<FornecedoresAgendamentos>());		
		setaStatus(idStatusNovo);
	}
	
	private void setaStatus(Long idStatus){		
		agendamentoSelecionado.setStatus(statusInt.recuperarUnico(new Status(idStatus)));		
	}
	
	private void setaStatusDemanda(Long idStatus){
		demanda.setStatus(new Status(idStatus));
	}
		
	public void preSalvar(){
		
		if(validaCamposSalvar()){
			fechaTela();
			mensagemTelaConfirmacao = "Confirma a grava��o do agendamento ?";
			exibeTelaConfirmacao();
		}		
		
	}
	
	public void preConcluir(){
		if(validaCamposSalvar() && validarCamposConcluir()){
			fechaTela();
			exibeTelaConfirmacao();
		}		
	}
	
	public void preAgendar(){
		if(validaCamposSalvar()){
			fechaTela();
			exibeTelaConfirmacao();
		}				
	}
	
	public void gravar(){
		
		Set<FornecedoresAgendamentos> fornecedoresAgendamentosParaPersistir = new HashSet<FornecedoresAgendamentos>();
		
		for(FornecedoresAgendamentos fornAgendamentos:fornecedoresAgendamentos){
			if(fornAgendamentos.isChecked()){								
				fornAgendamentos.setCheckedPersistido(false);
				fornecedoresAgendamentosParaPersistir.add(fornAgendamentos);
			}			
		}
		
		salvarFornecedoresAgendadosNaoPersistidos(excluirAlterarFornecedoresAgendamentosPersistidos(fornecedoresAgendamentosParaPersistir));			
		
		gravarLogDemanda();
		
		agendamentos = retornaAgendamentosAtualizados();
		
		if(demanda.getStatus().getIdstatus().equals(idStatusCompraAdjudicada)){
			finalizarMesasNaoConcluidas();
			enviaEmail();
		}
		
		demanda.setAgendamentos(new HashSet<Agendamentos>(agendamentos));
		demanda = demandaint.alterar(demanda);
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
						
		recuperaAgendamentos();
		recuperarAgendamentosFornecedores();
		
		fechaTelaConfirmacao();
		
	}
	
	private void enviaEmail() {
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		
		//pessoa logada
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
		
		//Solicitante
		pessoasEnvio.add(demanda.getAutor());				
				
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
		
		//Gerente de contratos		
		pessoasEnvio.addAll(participanteInt.recuperarGerentesContratos());
		
		//Gerente de aquisi��o
		pessoasEnvio.addAll(participanteInt.recuperarGerentesAquisicoes());		
				
		//Comprador
		EventosAnaliseGerenteCompra eventosAnaliseGerenteCompra = eventosint.recuperaUltimoEventosAnaliseGerenteComprasComCompradorIndicado(demanda);
		if(eventosAnaliseGerenteCompra!=null){
			pessoasEnvio.add(eventosAnaliseGerenteCompra.getComprador());	
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
		String assunto = "SSI COMPRAS - Mesa de Compra Adjudicada";
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" teve a mesa de compra adjudicada.";
				
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;
	
		
	}
	
	private List<Agendamentos> retornaAgendamentosAtualizados() {
		List<Agendamentos> agendamentosList = new ArrayList<Agendamentos>();
		for(Agendamentos agendamento:agendamentos){
			if(!agendamento.equals(agendamentoSelecionado)){
				agendamentosList.add(agendamento);
			}
		}
		
		agendamentosList.add(agendamentoSelecionado);
		return agendamentosList;
	}
	
	private void finalizarMesasNaoConcluidas() {
		
		for(Agendamentos agendamento:agendamentos){
			if(agendamento.getStatus().getIdstatus().equals(idStatusNovo) || agendamento.getStatus().getIdstatus().equals(idStatusAgendado)){
				agendamento.setStatus(new Status(idStatusNaoAdjudicado));
			}
		}
		
	}
	private void gravarLogDemanda() {
		String msgLog="";
		
		if(demanda.getStatus().getIdstatus().equals(idStatusAgendado)){
			msgLog = msgLogMesaDeCompraAgendada;
		}
		if(demanda.getStatus().getIdstatus().equals(idStatusCompraAdjudicada)){
			msgLog = msgLogMesaDeCompraAdjudicada;
		}
		if(demanda.getStatus().getIdstatus().equals(idStatusConcluido)){
			msgLog = msgLogMesaDeCompraConcluida;
		}
		
		Logs novoLog = demandasmb.criaLog(idTipoLogDemanda,demandasmb.recuperarPessoaLogada(), msgLog);
		
		novoLog.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,novoLog));

	}

	private void salvarFornecedoresAgendadosNaoPersistidos(Set<FornecedoresAgendamentos> fornecedoresAgendamentosParaPersistir) {
		
		for(FornecedoresAgendamentos fornecedorAgendamentoNaoPersistido:fornecedoresAgendamentosParaPersistir){		
			if(fornecedorAgendamentoNaoPersistido.isCheckedPersistido()==false){
				fornecedorAgendamentoInt.incluir(fornecedorAgendamentoNaoPersistido);
			}
			
		}
		
	}
	private Set<FornecedoresAgendamentos> excluirAlterarFornecedoresAgendamentosPersistidos(Set<FornecedoresAgendamentos> fornecedoresAgendamentosParaPersistir) {
		
		boolean fornecedorAgendamentoAlterado;
		
		Set<FornecedoresAgendamentos> fornecedoresAgendamentosParaExcluir = new HashSet<FornecedoresAgendamentos>();
		
		for(FornecedoresAgendamentos fornecedorAgendamentoPersistido:agendamentoSelecionado.getAgendamentosfornecedores()){

			fornecedorAgendamentoAlterado = false;
			
			for(FornecedoresAgendamentos fornecedorAgendamentoParaPersistir:fornecedoresAgendamentosParaPersistir){
				
				if(fornecedorAgendamentoParaPersistir.getFornecedores().equals(fornecedorAgendamentoPersistido.getFornecedores())){
					
					fornecedorAgendamentoPersistido.setNnrvaloradjudicado(fornecedorAgendamentoParaPersistir.getNnrvaloradjudicado());
					
					fornecedorAgendamentoInt.alterar(fornecedorAgendamentoPersistido);
					fornecedorAgendamentoParaPersistir.setCheckedPersistido(true);
					fornecedorAgendamentoAlterado = true;
					
				}
			}				
			
			if(fornecedorAgendamentoAlterado==false){
				fornecedoresAgendamentosParaExcluir.add(fornecedorAgendamentoPersistido);
			}
			
		}
		
		  Iterator<FornecedoresAgendamentos> it = fornecedoresAgendamentosParaExcluir.iterator();
	      while(it.hasNext()) {
	         FornecedoresAgendamentos fornAge = it.next();
	         fornecedorAgendamentoInt.remover(fornAge);
	      }
		
		return fornecedoresAgendamentosParaPersistir;
		
	}
		
	private boolean validarCamposConcluir(){
		
		if(agendamentoSelecionado.getBoolmesarealizada().equals(false) && StringUtils.isBlank(agendamentoSelecionado.getCnmcomentarioresultado())){
			devolveErroParaTela("formagendamento:comentarioresultado","Para mesa n�o realizada o coment�rio deve ser preenchido.");
			return false;
		}
		if(agendamentoSelecionado.getBoolmesarealizada().equals(true) && agendamentoSelecionado.getBoolhouveadjudicacao().equals(false) && StringUtils.isBlank(agendamentoSelecionado.getCnmcomentarioresultado())){
			devolveErroParaTela("formagendamento:comentarioresultado","Para mesa realizada sem adjudica��o o coment�rio deve ser preenchido.");
			return false;
		}
					
		return true;
	}
	
	private boolean validaCamposSalvar() {
		
		if(StringUtils.isBlank(agendamentoSelecionado.getCnmnumeroacm())){
			devolveErroParaTela("formagendamento:numeroacm","N�mero ACM � obrigat�rio.");
			return false;
		}
		if(StringUtils.isBlank(agendamentoSelecionado.getCnmobjeto())){
			devolveErroParaTela("formagendamento:objeto","Objeto � obrigat�rio.");
			return false;
		}
		
		if (agendamentoSelecionado.getDatacadastro().before(agendamentoSelecionado.getDatamesa())) {
			devolveErroParaTela("formagendamento:datamesa","Data da mesa deve ser menor ou igual a data de cadastro do agendamento.");
			return false;
		}
						
		return true;		
	}	
	
	private void devolveErroParaTela(String id, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(id,new FacesMessage(mensagem));
	}

	
	public boolean isExibeMesaDeCompra() {
		return exibeMesaDeCompra;
	}
	
	//GETTERS E SETTERS
		
	public Agendamentos getAgendamentoSelecionado() {
		return agendamentoSelecionado;
	}

	public String getMensagemTelaConfirmacao() {
		return mensagemTelaConfirmacao;
	}

	public void setMensagemTelaConfirmacao(String mensagemTelaConfirmacao) {
		this.mensagemTelaConfirmacao = mensagemTelaConfirmacao;
	}

	public void setAgendamentoSelecionado(Agendamentos agendamentoSelecionado) {
		this.agendamentoSelecionado = agendamentoSelecionado;
	}

	public void setExibeMesaDeCompra(boolean exibeMesaDeCompra) {
		this.exibeMesaDeCompra = exibeMesaDeCompra;
	}

	public boolean isExibeConfirmacaoMesaDeCompra() {
		return exibeConfirmacaoMesaDeCompra;
	}

	public void setExibeConfirmacaoMesaDeCompra(boolean exibeConfirmacaoMesaDeCompra) {
		this.exibeConfirmacaoMesaDeCompra = exibeConfirmacaoMesaDeCompra;
	}

	public List<Agendamentos> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamentos> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public boolean isAgendamentoMesaCompra() {
		return agendamentoMesaCompra;
	}

	public void setAgendamentoMesaCompra(boolean agendamentoMesaCompra) {
		this.agendamentoMesaCompra = agendamentoMesaCompra;
	}

	public List<Fornecedores> getFornecedoresAprovados() {
		return fornecedoresAprovados;
	}
	
	public List<FornecedoresAgendamentos> getFornecedoresAgendamentos() {
		return fornecedoresAgendamentos;
	}
	public void setFornecedoresAgendamentos(
			List<FornecedoresAgendamentos> fornecedoresAgendamentos) {
		this.fornecedoresAgendamentos = fornecedoresAgendamentos;
	}	
	
	//-------------------------	

	public Long getIdStatusAgendado() {
		return idStatusAgendado;
	}
	
	public Long getIdStatusNovo() {
		return idStatusNovo;
	}	
	
	public Long getIdStatusConcluido(){
		return idStatusConcluido;
	}
	
	public Long getIdStatusNaoAdjudicado(){
		return idStatusNaoAdjudicado;
	}
	
	
	
}
