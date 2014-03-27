package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Cronogramas;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Propostas;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.compras.common.interfaces.CronogramaInt;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.compras.common.interfaces.PropostasInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.Mensageria;

@ManagedBean
@ViewScoped
public class PropostasMB implements Serializable {

	private static final long serialVersionUID = -8643188259592674732L;
	
	private static final long idStatusPropostaEncaminhar = 29L;
	private static final long idStatusPropostaEmAnalise = 8L;
	private static final long idStatusAnaliseLaudoTecnico = 32L;
	private static final long idStatusAguardandoPropostas = 28L;
	private static final long idStatusCancelada = 18L;
	private static final long idStatusAprovada = 33L;
	private static final long idStatusReprovada = 34L;
	
	@EJB
	private DemandaInt demandaint;
	
	@EJB
	private PropostasInt propostaint;
	
	@EJB
	private CronogramaInt cronogramaint;
	
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
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Propostas proposta;
	
	private List<Propostas> propostas;
	private List<Propostas> propostaschecked;
	
	private boolean exibepopup;
	private boolean exibeconfirmacao;
	private boolean exibeencaminhamento;
	//private boolean exibenaopodeencaminhar;
	private boolean exibeanalisetecnica;
	private boolean exibealerta;
	
	// Usado para exibir na tela de An�lise T�cnica "Aprovar" ou "Reprovar" e para controlar a a��o (int)
	private String acaoanalise; 
	private int tipoanalise;
	
	private Eventos evento;
	
	// Usado na busca
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	@PostConstruct
	public void init(){
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		if(propostaschecked == null){
			propostaschecked = new ArrayList<Propostas>();
		}
		
		proposta = new Propostas();
		
		if(evento == null){
			evento = new Eventos();
		}
	}
	
	public void selecionarCheckbox(Propostas obj){
		boolean temchecado = false;
		List<Propostas> jachecados = recuperarChecados();
		
		if(jachecados != null && jachecados.size()>=1){
			Iterator<Propostas> it = jachecados.iterator();
			
			while(it.hasNext()){
				Propostas prop = it.next();
				if(prop.getIdproposta() == obj.getIdproposta()){
					temchecado = true;
				}
			}
			
			if(!temchecado){
				jachecados.add(obj);
				obj.setChecked(true);
			}
		}
		else{
			obj.setChecked(true);
			jachecados = new ArrayList<Propostas>();
			jachecados.add(obj);
		}
		
		// Armazena os checados em sess�o
		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessao.put("propostaschecked", jachecados);
	}
	
	public void destroiPropostasChecked(){
		// Recupera a Sess�o para eliminar o list de checados caso exista
		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessao.remove("propostaschecked");
	}
	
	@SuppressWarnings("unchecked")
	public List<Propostas> recuperarChecados(){
		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		List<Propostas>checados = (List<Propostas>) sessao.get("propostaschecked");
		return checados;
	}
	
