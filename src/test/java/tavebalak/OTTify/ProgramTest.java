package tavebalak.OTTify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.program.service.ProgramShowAndSaveService;

@SpringBootTest
public class ProgramTest {

    @Autowired
    private ProgramShowAndSaveService programShowAndSaveService;

    @Test
    @Rollback(value = false)
    @Transactional
    void testing() throws InterruptedException {

        int numberOfThreads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        executorService.execute(() -> {
            programShowAndSaveService.searchByName("겨울");
            latch.countDown();
        });

        executorService.execute(() -> {
            programShowAndSaveService.searchByMovieName("겨울", 1);
            latch.countDown();
        });

        latch.await();

    }
}
