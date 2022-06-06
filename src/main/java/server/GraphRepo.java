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
            .map(json -> {
                Graph graph;
                try {
                    final GraphJSON graphJSON = (GraphJSON) json;
                    graph = om.readValue(graphJSON.getJSON(), Graph.class);
                    graph.setId(graphJSON.getId());
                }
                catch (final Exception exc) {
                    graph = new Graph();
                }
                return graph;
            })
            .toList();
    }
}
