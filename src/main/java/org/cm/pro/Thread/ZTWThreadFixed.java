package org.cm.pro.Thread;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ZTWThreadFixed {
    final ExecutorService _service;
    public ZTWThreadFixed(int pool){
        this._service= Executors.newFixedThreadPool(pool);
    }

    public void concurrenceList(Collection list, Consumer cb) throws InterruptedException {
        final CountDownLatch latch=new CountDownLatch(list.size());
        list.forEach((item)->{
            _service.execute(()->{
                cb.accept(item);
                latch.countDown();
            });
        });
        latch.await();
    }
}
