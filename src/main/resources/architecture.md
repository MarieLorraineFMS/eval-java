fr.fms
├── App.java                       # Point d’entrée + menu principal
│
├── UiAuth.java                    # UI connexion / inscription
├── UiCart.java                    # UI panier + checkout
├── UiOrder.java                   # UI clients + commandes
├── UiTraining.java                # UI catalogue formations
│
├── config
│   └── DbConfig.java              # Chargement des properties + getConnection()
│
├── service
│   ├── AuthService.java           # Authentification & inscription
│   ├── CartService.java           # Logique panier
│   ├── OrderService.java          # Checkout & commandes
│   └── TrainingService.java       # Catalogue formations
│
├── dao
│   ├── TrainingDao.java
│   ├── UserAccountDao.java
│   ├── ClientDao.java
│   ├── CartDao.java
│   ├── OrderDao.java
│   │
│   └── factory
│       └── DaoFactory.java        # Fournit les DAO JDBC (singleton)
│
├── dao.jdbc
│   ├── TrainingDaoJdbc.java
│   ├── UserAccountDaoJdbc.java
│   ├── ClientDaoJdbc.java
│   ├── CartDaoJdbc.java
│   └── OrderDaoJdbc.java
│
├── model
│   ├── Training.java
│   ├── UserAccount.java
│   ├── Client.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   ├── OrderLine.java
│   └── OrderStatus.java
│
├── exception
│   ├── AuthenticationException.java
│   ├── CartEmptyException.java
│   ├── TrainingNotFoundException.java
│   ├── OrderException.java
│   └── DaoException.java
│
└── utils
    ├── Helpers.java               # Helpers UI, pagination, formatMoney, uiWarn/uiError
    ├── AppLogger.java             # Logger console (mode verbose)
    └── PasswordHasher.java        # Hash SHA-256 (comparaison en temps constant)
