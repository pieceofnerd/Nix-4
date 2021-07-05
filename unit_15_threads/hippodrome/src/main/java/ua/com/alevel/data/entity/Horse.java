package ua.com.alevel.data.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Phaser;

public class Horse implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(Horse.class);

    private static int previousId;

    private final Phaser phaser;

    private final int id;

    private  String name;

    private int place;


    static {
        previousId = 0;
    }

    @Override
    public void run() {
        int distance = 0;
        System.out.println(name + " is ready to start");
        phaser.arriveAndAwaitAdvance();
        System.out.println(name + " start right now");
        while (distance < RaceService.Race.getTotalDistance()) {
            distance += RaceService.Race.getHorseDistance();
            try {
                Thread.sleep(RaceService.Race.getHorseSleepTime());
            } catch (InterruptedException e) {
                logger.error("thread {} was interrupted", Thread.currentThread().getName());
                throw new RuntimeException(e);
            }
        }
        place=RaceService.getPositionCounter().getAndIncrement();
        phaser.arriveAndDeregister();

    }

    public Horse(Phaser phaser) {
        this.phaser = phaser;
        this.id = previousId + 1;
        previousId++;
        this.name = "horse " + id;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public int getId() {
        return id;
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Horse horse = (Horse) object;
        return id == horse.id;
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), id);
    }
}
