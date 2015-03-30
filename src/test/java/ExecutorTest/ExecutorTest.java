package ExecutorTest;

import CMExecutor.Executor;
import junit.framework.TestCase;
import org.junit.*;

public class ExecutorTest extends TestCase {

    private Executor executor;

    @BeforeClass
    public void setUp() throws Exception {
        executor = new Executor();
        System.out.println("Starting Executor test.");
    }

    @AfterClass
    public void tearDown() throws Exception {
        System.out.println("Executor test finished.");
    }

    @Test
    public void testGetCommand() throws Exception {
        String command = executor.getCommand();
        assertTrue(command.equalsIgnoreCase("ping -c 1 yandex.ru"));
    }

    @Test
    public void testGetInterval() throws Exception {
        int interval = executor.getInterval();
        assertTrue(interval == 60);
    }

    @Test
    public void testGetState() throws Exception {
        boolean state = executor.getState();
        assertFalse(state);
    }

    @Test
    public void testSetCommand() throws Exception {
        executor.setCommand("ping");
        String command = executor.getCommand();
        assertTrue(command.equalsIgnoreCase("ping"));
    }

    @Test
    public void testSetInterval() throws Exception {
        executor.setInterval(100);
        int interval = executor.getInterval();
        assertTrue(interval == 100);
    }

    @Test
    public void testSetState() throws Exception {
        executor.setState(true);
        assertTrue(executor.getState());
    }

}