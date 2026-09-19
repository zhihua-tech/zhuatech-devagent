/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.devagent;import cn.zhuatech.devagent.service.DeploymentRecoveryService;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class DeploymentRecoveryServiceTests{private final DeploymentRecoveryService s=new DeploymentRecoveryService();/**
                                                                                                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                */
@Test void blocksIrreversibleRelease(){var r=s.evaluate(new DeploymentRecoveryService.Request("R1",50,false,false,false,10,false));assertEquals("BLOCK",r.status());}/**
                                                                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                     */
@Test void approvesRecoverableRelease(){var r=s.evaluate(new DeploymentRecoveryService.Request("R2",90,true,true,true,80,true));assertEquals("READY",r.status());}}
