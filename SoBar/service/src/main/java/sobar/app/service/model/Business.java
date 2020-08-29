package sobar.app.service.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gson.JsonElement;

@Data
@Entity
public class Business {
	
	private transient BusinessAttributes attributes;

    private @Id @GeneratedValue Long id;

    private String name;
    private String address;
    private String city;
    private String state;
    private String postalCode;

    @Column(length=65535)
    private String categories;

    private double latitude;
    private double longitude;
    private double stars;
    
    public JsonElement getAttribute(String memberName) {   
    	if (attributes.getAttributes() == null)
    		return null;
    	
		return attributes.getAttributes().get(memberName);
    }
}