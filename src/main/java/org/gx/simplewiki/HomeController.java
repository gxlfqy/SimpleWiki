package org.gx.simplewiki;

import java.util.*;

import org.gx.simplewiki.crud.UserRepository;
import org.gx.simplewiki.model.User;
import org.pegdown.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests for the application index page.
 */
//@Controller
public class HomeController {
//    @Autowired
//    PageRepository pageRepository;
    @Autowired
    UserRepository userRepository;
    private PasswordEncoder encoder = new ShaPasswordEncoder(256);
    //private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    //임시로 public
    @Autowired
    public Parser parser;
    /**
     * Simply selects the index view to render by returning its name.
     */
   
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
//        return "redirect:/wiki/FrontPage";
//        return "redirect:/resources/static/html/index.jsp";
        return "redirect:/static/html/addPage.jsp?p=FrontPage";
    }


    @RequestMapping(value = "/wiki/{pageTitle}", method = RequestMethod.GET)
    public String home(@PathVariable("pageTitle") String pageTitle, Locale locale, Model model) {
//        Page page = pageRepository.getUser(pageTitle);
//        if(page == null){
//            model.addAttribute("title", pageTitle);
//            return "create";
//        }else{
//        	model.addAttribute("page", page);
//            return "index";
//        }
        return "";
    }
    
    @RequestMapping(value = "/wiki/{pageTitle}", method = {RequestMethod.GET, RequestMethod.POST}, params="action=history")
    public String home(@RequestParam("action") String action, @PathVariable("pageTitle") String pageTitle, Locale locale, Model model) {
//		Page page = pageRepository.getUser(pageTitle);
//		model.addAttribute("page", page);
//    	return "history";
        return "";
    }
    
    @Transactional
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String write(@RequestParam("title") String title, @RequestParam("contents") String contents, Model model) {
        write_page(title, contents);
        return "redirect:/";
    }

    @Transactional
    @RequestMapping(value = "/api/write", method = RequestMethod.POST)
    @ResponseBody
    public String api_write(@RequestParam("title") String title, @RequestParam("contents") String contents, Model model) {
        if (write_page(title, contents))
            return "1";
        else
            return "0";
    }

    @Transactional
    @RequestMapping(value = "/api/page", method = RequestMethod.POST)
    @ResponseBody
    public String api_page(@RequestParam("title") String title, Model model) {
//        Page page = pageRepository.getUser(title);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("succeed", page != null);
//
//        if (page != null)
//            jsonObject.put("page", page);
//
//        return jsonObject.toString();
        return "";
    }

    private boolean write_page(String title, String contents) {
//        Page page = pageRepository.getUser(title);
//
//        if (page == null)
//            return false;
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name;
//        if (principal instanceof UserDetails) {
//            name = ((UserDetails) principal).getUsername();
//        } else {
//            name = principal.toString();
//        }
//        page.suggest(contents, userRepository.getUser(name));
//
//        pageRepository.update(page);
//        return true;
        return false;
    }

    @Transactional
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam("title") String title, Model model){
//    	Page page = new Page(title);
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name;
//        if (principal instanceof UserDetails) {
//            name = ((UserDetails) principal).getUsername();
//           	page.suggest("This is New Page.", userRepository.getUser(name));
//            pageRepository.update(page);
//            model.addAttribute("page", page);
//
//            return "redirect:/wiki/"+title;
//        }
//
//        return "index";
        return "";
    }

    @Transactional
    @RequestMapping(value = "/author/find-user", method = RequestMethod.POST)
    public String finduser()
    {
        return "";
    }

    @Transactional
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public String signout() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String id;
//
//        if (principal instanceof UserDetails) {
//            id = ((UserDetails) principal).getUsername();
//        } else {
//            id = principal.toString();
//        }
//
//        User user = userRepository.getUser(id);
////        user.removeInfo();
//    	userRepository.update(user);
//
//    	return "redirect:/logout";
        return "";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("title") String title) {
//        pageRepository.delete(pageRepository.getUser(title));
//
//        return "redirect:/";
        return "";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("email") String email) {
//        userRepository.update(new User(id, encoder.encodePassword(password, id), email));
//        userRepository.update(new User(id, password, email));


        return "redirect:/";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }
}
