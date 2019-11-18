package org.gx.simplewiki.controller;

import org.gx.simplewiki.controller.result.EntryInfo;
import org.gx.simplewiki.controller.result.Result;
import org.gx.simplewiki.crud.EntryRepository;
import org.gx.simplewiki.crud.UserRepository;
import org.gx.simplewiki.exception.*;
import org.gx.simplewiki.exception.entry.EntryNoneFound;
import org.gx.simplewiki.model.Entry;
import org.gx.simplewiki.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/entry")
public class EntryController {

    @Autowired
    EntryRepository entryRepository;
    @Autowired
    UserRepository userRepository;

    // 创建词条操作
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result createEntry(@RequestParam(value = "entry_title") String entryTitle) {
        Result result = new Result();

        if (entryTitle.isEmpty() || entryTitle.length() > 255) {
            result.except(new WikiExceptionMsg(Expt.ENTRY_TITLE_LENGTH_ERR, "新建词条标题为空或过长"));
            return result;
        }

        try {
            try {
                entryRepository.getByTitle(entryTitle);
                throw new WikiException(Expt.ENTRY_TITLE_REPEAT, "词条标题冲突");
            } catch (EntryNoneFound e) {
            }

            User creater = userRepository.getCurUser();
            Entry entry = new Entry(entryTitle, creater);
            entryRepository.update(entry);
            result.success(entry);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result getEntryList() {
        Result result = new Result();
        try {
            List<Entry> list = entryRepository.getList();
            List<EntryInfo> ret_list = new LinkedList<EntryInfo>();
            for (Entry term : list) {
                ret_list.add(new EntryInfo(term));
            }

            result.success(ret_list);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    @RequestMapping(value = "/add/page", method = RequestMethod.POST)
    @ResponseBody
    public Result addPage(@RequestParam(value = "entry_title") String entryTitle, @RequestParam(value = "page_title") String pageTitle, @RequestParam(value = "content") String content) {
        Result result = new Result();

        if (entryTitle.isEmpty() || entryTitle.length() > 255) {
            result.except(new WikiExceptionMsg(Expt.ENTRY_TITLE_LENGTH_ERR, "词条标题为空或过长"));
            return result;
        }

        if (pageTitle.isEmpty() || pageTitle.length() > 255) {
            result.except(new WikiExceptionMsg(Expt.PAGE_TITLE_LENGTH_ERR, "短文标题为空或过长"));
            return result;
        }

        try {
            User user = userRepository.getCurUser();
            Entry entry = entryRepository.getByTitle(entryTitle);

            if (entry.getCreater() != null && !entry.getCreater().getId().equals(user.getId())) {
                throw new NonePermission();
            }

            entry.insertNewPage(-1, pageTitle, content, user);
            entryRepository.update(entry);
            result.success(entry);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }


    @RequestMapping(value = "/edit/title", method = RequestMethod.POST)
    @ResponseBody
    public Result editEntryTitle(@RequestParam(value = "entry_title") String entryTitle, @RequestParam(value = "new_title") String newTitle) {
        Result result = new Result();

        if (newTitle.isEmpty() || newTitle.length() > 255) {
            result.except(new WikiExceptionMsg(Expt.ENTRY_TITLE_LENGTH_ERR, "词条标题长度错误。"));
            return result;
        }

        try {
            Entry entry = entryRepository.getByTitle(entryTitle);
            try {
                entryRepository.getByTitle(newTitle);
                throw new WikiException(Expt.ENTRY_TITLE_REPEAT, "词条标题冲突！");
            } catch (EntryNoneFound e) {
            }
            User user = userRepository.getCurUser();
            if (entry.getCreater() != null && !entry.getCreater().getId().equals(user.getId()))
                throw new NonePermission();

            entry.setTitle(newTitle);
            entryRepository.update(entry);
            result.success(entry);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    @RequestMapping(value = "/edit/index", method = RequestMethod.POST)
    @ResponseBody
    public Result editPageIndex(@RequestParam(value = "entry_title") String entryTitle, @RequestParam(value = "page_id") String pageId, @RequestParam(value = "index") int index) {
        Result result = new Result();
        try {
            Entry entry = entryRepository.getByTitle(entryTitle);
            User user = userRepository.getCurUser();
            if (entry.getCreater() != null && !entry.getCreater().getId().equals(user.getId()))
                throw new NonePermission();

            entry.changePageIndex(pageId, index);
            entryRepository.update(entry);
            result.success(entry);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

}
