package StateManagement;

import Helpers.FakeLoginState;
import Helpers.FakeMenuState;
import com.sun.glass.ui.Application;
import java.util.Properties;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mvsm.statemanagement.StateManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateManagerTest {

    private StateManager testManager;
    private Button loginB;
    private Button menuB;
    private TextField loginF;
    private TextField menuF;
    private FakeLoginState fakeLogin;
    private FakeMenuState fakeMenu;
    private Scene scene;

    public StateManagerTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        final JFXPanel fxPanel = new JFXPanel();
        Platform.runLater(new Thread());
        this.loginB = new Button();
        this.menuB = new Button();
        this.loginF = new TextField();
        this.menuF = new TextField();
        this.testManager = new StateManager(new Properties());
        this.fakeLogin = new FakeLoginState(loginB, loginF, testManager);
        this.fakeMenu = new FakeMenuState(menuB, menuF, testManager);
        this.scene = new Scene(fakeMenu.getCurrent());
        this.testManager.addState(fakeLogin);
        this.testManager.addState(fakeMenu);
        this.testManager.setScene(scene);
        this.testManager.setSceneRoot(fakeLogin.getCurrent());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void setSceneRootTest() {
        assertTrue(this.scene.getRoot().equals(fakeLogin.getCurrent()));
        this.testManager.setSceneRoot(this.fakeMenu.getCurrent());
        assertTrue(this.scene.getRoot().equals(this.fakeMenu.getCurrent()));
    }

    @Test
    public void startLoopUpdatesRightState() throws InterruptedException {
        this.testManager.setCurrentState(1);
        this.testManager.setSceneRoot(this.testManager.getCurrentState().getCurrent());
        this.testManager.startLoop();
        Thread.sleep(1500);
        this.testManager.stopLoop();
        if (this.fakeMenu.getNumber() != 0 && this.fakeLogin.getNumber() == 0) {
            return;
        }
        fail("The update was not done to the right state. FakeMenu's number should be 60, but was " + this.fakeMenu.getNumber() + "\nand FakeLogin's number should be 0, but it was " + this.fakeLogin.getNumber());
    }
}
