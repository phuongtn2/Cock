package cock.com.controllers;

import cock.com.domain.Barner;
import cock.com.domain.Home;
import cock.com.domain.Product;
import cock.com.domain.ProductRelation;
import cock.com.services.BarnerService;
import cock.com.services.HomeService;
import cock.com.services.ProductRelationService;
import cock.com.services.ProductService;
import org.apache.commons.lang.RandomStringUtils;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class AdminController {
    private BarnerService barnerService;
    private ProductService productService;
    private ProductRelationService productRelationService;
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
    public void setProductRelationService(ProductRelationService productRelationService) {
        this.productRelationService = productRelationService;
    }
    @Autowired
    public  void setHomeService(HomeService homeService){this.homeService = homeService;}
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    String admin(Model model){
        model.addAttribute("products", productService.listAllProduct());
        return "admin";
    }
    @RequestMapping(value = "/admin/baners", method = RequestMethod.GET)
    String baners(Model model){
        model.addAttribute("baners", barnerService.listAllBarner());
        return "baners";
    }
    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    String home(Model model){
        model.addAttribute("homes", homeService.listHome());
        return "home";
    }
    @RequestMapping(value = "/admin/baner/edit/{id}", method = RequestMethod.GET)
    String editBaner(@PathVariable Integer id, Model model){
        model.addAttribute("baner", barnerService.getBarnerById(id));
        return "baner-form";
    }
    @RequestMapping(value = "/admin/home/edit/{id}", method = RequestMethod.GET)
    String editHome(@PathVariable Integer id, Model model){
        model.addAttribute("home", homeService.getHomeById(id));
        model.addAttribute("products", productService.listAllProduct());
        return "home-form";
    }
    @RequestMapping("/admin/baner/add")
    public String newBaner(Model model){
        model.addAttribute("baner", new Barner());
        return "baner-form";
    }
    @RequestMapping("/admin/home/add")
    public String newHome(Model model){
        model.addAttribute("home", new Home());
        model.addAttribute("products", productService.listAllProduct());
        return "home-form";
    }
    @RequestMapping(value = "/admin/baner/save", produces = "application/json", consumes = "multipart/form-data", headers = "Content-Type= multipart/related", method = RequestMethod.POST)
    public void saveBaner(@RequestParam("image") MultipartFile multipartFile, @RequestParam("status") byte status,
                            @RequestParam("active") Integer active,
                            @RequestParam("tempImage") String tempImage,
                            @RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
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
                Resource resource = new ClassPathResource("static/images/path.txt");
                String path = resource.getURI().getPath();
                OutputStream outputStream = new FileOutputStream(path.replaceAll("path.txt", "") + "slide/" + fileName);
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
        response.sendRedirect("/admin/baners");
    }

    @RequestMapping(value = "/admin/product/edit/{id}", method = RequestMethod.GET)
    String editProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "product-form";
    }
    @RequestMapping(value = "/admin/relation/edit/{id}", method = RequestMethod.GET)
    String editProductRelation(@PathVariable Long id, Model model){
        model.addAttribute("relation", productRelationService.getById(id));
        return "relation-form";
    }
    @RequestMapping(value = "/admin/relation/{id}", method = RequestMethod.GET)
    String viewProductRelation(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("productRelations", productRelationService.listAllByProductId(id));
        return "product-relation";
    }
    @RequestMapping("/admin/product/add")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "product-form";
    }
    @RequestMapping("/admin/relation/add/{id}")
    public String newProductRelation(@PathVariable Long id, Model model){
        ProductRelation productRelation = new ProductRelation();
        productRelation.setProductId(id);
        model.addAttribute("relation", productRelation);
        return "relation-form";
    }
    @RequestMapping(value = "/admin/product/save", produces = "application/json", consumes = "multipart/form-data", headers = "Content-Type= multipart/related", method = RequestMethod.POST)
    public void saveProduct(@RequestParam("image") MultipartFile multipartFile,
                              @RequestParam("status") byte status,
                              @RequestParam("tempImage") String tempImage,
                              @RequestParam("productId") Long id,
                              @RequestParam("productName") String productName,
                              @RequestParam("productCode") String productCode,
                              @RequestParam("memo") String memo,
                              @RequestParam("title") String title,
                              @RequestParam("price") String price,
                              @RequestParam("sized") String sized,
                              @RequestParam("color") String color,
                              @RequestParam("type") byte type, HttpServletResponse response) throws IOException {
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
            product.setTitle(title);
            product.setProductName(productName);
            product.setProductCode(productCode);
            product.setSized(sized);
            product.setType(type);
            if(!StringUtils.isEmpty(fileName)) {
                Resource resource = new ClassPathResource("static/images/path.txt");
                String path = resource.getURI().getPath();
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
                String primaryPath = path.replaceAll("path.txt", "") + pathType;
                //Create folder
                /*File files = new File(primaryPath + productCode);
                if (!files.exists()) {
                    if (files.mkdirs()) {
                        System.out.println("Multiple directories are created!");
                    } else {
                        System.out.println("Failed to create multiple directories!");
                    }
                }*/
                String nameGenerate = RandomStringUtils.randomAlphanumeric(10) + "_" + fileName;
                OutputStream outputStream = new FileOutputStream(primaryPath + nameGenerate);
                int bufferSize = 256;
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, bufferSize);
                bufferedOutputStream.write(multipartFile.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                outputStream.close();
                String localPath = "../../images/" + pathType + nameGenerate;
                product.setImage(localPath);
            }else{
                product.setImage(tempImage);
            }
            productService.saveProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        response.sendRedirect("/admin");
    }

    @RequestMapping(value = "/admin/relation/save", produces = "application/json", consumes = "multipart/form-data", headers = "Content-Type= multipart/related", method = RequestMethod.POST)
    public void saveRelation(@RequestParam("image") MultipartFile multipartFile, @RequestParam("status") byte status,
                            @RequestParam("tempImage") String tempImage,
                            @RequestParam("productId") long productId,
                               @RequestParam("productRelationId") Long productRelationId, HttpServletResponse response) throws IOException {
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
                Resource resource = new ClassPathResource("static/images/path.txt");
                String path = resource.getURI().getPath();
                String primaryPath = path.replaceAll("path.txt", "") + "relation/";
                //Create folder
                /*File files = new File(primaryPath + productId);
                if (!files.exists()) {
                    if (files.mkdirs()) {
                        System.out.println("Multiple directories are created!");
                    } else {
                        System.out.println("Failed to create multiple directories!");
                    }
                }*/
                String nameGenerate = RandomStringUtils.randomAlphanumeric(10) + "_" + fileName;
                OutputStream outputStream = new FileOutputStream(primaryPath + nameGenerate);
                int bufferSize = 256;
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, bufferSize);
                bufferedOutputStream.write(multipartFile.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                outputStream.close();
                String localPath = "../../images/relation/" + nameGenerate;
                productRelation.setImage(localPath);
            }else{
                productRelation.setImage(tempImage);
            }
            productRelationService.saveProduct(productRelation);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        response.sendRedirect("/admin/relation/" + productId);
    }

    @RequestMapping(value = "/admin/home/save", method = RequestMethod.POST)
    public void saveHome(@RequestParam("id") Integer id, @RequestParam("productId") Long productId,
                           HttpServletResponse response) throws IOException {
        //save image to local
        Home home = new Home();
        if(id != null && id > 0){
            home.setId(id);
        }
        home.setProductId(productId);
        homeService.saveHome(home);
        response.sendRedirect("/admin/home");
    }
}
