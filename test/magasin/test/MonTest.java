/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasin.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import magasin.entity.Categorie;
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
        rayBan.setCategorie(c2);
        em.persist(rayBan);

        em.getTransaction().commit();
    }

    @Test
    public void verifieQueCatId1EstBasket() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Categorie cat = em.find(Categorie.class, 1L);
        
        if( cat.getNom().equals("Basket123")==false ){
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

    @Test
    public void testCreateDB() {

    }

}
