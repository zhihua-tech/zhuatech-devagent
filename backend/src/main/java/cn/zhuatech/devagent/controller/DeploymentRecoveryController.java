/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.devagent.controller;import cn.zhuatech.devagent.common.ApiResponse;import cn.zhuatech.devagent.service.DeploymentRecoveryService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/devagent/insights/deployment-recovery") public class DeploymentRecoveryController{private final DeploymentRecoveryService service;/**
                                                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                         */
public DeploymentRecoveryController(DeploymentRecoveryService service){this.service=service;}/**
                                                                                                                                                                                                                                                                      * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                      */
@PostMapping ApiResponse<DeploymentRecoveryService.Result> evaluate(@Valid @RequestBody DeploymentRecoveryService.Request r){return ApiResponse.ok(service.evaluate(r));}}
