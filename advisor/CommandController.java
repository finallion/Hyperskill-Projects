package advisor;

public class CommandController {
    private Authorization auth = new Authorization();
    private String apiServerPath = "https://api.spotify.com/";
    private String authorizationServerUrl = "https://accounts.spotify.com";
    private int pages = 5;
    private final String[] args;
    private String response;


    public CommandController(String[] args) {
        this.args = args;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-access")) {
                authorizationServerUrl = args[i + 1];
            }
            if (args[i].equals("-resource")) {
                apiServerPath = args[i + 1] + "/";
            }
            if (args[i].equals("-page")) {
                pages = Integer.parseInt(args[i + 1]);
            }
        }
    }


    public boolean getAuthorized() {
        auth.authorize(authorizationServerUrl);
        auth.getAccessToken(authorizationServerUrl);
        return true;
    }

    public void getNewReleases() {
        String address = apiServerPath + "v1/browse/new-releases";
        response = auth.getCommand(address);
        print(response, address);
    }

    public void getFeatures() {
        String address = apiServerPath + "v1/browse/featured-playlists";
        response = auth.getCommand(address);
        print(response, address);
    }

    public void getCategories() {
        String address = apiServerPath + "v1/browse/categories";
        response = auth.getCommand(address);
        print(response, address);
    }

    public void getPlaylists(String name) {
        String playlistURL = auth.getCategoryId(name, apiServerPath + "v1/browse/categories");

        if (playlistURL != null) {
            response = auth.getCommand(playlistURL);
            print(response, playlistURL);
        } else {
            System.out.println("Unknown category name.");
        }
    }

    private void print(String response, String address) {
        View view = new View(response, address, pages);
        view.printHandler();
    }
}
