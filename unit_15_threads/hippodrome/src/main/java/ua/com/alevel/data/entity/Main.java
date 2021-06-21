package ua.com.alevel.data.entity;

public class Main {

    private static final int HORSE_NUMBER = 5;

    public static void main(String[] args) {

        RaceService raceService = new RaceService(HORSE_NUMBER);

        int bet = raceService.bet();

        raceService.prepareRace();

        int place = raceService.getHorsePlaceById(bet);

        System.out.println("Horse " + bet + " on " + place + " place");


    }

}
