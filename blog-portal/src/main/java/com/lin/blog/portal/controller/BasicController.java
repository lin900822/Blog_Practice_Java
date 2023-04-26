package com.lin.blog.portal.controller;

import com.lin.blog.portal.model.Basic;
import com.lin.blog.portal.service.IBasicService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/basic")
public class BasicController
{
    @Resource
    private IBasicService basicService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity saveBasic(String websiteName, String websiteThumbnail)
    {
        Basic basic = new Basic()
                .setWebsiteName(websiteName)
                .setWebsiteThumbnail(websiteThumbnail);

        basicService.saveBasic(basic);

        return ResponseEntity.ok(null);
    }

    @GetMapping("")
    public ResponseEntity<Basic> getBasic()
    {
        return ResponseEntity.ok(basicService.getBasic());
    }
}
