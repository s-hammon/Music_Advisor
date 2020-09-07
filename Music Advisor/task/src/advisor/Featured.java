package advisor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

class Featured extends GetRequest {
    static List<Data> data = new ArrayList<>();

    public static void request() {
        String path = Config.RESOURCE_PATH + "/v1/browse/featured-playlists";
        HttpRequest request = getRequest(path);

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            data = addPlaylists(response);
        } catch (InterruptedException | IOException e) { System.out.println("Error response"); }
    }
}
