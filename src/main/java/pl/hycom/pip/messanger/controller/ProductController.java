package pl.hycom.pip.messanger.controller;


import java.util.List;
import javax.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.ProductService;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class ProductController {

    private final ProductService productService;

    @GetMapping("/admin/products")
    public String findAllProducts(Model model) {
        List<Product> allProducts = productService.findAllProducts();
        model.addAttribute("products", allProducts);
        return "products";
    }

    @PostMapping("/admin/products")
    public ModelAndView productsSubmit(

            @RequestParam("name") final String name,
            @RequestParam("description") final String description,
            @RequestParam("imageUrl") final String url,
            @RequestParam("id") final String id) {
        try {
            int temp=Integer.parseInt(id);
            if (temp != 0) {
                productService.updateProduct(temp,name,description,url);
                log.info("Product updated !!!");
            }
            else
            {
                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setImageUrl(url);
                productService.addProduct(product);
                log.info("Product added !!!");
            }
        }
        catch (Exception ex){
            log.error("Error during post request from admin/products"+ex);
        }
        return new ModelAndView("redirect:/admin/products");
    }
}
