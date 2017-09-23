package com.example.__host;

/**
 * Created by anlijiu on 17-9-14.
 */

public interface A01Module {

    void launch();
    void shutdown();

    A01HostDelegate delegate();
    void delegate(A01HostDelegate delegate);

}