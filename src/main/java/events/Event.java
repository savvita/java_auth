package events;

import java.util.ArrayList;
import java.util.Collection;

public class Event {
    private Collection<ActionEvent> listeners = new ArrayList<>();
    public void add(ActionEvent listener) {
        listeners.add(listener);
    }
    public void remove(ActionEvent listener) {
        listeners.remove(listener);
    }
    public void invoke() {
        for(ActionEvent listener : listeners) {
            listener.invoke();
        }
    }
}
