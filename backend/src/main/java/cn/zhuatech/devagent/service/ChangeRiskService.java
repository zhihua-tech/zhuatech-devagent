/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.devagent.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** 在开发智能体提交变更前计算发布风险并给出门禁结论。 */
@Service
public class ChangeRiskService {
    public RiskResult evaluate(RiskRequest request) {
        int riskScore = Math.min(100,
            Math.min(25, request.changedFiles())
                + (request.testCoverage() < 60 ? 30 : request.testCoverage() < 80 ? 15 : 0)
                + Math.min(40, request.criticalVulnerabilities() * 20)
                + (request.databaseMigration() ? 15 : 0)
                + (!request.rollbackPlan() ? 20 : 0));
        List<String> failedChecks = new ArrayList<>();
        if (request.testCoverage() < 60) failedChecks.add("TEST_COVERAGE");
        if (request.criticalVulnerabilities() > 0) failedChecks.add("SECURITY");
        if (request.databaseMigration() && !request.rollbackPlan()) failedChecks.add("ROLLBACK_PLAN");
        String decision = request.criticalVulnerabilities() > 0 || (request.databaseMigration() && !request.rollbackPlan()) ? "BLOCK"
            : riskScore >= 50 ? "REVIEW" : "PASS";
        return new RiskResult(decision, riskScore, List.copyOf(failedChecks), "PASS".equals(decision) ? "允许创建合并请求" : "补充测试或回滚证据后重新评估");
    }

    public record RiskRequest(
        @NotBlank(message = "请输入变更说明") String changeSummary,
        @Positive int changedFiles,
        @DecimalMin("0.0") @DecimalMax("100.0") double testCoverage,
        @PositiveOrZero int criticalVulnerabilities,
        boolean databaseMigration,
        boolean rollbackPlan
    ) {}

    public record RiskResult(String decision, int riskScore, List<String> failedChecks, String nextAction) {}
}
