package com.playdate.app.util.executor;

import java.util.concurrent.ExecutorService;

public interface ExecutorProvider {

    ExecutorService computation();

    ExecutorService io();

    ExecutorService ui();
}
