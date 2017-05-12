package cock.com.controllers;

import cock.com.Category;
import cock.com.domain.Product;
import cock.com.domain.ProductRelation;
import cock.com.services.BarnerService;
import cock.com.services.ProductRelationService;
import cock.com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    private ProductRelationService productRelationService;
    private BarnerService barnerService;
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setProductRelationService(ProductRelationService productRelationService) {
        this.productRelationService = productRelationService;
    }
    @Autowired
    public void setBarnerService(BarnerService barnerService) {
        this.barnerService = barnerService;
    }
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
    List<Category> cal(Model model, List<Product> products, List<Category> categories){
        if(categories == null)
            categories = new ArrayList<Category>();
        if (products.size() > 4){
            //List<Product> t = new ArrayList<Product>();
            //t = products;
            List<Product> result = new ArrayList<Product>(4);
            for(int i = 0; i < 4; i ++){
                if(products.size() < 4) {
                    result.add(products.get(0));
                    products.remove(products.get(0));
                }
                else {
                    result.add(products.get(i));
                    products.remove(products.get(i));
                }

            }
            Category c = new Category();
            c.setProductList(result);
            categories.add(c);
            cal(model,products, categories);
        }else {
            Category c = new Category();
            c.setProductList(products);
            categories.add(c);
        }
       return categories;
    }
    @RequestMapping(value = "/products/men-wallet", method = RequestMethod.GET)
    public String listMenWallet(Model model){
        load(model);
        List<Product> products = (List<Product>) productService.listAllProductsByType((byte)1);
        List<Category> categories = cal(model, products, new ArrayList<Category>());
        model.addAttribute("products", categories);
        return "category1";
    }
    @RequestMapping(value = "/products/women-wallet", method = RequestMethod.GET)
    public String listWomenWallet(Model model){
        load(model);
        List<Product> products = (List<Product>)productService.listAllProductsByType((byte)2);
        List<Category> categories = cal(model, products, new ArrayList<Category>());
        model.addAttribute("products", categories);
        return "category1";
    }
    @RequestMapping(value = "/products/passport-wallet", method = RequestMethod.GET)
    public String listPassWallet(Model model){
        load(model);
        List<Product> products = (List<Product>)productService.listAllProductsByType((byte)3);
        List<Category> categories = cal(model, products, new ArrayList<Category>());
        model.addAttribute("products", categories);
        return "category1";
    }
    @RequestMapping(value = "/products/men-bag", method = RequestMethod.GET)
    public String listMenBag(Model model){
        load(model);
        List<Product> products = (List<Product>)productService.listAllProductsByType((byte)4);
        List<Category> categories = cal(model, products, new ArrayList<Category>());
        model.addAttribute("products", categories);
        return "category1";
    }

    @RequestMapping(value = "/products/women-bag", method = RequestMethod.GET)
    public String listWomenBag(Model model){
        load(model);
        List<Product> products = (List<Product>)productService.listAllProductsByType((byte)5);
        List<Category> categories = cal(model, products, new ArrayList<Category>());
        model.addAttribute("products", categories);
        return "category1";
    }
    @RequestMapping(value = "/products/men-belt", method = RequestMethod.GET)
    public String listMenBelt(Model model){
        load(model);
        List<Product> products = (List<Product>) productService.listAllProductsByType((byte)6);
        List<Category> categories = cal(model, products, new ArrayList<Category>());
        model.addAttribute("products", categories);
        return "category1";
    }
    @RequestMapping(value = "/products/women-belt", method = RequestMethod.GET)
    public String listWomenBelt(Model model){
        load(model);
        List<Product> products = (List<Product>)productService.listAllProductsByType((byte)7);
        List<Category> categories = cal(model, products, new ArrayList<Category>());
        model.addAttribute("products", categories);
        return "category1";
    }

    @RequestMapping("/product/{id}")
    public String showProduct(@PathVariable Long id, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        Product product = productService.getProductById(id);
        model.addAttribute("baners", barnerService.listBannerActive());
        model.addAttribute("product", product);
        model.addAttribute("p", product);
        model.addAttribute("n", product);
        List<Product> menW = (List<Product>) productService.listMenWalletProducts();
        model.addAttribute("menWallets", menW);
        List<Product> womenW = (List<Product>) productService.listWomenWalletProducts();
        model.addAttribute("womenWallets", womenW);
        List<Product> pass = (List<Product>) productService.listPasspostWalletProducts();
        model.addAttribute("passports", pass);
        List<Product> menB = (List<Product>) productService.listMenBagProducts();
        model.addAttribute("menBags", menB);
        List<Product> womenB = (List<Product>) productService.listWomenBagProducts();
        model.addAttribute("womenBags", womenB);
        List<Product> menBe = (List<Product>) productService.listMenBeltProducts();
        model.addAttribute("menBelts", menBe);
        List<Product> womenBe = (List<Product>) productService.listWomenBeltProducts();
        model.addAttribute("womenBelts", womenBe);
        if(product.getType() == 1){
            model.addAttribute("relationsP", menW);
        }
        if(product.getType() == 2){
            model.addAttribute("relationsP", womenW);
        }
        if(product.getType() == 3){
            model.addAttribute("relationsP", pass);
        }
        if(product.getType() == 4){
            model.addAttribute("relationsP", menB);
        }
        if(product.getType() == 5){
            model.addAttribute("relationsP", womenB);
        }
        if(product.getType() == 6){
            model.addAttribute("relationsP", menBe);
        }
        if(product.getType() == 7){
            model.addAttribute("relationsP", womenBe);
        }
        List<ProductRelation> productRelations= productRelationService.listActiveProductRelation(id, (byte) 1);
        model.addAttribute("relations", productRelations);
        return "product-detail1";
    }

}
