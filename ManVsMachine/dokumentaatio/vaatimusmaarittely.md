# Vaatimusmäärittely

## Sovelluksen tarkoitus
Pelin tarkoituksena on ohjata oma hahmo labyrintin läpi ennen vastustajaa, algoritmia. Algoritmi-vastustaja on valittavissa eri algoritmeista. Peli mahdollistaa pelaajille huipputulosten ylöskirjaamisen, ja tarkastelun.

## Käyttäjät
Perusohjelmassa sovelluksen käyttäjärooleja on vain yksi, *normaali käyttäjä*. Normaali käyttäjiä voi olla monella käyttäjänimellä, mutta perusohjelmaan ei ainakaan aluksi toteutetta erityistä salasanakirjautumismahdollisuutta. Perusohjelmassa käyttäjä syöttää sovelluksen aloitusruudulla, joko vanhan käyttäjänimensä, tai luo uuden käyttäjänimen. Myöhemmässä vaiheessa sovellukseen saatetaan lisätä *pääkäyttäjä*, jolla on suuremmat käyttöoikeudet, esimerkiksi huipputulosten poistamisoikeus tai mahdollisuus käyttötilastojen laatimiseen, sekä salasanallinen kirjautumismahdollisuus käyttäjille.

## Käyttöliittymäluonnos
Sovellus aukeaa kirjautumisnäkymään, jossa käyttäjä voi joko luoda uuden käyttäjänimen, tai kirjautua vanhalla käyttäjänimellä. Tämän jälkeen avautuu päävalikko, josta pelaaja voi siirtyä:
* Uuteen peliin
* Huipputulosten tarkasteluun
* Asetuksiin
Pelinäkymä on labyrintti, jonka lähtö on vasemmassa yläkulmassa, ja maali oikeassa alakulmassa. Pelin päätyttyä pelaaja voi siirtyä päävalikkoon, tai tulosten tallentamiseen.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista
* Käyttäjän on mahdollista joko kirjautua vanhalla käyttäjänimellä, tai kirjautua uudella käyttäjänimellä.
* Käyttäjä voi syöttää käyttäjänimensä lisäyslomakkeeseen uutta käyttäjänimeä luodessa. Nimen on oltava:
        * Vähintään 4 merkkiä, maksimissaan 16 merkkiä pitkä
        * Uniikki
* Kirjautuessa vanhalla nimellä, järjestelmä ilmoittaa jos nimeä ei ole järjestelmässä.

### Kirjautumisen jälkeen
* Käyttäjä näkee päävalikon, josta hän voi valita:
	* Uuden pelin
	* Huipputulosten tarkastelun
	* Asetukset
* Uuden pelin valitessaan käyttäjä voi valita algoritmeista vastustajan itselleen, minkä jälkeen peli alkaa satunnaisessa, valmiiksi generoidussa (EI TARKOITA SATUNNAISGENEROINTIA, VAAN KARTTA ON PIIRRETTY PELIN MUISTIIN VALMIIKSI) kartassa
* Käyttäjä ohjaa hahmonsa labyrintin läpi nuolinäppäimillä
* Tavoitteena on toteuttaa myös selviytymispelimuoto, jossa pelaaja aloittaa helppoa algoritmia vastaan, ja peli jatkuu aina uudessa kartassa, vaikeammalla algoritmilla, niin kauan kun pelaaja voittaa algoritmin
* Pelin jälkeen käyttäjän on mahdollista joko, tallentaa tuloksensa, tai palata päävalikkoon
* Huipputuloksissa käyttäjä voi tarkastella kaikkien pelaajien huipputuloksia erikseen jokaisesta kartasta, jokaista algoritmia vastaan. Myös selviytymispelimuodolla on erilliset tarkasteltavat huipputulokset.
* Asetuksissa pelaajan on mahdollista mm. vaihtaa käyttäjänimensä (ja ehkä myöhemmin salasanansa), sekä kustomoida pelihahmoaan.

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla
* Salasanakirjautuminen
* Omien huipputulosten nollaaminen
* Erilaiset pelimuodot
* Karttojen satunnaisgenerointi
* Uudet algoritmit
* Laajempi hahmon kustomointi
* Pääkäyttäjä
* Toiminnallisuuksien lisääminen itse peliin, esim algoritmien erikoisvoimat
* Huipputuloksissa voi valita näytetäänkö pelaajan omat, vai kaikkien tulokset
* Erilaiset graafiset paketit, joista käyttäjä voi valita mieluisensa
* Kontrolline kustomointi
