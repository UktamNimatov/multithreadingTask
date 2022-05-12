package uz.epam.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.epam.multithreading.exception.ProjectException;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Terminal {

    private static final Logger logger = LogManager.getLogger();

    private long id;

    public Terminal() {
    }

    public Terminal(int id) {
        this.id = id;
    }


    public void loadVan(Van van){
        try {
            logger.info("Van " + van.getId() + " is being loaded in terminal N" + this.getId());
            TimeUnit.MILLISECONDS.sleep(1000);
        }catch (InterruptedException exception){
            logger.error("Cannot finish loading ", exception);
        }
            van.setState(Van.State.FINISHED);
            logger.info("Loading van " + van.getId() + " is finished");
    }

    public void unload(Van van){
        try {
            logger.info("Van " + van.getId() + " is being unloaded in terminal N" + this.getId());
            TimeUnit.MILLISECONDS.sleep(1000);
        }catch (InterruptedException exception){
            logger.error("Cannot finish unloading ", exception);
//            Thread.currentThread().interrupt();
        }
            van.setState(Van.State.FINISHED);
            logger.info("Unloading van " + van.getId() + " is finished");
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal terminal = (Terminal) o;
        return id == terminal.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Terminal.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
