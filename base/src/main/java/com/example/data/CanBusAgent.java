package com.example.data;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by anlijiu on 17-9-14.
 */

public class CanBusAgent {
    private Subject<String> bus;
    private Scheduler mScheduler;
    private Scheduler.Worker ioWorker;

    public CanBusAgent() {
        mScheduler = Schedulers.computation();
        ioWorker = mScheduler.createWorker();
        initDataStreams();

    }

    private void initDataStreams() {
        bus = PublishSubject.<String>create().toSerialized();
    }

    public void deliver(String property) {
        if(bus != null) {
            bus.onNext(property);
        }
    }

    public Flowable<String> observable() {
        return bus.toFlowable(BackpressureStrategy.DROP);
    }

}
