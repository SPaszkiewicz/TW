package lab6.ActiveObject.methods;

import lab6.ActiveObject.core.Future;
import lab6.ActiveObject.core.Servant;

public class RemoveFromBuffor implements IMethodRequest {

    public int portion;
    private final Servant servant;

    private final Future future;

    public RemoveFromBuffor(int portion, Servant servant, Future future) {
        this.portion = portion;
        this.servant = servant;
        this.future = future;
    }

    @Override
    public void call() {
        this.servant.removeResources(portion);
        this.future.markDone();
    }

    @Override
    public boolean guard() {
        return servant.canRemove(portion);
    }
}
