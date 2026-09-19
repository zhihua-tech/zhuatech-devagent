/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.devagent.agent;
import org.springframework.stereotype.Component; import java.util.List; import java.util.Map;
/**
 * 研发智能体运行边界；演示任务仅模拟沙箱分支，不操作外部代码仓库。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface AgentRuntime {/**
                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                */
AgentResult run(AgentRequest request);/**
                                                                      * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                      */
record AgentRequest(String objective,Map<String,String> context){}/**
                                                                                                                                        * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                        */
record AgentStep(String name,String status,String evidence){}/**
                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                     */
record AgentResult(String runtime,String summary,List<AgentStep> steps,Map<String,Object> metrics){}}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Component class DemoAgentRuntime implements AgentRuntime {/**
                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                            */
public AgentResult run(AgentRequest request){return new AgentResult("local-dev-sandbox","已生成改动计划和验证清单，合并动作等待代码所有者审批。",List.of(new AgentStep("任务拆解","COMPLETED","形成 4 个原子步骤"),new AgentStep("沙箱验证","COMPLETED","单元测试与静态检查通过"),new AgentStep("变更合并","PENDING","等待 CODEOWNERS 复核")),Map.of("changedFiles",6,"tests",18,"objectiveLength",request.objective().length()));}}
