package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.compras.common.interfaces.EmailServiceInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.Mensageria;

@Stateless(mappedName = "EmailService")
public class EmailService implements EmailServiceInt {

	private Mensageria mensageria;
	
	@EJB
	private PessoasInt pessoasInt;
	
	@EJB
	private ParticipanteInt participanteInt;
	
	@EJB
	private ModulosInt modulosint;
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	public EmailService() {}

	@Override
	public void enviarEMail(List<Long> papeis, Demandas demanda, String mensagem, String assunto, List<Pessoas> outrasPessoasParaEnvio) {
				
		mensageria = new Mensageria();
				
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
				
		if(papeis.contains(Papeis.ID_EMISSOR)){
			if(demanda.getAutor()!=null){
				System.out.println("Envio para emissor");
				pessoasEnvio.add(demanda.getAutor());				
			}
		}

		if(papeis.contains(Papeis.ID_ANALISTA_AQUISICAO)){
			if(demanda.getAnalistaDeAquisicaoResponsavel()!=null){
				System.out.println("Envio analista aquisi��o");
				pessoasEnvio.add(demanda.getAnalistaDeAquisicaoResponsavel());				
			}
		}
		
		if(papeis.contains(Papeis.ID_RESPONSAVEL_TECNICO)){
			if(demanda.getResponsaveltecnico()!=null){
				System.out.println("Envio respons�vel t�cnico");
				pessoasEnvio.add(demanda.getResponsaveltecnico());				
			}
		}
		
		if(papeis.contains(Papeis.ID_GERENTE_COMPRA)){
			if(demanda.getLp()!=null){
				List<Lps> lps = new ArrayList<Lps>();
				
				lps.add(demanda.getLp());

				pessoasEnvio.addAll(participanteInt.recuperarGerentesPorLp(lps));
				System.out.println("Envio para gerente de compras");
			}
		}
				
		if(papeis.contains(Papeis.ID_ANALISTA_CONTRATO)){
			if(demanda.getAnalistaDeContratoResponsavel()!=null){
				System.out.println("Envio para analista de contrato");
				pessoasEnvio.add(demanda.getAnalistaDeContratoResponsavel());				
			}			
		}
				
		
		if(papeis.contains(Papeis.ID_GERENTE_AQUISICAO)){
			System.out.println("Envio para gerente de aquisi��o");
			pessoasEnvio.addAll(participanteInt.recuperarGerentesAquisicoes());
		}
		
		if(papeis.contains(Papeis.ID_GERENTE_CONTRATO)){
			System.out.println("Envio para gerente de contratos");
			pessoasEnvio.addAll(participanteInt.recuperarGerentesContratos());
		}
		
		if(papeis.contains(Papeis.ID_ANALISTA_ORCAMENTO)){
			System.out.println("Envio para analista de or�amentos");
			pessoasEnvio.addAll(participanteInt.recuperarAnalistasOrcamentos());
		}
		
		if(papeis.contains(Papeis.ID_GERENTE_ORCAMENTO)){
			System.out.println("Envio para gerente de or�amentos");
			pessoasEnvio.addAll(participanteInt.recuperarGerentesOrcamentos());
		}
		
		if(papeis.contains(Papeis.ID_COMPRADOR)){			
			if(demanda.getPessoacomprador()!=null){
				System.out.println("Envio para comprador");
				pessoasEnvio.add(demanda.getPessoacomprador());				
			}
		}

		if(papeis.contains(Papeis.ID_DIRETOR)){
			System.out.println("Envio para diretor");
			pessoasEnvio.addAll(participanteInt.recuperarDiretores());
		}
		
		if(outrasPessoasParaEnvio!=null){
			System.out.println("Envio para outras pessoas");
			pessoasEnvio.addAll(outrasPessoasParaEnvio);
		}
		
							
		Map<String,String>listaemails = new HashMap<String,String>();
		
		List<Emails> emails;
		
		for(Pessoas pessoa:pessoasEnvio){
			emails = new ArrayList<Emails>();
			emails = pessoasInt.retornarEmailsPessoa(pessoa);
			for(Emails email:emails){
				listaemails.put(pessoa.getCnmnome(),email.getCnmemail());	
			}
			
		}
		
		Modulos modulo = modulosint.recuperarUnico(336L);
					
		if(modulo!=null){
			mensageria.enviarMensagem(mensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), 
					demanda.getCnmnumero()!=null ? demanda.getCnmnumero() : "");
		}
						
	}
			

}
