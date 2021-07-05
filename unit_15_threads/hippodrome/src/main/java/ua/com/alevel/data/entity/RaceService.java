package ua.com.alevel.data.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;


public class RaceService {

    private final Phaser phaser;

    private final Set<Horse> horses;

    private static AtomicInteger positionCounter;

     private static final Logger logger = LoggerFactory.getLogger(RaceService.class);

    public RaceService(int horseNumber) {
        this.phaser = new Phaser();
        positionCounter = new AtomicInteger();
        this.horses = new ConcurrentHashMap<>().newKeySet();
        for (int i = 0; i < horseNumber; i++) {
            horses.add(new Horse(phaser));
        }
    }

    public synchronized void prepareRace() {
        positionCounter.set(1);
        phaser.bulkRegister(horses.size());
        int start = phaser.getPhase();
        for (Horse horse : horses) {
            new Thread(horse).start();
        }
        int end = phaser.awaitAdvance(start);
        phaser.awaitAdvance(end);
        System.out.println("Race ended");
    }

    public static AtomicInteger getPositionCounter() {
        return positionCounter;
    }

    public int bet() {
        System.out.println("Please, choose horse from the list: ");
        for (Horse horse : horses) {
            System.out.println(horse.getName());
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int value = Integer.parseInt(bufferedReader.readLine());
            if (value < 0 || value > horses.size()) {
                throw new IllegalArgumentException("Value cannot be less 0 and bigger than horse number");
            }
            return value;
        } catch (IOException e) {
              logger.error("Something goes wrong during bufferReader working");
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
              logger.error("Incorrect value was entered by user");
            System.out.println("Please, input correct number");
            return bet();
        }
    }

    public int getHorsePlaceById(int id) {
        Optional<Horse> horse = horses.stream()
                .filter(h -> h.getId() == id)
                .findFirst();

        if (horse.isPresent()) {
            return horse.get().getPlace();
        } else throw new IllegalArgumentException("There is no horse with id " + id);
    }


    public static class Race {

        private static final int TOTAL_DISTANCE = 1000;

        private static final int MIN_HORSE_DISTANCE = 100;

        private static final int MAX_HORSE_DISTANCE = 200;

        private static final int MIN_SLEEP_TIME = 400;

        private static final int MAX_SLEEP_TIME = 500;

        public static int getHorseDistance() {
            return (int) Math.floor(Math.random() * (MAX_HORSE_DISTANCE - MIN_HORSE_DISTANCE + 1) + MIN_HORSE_DISTANCE);
        }

        public static int getHorseSleepTime() {
            return (int) Math.floor(Math.random() * (MAX_SLEEP_TIME - MIN_SLEEP_TIME + 1) + MIN_SLEEP_TIME);
        }

        public static int getTotalDistance() {
            return TOTAL_DISTANCE;
        }

    }
}
