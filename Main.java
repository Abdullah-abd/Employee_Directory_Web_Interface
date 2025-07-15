import freemarker.template.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File("templates"));
        cfg.setDefaultEncoding("UTF-8");

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8000"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

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

        // Static CSS
        server.createContext("/css/", exchange -> {
            File file = new File(exchange.getRequestURI().getPath().substring(1));
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.getResponseHeaders().add("Content-Type", "text/css");
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
            } else {
                exchange.sendResponseHeaders(404, 0);
            }
            exchange.close();
        });

        // Static JS
        server.createContext("/js/", exchange -> {
            File file = new File(exchange.getRequestURI().getPath().substring(1));
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.getResponseHeaders().add("Content-Type", "application/javascript");
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
            } else {
                exchange.sendResponseHeaders(404, 0);
            }
            exchange.close();
        });

        System.out.println("✅ Server running on port: " + port);
        server.start();
    }
}
