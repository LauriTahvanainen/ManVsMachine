# Käyttöohje
Lataa tiedosto [ManVsMachine-1.1-SNAPSHOT.jar](https://github.com/LauriTahvanainen/ot-harjoitustyo/releases/tag/viikko6)

## Konfigurointi
Ohjelma etsii käynnistyshakemistosta konfiguraatiotiedostoa config.properties, joka määrittelee tietokannan sijainnin ja nimen. Sijainnin on oltava muodossa:

```
databasepath=path/database.db
```
Huom! Kansion jonne tietokanta tallennetaan on oltava olemassa!

Jos konfiguraatiotiedostoa ei ole, luo ohjelma tämän tiedoston default-tiedostosta käynnistyshakemistoon. Samalla käynnistyshakemistoon luodaan tietokanta.

## Ohjelman käynnistäminen
Ohjelma käynnistetään komennolla
```
java -jar ManVsMachine-1.1-SNAPSHOT.jar
```

## Kirjautuminen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/signin.png)
Sovellus käynnistyy kirjautumisnäkymään, jossa käyttäjä voi joko kirjautua olemassaolevalla käyttäjätunnuksella, tai siirtyä uuden käyttäjätunnuksen luomisen.

Kirjautuminen onnistuu, kirjoittamalla olemassaoleva käyttäjänimi ja tilin salasana syötekenttiin ja painamalla _Sign In_.
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/signinvisible.png)
Silmän kuvasta salasanan saa näkyviin ja pois näkyvistä.

## Uuden käyttäjän luominen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/createuser.png)
Kirjautumisnäkymästä on mahdollista siirtyä luomaan uusi käyttäjä painamalla nappia _Create New Account_.
Uusi käyttäjä luodaan syöttämällä käyttäjänimi, salasana, sekä salasanan vahvistus syötekenttiin ja painamalla _Create Account_.
Käyttäjänimen on oltava 4-16 merkkiä pitkä, uniikki, eikä se saa sisältää välilyöntejä. Salasanan on oltava 7-16 merkkiä pitkä, sisällettävä vain kirjaimia tai numeroja, sekä vähintään yhden pienen sekä ison kirjaimen, ja numeron.
Järjestelmä ilmoittaa, jos käyttäjätunnus luotiin onnistuneesti, kuten myös jos luomisessa tapahtui virhe.
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/usercreated.png)

## Päävalikko
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/menu.png)
Kirjautumisen jälkeen käyttäjä siirtyy päävalikkoon, jossa hän voi siirtyä joko, pelaamaan, huipputulosten tarkasteluun tai asetuksiin. Pelaaja voi myös lopettaa pelin, tai kirjautua ulos ja siirtyä takaisin kirjautumisnäkymään.

## Pelin aloittaminen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/algoselect.png)
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/mapselect.png)
Pelaamaan voi siirtyä päävalikosta painamalla nappia _Play_.
Tämän jälkeen pelaajalle avautuu valikko, jossa ensin valitaan vastustaja, algoritmi. Algoritmin valittuaan pelaajalle aukeaa karttanäkymä, jossa pelaaja voi valita haluamansa kartan. Kartasta painettuaan, pelaaja siirtyy pelinäkymään.

## Pelaaminen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/scanning.png)
Peli alkaa koneen reitin skannauksella. Pelaaja ei voi liikkua skannauksen aikana. Kun kone on skannannut reitin, lähtee se liikkeelle ja samaan aikaan pelaajan on mahdollista aloittaa liikkuminen. Pisteiden lasku alkaa myös skannauksen päätyttyä.

Pelaaja liikkuu nuolinäppäimillä.

![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/pause.png)
Pelin voi pysäyttää painamalla _Esc_ näppäintä. Pysäytysvalikosta voi palata takaisin päävalikkoon, tai aloittaa pelin alusta.

![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/winning.png)
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/losing.png)
Pelin päätyttyä pelaajalle aukeaa valikko, jossa hän voi: aloittaa pelin alusta, tallentaa huipputuloksensa (jos hän voitti ja jos tulos oli huipputulos), tai siirtyä päävalikkoon.
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/scoreupdated.png)

## Pisteytys
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/playing.png)
Lopullinen tason pisteyty koostuu kahdesta osasta: aikapisteistä(Time Score), ja pituuspisteistä(Route Length). Pelaaja saa siis sitä enemmän pisteitä, mitä pidemmän reitin hän kulkee ja mitä lyhyemmässä ajassa. Jos kone voittaa, on pelaajan tulos 0 pistettä.

## Huipputulosten tarkastelu
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/highscoremenu.png)
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/highscoremapselect.png)
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/highscoremap1bfs.png)
Huipputulosten tarkastelu näkymässä pelaaja voi valita algoritmin, ja tarkastella sitten sitä algoritmia vastaan tehtyjä tuloksia jokaisessa sen algoritmin sallimassa kartassa. Kartan valinta aukeaa lähes samaan tapaan kuin peliä aloittaessa. Kartan valittua näytetään pelaajalle hänen oma tuloksensa ylimpänä ja alleviivattuna, sekä 20 parasta tulosta. Tuloksia voi näkyä vain käyttäjiltä, jotka ovat kirjautuneet vähintään kerran peliin. Käyttäjän oma tulos on myös alleviivattu näiden 20 tuloksen joukossa, jos se kuuluu niihin.

## Asetukset
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/settingsmenu.png)
Asetuksissa käyttäjä voi vaihtaa käyttäjänimensä, salasanansa, sekä hahmonsa värin.

### Käyttäjänimen vaihtaminen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/updateusername.png)
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/usernameupdated.png)
Käyttäjänimen vaihtaminen tehdään syöttämällä uusi nimi tekstikenttään, ja painamalla _Update Username_. Käyttäjänimi päivitetään jos se on validi.

### Salasanan vaihtaminen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/changepassword.png)
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/passwordupdated.png)
Salasanan vaihtaminen tehdään syötämällä uusi salasana ja salasanan vahvistus salasanakenttiin ja painamalla _Change Password_. Salasana päivitetään jos jos on validi.

### Hahmon kustomointi
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/changecolor1.png)
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/colorchanged.png)
Käyttäjän on myös mahdollista vaihtaa pelihahmonsa väriä. Tämä tapahtuu valitsemalla mieluinen väri valintanapista avautuvalla värinvalitisimella, ja painamalla _Change Color_. Järjestelmä ilmoittaa värin vaihtumisesta.
