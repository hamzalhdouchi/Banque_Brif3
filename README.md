# Bank Transaction Analysis and Anomaly Detection System

##  Description

Cette application Java de gestion et d'analyse des transactions bancaires permet aux établissements financiers de centraliser leurs données clients, détecter les anomalies transactionnelles, identifier les comptes inactifs et générer des rapports financiers détaillés.

Développée pour **Banque Al Baraka** par **SoluBank Systems**, cette solution répond aux besoins critiques de suivi et d'analyse des flux financiers dans un environnement bancaire moderne.

##  Fonctionnalités

### Gestion des Clients
- Création, modification et suppression de clients
- Recherche de clients par ID ou nom
- Consultation de l'ensemble des clients

### Gestion des Comptes
- Ouverture de comptes courants et épargne
- Mise à jour des soldes et paramètres
- Recherche de comptes par client ou numéro
- Identification des comptes avec solde maximum/minimum

### Gestion des Transactions
- Enregistrement des opérations (versements, retraits, virements)
- Consultation de l'historique transactionnel
- Filtrage par montant, type, date ou lieu
- Regroupement par type ou période

### Analyse et Détection
- **Détection des transactions suspectes** : montants élevés, lieux inhabituels, fréquence excessive
- **Identification des comptes inactifs**
- **Alertes automatiques** : solde bas, inactivité prolongée
- **Calculs statistiques** : moyennes, totaux, tendances

### Rapports Financiers
- **Top 5 des clients** par solde
- **Rapports mensuels** : volume et type de transactions
- **Analyse des anomalies** avec seuils configurables
- **Rapports d'activité** par période


- **Java 17** (records, sealed classes, switch expressions, var)
- **Programmation fonctionnelle** (Stream API, lambda, Optional, Collectors)
- **JDBC** pour la persistance des données
- **PostgreSQL/MySQL** base de données relationnelle
- **Architecture en couches** (Entity, DAO, Service, UI)
- **Gestion des exceptions** avec messages clairs
- **Git** pour le contrôle de version

##  Architecture du Projet

```
src/
├── entity/
│ ├── Client.java (record)
│ ├── Compte.java (sealed class)
│ │ ├── CompteCourant.java
│ │ └── CompteEpargne.java
│ └── Transaction.java (record)
├── dao/
│ ├── ClientDAO.java
│ ├── CompteDAO.java
│ └── TransactionDAO.java
├── service/
│ ├── ClientService.java
│ ├── CompteService.java
│ ├── TransactionService.java
│ └── RapportService.java
├── util/
│ ├── DateUtils.java
│ ├── FormatUtils.java
│ └── ValidationUtils.java
├── ui/
│ └── ConsoleMenu.java
└── Main.java

```



### Couche Entity (Modèle)
- **Client** : informations client (id, nom, email)
- **Compte** : hiérarchie scellée pour comptes courants/épargne
- **Transaction** : opérations financières avec type enuméré

### Couche DAO (Accès aux données)
- Opérations CRUD complètes
- Recherches spécifiques par critères
- Gestion des relations clients/comptes/transactions

### Couche Service (Logique Métier)
- Règles métier et calculs financiers
- Détection d'anomalies et algorithmes d'analyse
- Génération de rapports et statistiques

### Couche UI (Interface Utilisateur)
- Menu console interactif
- Navigation intuitive entre fonctionnalités
- Affichage formaté des données


### Tables Principales

```sql
-- Table Clients
CREATE TABLE Client (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL
);

-- Table Comptes
CREATE TABLE Compte (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero VARCHAR(20) UNIQUE NOT NULL,
    solde DECIMAL(15,2) DEFAULT 0.00,
    idClient BIGINT NOT NULL,
    typeCompte ENUM('COURANT', 'EPARGNE') NOT NULL,
    decouvert_autorise DECIMAL(15,2) DEFAULT 0.00,
    taux_interet DECIMAL(5,4) DEFAULT 0.0000,
    FOREIGN KEY (idClient) REFERENCES Client(id)
);

-- Table Transactions
CREATE TABLE Transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_transaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    montant DECIMAL(15,2) NOT NULL,
    type_transaction ENUM('VERSEMENT', 'RETRAIT', 'VIREMENT') NOT NULL,
    lieu VARCHAR(100),
    idCompte BIGINT NOT NULL,
    FOREIGN KEY (idCompte) REFERENCES Compte(id)
);

```