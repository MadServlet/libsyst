package com.proj.itstaym.controller.api;

import com.proj.itstaym.controller.api.records.LoanRecord;
import com.proj.itstaym.service.api.LendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<LoanRecord> findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return lendingService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(path = "/report/history/bulk")
    public List<LoanRecord> findAll() {
        return lendingService.findAll();
    }

}
