package com.luizvictor.course.config;

import com.luizvictor.course.entities.*;
import com.luizvictor.course.entities.invoice.Invoice;
import com.luizvictor.course.entities.invoice.InvoiceStatus;
import com.luizvictor.course.entities.orderItem.InvoiceItem;
import com.luizvictor.course.entities.product.Product;
import com.luizvictor.course.entities.product.dto.ProductDto;
import com.luizvictor.course.entities.user.User;
import com.luizvictor.course.entities.user.dto.UserDto;
import com.luizvictor.course.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    private User createUser(String name, String email, String phone, String password) {
        UserDto userDto = new UserDto(name, email, phone, password);
        return new User(userDto);
    }

    private Product createProduct(String name, String description, String price, Category category) {
        ProductDto productDto = new ProductDto(name, description, new BigDecimal(price), category.getId());
        return new Product(productDto, category);
    }

    @Override
    public void run(String... args) {
        User user1 = createUser("John Doe", "john@email.com", "111-111", "123");
        User user2 = createUser("Joanna Doe", "joanna@email.com", "111-222", "321");

        Category category1 = new Category("Computers");
        Category category2 = new Category("Books");
        Category category3 = new Category("Electronics");

        Product product1 = createProduct("Macbook", "apple notebook", "1000", category1);
        Product product2 = createProduct("Iphone", "apple smartphone", "800", category3);
        Product product3 = createProduct("The man in the high castle", "dystopic book", "30", category2);

        Invoice invoice1 = new Invoice(user1);
        Invoice invoice2 = new Invoice(user2);
        Invoice invoice3 = new Invoice(user1);

        invoice3.updateStatus(InvoiceStatus.CANCELED);

        InvoiceItem invoiceItem1 = new InvoiceItem(2, product3, invoice1);
        InvoiceItem invoiceItem2 = new InvoiceItem(1, product2, invoice1);

        invoice1.addItem(invoiceItem1);
        invoice2.addItem(invoiceItem2);

        userRepository.saveAll(Arrays.asList(user1, user2));
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        productRepository.saveAll(Arrays.asList(product1, product2, product3));
        invoiceRepository.saveAll(Arrays.asList(invoice1, invoice2, invoice3));
        invoiceItemRepository.saveAll(Arrays.asList(invoiceItem1, invoiceItem2));
    }
}
