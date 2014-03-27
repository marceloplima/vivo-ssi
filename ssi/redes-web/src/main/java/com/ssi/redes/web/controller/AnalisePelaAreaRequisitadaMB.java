package com.ssi.redes.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.EventosRedes;
import com.ssi.kernel.redes.domain.LogsRedes;
import com.ssi.kernel.redes.domain.PareceresRedes;
import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.domain.TiposLogRedes;
import com.ssi.kernel.redes.interfaces.DemandasRedesInt;
import com.ssi.kernel.redes.interfaces.EventosRedesInt;
import com.ssi.kernel.redes.interfaces.LogsRedesInt;
import com.ssi.kernel.redes.interfaces.PareceresRedesInt;

@ManagedBean
@ViewScoped
public class AnalisePelaAreaRequisitadaMB implements Serializable {

	private static final long serialVersionUID = 8607376112801472472L;
		
	@EJB
	private PareceresRedesInt pareceresController;
	
	@EJB
	private DemandasRedesInt demandaInt;
		
	@EJB
	private LogsRedesInt logsInt;
	
	@EJB
	private EventosRedesInt eventosint;	
	
	@EJB
	private AreasInt areasController;
		
	private IndexMB indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
	
	private DemandasRedes demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private EventosRedes evento;
	private List<PareceresRedes> pareceresRedes;
	private List<Pessoas> responsaveisTecnicos;
	
	private boolean mostrarAnaliseAreaRequisitada = false;
	private boolean mostrarConfirmarAnaliseAreaRequisitada = false;
	
	private boolean mostraResponsavelTecnico = false;
	private boolean mostraComplexidade = false;
	private boolean mostraComentario = false;	

	@PostConstruct
	public void init(){
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}		
		
		if(evento == null){
			evento = new EventosRedes();
		}
		
		demanda.setComplexidade("");
		
