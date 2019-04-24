# Käyttöohje
Lataa tiedosto [ManVsMachine-1.1-SNAPSHOT.jar](https://github.com/LauriTahvanainen/ot-harjoitustyo/releases/tag/viikko6)

## Konfigurointi
Ohjelma etsii käynnistyshakemistosta konfiguraatiotiedostoa config.properties, joka määrittelee tietokannan sijainnin ja nimen. Sijainnin on oltava muodossa:

```
databasepath=path/database.db
```

Jos tiedostoa ei ole, luo ohjelma tämän tiedoston default-tiedostosta käynnistyshakemistoon. Samalla käynnistyshakemistoon luodaan tietokanta.

## Ohjelman käynnistäminen
Ohjelma käynnistetään komennolla
```
java -jar ManVsMachine-1.1-SNAPSHOT.jar
```

## Kirjautuminen
Sovellus käynnistyy kirjautumisnäkymään, jossa käyttäjä voi valita, joko kirjautumisen vanhalla käyttäjänimellä, tai uuden käyttäjänimen luomisen.


Kirjautuminen onnistuu valitsemalla _sign in with an existing account_, kirjoittamalla olemassaoleva käyttäjänimi syötekenttään ja painamalla _sign in_.

## Uuden käyttäjän luominen
kirjautumisnäkymästä on mahdollista siirtyä luomaan uusi käyttäjä painamalla nappia _Create a new account_.
Uusi käyttäjä luodaan syöttämällä käyttäjänimi syötekenttään ja painamalla _Create acoount_
Järjestelmä ilmoittaa, jos käyttäjä luotiin onnistuneesti.

## Pelin aloittaminen
Pelaamaan voi siirtyä päävalikosta painamalla nappia _Play_.
Tämän jälkeen pelaajalle avautuu valikko, jossa ensin valitaan vastustaja, algoritmi. Algoritmin valittuaan pelaajalle aukeaa karttanäkymä, jossa pelaaja voi valita haluamansa kartan. Kartasta painettuaan, pelaaja siirtyy pelinäkymään.

## Pelaaminen
Peli alkaa koneen reitin skannauksella. Pelaaja ei voi liikkua skannauksen aikana. Kun kone on skannannut reitin, lähtee se liikkeelle ja pelaajan on mahdollista liikkua.

Pelaaja liikkuu nuolinäppäimillä.

Pelin voi pysäyttää painamalla _Esc_ näppäintä. Pysäytysvalikosta voi palata takaisin päävalikkoon, tai aloittaa pelin alusta.

Pelin päätyttyä pelaajalle aukeaa valikko, jossa hän voi: aloittaa pelin alusta, tallentaa huipputuloksensa, jos hän voitti ja jos tulos oli huipputulos, tai siirtyä päävalikkoon.

## Pisteytys
Lopullinen tason pisteyty koostuu kahdesta osasta: aikapisteistä, ja pituuspisteistä. Pelaaja saa siis sitä enemmän pisteitä, mitä pidemmän reitin hän kulkee ja mitä lyhyemmässä ajassa. Jos kone voittaa, on pelaajan tulos 0 pistettä.
