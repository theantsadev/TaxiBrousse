# 📝 UserFlow : Réservation de voyage

**Fonctionnalité** : Un client achète des places pour Fasankarana → Ambolomadinika, le 14/01 à 14h

---

## 1️⃣ AFFICHAGE

### **Écran 1 : Sélection des gares**

**Titre** : "Rechercher un voyage"

**Éléments** :
- Gare de départ : [Liste déroulante]
- Gare d'arrivée : [Liste déroulante]
- Bouton [Rechercher]

**Logique d'affichage** :
1. Au chargement de la page :
   - Appel métier : `VoyageService.getAllVoyagesDispo()` → List<VoyageDetailDTO>
   - Stocker la liste complète en mémoire (JavaScript)

2. Remplir les listes déroulantes :
   - **Gare départ** : Projection unique sur `gare_depart_nom + gare_depart_ville`
   - **Gare arrivée** : Projection unique sur `gare_arrivee_nom + gare_arrivee_ville`
   - Éliminer les doublons

3. Au clic sur [Rechercher] :
   - Filtrer la liste en JavaScript :
     ```js
     voyagesFiltres = voyages.filter(v => 
       v.gare_depart_nom == gareDepart_selected &&
       v.gare_arrivee_nom == gareArrivee_selected
     )
     ```
   - Passer à l'écran 2 avec cette liste filtrée

**Appel métier** :
→ `VoyageService.getAllVoyagesDispo()` : List<VoyageDetailDTO>

---

### **Écran 2 : Sélection de la date**

**Titre** : "Fasankarana → Ambolomadinika - Choisir une date"

**Éléments** :
Liste des dates disponibles sous forme de boutons/cartes :

```
┌─────────────────────────────┐
│ 📅 Lundi 13 janvier 2026    │
│    3 voyages disponibles    │
│         [Choisir]           │
└─────────────────────────────┘

┌─────────────────────────────┐
│ 📅 Mardi 14 janvier 2026    │
│    5 voyages disponibles    │
│         [Choisir]           │
└─────────────────────────────┘

┌─────────────────────────────┐
│ 📅 Mercredi 15 janvier 2026 │
│    2 voyages disponibles    │
│         [Choisir]           │
└─────────────────────────────┘
```

