/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forwardit.control;

import br.com.forwardit.dao.ProductDAO;
import br.com.forwardit.infra.FileSaver;
import br.com.forwardit.model.BookType;
import br.com.forwardit.model.Product;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author mauri
 */
@Controller
@Transactional
@RequestMapping("/produtos")
public class ProductsController {

    @Autowired
    private ProductDAO productDao;

    @Autowired
    private FileSaver fileSaver;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@RequestParam("summary") MultipartFile summary, 
            @Validated Product product, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return form(product);
        }
        
        if (summary != null) {
            String webPath = fileSaver.write(summary);
            product.setSummaryPath(webPath);
        }

        productDao.save(product);
        redirectAttributes.addFlashAttribute("sucesso",
                "Produto cadastrado com sucesso");
        return new ModelAndView("redirect:produtos");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("products/list");
        modelAndView.addObject("products", productDao.list());
        return modelAndView;
    }

    @RequestMapping("/form")
    public ModelAndView form(Product product) {
        ModelAndView modelAndView = new ModelAndView("products/form");
        modelAndView.addObject("types", BookType.values());
        return modelAndView;
    }
    
    @RequestMapping("/{id}")
    public ModelAndView show(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView("products/show");
        Product product = productDao.find(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }
}
