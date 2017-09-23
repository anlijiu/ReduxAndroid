package com.example.ui.misc.command;


import java.util.concurrent.Callable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ReplyCommand<T> {

    private Consumer<T> execute1;
    private Action execute0;

    private Callable<Boolean> canExecute;

    public ReplyCommand(Action execute) { this.execute0 = execute;}

    public ReplyCommand(Consumer<T> execute) {
        this.execute1 = execute;
    }
    
    public ReplyCommand(Action execute, Callable<Boolean> canExecute) {
        this.execute0 = execute;
        this.canExecute = canExecute;
    }
    
    public ReplyCommand(Consumer<T> execute, Callable<Boolean> canExecute) {
        this.execute1 = execute;
        this.canExecute = canExecute;
    }

    private boolean canExecute() {
        if (canExecute == null) {
            return true;
        }
        try {
            return canExecute.call();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void execute() {
        if (execute0 != null && canExecute()) {
            try {
                execute0.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void execute(T parameter) {
        if (execute1 != null && canExecute()) {
            try {
                execute1.accept(parameter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
