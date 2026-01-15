# 📝 UserFlow : Reservation de voyage

**Fonctionnalite** : Un client achete des places pour Fasankarana → Ambolomadinika, le 14/01 a 14h

---

## 1️⃣ AFFICHAGE

### **Ecran 1 : Selection des gares**

**Titre** : "Rechercher un voyage"

**Elements** :
- Gare de depart : [Liste deroulante]
- Gare d'arrivee : [Liste deroulante]
- Bouton [Rechercher]

**Logique d'affichage** :
1. Au chargement de la page :
   - Appel metier : `VoyageService.getAllVoyagesDispo()` → List<VoyageDetailDTO>
   - Stocker la liste complete en memoire (JavaScript)

2. Remplir les listes deroulantes :
   - **Gare depart** : Projection unique sur `gare_depart_nom + gare_depart_ville`
   - **Gare arrivee** : Projection unique sur `gare_arrivee_nom + gare_arrivee_ville`
   - Eliminer les doublons

3. Au clic sur [Rechercher] :
   - Filtrer la liste en JavaScript :
     ```js
     voyagesFiltres = voyages.filter(v => 
       v.gare_depart_nom == gareDepart_selected &&
       v.gare_arrivee_nom == gareArrivee_selected
     )
     ```
   - Passer a l'ecran 2 avec cette liste filtree

**Appel metier** :
→ `VoyageService.getAllVoyagesDispo()` : List<VoyageDetailDTO>

---

### **Ecran 2 : Selection de la date**

**Titre** : "Fasankarana → Ambolomadinika - Choisir une date"

