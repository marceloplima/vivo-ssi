package com.ssi.compras.common.utils;

import javax.faces.context.FacesContext;

public class ParametrosSistema {

	public String recuperaNomeSistema(){
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("nomesistema");
	}
	
	public String recuperaModuloSistema(){
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("modulosistema");
	}
	
	public String recuperaVersaoSistema(){
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("versaosistema");
	}
	
	public String recuperaCabecalho(){
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("nomesistema")+" "+FacesContext.getCurrentInstance().getExternalContext().getInitParameter("versaosistema")+" - "+FacesContext.getCurrentInstance().getExternalContext().getInitParameter("modulosistema");
	}
	
	public String recuperaCaminhoUploads(){
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("caminhouploads");
	}
	
}
