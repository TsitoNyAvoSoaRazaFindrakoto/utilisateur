package perso.utilisateur.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ResponseJSON {
    String message;
    int  status;
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
