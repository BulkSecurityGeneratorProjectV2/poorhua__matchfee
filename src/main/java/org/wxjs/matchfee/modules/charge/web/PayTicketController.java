/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.matchfee.modules.charge.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wxjs.matchfee.common.config.Global;
import org.wxjs.matchfee.common.persistence.Page;
import org.wxjs.matchfee.common.web.BaseController;
import org.wxjs.matchfee.common.utils.StringUtils;
import org.wxjs.matchfee.modules.charge.entity.Charge;
import org.wxjs.matchfee.modules.charge.entity.PayTicket;
import org.wxjs.matchfee.modules.charge.service.ChargeService;
import org.wxjs.matchfee.modules.charge.service.PayTicketService;

/**
 * 缴费凭证Controller
 * @author GLQ
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/charge/payTicket")
public class PayTicketController extends BaseController {

	@Autowired
	private PayTicketService payTicketService;
	
	@Autowired
	private ChargeService chargeService;
	
	@ModelAttribute
	public PayTicket get(@RequestParam(required=false) String id) {
		PayTicket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = payTicketService.get(id);
		}
		if (entity == null){
			entity = new PayTicket();
		}
		return entity;
	}
	
	@RequiresPermissions("charge:charge:view")
	@RequestMapping(value = {"list", ""})
	public String list(PayTicket payTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayTicket> page = payTicketService.findPage(new Page<PayTicket>(request, response), payTicket); 
		model.addAttribute("page", page);
		return "modules/charge/payTicketList";
	}

	@RequiresPermissions("charge:charge:view")
	@RequestMapping(value = "form")
	public String form(PayTicket payTicket, Model model) {
		model.addAttribute("payTicket", payTicket);
		
		Charge charge = chargeService.get(payTicket.getCharge().getId());
		
		model.addAttribute("charge", charge);
		
		return "modules/charge/payTicketForm";
	}

	@RequiresPermissions("charge:charge:view")
	@RequestMapping(value = "save")
	public String save(PayTicket payTicket, HttpSession httpSession,  Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, payTicket)){
			return form(payTicket, model);
		}
		
		try{
			payTicketService.save(payTicket);
			addMessage(redirectAttributes, "保存缴费凭证成功");			
		}catch(DuplicateKeyException e1){
			addMessage(redirectAttributes, "保存失败。重复！");
			logger.error("save error", e1);
		}catch(Exception e2){
			addMessage(redirectAttributes, "保存失败。");
			logger.error("save error", e2);
		}		
		
		httpSession.setAttribute("chargeId", payTicket.getCharge().getId());
		
		return "redirect:"+Global.getAdminPath()+"/charge/charge/payTicketTab?repage";
	}
	
	@RequiresPermissions("charge:charge:view")
	@RequestMapping(value = "delete")
	public String delete(PayTicket payTicket, RedirectAttributes redirectAttributes) {
		payTicketService.delete(payTicket);
		addMessage(redirectAttributes, "删除缴费凭证成功");
		return "redirect:"+Global.getAdminPath()+"/charge/payTicket/?repage";
	}

}