package server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "graphs")
@NamedQueries({
    @NamedQuery(name = "GraphJSON.findAll", query = "SELECT g FROM GraphJSON g ORDER BY g.id")
})
public class GraphJSON implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "json", nullable = false)
    private String json;

    public GraphJSON() { }

    public GraphJSON(String json) {
        this.json = json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJSON() {
        return json;
    }

    public void setJSON(String json) {
        this.json = json;
    }
}
