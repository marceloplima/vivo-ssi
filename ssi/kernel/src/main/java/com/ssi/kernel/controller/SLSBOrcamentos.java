package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Orcamentos;
import com.ssi.kernel.controller.interfaces.OrcamentosInt;

@Stateless(mappedName = "SLSBOrcamentos")
public class SLSBOrcamentos implements OrcamentosInt {

	@PersistenceContext(unitName = "ssifwpc")
	EntityManager em;

	public SLSBOrcamentos() {
	}

	@Override
	public List<Orcamentos> recuperarOrcamentosDaDemanda(Demandas demanda,
			String ativoInativo) {

		try {
			String jpaquery = "SELECT orcamento FROM Orcamentos orcamento WHERE orcamento.demandas = :demanda";

			if (ativoInativo != null) {

				jpaquery = jpaquery + " AND orcamento.flagativo = :ativoinativo";

			}

			jpaquery = jpaquery + " ORDER BY orcamento.ufs";

			TypedQuery<Orcamentos> q = em.createQuery(jpaquery,Orcamentos.class);

			q.setParameter("demanda", demanda);

			if (ativoInativo != null) {
				q.setParameter("ativoinativo", ativoInativo);
			}

			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

}
