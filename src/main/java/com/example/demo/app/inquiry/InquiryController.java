package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;


@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	private final InquiryService inquiryService;
	
	public InquiryController(InquiryService inquiryService) {
		
		this.inquiryService=inquiryService;
	}
	
	//値の呼び出し
	@GetMapping
	public String index(Model model) {
		List<Inquiry>list=inquiryService.getAll();
		model.addAttribute("inquiryList", list);
		model.addAttribute("title","お問い合わせ一覧");
		return "inquiry/index_boot";
	}
	
	
	 @GetMapping("/form")
	 public String form(InquiryForm inquiryForm,
			 Model model,
			 @ModelAttribute("complete") String complete) {
		 model.addAttribute("title","お問い合わせフォーム");
		 return "inquiry/form_boot" ;
	 }
	 
	 @PostMapping("/form")
	 public String formGoBack(InquiryForm inquiryForm,Model model) {
		 model.addAttribute("title","お問い合わせフォーム");
		 return "inquiry/form_boot" ;
	 }
	
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,Model model){
		if(result.hasErrors()) {
			model.addAttribute("title","お問い合わせフォーム");
			 return "inquiry/form_boot";
		}

		model.addAttribute("title","確認画面");
		return "inquiry/confirm_boot";
	} 
	
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title","お問い合わせフォーム");
			return "inquiry/form_boot";
		}
		
		//DBへ登録
		Inquiry inquiry=new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.insert(inquiry);
		
		redirectAttributes.addFlashAttribute("complete","登録完了！");
		return "redirect:/inquiry/form";
		
		}
	
    @GetMapping("/{id}")
    public String showUpdate(
    		InquiryForm inquiryForm,
        @PathVariable int id,
        Model model) {

    	//Inquiryを取得(Optionalでラップ)
        Optional<Inquiry> inquiryOpt = inquiryService.getInquiry(id);

        //InquiryFormへの詰め直しs
		 Optional<InquiryForm> inquiryFormOpt = inquiryOpt.map(t->makeInquiryForm(t));
        //InquiryFormがnullでなければ中身を取り出し
        if(inquiryFormOpt.isPresent()) {
        	inquiryForm = inquiryFormOpt.get();
        }

        model.addAttribute("inquiryForm", inquiryForm);
        List<Inquiry> list = inquiryService.getAll();
        model.addAttribute("list", list);
        model.addAttribute("inquiryId", id);
        model.addAttribute("title", "更新用フォーム");

        return "inquiry/update";
    }
	
	  @PostMapping("/update")
	    public String update(
	    	@ModelAttribute @Validated InquiryForm inquiryForm,
	    	BindingResult result,
	    	@RequestParam("inquiryId") int inquiryId,
	    	Model model,
	    	RedirectAttributes redirectAttributes) {

	        if (!result.hasErrors()) {
	        	//InquiryFormのデータをInquiryに格納
	        	Inquiry inquiry=new Inquiry();
	        	inquiry.setId(inquiryId);
	    		inquiry.setName(inquiryForm.getName());
	    		inquiry.setEmail(inquiryForm.getEmail());
	    		inquiry.setContents(inquiryForm.getContents());
	    		inquiry.setCreated(LocalDateTime.now());
	        	
	        	//更新処理、フラッシュスコープの使用、リダイレクト（個々の編集ページ）
	        	inquiryService.update(inquiry);
	        	redirectAttributes.addFlashAttribute("complete", "変更が完了しました");
	            return "redirect:/inquiry/" + inquiryId;
	            
	        }else {
	        	model.addAttribute("inquiryForm", inquiryForm);
	        	model.addAttribute("inquiryId",inquiryId);
	            model.addAttribute("title", "更新用フォーム");
	            return "inquiry/update";
	        }
	        	
	        } 

	  @PostMapping("/delete")
	    public String delete(
	    	@RequestParam("inquiryId") int inquiryid,
	    	Model model) {
	    	//問い合わせを一件削除しリダイレクト
	        inquiryService.delete(inquiryid);
	        return "redirect:/inquiry";
	    }
	  
	  private InquiryForm makeInquiryForm(Inquiry inquiry) {
		  InquiryForm InquiryForm = new InquiryForm();

	        InquiryForm.setName(inquiry.getName());
	        InquiryForm.setEmail(inquiry.getEmail());
	        InquiryForm.setContents(inquiry.getContents());
	        
	        return InquiryForm;
	    }

	
		 
	 }

