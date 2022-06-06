package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Graph;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class GraphRepo {
    private final EntityManager em = Persistence.createEntityManagerFactory("GraphBot").createEntityManager();
    private final ObjectMapper om = new ObjectMapper();

    public void create(Graph graph) {
        em.getTransaction().begin();
        GraphJSON graphJSON;
        try { graphJSON = new GraphJSON(om.writeValueAsString(graph)); }
        catch (final Exception exc) { graphJSON = new GraphJSON(); }
        em.persist(graphJSON);
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
        return ((GraphJSON) em
            .createNamedQuery("GraphJSON.findById")
            .setParameter("id", id)
            .getSingleResult()).toGraph();
    }
}
