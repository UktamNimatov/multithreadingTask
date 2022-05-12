package uz.epam.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticsBase {

    private static final Logger logger = LogManager.getLogger();

    private static LogisticsBase instance;

    private static Lock instanceLock = new ReentrantLock(true);

    private Lock terminalLock = new ReentrantLock(true);

    private Deque<Terminal> availableTerminals;
    private Deque<Terminal> busyTerminals;


    private static final int MAX_TERMINAL_SIZE = 5;

    private LogisticsBase() {
        availableTerminals = new ArrayDeque<>(MAX_TERMINAL_SIZE);
        for (int i = 0; i < MAX_TERMINAL_SIZE; i++){
            availableTerminals.add(new Terminal());
        }
        busyTerminals = new ArrayDeque<>(0);
    }

    public static LogisticsBase getInstance(){
        try {
            instanceLock.lock();
            if (instance == null) {
                instance = new LogisticsBase();
                return instance;
            }
        }finally {
            instanceLock.unlock();
        }
        return instance;
    }

    public Terminal getTerminal(boolean isPerishable){ //FIXME
        Terminal terminal = new Terminal();
        terminalLock.lock();

        try {
            if (isPerishable){
                terminal = availableTerminals.pollFirst();
                busyTerminals.offerFirst(terminal);
            }else {
                terminal = availableTerminals.pollLast();
                busyTerminals.offerLast(terminal);
            }
        }catch (NoSuchElementException exception){
            logger.error("Could not retrieve a terminal ", exception);
            Thread.currentThread().interrupt();
        }finally {
            terminalLock.unlock();
        }

        return terminal;
    }

    public void releaseTerminal(Terminal terminal){
        terminalLock.lock();
        try {
            busyTerminals.remove(terminal);
            availableTerminals.add(terminal);
        }finally {
            terminalLock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsBase that = (LogisticsBase) o;
        return Objects.equals(terminalLock, that.terminalLock) &&
                Objects.equals(availableTerminals, that.availableTerminals) &&
                Objects.equals(busyTerminals, that.busyTerminals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminalLock, availableTerminals, busyTerminals);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LogisticsBase.class.getSimpleName() + "[", "]")
                .add("terminalLock=" + terminalLock)
                .add("availableTerminals=" + availableTerminals)
                .add("busyTerminals=" + busyTerminals)
                .toString();
    }
}
