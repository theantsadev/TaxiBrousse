# 📘 MCD – Gestion Taxi-Brousse

## 🏢 GareRoutiere
**Rôle métier** : Point de départ et d’arrivée des trajets.

- id_gare (PK)
- nom
- ville
- adresse
- telephone

---

## 🛣️ Trajet
**Rôle métier** : Itinéraire fixe et abstrait entre deux gares routières.

- id_trajet (PK)
- id_gare_depart (FK → GareRoutiere)
- id_gare_arrivee (FK → GareRoutiere)
- distance_km
- duree_estimee
- tarif_defaut

**Règles de gestion** :
- Un trajet est défini par une gare de départ et une gare d’arrivée
- Un trajet peut être effectué sur plusieurs jours
- Un trajet peut être effectué plusieurs fois dans une même journée
- Un trajet peut donner lieu à plusieurs voyages

---

## 🚐 Voiture
**Rôle métier** : Moyen de transport utilisé pour les voyages.

- id_voiture (PK)
- nom
- type (standard, confort, VIP)
- capacite
- consommation
- immatriculation
- statut (disponible, en_maintenance)

**Règles de gestion** :
- Chaque voiture possède une capacité fixe
- Une voiture peut effectuer plusieurs voyages à des moments différents

---

## 🗓️ Voyage
**Rôle métier** : Occurrence planifiée d’un trajet  
(équivalent d’une *session de film* au cinéma).

- id_voyage (PK)
- id_trajet (FK → Trajet)
- date_depart
- heure_depart
- id_voiture (FK → Voiture)
- tarif_voyage (nullable)
- statut (prévu, en_cours, terminé, annulé)

**Règles de gestion** :
- Un voyage correspond à un trajet à une date et une heure données
- Un voyage est effectué par une seule voiture
- Un trajet peut avoir plusieurs voyages

---

## 🧍 Client
**Rôle métier** : Passager effectuant des réservations.

- id_client (PK)
- nom
- prenom
- contact

---

## 🎟️ Reservation
**Rôle métier** : Réservation de places par un client pour un voyage.

- id_reservation (PK)
- id_client (FK → Client)
- id_voyage (FK → Voyage)
- nombre_places_reservees
- date_reservation
- montant_total
- statut (en_attente_paiement, confirmé, annulé)

**Règles de gestion** :
- Un client peut effectuer plusieurs réservations
- Une réservation concerne un seul voyage
- Le nombre de places réservées ne peut pas dépasser la capacité du voyage

---

## 💰 Paiement
**Rôle métier** : Encaissement lié à une réservation.

- id_paiement (PK)
- id_reservation (FK → Reservation)
- montant
- mode_paiement
- date_paiement
- reference_transaction

**Règles de gestion** :
- Une réservation peut donner lieu à un ou plusieurs paiements
- Le chiffre d’affaires est calculé à partir des paiements

---

## 🔗 Cardinalités principales

- GareRoutiere (1) —— (N) Trajet (départ)
- GareRoutiere (1) —— (N) Trajet (arrivée)
- Trajet (1) —— (N) Voyage
- Voiture (1) —— (N) Voyage
- Voyage (1) —— (N) Reservation
- Client (1) —— (N) Reservation
- Reservation (1) —— (N) Paiement
