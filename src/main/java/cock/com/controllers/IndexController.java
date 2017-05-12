package cock.com.controllers;

import cock.com.domain.Home;
import cock.com.domain.Product;
import cock.com.services.BarnerService;
import cock.com.services.HomeService;
import cock.com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
public class IndexController {
    private BarnerService barnerService;
    private ProductService productService;
    private HomeService homeService;
    @Autowired
    public void setBarnerService(BarnerService barnerService) {
        this.barnerService = barnerService;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setHomeService(HomeService homeService) {this.homeService = homeService;}
    public void load(Model model){
        model.addAttribute("baners", barnerService.listBannerActive());
        model.addAttribute("menWallets", productService.listMenWalletProducts());
        model.addAttribute("womenWallets", productService.listWomenWalletProducts());
        model.addAttribute("passports", productService.listPasspostWalletProducts());
        model.addAttribute("menBags", productService.listMenBagProducts());
        model.addAttribute("womenBags", productService.listWomenBagProducts());
        model.addAttribute("menBelts", productService.listMenBeltProducts());
        model.addAttribute("womenBelts", productService.listWomenBeltProducts());
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Model model){
        List<Home> homes = (List<Home>) homeService.listHome();
        for (Home home: homes) {
            Product product = productService.getProductById(home.getProductId());
            model.addAttribute("product_" + home.getId(), product);
        }
        load(model);
        return "index1";
    }
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    String contact(Model model){
        load(model);
        return "contact-us1";
    }
    @RequestMapping(value = "/feature", method = RequestMethod.GET)
    String feature(Model model){
        load(model);
        return "feature";
    }
}
