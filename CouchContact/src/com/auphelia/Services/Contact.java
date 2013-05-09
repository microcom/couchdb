package com.auphelia.Services;

import javax.xml.bind.annotation.XmlRootElement;

import org.ektorp.support.CouchDbDocument;

@XmlRootElement
public class Contact extends CouchDbDocument {
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String prenom;
	private String nom;
	private String rue;
	private String ville;
	private String province;
	private String codePostal;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	@Override
	public String toString() {
		String str = "Contact [";
		str += "email : '" + email + "', ";
		str += "prenom : '" + prenom + "', ";
		str += "nom : '" + nom + "', ";
		str += "rue : '" + rue + "', ";
		str += "ville : '" + ville + "', ";
		str += "province : '" + province + "', ";
		str += "codePostal : '" + codePostal + "'";
		str += "]";
		
		return str;	
	}
}