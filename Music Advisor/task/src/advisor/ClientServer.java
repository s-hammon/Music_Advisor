package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientServer {

    public void createServer() throws NullPointerException {
        String uri = Config.ACCESS_PATH + "/authorize" +
                "?client_id=" + Config.CLIENT_ID +
                "?redirect_uri=" + Config.REDIRECT_URI +
                "&response_type=code";
        System.out.println("use this link to request the access code:\n" + uri);

        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    exchange -> {
                        String failed = "Not found authorization code. Try again.";
                        String success = "Got the code. Return back to your program.";
                        String query = exchange.getRequestURI().getQuery();

                        if (query == null)
                            query = "";

                        String writeString;
                        if (query.contains("code")) {
                            Config.AUTH_CODE = query.substring(5);
                            writeString = success;
                        } else {
                            writeString = failed;
                        }

                        exchange.sendResponseHeaders(200, writeString.length());
                        exchange.getResponseBody().write(writeString.getBytes());
                        exchange.getResponseBody().close();
                    });

            System.out.println("\nwaiting for code...");
            while (Config.AUTH_CODE.equals("")) {
                Thread.sleep(10);
            }

            System.out.println("code received");
            server.stop(10);

        } catch (IOException | InterruptedException e) {
            System.out.println("Server error.");
        }
    }

    public void doAuth() throws InterruptedException, IOException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(Config.ACCESS_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code" +
                                "&code=" + Config.AUTH_CODE +
                                "&client_id=" + Config.CLIENT_ID +
                                "&client_secret=" + Config.CLIENT_SECRET +
                                "&redirect_uri=" + Config.REDIRECT_URI))
                .build();

        System.out.println("making http request for access token...");
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (httpResponse != null && httpResponse.body().contains("access_token")) {
            parseToken(httpResponse.body());
            System.out.println("Success!");
        }
    }

    public void parseToken(String body) {
        JsonObject jo = JsonParser.parseString(body).getAsJsonObject();
        Config.ACCESS_TOKEN = jo.get("access_token").getAsString();
    }
}
