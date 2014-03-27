package com.ssi.compras.service;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Participantes;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Stateless(mappedName = "SLSBParticipante")
public class SLSBParticipante implements ParticipanteInt {
				
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	public SLSBParticipante() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Participantes recuperarPorPapel(Integer idpapel) {
		final String jpaquery = "SELECT part FROM Participantes part where part.papel.idpapel="+idpapel;
		TypedQuery<Participantes> q = em.createQuery(jpaquery, Participantes.class);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarResponsaveisTecnicos() {
		final String jpaquery = "SELECT pessoas from ResponsaveisTecnicos resp INNER JOIN resp.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}		
					
	}
	

	@Override
	public void definir(Participantes obj) {
		em.merge(obj);
	}

	
	@Override
	public List<Pessoas> recuperarGerentesPorLp(List<Lps> lps) {

		String jpaquery = "";
				
		if(lps!=null){			
			
			for(Lps lp:lps){
				if(lp.getIdlp()!=null){					
					switch (lp.getIdlp().intValue()) {
					case (int) Lps.ID_LP1_EQUIPAMENTOS_E_MATERIAIS:					
						
						jpaquery = "SELECT pessoas from GerentesCompras gc INNER JOIN gc.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
					
						jpaquery += " AND gc.flaglp1 = 1 ";																
						break;
						
					case (int) Lps.ID_LP2_SERVICOS:
						
						jpaquery = "SELECT pessoas from GerentesComprasLP2s gc INNER JOIN gc.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
					
						jpaquery += " AND gc.flaglp2a = 1";												
						break;
						
					case (int) Lps.ID_LP3_PRODUTO_DO_MERCADO:
						
						jpaquery = "SELECT pessoas from GerentesComprasLP3 gc INNER JOIN gc.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
						
						jpaquery += " AND gc.flaglp3 = 1";													
						break;
						
					case (int) Lps.ID_LP4_SISTEMA_DE_INFORMACAO:
						
						jpaquery = "SELECT pessoas from GerentesComprasLP4 gc INNER JOIN gc.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
	
						jpaquery += " AND gc.flaglp4 = 1";											
						break;
						
					case (int) Lps.ID_LP2_OBRA_E_INFRA:
						
						jpaquery = "SELECT pessoas from GerentesComprasLP2ro gc INNER JOIN gc.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
						
						jpaquery += " AND gc.flaglp2b = 1";											
						break;						
						
					}
				}
			}
			
		}
		
		jpaquery += " ORDER BY pessoas.cnmnome";
					
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		try{
			return q.getResultList();
		}
		catch(NoResultException ex){
			return new ArrayList<Pessoas>();
		}		
		
	}	
	
			
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarGerentesAquisicoes() {
						
		final String jpaquery = "SELECT pessoas from GerentesAquisicoes geraq INNER JOIN geraq.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}
		
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarGerentesContratos() {

		final String jpaquery = "SELECT pessoas from GerentesContratos gc INNER JOIN gc.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}	
				
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recupearAnalistasContratos() {
		
		final String jpaquery = "SELECT pessoas from AnalistasContratos analista INNER JOIN analista.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}		
		
	}
			
	@Override
	public List<Pessoas> recuperarAnalistasAquisicaoes() {
									
		final String jpaquery = "SELECT pessoas from AnalistasAquisicoes analista INNER JOIN analista.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}						
		
	}

	@Override
	public List<GruposModulos> recuperarGruposGerenteAquisicao(boolean ativo) {
		final String jpaquery = "SELECT gm from GerentesAquisicoes geraq INNER JOIN geraq.grupoModulo gm WHERE gm.flagativo = :flagativo ORDER BY gm.datacadastro DESC";
		
		TypedQuery<GruposModulos> q = em.createQuery(jpaquery, GruposModulos.class);
		
		q.setParameter("flagativo", ativo);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<GruposModulos>();
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarAnalistasOrcamentos() {
		
		final String jpaquery = "SELECT pessoas from AnalistasOrcamentos analista INNER JOIN analista.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}		
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarGerentesOrcamentos() {

		final String jpaquery = "SELECT pessoas from GerentesOrcamentos go INNER JOIN go.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarDiretores() {

		final String jpaquery = "SELECT pessoas from Diretores diretor INNER JOIN diretor.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas ORDER BY pessoas.cnmnome";		
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}
		
	}

	@Override
	public List<Pessoas> recuperarCompradoresPorLp(List<Lps> lps) {		
		String jpaquery = "";
		if(lps!=null){			
			
			for(Lps lp:lps){
				if(lp.getIdlp()!=null){					
					switch (lp.getIdlp().intValue()) {
					case (int) Lps.ID_LP1_EQUIPAMENTOS_E_MATERIAIS:
						
						jpaquery="SELECT pessoas from Compradores comprador INNER JOIN comprador.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
											
						jpaquery += " AND comprador.flaglp1 = 1 ";																
						break;
						
					case (int) Lps.ID_LP2_SERVICOS:
						
						jpaquery = "SELECT pessoas from CompradoresLP2S comprador INNER JOIN comprador.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
	
						jpaquery += " AND comprador.flaglp2a = 1";												
						break;
						
					case (int) Lps.ID_LP3_PRODUTO_DO_MERCADO:
						
						jpaquery = "SELECT pessoas from CompradoresLP3 comprador INNER JOIN comprador.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
						
						jpaquery += " AND comprador.flaglp3 = 1";													
						break;
						
					case (int) Lps.ID_LP4_SISTEMA_DE_INFORMACAO:
						
						jpaquery = "SELECT pessoas from CompradoresLP4 comprador INNER JOIN comprador.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
	
						jpaquery += " AND comprador.flaglp4 = 1";											
						break;
						
					case (int) Lps.ID_LP2_OBRA_E_INFRA:
						
						jpaquery = "SELECT pessoas from CompradoresLP2ro comprador INNER JOIN comprador.grupoModulo gm "
								+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE 1=1";
						
						jpaquery += " AND comprador.flaglp2b = 1";											
						break;						
						
					}
				}
			}
		}
		
		jpaquery += " ORDER BY pessoas.cnmnome";
					
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		try{
			return q.getResultList();
		}
		catch(NoResultException ex){
			return new ArrayList<Pessoas>();
		}	
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<String> recuperarAutocompleteRespTecnico(String autobusca) {
		final String jpaquery = "SELECT pessoas.cnmnome from ResponsaveisTecnicos resp INNER JOIN resp.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE pessoas.cnmnome like '%"+autobusca+"%' ORDER BY pessoas.cnmnome";
		
		TypedQuery<String> q = em.createQuery(jpaquery, String.class);
		return q.getResultList();
	}

	@Override
	public List<GruposModulos> recuperarGruposGerentesPorLp(boolean ativo,List<Lps> lps) {
		String jpaquery = "";
		
		if(lps!=null){			
			
			for(Lps lp:lps){
				if(lp.getIdlp()!=null){					
					switch (lp.getIdlp().intValue()) {
					case (int) Lps.ID_LP1_EQUIPAMENTOS_E_MATERIAIS:					
						
						jpaquery = "SELECT gm from GerentesCompras gercomp INNER JOIN gercomp.grupoModulo gm WHERE gm.flagativo = :flagativo ";
						
						jpaquery += " AND gercomp.flaglp1 = 1 ";																
						break;
						
					case (int) Lps.ID_LP2_SERVICOS:
						
						jpaquery = "SELECT gm from GerentesComprasLP2s gercomp INNER JOIN gercomp.grupoModulo gm WHERE gm.flagativo = :flagativo ";
	
						jpaquery += " AND gercomp.flaglp2a = 1";												
						break;
						
					case (int) Lps.ID_LP3_PRODUTO_DO_MERCADO:
						
						jpaquery = "SELECT gm from GerentesComprasLP3 gercomp INNER JOIN gercomp.grupoModulo gm WHERE gm.flagativo = :flagativo ";
						
						jpaquery += " AND gercomp.flaglp3 = 1";													
						break;
						
					case (int) Lps.ID_LP4_SISTEMA_DE_INFORMACAO:
						
						jpaquery = "SELECT gm from GerentesComprasLP4 gercomp INNER JOIN gercomp.grupoModulo gm WHERE gm.flagativo = :flagativo ";
					
						jpaquery += " AND gercomp.flaglp4 = 1";											
						break;
						
					case (int) Lps.ID_LP2_OBRA_E_INFRA:
						
						jpaquery = "SELECT gm from GerentesComprasLP2ro gercomp INNER JOIN gercomp.grupoModulo gm WHERE gm.flagativo = :flagativo ";
					
						jpaquery += " AND gercomp.flaglp2b = 1";											
						break;						
						
					}
				}
			}
			
		}		
		
		jpaquery += " ORDER BY gm.datacadastro DESC";
		
		TypedQuery<GruposModulos> q = em.createQuery(jpaquery, GruposModulos.class);
		
		q.setParameter("flagativo", ativo);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<GruposModulos>();
		}
	}

}
