package cock.com.controllers;

import cock.com.services.BarnerService;
import cock.com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private BarnerService barnerService;
    private ProductService productService;
    @Autowired
    public void setBarnerService(BarnerService barnerService) {
        this.barnerService = barnerService;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Model model){

        model.addAttribute("baner", productService.listBarnerProducts());
        List<Integer> countProduct = new ArrayList<Integer>(3);
        countProduct.add(2);
        countProduct.add(3);
        countProduct.add(4);
        model.addAttribute("countProduct", countProduct);

        model.addAttribute("wallets", productService.listWalletProducts());
        model.addAttribute("bags", productService.listBagProducts());
        model.addAttribute("belts", productService.listBeltProducts());
        return "index";
    }
}