**Logique d'affichage** :
1. À partir de `voyagesFiltres` (liste de l'écran 1)
2. Extraire les dates uniques :
   ```js
   datesUniques = [...new Set(
     voyagesFiltres.map(v => v.date_depart)
   )]
   ```
3. Pour chaque date, compter le nombre de voyages :
   ```js
   nbVoyages = voyagesFiltres.filter(v => 
     v.date_depart == date
   ).length
   ```
4. Trier par date croissante
5. Au clic sur [Choisir] :
   - Filtrer par date sélectionnée
   - Passer à l'écran 3

**Pas d'appel métier** (filtrage JavaScript côté client)

---

### **Écran 3 : Sélection de l'heure**

**Titre** : "Fasankarana → Ambolomadinika - 14 janvier 2026"

**Éléments** :
Liste des horaires disponibles :

```
┌────────────────────────────────────────┐
│ 🕐 08h00                               │
│ 🚐 Voiture : Standard ABC              │
│ 💺 Places disponibles : 10/12          │
│ 💰 Tarif : 20 000 Ar                   │
│              [Réserver]                │
└────────────────────────────────────────┘

┌────────────────────────────────────────┐
│ 🕐 14h00                               │
│ 🚐 Voiture : Confort XYZ               │
│ 💺 Places disponibles : 12/15          │
│ 💰 Tarif : 25 000 Ar                   │
│              [Réserver]                │
└────────────────────────────────────────┘

┌────────────────────────────────────────┐
│ 🕐 16h30                               │
│ 🚐 Voiture : VIP Premium               │
│ 💺 Places disponibles : 6/8            │
│ 💰 Tarif : 30 000 Ar                   │
│              [Réserver]                │
└────────────────────────────────────────┘
```

**Logique d'affichage** :
1. À partir des voyages filtrés par date (écran 2)
2. Afficher chaque voyage avec :
   - Heure de départ
   - Nom et type de voiture
   - Places disponibles
   - Tarif
3. Trier par heure croissante
4. Au clic sur [Réserver] :
   - Stocker le voyage sélectionné
   - Passer à l'écran 4 (formulaire réservation)

**Pas d'appel métier** (filtrage JavaScript côté client)

---

### **Écran 4 : Formulaire réservation**

**Titre** : "Finaliser la réservation"

**Informations affichées** :
- Trajet : Fasankarana → Ambolomadinika
- Date : 14/01/2026 à 14h00
- Voiture : Confort XYZ
- Tarif unitaire : 25 000 Ar

**Champs de saisie** :
- Nom : [_______]
- Prénom : [_______]
- Contact : [_______]
- Nb places : [Liste 1-5]

**Calcul dynamique** :
- Montant total = tarif × nb_places

**Boutons** : [Annuler] [Confirmer]

**Logique d'affichage** :
- Vérifier disponibilité en temps réel
- Limiter nb places selon dispo actuelle
- Valider tous les champs obligatoires

---

### **Écran 5 : Confirmation**

**Titre** : "Réservation confirmée !"

**Informations** :
- N° réservation : #RES-001234
- Client : Jean RAKOTO
- Contact : 032 12 345 67
- Trajet, date, nb places, montant
- Statut : En attente de paiement

**Bouton** : [Effectuer le paiement]

---

## 2️⃣ MÉTIER

### **Classe : VoyageService**

```java
getAllVoyagesDispo() : List<VoyageDetailDTO>
  → Vue : VueVoyageDetaille
  → Vue : VuePlacesDisponibles
  → Filtrer : statut='prévu' ET places_dispo > 0
  → Retourne TOUS les voyages disponibles

getVoyageById(int id_voyage) : Voyage
  → Table : Voyage

verifierDisponibilite(int id_voyage, int nb_places) : boolean
  → Vue : VuePlacesDisponibles
```

**Note** : Les méthodes `rechercherVoyages()` et `calculerPlacesDisponibles()` ne sont plus nécessaires car le filtrage se fait côté client en JavaScript.

---

### **Classe : ClientService**

```java
creerOuRecupererClient(String nom, String prenom, String contact) : Client
  → Table : Client
  → Vérifier si existe par contact
  → Si non → INSERT nouveau client

getClientByContact(String contact) : Client
  → Table : Client
```

---

### **Classe : ReservationService**

```java
creerReservation(int id_client, int id_voyage, 
                 int nb_places, double montant) : Reservation
  → Table : Reservation
  → INSERT avec statut='en_attente_paiement'

calculerMontantTotal(int id_voyage, int nb_places) : double
  → Table : Voyage
  → Calcul : tarif_voyage × nb_places

annulerReservation(int id_reservation) : void
  → Table : Reservation
  → UPDATE statut='annulé'

getReservationById(int id_reservation) : Reservation
  → Table : Reservation
```

---

### **DTO : VoyageDetailDTO**

```java
class VoyageDetailDTO {
    int id_voyage
    String gare_depart_nom
    String gare_depart_ville
    String gare_arrivee_nom
    String gare_arrivee_ville
    Date date_depart
    Time heure_depart
    String nom_voiture
    String type_voiture
    int capacite
    int places_disponibles
    double tarif
    String statut
}
```

---

## 3️⃣ BASE DE DONNÉES

### **Tables utilisées**
1. Voyage
2. Trajet
3. GareRoutiere
4. Voiture
5. Client
6. Reservation

---

### **Vues à créer**

#### **Vue : VueVoyageDetaille**

**Objectif** : Récupérer tous les détails d'un voyage avec infos trajet et gares

**Tables concernées** :
- Voyage JOIN Trajet (sur id_trajet)
- JOIN GareRoutiere AS depart (sur id_gare_depart)
- JOIN GareRoutiere AS arrivee (sur id_gare_arrivee)
- JOIN Voiture (sur id_voiture)

**Colonnes retournées** :
- id_voyage, date_depart, heure_depart, statut, tarif_voyage
- gare_depart (nom, ville), gare_arrivee (nom, ville)
- voiture (nom, type, capacite)

---

#### **Vue : VuePlacesDisponibles**

**Objectif** : Calculer les places disponibles par voyage

**Tables concernées** :
- Voyage JOIN Voiture (sur id_voiture)
- LEFT JOIN Reservation (sur id_voyage)
  → WHERE statut != 'annulé'

**Calcul** :
```sql
places_disponibles = capacite - COALESCE(SUM(nombre_places_reservees), 0)
GROUP BY id_voyage
```

**Colonnes retournées** :
- id_voyage
- capacite
- places_reservees (somme)
- places_disponibles (calculé)

---

## 4️⃣ FLUX DE DONNÉES

### **Étape 1 : Chargement initial (Écran 1)**
```
Affichage → Métier : getAllVoyagesDispo()
Métier → BDD : SELECT VueVoyageDetaille + VuePlacesDisponibles
BDD → Métier → Affichage : List<VoyageDetailDTO> complète
→ Stocker en mémoire JavaScript
```

---

### **Étape 2 : Filtrage par gares (Écran 1 → 2)**
```
Affichage (JS) : Filtrer par gare_depart + gare_arrivee
→ Pas d'appel serveur
```

---

### **Étape 3 : Filtrage par date (Écran 2 → 3)**
```
Affichage (JS) : Filtrer par date_depart
→ Pas d'appel serveur
```

---

### **Étape 4 : Sélection voyage (Écran 3 → 4)**
```
Affichage : Stocker le voyage sélectionné
→ Afficher formulaire avec infos du voyage
```

---

### **Étape 5 : Confirmation réservation (Écran 4 → 5)**

**5.1 - Créer/récupérer client**
```
Affichage → Métier : creerOuRecupererClient(nom, prenom, contact)
Métier → BDD : SELECT Client (si existe)
Si non → INSERT Client
BDD → Métier : Retourne id_client
```

**5.2 - Vérifier disponibilité**
```
Métier : verifierDisponibilite(id_voyage, nb_places)
Métier → BDD : SELECT VuePlacesDisponibles
BDD → Métier : Vérification
```

**5.3 - Créer réservation**
```
Métier : creerReservation(id_client, id_voyage, nb_places, montant)
Métier → BDD : INSERT Reservation
BDD → Métier → Affichage : Confirmation + n° réservation
```

---

## ✅ Avantages de cette approche

- **1 seul appel serveur** au chargement initial
- **Filtrage rapide** côté client (pas de latence réseau)
- **Expérience fluide** pour l'utilisateur
- **Moins de charge serveur** (pas de requête à chaque étape)
- **Interface progressive** : gares → dates → heures

---

## 🎯 Concepts objets clés

- **Séparation des responsabilités** : 1 service par entité
- **DTO** : Transférer données enrichies entre couches
- **Vues SQL** : Éviter jointures répétées côté code
- **Validation métier** : Vérifier disponibilité avant insertion
- **Filtrage côté client** : Optimisation des performances
- **Encapsulation** : Services exposent méthodes métier