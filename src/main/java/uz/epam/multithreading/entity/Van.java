package uz.epam.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.nio.cs.ext.ISCII91;
import uz.epam.multithreading.exception.ProjectException;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.Callable;

public class Van implements Callable<Boolean> {

    private static final Logger logger = LogManager.getLogger();

    private long id;
    private boolean isPerishable;
    private Task task;
    private State state;

    public Van(long id, boolean isPerishable, Task task) {
        this.id = id;
        this.isPerishable = isPerishable;
        this.task = task;
    }

    public Van() {
    }

    public enum State{
        NEW, LOADING, UNLOADING, FINISHED
    }


    public enum Task{
        LOAD, UNLOAD
    }

    @Override
    public Boolean call() {
        this.state = State.NEW;
        LogisticsBase logisticsBase = LogisticsBase.getInstance();
        Terminal retrievedTerminal;
        try {
            retrievedTerminal = logisticsBase.getTerminal(isPerishable);
        }catch (ProjectException exception){
            logger.error("Terminal cannot be obtained ", exception);
            return false;
        }

        try {
            if (this.task.equals(Task.LOAD)){
                this.state = State.LOADING;
                retrievedTerminal.loadVan(this);
            }else {
                this.state = State.UNLOADING;
                retrievedTerminal.unload(this);
            }
        }catch (ProjectException exception){
            logger.error("Task cannot be finished " + exception);
            return false;
        }finally {
            logisticsBase.releaseTerminal(retrievedTerminal);
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPerishable() {
        return isPerishable;
    }

    public void setPerishable(boolean perishable) {
        isPerishable = perishable;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Van van = (Van) o;
        return id == van.id &&
                isPerishable == van.isPerishable &&
                task == van.task;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isPerishable, task);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Van.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("isPerishable=" + isPerishable)
                .add("task=" + task)
                .toString();
    }
}
