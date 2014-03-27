package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Pessoas;

@Local
public interface AtividadesInt {
	public List<Atividades> recuperar();
	public Atividades recuperarUnico(Atividades obj);
	public boolean verificaExistente(Atividades obj) throws IndexOutOfBoundsException;
	public List<Atividades> recuperarFiltrado(Map<String, Object> filtros);
	public List<Areas> retornarAreasAtividade(Atividades ativ);
	public void incluir(Atividades obj);
	public void alterar(Atividades obj);
	public List<Atividades> recuperarporarea(Areas area);
	List<Pessoas> retornarCopiadosAtividade(Atividades ativ);
}
