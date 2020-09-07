package advisor;

import java.util.Scanner;

public class Application {
    boolean runApp = false;

    void run() throws Exception {
        runApp = true;
        commandMenu();
    }

    void commandMenu() throws Exception {
        Scanner sc = new Scanner(System.in);

        while (!sc.hasNext("auth")) {
            if (sc.nextLine().equals("exit")) {
                System.out.println("Goodbye!");
                runApp = false;
                break;
            }

            System.out.println("Please, provide access for application.");
        }

        while (runApp) {
            String input = sc.nextLine().trim();
            String command = input.contains(" ") ? input.substring(0, input.indexOf(" ")) : input;

            PageInfo currentMenu = PageInfo.getInstance();

            switch (command) {
                case "new":
                    NewReleases.request();
                    currentMenu.setData(NewReleases.data);
                    currentMenu.showPage();
                    break;
                case "featured":
                    Featured.request();
                    currentMenu.setData(Featured.data);
                    currentMenu.showPage();
                    break;
                case "categories":
                    Categories.request();
                    currentMenu.setData(Categories.data);
                    currentMenu.showPage();
                    break;
                case "playlists":
                    String name = input.substring(input.indexOf(" ") + 1);
                    Playlists.request(name);
                    currentMenu.setData(Playlists.data);
                    currentMenu.showPage();
                    break;
                case "prev":
                    currentMenu.previous();
                    break;
                case "next":
                    currentMenu.next();
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    runApp = false;
                    break;
                case "auth":
                    ClientServer server = new ClientServer();
                    server.createServer();
                    server.doAuth();
                    break;
                default:
                    System.out.println("Command not recognized.");
            }
        }
    }
}
