# Architecture – Plateforme MYSDF formations

Ce document décrit l’architecture technique de l’application MYSDF formations.
L’architecture est organisée en couches afin de séparer les responsabilités
& de faciliter la maintenance, les tests et l’évolution de l’application.

---

## 1. Vue d’ensemble

L’application est structurée selon une **architecture en couches** :

- Couche Présentation (UI / CLI)
- Couche Application / Service
- Couche Domaine (Modèle métier)
- Couche Accès aux données (DAO)
- Couche Infrastructure (configuration, base de données)

---

## 2. Organisation

```text
fr.fms
├── App.java
│
├── config
│   └── DbConfig.java
│
├── service
│   ├── TrainingService.java
│   ├── AuthService.java
│   ├── CartService.java
│   └── OrderService.java
│
├── dao
│   ├── TrainingDao.java
│   ├── UserDao.java
│   ├── ClientDao.java
│   ├── CartDao.java
│   ├── CartItemDao.java
│   ├── OrderDao.java
│   └── OrderLineDao.java
│
├── model
│   ├── Training.java
│   ├── UserAccount.java
│   ├── Client.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   └── OrderLine.java
│
├── exception
│   ├── AuthenticationException.java
│   ├── CartEmptyException.java
│   ├── TrainingNotFoundException.java
│   └── OrderException.java
│
└── utils
    ├── AppLogger.java
    └── Helpers.java
```

## 3. Ressources & livrables

Les ressources non compilées sont stockées dans `src/main/resources` :

```text
src/main/resources
├── uml
│   ├── usecases.puml
│   ├── classes.puml
│   └── sequence_place_order.puml
│
├── architecture.md
├── technicalSpecifications.md
└── trainingPlatform.properties
```