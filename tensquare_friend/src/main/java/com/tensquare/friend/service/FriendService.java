package com.tensquare.friend.service;

import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.dao.pojo.Friend;
import com.tensquare.friend.dao.FriendDao;

import com.tensquare.friend.dao.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FriendService {
     @Autowired
    private FriendDao friendDao;
     @Autowired
    private NoFriendDao noFriendDao;

     @Transactional
    public int addFriend(String  userid, String friendid) {
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        //判断userid 到friendid 是否有数据，有就是重复添加好友，返回0
          if (friend!=null){
              return 0;
          }

        //直接添加好友，让好友表useid 到 friendid 方向的type 为0
         friend =new Friend();
          friend.setFriendid(friendid);
          friend.setUserid(userid);
          friend.setIslike("0");
          friendDao.save(friend);
        //判断从friendid 到userid是否有数据，如果有，有双方的状态都改为1；

  if(friendDao.findByUseridAndFriendid(friendid,userid)!=null){
      friendDao.updateIsLike(userid,friendid,"1");
      friendDao.updateIsLike(friendid,userid,"1");
  }
         return 1;
  }


    /**
     * 向不喜欢列表中添加记录
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid,String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setNofriendid(friendid);
        noFriendDao.save(noFriend);
    }

    public int addoNofriend(String userid, String friendid) {
        NoFriend noFriend = noFriendDao.findByUseridAndNofriendid(userid, friendid);
        //判断userid 到friendid 是否有数据，有就是重复添加好友，返回0
        if (noFriend!=null){
            return 0;
        }

        //直接添加好友，让好友表useid 到 friendid 方向的type 为0
         noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setNofriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }
    @Transactional
    public void deleteFriend(String userid, String friendid) {
         //从好友表中删除userid → friend 这条记录
           friendDao.deletefriend(userid,friendid);

           //更新friendid  → userid 的 islike 为 0
           friendDao.updateIsLike(friendid, userid,"0");

           //非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setNofriendid(friendid);
        noFriendDao.save(noFriend);


    }
}
