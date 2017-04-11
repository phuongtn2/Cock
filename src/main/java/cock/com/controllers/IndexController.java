package cock.com.controllers;

import cock.com.domain.Barner;
import cock.com.domain.Product;
import cock.com.domain.ProductRelation;
import cock.com.services.BarnerService;
import cock.com.services.ProductRelationService;
import cock.com.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private BarnerService barnerService;
    private ProductService productService;
    private ProductRelationService productRelationService;
    @Autowired
    public void setBarnerService(BarnerService barnerService) {
        this.barnerService = barnerService;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setProductRelationService(ProductRelationService productRelationService) {
        this.productRelationService = productRelationService;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Model model){
        model.addAttribute("baners", barnerService.listBannerActive());
        model.addAttribute("menWallets", productService.listMenWalletProducts());
        model.addAttribute("womenWallets", productService.listWomenWalletProducts());
        model.addAttribute("passposts", productService.listPasspostWalletProducts());
        model.addAttribute("menBags", productService.listMenBagProducts());
        model.addAttribute("womenBags", productService.listWomenBagProducts());
        model.addAttribute("menBelts", productService.listMenBeltProducts());
        model.addAttribute("womenBelts", productService.listWomenBeltProducts());
        return "index";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    String admin(Model model){
        model.addAttribute("products", productService.listAllProduct());
        return "admin";
    }
    @RequestMapping(value = "/baners", method = RequestMethod.GET)
    String baners(Model model){
        model.addAttribute("baners", barnerService.listAllBarner());
        return "baners";
    }
    @RequestMapping(value = "/baner/edit/{id}", method = RequestMethod.GET)
    String editBaner(@PathVariable Integer id, Model model){
        model.addAttribute("baner", barnerService.getBarnerById(id));
        return "baner-form";
    }
    @RequestMapping("/baner/add")
    public String newBaner(Model model){
        model.addAttribute("baner", new Barner());
        return "baner-form";
    }
    @RequestMapping(value = "/baner/save", produces = "application/json", consumes = "multipart/form-data", headers = "Content-Type= multipart/related", method = RequestMethod.POST)
    public String saveBaner(@RequestParam("image") MultipartFile multipartFile, @RequestParam("status") byte status,
                              @RequestParam("active") Integer active,
                              @RequestParam("tempImage") String tempImage,
                              @RequestParam("id") Integer id){
        //save image to local
        Barner barner = new Barner();
        if(id != null && id > 0){
            barner.setId(id);
        }
        try {
            String fileName = multipartFile.getOriginalFilename();
            barner.setStatus(status);
            barner.setActive(active);
            if(!StringUtils.isEmpty(fileName)) {
                Resource resource = new ClassPathResource("static/images/path.jpg");
                String path = resource.getURI().getPath();
                OutputStream outputStream = new FileOutputStream(path.replaceAll("path.jpg", "") + "slide/" + fileName);
                int bufferSize = 256;
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, bufferSize);
                bufferedOutputStream.write(multipartFile.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                outputStream.close();
                String localPath = "../../images/slide/" + fileName;
                barner.setImage(localPath);
            }else{
                barner.setImage(tempImage);
            }
            barnerService.saveBaner(barner);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return "redirect:/baners";
    }

    @RequestMapping(value = "/product/edit/{id}", method = RequestMethod.GET)
    String editProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "product-form";
    }
    @RequestMapping(value = "/relation/edit/{id}", method = RequestMethod.GET)
    String editProductRelation(@PathVariable Long id, Model model){
        model.addAttribute("relation", productRelationService.getById(id));
        return "relation-form";
    }
    @RequestMapping(value = "/relation/{id}", method = RequestMethod.GET)
    String viewProductRelation(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("productRelations", productRelationService.listAllByProductId(id));
        return "product-relation";
    }
    @RequestMapping("/product/add")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "product-form";
    }
    @RequestMapping("/relation/add/{id}")
    public String newProductRelation(@PathVariable Long id, Model model){
        ProductRelation productRelation = new ProductRelation();
        productRelation.setProductId(id);
        model.addAttribute("relation", productRelation);
        return "relation-form";
    }
    @RequestMapping(value = "/product/save", produces = "application/json", consumes = "multipart/form-data", headers = "Content-Type= multipart/related", method = RequestMethod.POST)
    public String saveProduct(@RequestParam("image") MultipartFile multipartFile,
                              @RequestParam("status") byte status,
                              @RequestParam("tempImage") String tempImage,
                              @RequestParam("productId") Long id,
                              @RequestParam("productName") String productName,
                              @RequestParam("productCode") String productCode,
                              @RequestParam("memo") String memo,
                              @RequestParam("price") String price,
                              @RequestParam("sized") String sized,
                              @RequestParam("color") String color,
                              @RequestParam("type") byte type){
        //save image to local
        Product product = new Product();
        if(id != null && id > 0){
            product.setProductId(id);
        }
        try {
            String fileName = multipartFile.getOriginalFilename();
            product.setStatus(status);
            product.setMemo(memo);
            product.setColor(color);
            product.setPrice(price);
            product.setProductName(productName);
            product.setProductCode(productCode);
            product.setSized(sized);
            product.setType(type);
            if(!StringUtils.isEmpty(fileName)) {
                Resource resource = new ClassPathResource("static/images/path.jpg");
                String path = resource.getURI().getPath();
                //Create folder
                File files = new File(path + productCode);
                if (!files.exists()) {
                    if (files.mkdirs()) {
                        System.out.println("Multiple directories are created!");
                    } else {
                        System.out.println("Failed to create multiple directories!");
                    }
                }
                String pathType = "";
                if(type == 1)
                    pathType = "men_wallet/";
                if(type == 2)
                    pathType = "womem_wallet/";
                if(type == 3)
                    pathType = "passpost/";
                if(type == 4)
                    pathType = "mem_bag/";
                if(type == 5)
                    pathType = "women_bag/";
                if(type == 6)
                    pathType = "mem_belt/";
                if(type == 7)
                    pathType = "women_belt/";
                OutputStream outputStream = new FileOutputStream(path.replaceAll("path.jpg", "") + pathType + productCode + "/" + fileName);
                int bufferSize = 256;
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, bufferSize);
                bufferedOutputStream.write(multipartFile.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                outputStream.close();
                String localPath = "../../images/" + pathType + productCode + "/" + fileName;
                product.setImage(localPath);
            }else{
                product.setImage(tempImage);
            }
            productService.saveProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/relation/save", produces = "application/json", consumes = "multipart/form-data", headers = "Content-Type= multipart/related", method = RequestMethod.POST)
    public String saveRelation(@RequestParam("image") MultipartFile multipartFile, @RequestParam("status") byte status,
                            @RequestParam("tempImage") String tempImage,
                            @RequestParam("productId") long productId,
                               @RequestParam("productRelationId") Long productRelationId){
        //save image to local
        ProductRelation productRelation = new ProductRelation();
        if(productRelationId != null && productRelationId > 0){
            productRelation.setProductRelationId(productRelationId);
        }
        try {
            String fileName = multipartFile.getOriginalFilename();
            productRelation.setStatus(status);
            productRelation.setProductId(productId);
            if(!StringUtils.isEmpty(fileName)) {
                Resource resource = new ClassPathResource("static/images/path.jpg");
                String path = resource.getURI().getPath();
                //Create folder
                File files = new File(path + productId);
                if (!files.exists()) {
                    if (files.mkdirs()) {
                        System.out.println("Multiple directories are created!");
                    } else {
                        System.out.println("Failed to create multiple directories!");
                    }
                }
                OutputStream outputStream = new FileOutputStream(path.replaceAll("path.jpg", "") + "relation/" + productId + "/" + fileName);
                int bufferSize = 256;
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, bufferSize);
                bufferedOutputStream.write(multipartFile.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                outputStream.close();
                String localPath = "../../images/relation/" + productId + "/" + fileName;
                productRelation.setImage(localPath);
            }else{
                productRelation.setImage(tempImage);
            }
            productRelationService.saveProduct(productRelation);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return "redirect:/relation/" + productId;
    }
}
