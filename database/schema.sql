-- Suppression des tables si elles existent
DROP TABLE IF EXISTS paiement CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS voyage CASCADE;
DROP TABLE IF EXISTS voiture CASCADE;
DROP TABLE IF EXISTS trajet CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS gare_routiere CASCADE;

-- Table GareRoutiere
CREATE TABLE gare_routiere (
    id_gare SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(20)
);

-- Table Trajet
CREATE TABLE trajet (
    id_trajet SERIAL PRIMARY KEY,
    id_gare_depart INTEGER NOT NULL REFERENCES gare_routiere(id_gare),
    id_gare_arrivee INTEGER NOT NULL REFERENCES gare_routiere(id_gare),
    distance_km DOUBLE PRECISION,
    duree_estimee INTEGER,
    tarif_defaut DOUBLE PRECISION NOT NULL
);

-- Table Voiture
CREATE TABLE voiture (
    id_voiture SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    capacite INTEGER NOT NULL,
    consommation DOUBLE PRECISION,
    immatriculation VARCHAR(50),
    statut VARCHAR(50)
);

-- Table Voyage
CREATE TABLE voyage (
    id_voyage SERIAL PRIMARY KEY,
    id_trajet INTEGER NOT NULL REFERENCES trajet(id_trajet),
    date_depart DATE NOT NULL,
    heure_depart TIME NOT NULL,
    id_voiture INTEGER NOT NULL REFERENCES voiture(id_voiture),
    tarif_voyage DOUBLE PRECISION,
    statut VARCHAR(50) DEFAULT 'prevu'
);

-- Table Client
CREATE TABLE client (
    id_client SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    contact VARCHAR(50) UNIQUE NOT NULL
);

-- Table Reservation
CREATE TABLE reservation (
    id_reservation SERIAL PRIMARY KEY,
    id_client INTEGER NOT NULL REFERENCES client(id_client),
    id_voyage INTEGER NOT NULL REFERENCES voyage(id_voyage),
    nombre_places_reservees INTEGER NOT NULL,
    date_reservation DATE NOT NULL,
    montant_total DOUBLE PRECISION NOT NULL,
    statut VARCHAR(50) DEFAULT 'en_attente_paiement'
);

-- Table Paiement
CREATE TABLE paiement (
    id_paiement SERIAL PRIMARY KEY,
    id_reservation INTEGER NOT NULL REFERENCES reservation(id_reservation),
    montant DOUBLE PRECISION NOT NULL,
    mode_paiement VARCHAR(50),
    date_paiement DATE,
    reference_transaction VARCHAR(100)
);

-- Vues pour les requetes frequentes
CREATE VIEW vue_voyage_detaille AS
SELECT 
    v.id_voyage,
    v.date_depart,
    v.heure_depart,
    v.statut,
    COALESCE(v.tarif_voyage, t.tarif_defaut) as tarif,
    gd.nom as gare_depart_nom,
    gd.ville as gare_depart_ville,
    ga.nom as gare_arrivee_nom,
    ga.ville as gare_arrivee_ville,
    vo.nom as nom_voiture,
    vo.type as type_voiture,
    vo.capacite
FROM voyage v
JOIN trajet t ON v.id_trajet = t.id_trajet
JOIN gare_routiere gd ON t.id_gare_depart = gd.id_gare
JOIN gare_routiere ga ON t.id_gare_arrivee = ga.id_gare
JOIN voiture vo ON v.id_voiture = vo.id_voiture;

CREATE VIEW vue_places_disponibles AS
SELECT 
    v.id_voyage,
    vo.capacite,
    COALESCE(SUM(CASE WHEN r.statut != 'annule' THEN r.nombre_places_reservees ELSE 0 END), 0) as places_reservees,
    vo.capacite - COALESCE(SUM(CASE WHEN r.statut != 'annule' THEN r.nombre_places_reservees ELSE 0 END), 0) as places_disponibles
FROM voyage v
JOIN voiture vo ON v.id_voiture = vo.id_voiture
LEFT JOIN reservation r ON r.id_voyage = v.id_voyage
GROUP BY v.id_voyage, vo.capacite;

