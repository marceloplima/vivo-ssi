package com.ssi.regulatorio.web.controller;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.redes.domain.AnexosRedes;
import com.ssi.kernel.redes.domain.TiposAnexosRedes;
import com.ssi.kernel.redes.interfaces.AnexosRedesInt;
import com.ssi.kernel.redes.interfaces.TiposAnexosRedesInt;
import com.ssi.regulatorio.common.utils.ParametrosSistema;

@ManagedBean
@ViewScoped
public class AnexosMB implements Serializable{

	private static final long serialVersionUID = -2633283464281442362L;
	
	@EJB
	private AnexosRedesInt anexoint;
	
	@EJB
	private TiposAnexosRedesInt tipoanexoint;
	
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private File file;
    private ParametrosSistema parametrosistema = new ParametrosSistema();
    
    private AnexosRedes anexo;
    private boolean exibeconfirmacaoapagar;
    
	// Usado na busca
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	public List<TiposAnexosRedes> recuperarTiposAnexos(){
		return tipoanexoint.recuperarTiposAnexos();
	}
	
	public void fecharPopupConfirmacaoRemoverAnexo(){
		exibeconfirmacaoapagar = false;
	}
	
	public void preRemover(AnexosRedes anexo){
		this.anexo = anexo;
		exibeconfirmacaoapagar = true;
	}
	
	public void removerAnexo(){
		try{
			File newfile = new File(parametrosistema.recuperaCaminhoUploads()+anexo.getTipoanexo().getIdtipoanexo()+"/"+anexo.getCnmcaminhoanexo());
			newfile.delete();
			anexoint.remover(anexo);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		fecharPopupConfirmacaoRemoverAnexo();
	}
	
	public AnexosDataModel getDataModel(){
		Map<String,Object> filtros = new LinkedHashMap<String,Object>();
		//filtros.put("demanda", demandasmb.getDemandas());
		this.filtros = filtros;
		return new AnexosDataModel(anexoint, filtros);
	}

	public Map<String,Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}

	public DemandasMB getDemandasmb() {
		return demandasmb;
	}

	public void setDemandasmb(DemandasMB demandasmb) {
		this.demandasmb = demandasmb;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ParametrosSistema getParametrosistema() {
		return parametrosistema;
	}

	public void setParametrosistema(ParametrosSistema parametrosistema) {
		this.parametrosistema = parametrosistema;
	}

	public AnexosRedes getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexosRedes anexo) {
		this.anexo = anexo;
	}

	public boolean isExibeconfirmacaoapagar() {
		return exibeconfirmacaoapagar;
	}

	public void setExibeconfirmacaoapagar(boolean exibeconfirmacaoapagar) {
		this.exibeconfirmacaoapagar = exibeconfirmacaoapagar;
	}

}