	public void encaminhar(){
		
		evento = new Eventos();
		//desabilitaNaoPodeEncaminhar();
		demanda.setStatusanterior(demanda.getStatus());
		
		// Atualiza o status das propostas encaminhadas
		Iterator<Propostas> it = recuperarChecados().iterator();
		
		String strpropostasfeitas = new String();
		
		while(it.hasNext()){
			Propostas prop = it.next();
			
			if (prop.getStatus().getIdstatus() == idStatusPropostaEncaminhar){
				Status status = new Status();
				status.setIdstatus(idStatusPropostaEmAnalise);
				status.setCnmstatus("Proposta Em An�lise");
				prop.setStatus(status);
				
				prop.setStatus(status);
				demanda.setStatus(status);
				
				propostaint.registrar(prop);
				
				strpropostasfeitas+="<BR>"+prop.getFornecedorprop().getCnmfornecedor();
			}
		}
		
		
		
		destroiPropostasChecked();
		
		// Atualiza o cronograma com a data de realiza��o de agora
		Cronogramas crono = cronogramaint.recuperarPorAtividade(4L, demanda);
		crono.setDatarealizacao(Calendar.getInstance());
		cronogramaint.atualizar(crono);
		
		// Seta a demanda em quest�o no objeto proposta
		proposta.setDemanda(demanda);
		
		// Cria o Log
		String descricaoLog;
		descricaoLog = "Proposta Em An�lise";
		Logs log = demandasmb.criaLog(3L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
		
		// Atualiza a demanda com as informa��es enviadas de proposta
		demandaint.alterar(demanda);
		
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
				
		Status statuspropanal = new Status();
		statuspropanal.setIdstatus(idStatusPropostaEmAnalise);
		evento.setStatus(statuspropanal);
		
		Status statuspropenc = new Status();
		statuspropenc.setIdstatus(idStatusPropostaEncaminhar);
		evento.setStatusanterior(statuspropenc);
		evento.getEgenerico().setEventogenerico(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		
		// Prepara o envio do email
		Modulos modulo = modulosint.recuperarUnico(336L);
		
		GruposModulos gm = new GruposModulos();
		gm.setIdgrupomodulo(8L);
		gm.setModulo(modulo);
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		//pessoa logada
		pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
						
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
		
		//Solicitante
		pessoasEnvio.add(demanda.getAutor());				
		
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
		String assunto = "SSI COMPRAS - "+statuspropanal.getCnmstatus();
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" est� com Propostas em An�lise.";
		strmensagem+="<P>Propostas</P>";
		strmensagem+=strpropostasfeitas;
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;
		
		desabilitaEncaminhamento();
		
	}
	
	public void habilitaAlerta(){
		exibealerta=true;
	}
	
	public void desabilitaAlerta(){
		exibealerta=false;
	}
	
	public void desabilitaEncaminhamento(){
		exibeencaminhamento = false;
		destroiPropostasChecked();
	}
	
	public void habilitaEncaminhamento(){
		List<Propostas> propostasencaminhar = new ArrayList<Propostas>();
		desabilitaAlerta();
		try{
			Iterator<Propostas> it = recuperarChecados().iterator();
					
			while(it.hasNext()){
				Propostas prop = it.next();
						
				if (prop.getStatus().getIdstatus() == idStatusPropostaEncaminhar){
				
					propostasencaminhar.add(prop);
				
				}
					
				setPropostaschecked(propostasencaminhar);
			}
			
			if(propostasencaminhar.size()>=1){
				exibeencaminhamento = true;
			}
		}catch(Exception ex){
			habilitaAlerta();
		}
	}
	
	public void desabilitaAnaliseTecnica(){
		exibeanalisetecnica = false;
		destroiPropostasChecked();
	}
	
	public void habilitaAnaliseTecnica(String acaoanalise, String tipoanalise){//Tipo de a��o: Aprovar ou Reprovar, dever� ser tratado de acordo
		
		this.tipoanalise = new Integer(tipoanalise);
		this.acaoanalise = acaoanalise;
		
		desabilitaAlerta();
		
		List<Propostas> propostasanalisar = new ArrayList<Propostas>();
		try{
			Iterator<Propostas> it = recuperarChecados().iterator();
					
			while(it.hasNext()){
				Propostas prop = it.next();
						
				if (prop.getStatus().getIdstatus() == idStatusPropostaEmAnalise){
				
					propostasanalisar.add(prop);
				
				}
					
				setPropostaschecked(propostasanalisar);
			}
			
			if(propostasanalisar.size()>=1){
				exibeanalisetecnica = true;
			}
		}catch(Exception ex){
			habilitaAlerta();
		}

	}
	
	public void efetuaranalisetecnica(Integer status){
		
		if(status == idStatusAprovada){
			aprovar();
		}
		else if(status == idStatusReprovada){
			reprovar();
		}
	}
	
	public void aprovar(){
		
		evento = new Eventos();
		evento.setStatusanterior(demanda.getStatus());
		demanda.setStatusanterior(demanda.getStatus());
		
		// Atualiza o status das propostas
		Iterator<Propostas> it = recuperarChecados().iterator();
		
		String strpropostas = new String();
		
		boolean alterarstatusdemanda = false;
		
		while(it.hasNext()){
			Propostas prop = it.next();
			
			if (prop.getStatus().getIdstatus() == idStatusPropostaEmAnalise){
				Status status = new Status();
				
				// Seta o status da Proposta como Aprovada
				status.setIdstatus(idStatusAprovada);
				status.setCnmstatus("Aprovada");
				prop.setStatus(status);
				
				propostaint.registrar(prop);
				
				strpropostas+="<BR>"+prop.getFornecedorprop().getCnmfornecedor();
				
				alterarstatusdemanda = true;
			}
		}
		
		if(alterarstatusdemanda){
			
			// O status da Demanda, ser� diferente
			Status status = new Status();
			
			status.setIdstatus(idStatusAnaliseLaudoTecnico);
			status.setCnmstatus("An�lise de Laudo T�cnico");
			demanda.setStatus(status);
		}
		
		
		destroiPropostasChecked();
		
		// Atualiza o cronograma com a data de realiza��o de agora
		Cronogramas crono = cronogramaint.recuperarPorAtividade(5L, demanda);
		crono.setDatarealizacao(Calendar.getInstance());
		cronogramaint.atualizar(crono);
		
		// Seta a demanda em quest�o no objeto proposta
		proposta.setDemanda(demanda);
		
		// Cria o Log
		String descricaoLog;
		descricaoLog = demanda.getStatus().getCnmstatus();
		Logs log = demandasmb.criaLog(3L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
		
		// Atualiza a demanda com as informa��es enviadas de proposta
		demandaint.alterar(demanda);
		
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
				
		Status status = new Status();
		status.setIdstatus(idStatusAnaliseLaudoTecnico);
		evento.setStatus(status);
		
		evento.getEgenerico().setEventogenerico(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		
		// Prepara o envio do email
		Modulos modulo = modulosint.recuperarUnico(336L);
		
		GruposModulos gm = new GruposModulos();
		gm.setIdgrupomodulo(8L);
		gm.setModulo(modulo);
		
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
		
		//analista de aquisi��o
		if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());	
		}
					
		//analista de contrato
		if(demanda.getAnalistaDeContratoResponsavel()!=null){
			pessoasEnvio.add(demanda.getAnalistaDeContratoResponsavel());	
		}		
		
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
		String assunto = "SSI COMPRAS - "+status.getCnmstatus();
		String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" est� em An�lise de Laudo T�cnico";
		strmensagem+="<P>Propostas</P>";
		strmensagem+=strpropostas;
		mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
		
		mensageria = null;
		listaemails = null;
		assunto = null;
		strmensagem = null;
		
		desabilitaAnaliseTecnica();
		
	}
	
	public void reprovar(){
		
		evento = new Eventos();
		evento.setStatusanterior(demanda.getStatus());
		
		boolean boolreprovandotodas = false;
		
		// Atualiza o status das propostas
		Iterator<Propostas> it = recuperarChecados().iterator();
		
		while(it.hasNext()){
			Propostas prop = it.next();
			
			if (prop.getStatus().getIdstatus() == idStatusPropostaEmAnalise){
				Status status = new Status();
				status.setIdstatus(idStatusReprovada);
				status.setCnmstatus("Reprovada");
				prop.setStatus(status);
				
				propostaint.registrar(prop);
			}
		}
		
		// Verifica se todas foram reprovadas
		// Se positivo, altera o status da demanda
		if(propostaint.verificarTodasPropostas(demanda).size() == propostaint.verificarTodasReprovadas(demanda).size()){
			Status status = new Status();
			status.setIdstatus(idStatusAguardandoPropostas);
			status.setCnmstatus("Aguardando Propostas");
			demanda.setStatusanterior(demanda.getStatus());
			demanda.setStatus(status);
			boolreprovandotodas = true;
		}
		
		destroiPropostasChecked();
		
		// Seta a demanda em quest�o no objeto proposta
		proposta.setDemanda(demanda);
		
		// Cria o Log
		String descricaoLog;
		if(!boolreprovandotodas)
			descricaoLog = demanda.getStatus().getCnmstatus();
		else
			descricaoLog = "Aguardando Propostas";
		Logs log = demandasmb.criaLog(3L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
		
		// Atualiza a demanda com as informa��es enviadas de proposta
		demandaint.alterar(demanda);
		
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		
		if(boolreprovandotodas){
			Status status = new Status();
			status.setIdstatus(idStatusAguardandoPropostas);
			status.setCnmstatus("Aguardando Propostas");
			evento.setStatus(status);
		}else{
			evento.setStatus(demanda.getStatus());
		}
		evento.getEgenerico().setEventogenerico(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		/*
		 * 
		 */
		if(boolreprovandotodas){
			// Prepara o envio do email
			Modulos modulo = modulosint.recuperarUnico(336L);
			
			GruposModulos gm = new GruposModulos();
			gm.setIdgrupomodulo(8L);
			gm.setModulo(modulo);
			
			List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
			
			//TODO: recuperar os compradores para envio
			
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
			String assunto = "SSI COMPRAS - "+demanda.getStatus().getCnmstatus();
			String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" est� aguardando propostas.";
			
			mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
			
			mensageria = null;
			listaemails = null;
			assunto = null;
			strmensagem = null;
		}
		
		desabilitaAnaliseTecnica();
		
	}
	
	public void habilitaModal(Propostas obj){
		exibepopup = true;
		proposta = new Propostas();
		
		// Se vier um obj, o obj do bean ser� esse que veio, de forma que ser� possivel alter�-lo.
		if(obj!=null){
			proposta = obj;
		}
		
		if(proposta.getStatus() == null){
			Status status = new Status();
			status.setIdstatus(idStatusPropostaEncaminhar);
			status.setCnmstatus("Proposta a Encaminhar");
			proposta.setStatus(status);
		}
	}
	
//	public void desabilitaNaoPodeEncaminhar(){
//		exibepopup = false;
//		exibenaopodeencaminhar = false;
//	}
	
	public void desabilitaModal(){
		exibepopup = false;
		exibeconfirmacao = false;
	}
	
	public void desabilitaConfirmacao(){
		exibepopup = true;
		exibeconfirmacao = false;
	}
	
	public void preGravar(){
		exibeconfirmacao = true;
	}
	
	public void gravar(){
		
		evento = new Eventos();
		evento.setStatusanterior(demanda.getStatus());
		
		// Se estiver alterando ou inserindo uma nova (j� existindo uma encaminhada na base)
		if(propostaint.verificaExistenteEncaminhada(demandasmb.getDemandas())!=null){
			Status status = new Status();
			status.setIdstatus(idStatusPropostaEmAnalise);
			status.setCnmstatus("Proposta Em An�lise");
			proposta.setStatus(status);
			
			// Prepara o envio do email
			Modulos modulo = modulosint.recuperarUnico(336L);
			
			GruposModulos gm = new GruposModulos();
			gm.setIdgrupomodulo(8L);
			gm.setModulo(modulo);
			
			List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
			//pessoa logada
			pessoasEnvio.add(demandasmb.recuperarPessoaLogada());
							
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
			
			//Solicitante
			pessoasEnvio.add(demanda.getAutor());				
			
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
			String assunto = "SSI COMPRAS - "+proposta.getStatus().getCnmstatus();
			String strmensagem = "A SSI de registro "+demanda.getCnmnumero()+" est� com Propostas em An�lise.";
			
			mensageria.enviarMensagem(strmensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), demanda.getCnmnumero());
			
			mensageria = null;
			listaemails = null;
			assunto = null;
			strmensagem = null;
		}
		proposta.setAutor(demandasmb.recuperarPessoaLogada());
		proposta.setDemanda(demanda);
		
		// Atualiza a demanda com as informa��es enviadas de proposta
		demanda.setStatusanterior(demanda.getStatus());
		demanda.setStatus(proposta.getStatus());
		propostaint.registrar(proposta);
		
		// Cria o Log
		String descricaoLog;
		descricaoLog = proposta.getStatus().getCnmstatus();
		Logs log = demandasmb.criaLog(3L, demandasmb.recuperarPessoaLogada(), descricaoLog);
		log.setDemanda(demanda);
		demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
		
		demandaint.alterar(demanda);
		
		// Registra Evento
		evento.setAutor(demandasmb.recuperarPessoaLogada());
		evento.setDemanda(getDemanda());
		evento.setStatus(proposta.getStatus());
		evento.getEgenerico().setEventogenerico(evento);// Faz o relacionamento inverso pro cascade funcionar
		eventosint.registrar(getEvento());
		
		desabilitaModal();
	}
	
	public PropostasDataModel getDataModel(){
		Map<String,Object> filtros = new LinkedHashMap<String,Object>();
		filtros.put("demanda", demandasmb.getDemandas());
		propostaschecked = new ArrayList<Propostas>();
		this.setFiltros(filtros);
		return new PropostasDataModel(propostaint, filtros);
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
	public Propostas getProposta() {
		return proposta;
	}
	public void setProposta(Propostas proposta) {
		this.proposta = proposta;
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

	public Map<String,Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}

	public List<Propostas> getPropostas() {
		return propostas;
	}

	public void setPropostas(List<Propostas> propostas) {
		this.propostas = propostas;
	}

	public List<Propostas> getPropostaschecked() {
		return propostaschecked;
	}

	public void setPropostaschecked(List<Propostas> propostaschecked) {
		this.propostaschecked = propostaschecked;
	}

	public boolean isExibeencaminhamento() {
		return exibeencaminhamento;
	}

	public void setExibeencaminhamento(boolean exibeencaminhamento) {
		this.exibeencaminhamento = exibeencaminhamento;
	}
	
	public Eventos getEvento() {
		return evento;
	}

	public void setEvento(Eventos evento) {
		this.evento = evento;
	}

//	public boolean isExibenaopodeencaminhar() {
//		return exibenaopodeencaminhar;
//	}
//
//	public void setExibenaopodeencaminhar(boolean exibenaopodeencaminhar) {
//		this.exibenaopodeencaminhar = exibenaopodeencaminhar;
//	}

	public boolean isExibeanalisetecnica() {
		return exibeanalisetecnica;
	}

	public void setExibeanalisetecnica(boolean exibeanalisetecnica) {
		this.exibeanalisetecnica = exibeanalisetecnica;
	}

	public static long getIdstatusanaliselaudotecnico() {
		return idStatusAnaliseLaudoTecnico;
	}

	public static long getIdstatusaguardandopropostas() {
		return idStatusAguardandoPropostas;
	}

	public static long getIdstatuscancelada() {
		return idStatusCancelada;
	}

	public String getAcaoanalise() {
		return acaoanalise;
	}

	public void setAcaoanalise(String acaoanalise) {
		this.acaoanalise = acaoanalise;
	}

	public int getTipoanalise() {
		return tipoanalise;
	}

	public void setTipoanalise(int tipoanalise) {
		this.tipoanalise = tipoanalise;
	}

	public boolean isExibealerta() {
		return exibealerta;
	}

	public void setExibealerta(boolean exibealerta) {
		this.exibealerta = exibealerta;
	}


}
