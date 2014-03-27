package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.PapeisAreas;

@Local
public interface PapeisAreasInt {
	
	public List<PapeisAreas> recuperar();
	public PapeisAreas recuperarUnico(PapeisAreas area);

}
