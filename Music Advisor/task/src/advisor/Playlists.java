package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

class Playlists extends GetRequest {
    static List<Data> data = new ArrayList<>();

    public static JsonObject getCategory() throws IOException, InterruptedException {
        HttpRequest request = getRequest(Config.RESOURCE_PATH + "/v1/browse/categories");
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    public static void request(String name) throws Exception {
        String categoryID = "";
        var json = getCategory();

        for (int i = 0; i < json.size(); i++) {
            var cat = json.get("categories").getAsJsonObject().get("items").getAsJsonArray();

            for (var c : cat) {
                var category = c.getAsJsonObject();
                if (name.equals(category.get("name").getAsString())) {
                    categoryID = category.get("id").getAsString();
                    break;
                }
            }
        }

        String path = Config.RESOURCE_PATH + "/v1/browse/categories/" + categoryID + "/playlists";
        HttpRequest request = getRequest(path);

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            if (jo.toString().contains("404")) {
                System.out.println(jo.toString());
            } else {
                data = addPlaylists(response);
            }

        } catch (InterruptedException | IOException e) { System.out.println("Error response"); }
    }
}
