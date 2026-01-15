-- Insertion des données de test

-- Gares routières
INSERT INTO gare_routiere (nom, ville, adresse, telephone) VALUES
('Gare Centrale Fasankarana', 'Fasankarana', '123 Rue Principale', '032 12 34 56'),
('Gare Ambolomadinika', 'Ambolomadinika', '456 Avenue Centrale', '032 23 45 67'),
('Gare Antananarivo', 'Antananarivo', '789 Boulevard Rova', '032 34 56 78'),
('Gare Toliara', 'Toliara', '321 Route Sud', '032 45 67 89'),
('Gare Antsirabe', 'Antsirabe', '654 Chemin Est', '032 56 78 90');

-- Trajets
INSERT INTO trajet (id_gare_depart, id_gare_arrivee, distance_km, duree_estimee, tarif_defaut) VALUES
(1, 2, 150, 240, 25000),
(1, 3, 300, 480, 45000),
(2, 3, 200, 360, 35000),
(3, 4, 450, 720, 60000),
(3, 5, 100, 120, 15000),
(1, 4, 600, 960, 80000);

-- Voitures
INSERT INTO voiture (nom, type, capacite, consommation, immatriculation, statut) VALUES
('Standard ABC', 'standard', 12, 8.5, 'ABC-001', 'disponible'),
('Confort XYZ', 'confort', 15, 9.0, 'XYZ-002', 'disponible'),
('VIP Premium', 'VIP', 8, 10.0, 'VIP-003', 'disponible'),
('Express 123', 'standard', 12, 8.5, 'EXP-004', 'disponible'),
('Luxe 456', 'VIP', 8, 10.0, 'LUX-005', 'disponible');

-- Voyages
INSERT INTO voyage (id_trajet, date_depart, heure_depart, id_voiture, tarif_voyage, statut) VALUES
(1, '2026-01-13', '08:00:00', 1, 25000, 'prevu'),
(1, '2026-01-13', '14:00:00', 2, 25000, 'prevu'),
(1, '2026-01-13', '16:30:00', 3, 30000, 'prevu'),
(1, '2026-01-14', '08:00:00', 1, 25000, 'prevu'),
(1, '2026-01-14', '14:00:00', 2, 25000, 'prevu'),
(1, '2026-01-14', '16:30:00', 4, 20000, 'prevu'),
(1, '2026-01-15', '08:00:00', 5, 28000, 'prevu'),
(1, '2026-01-15', '14:00:00', 1, 25000, 'prevu'),
(2, '2026-01-15', '06:00:00', 2, 45000, 'prevu'),
(2, '2026-01-15', '14:00:00', 3, 50000, 'prevu'),
(3, '2026-01-16', '09:00:00', 4, 35000, 'prevu'),
(3, '2026-01-16', '15:00:00', 1, 32000, 'prevu'),
(4, '2026-01-17', '05:00:00', 2, 60000, 'prevu'),
(5, '2026-01-17', '10:00:00', 3, 15000, 'prevu'),
(6, '2026-01-18', '04:00:00', 5, 80000, 'prevu');

-- Clients
INSERT INTO client (nom, prenom, contact) VALUES
('Rakoto', 'Jean', '032 12 345 67'),
('Rasolo', 'Marie', '032 23 456 78'),
('Randriamanana', 'Pierre', '032 34 567 89'),
('Ratsimilaho', 'Sophie', '032 45 678 90'),
('Rajohnson', 'Marc', '032 56 789 01');

-- Réservations (quelques exemples)
INSERT INTO reservation (id_client, id_voyage, nombre_places_reservees, date_reservation, montant_total, statut) VALUES
(1, 1, 2, '2026-01-10', 50000, 'en_attente_paiement'),
(2, 2, 1, '2026-01-11', 25000, 'confirmé'),
(3, 3, 3, '2026-01-11', 90000, 'en_attente_paiement'),
(4, 5, 2, '2026-01-12', 50000, 'confirmé'),
(5, 6, 1, '2026-01-12', 20000, 'confirmé');

-- Paiements
INSERT INTO paiement (id_reservation, montant, mode_paiement, date_paiement, reference_transaction) VALUES
(2, 25000, 'Espèces', '2026-01-11', 'PAY-20260111-001'),
(4, 50000, 'Carte bancaire', '2026-01-12', 'PAY-20260112-002'),
(5, 20000, 'Espèces', '2026-01-12', 'PAY-20260112-003');
