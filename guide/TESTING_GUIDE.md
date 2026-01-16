# 🧪 Guide de Test - Types de Places

## Demarrage de l'application

```bash
cd app
mvn spring-boot:run
```

Application disponible à: `http://localhost:8080`

---

## Test du Dashboard Admin

### 1. Acces au Dashboard
URL: `http://localhost:8080/admin`

**Attendu**:
- Page avec titre "Tableau de Bord Admin"
- 4 cards principales:
  1. Types de Places
  2. Configuration Places
  3. Accueil
  4. Reservations

### 2. Liens de Navigation
- Cliquez "Types de Places" → `/admin/types-places`
- Cliquez "Configuration Places" → `/admin/config-places`
- Cliquez "Accueil" → `/`
- Cliquez "Reservations" → `/reservation/list`

---

## Test Types de Places

### 1. Acces a la Page
URL: `http://localhost:8080/admin/types-places`

**Attendu**:
- Titre "Gestion des Types de Places"
- Formulaire "Creer un Type de Place"
- Liste des types (vide au debut)

### 2. Creer un Type
1. Entrez "Premium" dans le champ "Nom du type de place"
2. Cliquez "Creer le Type"
3. **Attendu**: Message de succes, Premium apparait dans la liste

### 3. Creer d'autres Types
Repetez avec:
- "Standard"
- "Economique"

### 4. Voir les Types dans la Liste
**Attendu**:
- 3 types dans le tableau
- Chaque type avec ID et nom
- Boutons "Configurer" et "Supprimer"

### 5. Supprimer un Type
1. Cliquez "Supprimer" sur un type
2. Confirmez dans la popup
3. **Attendu**: Le type disparait de la liste

---

## Test Configuration Places

### 1. Acces a la Page
URL: `http://localhost:8080/admin/config-places`

**Attendu**:
- Liste de tous les voyages
- Chaque voyage avec:
  - Route (depart → arrivee)
  - Date et heure
  - Nom vehicule
  - Bouton "Configurer Places"

### 2. Configurer un Voyage
1. Cliquez "Configurer Places" sur un voyage
2. **Attendu**: Redirection vers `/admin/config-places/voyage/{id}`

### 3. Ajouter une Configuration
Sur la page detail:
1. Selectionnez un type dans le dropdown
2. Entrez nombre de places (ex: 10)
3. Entrez prix (ex: 140000)
4. Cliquez "Ajouter Configuration"
5. **Attendu**: Configuration apparait dans la liste

### 4. Voir les Configurations
**Attendu**:
- Chaque config avec:
  - Nom du type
  - Nombre de places
  - Prix unitaire
  - Prix total (nombre × prix)
  - Boutons Modifier et Supprimer

### 5. Modifier une Configuration
1. Cliquez "Modifier" sur une config
2. Modal s'ouvre avec champs editable
3. Modifiez nombre ou prix
4. Cliquez "Sauvegarder"
5. **Attendu**: Config mise a jour

### 6. Supprimer une Configuration
1. Cliquez "Supprimer" sur une config
2. Confirmez
3. **Attendu**: Configuration disparait

### 7. Validation Capacite
1. Essayez d'ajouter des places qui depassent la capacite
2. **Attendu**: Message d'alerte "Le total des places depasse..."

---

## Test Reservation avec Types

### 1. Acces a la Page de Reservation
URL: `http://localhost:8080/app/reservation`

**Attendu**:
- Page de recherche de voyage
- (Les types devraient etre integres ici)

### 2. Reservation Normale (sans type)
- Fonctionne comme avant
- Pas de changement visible

---

## Test Direct de l'API (avec Postman/cURL)

### Creer un Type de Place
```bash
POST /admin/types-places/creer
Content-Type: application/x-www-form-urlencoded

nom=VIP
```

### Lister les Types
```bash
GET /admin/types-places
```

### Creer une Configuration
```bash
POST /admin/config-places/creer
Content-Type: application/x-www-form-urlencoded

id_voyage=1
id_type_place=1
nombre=5
prix=100000
```

### Lister les Configs d'un Voyage
```bash
GET /admin/config-places/voyage/1
```

---

## Scenarii de Test Complets

### Scenario 1: Setup Complet
1. Creer 3 types: Premium, Standard, Economique
2. Aller sur un voyage
3. Ajouter configuration Premium (10 places, 140000)
4. Ajouter configuration Standard (15 places, 80000)
5. Ajouter configuration Economique (25 places, 50000)
6. Total: 50 places
7. Verifier: Capacite du vehicule ≥ 50

### Scenario 2: Modification
1. Modifier le prix de Premium a 150000
2. Verifier dans la liste
3. Total doit se recalculer

### Scenario 3: Suppression
1. Supprimer la config Economique
2. Total doit passer a 25 places
3. Verifier calcul

### Scenario 4: Erreur de Capacite
1. Essayer d'ajouter 100 places quand capacite=50
2. **Attendu**: Alerte
3. Ne pas pouvoir ajouter

---

## Checklist de Verification

### Backend
- ✅ Modeles charges correctement
- ✅ Repositories accessible
- ✅ Services fonctionnent
- ✅ Controllers repondent

### Frontend
- ✅ Dashboard accessible
- ✅ Types page charge
- ✅ Config page charge
- ✅ Detail page charge

### Donnees
- ✅ Types creates et stockes
- ✅ Configs creates et stockes
- ✅ Relations correctes
- ✅ Validations fonctionnent

### Navigation
- ✅ Tous les liens fonctionnent
- ✅ Redirection correcte
- ✅ Bouton retour fonctionne
- ✅ Menu consistant

---

## Logs Attendus

### Au Demarrage
```
...
[Hibernate] create table config_place_voyage ...
[Hibernate] create table type_place ...
[Hibernate] alter table reservation add id_type_place ...
INFO: Server started on port 8080
```

### Lors de l'Ajout d'un Type
```
Hibernate: INSERT INTO type_place (nom) VALUES (?)
```

### Lors de l'Ajout d'une Config
```
Hibernate: INSERT INTO config_place_voyage (...) VALUES (...)
```

---

## Résolution des Problèmes

### Problem: Page non trouvee (404)
- Verifiez l'URL exacte
- Verifiez que le controller retourne le bon template
- Verifiez le nom du template

### Problem: Donnees non sauvegardees
- Verifiez la base de donnees
- Verifiez les contraintes (UNIQUE, FK)
- Verifiez les erreurs de validation

### Problem: Template erreur (5XX)
- Verifiez la syntaxe Thymeleaf
- Verifiez les ${} expressions
- Verifiez qu'il existe bien le modele envoye

### Problem: Style casse
- Verifiez Bootstrap CSS charge
- Verifiez FontAwesome icones
- Verifiez paths statiques

---

## Notes

- Les donnees sont reinitializes a chaque demarrage de app
- Les types par defaut (Premium, Standard, Economique) sont auto-crees
- Les configurations sont vides au debut
- Les modals utilise Bootstrap 5

---

**Bon Testing! 🚀**
