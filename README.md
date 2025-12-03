# Utilisateur

Gestion des utilisateurs.

## Configuration

1. Modifier les variables d'environnement dans `.env` :
   ```env
   POSTGRES_USER=postgres
   POSTGRES_PASSWORD=fifaliana
   POSTGRES_DB=utilisateur
   ```


## Déploiement et Lancement

## Étapes d'exécution

### 1. Déployer l'application avec Docker
Une fois le `.jar` généré, utilisez Docker Compose pour construire et exécuter les conteneurs :

```sh
docker-compose up --build 
```

Votre application est maintenant déployée avec Docker ! 🚀


### 2. Vérifier l'exécution des conteneurs

Liste des conteneurs en cours d'exécution :
```sh
docker ps
```

Consulter les logs de l'application :
```sh
docker logs -f utilisateur-app
```

Consulter les logs de la base de données :
```sh
docker logs -f utilisateur-database
```

### 3. Accéder à l'application

- Interface Swagger : [http://localhost:8082/api/swagger-ui.html](http://localhost:8082/api/swagger-ui.html)
- API principale : `http://localhost:8082`

## Arrêter le projet

Pour arrêter les conteneurs sans les supprimer :
```sh
docker-compose down
```

Pour arrêter et supprimer les volumes (perte des données) :
```sh
docker-compose down -v
```

## Remarque

- Assure-toi que Docker et Docker Compose sont installés sur ta machine.
- Vérifie que le port `8082` n'est pas déjà utilisé sur ton système.
- Si un problème survient, vérifie les logs des conteneurs pour identifier l'erreur.

