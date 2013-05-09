package com.auphelia.Services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.UpdateConflictException;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;


@Path("/contact")
public class ContactService {
	static final String tableName = "Contact";
	CouchDbConnector db;

    @Context
    private UriInfo context;

    public ContactService() {
    	StdHttpClient httpClient = null;
//		httpClient = (StdHttpClient) new StdHttpClient.Builder()
//			.host("microcom.cloudant.com")
//			.port(5984)
//			.username("microcom")
//			.password("makhno!3533")
//			.build();
    	try {
            httpClient = (StdHttpClient) new StdHttpClient.Builder().url("http://localhost:5984").build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
		}
    	CouchDbInstance dbInstance = new StdCouchDbInstance((org.ektorp.http.HttpClient) httpClient);
    	db = new StdCouchDbConnector(tableName, dbInstance);
    	db.createDatabaseIfNotExists();
    }


    /**
     * PUT method for updating or creating an instance of Contact
     * @param contact a JSON representation for a contact resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response putJson(Contact contact) {
    	ContactRepository repo = new ContactRepository(db);
    	
    	if (repo.getContactsByEmail(contact.getEmail()).size() > 0) {
    		return Response.status(412).entity("{\"response\":\"Contact already exists.\",\"code\":412}").build();
    	}
    	
    	repo.add(contact);
    	
        return Response.status(201).entity("{\"response\":\"ok\",\"code\":200,\"id\":" + contact.getId() + "}").build();
    }
    
    /**
     * GET method for filling with test content
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    @Path("/fill")
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response putJson() {
    	ContactRepository repo = new ContactRepository(db);
    	
    	// Emile
    	Contact emile = new Contact();
    	
    	emile.setEmail("nostradamus1935@gmail.com");
    	emile.setPrenom("Émile");
    	emile.setNom("Plourde-Lavoie");
    	emile.setRue("1234 Amherst");
    	emile.setVille("Montréal");
    	emile.setProvince("Québec");
    	emile.setCodePostal("H1S3P8");
    	
    	emile.setId("1ababa");
    	
    	String addResult1 = "";
    	try {
    		repo.add(emile);
    	} catch (UpdateConflictException e) {
    		addResult1 = "Update conflict.";
    	}
        
        // Test
    	Contact test = new Contact();
    	
    	test.setEmail("test@test.com");
    	test.setPrenom("Test");
    	test.setNom("Goodfellow");
    	test.setRue("4321 Nice St.");
    	test.setVille("Montréal");
    	test.setProvince("Québec");
    	test.setCodePostal("H2P2P1");
    	
    	test.setId("2abaabadsf");
    	
    	String addResult2 = "";
    	try {
    		repo.add(test);
    	} catch (UpdateConflictException e) {
    		addResult2 = "Update conflict.";
    	}
        
        // Jean
    	Contact jean = new Contact();
    	
    	jean.setEmail("jean@perdu.com");
    	jean.setPrenom("John");
    	jean.setNom("Doe");
    	jean.setRue("4321 Perdu");
    	jean.setVille("Montréal");
    	jean.setProvince("Québec");
    	jean.setCodePostal("H1Z3A5");
    	
    	jean.setId("3dfsdafdas");
    	
    	String addResult3 = "";
    	try {
    		repo.add(jean);
    	} catch (UpdateConflictException e) {
    		addResult3 = "Update conflict.";
    	}
    	
    	if (!addResult1.equals("") || !addResult2.equals("") || !addResult3.equals("")) {
    		return Response.status(412).entity("[\"" + addResult1 + "\",\"" + addResult2 + "\",\"" + addResult3 + "\"]").build();
    	} else {
    		return Response.status(201).entity("[\"" + addResult1 + "\",\"" + addResult2 + "\",\"" + addResult3 + "\"]").build();
    	}
        
    }
    
    /**
     * Retrieves representation of all instances of Contact
     * @param prenom first name of contact to return
     * @param nom last name of contact to return
     * @return an instance of String
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public List<Contact> getAllContacts() {
    	ContactRepository repo = new ContactRepository(db);
    	
    	List<Contact> allContacts = repo.getAllContacts();
		
		return allContacts;
    }
    
    /**
     * Retrieves representation of an instance of Contact from the first name
     * @param prenom first name of contact to return
     * @return an instance of String
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     */
    @GET
    @Path("{prenom}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public List<Contact> getContactByprenom(@PathParam("prenom") String prenom) {
		ContactRepository repo = new ContactRepository(db);
    	
    	List<Contact> filteredContacts = repo.getContactsByFirstName(prenom);
		
		return filteredContacts;
    }

    /**
     * Retrieves representation of an instance of Contact from the first name and the last name
     * @param prenom first name of contact to return
     * @param nom last name of contact to return
     * @return an instance of String
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     */
    @GET
    @Path("{prenom}/{nom}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public List<Contact> getContactByFullName(@PathParam("prenom") String prenom, @PathParam("nom") String nom) {
    	ContactRepository repo = new ContactRepository(db);
    	
    	List<Contact> filteredContacts = repo.getContactsByFullName(prenom, nom);
		
		return filteredContacts;
    }
    
    /**
     * Retrieves representation of an instance of Contact from email
     * @param email email of contact to return
     * @return an instance of String
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     */
    @GET
    @Path("{email: (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public Response getContactByEmail(@PathParam("email") String email) {
    	ContactRepository repo = new ContactRepository(db);
    	
    	List<Contact> filteredContacts = repo.getContactsByEmail(email);
		
    	if (filteredContacts.size() > 0) {
    		return Response.status(200).entity(filteredContacts.get(0)).build();
    	} else {
    		return Response.status(404).entity("{\"response\":\"Contact not found.\",\"code\":404}").build();
    	}
    }
    
    @POST
    @Path("{email: (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response changeContact(@PathParam("email") String email, Contact contact){
    	ContactRepository repo = new ContactRepository(db);
    	
    	List<Contact> existence = repo.getContactsByEmail(email);
    	if (existence.size() == 0) {
    		return Response.status(404).entity("{\"response\":\"Contact not found.\",\"code\":404}").build();
    	}
    	
    	contact.setId(existence.get(0).getId());
    	contact.setRevision(existence.get(0).getRevision());
    	
    	if (!contact.getEmail().equals(email)) {
    		return Response.status(404).entity("{\"response\":\"URL and JSON mismatch.\",\"code\":400}").build();
    	}
    	
    	repo.update(contact);
        
    	return Response.status(200).entity("{\"response\":\"ok\",\"code\":200}").build();
    }
    
    @DELETE
    @Path("{email: (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response deleteContact(@PathParam("email") String email){
		ContactRepository repo = new ContactRepository(db);
		    	
    	List<Contact> existence = repo.getContactsByEmail(email);
    	if (existence.size() == 0) {
    		return Response.status(404).entity("{\"response\":\"Contact not found.\",\"code\":404}").build();
    	}
    	
    	repo.remove(existence.get(0));
    	
        return Response.status(200).entity("{\"response\":\"ok\",\"code\":200}").build();
    }

}