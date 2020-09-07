package advisor;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 1) {
            for (int i = 0; i < args.length; i++)
                switch (args[i]) {
                    case "-access":
                        Config.ACCESS_PATH = args[i + 1];
                        break;
                    case "-resource":
                        Config.RESOURCE_PATH = args[i + 1];
                        break;
                    case "-page":
                        Config.DISPLAY_ITEMS = Integer.parseInt(args[i + 1]);
                        break;
                }
        }

        Application app = new Application();
        app.run();
    }
}
