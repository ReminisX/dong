package com.zijin.dong.service.Impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class DeadLockTest {

    private int a;

    private int b;

    public DeadLockTest(){
        a = 1;
        b = 1;
    }

    class P implements Runnable{

        @SneakyThrows
        @Override
        public void run() {
            boolean getA = false;
            boolean getB = false;

            while(true){
                if (a > 0){
                    a--;
                    System.out.println(Thread.currentThread().getName() + "获取到a");
                }else{
                    System.out.println(Thread.currentThread().getName() + "未获取到a");
                }
                Thread.sleep(1000);
                if (b > 0){
                    b--;
                    System.out.println(Thread.currentThread().getName() + "获取到b");
                }else{
                    System.out.println(Thread.currentThread().getName() + "未获取到b");
                }
                Thread.sleep(1000);
                if (getA && getB){
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + "获取到全部资源");
        }
    }

    public void runDeadLock() throws InterruptedException {
        P p1 = new P();
        P p2 = new P();
        new Thread(p1).start();
        Thread.sleep(50);
        new Thread(p2).start();
    }

}
