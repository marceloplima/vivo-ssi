package com.ssi.compras.web.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.richfaces.component.Mode;
import org.richfaces.component.UIDropDownMenu;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.component.UIMenuItem;
import org.richfaces.component.UIToolbar;

import com.ssi.kernel.controller.interfaces.FuncionalidadesInt;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.Pessoas;

 
@ManagedBean
@RequestScoped
public class MenuBarMB {
	FacesContext ctx = FacesContext.getCurrentInstance();
    private UIToolbar menuBar;
 
    @SuppressWarnings("rawtypes")
	Class[] params = {}; // Necess�rio pro MethodExpression
    
    @EJB
	private FuncionalidadesInt funcionalidadesproxy;
    
    public String doVazio() {
        return "";
    }
 
    public void setMenuBar(UIToolbar menuBar) {
        this.menuBar = menuBar;
    }
    
    public void recuperarFilhos(Funcionalidades pai, UIDropDownMenu ddmpai, UIMenuGroup mgpai){
    	
    	List<Funcionalidades> filhos = pai.getFuncionalidadesfilhas();
		
		for (Funcionalidades filho:filhos){
			
			if(filho.getFuncionalidadesfilhas().size()>=1){
				UIMenuGroup menuGroup = (UIMenuGroup)ctx.getApplication().createComponent(ctx, UIMenuGroup.COMPONENT_TYPE,"org.richfaces.MenuGroupRenderer");
				menuGroup.setLabel(filho.getCnmfuncionalidade());
				menuGroup.setId("grp_"+filho.getIdfuncionalidade());
				menuGroup.setRendered(funcionalidadesproxy.verificarPermissaoFuncionalidadePessoa(filho,recuperaPessoaLogado()) || funcionalidadesproxy.verificarPermissaoFuncionalidadeGrupo(filho,recuperaPessoaLogado().getUsuario().getGrupo()));
				//menuGroup.setIcon("imagens/icones_menu/lupamenu.png");

				if(mgpai != null){
					mgpai.getChildren().add(menuGroup);
				}else{
					ddmpai.getChildren().add(menuGroup);
				}
				
				recuperarFilhos(filho, ddmpai, menuGroup);
			}
			else{
				UIMenuItem menItm = (UIMenuItem) ctx.getApplication().createComponent(ctx, UIMenuItem.COMPONENT_TYPE,"org.richfaces.MenuItemRenderer");
				menItm.setLabel(filho.getCnmfuncionalidade());
				
				String cnmaction = "#{menuBarMB.doVazio}";
				if(filho.getCnmaction()!=null && filho.getCnmaction().length()>=1){
					if(filho.getCnmaction().equals("sair")){
						cnmaction = "#{loginMB.deslogar()}";
					}
					else if(filho.getCnmaction().equals("index_sistema")){
						cnmaction = "#{indexMB.processarmenu('/"+filho.getCnmaction()+".xhtml')}";
					}
					else{
						cnmaction = "#{indexMB.processarmenu('/interno/"+filho.getCnmaction()+"')}";
					}
                }
                
                MethodExpression me = ctx.getApplication().getExpressionFactory().createMethodExpression(ctx.getELContext(),cnmaction, String.class, params);
                menItm.setActionExpression(me);
                menItm.setMode(Mode.server);
                
				menItm.setRendered(funcionalidadesproxy.verificarPermissaoFuncionalidadePessoa(filho,recuperaPessoaLogado()) || funcionalidadesproxy.verificarPermissaoFuncionalidadeGrupo(filho,recuperaPessoaLogado().getUsuario().getGrupo()));
                
                menItm.setId("mitem_"+filho.getFuncionalidadepai().getIdfuncionalidade()+"_"+filho.getIdfuncionalidade().toString());
                
                if(mgpai!=null){
                	mgpai.getChildren().add(menItm);
                }
                else{
                	ddmpai.getChildren().add(menItm);
                }
                
                recuperarFilhos(filho, ddmpai, mgpai);
			}
		}
	}
    
    public UIToolbar getMenuBar(){
    	
    	menuBar = (UIToolbar) ctx.getApplication().createComponent(ctx, UIToolbar.COMPONENT_TYPE, "org.richfaces.ToolbarRenderer");
    	
    	List<Funcionalidades> funcionalidades = funcionalidadesproxy.recuperar(336L,recuperaPessoaLogado());
    	
    	for (Funcionalidades obj:funcionalidades){
    		
    		if(obj.getFuncionalidadepai()==null){
    		
    			UIDropDownMenu dropDownMenu = (UIDropDownMenu) ctx.getApplication().createComponent(ctx, UIDropDownMenu.COMPONENT_TYPE,"org.richfaces.DropDownMenuRenderer");
    			HtmlOutputText label = (HtmlOutputText) ctx.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
    			label.setValue(obj.getCnmfuncionalidade());
    			
    			dropDownMenu.getFacets().put(UIDropDownMenu.Facets.label.name(), label);
    			dropDownMenu.setMode(Mode.server);
    			dropDownMenu.setHideDelay(100);
    			//dropDownMenu.setId("menuraiz_"+new String(Math.random()*(999-111)));
    			
    			menuBar.getChildren().add(dropDownMenu);
    			
    			if(obj.getFuncionalidadesfilhas().size()>=1){
    				recuperarFilhos(obj,dropDownMenu,null);
    			}
    		}
    	}
    	return menuBar;
    }
    
    public Pessoas recuperaPessoaLogado(){
    	// Pega a sess�o ativa para recuperar o menu de acordo com as permiss�es do usuario logado (permiss�es de grupo e as permiss�es espec�ficas do usu�rio)
    	Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa;
		pessoa = (Pessoas) sessaoAtiva.get("sessao");
		return pessoa;
    }
}
