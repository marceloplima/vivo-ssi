package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Noticias;

@Local
public interface NoticiasInt {

	public List<Noticias> recuperar();
	public Noticias recuperarUnico(Noticias noticia);
	public void alterar(Noticias noticia);
	public boolean verificaExistente(Noticias noticia) throws IndexOutOfBoundsException;
	public List<Noticias> recuperarFiltrado(Map<String, Object> filtros);
	public Noticias incluir(Noticias noticia);
	public List<Modulos> retornarModulosNoticias(Noticias noticia);
}
