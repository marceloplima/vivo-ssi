package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Regionais;

@Local
public interface RegionaisInt {
	public List<Regionais> recuperar();
	public Regionais recuperarUnico(Regionais regional);
}
