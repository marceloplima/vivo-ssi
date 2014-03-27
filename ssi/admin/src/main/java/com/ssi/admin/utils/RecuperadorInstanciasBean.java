package com.ssi.admin.utils;

import javax.faces.context.FacesContext;

import com.ssi.admin.controller.BuscarPessoasMB;
import com.ssi.admin.controller.EmailsMB;
import com.ssi.admin.controller.IndexMB;
import com.ssi.admin.controller.TelefonesMB;

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
	
	// Recupera a instancia do bean representado pela classe BuscarPessoasMB.java 
	public static BuscarPessoasMB recuperarInstanciaBuscarPessoasBean(){
		FacesContext facescontext = FacesContext.getCurrentInstance();
		javax.el.ValueExpression ve = facescontext.getApplication().getExpressionFactory().createValueExpression(facescontext.getELContext(),"#{buscarPessoasMB}",BuscarPessoasMB.class);
		BuscarPessoasMB mb = (BuscarPessoasMB)ve.getValue(facescontext.getELContext());
			
		ve = null;
		facescontext = null;
		
		return mb;			
	}
	
	// Recupera a instancia do bean representado pela classe EmailsMB.java 
	public static EmailsMB recuperarInstanciaEmailsBean(){
		FacesContext facescontext = FacesContext.getCurrentInstance();
		javax.el.ValueExpression ve = facescontext.getApplication().getExpressionFactory().createValueExpression(facescontext.getELContext(),"#{emailsMB}",EmailsMB.class);
		EmailsMB mb = (EmailsMB)ve.getValue(facescontext.getELContext());
				
		ve = null;
		facescontext = null;
			
		return mb;			
	}
	
	// Recupera a instancia do bean representado pela classe TelefonesMB.java 
	public static TelefonesMB recuperarInstanciaTelsBean(){
		FacesContext facescontext = FacesContext.getCurrentInstance();
		javax.el.ValueExpression ve = facescontext.getApplication().getExpressionFactory().createValueExpression(facescontext.getELContext(),"#{telefonesMB}",TelefonesMB.class);
		TelefonesMB mb = (TelefonesMB)ve.getValue(facescontext.getELContext());
					
		ve = null;
		facescontext = null;
				
		return mb;			
	}
}
