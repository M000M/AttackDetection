package cn.edu.pku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/ajax")
public class AjaxController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/a1", method = RequestMethod.GET)
    public void ajax(@RequestParam("name") String name, HttpServletResponse response) throws IOException {
        if (name.equals("admin")) {
            response.getWriter().print("true");
        } else {
            response.getWriter().print("false");
        }
    }
}
