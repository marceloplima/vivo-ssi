package com.ssi.redes.web.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Regionais;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.EventosRedes;
import com.ssi.kernel.redes.domain.LogsRedes;
import com.ssi.kernel.redes.domain.Rotas;
import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.interfaces.DemandasRedesInt;
import com.ssi.kernel.redes.interfaces.EventosRedesInt;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;
import com.ssi.redes.common.utils.ConexaoExterna;

@ManagedBean
@ViewScoped
public class EncaminharMB implements Serializable {

	private static final long serialVersionUID = 8607376112801472472L;

	private static final String tituloLog = "Demanda encaminhada";
	
	@EJB
	private DemandasRedesInt demandaint;
	
	@EJB
	private EventosRedesInt eventosint;
	
	@EJB
	private ParticipantesRedesInt participanteInt;
	
	@EJB
	private ParticipantesRedesInt participanteint;
	
	@EJB
	private AreasInt areasController;
		
	@EJB
	private ModulosInt modulosint;
	
	private DemandasRedes demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private EventosRedes evento;
	
	private boolean mostrarConfirmaEncaminhar = false;
	
	// Mensagens
	private static final String mensagemSSIEncaminhadaSucesso = "SSI encaminhada com sucesso";
	
	@PostConstruct
	public void init(){
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		else{
			demandasmb.setCnmtelefonesolicitante(demanda.getCnmtelefone());
		}
		
		if(evento == null){
			evento = new EventosRedes();
		}
		
	}
	
	public void preEncaminhar(){
		
		demanda.setRegionaisdemandas(new HashSet<Regionais>(demandasmb.getRegionaischecked()));
		
		if(demandasmb.validarCamposObrigatorios()){
			mostrarTelaConfirmaEncaminharSSI();
		}
								
	}
	
	public void mostrarTelaConfirmaEncaminharSSI(){
		mostrarConfirmaEncaminhar=true;
	}
	
	public void fecharTelaConfirmaEncaminharSSI(){
		mostrarConfirmaEncaminhar=false;
	}
	
