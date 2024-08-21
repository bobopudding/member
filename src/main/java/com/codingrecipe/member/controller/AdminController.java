package com.codingrecipe.member.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String adminLoginForm() {
        return "admin-login";
    }

    @GetMapping("/member-list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminMemberList(Model model) {
        // 여기에 실제 회원 목록을 가져오는 로직이 추가되어야 함
        return "admin-member-list";
    }

    @GetMapping("/logout")
    public String adminLogout() {
        return "redirect:/";
    }
}
