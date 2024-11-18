package com.example.demo.Controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Entity.Buy;
import com.example.demo.Entity.Sell;
import com.example.demo.Entity.Shohin;
import com.example.demo.Repository.ShohinRepository;
import com.example.demo.Service.BuyService;
import com.example.demo.Service.SellService;
import com.example.demo.Service.ShohinService;

@Controller
public class ShohinController {
@Autowired
ShohinService shohinService;
@Autowired
BuyService buyService;
@Autowired 
SellService sellService;
@Autowired
ShohinRepository shohinRepository;


	@GetMapping("/")
	public ModelAndView home(ModelAndView mv) {
		mv.addObject("title", "Main Menu");
		mv.setViewName("home");
		return mv;
	}
	@GetMapping("/zaiko")
	public ModelAndView zaikoview(ModelAndView mv) {
		List<Shohin>list = shohinService.findAll();
		mv.addObject("title", "在庫一覧");
		mv.addObject("list",list);
		mv.setViewName("zaiko");
		return mv;
	}
	@PostMapping("/search") 
	public ModelAndView search(@RequestParam("key") String key, ModelAndView mv) {
		List<Shohin> list = shohinRepository.findByHinmeiLike("%" + key + "%"); 
		mv.addObject("list", list);
		mv.setViewName("zaiko");
		return mv;
	}
	@GetMapping("/add")
	public ModelAndView addShohin(ModelAndView mv) {
		mv.addObject("title", "商品追加");
		mv.addObject("form",new Shohin());
		mv.setViewName("addShohin");
		return mv;
	}
	@PostMapping("/add")
    public String add(@ModelAttribute("form") Shohin shohin, Model model) {
            shohinService.saveAndFlush(shohin);
            return "redirect:/";
            }
	@GetMapping("/change/{id}")
    public String change(@PathVariable Integer id, Model model) {
    	Shohin shohin = shohinService.getbyId(id);
    	model.addAttribute("title", "商品編集");
        model.addAttribute("form", shohin);
        return "addShohin"; 
    }
	
	@GetMapping("/detail/{id}")
	public ModelAndView data(@PathVariable Integer id,ModelAndView mv) {
		List<Sell>sellList = sellService.getAllSellByShohinId(id);
		List<Buy>buyList = buyService.getAllBuyByShohinId(id);
		Shohin shohin = shohinService.getbyId(id);
		mv.addObject("sellList", sellList);
		mv.addObject("buyList", buyList);
		mv.setViewName("detail");
		mv.addObject("shohin", shohin);
		mv.addObject("title", "詳細");
		return mv;
	}
	
	@GetMapping("/buy/{shohinId}")
	public ModelAndView buy(@PathVariable Integer shohinId, ModelAndView mv) {
		Shohin shohin = shohinService.getbyId(shohinId);
		List<Buy>buyList = buyService.getAllBuyByShohinId(shohinId);
		mv.addObject("title", "仕入れ一覧");
		mv.addObject("shohin",shohin);
		mv.addObject("list",buyList);
		mv.setViewName("buy");
		return mv;
	}
	@GetMapping("/sell/{shohinId}")
	public ModelAndView sell(@PathVariable Integer shohinId, ModelAndView mv) {
		List<Sell>sellList = sellService.getAllSellByShohinId(shohinId);
		Shohin shohin = shohinService.getbyId(shohinId);
		mv.addObject("shohin", shohin);
		mv.addObject("title", "売り一覧");
		mv.addObject("list",sellList);
		mv.setViewName("sell");
		return mv;
	}
	@GetMapping("buy/{shohinId}/addBuy")
	public ModelAndView addBuy(@PathVariable Integer shohinId , ModelAndView mv) {
		Shohin shohin = shohinService.getbyId(shohinId);
    	mv.addObject("shohin", shohin);
		mv.addObject("title", "仕入れ");
		mv.addObject("buyForm", new Buy());
		mv.setViewName("addBuy");
		return mv;
		
	}
	@PostMapping("buy/{shohinId}/addBuy")
    public String add(@PathVariable Integer shohinId, @ModelAttribute("buyForm") Buy buy, Model model) {
        try {
        	Shohin shohin = shohinService.getbyId(shohinId);
        	model.addAttribute("shohin", shohin);
        	buy.setShohin(shohin);
            buyService.saveAndFlush(buy);
            return "redirect:/buy/" + shohinId; // 正しいリダイレクト先"
        } catch (Exception e) {
            model.addAttribute("error", "店舗の登録に失敗しました: " + e.getMessage());
            return "addBuy"; // フォームを再表示
        }
    }
	@GetMapping("sell/{shohinId}/addSell")
	public ModelAndView addSell(@PathVariable Integer shohinId, ModelAndView mv) {
		Shohin shohin = shohinService.getbyId(shohinId);
    	mv.addObject("shohin", shohin);
		mv.addObject("title", "売上");
		mv.addObject("sellForm", new Sell());
		mv.setViewName("addSell");
		return mv;
		
	}
	@PostMapping("sell/{shohinId}/addSell")
    public String addSell(@PathVariable Integer shohinId, @ModelAttribute("sellForm") Sell sell, Model model) {
        try {
        	Shohin shohin = shohinService.getbyId(shohinId);
        	model.addAttribute("shohin", shohin);
        	sell.setShohin(shohin);
            sellService.saveAndFlush(sell);
            return "redirect:/sell/" + shohinId; // 正しいリダイレクト先
        } catch (Exception e) {
            model.addAttribute("error", "店舗の登録に失敗しました: " + e.getMessage());
            return "addSell"; // フォームを再表示
        }
    }
	@GetMapping("/buy/{shohinId}/delete/{id}")
    public String deleteBuy(@PathVariable Integer shohinId,@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            buyService.deleteBuy(id);
            redirectAttributes.addFlashAttribute("message", "Store deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete store: " + e.getMessage());
        }
        return "redirect:/buy/" + shohinId; // Redirect after deletion
    }
    @GetMapping("/buy/{shohinId}/change/{id}")
    public String changeBuy(@PathVariable Integer shohinId,@PathVariable Integer id, Model model) {
    	Shohin shohin = shohinService.getbyId(shohinId);
    	Optional<Buy> buy = buyService.getBuyById(id);
    	model.addAttribute("title", "仕入れ編集");
        model.addAttribute("shohin", shohin);
        model.addAttribute("buyForm", buy);
        return "addBuy"; 
    }
    @GetMapping("/sell/{shohinId}/delete/{id}")
    public String deleteSell(@PathVariable Integer shohinId,@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            sellService.deleteSell(id);
            redirectAttributes.addFlashAttribute("message", "Store deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete store: " + e.getMessage());
        }
        return "redirect:/sell/" + shohinId; // Redirect after deletion
    }
    @GetMapping("/sell/{shohinId}/change/{id}")
    public String changeSell(@PathVariable Integer shohinId,@PathVariable Integer id, Model model) {
    	Shohin shohin = shohinService.getbyId(shohinId);
    	Optional<Sell> sell = sellService.getSellById(id);
    	model.addAttribute("title", "売り編集");
        model.addAttribute("shohin", shohin);
        model.addAttribute("sellForm", sell);
        return "addSell"; 
    }
}
