package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest request;


	/**
	 * 更新粉丝数和关注数
	 *
	 */
	@RequestMapping(value = "/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
	public void  updatefanscountandfollowcount(@PathVariable String userid,@PathVariable String friendid,@PathVariable int x){
		userService.updatefanscountandfollowcount(x,userid,friendid);
	}




	/**
	 * 发短信
	 *
	 * @return
	 */
	@RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile) {   //sendSms
		userService.sendSms(mobile);
		return new Result(true, StatusCode.OK, "发送成功");
	}

	/**
	 * 查询全部数据
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
	}

	/**
	 * 根据ID查询
	 *
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 *
	 * @param searchMap 查询条件封装
	 * @param page      页码
	 * @param size      页大小
	 * @return 分页结果
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 *
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
	}

	/**
	 * 用户注册
	 *
	 * @param user
	 */
	@RequestMapping(value = "register/{code}", method = RequestMethod.POST)
	public Result add(@RequestBody User user, @PathVariable String code) {

		//判断code是否正确
		if(code ==null){
			return new Result(true, StatusCode.OK, "请点击获取验证码");
		}
		String checkcode = (String) redisTemplate.opsForValue().get("checkcode"+user.getMobile());
		System.out.println(checkcode);
		if(checkcode!=null && !checkcode.equals(code)){
			return new Result(true, StatusCode.OK, "你输入的验证码不正确");
		}
		if (checkcode==null){
			return new Result(true, StatusCode.OK, "请先发送验证码，再注册");

		}
		//判断用户是否已注册
		Map map =new HashMap();
		map.put("mobile",user.getMobile());
		List udb = userService.findSearch(map);
		User user1= new User();
		if(udb!=null&&udb.size()>0){

		 user1 = (User) udb.get(0);
		}
		if (udb!=null && user.getMobile().equalsIgnoreCase(user1.getMobile())){
			return new Result(true, StatusCode.OK, "你的手机号码已注册过，请直接登陆！");
		}

		userService.save(user);
       return new Result(true, StatusCode.OK, "注册成功");
	}

	/**
	 * 增加
	 *
	 * @param user
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody User user) {
		userService.add(user);
		return new Result(true, StatusCode.OK, "增加成功");
	}
/**
	 * 修改
	 *
	 * @param user
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id) {
		user.setId(id);
		userService.update(user);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		String authHeader = request.getHeader("Authorization");//获取头信息
		if(authHeader==null){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足1");
		}
		if(!authHeader.startsWith("Bearer ")){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足2");
		}

		String token=authHeader.substring(7);//提取token
		try {

		Claims claims = jwtUtil.parseJWT(token);

		if(claims==null){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足3");
		}
		if(!"admin".equals(claims.get("roles"))){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足4");
		}
		}catch (Exception e){

		}
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	//用户登陆
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user) {
	User user1 = userService.login(user);
		if (user1!=null) {
			String token = jwtUtil.createJWT(user1.getId(),
					user1.getNickname(), "user");
			Map map=new HashMap();
			map.put("token",token);
			map.put("name",user1.getNickname());//昵称
			map.put("avatar",user1.getAvatar());//头像
			map.put("user",user.getNickname());
			return new Result(true,StatusCode.OK,"登陆成功",map);
		} else {
			return new Result(true, StatusCode.LOGINERROR, "用户名或密码错误");

		}
	}
}
