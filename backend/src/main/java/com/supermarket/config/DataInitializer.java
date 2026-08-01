package com.supermarket.config;

import com.supermarket.model.Category;
import com.supermarket.model.Product;
import com.supermarket.model.Supplier;
import com.supermarket.model.User;
import com.supermarket.repository.CategoryRepository;
import com.supermarket.repository.ProductRepository;
import com.supermarket.repository.SupplierRepository;
import com.supermarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize users
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setRole("ADMIN");

            User user = new User();
            user.setUsername("user");
            user.setPassword("123456");
            user.setRole("USER");

            userRepository.saveAll(Arrays.asList(admin, user));
        }

        // Initialize categories
        if (categoryRepository.count() == 0) {
            Category snacks = new Category();
            snacks.setName("零食");
            snacks.setDescription("薯片、糖果、饼干等");

            Category drinks = new Category();
            drinks.setName("饮料");
            drinks.setDescription("矿泉水、可乐、果汁等");

            Category fruit = new Category();
            fruit.setName("水果");
            fruit.setDescription("新鲜水果");

            categoryRepository.saveAll(Arrays.asList(snacks, drinks, fruit));

            Product p1 = new Product();
            p1.setName("乐事薯片");
            p1.setPrice(new BigDecimal("7.5"));
            p1.setStock(100);
            p1.setBarcode("6912345678901");
            p1.setCategory(snacks);

            Product p2 = new Product();
            p2.setName("可口可乐 500ml");
            p2.setPrice(new BigDecimal("3.5"));
            p2.setStock(200);
            p2.setBarcode("6912345678902");
            p2.setCategory(drinks);

            Product p3 = new Product();
            p3.setName("富士苹果 1kg");
            p3.setPrice(new BigDecimal("12.8"));
            p3.setStock(50);
            p3.setBarcode("6912345678903");
            p3.setCategory(fruit);

            productRepository.saveAll(Arrays.asList(p1, p2, p3));
        }

        // Initialize suppliers
        if (supplierRepository.count() == 0) {
            Supplier s1 = new Supplier();
            s1.setName("百事食品有限公司");
            s1.setContact("张经理");
            s1.setPhone("13800138001");
            s1.setAddress("北京市朝阳区");

            Supplier s2 = new Supplier();
            s2.setName("可口可乐饮料公司");
            s2.setContact("李经理");
            s2.setPhone("13800138002");
            s2.setAddress("上海市浦东新区");

            supplierRepository.saveAll(Arrays.asList(s1, s2));
        }

        System.out.println("Demo data initialized.");
    }
}
