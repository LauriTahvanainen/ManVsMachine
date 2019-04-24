# Arkkitehtuurikuvaus
## Rakenne
Ohjelmassa ei ole selkeää kerrosrakennetta, sillä kyseessä on peli. Rekenteessa ollaankin pyritty erilaisia tiloja sisältävään rakenteeseen. Käyttäjän syötteiden mukaan ohjelma siirtyy eri tiloihin. Keskeisenä rakenteessa on luokka StateManager, jonka tehtävänä on hallinnoida tiloja. Statemanagerin tehtävänä on mm. tilan vaihtaminen, sekä pelin kannalta keskeinen tilan päivittäminen. Jokaisen tilan on myös tunnettava StateManager, jotta ne voivat pyytää siltä palveluja.

Vaikka riippuvuuksia rakenteessa on vähän joka suuntaan, on silti pyritty siihen, että eri tilat olisivat mahdollisimman helposti irrotettavissa, sekä uusien tilojen lisääminen olisi helppoa.
Ohjelman pakkausrakenne on seuraava:
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/pakkausrakenne.png)

mvsm.ui sisältää ohjelman alustavan Main luokan, sekä kaikki tilaluokat. Alustettaessa luodaan uusi Statemanager, tarvittavat tilat, dao-oliot ja tapahtumankuuntelijat. 

mvsm.statemanagement sisältää tilan määrittelevän luokan State.java, joka jokaisen tilan pitää toteuttaa, sekä StateManager luokan, joka vastaa tilojen hallinnasta. 

mvsm.eventhandling pakkauksessa on tapahtumien kuuntelemisesta, kuten näppäimen painalluksista, vastaavat luokat. 

mvsm.dao paketin luokat vastaavat ohjelman tietojen pysyväistallennuksesta. 

mvsm.game pakkaus sisältää pelitilan tarvitsemat luokat. Luokat vastaavat mm. pelin fysiikoista, kartan piirtämisestä, sekä pisteiden laskusta. 

mvsm.algorithm pakkaus taas vastaa pelin tarvitsemien algoritmien toteutuksesta. mvsm.sprite sisältää pelaajan ja algoritmin hahmojen tarvitseman koodin, ja se on riippuvainen pakkauksesta mvsm.algorithm.

#### Sovellusta pääpiirteittäin kuvaava luokkakaavio

![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/luokkakaavio.png)

### Käyttöliittymä
Sovelluksen käyttöliittymä sisältää pääpiirteittäin 6 näkymää, minkä lisäksi joissain näkymissä on "alinäkymiä". Näkymät ovat:
 - Kirjautumisnäkymä (LoginState)
 - Päävalikko (MenuState)
 - Pelinvalintanäkymä (GameSelectionState)
 - Pelitila (PlayingState)
 - Asetusnäkymä (SettingsState)
 - Huipputulosten tarkastelu (HighscoreState)
 
Yksi tila vastaa siis yhdestä näkymästä. Tilat, ja näkymät on toteutettu niin, että sovelluksella on vain yksi Scene olio, jonka root-nodea GameStateManager vaihtaa. Jokainen tila omaa siis root paneelin, joka tarvittaessa vaihdetaan Scene olion root-nodeksi. Vaihtelu jokaisen tilan näkymässä toteutetaan manipuloimalla tilan root paneelin lapsia. 

Käyttöliittymä on pyritty erottamaan sovelluslogiikasta niin hyvin kuin pelin rakenne sallii. Eri tilat kutsuvat sopivilla parametreilla omaaviensa olioiden, kuten dao-olioiden metodeja. Esimerkiksi HighscoreState pyytää DatabaseScoreDao oliota tuomaan sille järjestetyn listan tietyn kartan huipputuloksia, minkä jälkeen tulokset piirretään käyttäjälle taulukkona metodin formScoreList avulla.

## Sovelluslogiikka
Sovelluslogiikan esitteleminen on helpompi jakaa osiin. 

