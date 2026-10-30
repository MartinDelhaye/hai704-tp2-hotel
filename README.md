# HAI704I TP 2 - Projet `hotel`

> Francisco-Ernesto Suarez-Roca.
>
> Martin Delhaye

Ce projet est le **fournisseur** : il publie le service web d'un hôtel.
Il est indépendant du projet `agence`, qui ne le connaît que par son contrat (WSDL).

> État : A4

## 1. Rôle et relations

| Projet | Rôle | Lien avec `hotel` |
|---|---|---|
| `hotel` | publie le service SOAP (`HotelService`) | — |
| `agence` | client qui appelle l'hôtel | aucune dépendance : il utilise uniquement le WSDL/XSD publié par l'hôtel |

## 2. Environnement

- **JDK** : Eclipse Temurin 17.0.20.1 (le projet est compilé avec `--release 17`)
- **Maven** : 3.9.x
- **IDE** : IntelliJ IDEA 2026.2.3 (Windows). Toutes les commandes ci-dessous sont des commandes Maven, aucun élément ne dépend de l'IDE.

## 3. Dépendances

| Dépendance | Version | Usage |
|---|---|---|
| `com.sun.xml.ws:jaxws-rt` (Eclipse Metro) | 4.0.5 | API Jakarta XML Web Services + implémentation + JAXB |
| `org.codehaus.mojo:exec-maven-plugin` | 3.1.0 | lancement du serveur avec `mvn exec:java` |

Espace de noms `jakarta.*` exclusivement.

## 4. Construction et exécution

Depuis la racine du projet `hotel` :

```
mvn -q compile exec:java                        # port 8081 par défaut
mvn -q compile exec:java "-Dexec.args=8082"     # autre port
```

Dans IntelliJ : lancer la classe `hai704i.tp2.hotel.ServeurHotel` (le port est un argument du programme).

Le processus reste actif tant que le serveur écoute. Message attendu :
`Hôtel publié : http://localhost:8081/hotel?wsdl`

Pour arrêter : `Ctrl+C` (terminal) ou bouton d'arrêt (IntelliJ).

## 6. Adresses publiées

| Ressource | URL |
|---|---|
| Point d'accès SOAP | `http://localhost:<port>/hotel` |
| Contrat WSDL | `http://localhost:<port>/hotel?wsdl` |
| Schéma XSD | `http://localhost:<port>/hotel?xsd=1` |

Port par défaut : 8081

## 6. Jeu de données fictif

- **Agences partenaires** : `AG-01`, `AG-02`, `AG-67`. Toute autre agence reçoit la faute `AgenceInconnueException`.
- **Chambres** (ville : Montpellier, hôtel : « Hôtel 1 »)

| Id | Nom | Étoiles | Capacité | Prix / nuit |
|---|---|---|---|---|
| CH-001 | Premium | 4 | 2 | 99.99 |
| CH-002 | Familiale | 3 | 4 | 123.45 |
| CH-003 | Classique | 2 | 1 | 67.00 |

Le prix d'une offre est le prix de la nuit multiplié par le nombre de nuits.
Aucune donnée bancaire réelle n'est utilisée dans ce projet.

## 8. Essai à la main

Exemple de requête (fichier `captures/requete.xml`). Les éléments de `<demande>` doivent suivre l'ordre du schéma (alphabétique) :

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:h="http://hai704i.hotel.fr/">
  <soap:Body>
    <h:chercherOffres>
      <demande>
        <agence>AG-01</agence>
        <arrivee>2026-12-10</arrivee>
        <depart>2026-12-13</depart>
        <etoilesMin>3</etoilesMin>
        <personnes>2</personnes>
        <prixMax>400.00</prixMax>
        <ville>Montpellier</ville>
      </demande>
    </h:chercherOffres>
  </soap:Body>
</soap:Envelope>
```

```
curl.exe -s -X POST http://localhost:8081/hotel -H "Content-Type: text/xml; charset=utf-8" --data-binary "@captures/requete.xml"
```

Résultat attendu : deux offres (`OF-CH-001` à 299.97, `OF-CH-002` à 370.35).
Avec `<agence>AG-99999</agence>` : HTTP 500 et un `<S:Fault>` dont le `<detail>` contient `AgenceInconnueException`.