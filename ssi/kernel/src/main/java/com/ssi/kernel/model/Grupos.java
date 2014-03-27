package com.ssi.kernel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

/** 
 * Representa os grupos de usu�rios
 * 
 * @see Componentes gruposcomponentes
 * @see Funcionalidades gruposfuncionalidades
 */


@Entity
@Cacheable
@Table(name="Grupos")
public class Grupos implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9004518550003893508L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idgrupo")
	private Long idgrupo;
	
	@Column(name="cnmgrupo")
	private String cnmgrupo;
	
	@Column(name="flagativo")
	private boolean flagativo;
	
	@Transient
	private boolean checked;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Funcionalidades_Grupos",
			   joinColumns={@JoinColumn(name="idgrupo", referencedColumnName="idgrupo")},
			   inverseJoinColumns={@JoinColumn(name="idfuncionalidade", referencedColumnName="idfuncionalidade")})
	private Set<Funcionalidades> gruposfuncionalidades;
	
	public void remapearItens(Grupos grupo){
		if(this.getGruposfuncionalidades()!=null && !this.getGruposfuncionalidades().isEmpty()){
			Iterator<Funcionalidades>it = this.getGruposfuncionalidades().iterator();
			while(it.hasNext()){
				Funcionalidades obj = it.next();
				List<Grupos>lista = new ArrayList<Grupos>();
				lista.add(grupo);
				obj.setGrupos(new HashSet<Grupos>(lista));
			}
		}
	}
	
	public Long getIdgrupo() {
		return idgrupo;
	}
	public void setIdgrupo(Long idgrupo) {
		this.idgrupo = idgrupo;
	}
	public String getCnmgrupo() {
		return cnmgrupo;
	}
	public void setCnmgrupo(String cnmgrupo) {
		this.cnmgrupo = cnmgrupo;
	}
	
	public boolean isFlagativo() {
		return flagativo;
	}

	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public List<Funcionalidades> getGruposfuncionalidades() {
		if(gruposfuncionalidades!=null){
			return new ArrayList<Funcionalidades>(gruposfuncionalidades);
		}
		else{
			return new ArrayList<Funcionalidades>();
		}
	}
	public void setGruposfuncionalidades(Set<Funcionalidades> gruposfuncionalidades) {
		this.gruposfuncionalidades = gruposfuncionalidades;
	}
	
	public Grupos getByid(Long id){
		Grupos obj = new Grupos();
		obj.setIdgrupo(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.idgrupo != null ? 
		this.getClass().hashCode() + this.idgrupo.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Grupos objint = (Grupos)obj;
		
		if(this.getIdgrupo() != null && objint.getIdgrupo() != null){
			if(this.getIdgrupo().equals(objint.getIdgrupo())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
