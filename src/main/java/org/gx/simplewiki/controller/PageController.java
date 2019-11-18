package org.gx.simplewiki.controller;

import org.gx.simplewiki.controller.result.Result;
import org.gx.simplewiki.crud.EntryRepository;
import org.gx.simplewiki.crud.PageRepository;
import org.gx.simplewiki.crud.UserRepository;
import org.gx.simplewiki.exception.*;
import org.gx.simplewiki.model.Page;
import org.gx.simplewiki.model.Revision;
import org.gx.simplewiki.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    EntryRepository entryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PageRepository pageRepository;

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result editContent(@RequestParam(value = "page_id") String pageId, @RequestParam(value = "title") String title, @RequestParam(value = "content") String content) {
        Result result = new Result();
        try {
            User user = userRepository.getCurUser();
            Page page = pageRepository.getPage(pageId);
            if (page.getCreater() != null && !user.getId().equals(page.getCreater().getId())) {
                page.addRevision(new Revision(user, content));
            } else {
                page.setTitle(title);
                page.setContent(content);
            }

            pageRepository.update(page);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

}
