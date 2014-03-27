package com.ssi.regulatorio.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.PapeisRedes;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;
import com.ssi.kernel.redes.interfaces.service.DemandasRedesServiceInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Stateless(mappedName = "DemandasRedesService")
public class DemandasRedesService implements DemandasRedesServiceInt {
	
	@EJB
	private ParticipantesRedesInt participanteInt;
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	public DemandasRedesService() {}

	@Override
	public DemandasRedes setaPessoaComQuemEsta(Pessoas pessoa, DemandasRedes demanda) {
		demanda.setGrupocomquemesta(null);
		demanda.setPessoacomquemesta(pessoa);
		return demanda;				
	}

	@Override
	public DemandasRedes setaGrupoComQuemEsta(PapeisRedes papel, DemandasRedes demanda) {
		demanda.setPessoacomquemesta(null);
		List<GruposModulos> grupos = new ArrayList<GruposModulos>();
		GruposModulos grupoComQuemEsta=null;
		
		switch(papel.getIdpapel().intValue())
		{    
		    case (int) PapeisRedes.ID_EMISSOR:
		    	
		    	grupos.addAll(participanteInt.recuperarGrupoEmissores(true));
		    	
		    	if(!grupos.isEmpty()){
		    		grupoComQuemEsta = grupos.get(0); 
		    	}
		    				    	
		    break;
		    
		    case (int) PapeisRedes.ID_REQUISITADO:
		    	
		    	grupos.addAll(participanteInt.recuperarGrupoRequisitados(true));
		    	
		    	if(!grupos.isEmpty()){
		    		grupoComQuemEsta = grupos.get(0); 
		    	}
		    				    	
		    break;
		    
		    case (int) PapeisRedes.ID_COPIADO:
		    	
		    	grupos.addAll(participanteInt.recuperarGrupoCopiados(true));
		    	
		    	if(!grupos.isEmpty()){
		    		grupoComQuemEsta = grupos.get(0); 
		    	}
		    				    	
		    break;
		
		}
		
		demanda.setGrupocomquemesta(grupoComQuemEsta);
		return demanda;
	}	
	
}
