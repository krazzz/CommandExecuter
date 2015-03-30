package CMExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by evgeniy on 20.03.15.
 * <p/>
 * Command executor.
 * Im
 */
public class Executor implements Runnable {

    private static final String DEFAULT_COMMAND = "ping -c 1 yandex.ru";
    private static final int DEFAULT_INTEVAL = 60;
    private static final boolean DEFAULT_STATE = false;
    private String command;
    private int interval;
    private volatile boolean state;

    public Executor() {
        command = DEFAULT_COMMAND;
        interval = DEFAULT_INTEVAL;
        state = DEFAULT_STATE;
    }

    public Executor(String executeCommand, int period, boolean activity) {
        command = executeCommand;
        interval = period;
        state = activity;
    }

    /**
     * Method return current executed command.
     *
     * @return String Executed command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Method return current interval.
     * Interval in seconds.
     *
     * @return int Execute interval.
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Method return current state.
     * True active, false inactive.
     *
     * @return boolean Current state
     */
    public boolean getState() {
        return state;
    }

    /**
     * Method set execute command.
     * Command transmit with all required arguments.
     *
     * @param executeCommand Execute command.
     */
    public void setCommand(String executeCommand) {
        command = executeCommand;
    }

    /**
     * Method set execute interval.
     * Interval in seconds.
     *
     * @param period Execute interval.
     */
    public void setInterval(int period) {
        interval = period;
    }

    /**
     * Method set state.
     * True is active, false inactive.
     *
     * @param activity State.
     */
    public void setState(boolean activity) {
        state = activity;
    }

    /**
     * Method execute command.
     * Used java.lang.Process.
     * All output send to System.out.
     * Information messages with tag "current date + [INFO]".
     * Error messages with tag "current date + [ERROR]".
     *
     * @throws IOException
     */
    public void execute() throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String s = null;
        while ((s = reader.readLine()) != null) {
            System.out.println(new Date(System.currentTimeMillis()).toString() + " [INFO] " + s);
        }
        while ((s = errorReader.readLine()) != null) {
            System.out.println(new Date(System.currentTimeMillis()).toString() + " [ERROR] " + s);
        }
    }

    /**
     * Call execute.
     * After sleep n seconds.
     * Where n - interval.
     *
     */
    @Override
    public void run() {
        while (true) {
            try {
                if (state) {
                    execute();
                    Thread.sleep(interval * 1000);
                } else {
                    break;
                }
            } catch (IOException ex) {
                System.out.println(new Date(System.currentTimeMillis()).toString() + " [ERROR] " + ex.toString());
            } catch (InterruptedException ex) {
                System.out.println(new Date(System.currentTimeMillis()).toString() + " [ERROR] " + ex.toString());
            }
        }
    }
}
