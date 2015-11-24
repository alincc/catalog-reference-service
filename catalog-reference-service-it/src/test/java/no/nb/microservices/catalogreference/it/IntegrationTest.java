package no.nb.microservices.catalogreference.it;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import no.nb.microservices.catalogreference.Application;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, RibbonClientConfiguration.class})
@WebIntegrationTest("server.port: 0")
public class IntegrationTest {

    @Value("${local.server.port}")
    int port;

    @Autowired
    ILoadBalancer loadBalancer;

    MockWebServer mockWebServer;
    RestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setup() throws Exception {
        String item1 = IOUtils.toString(getClass().getResourceAsStream("/item1.json"));
        String item2 = IOUtils.toString(getClass().getResourceAsStream("/item2.json"));

        mockWebServer = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
                if (recordedRequest.getPath().equals("/v1/catalog/items/41a7fb4e94aab9a88be23745a1504a92")) {
                    return new MockResponse().setBody(item1).setResponseCode(200).setHeader("Content-Type", "application/hal+json; charset=utf-8");
                } else if (recordedRequest.getPath().equals("/v1/catalog/items/abcdef1234567890abcdef1234567890")) {
                    return new MockResponse().setBody(item2).setResponseCode(200).setHeader("Content-Type", "application/hal+json; charset=utf-8");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockWebServer.setDispatcher(dispatcher);
        mockWebServer.start();

        BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) loadBalancer;
        baseLoadBalancer.setServersList(Arrays.asList(new Server(mockWebServer.getHostName(), mockWebServer.getPort())));;

    }

    @Test
    public void testCreateWikibookReference() {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/v1/catalog/reference/41a7fb4e94aab9a88be23745a1504a92/wiki", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
    }

    @Test
    public void testCreateWikifilmReference() {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/v1/catalog//reference/abcdef1234567890abcdef1234567890/wiki", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
    }

    @Test
    public void testCreateRISReference() throws Exception {
        URI uri = new URI("http://localhost:" + port + "/v1/catalog/reference/41a7fb4e94aab9a88be23745a1504a92/ris");
        ResponseEntity<ByteArrayResource> entity = restTemplate.getForEntity(uri, ByteArrayResource.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void testCreateENWReference() throws Exception {
        URI uri = new URI("http://localhost:" + port + "/v1/catalog/reference/41a7fb4e94aab9a88be23745a1504a92/enw");
        ResponseEntity<ByteArrayResource> entity = restTemplate.getForEntity(uri, ByteArrayResource.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}

@Configuration
class RibbonClientConfiguration {
    @Bean
    public ILoadBalancer ribbonLoadBalancer() {
        return new BaseLoadBalancer();
    }
}