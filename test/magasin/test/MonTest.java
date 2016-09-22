/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasin.test;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import magasin.entity.Categorie;
import magasin.entity.Client;
import magasin.entity.Commande;
import magasin.entity.Produit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author tom
 */
public class MonTest {

    @Before
    public void avant() {
        // Vide toutes les tables
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM Produit p");
        query.executeUpdate();
        em.createQuery("DELETE FROM Categorie p").executeUpdate();

        //supprimer les commandes avant de supprimer les clients
        em.createQuery("DELETE FROM Commande c").executeUpdate();
        em.createQuery("DELETE FROM Client c").executeUpdate();
        em.createQuery("DELETE FROM Produit p").executeUpdate();
        em.createQuery("DELETE FROM Categorie c").executeUpdate();

        /*Query query2 = em.createQuery("DELETE FROM Commande c");
        query2.executeUpdate();
        em.createQuery("DELETE FROM Commande c").executeUpdate();
        
        //supprimer les commandes avant de supprimer les clients
        Query query2 = em.createQuery("DELETE FROM Commande c");
        query2.executeUpdate();
        em.createQuery("DELETE FROM Commande c").executeUpdate();
         */
        // Ajoutes des données en spécifiant les IDs que l'on va récup ds les tests unitaires
        // Persister en bases certaines données
        Categorie c1 = new Categorie();
        c1.setId(1L);
        c1.setNom("Basket");
        em.persist(c1);

        Categorie c2 = new Categorie();
        c2.setId(2L);
        c2.setNom("Lunettes solaires");
        em.persist(c2);

        Produit rayBan = new Produit();
        rayBan.setId(1L);
        rayBan.setCategorie(c2);//a.setB(b)
        c2.getProduits().add(rayBan);// b.getAS().add(a) initialise des 2 cotes
        em.persist(rayBan);

        //exo
        Client ri = new Client();
        ri.setId(1L);
        ri.setLogin("riri");
        ri.setEmail("riri@disney.com");
        ri.setMdp("ririmdp");
        em.persist(ri);

        Client fi = new Client();
        fi.setId(2L);
        fi.setLogin("fifi");
        em.persist(fi);

        Client lou = new Client();
        lou.setId(3L);
        lou.setLogin("loulou");
        em.persist(lou);

        Commande ricom = new Commande();
        ricom.setId(1L);
        ricom.setClient(ri);
        ri.getCommandes().add(ricom);
        ricom.setSomme(1000);
        em.persist(ricom);

        //persister les commandes
        Commande loucom = new Commande();
        loucom.setId(2L);
        loucom.setClient(lou);
        lou.getCommandes().add(loucom);
        loucom.setSomme(5);
        em.persist(loucom);

        Commande loucom2 = new Commande();
        loucom2.setId(3L);
        loucom2.setClient(lou);
        lou.getCommandes().add(loucom2);
        loucom2.setSomme(2);
        em.persist(loucom2);

        em.getTransaction().commit();
    }

    @Test // verifier que loulou a 2 commandes
    public void verifNBCmdlou2OK() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Client c = em.find(Client.class, 3L);
        if (c.getCommandes().size() != 2) {
            Assert.fail("loulou n'a pas 2 commandes");
        }
    }

    /*@Test 
    public void verifNBCmdlou2(){
        //verif que la commande de loulou 2 euros
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Commande loucom = em.find(Commande.class, 2);
        if( cat.getNom().equals("loulou")==false ){
            Assert.fail("erreur sur commande loulou");
    }
     */
    @Test //verif que la commande 3 soit passée par loulou
    public void verifcmd3estloulouOK() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Commande co = em.find(Commande.class, 3L);
        Assert.assertEquals("loulou", co.getClient().getLogin());
        /*equivaut a ci-dessous
        if (co.getClient().getLogin().equals("loulou") == false) {
            Assert.fail("commande numéro 3 non passée par loulou");
        }
        */
        
    }

    @Test //verifier que la commande numero 2 soit passée par riri
    public void verifcmd2paririKO() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Commande co2 = em.find(Commande.class, 2L);
        Assert.assertNotEquals("riri", co2.getClient().getLogin());
        /*equivaut a ci dessous
        if (co2.getClient().getLogin().equals("riri") == false) {
            Assert.fail("commande numéro 2 non passée par riri"); //fait un fail en rouge lors du test 
        }*/
    }

    @Test
    public void verifieQueCatId1EstBasket() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Categorie cat = em.find(Categorie.class, 1L);

        if (cat.getNom().equals("Basket") == false) { //new String("test").equals("test") Objects.equals("test", new String("test"))
            Assert.fail("CA MARCHE PAS MON GARS!");
        }
    }

    @Test
    public void testListeProdCategorie() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Categorie cat = em.find(Categorie.class, 1L);
        for (Produit p : cat.getProduits()) {

            System.out.println(p);
        }
    }

    /*@Test
    public void testCreateDB() {

    }
     */
}
