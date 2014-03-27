package com.ssi.kernel.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/** 
 * Classe que representa os usu�rios dos sistemas SSI, associados ao registro de Pessoas
 * 
 * @see TiposUsuario tipousuario
 * @see Grupos grupo
 * @see Componentes usuarioscomponentes
 */

@Entity
@Cacheable
@Table(name="Usuarios")
public class Usuarios implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3436801326338831820L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idusuario")
	private Long idusuario;
	
	@OneToOne
	@JoinColumn(name="idpessoa")
	private Pessoas pessoa;
	
	@ManyToOne
	@JoinColumn(name="idtipousuario", referencedColumnName="idtipousuario")
	private TiposUsuario tipousuario;
	
	@ManyToOne
	@JoinColumn(name="idgrupo", referencedColumnName="idgrupo")
	private Grupos grupo;
	
	@Column(name="cnmlogin")
	private String cnmlogin;
	
	@Column(name="cnmsenha")
	private String cnmsenha;
	
	@Column(name="flagativo", columnDefinition="default true")
	private Boolean flagativo;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="Funcionalidades_Usuarios",
			   joinColumns={@JoinColumn(name="idusuario", referencedColumnName="idusuario")},
			   inverseJoinColumns={@JoinColumn(name="idfuncionalidade", referencedColumnName="idfuncionalidade")})
	private List<Funcionalidades> usuariosfuncionalidades;
	
	@Version
	@Transient
	private Long version;
	
	@Column(name="flagloginrede", columnDefinition="default false")
	private Boolean flagloginrede;
	
	public Long getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(Long idusuario) {
		this.idusuario = idusuario;
	}
	public TiposUsuario getTipousuario() {
		return tipousuario;
	}
	public void setTipousuario(TiposUsuario tipousuario) {
		this.tipousuario = tipousuario;
	}
	public Grupos getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupos grupo) {
		this.grupo = grupo;
	}
	public String getCnmlogin() {
		return cnmlogin;
	}
	public void setCnmlogin(String cnmlogin) {
		this.cnmlogin = cnmlogin;
	}
	public String getCnmsenha() {
		return cnmsenha;
	}
	public void setCnmsenha(String cnmsenha) {
		this.cnmsenha = cnmsenha;
	}
	public Boolean getFlagativo() {
		return flagativo;
	}
	public void setFlagativo(Boolean flagativo) {
		this.flagativo = flagativo;
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Funcionalidades> getUsuariosfuncionalidades() {
		return usuariosfuncionalidades;
	}
	public void setUsuariosfuncionalidades(List<Funcionalidades> usuariosfuncionalidades) {
		this.usuariosfuncionalidades = usuariosfuncionalidades;
	}
	
	public Pessoas getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}
	
	@Override
	public int hashCode() {
		return this.idusuario != null ? 
		this.getClass().hashCode() + this.idusuario.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Usuarios objint = (Usuarios)obj;
		
		if(this.getIdusuario() != null && objint.getIdusuario() != null){
			if(this.getIdusuario().equals(objint.getIdusuario())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	public Boolean getFlagloginrede() {
		return flagloginrede;
	}
	public void setFlagloginrede(Boolean flagloginrede) {
		this.flagloginrede = flagloginrede;
	}

}
