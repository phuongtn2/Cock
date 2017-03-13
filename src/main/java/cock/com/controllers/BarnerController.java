/*
package cock.com.controllers;

import cock.com.services.BarnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BarnerController {

    private BarnerService barnerService;

    @Autowired
    public void setBarnerService(BarnerService barnerService) {
        this.barnerService = barnerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("data", barnerService.listAllBarner());
        System.out.println("Returning rpoducts:");
        return "index";
    }
}
*/
