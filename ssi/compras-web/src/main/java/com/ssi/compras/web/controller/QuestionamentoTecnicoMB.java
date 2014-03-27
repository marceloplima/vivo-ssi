package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.RespostasQuestionamentos;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.RespostaQuestionamentoInt;

@ManagedBean
@ViewScoped
public class QuestionamentoTecnicoMB implements Serializable {

	private static final long serialVersionUID = -5947495659675823634L;
	
	private static final String tituloLogCriacao = "Questionamento T�cnico Criado";
	private static final String tituloLogInclusaoNovaResposta = "Questionamento T�cnico Respondido";
	
	private static final String validacaoCampoAssuntoObrigatorio = "O Assunto deve ser preenchido.";
	private static final String validacaoCampoComentarioObrigatorio = "O coment�rio deve ser preenchido.";
	private static final String validacaoCampoComentarioNaoPodeSerMaiorQueQtdCaracteres = "O coment�rio deve ser maior que 255 caracteres.";
	
	@EJB
	private DemandaInt demandaint;

	@EJB
	private RespostaQuestionamentoInt respostaQuestionamentoInt;	
		
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private String assunto;
	private String questionamento;	
	
	//Respostas
	private boolean mostrarRespostaQuestionamentoTecnico = false;	
	private boolean mostrarTelaConfirmacaoRespostaQuestionamentoTecnico = false;
	
	//Confirma��o de questionamento t�cnico
	private boolean mostraTelaConfirmacaoQuestionamentoTecnico = false;
	
	private RespostasQuestionamentos respostaQuestionamentoSelecionado;
	
	private List<RespostasQuestionamentos> respostasQuestionamentos = new ArrayList<RespostasQuestionamentos>();

	@PostConstruct
	public void init(){
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}		
		
