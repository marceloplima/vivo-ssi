package com.ssi.redes.service;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.ParticipantesRedes;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Stateless(mappedName = "SLSBParticipante")
public class SLSBParticipante implements ParticipantesRedesInt {
				
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	public SLSBParticipante() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<String> recuperarAutocompleteRespTecnico(String autobusca) {
		final String jpaquery = "SELECT pessoas.cnmnome from ResponsaveisTecnicosRedes resp INNER JOIN resp.grupoModulo gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE pessoas.cnmnome like '%"+autobusca+"%' ORDER BY pessoas.cnmnome";
		
		TypedQuery<String> q = em.createQuery(jpaquery, String.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarResponsaveisTecnicos() {
		final String jpaquery = "SELECT pessoas from ResponsaveisTecnicosRedes resp INNER JOIN resp.grupoModulo gm "
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
	public ParticipantesRedes recuperarPorPapel(Integer idpapel) {
		final String jpaquery = "SELECT part FROM ParticipantesRedes part where part.papel.idpapel="+idpapel;
		TypedQuery<ParticipantesRedes> q = em.createQuery(jpaquery, ParticipantesRedes.class);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Pessoas> recuperarEmissores() {

		final String jpaquery = "SELECT pessoas from EmissoresRedes emissor INNER JOIN emissor.grupoModulo gm "
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
	public List<Pessoas> recuperarRequisitados() {

		final String jpaquery = "SELECT pessoas from RequisitadosRedes requisitado INNER JOIN requisitado.grupoModulo gm "
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
	public List<Pessoas> recuperarCopiados() {

		final String jpaquery = "SELECT pessoas from CopiadosRedes copiado INNER JOIN copiado.grupoModulo gm "
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
	public List<Pessoas> recuperarFaturamento() {

		final String jpaquery = "SELECT pessoas from GruposModulos gm "
				+ " INNER JOIN gm.gruposmodulospessoas as pessoas WHERE gm.cnmgrupomodulo = 'FATURAMENTO' ORDER BY pessoas.cnmnome";		
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}
		
	}
	
	@Override
	public List<GruposModulos> recuperarGrupoEmissores(boolean ativo) {
		final String jpaquery = "SELECT gm from EmissoresRedes emissores INNER JOIN emissores.grupoModulo gm WHERE gm.flagativo = :flagativo ORDER BY gm.datacadastro DESC";
		
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
	public List<GruposModulos> recuperarGrupoRequisitados(boolean ativo) {
		final String jpaquery = "SELECT gm from RequisitadosRedes req INNER JOIN req.grupoModulo gm WHERE gm.flagativo = :flagativo ORDER BY gm.datacadastro DESC";
		
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
	public List<GruposModulos> recuperarGrupoCopiados(boolean ativo) {
		final String jpaquery = "SELECT gm from CopiadosRedes cop INNER JOIN cop.grupoModulo gm WHERE gm.flagativo = :flagativo ORDER BY gm.datacadastro DESC";
		
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
	public void definir(ParticipantesRedes obj) {
		em.merge(obj);
	}

	@Override
	public List<Pessoas> recuperarPessoasGruposCopiados(boolean ativo) {
				
		final String jpaquery = "SELECT pessoas from CopiadosRedes cop INNER JOIN cop.grupoModulo gm " 
				+ "  INNER JOIN gm.gruposmodulospessoas as pessoas WHERE gm.flagativo = :flagativo ORDER BY gm.datacadastro DESC";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		q.setParameter("flagativo", ativo);
		
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return new ArrayList<Pessoas>();
		}
	}

	

}
