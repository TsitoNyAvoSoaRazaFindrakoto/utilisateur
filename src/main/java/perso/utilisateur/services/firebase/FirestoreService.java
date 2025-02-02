package perso.utilisateur.services.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import perso.utilisateur.dto.UtilisateurFirestore;
import perso.utilisateur.exception.EmailNotFoundException;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private final Firestore firestore;

    public FirestoreService(){
        this.firestore=FirestoreClient.getFirestore();
    }
    public void findById() throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = firestore.collection("utilisateur").get();

        try {
            // Récupérer les résultats de la requête
            QuerySnapshot querySnapshot = future.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            // Parcourir les documents et les afficher
            for (QueryDocumentSnapshot document : documents) {
                System.out.println("Document ID: " + document.getId());
                System.out.println("Data: " + document.getData());
            }
        } catch (Exception e) {
            System.out.println("Error getting documents: " + e.getMessage());
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
}