**Elements** :
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
1. A partir de `voyagesFiltres` (liste de l'ecran 1)
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
   - Filtrer par date selectionnee
   - Passer a l'ecran 3

**Pas d'appel metier** (filtrage JavaScript cote client)

---

### **Ecran 3 : Selection de l'heure**

**Titre** : "Fasankarana → Ambolomadinika - 14 janvier 2026"

**Elements** :
Liste des horaires disponibles :

```
┌────────────────────────────────────────┐
│ 🕐 08h00                               │
│ 🚐 Voiture : Standard ABC              │
│ 💺 Places disponibles : 10/12          │
│ 💰 Tarif : 20 000 Ar                   │
│              [Reserver]                │
└────────────────────────────────────────┘

┌────────────────────────────────────────┐
│ 🕐 14h00                               │
│ 🚐 Voiture : Confort XYZ               │
│ 💺 Places disponibles : 12/15          │
│ 💰 Tarif : 25 000 Ar                   │
│              [Reserver]                │
└────────────────────────────────────────┘

┌────────────────────────────────────────┐
│ 🕐 16h30                               │
│ 🚐 Voiture : VIP Premium               │
│ 💺 Places disponibles : 6/8            │
│ 💰 Tarif : 30 000 Ar                   │
│              [Reserver]                │
└────────────────────────────────────────┘
```

**Logique d'affichage** :
1. A partir des voyages filtres par date (ecran 2)
2. Afficher chaque voyage avec :
   - Heure de depart
   - Nom et type de voiture
   - Places disponibles
   - Tarif
3. Trier par heure croissante
4. Au clic sur [Reserver] :
   - Stocker le voyage selectionne
   - Passer a l'ecran 4 (formulaire reservation)

**Pas d'appel metier** (filtrage JavaScript cote client)

---

### **Ecran 4 : Formulaire reservation**

**Titre** : "Finaliser la reservation"

**Informations affichees** :
- Trajet : Fasankarana → Ambolomadinika
- Date : 14/01/2026 a 14h00
- Voiture : Confort XYZ
- Tarif unitaire : 25 000 Ar

**Champs de saisie** :
- Nom : [_______]
- Prenom : [_______]
- Contact : [_______]
- Nb places : [Liste 1-5]

**Calcul dynamique** :
- Montant total = tarif × nb_places

**Boutons** : [Annuler] [Confirmer]

**Logique d'affichage** :
- Verifier disponibilite en temps reel
- Limiter nb places selon dispo actuelle
- Valider tous les champs obligatoires

---

### **Ecran 5 : Confirmation**

**Titre** : "Reservation confirmee !"

**Informations** :
- N° reservation : #RES-001234
- Client : Jean RAKOTO
- Contact : 032 12 345 67
- Trajet, date, nb places, montant
- Statut : En attente de paiement

**Bouton** : [Effectuer le paiement]

---

## 2️⃣ METIER

### **Classe : VoyageService**

```java
getAllVoyagesDispo() : List<VoyageDetailDTO>
  → Vue : VueVoyageDetaille
  → Vue : VuePlacesDisponibles
  → Filtrer : statut='prevu' ET places_dispo > 0
  → Retourne TOUS les voyages disponibles

getVoyageById(int id_voyage) : Voyage
  → Table : Voyage

verifierDisponibilite(int id_voyage, int nb_places) : boolean
  → Vue : VuePlacesDisponibles
```

**Note** : Les methodes `rechercherVoyages()` et `calculerPlacesDisponibles()` ne sont plus necessaires car le filtrage se fait cote client en JavaScript.

---

### **Classe : ClientService**

```java
creerOuRecupererClient(String nom, String prenom, String contact) : Client
  → Table : Client
  → Verifier si existe par contact
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
  → UPDATE statut='annule'

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

## 3️⃣ BASE DE DONNEES

### **Tables utilisees**
1. Voyage
2. Trajet
3. GareRoutiere
4. Voiture
5. Client
6. Reservation

---

### **Vues a creer**

#### **Vue : VueVoyageDetaille**

**Objectif** : Recuperer tous les details d'un voyage avec infos trajet et gares

**Tables concernees** :
- Voyage JOIN Trajet (sur id_trajet)
- JOIN GareRoutiere AS depart (sur id_gare_depart)
- JOIN GareRoutiere AS arrivee (sur id_gare_arrivee)
- JOIN Voiture (sur id_voiture)

**Colonnes retournees** :
- id_voyage, date_depart, heure_depart, statut, tarif_voyage
- gare_depart (nom, ville), gare_arrivee (nom, ville)
- voiture (nom, type, capacite)

---

#### **Vue : VuePlacesDisponibles**

**Objectif** : Calculer les places disponibles par voyage

**Tables concernees** :
- Voyage JOIN Voiture (sur id_voiture)
- LEFT JOIN Reservation (sur id_voyage)
  → WHERE statut != 'annule'

**Calcul** :
```sql
places_disponibles = capacite - COALESCE(SUM(nombre_places_reservees), 0)
GROUP BY id_voyage
```

**Colonnes retournees** :
- id_voyage
- capacite
- places_reservees (somme)
- places_disponibles (calcule)

---

## 4️⃣ FLUX DE DONNEES

### **Etape 1 : Chargement initial (Ecran 1)**
```
Affichage → Metier : getAllVoyagesDispo()
Metier → BDD : SELECT VueVoyageDetaille + VuePlacesDisponibles
BDD → Metier → Affichage : List<VoyageDetailDTO> complete
→ Stocker en memoire JavaScript
```

---

### **Etape 2 : Filtrage par gares (Ecran 1 → 2)**
```
Affichage (JS) : Filtrer par gare_depart + gare_arrivee
→ Pas d'appel serveur
```

---

### **Etape 3 : Filtrage par date (Ecran 2 → 3)**
```
Affichage (JS) : Filtrer par date_depart
→ Pas d'appel serveur
```

---

### **Etape 4 : Selection voyage (Ecran 3 → 4)**
```
Affichage : Stocker le voyage selectionne
→ Afficher formulaire avec infos du voyage
```

---

### **Etape 5 : Confirmation reservation (Ecran 4 → 5)**

**5.1 - Creer/recuperer client**
```
Affichage → Metier : creerOuRecupererClient(nom, prenom, contact)
Metier → BDD : SELECT Client (si existe)
Si non → INSERT Client
BDD → Metier : Retourne id_client
```

**5.2 - Verifier disponibilite**
```
Metier : verifierDisponibilite(id_voyage, nb_places)
Metier → BDD : SELECT VuePlacesDisponibles
BDD → Metier : Verification
```

**5.3 - Creer reservation**
```
Metier : creerReservation(id_client, id_voyage, nb_places, montant)
Metier → BDD : INSERT Reservation
BDD → Metier → Affichage : Confirmation + n° reservation
```

---

## ✅ Avantages de cette approche

- **1 seul appel serveur** au chargement initial
- **Filtrage rapide** cote client (pas de latence reseau)
- **Experience fluide** pour l'utilisateur
- **Moins de charge serveur** (pas de requete a chaque etape)
- **Interface progressive** : gares → dates → heures

---

## 🎯 Concepts objets cles

- **Separation des responsabilites** : 1 service par entite
- **DTO** : Transferer donnees enrichies entre couches
- **Vues SQL** : Eviter jointures repetees cote code
- **Validation metier** : Verifier disponibilite avant insertion
- **Filtrage cote client** : Optimisation des performances
- **Encapsulation** : Services exposent methodes metier
