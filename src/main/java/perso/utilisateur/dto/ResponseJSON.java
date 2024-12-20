package perso.utilisateur.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import perso.utilisateur.other.POV;

@Data
public class ResponseJSON {
    @JsonView(POV.Public.class)
    String message;
    @JsonView(POV.Public.class)
    int  status;
    @JsonView(POV.Public.class)
    Object data;
    @JsonIgnore
    protected HashMap<String, Object> dataMap = new HashMap<>();

    public ResponseJSON(String message,int status,Object data){
        this.setMessage(message);
        this.setStatus(status);
        this.setData(data);
    }

    public ResponseJSON(String message,int status){
        this.setMessage(message);
        this.setStatus(status);
    }

		public void addObject(String key,Object data){
			this.dataMap.put(key, data);
			this.setData(dataMap);
		}
}
