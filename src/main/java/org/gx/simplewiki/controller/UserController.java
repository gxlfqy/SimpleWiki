package org.gx.simplewiki.controller;

import net.sf.json.JSONObject;
import org.gx.simplewiki.controller.result.Result;
import org.gx.simplewiki.crud.UserRepository;
import org.gx.simplewiki.crud.hibernate.HibernateUserRepository;
import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.NonePermission;
import org.gx.simplewiki.exception.WikiException;
import org.gx.simplewiki.exception.WikiExceptionMsg;
import org.gx.simplewiki.exception.user.AccessFailure;
import org.gx.simplewiki.exception.user.SigninInfoError;
import org.gx.simplewiki.exception.user.UserNoneFound;
import org.gx.simplewiki.model.Access;
import org.gx.simplewiki.model.User;
import org.gx.simplewiki.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // 获取用户信息
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserInfo() {
        Result result = new Result();
        try {
            User user = userRepository.getCurUser();
            JSONObject jsonObject = JSONObject.fromObject(user);
            jsonObject.put("info", user.getInfo());
            result.success(jsonObject);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    // 获取用户所有编辑的词条
    @RequestMapping(value = "/entrys", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserEntrys() {
        Result result = new Result();
        try {
            User user = userRepository.getCurUser();
            result.success(user.getEntrys());
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    // 获取用户所有的修改
    @RequestMapping(value = "/revisions", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserRevisions() {
        Result result = new Result();
        try {
            User user = userRepository.getCurUser();
            result.success(user.getRevisions());
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    // 注册
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public Result signup(@RequestParam(value = "user_name") String userName, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email) {
        Result result = new Result();

        try {
            if (userName.isEmpty() || userName.length() > 64) {
                throw new WikiException(Expt.USER_NAME_LENGTH_ERR, "用户名为空或超过64个字符。");
            }

            try {
                userRepository.getUser(userName);
                throw new WikiException(Expt.USER_NAME_REPEAT, "用户名重复");
            } catch (UserNoneFound e) {
            }

            User user = new User(userName, password);
            UserInfo info = user.getInfo();
            info.setMail(email);
            user.setInfo(info);
            userRepository.update(user);
            result.success(user);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    // 验证码获取
    @RequestMapping(value = "/verifycode", method = {RequestMethod.GET, RequestMethod.POST})
    public void VerifyCode(HttpServletRequest request, HttpServletResponse response) {
        // 创建一个宽100,高50,且不带透明色的image对象 100 50
        BufferedImage bi = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        //RGB色彩
        Color c = new Color(200, 150, 255);
        // 框中的背景色
        g.setColor(c);
        // 颜色填充像素
        g.fillRect(0, 0, 100, 50);
        // 定义验证码字符数组
        char[] ch = "ABCDEFGHIJKLMNPQRSTUVWXYZ0123456798".toCharArray();
        Random r = new Random();
        int len = ch.length;
        int index;
        StringBuffer sb = new StringBuffer();
        // 取出四个数字
        for (int i = 0; i < 4; i++) {
            // 循环四次随机取长度定义为索引
            index = r.nextInt(len);
            g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
            Font font = new Font("Times New Roman", Font.ITALIC, 18);
            g.setFont(font);
            g.drawString(ch[index] + "", (i * 18) + 10, 30);
            sb.append(ch[index]);
        }
        //放入Session中
        request.getSession().setAttribute("piccode", sb.toString());
        try {
            ImageIO.write(bi, "JPG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/access", method = RequestMethod.POST)
    @ResponseBody
    public Result access(@RequestParam(value = "user_id") String userId, @RequestParam(value = "access_id") String accessId) {
        Result result = new Result();
        try {
            userRepository.extendAccess(userId, accessId);
            try {
                Authentication authentication = new UsernamePasswordAuthenticationToken(userId, accessId);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException e) {
                throw new AccessFailure();
            }

            result.success(null);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    // 登录
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public Result sigin(HttpServletRequest request, @RequestParam("user_name") String userName, @RequestParam("password") String password, @RequestParam("verifycode") String verifycode, Model model) {
        String piccode = (String) request.getSession().getAttribute("piccode");
        Result result = new Result();
        if (piccode.equals(verifycode)) {
            try {
                User user;
                try {
                    user = userRepository.getUser(userName);
                } catch (UserNoneFound e) {
                    throw new SigninInfoError();
                }

                if (!user.getPassword().equals(password))
                    throw new SigninInfoError();

                Access access = userRepository.createAccess(user);
                try {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(access.getUser().getId(), access.getId());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (AuthenticationException e) {
                    throw new AccessFailure();
                }
                result.success(access);
            } catch (WikiException e) {
                result.except(e.getWikiExceptionMsg());
            }
        }
        else {
            result.except(new WikiExceptionMsg(Expt.VALIDCODE_ERROR, "验证码错误"));
        }

        // 如果登录信息错误，销毁
        if (!result.isSucceed())
            request.getSession().removeAttribute("piccode");

        return result;
    }

    // 登出
    @RequestMapping(value = "/signout", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Result signout(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            result.success("注销成功");
        } else {
            result.except(new WikiExceptionMsg(Expt.SIGNOUT_FAILURE, "注销失败！"));
        }

        return result;
    }

    // 查找用户
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public Result findUser(@RequestParam(value = "user_name") String userName) {
        Result result = new Result();
        try {
            User user = userRepository.getUser(userName);
            result.success(user);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    @RequestMapping(value = "/change/password")
    @ResponseBody
    public Result changePassword(@RequestParam("password") String password, @RequestParam(value = "new_password") String newPassword) {
        Result result = new Result();
        if (password.isEmpty() || password.length() > 256) {
            result.except(new WikiExceptionMsg(Expt.PASSWORD_LENGTH_ERR, "原密码长度错误"));
            return result;
        }

        if (newPassword.isEmpty() || newPassword.length() > 256) {
            result.except(new WikiExceptionMsg(Expt.PASSWORD_LENGTH_ERR, "新密码长度错误"));
            return result;
        }

        try {
            User user = userRepository.getCurUser();
            if (!user.getPassword().equals(password)) {
                throw new NonePermission();
            }

            user.setPassword(newPassword);
            user.accesses.clear();
            userRepository.update(user);
            result.success(null);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }

    @RequestMapping(value = "/change/name")
    @ResponseBody
    public Result changeUserName(@RequestParam("password") String password, @RequestParam(value = "user_name") String userName) {
        Result result = new Result();

        if (password.isEmpty() || password.length() > 256) {
            result.except(new WikiExceptionMsg(Expt.PASSWORD_LENGTH_ERR, "原密码长度错误"));
            return result;
        }

        if (userName.isEmpty() || userName.length() > 64) {
            result.except(new WikiExceptionMsg(Expt.PASSWORD_LENGTH_ERR, "用户名长度错误"));
            return result;
        }

        if (HibernateUserRepository.isSpecialChar(userName)) {
            result.except(new WikiExceptionMsg(Expt.USER_NAME_INVALID_CHAR, "用户名含非法字符"));
            return result;
        }

        try {
            User user = userRepository.getCurUser();
            if (!user.getPassword().equals(password)) {
                throw new NonePermission();
            }

            user.setName(userName);
            userRepository.update(user);
            result.success(null);
        } catch (WikiException e) {
            result.except(e.getWikiExceptionMsg());
        }

        return result;
    }
}