	public void encaminharSSI(){
		
					
		if(demanda.getStatus().getIdstatus().equals(StatusRedes.ID_DEMANDA_RASCUNHO)){
			
			Calendar cal = Calendar.getInstance();
			 
			 DemandasRedes ultimaDemandaComNumero = demandaint.recuperaUltimaDemandaComNumero();
			
			if(ultimaDemandaComNumero == null){
				ultimaDemandaComNumero = new DemandasRedes();
				ultimaDemandaComNumero.setNnrultimonumero(1);
			}
			
			demanda.setCnmnumero(StringUtils.leftPad(ultimaDemandaComNumero.getNnrultimonumero().toString(), 6, '0')
					+ "/"
					+ StringUtils.leftPad(Integer.toString(cal.get(Calendar.MONTH)+1), 2, '0')
					+ StringUtils.right(Integer.toString(cal.get(Calendar.YEAR)), 2));

			demanda.setNnrultimonumero(ultimaDemandaComNumero.getNnrultimonumero() + 1);
			
		}
		
		demanda.setCnmtelefone(demandasmb.getCnmtelefonesolicitante());
		
		incluiLog();
		
		demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_ANALISE_PELA_EXECUTORA));
		if(demanda.getIddemanda()==null){
			demandaint.incluir(demanda);	
		}else{
			demanda = demandaint.alterar(demanda);
		}
				
		//criaEventoEncaminhar();
		
		demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
		
		enviaEMailEncaminhar();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = demanda.getDatacadastro().getTime();
		String databanco = sdf.format(d);
		
		// Grava informa��o na base de transfer�ncia pro SCIENCE, se tiver rota
		
		// ##### Inicia conex�o com o banco externo 
		Connection con = ConexaoExterna.getConnection();
		// #####
		
		try {
			
			Iterator<Rotas> it = demanda.getRotas().iterator();
			
			while(it.hasNext()){
				
				Rotas r = it.next();
				
				String dpc_dec = "";
				if(!r.getCnmtiporota().equals("Externa")){
					dpc_dec = r.getCentraldestino().getCnmopc();
				}
				
				Statement stmtin = con.createStatement();
				
				String sqlext = "INSERT INTO ROTAS_SSI_SCIENCE (NumeroSSI, " +
						"URLSSI, " +
						"DataConclusao, " + 
						"IDRota, " + 
						"DescricaoRota, " +
						"GrupoOperadoraOrigem, " +
						"OperadoraOrigem, " +
						"AreaPontaA, " +
						"Central, " +
						"CodCCC, " +
						"GrupoOperadoraDestino, " +
						"OperadoraDestino, " +
						"AreaPontaB, " +
						"SentidoRota, " +
						"OPC, " + 
						"DPC_Dec, " + // D�vida (meio esclarecido - Se tiporota != externa, grava o DPC aqui (OPC de destino))
						"DPC_Hex, " + // D�vida (meio esclarecido) - Se for Externa, acontece alguma outra coisa que n�o foi esclarecida no levantamento (n�o culpe o programador!)
						"CodRotaScience, " + 
						"DataAtualizacaoScience, " + 
						"UrlRotaScience) values " + 
						"('"+demanda.getCnmnumero()+"'," +
						"'http://"+recuperarRequest().getLocalAddr()+"/REDES/?iddemanda="+demanda.getIddemanda()+"'," +
						"'"+databanco+"'," +
						"'"+r.getCnmrota()+"'," +
						"'"+r.getCnmdescricao()+"'," +
						"'"+r.getCentralorigem().getCnmgrupooperadora()+"'," +
						"'"+r.getCentralorigem().getCnmoperadora()+"'," +
						"'"+r.getCentralorigem().getCnmareaponta()+"'," +
						"'"+r.getCentralorigem().getCnmcentral()+"'," +
						"'"+r.getCentralorigem().getCnmcodigoccc()+"'," +
						"'"+r.getCentraldestino().getCnmgrupooperadora()+"'," +
						"'"+r.getCentraldestino().getCnmoperadora()+"'," +
						"'"+r.getCentraldestino().getCnmareaponta()+"'," +
						"'"+r.getCnmsentidorota()+"'," +
						"'"+r.getCentralorigem().getCnmopc()+"'," +
						"'"+dpc_dec+"'," +
						"''," +
						"''," +
						"''," +
						"''" +
						")";
				//System.out.println(sqlext);
				//stmtin.execute(sqlext);
				stmtin.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			ConexaoExterna.closeConnection(con);
		}
				
		fecharTelaConfirmaEncaminharSSI();
		
		demandasmb.exibeMensagemSucesso(mensagemSSIEncaminhadaSucesso);
		
		demandasmb.redirecionaParaNotes();
		
	}
	
	private void incluiLog() {
		LogsRedes log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), tituloLog,demanda);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adicionaLogDemandas(demanda,log));
	}
	
	private void enviaEMailEncaminhar() {
		
		//TODO Envio de email incompleto		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		
		//Pessoa logada
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
		
		//Solicitante
		pessoasEnvio.add(demanda.getAutor());
		
		//Envia para Copiados		
		pessoasEnvio.addAll(demanda.getCopiados());
		
		//Envia para Faturamento, somente se tiver rota
		if(demanda.getRotas() != null && demanda.getRotas().size()>=1){
			pessoasEnvio.addAll(participanteint.recuperarFaturamento());
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
		
		String assunto = "SSI REDES "+demanda.getCnmnumero()+", Encaminhada �s "+dataformat;
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

//	private void criaEventoEncaminhar() {
//		
//		EventosRedes evento = new EventosRedes();
//		
//		evento.setAutor(demandasmb.recuperarPessoaLogada());
//		evento.setDemanda(demanda);
//		eventosint.registrar(evento);
//						
//	}

	public boolean isMostrarConfirmaEncaminhar() {
		return mostrarConfirmaEncaminhar;
	}

	public void setMostrarConfirmaEncaminhar(boolean mostrarConfirmaEncaminhar) {
		this.mostrarConfirmaEncaminhar = mostrarConfirmaEncaminhar;
	}	

	public HttpServletRequest recuperarRequest(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request;
	}

}
