package org.gx.simplewiki.controller;

import org.gx.simplewiki.controller.result.EntryInfoWP;
import org.gx.simplewiki.controller.result.PageInfoWC;
import org.gx.simplewiki.crud.EntryRepository;
import org.gx.simplewiki.crud.PageRepository;
import org.gx.simplewiki.crud.UserRepository;
import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.exception.WikiException;
import org.gx.simplewiki.model.Entry;
import org.gx.simplewiki.model.Page;
import org.gx.simplewiki.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    PageRepository pageRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Locale locale, Model model) {
        try {
            List<Entry> entries = entryRepository.getList();
            model.addAttribute("list", entries);
            /*if (entries != null) {
                List<EntryInfo> ret_list = new LinkedList<EntryInfo>();
                for (Entry term : entries) {
                    ret_list.add(new EntryInfo(term));
                }

                model.addAttribute("list", ret_list);
            }*/
        } catch (DataBaseError e) {
        }

        return "/index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupView() {
        return "/signup";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signinView() {
        return "/login";
    }

    @Transactional
    @RequestMapping(value = "/editEntry/{entry_title}", method = RequestMethod.GET)
    public String editEntry(@PathVariable("entry_title") String entryTitle, Model model) {
        try {
            Entry entry = entryRepository.getByTitle(entryTitle);
            EntryInfoWP entryInfoWP = new EntryInfoWP(entry);
            model.addAttribute("entry", entryInfoWP);
            return "/editTitle";
        } catch (WikiException e) {
            return "redirect: /";
        }

    }

    @RequestMapping(value = "/addPage/{entry_title}", method = RequestMethod.GET)
    public String addPage(@PathVariable("entry_title") String entryTitle, Model model) {
        try {
            Entry entry = entryRepository.getByTitle(entryTitle);
            model.addAttribute("entry_id", entry.getId());
            model.addAttribute("entry_title", entry.getTitle());
            return "/addPage";
        } catch (WikiException e) {
            return "redirect: /";
        }
    }

    @RequestMapping(value = "/wiki/{entry_title}")
    public String wiki(@PathVariable("entry_title") String entryTitle, Model model) {
        try {
            Entry entry = entryRepository.getByTitle(entryTitle);
            model.addAttribute("entry_id", entry.getId());
            model.addAttribute("entry_title", entry.getTitle());
            model.addAttribute("pages", entry.getPages());
            return "/articleDetail";
        } catch (WikiException e) {
            return "redirect: /";
        }
    }

    // 个人中心
    @Transactional
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String userHome(Model model) {
        User user = new User("", "");
        try {
            user = userRepository.getCurUser();
        } catch (WikiException e) {
        }

        if (user != null) {
            model.addAttribute("user", user);
            List<Entry> list = user.getEntrys();
//            model.addAttribute("entrys", list);
            if (list != null) {
                List<EntryInfoWP> ret_list = new LinkedList<EntryInfoWP>();
                for (Entry term : list) {
                    ret_list.add(new EntryInfoWP(term));
                }
                model.addAttribute("entrys", ret_list);
            }
        }

        return "/home";
    }

    // 修改密码
    @RequestMapping(value = "/chgpwd", method = RequestMethod.GET)
    public String changePassword(Model model) {
        User user = new User("", "");
        try {
            user = userRepository.getCurUser();
        } catch (WikiException e) {
        }

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "/editPassword";
    }

    @RequestMapping(value = "/chgname", method = RequestMethod.GET)
    public String changeUserName(Model model) {
        User user = new User("", "");
        try {
            user = userRepository.getCurUser();
        } catch (WikiException e) {
        }

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "/editUserName";
    }

    @RequestMapping(value = "/view/header", method = RequestMethod.GET)
    public String headerView() {
        return "/header";
    }

    @Transactional
    @RequestMapping(value = "/editPage", method = RequestMethod.GET)
    public String editPage(@RequestParam(value = "page_id") String pageId, Model model) {
        try {
            Page page = pageRepository.getPage(pageId);
            PageInfoWC pageInfoWC = new PageInfoWC(page);
            model.addAttribute("page", pageInfoWC);
            return "/editArticle";
        } catch (WikiException e) {
            return "redirect: /";
        }
    }

}
