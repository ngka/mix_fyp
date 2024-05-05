package com.router.Core.Exector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/************************************************************
 * Description: 線程池
 ***********************************************************/
public class ThreadExector {

    private static final int DOWNLOAD_POOL_SIZE = 3;

    /**
     * 文件上傳、下載的線程池
     */
    public static final ExecutorService executor = Executors.newFixedThreadPool(DOWNLOAD_POOL_SIZE);

    /**
     * 其他耗時操作的線程池
     */
    public static final ExecutorService Taskexecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

}
