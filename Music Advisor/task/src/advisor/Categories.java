package advisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

class Categories extends GetRequest {
    static List<Data> data = new ArrayList<>();

    public static void request() {

        String path = Config.RESOURCE_PATH + "/v1/browse/categories";

        HttpRequest request = getRequest(path);

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject categories = jo.getAsJsonObject("categories");

            for (JsonElement item : categories.getAsJsonArray("items")) {
                Data elem = new Data();
                elem.setCategory(item.getAsJsonObject().get("name").getAsString());
                data.add(elem);
            }

        } catch (InterruptedException | IOException e) { System.out.println("Error response"); }
    }
}
