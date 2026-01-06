# Technical Specifications – Plateforme MYSDF Formations

Ce document décrit les traitements, les services, les DAO et les tables
impliqués pour chaque cas d’utilisation de l’application **MYSDF Formations**.

---

## UC_ListAll – Consulter toutes les formations

### Service
`TrainingService.listAllTrainings() : List<Training>`

### DAO
- `TrainingDao.readAll() : List<Training>`

### Entités
- `Training`

### Tables
- `training`

---

## UC_SearchKeyword – Rechercher des formations par mot-clé

### Service
`TrainingService.searchTrainingsByKeyword(keyword: String) : List<Training>`

### DAO
- `TrainingDao.readByKeyword(keyword: String) : List<Training>`

### Entités
- `Training`

### Tables
- `training`

---

## UC_FilterMode – Filtrer les formations par modalité (présentiel / distanciel)

### Service
`TrainingService.filterTrainingsByMode(mode: DeliveryMode) : List<Training>`

### DAO
- `TrainingDao.readByMode(mode: DeliveryMode) : List<Training>`

### Entités
- `Training`

### Tables
- `training`

---

## UC_Register – Créer un compte utilisateur

### Service
`AuthService.registerUser(login: String, password: String) : UserAccount`

### DAO
- `UserDao.readByLogin(login: String) : Optional<UserAccount>`
- `UserDao.create(user: UserAccount) : int`

### Entités
- `UserAccount`

### Tables
- `user_account`

---

## UC_Login – Se connecter

### Service
`AuthService.authenticate(login: String, password: String) : Optional<UserAccount>`

### DAO
- `UserDao.readByLogin(login: String) : Optional<UserAccount>`

### Entités
- `UserAccount`

### Tables
- `user_account`

---

## UC_Logout – Se déconnecter

### Service
`AuthService.logout(userId: int) : void`

### DAO
- Aucun (gestion de session uniquement)

### Tables
- Aucune

---

## UC_AddToCart – Ajouter une formation au panier

### Service
`CartService.addTrainingToCart(userId: int, trainingId: int) : void`

### DAO
- `CartDao.readByUser(userId: int) : Optional<Cart>`
- `CartDao.create(userId: int) : int`
- `CartItemDao.create(cartId: int, trainingId: int) : void`

### Entités
- `Cart`
- `CartItem`
- `Training`

### Tables
- `cart`
- `cart_item`
- `training`

---

## UC_RemoveFromCart – Retirer une formation du panier

### Service
`CartService.removeTrainingFromCart(userId: int, trainingId: int) : void`

### DAO
- `CartDao.readByUser(userId: int) : Cart`
- `CartItemDao.delete(cartId: int, trainingId: int) : void`

### Entités
- `Cart`
- `CartItem`

### Tables
- `cart`
- `cart_item`

---

## UC_ViewCart – Consulter le panier

### Service
`CartService.getCart(userId: int) : Cart`

### DAO
- `CartDao.readByUser(userId: int) : Cart`
- `CartItemDao.readByCart(cartId: int) : List<CartItem>`
- `TrainingDao.read(trainingId: int) : Training`

### Entités
- `Cart`
- `CartItem`
- `Training`

### Tables
- `cart`
- `cart_item`
- `training`

---

## UC_Checkout – Passer / Valider une commande

### Service
`OrderService.placeOrder(userId: int, client: Client) : Order`

### DAO
- `CartDao.readByUser(userId: int) : Cart`
- `CartItemDao.readByCart(cartId: int) : List<CartItem>`
- `ClientDao.readByEmail(email: String) : Optional<Client>`
- `ClientDao.create(client: Client) : int`
- `OrderDao.create(order: Order) : int`
- `OrderLineDao.create(orderLine: OrderLine) : void`
- `CartItemDao.deleteAll(cartId: int) : void`

### Entités
- `Order`
- `OrderLine`
- `Client`
- `Cart`
- `CartItem`
- `Training`

### Tables
- `cart`
- `cart_item`
- `order`
- `order_line`
- `client`
- `training`

---

## Exceptions
- `AuthenticationException` : identifiants invalides
- `TrainingNotFoundException` : formation inexistante
- `CartEmptyException` : panier vide
- `OrderException` : erreur lors de la création de la commande

---

### Règles métier associées

- Un utilisateur doit être **authentifié** pour gérer un panier ou passer commande.
- Une commande ne peut être validée que si le panier n’est pas vide.
- Chaque commande est associée à **un client**.
- Un utilisateur peut passer **plusieurs commandes**, pour **des clients différents**.

---

### Fonctionnalités "nice to have"

### Paiement
- `PaymentService.processPayment(orderId: int, paymentData)`

### Historique des commandes
- `OrderService.listOrdersByUser(userId: int) : List<Order>`

### Administration
- Gestion des formations (CRUD)
- Gestion des utilisateurs
- Statistiques de ventes

---
