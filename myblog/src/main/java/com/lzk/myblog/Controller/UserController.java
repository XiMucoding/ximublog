package com.lzk.myblog.Controller;

import com.lzk.myblog.Pojo.Result;
import com.lzk.myblog.Pojo.User;
import com.lzk.myblog.Service.userService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userService userService;
    @Autowired
    JavaMailSenderImpl mailSender;
    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public Result login(@RequestParam("userName") String userName, @RequestParam("password") String password){
        User user = userService.findUser(userName, password);
        if(user!=null)
            return Result.succ(user);
        return Result.fail("账号或密码不正确！");
    }

    /**
     * 注册
     * @param userName
     * @param password
     * @param nickName
     * @param code
     * @param s
     * @return
     */
    @RequestMapping("/register")
    public Result register(@RequestParam("userName") String userName, @RequestParam("password") String password,
                           @RequestParam("nickName") String nickName,@RequestParam("code") String code,HttpSession s){
        String session_code = (String)s.getAttribute("code");
        if(session_code ==null)return Result.fail("还没有获取验证码~");
        if(!code.equalsIgnoreCase(session_code))
            //验证码错误
            return Result.fail("验证码输入错误！");
        User user=userService.register(userName,password,nickName);
        //邮箱已被注册
        if(user!=null)return Result.fail("该邮箱已被注册！");
        //注册成功
        return Result.succ(user);
    }

    /**
     * 获得验证码
     * @param s
     * @return
     */
    @RequestMapping("/getCode")
    public Result getCode(@RequestParam("userName") String userName,HttpSession s){
        //生成验证码
        String str="qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        s.setAttribute("code",sb.toString());

        //向用户邮箱发送验证码
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        mailMessage.setSubject("【稀牧博客】");
        mailMessage.setText("欢迎您的注册,验证码:"+sb+"。请勿转发给他人，若是不是本人注册，不理会即可！");
        //发送的对象
        mailMessage.setTo(userName);
        //发件人
        mailMessage.setFrom("204967882@qq.com");
        //发送
        mailSender.send(mailMessage);

        return Result.succ(null);
    }

    /**
     * 查询指定用户
     * @param uid
     * @return
     */
    @RequestMapping("/queryTheUser")
    public Result queryTheUser(int uid){
        User user = userService.queryTheUser(uid);
        if (user!=null)
            return Result.succ(user);
        return Result.fail(null);
    }

    /**
     * 修改信息
     * @param uid
     * @param password
     * @param nickName
     * @return
     */
    @RequestMapping("/updateUser")
    public Result updateUser(@RequestParam("uid")int uid,@RequestParam("password")String password,
                             @RequestParam("nickName")String nickName){
        int i = userService.updateUser(uid, password, nickName);
        if (i>0)return Result.succ(null);
        return Result.fail("修改失败！");
    }

    /**
     * 更换头像
     * @param uid
     * @param avatarUrl
     * @return
     */
    @RequestMapping("/changeAvatar")
    public Result changeAvatar(@RequestParam("uid")int uid,@RequestParam("avatarUrl")String avatarUrl){
        int i=userService.changeAvatar(uid, avatarUrl);
        if (i>0)return Result.succ(null);
        return Result.fail("修改失败！");
    }

    /**
     * 获得user
     * @param index
     * @param num
     * @return
     */
    @RequestMapping("/queryUser")
    public Result queryUser(@RequestParam("index") int index, @RequestParam("num")int num){
        List<User> users = userService.queryUser(index, num);
        int sumOfUser = userService.sumOfUser();
        if (sumOfUser>0)sumOfUser=sumOfUser%8>0?sumOfUser/8+1:sumOfUser/8;
        HashMap<String,Object> data=new HashMap<>();
        data.put("sumOfUser",sumOfUser);
        data.put("users",users);
        return Result.succ(data);
    }

    /**
     * 查询符合条件的前num个user
     * @param keyword
     * @param index
     * @param num
     * @return
     */
    @RequestMapping("/findUserByKW")
    public Result findUserByKW(@RequestParam("keyword") String keyword,@RequestParam("index") int index, @RequestParam("num")int num){
        keyword="%"+keyword+"%";
        List<User> users = userService.queryUserByKW(keyword,index, num);
        int sumOfUser = userService.sumOfUserByKW(keyword);
        if (sumOfUser>0)sumOfUser=sumOfUser%8>0?sumOfUser/8+1:sumOfUser/8;
        HashMap<String,Object> data=new HashMap<>();
        data.put("sumOfUser",sumOfUser);
        data.put("users",users);
        return Result.succ(data);
    }
    /**
     * 更改用户状态
     * @param uid
     * @param status
     * @return
     */
    @RequestMapping("/changeStatus")
    public Result changeStatus(@RequestParam("uid")int uid,@RequestParam("status")int status){
        int i = userService.changeStatus(uid, status);
        if(i==0)return Result.fail(null);
        return  Result.succ(null);
    }

    /**
     * 删除指定用户
     * @param uid
     * @return
     */
    @RequestMapping("/deleteUser")
    public Result deleteUser(int uid){
        int i = userService.deleteUser(uid);
        if(i==0)return Result.fail(null);
        return Result.succ(null);
    }
}
