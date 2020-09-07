package advisor;

import java.util.List;

public class PageInfo {
    private static final PageInfo INSTANCE = new PageInfo();

    private List<Data> data;
    private int current = 1;
    private int last;

    private PageInfo() {

    }

    public static PageInfo getInstance() {
        return INSTANCE;
    }

    public void setData(List<Data> data) {
        this.current = 1;
        this.data = data;
        this.last = data.size() % Config.DISPLAY_ITEMS == 0 ?
                data.size() / Config.DISPLAY_ITEMS :
                data.size() / Config.DISPLAY_ITEMS + 1;
    }

    public void previous() {
        if (current > 1) {
            current--;
            showPage();
        } else noMorePages();
    }

    public void next() {
        if (current < last) {
            current++;
            showPage();
        } else noMorePages();
    }

    public void showPage() {
        int first = Config.DISPLAY_ITEMS * (current - 1);
        int nextPage = Config.DISPLAY_ITEMS * current;

        for (int i = first; i < nextPage; i++) {
            System.out.println(data.get(i));
        }

        System.out.printf("---PAGE %d OF %d---", current, last);
    }

    public void noMorePages() {
        System.out.println("No more pages.");
    }
}
