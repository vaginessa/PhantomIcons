package highridas.phantom.icons.items;

/**
 * Created by Patrick Ridge on 2017-07-24.
 */
public class WallpaperItem {

    private String name, author, url, thumb;

    public WallpaperItem(String name, String author, String url, String thumb) {
        this.name = name;
        this.author = author;
        this.url = url;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getThumb() {
        return thumb;
    }
}
