package com.auphelia.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;

public class ContactRepository extends CouchDbRepositorySupport<Contact> {
	public ContactRepository(CouchDbConnector db) {
		 super(Contact.class, db);
	}
	
    public List<Contact> getAllContacts() {
        ViewQuery q = createQuery("allContacts");
        
        return db.queryView(q, Contact.class);
    }
    
    public List<Contact> getContactsByFirstName(String prenom) {
    	ViewQuery q = createQuery("byFirstName");
    	
    	q.key(prenom);
    	
    	return db.queryView(q, Contact.class);
    }
    
    public List<Contact> getContactsByFullName(String prenom, String nom) {
    	ViewQuery q = createQuery("byFullName");
    	
    	Collection<String> keys = new ArrayList<String>();
    	keys.add(prenom);
    	keys.add(nom);
    	
    	q.keys(keys);
    	
    	return db.queryView(q, Contact.class);
    }
    
    public List<Contact> getContactsByEmail(String email) {
    	ViewQuery q = createQuery("byEmail");
    	
    	q.key(email);
    	
    	return db.queryView(q, Contact.class);
    }
}
