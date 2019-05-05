# Vaatimusmäärittely

## Sovelluksen tarkoitus
Pelin tarkoituksena on ohjata oma hahmo labyrintin läpi ennen vastustajaa, algoritmia. Algoritmi-vastustaja on valittavissa eri algoritmeista. Peli mahdollistaa pelaajille huipputulosten ylös kirjaamisen, ja tarkastelun.

## Käyttäjät
Sovelluksen käyttäjärooleja on vain yksi, *normaali käyttäjä*. Ohjelmaan on toteutettu salasanakirjautuminen. Aloitusnäkymässä käyttäjä voi kirjautua olemassa olevalla käyttäjänimellä ja salasanalla, tai siirtyä luomaan uuden käyttäjän.

## Käyttöliittymäluonnos
Sovellus aukeaa kirjautumisnäkymään, jossa käyttäjä voi kirjautua vanhalla käyttäjänimellä, tai siirtyä luomaan uuden käyttäjän. Tämän jälkeen avautuu päävalikko, josta pelaaja voi siirtyä:
* Uuteen peliin
* Huipputulosten tarkasteluun
* Asetuksiin
* Kirjautua ulos
* Lopettaa pelin
Pelinäkymä on symmetrinen labyrintti, jossa pelaajan sekä algoritmin lähdön ja maalin paikka voi vaihdella. Pelin päätyttyä pelaaja voi siirtyä päävalikkoon, käynnistää pelin uudelleen tai tallentaa huipputuloksena, jos hän sellaisen teki.

Jokaisessa tilassa on sille tilalle ominaiset musiikit.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista
* Käyttäjän on mahdollista joko kirjautua vanhalla käyttäjänimellä, tai luoda uusi käyttäjänimi, jolla kirjautua.
* Käyttäjä voi syöttää käyttäjänimensä ja salasanansa lisäyslomakkeeseen uutta käyttäjänimeä luodessa. Nimen on oltava:
	* Vähintään 4 merkkiä, maksimissaan 16 merkkiä pitkä
	* Uniikki
	* Nimi ei saa sisältää välilyöntejä
* Salasanan on: 
	* Oltava vähintään 7 ja enintään 16 merkkiä pitkä
	* Sisällettävä vain kirjaimia ja numeroita.
	* Sisällettävä vähintään yksi:
		* Iso kirjain
		* Pieni kirjain
		* Numero
* Järjestelmä ilmoittaa, jos nimi saadaan lisättyä, ja jos nimi tai salasana ei täytä vaatimuksia.

### Kirjautumisen jälkeen
* Käyttäjä näkee päävalikon, josta hän voi valita:
	* Uuden pelin
	* Huipputulosten tarkastelun
	* Asetukset
	* Uloskirjautumisen
	* Pelin sulkemisen
* Uuden pelin valitessaan käyttäjä voi valita algoritmeista vastustajan itselleen, minkä jälkeen hänelle avautuu valitulla algoritmilla toimivat kartat. Kartan valittua, ja algoritmin skannattua kartan, peli alkaa.
* Itse pelissä on kaksi hahmoa, algoritmin hahmo, ja käyttäjän hahmo. Molempien tavoitteena on päästä ensimmäisenä maaliin.
* Käyttäjä ohjaa hahmonsa labyrintin läpi nuolinäppäimillä
* Pisteitä käyttäjä saa kartan voittaessa tasoon kulutetun ajan, sekä kuljetun matkan mukaan.
* Pelin saa pauselle _esc_ näppäimestä.
* Pelin jälkeen käyttäjän on mahdollista joko, tallentaa tuloksensa, aloittaa peli alusta, tai palata päävalikkoon
* Huipputuloksissa käyttäjä voi tarkastella 20 parhaan pelaajan huipputuloksia erikseen jokaisesta algoritmista, jokaisessa kartassa. Pelaajan oma tulos näytetään alleviivattuna.
* Asetuksissa pelaajan on mahdollista mm. vaihtaa käyttäjänimensä ja salasanansa, hahmonsa tekstuuri, sekä valita portaalinsa väri.

## Jatkokehitysideoita

* Omien huipputulosten nollaaminen
* Erilaiset pelimuodot
* Karttojen satunnaisgenerointi
* Uudet algoritmit
* Laajempi hahmon kustomointi
* Pääkäyttäjä
* Toiminnallisuuksien lisääminen itse peliin, esimerkiksi algoritmien erikoisvoimat
* Huipputuloksissa voi valita näytetäänkö pelaajan omat, vai kaikkien tulokset
* Erilaiset graafiset paketit, joista käyttäjä voi valita mieluisensa
* Kontrollien kustomointi
* Selviytymispelimuoto, jossa pelaaja aloittaa helppoa algoritmia vastaan, ja peli jatkuu aina uudessa kartassa, vaikeammalla algoritmilla, niin kauan kun pelaaja voittaa algoritmin

