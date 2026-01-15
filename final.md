# 📘 MCD – Gestion Taxi-Brousse

## 🏢 GareRoutiere
**Role metier** : Point de depart et d’arrivee des trajets.

- id_gare (PK)
- nom
- ville
- adresse
- telephone

---

## 🛣️ Trajet
**Role metier** : Itineraire fixe et abstrait entre deux gares routieres.

- id_trajet (PK)
- id_gare_depart (FK → GareRoutiere)
- id_gare_arrivee (FK → GareRoutiere)
- distance_km
- duree_estimee
- tarif_defaut

**Regles de gestion** :
- Un trajet est defini par une gare de depart et une gare d’arrivee
- Un trajet peut etre effectue sur plusieurs jours
- Un trajet peut etre effectue plusieurs fois dans une meme journee
- Un trajet peut donner lieu a plusieurs voyages

---

## 🚐 Voiture
**Role metier** : Moyen de transport utilise pour les voyages.

- id_voiture (PK)
- nom
- type (standard, confort, VIP)
- capacite
- consommation
- immatriculation
- statut (disponible, en_maintenance)

**Regles de gestion** :
- Chaque voiture possede une capacite fixe
- Une voiture peut effectuer plusieurs voyages a des moments differents

---

## 🗓️ Voyage
**Role metier** : Occurrence planifiee d’un trajet  
(equivalent d’une *session de film* au cinema).

- id_voyage (PK)
- id_trajet (FK → Trajet)
- date_depart
- heure_depart
- id_voiture (FK → Voiture)
- tarif_voyage (nullable)
- statut (prevu, en_cours, termine, annule)

**Regles de gestion** :
- Un voyage correspond a un trajet a une date et une heure donnees
- Un voyage est effectue par une seule voiture
- Un trajet peut avoir plusieurs voyages

---

## 🧍 Client
**Role metier** : Passager effectuant des reservations.

- id_client (PK)
- nom
- prenom
- contact

---

## 🎟️ Reservation
**Role metier** : Reservation de places par un client pour un voyage.

- id_reservation (PK)
- id_client (FK → Client)
- id_voyage (FK → Voyage)
- nombre_places_reservees
- date_reservation
- montant_total
- statut (en_attente_paiement, confirme, annule)

**Regles de gestion** :
- Un client peut effectuer plusieurs reservations
- Une reservation concerne un seul voyage
- Le nombre de places reservees ne peut pas depasser la capacite du voyage

---

## 💰 Paiement
**Role metier** : Encaissement lie a une reservation.

- id_paiement (PK)
- id_reservation (FK → Reservation)
- montant
- mode_paiement
- date_paiement
- reference_transaction

**Regles de gestion** :
- Une reservation peut donner lieu a un ou plusieurs paiements
- Le chiffre d’affaires est calcule a partir des paiements

---

## 🔗 Cardinalites principales

- GareRoutiere (1) —— (N) Trajet (depart)
- GareRoutiere (1) —— (N) Trajet (arrivee)
- Trajet (1) —— (N) Voyage
- Voiture (1) —— (N) Voyage
- Voyage (1) —— (N) Reservation
- Client (1) —— (N) Reservation
- Reservation (1) —— (N) Paiement

