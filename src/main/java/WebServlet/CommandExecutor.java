package WebServlet;

import CMExecutor.Executor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by evgeniy on 21.03.15.
 *
 * Current servlet initialize command execution.
 * Via get method you can gain command (execution command),
 * interval & current state.
 */
public class CommandExecutor extends HttpServlet {
    private Thread executorThread;
    private Executor commandExecutor;

    @Override
    public void init() {
        try {
            super.init();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        commandExecutor = new Executor();
    }

    @Override
    public void destroy() {
        if (executorThread.isAlive()) {
            commandExecutor.setState(false);
        }
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("command", commandExecutor.getCommand());
        request.setAttribute("interval", commandExecutor.getInterval());
        request.setAttribute("state", commandExecutor.getState());
        request.setAttribute("status", "");
        request.getRequestDispatcher("ExecutorView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        String interval = request.getParameter("interval");
        String state =  request.getParameter("state");
        commandExecutor.setCommand(command);
        if(interval != null) {
            commandExecutor.setInterval(Integer.parseInt(interval));
        }
        if (state.equalsIgnoreCase("true")) {
            if (executorThread == null) {
                executorThread = new Thread(commandExecutor);
                commandExecutor.setState(true);
                executorThread.start();
                System.out.println(new Date(System.currentTimeMillis()).toString() + " [INFO] Executor activated.");
            } else if (!commandExecutor.getState()) {
                commandExecutor.setState(true);
                executorThread = new Thread(commandExecutor);
                executorThread.start();
                System.out.println(new Date(System.currentTimeMillis()).toString() + " INFO] Executor activated.");
            }
        } else if (state.equalsIgnoreCase("false")) {
            if (commandExecutor.getState()) {
                commandExecutor.setState(false);
                System.out.println(new Date(System.currentTimeMillis()).toString() + " [INFO] Executor deactivated.");
            }
        }
        request.setAttribute("status", "Successful changed!");
        request.setAttribute("command", commandExecutor.getCommand());
        request.setAttribute("interval", commandExecutor.getInterval());
        request.setAttribute("state", commandExecutor.getState());
        request.getRequestDispatcher("ExecutorView.jsp").forward(request, response);
    }
}
