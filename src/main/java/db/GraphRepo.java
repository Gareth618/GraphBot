package db;

import model.Graph;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class GraphRepo {
    private final EntityManager em = Persistence.createEntityManagerFactory("GraphBot").createEntityManager();

    public void create(Graph graph) {
        em.getTransaction().begin();
        em.persist(new GraphJSON(Converter.toJSON(graph)));
        em.getTransaction().commit();
    }

    public List<Graph> findAll() {
        return em
            .createNamedQuery("GraphJSON.findAll")
            .getResultList()
            .stream()
            .map(graphJSON -> ((GraphJSON) graphJSON).toGraph())
            .toList();
    }

    public Graph findById(int id) {
        em.getTransaction().begin();
        final GraphJSON graphJSON = (GraphJSON) em
            .createNamedQuery("GraphJSON.findById")
            .setParameter("id", id)
            .getSingleResult();
        em.getTransaction().commit();
        return graphJSON.toGraph();
    }
}
