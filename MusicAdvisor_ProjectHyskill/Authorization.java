package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Authorization {
    private static final String CLIENT_ID = "018cdb6746864ba7a9d5ef9cad6bc93e";
    private static final String CLIENT_SECRET = "490d753f82f04b28a9d99229ee7570a1";
    private static final String REDIRECT_URI = "http://localhost:8080";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";
    private static String authorizationCode;
    private static String accessToken;

    protected void authorize(String authorizationServerUrl) {
        System.out.println("use this link to request the access code:");
        System.out.println(authorizationServerUrl
                + "/authorize"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE);
        getRequest();
    }

    private void getRequest() {
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String request;
                        if (query != null && query.contains("code")) {
                            authorizationCode = query.substring(5);
                            //System.out.println("code received");
                            //System.out.println(authorizationCode);
                            request = "Got the code. Return back to your program.";
                        } else {
                            request = "Authorization code not found. Try again.";
                        }
                        exchange.sendResponseHeaders(200, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                        System.out.println(request);
                    });

            System.out.println("waiting for code...");
            while (authorizationCode == null) {
                Thread.sleep(10);
            }
            server.stop(10);

        } catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }
    }



    protected String getCommand(String address) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(address))
                .GET()
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assert response != null;

            return response.body();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return "ERROR";

    }


    protected String getCategoryId(String name, String URL) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(URL))
                .GET()
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonArray items = JsonParser.parseString(response.body()).getAsJsonObject().get("categories").getAsJsonObject().get("items").getAsJsonArray();
            for (JsonElement item : items) {
                JsonObject category = item.getAsJsonObject();
                String categoryName = category.get("name").getAsString();

                if (categoryName.equals(name)) {
                    return URL + "/" + item.getAsJsonObject().get("id").getAsString() + "/playlists";
                }
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    protected void getAccessToken(String accessPoint) {
        System.out.println("making http request for access_token...");
        //System.out.println("response:");
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accessPoint + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=" + GRANT_TYPE
                                + "&code=" + authorizationCode
                                + "&client_id=" + CLIENT_ID
                                + "&client_secret=" + CLIENT_SECRET
                                + "&redirect_uri=" + REDIRECT_URI))
                .build();


        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }

        assert response != null;
        JsonObject responseJson = JsonParser.parseString(String.valueOf(response.body())).getAsJsonObject();

        try {
            accessToken = responseJson.get("access_token").getAsString();
            if (accessToken == null) {
                throw new NullPointerException();
            }
            System.out.println("---SUCCESS---");
        } catch (Exception e) {
            System.out.println("Failed, please re-authorize.");
        }

    }
}
