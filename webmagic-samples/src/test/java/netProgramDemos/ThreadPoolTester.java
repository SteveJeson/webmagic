package netProgramDemos;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ThreadPoolTester {
    public static void main (String[] args) {
        if (args.length != 2) {
            System.out.println("用法:java ThreadPoolTest  numTasks poolSize");
            System.out.println(" numTaks -integer:任务的数目");
            System.out.println(" numThreads -integer:线程池中的线程数目");
            return;
        }
        int numTasks = Integer.parseInt(args[0]);
        int poolSize = Integer.parseInt(args[1]);

        ThreadPool threadPool = new ThreadPool(poolSize);

        //运行任务
        for (int i=0;i<numTasks;i++) {
            threadPool.execute(createTask(i));
            threadPool.join();
//            threadPool.close();
        }
    }

    private static Runnable createTask (final int taskID) {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Task "+ taskID +":start");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Task "+ taskID + ":end");
                }
            }
        };
    }
}
