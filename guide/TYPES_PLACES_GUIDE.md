# 📋 Guide d'integration - Types de Places et Configuration

## Description

Cette nouvelle fonctionnalite permet de gerer differents types de places (Premium, Standard, Economique) pour chaque voyage avec des prix differents selon le type.

## Structure de la base de donnees

### Table TypePlace
Stocke les types de places disponibles dans le systeme.

```sql
CREATE TABLE type_place (
    id_type_place SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE
);
```

**Exemple de donnees**:
- Premium
- Standard
- Economique

### Table ConfigPlaceVoyage
Configure le nombre de places et le prix pour chaque type de place dans un voyage specifique.

```sql
CREATE TABLE config_place_voyage (
    id_config SERIAL PRIMARY KEY,
    id_voyage INTEGER NOT NULL REFERENCES voyage(id_voyage),
    id_type_place INTEGER NOT NULL REFERENCES type_place(id_type_place),
    nombre INTEGER NOT NULL,
    prix DOUBLE PRECISION NOT NULL,
    UNIQUE(id_voyage, id_type_place)
);
```

### Modification de la Table Reservation
Ajout d'une colonne pour tracer le type de place reserve.

```sql
ALTER TABLE reservation ADD id_type_place INTEGER REFERENCES type_place(id_type_place);
```

## Architecture Java

### Modeles (Models)
- **TypePlace.java** : Representation du type de place
- **ConfigPlaceVoyage.java** : Configuration des places pour un voyage
- **Reservation.java** : Mise a jour pour inclure typePlace

### Repositories
- **TypePlaceRepository** : Acces aux types de places
- **ConfigPlaceVoyageRepository** : Acces aux configurations

### Services
- **TypePlaceService** : Gestion des types de places
- **ConfigPlaceVoyageService** : Gestion des configurations de places

### Controllers
- **TypePlaceController** : API pour gerer les types de places
- **ConfigPlaceVoyageController** : API pour gerer les configurations

### DTOs
- **ConfigPlaceDTO** : Transfert de donnees pour la configuration

## Flux d'utilisation

### 1. Creer des types de places
```java
TypePlace premium = typePlaceService.creerTypePlace("Premium");
TypePlace standard = typePlaceService.creerTypePlace("Standard");
```

### 2. Configurer les places pour un voyage
```java
// Pour le voyage ID=1, creer 5 places premium a 140000 Ar
configPlaceVoyageService.creerConfig(voyage, premium, 5, 140000);

// Pour le voyage ID=1, creer 10 places standard a 80000 Ar
configPlaceVoyageService.creerConfig(voyage, standard, 10, 80000);
```

### 3. Faire une reservation avec type de place
```java
Reservation res = reservationService.creerReservationAvecType(
    client,
    voyage,
    premium,
    2,  // 2 places premium
    280000  // 2 * 140000
);
```

### 4. Calculer le montant avec type de place
```java
double montant = reservationService.calculerMontantAvecType(
    idVoyage,
    idTypePlace,
    nombre_places
);
```

## Donnees d'initialisation

Les types de places suivants sont crees automatiquement au demarrage:
- Premium
- Standard
- Economique

Voir `database/data.sql` pour les insertions.

## Points d'extension

Pour ajouter la gestion des places dans l'interface de reservation:
1. Mettre a jour `search.html` pour afficher les types de places
2. Creer une page admin pour configurer les places de chaque voyage
3. Ajouter validation pour les limites de places disponibles

## Notes de compatibilite

- Les reservations existantes restent compatibles (id_type_place nullable)
- Les nouvelles reservations devraient specifier un type de place
- Le calcul du montant prend en compte le prix du type de place
