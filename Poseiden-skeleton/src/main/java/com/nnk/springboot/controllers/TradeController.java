package com.nnk.springboot.controllers;

import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.interfaces.TradeService;
import com.nnk.springboot.model.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@Controller
public class TradeController {

    private final TradeRepository tradeRepository;
    private final TradeService  tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        log.info("home: show trade/list");
        model.addAttribute("trades", tradeRepository.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser() {
        log.info("addUser: show trade/add");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, RedirectAttributes redirectAttributes) {
        log.info("validate trade: name " + trade.getAccount() + " Type: " + trade.getType());
        try {
            tradeService.validateTrade(trade);
        } catch (NegativeNumberException e) {
            redirectAttributes.addAttribute("error_number_negative", true);
            return "redirect:/trade/add";
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        log.info("showUpdateForm Trade:  " + id);
        Trade trade = tradeRepository.findTradeById(id);
        if (trade == null){
            redirectAttributes.addAttribute("id_not_found", true);
            return  "redirect:/trade/list";
        }
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("updateRuleName: " + id);
        if(result.hasErrors()){
            redirectAttributes.addAttribute("error", true);
        }
        try {
            tradeService.updateTrade(id, trade);
        } catch (NegativeNumberException e) {
            redirectAttributes.addAttribute("error_number_negative", true);
            return "redirect:/trade/update/{id}";
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        log.info("delete Trade:" + id);
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
