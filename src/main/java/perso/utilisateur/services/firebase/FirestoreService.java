package perso.utilisateur.services.firebase;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import perso.utilisateur.dto.UtilisateurFirestore;
import perso.utilisateur.exception.EmailNotFoundException;
import perso.utilisateur.models.Utilisateur;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private final Firestore firestore;

    public FirestoreService()throws IOException{
        this.firestore=FirestoreClient.getFirestore();
    }

    public Integer getIdUtilisateur(){
        CollectionReference utilisateurs= firestore.collection("utilisateur");
        try{
            ApiFuture<QuerySnapshot> future = utilisateurs.orderBy("idUtilisateur", Query.Direction.DESCENDING).limit(1)
                    .get();
            QuerySnapshot querySnapshot = future.get();
            List<QueryDocumentSnapshot> documents=querySnapshot.getDocuments();
            return ((Long)documents.get(0).getData().get("idUtilisateur")).intValue();
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public UtilisateurFirestore findUtilisateur(String email)throws RuntimeException {
        CollectionReference utilisateurs= firestore.collection("utilisateur");
        try{
            ApiFuture<QuerySnapshot> future = utilisateurs.whereEqualTo("mobile",true)
                    .whereEqualTo("email",email)
                    .get();
            QuerySnapshot querySnapshot = future.get();
            List<QueryDocumentSnapshot> documents=querySnapshot.getDocuments();
            return UtilisateurFirestore.turnIntoUtilisateurFirestore(documents.get(0));
        }
        catch (IndexOutOfBoundsException e){
            throw new EmailNotFoundException(email);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public Integer insertUtilisateur(Map<String,Object> data) throws ExecutionException, InterruptedException {
        Integer idUtilisateur = this.getIdUtilisateur()+1;
        data.put("idUtilisateur",idUtilisateur);
        // Insérer avec un ID spécifique
        DocumentReference docRef = firestore.collection("utilisateur").document(idUtilisateur.toString());
        ApiFuture<WriteResult> writeResult = docRef.set(data);
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
        return idUtilisateur;
    }
}
