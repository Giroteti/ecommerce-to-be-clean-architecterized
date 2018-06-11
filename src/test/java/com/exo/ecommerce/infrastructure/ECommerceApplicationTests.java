package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.SlowTests;
import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.infrastructure.bdd.CartCRUDRepository;
import com.exo.ecommerce.infrastructure.bdd.InvoiceCRUDRepository;
import com.exo.ecommerce.infrastructure.bdd.ItemCRUDRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ECommerceApplicationTests {

    @Autowired
    private ItemCRUDRepository itemRepository;
    @Autowired
    private CartCRUDRepository cartRepository;
    @Autowired
    private InvoiceCRUDRepository invoiceRepository;

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
    public void should_list_all_items() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_list_items.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());

        // when
        String json = getResponseAsPrettyJson("/items");

        // then
        assertEquals(expectedBody, json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_add_available_items() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_add_available_items.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart = cartRepository.save(new Cart());
        expectedBody = String.format(expectedBody, cart.getId());

        // when
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        String json = getResponseAsPrettyJson("/add-item?id=2");

        // then
        assertEquals(expectedBody, json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_not_add_exhausted_items() throws IOException {
        // given
        cartRepository.save(new Cart());

        // when
        this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());
        this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());
        this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());
        ResponseEntity<String> response = this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());

        // then
        assertNull(response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_not_add_unknown_items() throws IOException {
        // given
        cartRepository.save(new Cart());

        // when
        ResponseEntity<String> response = this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(3), String.class, Collections.emptyMap());

        // then
        assertNull(response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_check_out_cart() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_check_out_cart.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart = cartRepository.save(new Cart());

        // when
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        String json = getResponseAsPrettyJson("/check-out");
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) invoiceRepository.findAll();
        expectedBody = String.format(expectedBody, invoices.get(0).getId(), invoices.get(0).getDate(), cart.getId());

        // then
        assertEquals(expectedBody, json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_display_given_invoice() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_display_given_invoice.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart = cartRepository.save(new Cart());
        // when
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/check-out", String.class);
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) invoiceRepository.findAll();
        String json = getResponseAsPrettyJson("/invoice?id="+invoices.get(0).getId());
        expectedBody = String.format(expectedBody, invoices.get(0).getId(), invoices.get(0).getDate(), cart.getId());

        // then
        assertEquals(expectedBody, json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_list_invoices() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_list_invoices.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart1= cartRepository.save(new Cart());
        // when
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/check-out", String.class);
        Cart cart2 = cartRepository.save(new Cart());

        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/check-out", String.class);
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) invoiceRepository.findAll();
        String json = getResponseAsPrettyJson("/invoices");
        expectedBody = String.format(
                expectedBody,
                invoices.get(0).getId(),
                invoices.get(0).getDate(),
                cart1.getId(),
                invoices.get(1).getId(),
                invoices.get(1).getDate(),
                cart2.getId()
        );

        // then
        assertEquals(expectedBody, json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_list_invoices_when_empty() throws IOException {
        // when
        String json = getResponseAsPrettyJson("/invoices");

        // then
        assertEquals("[ ]", json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_list_items_when_empty() throws IOException {
        // given
        itemRepository.deleteAll();

        // when
        String json = getResponseAsPrettyJson("/items");

        // then
        assertEquals("[ ]", json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_return_empty_cart_when_no_cart() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_return_empty_cart.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        // when
        String json = getResponseAsPrettyJson("/cart");

        // then
        assertEquals(expectedBody, json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_return_empty_cart_when_empty_cart() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_return_empty_cart.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart1= cartRepository.save(new Cart());
        // when
        String json = getResponseAsPrettyJson("/cart");

        // then
        assertEquals(expectedBody, json);
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_return_404_when_invoice_not_found() throws IOException {
        // when
        ResponseEntity<String> response = this.restTemplate.getForEntity("/invoice?id=99999999", String.class);

        // then
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_return_403_when_no_cart_to_check_out() {
        // when
        ResponseEntity<String> response = this.restTemplate.getForEntity("/check-out", String.class);

        // then
        assertNull(response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_return_403_when_no_item_to_check_out() {
        // given
        Cart cart = cartRepository.save(new Cart());

        // when
        ResponseEntity<String> response = this.restTemplate.getForEntity("/check-out", String.class);

        // then
        assertNull(response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_display_current_cart() throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_display_current_cart.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart = cartRepository.save(new Cart());
        expectedBody = String.format(expectedBody, cart.getId());

        // when
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=1", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        this.restTemplate.getForObject("/add-item?id=2", String.class);
        String jsonCurrentCart = getResponseAsPrettyJson("/cart");

        // then
        assertEquals(expectedBody, jsonCurrentCart);
    }

    private String getResponseAsPrettyJson(String uri) throws IOException {

        String responseBody = this.restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return this.formatJson(responseBody);
    }

    private String formatJson(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(body, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private void printJsons(String expectedBody, String json) {
        System.out.println("---EXPECTED->>");
        System.out.println(expectedBody);
        System.out.println("----ACTUAL-->>");
        System.out.println(json);
        System.out.println("--------------");
    }

    private HttpEntity<?> buildHttpRequestWithId(Integer id) {
        HttpHeaders requestHeaders = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("id", id.toString());
        return new HttpEntity<Object>(body, requestHeaders);
    }
}
