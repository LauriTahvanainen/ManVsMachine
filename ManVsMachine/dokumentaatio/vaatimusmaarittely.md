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
Pelinäkymä on symmetrinen labyrintti, jossa pelaajan sekä algoritmin lähdön ja maalin paikka voi vaihdella. Pelin päätyttyä pelaaja voi siirtyä päävalikkoon, käynnistää pelin uudelleen tai tallentaa huipputuloksena, jos hän sellaisen teki.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista
* Käyttäjän on mahdollista joko kirjautua vanhalla käyttäjänimellä, tai luoda uusi käyttäjänimi jolla kirjautua.
* Käyttäjä voi syöttää käyttäjänimensä lisäyslomakkeeseen uutta käyttäjänimeä luodessa. Nimen on oltava:
	* Vähintään 4 merkkiä, maksimissaan 16 merkkiä pitkä
	* Uniikki
	* Nimi ei saa sisältää välilyöntejä
* Järjestelmä ilmoittaa jos nimi saadaan lisättyä, ja jos nimi ei täytä vaatimuksia.

### Kirjautumisen jälkeen
* Käyttäjä näkee päävalikon, josta hän voi valita:
	* Uuden pelin
	* Huipputulosten tarkastelun
	* Asetukset
* Uuden pelin valitessaan käyttäjä voi valita algoritmeista vastustajan itselleen, minkä jälkeen hänelle avautuu valitulla algoritmilla toimivat kartat. Kartan valittua peli alkaa.
* Itse pelissä on kaksi hahmoa, algoritmin hahmo, ja käyttäjän hahmo. Molempien tavoitteena on päästä ensimmäisenä maaliin.
* Käyttäjä ohjaa hahmonsa labyrintin läpi nuolinäppäimillä
* Pisteitä käyttäjä saa kartan voittaessa tasoon kulutetun ajan mukaan
* Pelin jälkeen käyttäjän on mahdollista joko, tallentaa tuloksensa, tai palata päävalikkoon
* Huipputuloksissa käyttäjä voi tarkastella 20 parhaan pelaajan huipputuloksia erikseen jokaisesta kartasta, jokaista algoritmia vastaan. Myös selviytymispelimuodolla on erilliset tarkasteltavat huipputulokset.
* Asetuksissa pelaajan on mahdollista mm. vaihtaa käyttäjänimensä, sekä kustomoida pelihahmoaan.

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
* Kontrollien kustomointi
* Selviytymispelimuoto, jossa pelaaja aloittaa helppoa algoritmia vastaan, ja peli jatkuu aina uudessa kartassa, vaikeammalla algoritmilla, niin kauan kun pelaaja voittaa algoritmin