Keskeisin sovelluslogiikan osa on itse pelitilan pyörittäminen. Pelin toiminnan mahdollistamisessa on pääosin mukana luokat PlayingState, Statemanager, MapRenderer, GamePhysics, sekä KeyEventHandler.
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/pelilogiikka.png)
Ylläolevassa kuvassa näytetään näiden keskeisten luokkien suhteet toisiinsa. Luokkien muuttujat ja metodit ovat pelkistettyjä, ja niistä on kuvassa vain oleellisimmat.

PlayingState vastaa sen paneelin hallinnasta, jossa peli näytetään. StateManager vastaa tässä tapauksessa peliloopin päivittämisestä (eli toistuvasta handle kutsusta). GamePhysics vastaa pelin tilan päivittämisestä, KeyEventListener tallentaa näppäimien painallukse, ja MapRenderer piirtää kartan.

Peli "pyörii" seuraavasti: Kun uusi peli aloitetaan, rakentaa PlayingState pelinäkymän restore(a, mapName) metodissaan. Tässä metodissa luodaan Spritet, alustetaan fysiikat, sekä annetaan mapName parametrina MapRenderin metodille formArrayMap(mapName). MapRenderer lukee kartan resurssikansiosta. Kun tarvittavat MapRendererin metodit on kutsuttu, on PlayingStatella valmis GridPane paneeli, jonka se voi näyttää pelaajalle. Nyt pelaaja näkee pelin. restore-metodin lopuksi PlayingState kutsuu StateManagerin startLoop() metodia, joka aloittaa pelin päivittämisen.

Pelin ollessa käynnissä, StateManager kutsuu 60 kertaa sekunnissa metodia handle, joka kutsuu StateManagerin nykyisen tilan update metodia, joka PlayingState:n tapauksessa kutsuu GamePhysics:n metodia updateGameworld(). 

Update gameGameWorld() hoitaa törmäysten tunnistuksen ja käsittelyn, sekä Spritejen liikuttamisen. Pelaajan liikuttamiseen GamePhysics käyttää KeyEventListenerin keyCode listaa. Lopulta GamePhysics palauttaa PlayingStatelle arvon, joka kertoo PlayingStatelle tapahtuiko päivityksen aikana mitään. Jos esimerkiksi kone pääsee maaliin, niin GamePhysics palauttaa arvon -2, ja PlayingState saa viestin koneen voitosta, jolloin se kutsuu update metodissaan metodiaan machineWin(), jossa pysäytetään peli(StateManager.stopLoop()), ja ilmoitetaan pelaajalle häviöstä.

## Päätoiminnallisuudet
### Sisäänkirjautuminen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/Onnistunut%20sis%C3%A4%C3%A4nkirjautuminen.png)
ActionEventHandler käsittelee kaikki nappitapahtumat, ja kun käyttäjä on kirjoittanut käyttäjänimensä tekstikenttään ja painaa kirjautumisnappia, kutsuu ActionEventHandler handle metodiaan, jossa se kysyy omaavaltaan StateManagerilta, että mikä tila on tällä hetkellä aktiivisena. StateManager palauttaa LoginStaten, joten handle metodissa kutsutaan nimenomaan vain LoginState:n handleAction metodia. handleAction metodissa tarkistetaan, mikä paneeli loginStatessa on tällä hetkellä näkyvillä, ja koska aktiivisena on kirjautumispaneeli, kutsutaan LoginStaten metodia handleSignInView, mikä saa parametrinaan tiedot painalluksesta, errortekstikentän sekä tekstikentän johon käyttäjä on syöttänyt nimensä. Tässä metodissa kutsutaan UserDaon metodia read, joka hakee ja palauttaa tietokannasta syötettyä käyttäjänimeä vastaavan käyttäjäolion. Sitten nykyisen paneelin tila nollataan, ja StateManagerin avulla vaihdetaan nykyiseksi tilaksi MenuState ja Scenen root-paneeliksi MenuStaten alkupaneeli. StateManagerin avulla LoginStatesta myös lopuksi kutsutaan MenuStaten update metodia, joka päivittää oikean tervehdystekstin menu näkymään. Loppujen lopuksi käyttäjä on siis siirtynyt menu-tilaan.
