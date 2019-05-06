# Ohjelmistotekniikka, harjoitustyö
## [ManVsMachine-peli](https://github.com/LauriTahvanainen/ot-harjoitustyo/tree/master/ManVsMachine) 
Peli sijoittuu vaihtoehtoiseen todellisuuteen, jossa yli-innokkaat robotit yrittävät pakkosyöttää pelaajan kuoliaaksi maksalaatikolla. Ainoa mahdollisuus pakenemiseen vaikuttaisi olevan mystinen portaali, joka palauttaa pelaajan tähän täysin samaan todellisuuteen. Ehditkö portaalille ennen kuin robotti uunittaa maksalaatikkonsa?

Pelin tavoitteena on ohjata oma hahmo labyrintin läpi portaalille ennen vastustajaa, algoritmia. Algoritmi-vastustaja on valittavissa eri algoritmeista. Peli mahdollistaa pelaajille huipputulosten ylöskirjaamisen, ja tarkastelun. Ei toimi Windows alustalla tällä hetkellä.

A small 2D game where you try to solve a maze before an algorithm. Does not work on Windows at the moment.

### Dokumentaatio
[Vaatimusmäärittely](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/vaatimusmaarittely.md)

[Tyoaikakirjanpito](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/arkkitehtuuri.md)

[Kayttöohje](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kayttoohje.md)

[Testausdokumentti](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/testaus.md)

## Releaset
[Viikko 5: V1.0](https://github.com/LauriTahvanainen/ot-harjoitustyo/releases/tag/viikko5) 

[Viikko 6: V1.1](https://github.com/LauriTahvanainen/ot-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/LauriTahvanainen/ot-harjoitustyo/releases/tag/loppupalautus)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn test jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _ManVsMachine-1.0-SNAPSHOT.jar_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

### Materials
Parts of music mixed from: 
[Machine Takeover by TeknoAXE](https://www.youtube.com/watch?v=pyYZQOy082o)

[Licence](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/TeknoAxeLicence.pdf)

Textures
##### Seinät
[Wall cover 5](https://opengameart.org/content/wall-cover-5) by Georges "TRaK" Grondin, under the [CC BY-SA 3.0](https://creativecommons.org/licenses/by-sa/3.0/) licence. 

##### Uuni
[Oven](https://opengameart.org/content/oven) by [Santoniche](https://opengameart.org/users/santoniche), under the [(CC BY 4.0)](https://creativecommons.org/licenses/by/4.0/) Some parts of the oven were cut to fit the other textures.

##### Muut tekstuurit
Other textures under the public domain, mainly from [opengameart.org](https://opengameart.org/). Sprite textures by [Umz](https://opengameart.org/users/umz).
