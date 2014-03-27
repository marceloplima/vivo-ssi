package com.ssi.regulatorio.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.PapeisRedes;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;
import com.ssi.kernel.redes.interfaces.service.EmailRedesServiceInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.Mensageria;

@Stateless(mappedName = "EmailService")
public class EmailRedesService implements EmailRedesServiceInt {

	private Mensageria mensageria;
	
	@EJB
	private PessoasInt pessoasInt;
	
	@EJB
	private ParticipantesRedesInt participanteInt;
	
	@EJB
	private ModulosInt modulosint;
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	public EmailRedesService() {}

	@Override
	public void enviarEMail(List<Long> papeis, DemandasRedes demanda, String mensagem, String assunto, List<Pessoas> outrasPessoasParaEnvio) {
				
		mensageria = new Mensageria();
				
		List<Pessoas> pessoasEnvio = new ArrayList<Pessoas>();
				
		if(papeis.contains(PapeisRedes.ID_EMISSOR)){
			if(demanda.getAutor()!=null){
				System.out.println("Envio para emissor");
				pessoasEnvio.add(demanda.getAutor());				
			}
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
		
		Modulos modulo = modulosint.recuperarUnico(338L);
					
		if(modulo!=null){
			mensageria.enviarMensagem(mensagem, assunto, listaemails, modulo.getConfigsmtp().getCnmipsmtp(), modulo.getCnmmodulo(), 
					demanda.getCnmnumero()!=null ? demanda.getCnmnumero() : "");
		}
						
	}
			

}
