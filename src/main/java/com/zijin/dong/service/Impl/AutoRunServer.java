package com.zijin.dong.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AutoRunServer implements ApplicationRunner {

    private final DeadLockTest deadLockTest;

    @Autowired
    public AutoRunServer(DeadLockTest deadLockTest) {
        this.deadLockTest = deadLockTest;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        deadLockTest.runDeadLock();
    }
}
