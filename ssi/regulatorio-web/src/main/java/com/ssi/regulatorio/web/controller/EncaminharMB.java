package com.ssi.regulatorio.web.controller;

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

	@PostConstruct
	public void init(){
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
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
								
		incluiLog();
		
		demanda.setStatus(new StatusRedes(StatusRedes.ID_DEMANDA_ANALISE_PELA_EXECUTORA));
		if(demanda.getIddemanda()==null){
			demandaint.incluir(demanda);	
		}else{
			demanda = demandaint.alterar(demanda);
		}
				
		criaEventoEncaminhar();
		
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
				
				HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				
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
						"'http://"+request.getLocalAddr()+"/REDES/?iddemanda="+demanda.getIddemanda()+"'," +
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
				stmtin.execute(sqlext);
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
		
		String assunto = "SSI REDES - Encaminhar";
		String strmensagem = "A SSI de registro "+ demanda.getCnmnumero() + " foi encaminhada por " + demandasmb.recuperarPessoaLogada().getCnmnome();
		
		demandasmb.envioEMail(pessoasEnvio,assunto,strmensagem);				
		
	}

	private void criaEventoEncaminhar() {
		
		EventosRedes evento = new EventosRedes();
		
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(demanda);
		eventosint.registrar(evento);
						
	}

	public boolean isMostrarConfirmaEncaminhar() {
		return mostrarConfirmaEncaminhar;
	}

	public void setMostrarConfirmaEncaminhar(boolean mostrarConfirmaEncaminhar) {
		this.mostrarConfirmaEncaminhar = mostrarConfirmaEncaminhar;
	}	


	
}
