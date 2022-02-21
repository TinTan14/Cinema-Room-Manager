package com.company;

import java.util.Scanner;

public class Main {

    //Shows menu options for the Cinema Room Manager
    public static int menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        return scanner.nextInt();
    }

    //Prints the layout of the Cinema
    // S - seat is available
    // B - seat is already purchased
    public static void printCinema(String[][] seatArrangement) {
        //Print header "Cinema:" and the seats number
        System.out.println("\nCinema:");
        for (int i = 1; i <= seatArrangement[0].length ; i++) {
            if (i == 1){
                System.out.print(" ");
            }
            System.out.print(" " + i);
        }
        System.out.println();

        //Print the row numbers and the availability of seats
        for(int i=0; i < seatArrangement.length; i++) {
            for(int j=0; j < seatArrangement[i].length; j++) {
                if (j == 0) {
                    System.out.print(i + 1 + " ");
                }
                System.out.print(seatArrangement[i][j] + " ");
            }
            System.out.println();
        }

    }

    //Calculates ticket price depending on whether the room is small or large
    //Small room - less than 60 seats, ticket price is $10
    //Large room - more than 60 seats,
    //ticket price is $10 for the front half
    //ticket price is $8 for the rest
    public static int ticketPrice (String[][] seatArrangement, int row) {
        final int sixtySeats = 60;
        final int ten$ = 10;
        final int eight$ = 8;

        int totalSeats = seatArrangement.length * seatArrangement[0].length;
        boolean isSmallRoom = totalSeats < sixtySeats;

        if (isSmallRoom) {
            return ten$;
        } else {
            return row <= (seatArrangement.length/2) ? ten$ : eight$;
        }
    }


    public static boolean availableSeat (String[][] seatArrangement, int row, int seat) {
        //Check if chosen seat is within the bounds and if it's available
        boolean isValid = row > 0 && row <= seatArrangement.length &&
                          seat > 0 && seat <= seatArrangement[0].length;
        //If valid and available, mark the seat with "B" and print the ticket price
        if (isValid) {
            boolean isAvailable = seatArrangement[row - 1][seat - 1] == "S";
            if (isAvailable) {
                seatArrangement[row - 1][seat - 1] = "B";
                return true;
            } else {
                System.out.println("That ticket has already been purchased!");
                return false;
            }
        } else {
            System.out.println("Wrong input!");
            return false;
        }
    }

    public static float percentage (String[][] seatArrangement, int soldTickets) {
        int totalSeats = seatArrangement.length * seatArrangement[0].length;
        return (float)soldTickets / totalSeats * 100;
    }

    public static int totalIncome (String[][] seatArrangement) {
        final int sixtySeats = 60;
        final int ten$ = 10;
        final int eight$ = 8;

        int totalSeats = seatArrangement.length * seatArrangement[0].length;
        boolean isSmallRoom = totalSeats < sixtySeats;

        if (isSmallRoom) {
            return totalSeats * ten$;
        } else {
            int frontSeats = (seatArrangement.length/2) * seatArrangement[0].length;
            int backSeats = totalSeats - frontSeats;
            return (frontSeats * ten$) + (backSeats * eight$);
        }
    }

    public static void statistics (String[][] seatArrangement, int soldTickets, int currentIncome) {
        System.out.printf("%nNumber of purchased tickets: %d", soldTickets);
        System.out.printf("%nPercentage: %.2f%%", percentage(seatArrangement, soldTickets));
        System.out.printf("%nCurrent income: $%d", currentIncome);
        System.out.printf("%nTotal income: $%d%n", totalIncome(seatArrangement));
    }

    //Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Ask user for the number of rows and seats per row
        System.out.println("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int seatsPerRow = scanner.nextInt();

        //Declaring, instantiating and initializing the seat arrangement array
        //Mark every element with "S" as a symbol for available
        String[][] seatArrangement = new String[rows][seatsPerRow];
        for (int i = 0; i < seatArrangement.length; i++) {
            for (int j = 0; j < seatArrangement[i].length; j++) {
                seatArrangement[i][j] = "S";
            }
        }

        //Prompt user for next action, shows menu
        int soldTickets = 0;
        int currentIncome = 0;

        boolean exit = false;
        while (!exit) {
            int command = menu();
            switch (command) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    //Prints the layout of the cinema
                    printCinema(seatArrangement);
                    break;
                case 2:
                    //Ask user to choose a seat
                    boolean validInput = false;
                    int selectedRow = 0;
                    int selectedSeat = 0;

                    while (!validInput) {
                        System.out.println("\nEnter a row number: ");
                        selectedRow = scanner.nextInt();
                        System.out.println("Enter a seat number in that row: ");
                        selectedSeat = scanner.nextInt();
                        validInput = availableSeat(seatArrangement, selectedRow, selectedSeat);

                    }
                    if (validInput) {
                        int price = ticketPrice(seatArrangement, selectedRow);
                        System.out.println("Ticket price: $" + price);
                        soldTickets++;
                        currentIncome += price;
                    }
                    break;

                case 3:
                    //Prints the number of sold tickets
                    //Calculates the percentage of sold tickets over the total tickets
                    //Shows the current income
                    //Prints the total income
                    statistics(seatArrangement, soldTickets, currentIncome);
                    break;
                default:
                    System.out.println("Wrong input. Try again");
                    break;
            }
        }
    }

}
