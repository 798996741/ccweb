package com.yulun.controller.oftenword;

import com.fh.controller.base.BaseController;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.yulun.service.OftenWordManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value="/oftenword")
public class OftenWordController extends BaseController {
    @Resource(name="oftenWordService")
    private OftenWordManager oftenWordManage;

    @RequestMapping(value="/oftenwordlist.do")
    public ModelAndView oftenwordlist() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"快捷回复列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        List<PageData> word = oftenWordManage.findWord(pd);
        mv.addObject("word", word);
        mv.setViewName("onlineserver/oftenword/oftenword_list");
        return mv;
    }
    @RequestMapping(value="/findWordById.do")
    public ModelAndView findWordById() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        PageData word = oftenWordManage.findWordById(pd);
        mv.addObject("pd", word);
        mv.addObject("msg", "updateWord");
        mv.setViewName("onlineserver/oftenword/oftenword_edit");
        return mv;
    }
    @RequestMapping(value="/goWordAdd")
    public ModelAndView goWordAdd()throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        mv.setViewName("xxgl/zxz/zxz_edit");
        mv.addObject("msg", "insertWord");
        mv.addObject("pd", pd);
        mv.setViewName("onlineserver/oftenword/oftenword_edit");
        return mv;
    }

    @RequestMapping(value="/insertWord.do")
    public ModelAndView insertWord() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"新增快捷回复");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        oftenWordManage.insertWord(pd);
        mv.addObject("msg","success");
        mv.setViewName("redirect:/oftenword/oftenwordlist.do");
        return mv;
    }

    @RequestMapping(value="/updateWord.do")
    public ModelAndView updateWord() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"编辑快捷回复");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        oftenWordManage.updateWord(pd);
        mv.addObject("msg","success");
        mv.setViewName("redirect:/oftenword/oftenwordlist.do");
        return mv;
    }

    @RequestMapping(value="/deleteWord.do")
    public ModelAndView deleteWord() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"删除快捷回复");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        oftenWordManage.deleteWord(pd);
        mv.addObject("msg","success");
        mv.setViewName("onlineserver/oftenword/oftenword_list");
        return mv;
    }

    @RequestMapping(value="/findevaluate.do")
    public ModelAndView findevaluate() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"快捷回复列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        List<PageData> valuate = oftenWordManage.findevaluate(pd);
        mv.addObject("valuate", valuate);
        mv.setViewName("onlineserver/oftenword/evaluate_list");
        return mv;
    }
}
