package controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.IProductService;
import service.impl.ProductService;

import java.util.ArrayList;

@Controller
@RequestMapping("/product")
public class HomeController {
    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ModelAndView showProduct() {
        ModelAndView modelAndView = new ModelAndView("list");
        ArrayList<Product> products = productService.getAllProduct();
        if (products.isEmpty()) {
            modelAndView.addObject("message","No Product");
            modelAndView.addObject("color", "red");
        }
        modelAndView.addObject("products",products);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("list");
        Product product = productService.deleteProduct(id);
        if (product == null) {
            modelAndView.addObject("message", "Id Invalid");
            modelAndView.addObject("color", "red");
        }
        ArrayList<Product> products = productService.getAllProduct();
        modelAndView.addObject("products",products);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showDetail(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Product product = productService.getProduct(id);
        if (product == null) {
            modelAndView.addObject("message", "ID Invalid");
        } else {
            modelAndView.addObject("product", product);
        }
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProduct(Model model){
        ModelAndView modelAndView = new ModelAndView("create");
        model.addAttribute("product",new Product());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@ModelAttribute Product product){
        ModelAndView modelAndView = new ModelAndView("create");
        Product product1 = productService.saveProduct(product);
        if (product1 != null) {
            modelAndView.addObject("message","create successfully!");
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = productService.getProduct(id);
        if (product != null) {
            modelAndView.addObject("product",product);
        } else {
            modelAndView.addObject("message", "ID Invalid");
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView edit(@ModelAttribute Product product, @PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("edit");
        product.setId(id);
        Product product1 = productService.saveProduct(product);
        if (product1 != null) {
            modelAndView.addObject("message", "Update Successfully");
        }
        return modelAndView;
    }
}
