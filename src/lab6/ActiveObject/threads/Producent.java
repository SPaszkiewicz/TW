package lab6.ActiveObject.threads;

import lab6.ActiveObject.TimeOrchiester;
import lab6.ActiveObject.core.Future;
import lab6.ActiveObject.Proxy;

import static lab3.CustomerProducer.Main.randomPortion;

public class Producent implements Runnable {
    private final Proxy proxy;
    private final int maxFoodPortion;
    private Future task = null;
    private int receivedBuffer;
    public final int index;
    private final TimeOrchiester timeOrchiester;
    private final int time;
    public Producent(Proxy proxy, int maxFoodPortion, int index, TimeOrchiester timeOrchiester, int time) {
        this.proxy = proxy;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffer = 0;
        this.index = index;
        this.timeOrchiester = timeOrchiester;
        this.time = time;
    }

    @Override
    public void run() {
        while (!timeOrchiester.isFinished) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (this.task == null) {
                try {
                    int portion = randomPortion(1, maxFoodPortion);
                    this.task = this.proxy.add(portion);
                    this.receivedBuffer++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                boolean isDone = this.task.isDone();
                if (isDone) {
                    this.task = null;
                }
            }
        }
    }

    public int getReceivedBuffer() {
        return receivedBuffer;
    }
}
