package com.exo.ecommerce.infrastructure.http.routing;

import com.exo.ecommerce.SlowTests;
import com.exo.ecommerce.infrastructure.bdd.cart.Cart;
import com.exo.ecommerce.infrastructure.bdd.cart.CartCRUDRepository;
import com.exo.ecommerce.infrastructure.bdd.invoice.Invoice;
import com.exo.ecommerce.infrastructure.bdd.invoice.InvoiceCRUDRepository;
import com.exo.ecommerce.infrastructure.bdd.item.Item;
import com.exo.ecommerce.infrastructure.bdd.item.ItemCRUDRepository;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

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

        Item item1 = new Item(1L, "item1", "This is item 1", 2, 10d);
        Item item2 = new Item(2L, "item2", "This is item 2", 5, 20d);
        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @Test
    @Category(SlowTests.class)
    public void should_list_all_items() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_list_items.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());

        // when
        String json = getResponseAsPrettyJson("/items");

        // then
        assertThat(json).isEqualTo(expectedBody);
    }

    @Test
    @Category(SlowTests.class)
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
        assertThat(json).isEqualTo(expectedBody);
    }

    @Test
    @Category(SlowTests.class)
    public void should_not_add_exhausted_items() {
        // given
        cartRepository.save(new Cart());

        // when
        this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());
        this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());
        this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());
        ResponseEntity<String> response = this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(1), String.class, Collections.emptyMap());

        // then
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Category(SlowTests.class)
    public void should_not_add_unknown_items() {
        // given
        cartRepository.save(new Cart());

        // when
        ResponseEntity<String> response = this.restTemplate.postForEntity("/add-item", buildHttpRequestWithId(3), String.class, Collections.emptyMap());

        // then
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Category(SlowTests.class)
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
        assertThat(json).isEqualTo(expectedBody);
    }

    @Test
    @Category(SlowTests.class)
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
        String json = getResponseAsPrettyJson("/invoice?id=" + invoices.get(0).getId());
        expectedBody = String.format(expectedBody, invoices.get(0).getId(), invoices.get(0).getDate(), cart.getId());

        // then
        assertThat(json).isEqualTo(expectedBody);
    }

    @Test
    @Category(SlowTests.class)
    public void should_list_invoices() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_list_invoices.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart1 = cartRepository.save(new Cart());
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
        assertThat(json).isEqualTo(expectedBody);
    }

    @Test
    @Category(SlowTests.class)
    public void should_list_invoices_when_empty() throws IOException {
        // when
        String json = getResponseAsPrettyJson("/invoices");

        // then
        assertThat(json).isEqualTo("[ ]");
    }

    @Test
    @Category(SlowTests.class)
    public void should_list_items_when_empty() throws IOException {
        // given
        itemRepository.deleteAll();

        // when
        String json = getResponseAsPrettyJson("/items");

        // then
        assertThat(json).isEqualTo("[ ]");
    }

    @Test
    @Category(SlowTests.class)
    public void should_return_empty_cart_when_no_cart() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_return_empty_cart.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        // when
        String json = getResponseAsPrettyJson("/cart");

        // then
        assertThat(json).isEqualTo(expectedBody);
    }

    @Test
    @Category(SlowTests.class)
    public void should_return_empty_cart_when_empty_cart() throws IOException {
        // given
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/should_return_empty_cart.json"));
        String expectedBody = new String(encoded, Charset.defaultCharset());
        Cart cart1 = cartRepository.save(new Cart());
        // when
        String json = getResponseAsPrettyJson("/cart");

        // then
        assertThat(json).isEqualTo(expectedBody);
    }

    @Test
    @Category(SlowTests.class)
    public void should_return_404_when_invoice_not_found() {
        // when
        ResponseEntity<String> response = this.restTemplate.getForEntity("/invoice?id=99999999", String.class);

        // then
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Category(SlowTests.class)
    public void should_return_403_when_no_cart_to_check_out() {
        // when
        ResponseEntity<String> response = this.restTemplate.getForEntity("/check-out", String.class);

        // then
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Category(SlowTests.class)
    public void should_return_403_when_no_item_to_check_out() {
        // given
        Cart cart = cartRepository.save(new Cart());

        // when
        ResponseEntity<String> response = this.restTemplate.getForEntity("/check-out", String.class);

        // then
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Category(SlowTests.class)
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
        assertThat(jsonCurrentCart).isEqualTo(expectedBody);
    }

    private String getResponseAsPrettyJson(String uri) throws IOException {
        String responseBody = this.restTemplate.getForObject(uri, String.class);
        return this.formatJson(responseBody);
    }

    private String formatJson(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(body, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private HttpEntity<?> buildHttpRequestWithId(Integer id) {
        HttpHeaders requestHeaders = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("id", id.toString());
        return new HttpEntity<Object>(body, requestHeaders);
    }
}
