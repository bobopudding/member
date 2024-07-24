package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private static final String ADMIN_EMAIL = "zerotrust";
    private static final String ADMIN_PASSWORD = "zerotrust";

    @GetMapping("/login")
    public String adminLoginForm() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam("adminEmail") String adminEmail,
                             @RequestParam("adminPassword") String adminPassword,
                             HttpSession session, Model model) {
        if (ADMIN_EMAIL.equals(adminEmail) && ADMIN_PASSWORD.equals(adminPassword)) {
            session.setAttribute("admin", true);
            return "redirect:/admin/member-list";
        } else {
            model.addAttribute("loginError", "잘못된 이메일 또는 비밀번호입니다.");
            return "admin-login";
        }
    }

    @GetMapping("/member-list")
    public String adminMemberList(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "admin-member-list";
    }

    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
