## Présentation:

MDD est un réseau social destiné aux développeurs.
Son objectif est d’aider les développeurs à trouver un emploi en favorisant la mise en relation et la collaboration entre pairs.
 ce projet permet aux utilisateurs de:

- S’inscrire et se connecter
- S’abonner à des thèmes (JavaScript, Java, Python,…)
- Consulter un fil d’actualité personnalisé
- Créer et consulter des articles
- Commenter les articles
- Modifier leur profil

## Technologies utilisées:

### Front:

- Angular: 14
- Node.js: +18
- Angular material: 14
- Typescript: 4
- RxJS: 7.5

### Back:

- Spring Boot: 3.2
- Java: 21
- maven: 3.8
- Spring security: 6.1
- JWT: 0.12

### Base de données:

- MySQL :8.0

### Tests:

- Backend : JUnit 5
- Frontend: Karma + Jasmine
- Cypress: e2e


## Installation et Lancement de l'application:

### Cloner le projet :

1- git clone https://github.com/HassanCHKIRROU/Prenez-en-charge-le-d-veloppement-d-une-application-full-stack-compl-te


### Configurer les variables d'environnement:

Le backend lit ses identifiants sensibles (base de données, secret JWT) depuis des variables d'environnement grâce à 'spring-dotenv'.
Créer un fichier .env à la racine du dossier back/ (ce fichier est ignoré par Git) :

DB_USERNAME=votre_utilisateur_mysql
DB_PASSWORD=votre_mot_de_passe_mysql
JWT_SECRET=votre_secret_jwt

### Base de données:

Assurez-vous que votre serveur MySQL est démarré. Aucune commande manuelle n'est nécessaire pour créer les tables ou les données de test : le fichier back/src/main/resources/data.sql s'exécute automatiquement au démarrage du backend et crée la base 'mdd_db', les tables, et insère les thèmes de test.


### Lancer le backend avec les commandes: 

2- cd back
3- mvn clean install
4- mvn compile
5- mvn spring-boot:run
Le backend sera accessible sur: http://localhost:8080/api

### Lancer le frontend avec: 

6- cd front
7- npm install
8- ng serve
Le frontend sera accessible sur : http://localhoet:4200


## Tester l'application:
### Le backend:
 
1- cd back
2- mvn test
le rapport de couverture des tests unitaire est accessible sur: back/target/site/jacoco/index.html

### Le frontend:

 #### Tests unitaires avec Karma:

1- cd front
2- npm run test:coverage
Le rapport de couverture des tests unitaire est sur: coverage/front/index.html

 #### Test d'integration avec Cypress:

 1- cd front
 2-npm run e2e:coverage
 Le rapport de couverture des tests E2E est sur: coverage/index.html






### Authentification:
Exemple:

Inscription:

- Endpoint  POST /api/auth/register

-  Body:
    {
  "username": "Jane",
  "email": "jane@email.com",
  "password": "Jane123!"
   }

- Règles de mot de passe: 
  8 caractères minimum
  1 chiffre
  1 lettre minuscule
  1 lettre majuscule
  1 caractère spécial


Connexion:

- Endpoint    POST /api/auth/login
-  Header:  Authorization: Bearer <token>
- Body:
   {
     "usernameOrEmail": "Jane",
     "password": "Jane123!"
  }

-réponse:
   {
  "token": "eyJhbGciOiJIUz...",
  "id": 65,
  "username": "Jane",
  "email": "jane@email.com"
}
   

### Fonctionnalités:

1- Fil d'actualité (/feed)
Affiche les articles des thèmes auxquels l’utilisateur est abonné
Tri : Plus récent / Plus ancien
Cliquer sur un article pour le consulter
Bouton "Créer un article"

2- Créer un article (/article/create)
Choisir le thème associé
Définir le titre et le contenu
Auteur et date définis automatiquement

3- Consulter un article (/article/{id})
Affiche le titre, l'auteur, la date, le thème et le contenu
Liste des commentaires
Ajouter un commentaire

4- Thèmes (/topics)
Liste de tous les thèmes disponibles
S'abonner à un thème
Le bouton devient "Déjà abonné"

5- Profil (/profile)
Consulter ses informations (nom d’utilisateur, email, date d’inscription)
Modifier ses informations (nom, email, mot de passe)
Voir la liste de ses abonnements
Se désabonner
Se déconnecter




### Endpoints API:

POST       /auth/register	            : Inscription
POST       /auth/login	                : Connexion
GET	       /user/profile	            : Profil utilisateur
PUT	       /user/profile                : Modifier profil
GET	       /topics	                    : Liste des thèmes
POST   	   /subscriptions/{id}	        : S'abonner
DELETE	   /subscriptions/{id}  	    : Se désabonner
GET	       /articles/feed	            : Fil d'actualité
POST	   /articles	                : Créer un article
GET	       /articles/{id}	            : Consulter un article
POST       /articles/{id}/comments	    : Ajouter un commentaire


## Architecture du projet:

### Architecture du backend:

Clic sur le lien pour visualiser l'image:
![Architecture Back](doc/images/architecture-back.png)

### Architecture du frontend:

Clic sur le lien pour visualiser l'image:
![Architecture Front](doc/images/architecture-front.png)














