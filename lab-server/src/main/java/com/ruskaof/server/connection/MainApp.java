package com.ruskaof.server.connection;

import com.ruskaof.common.util.State;

import java.io.IOException;

public interface MainApp {
    void start(State<Boolean> isWorking) throws IOException;
}
