package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class View {
    private String response;
    private String command;
    private List<String> responses;
    private int pages;

    public View(String response, String command, int pages) {
        this.response = response;
        this.command = command;
        this.responses = new ArrayList<>();
        this.pages = pages;

    }

    public void printHandler() {
        if (command.contains("categories")) {
            if (command.contains("playlists")) {
                try {
                    printPlaylistCategory(response);
                } catch (Exception e) {
                    System.out.println(response);
                }

            } else {
                printCategories(response);
            }
        } else if (command.contains("featured")) {
            printFeatured(response);
        } else if (command.contains("new")) {
            printNewReleases(response);
        }

    }

    private void printFeatured(String response) {
        JsonParser.parseString(response).getAsJsonObject().get("playlists").getAsJsonObject().get("items").getAsJsonArray()
                .forEach(item -> {
                    JsonObject playlist = item.getAsJsonObject();
                    String url = playlist.get("external_urls").getAsJsonObject().get("spotify").getAsString();
                    String name = playlist.get("name").getAsString();

                    responses.add(name + "\n" + url + "\n");
                });

        printPaginated();
    }

    private void printNewReleases(String response) {
        JsonParser.parseString(response).getAsJsonObject().get("albums").getAsJsonObject().get("items").getAsJsonArray()
                .forEach(item -> {
                    JsonObject album = item.getAsJsonObject();
                    String albumName = album.get("name").getAsString();
                    String url = album.get("external_urls").getAsJsonObject().get("spotify").getAsString();
                    List<String> artists = new ArrayList<>();

                    album.get("artists").getAsJsonArray().forEach(artist -> {
                        artists.add(artist.getAsJsonObject().get("name").getAsString());
                    });


                    StringBuilder artistString = new StringBuilder();
                    artistString.append("[");
                    for (int i = 0; i < artists.size(); i++) {
                        artistString.append(artists.get(i));
                        if (i != artists.size() - 1) {
                            artistString.append(", ");
                        }
                    }
                    artistString.append("]");

                    responses.add(albumName + "\n" + artistString.toString() + "\n" + url + "\n");
                });

        printPaginated();
    }


    private void printCategories(String response) {
        JsonParser.parseString(response).getAsJsonObject().getAsJsonObject().get("categories").getAsJsonObject().get("items").getAsJsonArray()
                .forEach(item -> {
                    JsonObject category = item.getAsJsonObject();
                    String categoryName = category.get("name").getAsString();
                    responses.add(categoryName);
                });
        printPaginated();
    }

    private void printPlaylistCategory(String response) {
        JsonParser.parseString(response).getAsJsonObject().get("playlists").getAsJsonObject().get("items").getAsJsonArray()
                .forEach(item -> {
                    JsonObject playlist = item.getAsJsonObject();
                    String url = playlist.get("external_urls").getAsJsonObject().get("spotify").getAsString();
                    String name = playlist.get("name").getAsString();


                    responses.add(name + "\n" + url);
                });

        printPaginated();
    }

    private List<List<String>> splitList() {
        int partitionSize = pages;
        List<List<String>> partitions = new LinkedList<List<String>>();
        for (int i = 0; i < responses.size(); i += partitionSize) {
            partitions.add(responses.subList(i, Math.min(i + partitionSize, responses.size())));
        }
        return partitions;
    }


    private void printPaginated() {
        Scanner scanner = new Scanner(System.in);
        List<List<String>> partitions = splitList();

        int counter = partitions.size();
        for (int i = 1; i <= counter; i++) {
            if (i - 1 >= partitions.size()) {
                System.out.println("HERE");
                System.out.println("No more pages.");
            } else {
                for (String part : partitions.get(i - 1)) {
                    System.out.println(part);
                }
                System.out.println("---PAGE " + i + " OF " + counter + "---");
            }

            while (true) {
                String command = scanner.nextLine();

                if (command.equals("prev")) {
                    if (i - 1 >= 1) {
                        i -= 2;
                        break;
                    } else {
                        System.out.println("No more pages.");
                    }
                } else if (command.equals("next")) {
                    if (i + 1 > counter) {
                        System.out.println("No more pages.");
                    } else {
                        break;
                    }
                } else {
                    Main main = new Main();
                    main.menuOptions(command);
                    return;
                }
            }
        }
    }
}
