package com.proj.itstaym.controller.api;

import com.proj.itstaym.controller.api.records.LoanRecord;
import com.proj.itstaym.service.api.LendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/lending")
public class LendingCtrl {

    private final LendingService lendingService;

    @Autowired
    public LendingCtrl(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @GetMapping(path = "/report/history", params = {"page", "size"})
    public Page<LoanRecord> findAll(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        var email = userDetails.getUsername();
        var role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("STUDENT");

        PageRequest pageable = PageRequest.of(page, size);

        if (role.equals("ADMIN") || role.equals("LIBRARIAN")) {
            return lendingService.findAll(pageable);
        } else {
            return lendingService.findByEmailPaged(email, pageable);
        }
    }

    @GetMapping(path = "/report/history/bulk")
    public List<LoanRecord> findAll() {
        return lendingService.findAll();
    }

    @GetMapping(path = "/report/history", params = {"email", "page", "size"})
    public Page<LoanRecord> findAllByEmail(@RequestParam String email, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return lendingService.findByEmailPaged(email, PageRequest.of(page, size));
    }

    @GetMapping(path = "/report/history/bulk", params = {"email"})
    public List<LoanRecord> findAllByEmail(@RequestParam String email) {
        return lendingService.findByEmail(email);
    }



}
