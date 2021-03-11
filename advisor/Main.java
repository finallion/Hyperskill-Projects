package advisor;

import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static boolean isAuthorized = false;
    private static CommandController commandController;


    public static void main(String[] args) {
        commandController = new CommandController(args);

        while (true) {
            String userInput = input.nextLine();

            if (userInput.equals("exit")) {
                System.out.println("---GOODBYE!---");
                return;
            } else if (userInput.equals("auth") || isAuthorized) {
                menuOptions(userInput);
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }

    public static void menuOptions(String userInput) {
        String firstWord = userInput.split(" ")[0];

        switch (firstWord) {
            case "new":
                commandController.getNewReleases();
                break;
            case "featured":
                commandController.getFeatures();
                break;
            case "auth":
                isAuthorized = commandController.getAuthorized();
                break;
            case "categories":
                commandController.getCategories();
                break;
            case "playlists":
                commandController.getPlaylists(userInput.substring(10));
                break;
            case "exit":
                System.exit(0);
            default:
                System.out.println("Invalid option");
                break;
        }
    }

}