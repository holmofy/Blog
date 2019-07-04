package cn.hff.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import cn.hff.blog.dao.ArticleDao.IdAndTitle;
import cn.hff.blog.dto.ArticleSearchDto;
import cn.hff.blog.dto.Views;
import cn.hff.blog.entity.Article;
import cn.hff.blog.entity.User;
import cn.hff.blog.mvc.CurrentUser;
import cn.hff.blog.service.ArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @JsonView(Views.WithLob.class)
    public Article post(@CurrentUser User user,
                        @Valid @RequestBody Article article) {
        return articleService.save(user, article);
    }

    @DeleteMapping("{id}")
    public void delete(@CurrentUser User user,
                       @PathVariable int id) {
        articleService.delete(user, id);
    }

    @PutMapping("{id}")
    @JsonView(Views.WithLob.class)
    public Article update(@CurrentUser User user,
                          @PathVariable int id,
                          @Valid @RequestBody Article article) {
        return articleService.update(user, id, article);
    }

    @GetMapping("{id}")
    @JsonView(Views.WithLob.class)
    public Article get(@PathVariable int id) {
        return articleService.get(id);
    }

    @GetMapping
    @JsonView(Views.WithoutLob.class)
    public Page<Article> search(@RequestParam ArticleSearchDto search,
                                @PageableDefault(sort = "created") Pageable pageable) {
        return articleService.search(search, pageable);
    }

    @GetMapping("prefix")
    public List<IdAndTitle> likeTitlePrefix(@CurrentUser User user,
                                            @RequestParam String titlePrefix,
                                            @PageableDefault(5) Pageable pageable) {
        return articleService.likeTitlePrefix(user.getId(), titlePrefix, pageable);
    }

    @GetMapping("fuzzy-title")
    @JsonView(Views.WithoutLob.class)
    public Page<Article> likeFuzzyTitle(@CurrentUser User user,
                                        @RequestParam String fuzzyTitle,
                                        @PageableDefault(sort = "created") Pageable pageable) {
        return articleService.likeFuzzyTitle(user.getId(), fuzzyTitle, pageable);
    }

}
