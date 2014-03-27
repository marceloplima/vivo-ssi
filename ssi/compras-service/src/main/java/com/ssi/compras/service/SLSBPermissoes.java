package com.ssi.compras.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.PermissoesInt;
import com.ssi.kernel.compras.domain.AnalistasAquisicoes;
import com.ssi.kernel.compras.domain.AnalistasContratos;
import com.ssi.kernel.compras.domain.AnalistasOrcamentos;
import com.ssi.kernel.compras.domain.Compradores;
import com.ssi.kernel.compras.domain.Diretores;
import com.ssi.kernel.compras.domain.Emissores;
import com.ssi.kernel.compras.domain.GerentesAquisicoes;
import com.ssi.kernel.compras.domain.GerentesCompras;
import com.ssi.kernel.compras.domain.GerentesContratos;
import com.ssi.kernel.compras.domain.GerentesOrcamentos;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.ResponsaveisTecnicos;
import com.ssi.kernel.model.Pessoas;


@Stateless(mappedName = "SLSBPermissoes")
public class SLSBPermissoes implements PermissoesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBPermissoes() {
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificarPermissaoEmissor(Pessoas pessoa) {
		
		final String jpaquery = "SELECT e from Emissores e join fetch e.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<Emissores> q = em.createQuery(jpaquery, Emissores.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoResponsavelTecnico(Pessoas pessoa) {
		final String jpaquery = "SELECT rt from ResponsaveisTecnicos rt join fetch rt.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<ResponsaveisTecnicos> q = em.createQuery(jpaquery, ResponsaveisTecnicos.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoGerenteOrcamentos(Pessoas pessoa) {
		final String jpaquery = "SELECT gerorca from GerentesOrcamentos gerorca join fetch gerorca.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<GerentesOrcamentos> q = em.createQuery(jpaquery, GerentesOrcamentos.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoAnalistaOrcamentos(Pessoas pessoa) {
		final String jpaquery = "SELECT analorca from AnalistasOrcamentos analorca join fetch analorca.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<AnalistasOrcamentos> q = em.createQuery(jpaquery, AnalistasOrcamentos.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoGerenteCompras(Pessoas pessoa, Lps lp) {
		
		String jpaquery = "";
		
		if(lp!=null && lp.getIdlp()!=null){
			if(lp.getIdlp() == 5){
				jpaquery = "SELECT gercomp from GerentesComprasLP2ro gercomp join fetch gercomp.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
				jpaquery+=" and gercomp.flaglp2b = 1";
			}
			else if(lp.getIdlp() == 2){
				jpaquery = "SELECT gercomp from GerentesComprasLP2s gercomp join fetch gercomp.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
				jpaquery+=" and gercomp.flaglp2a = 1";
			}
			else if (lp.getIdlp() == 1){
				jpaquery = "SELECT gercomp from GerentesCompras gercomp join fetch gercomp.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
				jpaquery+=" and gercomp.flaglp"+lp.getIdlp()+" = 1";
			}
			else if (lp.getIdlp() == 3){
				jpaquery = "SELECT gercomp from GerentesComprasLP3 gercomp join fetch gercomp.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
				jpaquery+=" and gercomp.flaglp"+lp.getIdlp()+" = 1";
			}
			else if (lp.getIdlp() == 4){
				jpaquery = "SELECT gercomp from GerentesComprasLP4 gercomp join fetch gercomp.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
				jpaquery+=" and gercomp.flaglp"+lp.getIdlp()+" = 1";
			}
		}
		
		TypedQuery<GerentesCompras> q = em.createQuery(jpaquery, GerentesCompras.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoDiretor(Pessoas pessoa) {
		final String jpaquery = "SELECT diretor from Diretores diretor join fetch diretor.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<Diretores> q = em.createQuery(jpaquery, Diretores.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoGerenteAquisicoes(Pessoas pessoa) {
		final String jpaquery = "SELECT geraq from GerentesAquisicoes geraq join fetch geraq.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<GerentesAquisicoes> q = em.createQuery(jpaquery, GerentesAquisicoes.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoAnalistaAquisicoes(Pessoas pessoa) {
		final String jpaquery = "SELECT analaq from AnalistasAquisicoes analaq join fetch analaq.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<AnalistasAquisicoes> q = em.createQuery(jpaquery, AnalistasAquisicoes.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoCompradorLP1(Pessoas pessoa) {
		final String jpaquery = "SELECT comprador from Compradores comprador join fetch comprador.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<Compradores> q = em.createQuery(jpaquery, Compradores.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}
	
	@Override
	public boolean verificarPermissaoCompradorLP2s(Pessoas pessoa) {
		final String jpaquery = "SELECT comprador from CompradoresLP2s comprador join fetch comprador.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<Compradores> q = em.createQuery(jpaquery, Compradores.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}
	
	@Override
	public boolean verificarPermissaoCompradorLP2ro(Pessoas pessoa) {
		final String jpaquery = "SELECT comprador from CompradoresLP2ro comprador join fetch comprador.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<Compradores> q = em.createQuery(jpaquery, Compradores.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}
	
	@Override
	public boolean verificarPermissaoCompradorLP3(Pessoas pessoa) {
		final String jpaquery = "SELECT comprador from CompradoresLP3 comprador join fetch comprador.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<Compradores> q = em.createQuery(jpaquery, Compradores.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}
	
	@Override
	public boolean verificarPermissaoCompradorLP4(Pessoas pessoa) {
		final String jpaquery = "SELECT comprador from CompradoresLP4 comprador join fetch comprador.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<Compradores> q = em.createQuery(jpaquery, Compradores.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoGerenteContrato(Pessoas pessoa) {
		final String jpaquery = "SELECT gercont from GerentesContratos gercont join fetch gercont.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<GerentesContratos> q = em.createQuery(jpaquery, GerentesContratos.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

	@Override
	public boolean verificarPermissaoAnalistaContrato(Pessoas pessoa) {
		final String jpaquery = "SELECT anacont from AnalistasContratos anacont join fetch anacont.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<AnalistasContratos> q = em.createQuery(jpaquery, AnalistasContratos.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}

}
