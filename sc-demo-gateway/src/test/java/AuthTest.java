import io.netty.handler.codec.http.HttpHeaders;
import org.junit.Test;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Janle on 2022/8/21
 */
public class AuthTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(20);

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            int finalI = i;
            Runnable run = () -> {
                Consumer<HttpHeaders> headersConsumer = httpHeaders -> {
                    httpHeaders.add("token", "user" + finalI);
                };
                HttpClient client = HttpClient.create();
                client.headers(headersConsumer)
                        .get()
                        .uri("http://localhost:9000/auth/user" + finalI)
                        .response()
                        .block();
            };
            executor.execute(run);
        }

        Thread.sleep(10000);
    }
}
