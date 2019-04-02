# Ohjelmistotekniikka, harjoitustyö
## [ManVsMachine-peli](https://github.com/LauriTahvanainen/ot-harjoitustyo/tree/master/ManVsMachine) 
Pelin tarkoituksena on ohjata oma hahmo labyrintin läpi ennen vastustajaa, algoritmia. Algoritmi-vastustaja on valittavissa eri algoritmeista. Peli mahdollistaa pelaajille huipputulosten ylöskirjaamisen, ja tarkastelun.
### Dokumentaatio
[Vaatimusmäärittely](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/vaatimusmaarittely.md)

[Tyoaikakirjanpito](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/tyoaikakirjanpito.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _ManVsMachine-1.0-SNAPSHOT.jar_

