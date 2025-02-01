package perso.utilisateur.services.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {
    private final DatabaseReference databaseReference;

    public FirebaseService(){
        this.databaseReference= FirebaseDatabase.getInstance().getReference();
    }

    public void insertData(String key, Object data) {
        databaseReference.child("utilisateur").child(key).setValueAsync(data);
    }
}
