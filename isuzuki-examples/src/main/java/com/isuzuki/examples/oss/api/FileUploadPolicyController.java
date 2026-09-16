package com.isuzuki.examples.oss.api;

import com.isuzuki.examples.oss.service.FileServiceApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 
 * @date : 
 * @description :
 */
@RestController
@RequestMapping(value = "/api")
public class FileUploadPolicyController {

    private final FileServiceApplicationService applicationService;

    public FileUploadPolicyController(FileServiceApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping(value = {"/pri-auth/policy/{appId}"})
    public Rs<FileUploadPolicyVo> apiResolvePrivatePolicy(@PathVariable("appId") String appId,
                                                          String filename,
                                                          String bizType) {
        return Rs.success(applicationService.handleOnlinePrivate(appId, filename, bizType));
    }

    @GetMapping("/pub-auth/policy/{appId}")
    public Rs<FileUploadPolicyVo> gaResolvePrivatePolicy(@PathVariable("appId") String appId,
                                                         String filename,
                                                         String bizType) {
        return Rs.success(applicationService.handleGuestPrivate(appId, filename, bizType));
    }

    @GetMapping(value = {"/pri-none/policy/{appId}"})
    public Rs<FileUploadPolicyVo> apiResolvePublicPolicy(@PathVariable("appId") String appId,
                                                         String filename,
                                                         String bizType) {
        return Rs.success(applicationService.handleOnlinePublic(appId, filename, bizType));
    }

    @GetMapping("/pub-none/policy/{appId}")
    public Rs<FileUploadPolicyVo> gaResolvePublicPolicy(@PathVariable("appId") String appId,
                                                        String filename,
                                                        String bizType) {

        return Rs.success(applicationService.handleGuestPublic(appId, filename, bizType));
    }

}
