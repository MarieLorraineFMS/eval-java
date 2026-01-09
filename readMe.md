# 🧾 MYSDF Formations – Application Java (CLI)

Application **console (CLI)** de gestion de formations, panier & commandes, développée en **Java** avec une architecture en couches (**DAO / Service / UI**) et une base **MariaDB** (dump fourni).

---

## ✨ Fonctionnalités

### Catalogue (accessible sans connexion)
- Lister toutes les formations (pagination)
- Rechercher par mot-clé (nom / description)
- Filtrer par modalité (présentiel / distanciel)

### Authentification
- Création de compte
- Connexion / déconnexion
- Mots de passe stockés sous forme de **hash** (SHA-256)

### Panier (utilisateur connecté)
- Ajouter une formation (sélection via catalogue)
- Modifier une quantité / retirer une formation
- Vider le panier
- Valider & passer commande

### Commandes
- Checkout : transformation du panier en commande
- Insertion des lignes de commande
- Vidage du panier après succès
- Consultation de l'historique des commandes de l’utilisateur connecté

### Clients
- Sélection d’un client existant (recherche / derniers / id / pagination)
- Création d’un nouveau client
- Réutilisation d’un client existant via email (évite les doublons)

---

## 🧱 Architecture

L’application suit une séparation claire des responsabilités :

- **UI** : affichage & saisie utilisateur (menus CLI), logique minimale
- **Service** : logique métier, validations, orchestration des opérations
- **DAO (JDBC)** : accès base de données, mapping ResultSet → modèles
- **Model** : objets métier (Training, Cart, Order, Client, etc.)
- **Utils** : helpers CLI, pagination, formatting, logging, hashing
- **Exception** : exceptions métier explicites

---

## 📦 Structure du projet

```text
fr.fms
├── App.java                     # Point d’entrée (main) + menu principal
├── UiAuth.java                  # UI connexion / inscription
├── UiCart.java                  # UI panier + checkout
├── UiOrder.java                 # UI clients + commandes
├── UiTraining.java              # UI catalogue formations
│
├── config
│   └── DbConfig.java            # Chargement des properties + getConnection()
│   └── platform.properties
│
├── dao
│   ├── CartDao.java
│   ├── ClientDao.java
│   ├── OrderDao.java
│   ├── TrainingDao.java
│   ├── UserAccountDao.java
│   └── factory
│       └── DaoFactory.java      # Fournit les DAO JDBC (singleton)
│
├── dao.jdbc
│   ├── CartDaoJdbc.java
│   ├── ClientDaoJdbc.java
│   ├── OrderDaoJdbc.java
│   ├── TrainingDaoJdbc.java
│   └── UserAccountDaoJdbc.java
│
├── model
│   ├── Cart.java / CartItem.java
│   ├── Order.java / OrderLine.java / OrderStatus.java
│   ├── Training.java
│   ├── Client.java
│   └── UserAccount.java
│
├── service
│   ├── AuthService.java
│   ├── CartService.java
│   ├── OrderService.java
│   └── TrainingService.java
│
├── utils
│   ├── Helpers.java             # UI helpers, pagination, formatMoney, uiWarn/uiError…
│   ├── AppLogger.java           # Logs simples
│   └── PasswordHasher.java      # Hash SHA-256
│
└── exception
    ├── AuthenticationException.java
    ├── CartEmptyException.java
    ├── DaoException.java
    ├── OrderException.java
    └── TrainingNotFoundException.java


---

## 🗄️ Base de données

Le projet utilise une base MariaDB avec les tables suivantes :

- `training`
- `user_account`
- `cart` (1 panier par user, via UNIQUE(user_id))
- `cart_item` (UNIQUE(cart_id, training_id))
- ``order`` (doubles backticks => mot réservé)
- `order_line`
- `client` (UNIQUE(email))

✅ Le dump SQL inclut :
- structure des tables
- données d’exemple
- contraintes (FK / UNIQUE) nécessaires au bon fonctionnement des DAO

---

## 🔄 Flux checkout (panier → commande)

1. Vérification que le panier existe et n’est pas vide (`CartService.requireNonEmptyCart`)
2. Création ou réutilisation d’un client via email (`OrderService.getOrCreateClient`)
3. Conversion `CartItem` → `OrderLine`
4. Création de la commande + lignes **en transaction** (`OrderDao.createOrderWithLines`)
5. Vidage du panier après succès

---

## 🧯 Gestion des erreurs

- Exceptions métier dédiées (auth, panier vide, commande, formation introuvable)
- Exceptions DB encapsulées via `DaoException`
- UI : gestion explicite des erreurs
- Helpers `uiWarn(...)` / `uiError(...)` pour uniformiser l’affichage

---

## 🔐 Sécurité (contexte projet)

- Hash des mots de passe : **SHA-256**
- Projet CLI local : pas d’exposition réseau
- Pour un contexte “production”, on recommanderait :
  - bcrypt/argon2 + salt
  - limitation des tentatives de connexion
  - pool de connexions DB

---

## 🚀 Lancement

### 1) Importer la base
- Créer une base `platform`
- Importer le dump SQL (phpMyAdmin ou client SQL)

### 2) Configurer les properties
`DbConfig` charge un fichier `<env>.properties` selon la propriété système `env` :

- par défaut : `platform.properties`
- sinon : `-Denv=dev` → `dev.properties`

Exemple attendu dans `platform.properties` :

- `db.url`
- `db.user`
- `db.pwd`

### 3) Exécuter l’application
Lancer la classe `fr.fms.App`.

---


## 🧠 Évolutions possibles

- Tests unitaires (JUnit) sur services
- Recherche client côté SQL (au lieu de filtrer en mémoire)
- Optimiser `findByUserId` des commandes (éviter N+1 si gros volume)
- Renforcer la sécurité auth (bcrypt/argon2, etc.)

---

## 👤 Auteur

Marie-Lorraine


