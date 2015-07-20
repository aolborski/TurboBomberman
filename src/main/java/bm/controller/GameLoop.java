package bm.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static bm.Constants.RELEASE_FAST_FPS;
import static javafx.animation.Animation.INDEFINITE;

/**
 * Class GameLoop is responsible for building and setting game loop and starting.
 */
@Component
public class GameLoop {

    /**
     * The game loop using JavaFXs <code>Timeline</code> API.
     */
    private static Timeline gameLoop;

    @Autowired
    GameWorldController gameWorldController;

    /**
     * Constructor
     */
    public GameLoop() {
        buildAndSetGameLoop();
    }

    /**
     * Builds and sets the game loop and is ready to start
     */
    private void buildAndSetGameLoop() {
        final Duration oneFrameAmount = Duration.millis(RELEASE_FAST_FPS);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmount, event -> {
            gameWorldController.update();
        });

        // sets the game world's game loop (Timeline)
        gameLoop = new Timeline(oneFrame);
        gameLoop.setCycleCount(INDEFINITE);
    }

    /**
     * Kick off (plays) the TimeLine.
     */
    public void beginGameLoop() {
        gameLoop.play();
    }

    /**
     * Stop threads and stop media from playing
     */
    public void stopGameLoop() {
        gameLoop.stop();
    }
}
