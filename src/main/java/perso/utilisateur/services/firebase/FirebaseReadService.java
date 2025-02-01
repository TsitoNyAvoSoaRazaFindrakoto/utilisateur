package perso.utilisateur.services.firebase;

import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class FirebaseReadService {
    private final DatabaseReference databaseReference;

    public FirebaseReadService() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("utilisateur");
    }

    public CompletableFuture<String> fetchData(String node) {
        CompletableFuture<String> future = new CompletableFuture<>();

        // Référence au nœud Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(node);

        // Lire les données une seule fois
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    future.complete(snapshot.getValue().toString());
                } else {
                    future.complete("Aucune donnée trouvée !");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(new RuntimeException("Erreur : " + error.getMessage()));
            }
        });

        return future;
    }

    public void getData(String key) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    System.out.println("Data: " + snapshot.getValue());
                } else {
                    System.out.println("No data found for key: " + key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Failed to read data: " + error.getMessage());
            }
        });
    }
}
