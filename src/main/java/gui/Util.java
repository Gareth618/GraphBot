package gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Graph;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import server.GraphJSON;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class Util {
    public static void exportPNG(Component component, String path) throws IOException {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        ImageIO.write(image, "png", new File(path));
    }

    public static void exportSVG(DrawingPanel drawingPanel, String path) throws Exception {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        drawingPanel.paintGraph(svgGenerator);
        Writer out = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8);
        svgGenerator.stream(out, true);
    }

    public static void saveGraph(Graph graph, String uriGraph) throws JsonProcessingException {
        String graphJSON = new ObjectMapper().writeValueAsString(graph);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.postForObject(uriGraph, new HttpEntity<>(graphJSON, headers), String.class);
    }
}
