package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**                             第九天作业
         * 问答微服务完善
         * 1.如果面试遇见一个不会的问题，可以来十次方搜索
         *      如果搜索到了，下面没有回答，或者没有解决问题的回答，可以顶帖子。
         *      如果有可用的回答，可以标记此回答可用。把该问题设置为已解决。
         * 2.如果是我的提问的问题，我可以快速找到。
         *      可以直接在个人中心看到回复，和当前问题的状态。
         * 3.发布问题需要审核，审核之后才能显示。
 *
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
public class QAController {
    @Autowired
    private ArticleSearchService articleSearchService;
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleSearchService.add(article);
        return  new Result(true, StatusCode.OK,"添加成功");
          }

     @RequestMapping(value="/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitleOrContentLike(@PathVariable String keywords ,@PathVariable int page,@PathVariable int size){
         Page<Article> pagelist = articleSearchService.findByTitleOrContentLike(keywords, page, size);
         return  new Result(true, StatusCode.OK,"查询成功",new PageResult<Article>(pagelist.getTotalElements(),pagelist.getContent()));
          }
}
