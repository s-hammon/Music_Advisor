package advisor;

public class Data {
    private String album;
    private String artists;
    private String category;
    private String link;

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getArtists() {
        return artists;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (album != null) sb.append(album).append("\n");

        if (artists != null) sb.append(artists).append("\n");

        if (link != null) sb.append(link).append("\n");

        if (category != null) sb.append(category).append("\n");

        return sb.toString().replaceAll("\"", "");
    }
}
