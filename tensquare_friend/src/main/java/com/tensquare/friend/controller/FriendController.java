package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;

@RestController
@RequestMapping("/friend")
public class FriendController {
     @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友
     * @param friendid 对方用户ID
     * @param type 1：喜欢 0：不喜欢
     * @return
     */

     @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
         Claims claims=(Claims)request.getAttribute("user_claims");
         if(claims==null){
             return new Result(false, StatusCode.ACCESSERROR,"无权访问");
         }
         //如果是喜欢
             String userid = claims.getId();
         if(type!=null){


         if(type.equals("1")){
             int flag=friendService.addFriend(claims.getId(),friendid);
             if(flag==0){

                 return new Result(false, StatusCode.ERROR,"不能重复添加好友");
             }
             if(flag==1){
                 userClient.updatefanscountandfollowcount(userid, friendid,1);

                 return new Result(false, StatusCode.OK,"成功添加此好友");

             }
         }else if (type.equals("2")){
               //添加非好友
             int flag =friendService.addoNofriend(claims.getId(),friendid);
             if(flag==0){
                 return new Result(false, StatusCode.ERROR,"不能重复添加好友");
             }
             if (flag==1){

             return new Result(false, StatusCode.REPERROR,"已经添加此非好友");
             }
         }
            return new Result(true, StatusCode.ERROR, "参数有误");
         }else {

            return new Result(true, StatusCode.ERROR, "参数有误");

         }
     }

    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
   public Result deleteFriend(@PathVariable String friendid) {
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"无权访问");
        }
        //如果是喜欢
        String userid = claims.getId();

        userClient.updatefanscountandfollowcount(userid, friendid, -1);
        friendService.deleteFriend(userid,friendid);

        return new Result(true, StatusCode.OK,"删除成功");
   }

    }


