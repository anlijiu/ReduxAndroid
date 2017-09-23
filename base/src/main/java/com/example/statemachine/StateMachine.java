package com.example.statemachine;

import java.util.ArrayList;

/**
 * A simple event based state machine.
 *
 * @param <State>     The state of the entity
 * @param <EventType> The event type to be handled
 */
public final class StateMachine<State extends Enum<State>, EventType extends Enum<EventType>> {
    private Node<State, EventType> root;
    private ArrayList<OnStateChangeListener> mOnStateChangeListeners;
    public interface OnStateChangeListener {
        void onStateChange(Enum state);
    }
    
    StateMachine(Node<State, EventType> root) {
        this.root = root;
    }

    /**
     * Apply an event to the state machine.
     *
     * @param eventType The event type to be handled
     */
    public void apply(EventType eventType) {
        Node<State, EventType> nextNode = root.getNeighbor(eventType);

        if (nextNode == null) {
            throw new UnexpectedEventTypeException(root.getState(), eventType);
        }

        root.onExit();
        root = nextNode;
        root.onEnter();

        if (mOnStateChangeListeners != null) {
            ArrayList<OnStateChangeListener> listenersCopy =
                    (ArrayList<OnStateChangeListener>)mOnStateChangeListeners.clone();
            int numListeners = listenersCopy.size();
            for (int i = 0; i < numListeners; ++i) {
                listenersCopy.get(i).onStateChange(nextNode.getState());
            }
        }
    }

    public void addOnStateChangeListener(OnStateChangeListener listener) {
        if (mOnStateChangeListeners == null) {
            mOnStateChangeListeners = new ArrayList<OnStateChangeListener>();
        }
        if (!mOnStateChangeListeners.contains(listener)) {
            mOnStateChangeListeners.add(listener);
        }
    }

    public void removeOnStateChangeListener(OnStateChangeListener listener) {
        if (mOnStateChangeListeners == null) {
            return;
        }
        mOnStateChangeListeners.remove(listener);
    }
    
    /**
     * @return The current state of the state machine
     */
    public State getState() {
        return root.getState();
    }
}
