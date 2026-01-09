# Technical Specifications – Plateforme MYSDF Formations

Ce document décrit les **cas d’utilisation**, les **services**, les **DAO**,
les **entités** et les **tables SQL** utilisés dans l’application
**MYSDF Formations**.

---

## Architecture générale

L’application suit une architecture en couches :

- **UI (Console)** : interaction utilisateur uniquement
- **Service** : logique métier & règles fonctionnelles
- **DAO** : accès aux données (JDBC)
- **Model** : entités métier
- **Utils / Exceptions** : support technique & gestion d’erreurs

Les UI ne contiennent **aucune logique métier** ni SQL.

---

## UC_ListAll – Consulter toutes les formations

### Service
`TrainingService.listAll() : List<Training>`

### DAO
- `TrainingDao.findAll() : List<Training>`

### Entités
- `Training`

### Tables
- `training`

---

## UC_SearchKeyword – Rechercher des formations par mot-clé

### Service
`TrainingService.searchByKeyword(keyword: String) : List<Training>`

### DAO
- `TrainingDao.searchByKeyword(keyword: String) : List<Training>`

### Entités
- `Training`

### Tables
- `training`

---

## UC_FilterMode – Filtrer les formations (présentiel / distanciel)

### Service
`TrainingService.listByOnsite(onsite: boolean) : List<Training>`

### DAO
- `TrainingDao.findByOnsite(onsite: boolean) : List<Training>`

### Entités
- `Training`

### Tables
- `training`

---

## UC_Register – Créer un compte utilisateur

### Service
`AuthService.register(login: String, password: String) : UserAccount`

### DAO
- `UserAccountDao.findByLogin(login: String) : Optional<UserAccount>`
- `UserAccountDao.create(user: UserAccount) : int`
- `UserAccountDao.findById(id: int) : Optional<UserAccount>`

### Entités
- `UserAccount`

### Tables
- `user_account`

---

## UC_Login – Se connecter

### Service
`AuthService.login(login: String, password: String) : UserAccount`

### DAO
- `UserAccountDao.findByLogin(login: String) : Optional<UserAccount>`

### Entités
- `UserAccount`

### Tables
- `user_account`

---

## UC_ViewCart – Consulter le panier

### Service
`CartService.getOrCreateCart(userId: int) : Cart`

### DAO
- `CartDao.getOrCreateCartId(userId: int) : int`
- `CartDao.findByUserId(userId: int) : Optional<Cart>`

### Entités
- `Cart`
- `CartItem`
- `Training`

### Tables
- `cart`
- `cart_item`
- `training`

---

## UC_AddToCart – Ajouter une formation au panier

### Service
`CartService.addTraining(userId: int, trainingId: int, quantity: int) : Cart`

### DAO
- `CartDao.getOrCreateCartId(userId: int)`
- `CartDao.addOrIncrement(cartId, trainingId, quantity, unitPrice)`

### Entités
- `Cart`
- `CartItem`
- `Training`

### Tables
- `cart`
- `cart_item`
- `training`

---

## UC_RemoveFromCart – Modifier / retirer une formation du panier

### Service
- `CartService.decrementTraining(userId, trainingId, delta)`
- `CartService.removeTraining(userId, trainingId)`

### DAO
- `CartDao.decrementOrRemove(cartId, trainingId, delta)`
- `CartDao.removeLine(cartId, trainingId)`

### Entités
- `Cart`
- `CartItem`

### Tables
- `cart`
- `cart_item`

---

## UC_Checkout – Valider une commande

### Service
`OrderService.checkout(userId: int, client: Client) : Order`

### DAO
- `CartDao.findByUserId(userId)`
- `ClientDao.findByEmail(email)`
- `ClientDao.create(client)`
- `OrderDao.createOrderWithLines(order, lines)`
- `CartDao.clear(cartId)`

### Entités
- `Order`
- `OrderLine`
- `Client`
- `Cart`
- `CartItem`
- `Training`

### Tables
- `order`
- `order_line`
- `client`
- `cart`
- `cart_item`
- `training`

---

## Exceptions métier

- `AuthenticationException` : erreur d’authentification
- `TrainingNotFoundException` : formation inexistante
- `CartEmptyException` : panier vide
- `OrderException` : erreur de commande
- `DaoException` : erreur d’accès aux données

---

## Règles métier principales

- L’utilisateur doit être **authentifié** pour gérer un panier ou passer commande
- Une commande ne peut être validée que si le panier n’est **pas vide**
- Chaque commande est associée à **un seul client**
- Un utilisateur peut passer **plusieurs commandes**, pour **des clients différents**
- Le panier est **vidé automatiquement** après validation de commande

---

## Choix d’architecture

- Pas de `CartItemDao` ni `OrderLineDao` :
  - Les DAO `CartDao` et `OrderDao` gèrent leurs lignes
- Les UI ne contiennent **aucune logique métier**

---

## Évolutions possibles

### Sécurité & authentification
- Ajout d’un **salt par utilisateur** pour le hashing des mots de passe
- Utilisation d’un algorithme dédié (`bcrypt`, `argon2`) au lieu de SHA-256
- Protection contre les attaques par force brute (limitation de tentatives)
- Gestion de session avec expiration (token ou session serveur)

### Application Web
- Exposition des services via une API REST
- Remplacement des UI console par :
  - une interface Web
  - ou une application mobile
- Gestion des erreurs via HTTP status codes
- Authentification par token (JWT / session)

### Données & performance
- Pagination SQL côté DAO (LIMIT / OFFSET)
- Indexation avancée (email client, user_id, order_id)
- Cache applicatif sur le catalogue des formations
- Optimisation des requêtes de chargement panier / commandes

### Fonctionnalités métier
- Gestion du statut de commande (DRAFT → CONFIRMED → CANCELLED)
- Facturation PDF / export CSV
- Notifications (email / webhook)

### Administration
- CRUD des formations
- Gestion des utilisateurs
- Tableau de bord administrateur


---
