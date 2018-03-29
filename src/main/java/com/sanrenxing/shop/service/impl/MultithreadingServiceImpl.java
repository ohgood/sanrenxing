package com.sanrenxing.shop.service.impl;

import com.sanrenxing.shop.service.MultithreadingService;
import com.sanrenxing.shop.util.http.OKHttpUtil;

import java.util.concurrent.*;

/**
 * Created by Dell on 2018/3/29.
 */
public class MultithreadingServiceImpl implements MultithreadingService{

    private String url = "http://localhost:8090/login?username=15251323101&password=abc123";

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void testSubmit() {

        //多线程执行
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return  OKHttpUtil.get(url);
            }
        };
        Future future = executorService.submit(callable);
        try {
            String body = (String) future.get();
            System.out.println("=========================================" + body);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
