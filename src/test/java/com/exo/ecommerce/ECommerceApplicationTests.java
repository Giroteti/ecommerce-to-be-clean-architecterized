package com.exo.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ECommerceApplicationTests {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void beforeTest() {
        invoiceRepository.deleteAll();
        cartRepository.deleteAll();
        itemRepository.deleteAll();

        Item item1 = new Item(1L, "item1", "This is item 1", 2, (float) 10.0);
        Item item2 = new Item(2L, "item2", "This is item 2", 5, (float) 20.0);
        itemRepository.save(item1);
        itemRepository.save(item2);

    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    @Ignore
    public void should_list_all_items() {
        // given
        String expectedBody = "[{\"id\":1,\"name\":\"item1\",\"description\":\"This is item 1\",\"remainingInStock\":2,\"price\":10.0},{\"id\":2,\"name\":\"item2\",\"description\":\"This is item 2\",\"remainingInStock\":5,\"price\":20.0}]";

        // when
        String body = this.restTemplate.getForObject("/items", String.class);

        // then
        assertEquals(expectedBody, body);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_add_available_items() throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_add_available_items.txt"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart = cartRepository.save(new Cart());
        expectedBody = String.format(expectedBody, cart.getId());

        // when
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        String json = getResponseAsPrettyJson("/add-item?id=2");

        this.printJsons(expectedBody, json);

        // then
        assertEquals(expectedBody, json);
    }

    private void printJsons(String expectedBody, String json) {
        System.out.println(expectedBody);
        System.out.println("--------------");
        System.out.println(json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_not_add_exhausted_items() throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_not_add_exhausted_items.txt"));
        String expectedBodyExhausted = new String(encoded, Charset.defaultCharset());
        Cart cart = cartRepository.save(new Cart());

        // when
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        String jsonExhausted = getResponseAsPrettyJson("/add-item?id=1");

        System.out.println(jsonExhausted);

        // then
        assertEquals(expectedBodyExhausted, jsonExhausted);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_not_add_unknown_items() throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_not_add_unknown_items.txt"));
        String expectedBodyUnknown = new String(encoded, Charset.defaultCharset());
        Cart cart = cartRepository.save(new Cart());

        // when
        String jsonUnknown = getResponseAsPrettyJson("/add-item?id=3");

        // then
        assertEquals(expectedBodyUnknown, jsonUnknown);
    }


    // TODO
    // should_display_current_cart
    // should_check_out_cart
    // should_list_invoices (empty)
    // should_display_invoice (empty)
    // test cart repository
    //

    private String getResponseAsPrettyJson(String uri) throws IOException {

        String responseBodyExhausted = this.restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return this.formatJson(responseBodyExhausted);
    }

    private String formatJson(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(body, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }
}
