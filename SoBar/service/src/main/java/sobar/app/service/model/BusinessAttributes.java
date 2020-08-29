package sobar.app.service.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BusinessAttributes {
	
	private JsonElement attributes;
	
	public JsonObject getAttributes() {
    	if (attributes == null || attributes.isJsonNull())
    		return null;
    	
		return attributes.getAsJsonObject();
    }
}
