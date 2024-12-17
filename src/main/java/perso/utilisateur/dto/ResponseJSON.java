package perso.utilisateur.dto;

import lombok.Data;

@Data
public class ResponseJSON {
    String message;
    int  status;
    Object data;
    public ResponseJSON(String message,int status,Object data){
        this.setMessage(message);
        this.setStatus(status);
        this.setData(data);
    }

    public ResponseJSON(String message,int status){
        this.setMessage(message);
        this.setStatus(status);
    }
}
