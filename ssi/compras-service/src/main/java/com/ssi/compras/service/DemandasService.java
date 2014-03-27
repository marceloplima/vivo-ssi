package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.compras.common.interfaces.DemandasServiceInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Cronogramas;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Stateless(mappedName = "DemandasService")
public class DemandasService implements DemandasServiceInt {
	
	@EJB 
	private ParticipanteInt participanteInt;
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	public DemandasService() {}

	@Override
	public Demandas setaPessoaComQuemEsta(Pessoas pessoa, Demandas demanda) {
		demanda.setGrupocomquemesta(null);
		demanda.setPessoacomquemesta(pessoa);
		return demanda;				
	}

	@Override
	public Demandas setaGrupoComQuemEsta(Papeis papel, Demandas demanda, List<Lps> lps) {
		demanda.setPessoacomquemesta(null);
		List<GruposModulos> grupos = new ArrayList<GruposModulos>();
		GruposModulos grupoComQuemEsta=null;
		
		switch(papel.getIdpapel().intValue())
		{
			    	
		    case (int) Papeis.ID_GERENTE_AQUISICAO:
		    	
		    	grupos.addAll(participanteInt.recuperarGruposGerenteAquisicao(true));
		    	
		    	if(!grupos.isEmpty()){
		    		grupoComQuemEsta = grupos.get(0); 
		    	}
		    				    	
		    	break;
		    	
		    case (int) Papeis.ID_GERENTE_COMPRA:
		    	
		    	grupos.addAll(participanteInt.recuperarGruposGerentesPorLp(true, lps));
		    	
		    	if(!grupos.isEmpty()){
		    		grupoComQuemEsta = grupos.get(0); 
		    	}
		    				    	
		    	break;		    	
		
		}
		
		demanda.setGrupocomquemesta(grupoComQuemEsta);
		return demanda;
	}

	@Override
	public Demandas setarAtividadeCronograma(Demandas demanda, Atividades atividade, Pessoas pessoaConclusao) {
		for(Cronogramas cronograma:demanda.getCronogramas()){
			if(cronograma.getAtividade().getIdatividade().equals(atividade.getIdatividade())){
				cronograma.setDatarealizacao(Calendar.getInstance());
				cronograma.setResponsavel(pessoaConclusao);
			}
		}
		
		return demanda;
	}
	
	
}
