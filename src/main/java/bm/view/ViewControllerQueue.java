package bm.view;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class ViewControllerQueue {

    public final BlockingQueue<NodePair> queue;

    public ViewControllerQueue() {
        this.queue = new LinkedBlockingDeque<>();
    }
}