		recuperaRespostasQuestionamentos();
						
	}
	
	//TELA DE QUESTIONARIO
	public void mostrarTelaConfirmacaoQuestionario(){
		mostraTelaConfirmacaoQuestionamentoTecnico = true;
	}
	
	public void fecharTelaConfirmacaoQuestionario(){
		mostraTelaConfirmacaoQuestionamentoTecnico = false;
	}
	
	//TELA DE RESPOSTA
	public void mostrarTelaRespostaQuestionario(){
		mostrarRespostaQuestionamentoTecnico = true;				
	}
	
	public void fecharTelaRespostaQuestionario(){
		mostrarRespostaQuestionamentoTecnico = false;
	}
	
	public void mostraTelaConfirmacaoRespostaQuestionario(){
		mostrarTelaConfirmacaoRespostaQuestionamentoTecnico = true;	
	}
	
	public void fechaTelaConfirmacaoRespostaQuestionario(){
		mostrarTelaConfirmacaoRespostaQuestionamentoTecnico = false;		
	}
	
	//FIM
	
	private void recuperaRespostasQuestionamentos(){
		respostasQuestionamentos = respostaQuestionamentoInt.recuperarRespostasQuestionamento(demanda);
	}
	
	public void preIncluirQuestionamentoTecnico(){
		
		if(validaCriacaoQuestionamentoTecnico()){
			mostrarTelaConfirmacaoQuestionario();
		}					
				
	}	
	
	public void preIncluirRespostaQuestionamentoTecnico(RespostasQuestionamentos respostaQuestionamento){
		if(respostaQuestionamento==null){
			respostaQuestionamentoSelecionado = new RespostasQuestionamentos();
		}else{
			respostaQuestionamentoSelecionado = respostaQuestionamento;
		}
			
		mostrarTelaRespostaQuestionario();
	}
	
	public void preSalvarRespostaQuestionamentoTecnico(){
		if(validaRespostaQuestionamentoTecnico()){
			fecharTelaRespostaQuestionario();
			mostraTelaConfirmacaoRespostaQuestionario();
		}
	}
	
	
	public void criarQuestionamentoTecnico(){

		incluiLog(tituloLogCriacao);
		
		demanda.setAutorquestionamento(demandasmb.recuperarPessoaLogada());
		Calendar cal = Calendar.getInstance();
		demanda.setDataquestionamento(cal);
		
		demanda.setCnmassuntoquestionamento(assunto);
		demanda.setCnmquestionamento(questionamento);
		
		demanda = demandaint.alterar(demanda);
		
		fecharTelaConfirmacaoQuestionario();
	}
	
	private void incluiLog(String tituloLog) {
		Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	}
		
	public void incluirResposta(){
		
		respostaQuestionamentoSelecionado.setDemanda(demanda);
		respostaQuestionamentoSelecionado.setAutorresposta(demandasmb.recuperarPessoaLogada());
		
		respostaQuestionamentoInt.incluir(respostaQuestionamentoSelecionado);
		
		incluiLog(tituloLogInclusaoNovaResposta);
		
		demanda = demandaint.alterar(demanda);
		
		recuperaRespostasQuestionamentos();
		
		fechaTelaConfirmacaoRespostaQuestionario();
		
		respostaQuestionamentoSelecionado = new RespostasQuestionamentos();
		
	}

	private boolean validaCriacaoQuestionamentoTecnico() {	
	
		if(StringUtils.isBlank(getAssunto())){
			demandasmb.devolveErroParaTela("demandaform:assuntoquestionamento", validacaoCampoAssuntoObrigatorio);
			return false;
		}
		
		if(StringUtils.isBlank(getQuestionamento())){
			demandasmb.devolveErroParaTela("demandaform:questionamento", validacaoCampoAssuntoObrigatorio);
			return false;
		}
		
		return true;
	}
	
	private boolean validaRespostaQuestionamentoTecnico(){
		if(StringUtils.isBlank(respostaQuestionamentoSelecionado.getCnmcomentario())){
			demandasmb.devolveErroParaTela("formrespostaquestionamento:comentarioquestionamento", validacaoCampoComentarioObrigatorio);
			return false;
		}
		
		if(respostaQuestionamentoSelecionado.getCnmcomentario().length()>255){
			demandasmb.devolveErroParaTela("formrespostaquestionamento:comentarioquestionamento", validacaoCampoComentarioNaoPodeSerMaiorQueQtdCaracteres);
			return false;
		}
		
		return true;		
	}
	
	public boolean isMostrarRespostaQuestionamentoTecnico() {
		return mostrarRespostaQuestionamentoTecnico;
	}

	public void setMostrarRespostaQuestionamentoTecnico(
			boolean mostrarRespostaQuestionamentoTecnico) {
		this.mostrarRespostaQuestionamentoTecnico = mostrarRespostaQuestionamentoTecnico;
	}

	//Gets e Setters
	
	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getQuestionamento() {
		return questionamento;
	}

	public void setQuestionamento(String questionamento) {
		this.questionamento = questionamento;
	}

	public RespostasQuestionamentos getRespostaQuestionamentoSelecionado() {
		return respostaQuestionamentoSelecionado;
	}

	public void setRespostaQuestionamentoSelecionado(
			RespostasQuestionamentos respostaQuestionamentoSelecionado) {
		this.respostaQuestionamentoSelecionado = respostaQuestionamentoSelecionado;
	}

	public boolean isMostraTelaConfirmacaoQuestionamentoTecnico() {
		return mostraTelaConfirmacaoQuestionamentoTecnico;
	}

	public void setMostraTelaConfirmacaoQuestionamentoTecnico(
			boolean mostraTelaConfirmacaoQuestionamentoTecnico) {
		this.mostraTelaConfirmacaoQuestionamentoTecnico = mostraTelaConfirmacaoQuestionamentoTecnico;
	}

	public List<RespostasQuestionamentos> getRespostasQuestionamentos() {
		return respostasQuestionamentos;
	}

	public void setRespostasQuestionamentos(List<RespostasQuestionamentos> respostasQuestionamentos) {
		this.respostasQuestionamentos = respostasQuestionamentos;
	}

	public boolean isMostrarTelaConfirmacaoRespostaQuestionamentoTecnico() {
		return mostrarTelaConfirmacaoRespostaQuestionamentoTecnico;
	}

	public void setMostrarTelaConfirmacaoRespostaQuestionamentoTecnico(
			boolean mostrarTelaConfirmacaoRespostaQuestionamentoTecnico) {
		this.mostrarTelaConfirmacaoRespostaQuestionamentoTecnico = mostrarTelaConfirmacaoRespostaQuestionamentoTecnico;
	}				
	
	
	
}
