package spring;

import db.Converter;
import model.Graph;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class SpringClient {
    public List<Graph> getGraphs() {
        final RestTemplate template = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<List<Graph>> response = template.exchange(
            "http://localhost:8000/api/graphs",
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<>() { }
        );
        return response.getBody();
    }

    public void saveGraph(Graph graph) {
        final RestTemplate template = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String json = Converter.toJSON(graph);
        template.getMessageConverters().add(new StringHttpMessageConverter());
        template.postForObject(
            "http://localhost:8000/api/graphs",
            new HttpEntity<>(json, headers),
            String.class
        );
    }

    public List<Graph> runAlgorithm(String algorithm, int graphId, int arg) {
        final RestTemplate template = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<List<Graph>> response = template.exchange(
            "http://localhost:8000/api/algo/" + algorithm + "/" + graphId + (arg == -1 ? "" : "?source=" + arg),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<>() { }
        );
        return response.getBody();
    }
}
