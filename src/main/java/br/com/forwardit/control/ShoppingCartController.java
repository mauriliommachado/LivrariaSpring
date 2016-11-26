/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forwardit.control;

import br.com.forwardit.dao.ProductDAO;
import br.com.forwardit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shopping")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShoppingCartController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ShoppingCart shoppingCart;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView add(Integer productId, BookType bookType) {
        ShoppingItem item = createItem(productId, bookType);
        shoppingCart.add(item);
        return new ModelAndView("redirect:/produtos");
    }

    private ShoppingItem createItem(Integer productId, BookType bookType) {
        Product product = productDAO.find(productId);
        ShoppingItem item = new ShoppingItem(product, bookType);
        return item;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String items() {
        return "shoppingCart/items";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/${productId}")
    public String remove(@PathVariable("productId") Integer productId, BookType bookType) {
        shoppingCart.remove(createItem(productId, bookType));
        return "redirect:/shopping";
    }
}
