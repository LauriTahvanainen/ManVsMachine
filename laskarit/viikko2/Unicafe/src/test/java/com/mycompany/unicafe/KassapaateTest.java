package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate paate;
    Maksukortti kortti;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void kassapaateKonstruktoriToimiiOikein() {
        assertTrue(paate.kassassaRahaa() == 100000 && paate.maukkaitaLounaitaMyyty() == 0 && paate.edullisiaLounaitaMyyty() == 0);
    }

    @Test
    public void syoEdullisestiKateinenRahamaaraKasvaaJaVaihtorahaOikea() {
        int vaihtoraha = paate.syoEdullisesti(500);
        assertTrue(vaihtoraha == 260 && paate.kassassaRahaa() == 100240);
    }

    @Test
    public void syoMaukkaastiKateinenRahamaaraKasvaaJaVaihtorahaOikea() {
        int vaihtoraha = paate.syoMaukkaasti(500);
        assertTrue(vaihtoraha == 100 && paate.kassassaRahaa() == 100400);
    }

    @Test
    public void syoMaukkaastiMyytyjenMaaraKasvaa() {
        paate.syoMaukkaasti(500);
        paate.syoMaukkaasti(1000);
        assertEquals(paate.maukkaitaLounaitaMyyty(), 2);
    }

    @Test
    public void syoEdullisestiMyytyjenMaaraKasvaa() {
        paate.syoEdullisesti(500);
        paate.syoEdullisesti(1000);
        paate.syoEdullisesti(300);
        paate.syoEdullisesti(10);
        assertEquals(paate.edullisiaLounaitaMyyty(), 3);
    }

    @Test
    public void syoMaukkaastiJosMaksuEiRiitaToimiiOikein() {
        paate.syoMaukkaasti(400);
        int vaihtoraha = paate.syoMaukkaasti(390);
        assertTrue(vaihtoraha == 390 && paate.maukkaitaLounaitaMyyty() == 1 && paate.kassassaRahaa() == 100400);
    }

    @Test
    public void syoEdullisestiJosMaksuEiRiitaToimiiOikein() {
        paate.syoEdullisesti(300);
        int vaihtoraha = paate.syoEdullisesti(200);
        assertTrue(vaihtoraha == 200 && paate.edullisiaLounaitaMyyty() == 1 && paate.kassassaRahaa() == 100240);
    }

    @Test
    public void syoEdullisestiKorttiostoToimiiJosRahaaOn() {
        boolean onnistuiko = paate.syoEdullisesti(kortti);
        assertTrue(onnistuiko && kortti.saldo() == 760);
    }

    @Test
    public void syoMaukkaastiKorttiostoToimiiJosRahaaOn() {
        boolean onnistuiko = paate.syoMaukkaasti(kortti);
        assertTrue(onnistuiko && kortti.saldo() == 600);
    }

    @Test
    public void syoMaukkaastiKunKortillaTarpeeksiRahaaMyytyjenMaaraKasvaa() {
        boolean onnistuiko = paate.syoMaukkaasti(kortti);
        assertTrue(onnistuiko && paate.maukkaitaLounaitaMyyty() == 1);
    }

    @Test
    public void syoEdullisestiKunKortillaTarpeeksiRahaaMyytyjenMaaraKasvaa() {
        boolean onnistuiko = paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        assertTrue(onnistuiko && paate.edullisiaLounaitaMyyty() == 2);
    }

    @Test
    public void syoEdullisestiKunKortillaEiTarpeeksiRahaaMyytyjenMaaraEiKasvaaJaPalautetaanFalse() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        //kortilla rahaa 60
        boolean onnistuiko = paate.syoEdullisesti(kortti);
        assertTrue(!onnistuiko && paate.edullisiaLounaitaMyyty() == 4 && kortti.saldo() == 40);
    }

    @Test
    public void syoMaukkaastiKunKortillaEiTarpeeksiRahaaMyytyjenMaaraEiKasvaaJaPalautetaanFalse() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        //kortilla rahaa 200
        boolean onnistuiko = paate.syoMaukkaasti(kortti);
        assertTrue(!onnistuiko && paate.maukkaitaLounaitaMyyty() == 2 && kortti.saldo() == 200);
    }

    @Test
    public void kassanRahaMaaraEiMuutuKortillaOstaessa() {
        paate.syoMaukkaasti(kortti);
        paate.syoEdullisesti(kortti);

        paate.syoMaukkaasti(400);
        paate.syoEdullisesti(260);
        paate.syoEdullisesti(kortti);
        assertEquals(100640, paate.kassassaRahaa());
    }
    
    @Test
    public void kortilleLadatessaSaldoMuuttuuJaKassaKasvaa() {
        paate.syoMaukkaasti(kortti);
        paate.lataaRahaaKortille(kortti, 600);
        assertTrue(kortti.saldo() == 1200 && paate.kassassaRahaa() == 100600);
    }
    
    @Test
    public void kortilleLadatessaSaldoEiMuutuEikaKassaKasvaJosSummaNegatiivinen() {
        paate.syoMaukkaasti(kortti);
        paate.lataaRahaaKortille(kortti, -600);
        assertTrue(kortti.saldo() == 600 && paate.kassassaRahaa() == 100000);
    }

}
