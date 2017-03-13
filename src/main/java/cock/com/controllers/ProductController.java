package cock.com.controllers;

import cock.com.domain.Product;
import cock.com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "products/men-wallet", method = RequestMethod.GET)
    public String listMenWallet(Model model){
        model.addAttribute("products", productService.listAllProductsByType(1));
        return "category";
    }
    @RequestMapping(value = "products/men-bag", method = RequestMethod.GET)
    public String listMenBag(Model model){
        model.addAttribute("products", productService.listAllProductsByType(2));
        return "category";
    }
    @RequestMapping(value = "products/women-wallet", method = RequestMethod.GET)
    public String listWomenWallet(Model model){
        model.addAttribute("products", productService.listAllProductsByType(3));
        return "category";
    }
    @RequestMapping(value = "products/women-bag", method = RequestMethod.GET)
    public String listWomenBag(Model model){
        model.addAttribute("products", productService.listAllProductsByType(4));
        return "category";
    }

    @RequestMapping("product/{id}")
    public String showProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "product-detail";
    }

}