		pareceresRedes = pareceresController.recuperarPorStatus(new StatusRedes(StatusRedes.ID_DEMANDA_ANALISE_PELA_EXECUTORA));
		responsaveisTecnicos = areasController.recuperarPessoasArea(demanda.getArearequisitada());
		
	}
	
	public void setaParecer(ValueChangeEvent event){
		
		PareceresRedes parecer = (PareceresRedes) event.getNewValue();
		
		evento.setParecer(parecer);

		mostraResponsavelTecnico = false;
		mostraComplexidade = false;
		mostraComentario = true;		
		
		if(evento.getParecer().getIdparecer().equals(PareceresRedes.ID_PROCEDE) || evento.getParecer().getIdparecer().equals(PareceresRedes.ID_PROCEDE_COM_RESSALVA)){
			mostraResponsavelTecnico = true;
			mostraComplexidade = true;			
		}
							
	}
	
	public void preAnalise(){
		if(validarCamposObrigatorios()){
			mostrarConfirmarAnaliseAreaRequisitada = true;
		}		
	}
	
	public void efetuarAnalise(){
		salvarDemanda();
		enviarEmail();		
	}
	
	private void enviarEmail() {
		String msg = "";
		String complementoassunto = "";
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
				
		switch (evento.getParecer().getIdparecer().intValue())
		{
			case (int) PareceresRedes.ID_NECESSIDADE_DADOS:
				//msg = "Necessidade de dados";
				complementoassunto = "Necessita de dados";
				pessoasEnvio.add(demanda.getAutor());
				break;
			
			case (int) PareceresRedes.ID_IMPROCEDENTE:
				//msg = "Improcedente";
				complementoassunto = "Improcedente";
				pessoasEnvio.add(demanda.getAutor());
				break;
			
			default:
				//msg = "Demanda em execu��o";
				complementoassunto = "Em execu��o";
				pessoasEnvio = responsaveisTecnicos;
				break;
		}
		
		SimpleDateFormat sdfH = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
		Date d = new Date();
		String dataformat = sdfH.format(d);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String prazoexec = sdf.format(demanda.getPrazoexecucao().getTime());
		
		String tarifacao = "N�O";
		if(demanda.getFlagtarifacao())
			tarifacao = "SIM";
		
		String sinalizacao = "N�O";
		if(demanda.getFlagsinalizacao())
			sinalizacao = "SIM";
		
		String seguranca = "NAO";
		if(demanda.getFlagseguranca())
			seguranca = "SIM";
		
		String alarmistica = "NAO";
		if(demanda.getFlagalarmistica())
			alarmistica = "SIM";
		
		String dadostrafego = "NAO";
		if(demanda.getFlagdadostrafego())
			dadostrafego = "SIM";
		
		String avisoimprensa = "NAO";
		if(demanda.getFlagavisoimprensa())
			avisoimprensa = "SIM";
		
		
		String assunto = "SSI REDES "+demanda.getCnmnumero()+", "+complementoassunto+" �s "+dataformat;
		
		String strmensagem = "Solicitante: "+demanda.getAutor().getCnmnome()+"<br>";
		strmensagem+="�rea Solicitante: "+demanda.getAreasolicitante().getCnmsiglaarea()+"<br>";
		strmensagem+="�rea Requisitada: "+demanda.getArearequisitada().getCnmsiglaarea()+"<br>";
		strmensagem+="T�tulo: "+demanda.getAtividade().getCnmatividade()+"<br>";
		strmensagem+="�rea Solicitante: "+demanda.getAreasolicitante().getCnmsiglaarea()+"<br>";
		strmensagem+="Prazo para Execu��o: "+prazoexec+"<br>";
		strmensagem+="Prioridade: "+demanda.getPrioridade().getCnmprioridade()+"<br>";
		strmensagem+="Telefone: "+demanda.getCnmtelefone()+"<br>";
		strmensagem+="Motiva��o: "+demanda.getMotivacao().getCnmmotivacao()+"<br>";
		strmensagem+="Refer�ncia: "+demanda.getCnmreferencia()+"<br>";
		strmensagem+="Objetivo:<br>"+demanda.getCdsobjetivo()+"<br>";
		strmensagem+="�rea de Aplica��o:<br>"+demanda.getCdsareaaplicacao()+"<br>";
		strmensagem+="Cen�rio Atual:<br>"+demanda.getCdscenarioatual()+"<br>";
		strmensagem+="Cen�rio Proposto:<br>"+demanda.getCdscenarioproposto()+"<br>";
		strmensagem+="Atividades Para Execu��o:<br>"+demanda.getCdsatividadesexecucao()+"<br>";
		strmensagem+="Recursos Necess�rios:<br>"+demanda.getCdsrecursosnecessarios()+"<br>";
		strmensagem+="Testes Recomendados:<br>"+demanda.getCdstestesrecomendados()+"<br>";
		strmensagem+="Objetivo:<br>"+demanda.getCdsobjetivo()+"<br>";
		strmensagem+="Tarifa��o? "+tarifacao+"<br>";
		strmensagem+="Sinaliza��o? "+sinalizacao+"<br>";
		strmensagem+="Seguran�a? "+seguranca+"<br>";
		strmensagem+="Alarm�stica? "+alarmistica+"<br>";
		strmensagem+="Dados de Tr�fego? "+dadostrafego+"<br>";
		strmensagem+="Aviso � Imprensa? "+avisoimprensa+"<br>";
		strmensagem+="<P>Aviso � Imprensa? "+avisoimprensa+"<br>";
		strmensagem+="<P>http://"+recuperarRequest().getLocalAddr()+"/REDES/?iddemanda="+demanda.getIddemanda()+"<br>";
		strmensagem+="<P>Esta � uma mensagem gerada automaticamente pelo sistema, portanto n�o deve ser respondida.<br>";
		
		//System.out.println(strmensagem);
		
		demandasmb.envioEMail(pessoasEnvio,assunto,strmensagem);
						
	}

	private void salvarDemanda() {

		demanda.setPessoacomquemesta(demanda.getResponsaveltecnico());
		demanda.setStatusanterior(demanda.getStatus());
		
		switch (evento.getParecer().getIdparecer().intValue())
		
			{
			case (int) PareceresRedes.ID_NECESSIDADE_DADOS:
				demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_REVISAO_PELO_EMISSOR));
				demanda.setPessoacomquemesta(demanda.getAutor());
				break;
			
			case (int) PareceresRedes.ID_IMPROCEDENTE:
				demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_CANCELADA));
				break;
			
			default:
				demanda.setPessoacomquemesta(demanda.getResponsaveltecnico());
				demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_EM_EXECUCAO));
				break;
			}
		
		
		incluirLog();
		salvaEventoAnalise();
		demanda = demandaInt.alterar(demanda);	
		
		demanda.setLogs(new LinkedHashSet<LogsRedes>(logsInt.recuperarLogsDemanda(demanda)));
		
		demandasmb.setDemandas(demandaInt.recuperarDemandaEspecifica(demanda.getIddemanda()));		
				
		indexmb.setMsgpanel("An�lise efetuada com sucesso");
		indexmb.setPanelexibesucesso(true);
		
		fechaAnaliseAreaRequisitada();
		fechaConfirmaAnalise();
		
	}
		
	
	private void salvaEventoAnalise() {
		
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(demanda);
		evento.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_ANALISE_PELA_EXECUTORA));
		eventosint.registrar(evento);
						
	}		

	private void incluirLog() {
		
		LogsRedes novoLog = demandasmb.criaLog(TiposLogRedes.ID_DEMANDA,demandasmb.recuperarPessoaLogada(), recuperarTextoParaLog(),demanda);
		
		demanda.setLogs(demandasmb.adicionaLogDemandas(demanda,novoLog));
		
	}

	private boolean validarCamposObrigatorios(){
		
		if(evento.getParecer() == null){
			demandasmb.devolveErroParaTela("formanalisearearequisitada:pareceranalisearearequisitada","O parecer deve ser preenchido");
			return false;
		}
		
		switch (evento.getParecer().getIdparecer().intValue())
		
		{
		case (int) PareceresRedes.ID_PROCEDE_COM_RESSALVA:
			
			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("formanalisearearequisitada:comentario","O campo coment�rio � obrigat�rio");
				return false;
			}
										
		break;
		
		case (int) PareceresRedes.ID_NECESSIDADE_DADOS:
			
			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("formanalisearearequisitada:comentario","O campo coment�rio � obrigat�rio");
				return false;
			}
										
		break;
		
		case (int) PareceresRedes.ID_IMPROCEDENTE:
			
			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("formanalisearearequisitada:comentario","O campo coment�rio � obrigat�rio");
				return false;
			}			
			
			break;
		
		default:
			
			if("".equals(demanda.getComplexidade())){
				demandasmb.devolveErroParaTela("formanalisearearequisitada:complexidade","A complexidade deve ser preenchida");
				return false;				
			}
			
			if(demanda.getResponsaveltecnico() == null){
				demandasmb.devolveErroParaTela("formanalisearearequisitada:responsaveltecnicoanalisearearequisitada","O respons�vel t�cnico deve ser preenchido");
				return false;				
			}
			
			
			if(StringUtils.isBlank(evento.getCnmcomentario())){
				demandasmb.devolveErroParaTela("formanalisearearequisitada:comentario","O campo coment�rio � obrigat�rio");
				return false;
			}				
			
			break;
			
		}		
		
		return true;
	}
	
	private String recuperarTextoParaLog(){
				
		switch (evento.getParecer().getIdparecer().intValue())
		
		{
		case (int) PareceresRedes.ID_NECESSIDADE_DADOS:
			return "Solicita��o de dados feita";	
		
		case (int) PareceresRedes.ID_IMPROCEDENTE:
			return "Solicita��o improcedente";
		
		case (int) PareceresRedes.ID_PROCEDE:
			return "Solicita��o procede";
			
		case (int) PareceresRedes.ID_PROCEDE_COM_RESSALVA:
			return "Solicita��o procede com ressalva";
		}
		
		return "";
		
	}

	/*Getters e Setters*/			
	public void fechaAnaliseAreaRequisitada(){
		mostrarAnaliseAreaRequisitada = false;
	}
	
	public boolean isMostraResponsavelTecnico() {
		return mostraResponsavelTecnico;
	}

	public void setMostraResponsavelTecnico(boolean mostraResponsavelTecnico) {
		this.mostraResponsavelTecnico = mostraResponsavelTecnico;
	}

	public boolean isMostraComplexidade() {
		return mostraComplexidade;
	}

	public void setMostraComplexidade(boolean mostraComplexidade) {
		this.mostraComplexidade = mostraComplexidade;
	}

	public boolean isMostraComentario() {
		return mostraComentario;
	}

	public void setMostraComentario(boolean mostraComentario) {
		this.mostraComentario = mostraComentario;
	}

	public DemandasRedes getDemanda() {
		return demanda;
	}

	public void setDemanda(DemandasRedes demanda) {
		this.demanda = demanda;
	}

	public void fechaConfirmaAnalise(){
		fechaAnaliseAreaRequisitada();
		mostrarConfirmarAnaliseAreaRequisitada = false;
	}
	
	public void mostrarTelaAnalise(){
		mostrarAnaliseAreaRequisitada = true;
	}
	
	public boolean isMostrarAnaliseAreaRequisitada() {
		return mostrarAnaliseAreaRequisitada;
	}

	public List<PareceresRedes> getPareceresRedes() {
		return pareceresRedes;
	}

	public void setPareceresRedes(List<PareceresRedes> pareceresRedes) {
		this.pareceresRedes = pareceresRedes;
	}

	public EventosRedes getEvento() {
		return evento;
	}

	public void setEvento(EventosRedes evento) {
		this.evento = evento;
	}

	public void setMostrarAnaliseAreaRequisitada(
			boolean mostrarAnaliseAreaRequisitada) {
		this.mostrarAnaliseAreaRequisitada = mostrarAnaliseAreaRequisitada;
	}

	public boolean isMostrarConfirmarAnaliseAreaRequisitada() {
		return mostrarConfirmarAnaliseAreaRequisitada;
	}

	public void setMostrarConfirmarAnaliseAreaRequisitada(
			boolean mostrarConfirmarAnaliseAreaRequisitada) {
		this.mostrarConfirmarAnaliseAreaRequisitada = mostrarConfirmarAnaliseAreaRequisitada;
	}

	public List<Pessoas> getResponsaveisTecnicos() {
		return responsaveisTecnicos;
	}

	public void setResponsaveisTecnicos(List<Pessoas> responsaveisTecnicos) {
		this.responsaveisTecnicos = responsaveisTecnicos;
	}
	
	public HttpServletRequest recuperarRequest(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request;
	}

	
}
