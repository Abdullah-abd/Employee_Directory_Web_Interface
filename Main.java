import freemarker.template.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Configure Freemarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File("templates"));
        cfg.setDefaultEncoding("UTF-8");

        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Serve index.ftl at root
        server.createContext("/", exchange -> {
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("employees", SampleData.getEmployees());

            try {
                Template template = cfg.getTemplate("index.ftl");
                StringWriter writer = new StringWriter();
                template.process(dataModel, writer);

                String response = writer.toString();
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (TemplateException e) {
                e.printStackTrace();
                String response = "Template processing error: " + e.getMessage();
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // ✅ Serve form.ftl at /form.html
        server.createContext("/form.html", exchange -> {
            URI uri = exchange.getRequestURI();
            Map<String, String> queryParams = parseQuery(uri.getQuery());

            Map<String, Object> dataModel = new HashMap<>();

            if (queryParams.containsKey("id")) {
                int id = Integer.parseInt(queryParams.get("id"));
                Employee emp = SampleData.getEmployeeById(id); // Make sure you have this!
                dataModel.put("employee", emp);
            }

            try {
                Template template = cfg.getTemplate("form.ftl");
                StringWriter writer = new StringWriter();
                template.process(dataModel, writer);

                String response = writer.toString();
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (TemplateException e) {
                e.printStackTrace();
                String response = "Template processing error: " + e.getMessage();
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // Serve CSS
        server.createContext("/css/", exchange -> {
            String path = "css" + exchange.getRequestURI().getPath().replace("/css", "");
            File file = new File(path);
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.getResponseHeaders().add("Content-Type", "text/css");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // Serve JS
        server.createContext("/js/", exchange -> {
            String path = "js" + exchange.getRequestURI().getPath().replace("/js", "");
            File file = new File(path);
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.getResponseHeaders().add("Content-Type", "application/javascript");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        System.out.println("✅ Server running at http://localhost:8000");
        server.start();
    }

    // ✅ Helper to parse query params
    private static Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null || query.isEmpty()) return map;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            if (parts.length > 1) {
                map.put(parts[0], parts[1]);
            }
        }
        return map;
    }
}
