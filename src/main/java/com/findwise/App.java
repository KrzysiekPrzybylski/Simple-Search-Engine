package com.findwise;

import com.findwise.exceptions.ExitException;

import java.util.Scanner;
public class App {

    SearchEngineImp searchEngine = new SearchEngineImp();
    Scanner searchEngineScanner = new Scanner(System.in);


    boolean menuLoop = true;
    public String menuChoice;


    public static void main(String[] args) {

        App app = new App();
        app.menu();

    }

    public void indexCommand() {


        String docId, document;
        System.out.println("Enter document ID: ");
        docId = searchEngineScanner.nextLine();
        System.out.println("Enter document content");
        System.out.println();

        document = searchEngineScanner.nextLine();

        searchEngine.indexDocument(docId, document);
        menu();
    }

    public void searchCommand() {
        String term;
        System.out.println("Enter search term: ");
        term = searchEngineScanner.nextLine().toLowerCase();
        searchEngine.search(term).forEach(word -> {
            System.out.println("id: " + word.getId() + " td-idf score: " + word.getScore());
        });
        System.out.println();
        menu();
    }
    public void exit() {
        menuLoop = false;
    }

    private void menu() {
        int input;
        System.out.println("1. Index document");
        System.out.println("2. Search in documents");
        System.out.println("3. Exit");

        try {
            String menuChoice = searchEngineScanner.nextLine();

            input = Integer.parseInt(menuChoice);

            while (menuLoop) {

                switch (input) {
                    case 1:
                        indexCommand();
                        break;
                    case 2:
                        searchCommand();
                        break;
                    case 3:
                        throw  new ExitException("exiting application");
                    default: menu();
                        break;
                }
            }
        } catch (NumberFormatException e) {
            if(menuChoice.equals("")) {
                System.out.println("empty input");
            } else {
                System.out.println("you've entered non integer number");
            }
            e.printStackTrace();
        } catch (ExitException e) {
            menuLoop = false;
            System.out.println(e.getMessage());
        }
    }


}
