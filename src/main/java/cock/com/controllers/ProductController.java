package cock.com.controllers;

import cock.com.domain.Product;
import cock.com.domain.ProductRelation;
import cock.com.services.ProductRelationService;
import cock.com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    private ProductRelationService productRelationService;
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setProductRelationService(ProductRelationService productRelationService) {
        this.productRelationService = productRelationService;
    }

    @RequestMapping(value = "products/men-wallet", method = RequestMethod.GET)
    public String listMenWallet(Model model){
        model.addAttribute("products", productService.listAllProductsByType((byte)1));
        return "category";
    }
    @RequestMapping(value = "products/women-wallet", method = RequestMethod.GET)
    public String listWomenWallet(Model model){
        model.addAttribute("products", productService.listAllProductsByType((byte)2));
        return "category";
    }
    @RequestMapping(value = "products/passport-wallet", method = RequestMethod.GET)
    public String listPassWallet(Model model){
        model.addAttribute("products", productService.listAllProductsByType((byte)3));
        return "category";
    }
    @RequestMapping(value = "products/men-bag", method = RequestMethod.GET)
    public String listMenBag(Model model){
        model.addAttribute("products", productService.listAllProductsByType((byte)4));
        return "category";
    }

    @RequestMapping(value = "products/women-bag", method = RequestMethod.GET)
    public String listWomenBag(Model model){
        model.addAttribute("products", productService.listAllProductsByType((byte)5));
        return "category";
    }
    @RequestMapping(value = "products/men-belt", method = RequestMethod.GET)
    public String listMenBelt(Model model){
        model.addAttribute("products", productService.listAllProductsByType((byte)6));
        return "category";
    }
    @RequestMapping(value = "products/women-belt", method = RequestMethod.GET)
    public String listWomenBelt(Model model){
        model.addAttribute("products", productService.listAllProductsByType((byte)7));
        return "category";
    }

    @RequestMapping("product/{id}")
    public String showProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        List<ProductRelation> productRelations= productRelationService.listActiveProductRelation(id, (byte) 1);
        if(productRelations.size() > 3){
            model.addAttribute("relationsActive",productRelations.subList(0,3));
            List<ProductRelation>  productRelations1 = productRelations.subList(3,productRelations.size());
            if(productRelations1.size() > 3){
                model.addAttribute("relations1",productRelations1.subList(0,3));
                List<ProductRelation>  productRelations2 = productRelations1.subList(3,productRelations1.size());
                if(productRelations2.size() > 3){
                    model.addAttribute("relations2",productRelations2.subList(0,3));
                }else{
                    model.addAttribute("relations2",productRelations2);
                }
            }else{
                model.addAttribute("relations1",productRelations1);
            }

        }else{
            model.addAttribute("relationsActive",productRelations);
        }
        return "product-detail";
    }

}
