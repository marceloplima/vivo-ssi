package com.ssi.compras.web.controller;

import javax.faces.context.FacesContext;

import com.ssi.compras.web.controller.DemandasMB;
import com.ssi.compras.web.controller.IndexMB;
import com.ssi.compras.web.controller.LoginMB;
import com.ssi.compras.web.controller.PessoasMB;


public class RecuperadorInstanciasBean {

	// Recupera a instancia do bean representado pela classe IndexMB.java 
	// Usado principalmente para acionar os alertas de valida��o
	public static IndexMB recuperarInstanciaIndexBean(){
		FacesContext facescontext = FacesContext.getCurrentInstance();
		javax.el.ValueExpression ve = facescontext.getApplication().getExpressionFactory().createValueExpression(facescontext.getELContext(),"#{indexMB}",IndexMB.class);
		IndexMB mb = (IndexMB)ve.getValue(facescontext.getELContext());
		
		ve = null;
		facescontext = null;
		
		// Reseta os pain�is de aviso do JSF/RF
		ResetadorPaineisPadrao.resetarPaineis(mb);
		
		return mb;			
	}
	
	public static LoginMB recuperarInstanciaLoginBean(){
		FacesContext facescontext = FacesContext.getCurrentInstance();
		javax.el.ValueExpression ve = facescontext.getApplication().getExpressionFactory().createValueExpression(facescontext.getELContext(),"#{loginMB}",LoginMB.class);
		LoginMB mb = (LoginMB)ve.getValue(facescontext.getELContext());
		
		ve = null;
		facescontext = null;
		
		return mb;			
	}
	
	public static DemandasMB recuperarInstanciaDemandasBean(){
		FacesContext facescontext = FacesContext.getCurrentInstance();
		javax.el.ValueExpression ve = facescontext.getApplication().getExpressionFactory().createValueExpression(facescontext.getELContext(),"#{demandasMB}",DemandasMB.class);
		DemandasMB mb = (DemandasMB)ve.getValue(facescontext.getELContext());
		
		ve = null;
		facescontext = null;
		
		return mb;			
	}
	
	public static PessoasMB recuperarInstanciaPessoasBean(){
		FacesContext facescontext = FacesContext.getCurrentInstance();
		javax.el.ValueExpression ve = facescontext.getApplication().getExpressionFactory().createValueExpression(facescontext.getELContext(),"#{pessoasMB}",PessoasMB.class);
		PessoasMB pessoasMb = (PessoasMB)ve.getValue(facescontext.getELContext());
		
		ve = null;
		facescontext = null;
				
		return pessoasMb;
		
	}	
	
}
