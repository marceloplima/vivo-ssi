package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Anexos;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Leiloes;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.compras.common.interfaces.PermissoesInt;
import com.ssi.compras.common.interfaces.PropostasInt;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class PermissoesMB implements Serializable{

	private static final long serialVersionUID = -2517868557669308792L;
				
	@EJB
	private PermissoesInt permissoesInt;
	
	@EJB
	private PropostasInt propostaint;
	
	private Demandas demanda;
	
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	@PostConstruct
	public void init() {
		if(demanda == null){
			demanda = demandasmb.getDemandas();
			if(demanda == null){
				demanda = new Demandas();
				demanda.setIddemanda(Status.ID_DEMANDA_INICIAL);
				demanda.setAutor(demandasmb.recuperarPessoaLogada());
			}
		}
	}
	
	public boolean verificarQuestionamentoTecnicoAba(){
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_INICIAL);
		statuspossiveis.add(Status.ID_DEMANDA_RASCUNHO);
		statuspossiveis.add(Status.ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO);
		statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR);
		statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
		statuspossiveis.add(Status.ID_DEMANDA_ABERTURA_REQUISICAO_COMPRA);
		
		return statuspossiveis.contains(demanda.getStatus().getIdstatus());

	}
	
	public boolean verificarQuestionamentoTecnicoInclusao(){
		
		return true;
	}
	
	public boolean verificarQuestionamentoTecnicoResponder(){
		return true;
	}
	
	public boolean verificarAlterarCronograma(){
		
		if(!verificarDemandaPersistida()){
			return false;
		}
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_RASCUNHO);
		statuspossiveis.add(Status.ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO);
		statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR);
		statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
		statuspossiveis.add(Status.ID_DEMANDA_ABERTURA_REQUISICAO_COMPRA);
		statuspossiveis.add(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA);
		statuspossiveis.add(Status.ID_DEMANDA_ENVIO_AO_MERCADO);
		statuspossiveis.add(Status.ID_DEMANDA_ACIONA_CRIACAO_RC);
		statuspossiveis.add(Status.ID_DEMANDA_INDICA_NOVO_COMPRADOR);
		statuspossiveis.add(Status.ID_DEMANDA_REGISTRA_LEILAO);
		statuspossiveis.add(Status.ID_DEMANDA_FINALIZA_LEILAO);
		statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_TECNICA);
		statuspossiveis.add(Status.ID_DEMANDA_CANCELADA);
		statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA);
		statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_ANALISTA_AQUISICAO);
		statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_PROPOSTA);
		statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_LAUDO_TECNICO);
		statuspossiveis.add(Status.ID_DEMANDA_AGENDAMENTO_MESA_COMPRA);
		statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_SEGUNDO_NIVEL);
		statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_RESULTADO_MESA_COMPRA);
		statuspossiveis.add(Status.ID_DEMANDA_COMPRA_ADJUDICADA);
		
		if(statuspossiveis.contains(demanda.getStatus().getIdstatus())){
			
			if(permissoesInt.verificarPermissaoAnalistaAquisicoes(demandasmb.recuperarPessoaLogada())){
				return true;
			}
			else{
				boolean permite = false;
				Lps lp = demandasmb.getDemandas().getLp();
				if(lp != null && lp.getIdlp() == 1){
					permite = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 2){
					permite = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 3){
					permite = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 4){
					permite = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 5){
					permite = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
				}
				
				return permite;
			}
		}
		
		return false;
				
	}
	
	public boolean verificarUploadEmissor(){
		if(verificarDemandaPersistida()){
			boolean isemissor = permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
			
			if(isemissor){
				List<Long>statuspossiveis = new ArrayList<Long>();
				statuspossiveis.add(Status.ID_DEMANDA_INICIAL);
				statuspossiveis.add(Status.ID_DEMANDA_RASCUNHO);
				statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
				
				boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
				
				if(statuspermite){
					return true;
				}
				
				return false;
				
			}
			else{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean verificarPermissoesEmissor(){
		if(verificarDemandaPersistida()){
			boolean permitido = false;
			
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_INICIAL);
			statuspossiveis.add(Status.ID_DEMANDA_RASCUNHO);
			statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
			
			boolean papel = permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
			
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
				
			if(papel && statuspermite){
				permitido = true;
			}
				
			return permitido;
		}
		return false;
	}
	
	public boolean verificarPermissoesResponsavelTecnico(){
		
		if(verificarDemandaPersistida()){
			boolean permitido = false;
			
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_RASCUNHO);
			statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
			
			boolean papel = permissoesInt.verificarPermissaoResponsavelTecnico(demandasmb.recuperarPessoaLogada());
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
			
			if(papel && statuspermite){
				permitido = true;
			}
					
			return permitido;
		}
		return false;
	}
	
	// Verifica se demanda est� no status anterior para permitir An�lise de Aquisi��o e se o usuario logado tem permiss�o 
	public boolean verificarAnaliseAquisicao(){
		
		if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO);
			
			return statuspossiveis.contains(demanda.getStatus().getIdstatus()) 
					&&isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada());
		}
		return false;

	}
	
	// Verifica se demanda est� em alguns dos status anteriores para permitir Indica��o de Novo Analista de Aquisi��es e se o usuario logado tem permiss�o 
	public boolean verificarIndicaNovoAnalista(){
		if(verificarDemandaPersistida()){
			if(permissoesInt.verificarPermissaoGerenteAquisicoes(demandasmb.recuperarPessoaLogada())){
					
				List<Long>statuspossiveis = new ArrayList<Long>();
				statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR);
				statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
				statuspossiveis.add(Status.ID_DEMANDA_ABERTURA_REQUISICAO_COMPRA);
				statuspossiveis.add(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA);
				statuspossiveis.add(Status.ID_MINUTA_ANALISE);
				statuspossiveis.add(Status.ID_DEMANDA_ENVIO_AO_MERCADO);
				statuspossiveis.add(Status.ID_DEMANDA_ACIONA_CRIACAO_RC);
				statuspossiveis.add(Status.ID_DEMANDA_INDICA_NOVO_COMPRADOR);
				statuspossiveis.add(Status.ID_DEMANDA_REGISTRA_LEILAO);
				statuspossiveis.add(Status.ID_DEMANDA_FINALIZA_LEILAO);
				statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_TECNICA);
					
				return statuspossiveis.contains(demanda.getStatus().getIdstatus());
					
			}
		}
		return false;
	}
	
	public boolean verificarConcluir(){
		if(!verificarDemandaPersistida()){
			return false;
		}
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_COMPRA_ADJUDICADA);		
		
		if(!statuspossiveis.contains(demanda.getStatus().getIdstatus())){
			return false;
		}
		
		if(isGerenteAquisicao(demandasmb.recuperarPessoaLogada()) || 
				isAnalistaAquisicao(demandasmb.recuperarPessoaLogada())){
			return true;
		}
		
		return false;
	}
	
	public boolean verificarEncaminhar(){
		
		if(demanda.getIddemanda()==null){
			return isEmissor(demandasmb.recuperarPessoaLogada());
		}
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_INICIAL);
		statuspossiveis.add(Status.ID_DEMANDA_RASCUNHO);
		statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR);
		
		return isEmissor(demandasmb.recuperarPessoaLogada()) 
				&& statuspossiveis.contains(demanda.getStatus().getIdstatus());
	}
	
	public boolean verificarValidacaoTecnica(){
		if(verificarDemandaPersistida()){
			if(permissoesInt.verificarPermissaoAnalistaAquisicoes(demandasmb.recuperarPessoaLogada())){
						
				List<Long>statuspossiveis = new ArrayList<Long>();
				statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA);
						
				boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
						
				if(statuspermite)
					return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean verificarAcionaCriarRC(){
		if(verificarDemandaPersistida()){
			
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA);
					
			if (!statuspossiveis.contains(demanda.getStatus().getIdstatus())){
				return false;
			}
						
			return permissoesInt.verificarPermissaoAnalistaAquisicoes(demandasmb.recuperarPessoaLogada());
		}
		return false;
	}
	
	public boolean verificarMesaDeCompra(){		
		
		if(!verificarDemandaPersistida()){
			return false;
		}
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_AGENDAMENTO_MESA_COMPRA);
		
		return statuspossiveis.contains(demanda.getStatus().getIdstatus())				
				&& (isGerenteAquisicao(demandasmb.recuperarPessoaLogada())||
				isAnalistaAquisicao(demandasmb.recuperarPessoaLogada()) ||
				isGerenteCompras() ||
				isComprador(demandasmb.recuperarPessoaLogada()));
				
	}
	
	private boolean isComprador(Pessoas pessoaLogada) {
		
		if(!verificarDemandaPersistida()){
			return false;
		}
		
		if(demanda.getPessoacomprador()==null){
			return false;
		}
		
		return demanda.getPessoacomprador().equals(pessoaLogada);
		
	}
	
	public boolean verificarUploadMinuta(){
		return isGerenteContrato(demandasmb.recuperarPessoaLogada()) || isAnalistaAquisicao(demandasmb.recuperarPessoaLogada());
	}
	
	public boolean verificarUploadProposta(){
		boolean permite = false;
		Lps lp = demandasmb.getDemandas().getLp();
		if(lp!=null && lp.getIdlp() == 1){
			permite = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
		}
		if(lp!=null && lp.getIdlp() == 2){
			permite = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
		}
		if(lp!=null && lp.getIdlp() == 3){
			permite = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
		}
		if(lp!=null && lp.getIdlp() == 4){
			permite = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
		}
		if(lp!=null && lp.getIdlp() == 5){
			permite = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
		}
		
		return permite;
	}
	

	private boolean isGerenteContrato(Pessoas pessoa){
		return verificarDemandaPersistida() && permissoesInt.verificarPermissaoGerenteContrato(pessoa);
	}
	
	private boolean isAnalistaAquisicao(Pessoas pessoa){
		return verificarDemandaPersistida() && permissoesInt.verificarPermissaoAnalistaAquisicoes(pessoa);
	}
	
	private boolean isGerenteAquisicao(Pessoas pessoa){
		return verificarDemandaPersistida() && permissoesInt.verificarPermissaoGerenteAquisicoes(pessoa);
	}
	
	private boolean isGerenteCompras(){
		return verificarDemandaPersistida() && permissoesInt.verificarPermissaoGerenteCompras(demandasmb.recuperarPessoaLogada(), demanda.getLp());
	}
	
	private boolean isDiretor(Pessoas pessoa){
		return permissoesInt.verificarPermissaoDiretor(pessoa);
	}

	public boolean verificarCancelarSSI(){
		if(verificarDemandaPersistida()){
			List<Long>statusNaoPossiveis = new ArrayList<Long>();
			statusNaoPossiveis.add(Status.ID_DEMANDA_INICIAL);
			statusNaoPossiveis.add(Status.ID_DEMANDA_CANCELADA);
			
			return (isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada()))
					&& !statusNaoPossiveis.contains(demanda.getStatus().getIdstatus());
			}
		return false;
	}
	
	private boolean isAnalistaAquisicaoOuGerenteAquisicao(
			Pessoas pessoa) {		
		return (isAnalistaAquisicao(pessoa) || isGerenteAquisicao(pessoa));
	}

	public boolean verificarIndicarAnalistaDeContrato(){
		
		//if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR);
			statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
			statuspossiveis.add(Status.ID_DEMANDA_ABERTURA_REQUISICAO_COMPRA);
			statuspossiveis.add(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA);
			statuspossiveis.add(Status.ID_MINUTA_ANALISE);
			statuspossiveis.add(Status.ID_DEMANDA_ENVIO_AO_MERCADO);
			statuspossiveis.add(Status.ID_DEMANDA_ACIONA_CRIACAO_RC);
			statuspossiveis.add(Status.ID_DEMANDA_INDICA_NOVO_COMPRADOR);
			statuspossiveis.add(Status.ID_DEMANDA_REGISTRA_LEILAO);
			statuspossiveis.add(Status.ID_DEMANDA_FINALIZA_LEILAO);
			statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_TECNICA);
			statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_PELO_ANALISTA);
			statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_ANALISTA_AQUISICAO);
			statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_PROPOSTA);
			statuspossiveis.add(Status.ID_DEMANDA_ANALISE_PRELIMINAR_AQUISICAO);
				
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
				
			return statuspermite && isGerenteContrato(demandasmb.recuperarPessoaLogada());
		//}
		
		//return false;
		
	}
	
	public boolean verificarAnalisPeloGerenteDeCompra(){
		if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA);
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
			return statuspermite && isGerenteCompras();
		}
		return false;
	}
	
	public boolean verificarSolicitacaoRevisaoAquisicao(){
		if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_ENVIO_AO_MERCADO);
			statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_PROPOSTA);
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
			
			if(statuspermite){
				boolean permite = false;
				Lps lp = demandasmb.getDemandas().getLp();
				if(lp != null && lp.getIdlp() == 1){
					permite = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 2){
					permite = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 3){
					permite = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 4){
					permite = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 5){
					permite = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
				}
				
				if(permite){
					return isComprador(demandasmb.recuperarPessoaLogada());
				}
				
				return false;
			}
		}
		return false;
	}
	
	public boolean verificarIndicaNovoComprador(){
		if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR);
			statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
			statuspossiveis.add(Status.ID_DEMANDA_ABERTURA_REQUISICAO_COMPRA);
			statuspossiveis.add(Status.ID_DEMANDA_ANALISE_GERENTE_COMPRA);
			statuspossiveis.add(Status.ID_MINUTA_ANALISE);
			statuspossiveis.add(Status.ID_DEMANDA_ENVIO_AO_MERCADO);
			statuspossiveis.add(Status.ID_DEMANDA_ACIONA_CRIACAO_RC);
			statuspossiveis.add(Status.ID_DEMANDA_INDICA_NOVO_COMPRADOR);
			statuspossiveis.add(Status.ID_DEMANDA_REGISTRA_LEILAO);
			statuspossiveis.add(Status.ID_DEMANDA_FINALIZA_LEILAO);
			statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_TECNICA);
			statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_ANALISTA_AQUISICAO);
			statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_PROPOSTA);
				
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));			
			
			return statuspermite && isGerenteCompras() && isJaExisteCompradorCadastrado();
		}
		return false;
	}
	
	private boolean isJaExisteCompradorCadastrado(){
		return demanda.getPessoacomprador() == null ? false : true;
	}
	
	public boolean verificarRevisaoAnalistaAquisicao(){
		if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_ANALISTA_AQUISICAO);
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
			return statuspermite && permissoesInt.verificarPermissaoAnalistaAquisicoes(demandasmb.recuperarPessoaLogada());
		}
		return false;
	}
	
	public boolean verificarEnvioAoMercado(){
		if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_ENVIO_AO_MERCADO);
			statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_PROPOSTA);
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
			
			if(statuspermite){
				boolean permite = false;
				Lps lp = demandasmb.getDemandas().getLp();
				if(lp != null && lp.getIdlp() == 1){
					permite = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 2){
					permite = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 3){
					permite = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 4){
					permite = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 5){
					permite = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
				}
				
				if(permite){
					return isComprador(demandasmb.recuperarPessoaLogada());
				}
				
				return false;
			}
		}
		return false;
	}
	
	public boolean editaCamposDadosDeAberturaDemanda(){
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_REVISAO_PELO_EMISSOR);
		statuspossiveis.add(Status.ID_DEMANDA_INICIAL);
		statuspossiveis.add(Status.ID_DEMANDA_RASCUNHO);
						
		return statuspossiveis.contains(demanda.getStatus().getIdstatus()) 
				&& isEmissor(demandasmb.recuperarPessoaLogada());
		
	}
	
	private boolean isEmissor(Pessoas pessoaLogada) {						
		return demanda.getAutor().equals(pessoaLogada) 
				&& permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
	}
	
	public boolean verificarPodeRemoverAnexo(Anexos anexo){
		if(anexo.getAutor().getIdpessoa() == demandasmb.recuperarPessoaLogada().getIdpessoa()){
			return true;
		}
		
		return false;
	}
	
	public boolean verificarRegistrarProposta(){
		
		if(verificarDemandaPersistida())
		{
			List<Long>statuspossiveis = new ArrayList<Long>();
			if(propostaint.verificaExistenteDemanda(demandasmb.getDemandas())==null){
				statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_PROPOSTA);
			}
			else if(propostaint.verificaExistenteEncaminhada(demandasmb.getDemandas())!=null){
				statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
			}	
			if(statuspossiveis.contains(demanda.getStatus().getIdstatus())){
				boolean permite = false;
				Lps lp = demandasmb.getDemandas().getLp();
				if(lp != null && lp.getIdlp() == 1){
					permite = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 2){
					permite = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 3){
					permite = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 4){
					permite = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 5){
					permite = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
				}
				
				if(permite){
					return isComprador(demandasmb.recuperarPessoaLogada());
				}
				
				return false;
			}
		}
		
		return false;
	}
	
	public boolean verificarAlterarProposta(){
		if(verificarDemandaPersistida()){
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
			
			if(statuspossiveis.contains(demanda.getStatus().getIdstatus())){
				boolean permite = false;
				Lps lp = demandasmb.getDemandas().getLp();
				if(lp != null && lp.getIdlp() == 1){
					permite = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 2){
					permite = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 3){
					permite = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 4){
					permite = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 5){
					permite = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
				}
				
				if(permite){
					return isComprador(demandasmb.recuperarPessoaLogada());
				}
				
				return false;
			}
		}
		return false;
	}
	
	public boolean verificarEncaminharProposta(){
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_PROPOSTA_A_ENCAMINHAR);
	
		if(statuspossiveis.contains(demanda.getStatus().getIdstatus())){
			boolean permite = false;
			Lps lp = demandasmb.getDemandas().getLp();
			if(lp != null && lp.getIdlp() == 1){
				permite = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 2){
				permite = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 3){
				permite = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 4){
				permite = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 5){
				permite = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
			}
			
			if(permite){
				return isComprador(demandasmb.recuperarPessoaLogada());
			}
			
			return false;
		}
		return false;
	}
	
	public boolean verificarAnaliseProposta(){
		if(verificarDemandaPersistida()){
			if(demandasmb.getDemandas().getResponsaveltecnico()!=null || demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel() != null){
				List<Long>statuspossiveis = new ArrayList<Long>();
				statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
				 
				if(!statuspossiveis.contains(demanda.getStatus().getIdstatus())){
					return false;
				}
				 
				return demandasmb.getDemandas().getResponsaveltecnico().equals(demandasmb.recuperarPessoaLogada()) 
				|| demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel().equals(demandasmb.recuperarPessoaLogada()) 
				|| permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada()); 

			}
			 
			System.out.println("COMPRAS :: ATEN��O -> demandasmb.getDemandas().getResponsaveltecnico() ou demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel() retornaram null");
		}
		return false;
	}
	
	public boolean verificarAnaliseMinuta(){
		if(verificarDemandaPersistida() && !demandasmb.getDemandas().isBoolanaliseminuta()){
			
			if(demandasmb.getDemandas().getAnalistaDeContratoResponsavel()!=null || permissoesInt.verificarPermissaoGerenteContrato(demandasmb.recuperarPessoaLogada())){
				List<Long>statuspossiveis = new ArrayList<Long>();
				statuspossiveis.add(Status.ID_DEMANDA_PROPOSTA_EM_ANALISE);
				
				if(statuspossiveis.contains(demanda.getStatus().getIdstatus())){
					return demandasmb.getDemandas().getResponsaveltecnico() == demandasmb.recuperarPessoaLogada() || demandasmb.getDemandas().getAnalistaDeAquisicaoResponsavel() == demandasmb.recuperarPessoaLogada() || permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
				}
			}
			
			System.out.println("COMPRAS :: ATEN��O -> demandasmb.getDemandas().getAnalistaDeContratoResponsavel() ou permissoesInt.verificarPermissaoGerenteContrato(demandasmb.recuperarPessoaLogada()) retornaram null");
		}
		return false;
	}
	
	public boolean verificarValidacaoLaudoTecnico(){
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_LAUDO_TECNICO);
		
		if(!statuspossiveis.contains(demanda.getStatus().getIdstatus())){
			return false;
		}
		
		return isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada());
		
	}
	
	public boolean verificarValidacaoSegundoNivel(){
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_VALIDACAO_SEGUNDO_NIVEL);
		
		if(!statuspossiveis.contains(demanda.getStatus().getIdstatus())){
			return false;
		}
						
		return isDiretor(demandasmb.recuperarPessoaLogada());
		
	}
	
	public boolean verificarRegistrarLeilao(){
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(Status.ID_DEMANDA_AGUARDANDO_RESULTADO_MESA_COMPRA);
		statuspossiveis.add(Status.ID_DEMANDA_COMPRA_ADJUDICADA);
		
		if(statuspossiveis.contains(demanda.getStatus().getIdstatus())){
			boolean permitecomprador = false;
			Lps lp = demandasmb.getDemandas().getLp();
			if(lp != null && lp.getIdlp() == 1){
				permitecomprador = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 2){
				permitecomprador = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 3){
				permitecomprador = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 4){
				permitecomprador = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
			}
			if(lp != null && lp.getIdlp() == 5){
				permitecomprador = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
			}
			
			if(permitecomprador){
				permitecomprador =  isComprador(demandasmb.recuperarPessoaLogada());
			}
			
			return permitecomprador || isGerenteCompras() || isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada());
		}
		return false;
	}
	
	public boolean verificarFinalizarLeilao(Leiloes leilao){
		if(leilao.getStatus()!=null){
			if(leilao.getStatus().getIdstatus() == Status.ID_LEILAO_EM_ABERTO){
				
				boolean permitecomprador = false;
				Lps lp = demandasmb.getDemandas().getLp();
				if(lp != null && lp.getIdlp() == 1){
					permitecomprador = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 2){
					permitecomprador = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 3){
					permitecomprador = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 4){
					permitecomprador = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
				}
				if(lp != null && lp.getIdlp() == 5){
					permitecomprador = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
				}
				
				if(permitecomprador){
					permitecomprador =  isComprador(demandasmb.recuperarPessoaLogada());
				}
				
				return permitecomprador || isGerenteCompras() || isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada());
			}
		}
		return false;
	}
	
	public boolean verificarCompletarLeilao(Leiloes leilao){
		if(leilao.getStatus()!=null){
			if(leilao.getStatus().getIdstatus() == Status.ID_LEILAO_EM_ABERTO){
				if(leilao.getLancevencedor().equals("") && leilao.getComentario().equals("")){
					boolean permitecomprador = false;
					Lps lp = demandasmb.getDemandas().getLp();
					if(lp != null && lp.getIdlp() == 1){
						permitecomprador = permissoesInt.verificarPermissaoCompradorLP1(demandasmb.recuperarPessoaLogada());
					}
					if(lp != null && lp.getIdlp() == 2){
						permitecomprador = permissoesInt.verificarPermissaoCompradorLP2s(demandasmb.recuperarPessoaLogada());
					}
					if(lp != null && lp.getIdlp() == 3){
						permitecomprador = permissoesInt.verificarPermissaoCompradorLP3(demandasmb.recuperarPessoaLogada());
					}
					if(lp != null && lp.getIdlp() == 4){
						permitecomprador = permissoesInt.verificarPermissaoCompradorLP4(demandasmb.recuperarPessoaLogada());
					}
					if(lp != null && lp.getIdlp() == 5){
						permitecomprador = permissoesInt.verificarPermissaoCompradorLP2ro(demandasmb.recuperarPessoaLogada());
					}
					
					if(permitecomprador){
						permitecomprador =  isComprador(demandasmb.recuperarPessoaLogada());
					}
					
					return permitecomprador || isGerenteCompras() || isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada());
				}
			}
		}
		return false;
	}
	
	public boolean verificarPermissoesSalvar(){
		if(verificarDemandaPersistida()){
			if((demandasmb.getDemandas().getStatus().getIdstatus() != Status.ID_DEMANDA_CANCELADA && demandasmb.getDemandas().getStatus().getIdstatus() != Status.ID_DEMANDA_CONCLUIDA && demandasmb.getDemandas().getStatus().getIdstatus() != Status.ID_DEMANDA_RASCUNHO)){
				if(demandasmb.getDemandas().getStatus().getIdstatus() == Status.ID_DEMANDA_REVISAO_PELO_EMISSOR){
					return permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
				}
				else{
					return isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada());
				}
			}
		}
		return false;
	}
	
	public boolean verificarPermissoesSalvarOrcamento(){
		if(verificarDemandaPersistida()){
			if((demandasmb.getDemandas().getStatus().getIdstatus() != Status.ID_DEMANDA_CANCELADA && demandasmb.getDemandas().getStatus().getIdstatus() != Status.ID_DEMANDA_CONCLUIDA)){
				if(demandasmb.getDemandas().getStatus().getIdstatus() == Status.ID_DEMANDA_REVISAO_PELO_EMISSOR || demandasmb.getDemandas().getStatus().getIdstatus() == Status.ID_DEMANDA_RASCUNHO){
					return permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
				}
				else{
					return isAnalistaAquisicaoOuGerenteAquisicao(demandasmb.recuperarPessoaLogada());
				}
			}
		}
		return false;
	}
	
	public boolean verificarDemandaPersistida(){
		if(demandasmb.getDemandas() != null || demandasmb.getDemandas().getIddemanda()>=1){
			return true;
		}
		return false;
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
}
