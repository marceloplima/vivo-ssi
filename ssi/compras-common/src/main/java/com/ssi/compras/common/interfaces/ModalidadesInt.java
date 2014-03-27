package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Modalidades;

@Local
public interface ModalidadesInt {
	public List<Modalidades> recuperar();
}
