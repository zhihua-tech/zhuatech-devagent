/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.devagent.config;
import cn.zhuatech.devagent.model.*; import cn.zhuatech.devagent.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration public class DataInitializer {/**
                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                              */
@Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository records,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
 OperatingUnit platform=units.save(new OperatingUnit("DEV-PLATFORM","平台工程组","研发中心",2600)),commerce=units.save(new OperatingUnit("DEV-COMMERCE","交易研发组","产品技术中心",2200)),mobile=units.save(new OperatingUnit("DEV-MOBILE","移动体验组","客户端中心",1800));
 WorkRecord a=records.save(new WorkRecord("TASK-260801-326","REPO-ORDER-API","修复订单幂等校验并补充回归测试",commerce,42,29,2,LocalDate.now().plusDays(1),WorkRecord.Status.RUNNING,"BRANCH-AGT-326")); WorkRecord b=records.save(new WorkRecord("TASK-260801-309","REPO-IAM","升级鉴权组件并清理兼容层",platform,36,36,1,LocalDate.now(),WorkRecord.Status.COMPLETED,"BRANCH-AGT-309")); WorkRecord c=records.save(new WorkRecord("TASK-260801-341","REPO-MOBILE","优化移动端库存查询首屏",mobile,48,25,4,LocalDate.now().plusDays(3),WorkRecord.Status.RELEASED,"BRANCH-AGT-341"));
 resources.saveAll(List.of(new ResourceRegister("SANDBOX-JAVA-01","Java 隔离执行池",platform,ResourceRegister.Status.RUNNING,91),new ResourceRegister("TOOL-GIT-02","代码仓库只读工具",commerce,ResourceRegister.Status.RUNNING,97),new ResourceRegister("EVAL-CI-03","测试与质量门禁",mobile,ResourceRegister.Status.ALARM,74)));
 reviews.saveAll(List.of(new ReviewRecord("CR-260801-326",a,"代码审查",18,2,ReviewRecord.Result.PENDING,"俞川"),new ReviewRecord("CR-260801-309",b,"回归测试",22,0,ReviewRecord.Result.PASSED,"闻序"),new ReviewRecord("CR-260731-341",c,"性能基线",16,3,ReviewRecord.Result.FAILED,"宋惟")));
 String demo=encoder.encode("Demo@2026"); users.saveAll(List.of(new UserAccount("operator",demo,"闻序",UserAccount.Role.DOMAIN_USER,"DEV-COMMERCE"),new UserAccount("planner",demo,"俞川",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"宋惟",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));};}}
