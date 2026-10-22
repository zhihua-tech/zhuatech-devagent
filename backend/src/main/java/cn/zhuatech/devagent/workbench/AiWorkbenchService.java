/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.devagent.workbench;
import org.springframework.stereotype.Service;
import java.math.*;
import java.util.*;
import static cn.zhuatech.devagent.workbench.WorkbenchInput.*;
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
    /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Map<String,Object> execute(Map<String,Object> r){
        validate(r);

        keys(r,"patch");String patch=text(r,"patch",64000);String[] lines=patch.split("\\r?\\n",-1);int oldLeft=0,newLeft=0,added=0,deleted=0,hunks=0;String file=null;Set<String> files=new TreeSet<>(),sensitive=new TreeSet<>();
        var header=java.util.regex.Pattern.compile("^@@ -(\\d+)(?:,(\\d+))? \\+(\\d+)(?:,(\\d+))? @@.*$");
        for(int i=0;i<lines.length;i++){String line=lines[i];if(line.startsWith("\\ No newline"))continue;
            if(oldLeft>0||newLeft>0){if(line.isEmpty())throw new IllegalArgumentException("补丁行缺少前缀");char c=line.charAt(0);if(c==' '){oldLeft--;newLeft--;}else if(c=='-'){oldLeft--;deleted++;}else if(c=='+'){newLeft--;added++;}else throw new IllegalArgumentException("补丁内容前缀无效");if(oldLeft<0||newLeft<0)throw new IllegalArgumentException("补丁块行数超出声明");continue;}
            if(line.startsWith("+++ ")||line.startsWith("--- ")){String previous=file;file=line.substring(4).split("\\t",2)[0];if(file.startsWith("b/")||file.startsWith("a/"))file=file.substring(2);if(!file.equals("/dev/null")&&(file.startsWith("/")||file.contains("\\")||Arrays.asList(file.split("/")).contains("..")||file.contains("\"")))throw new IllegalArgumentException("补丁路径不安全");if(!file.equals("/dev/null")){files.add(file);String lower=file.toLowerCase(Locale.ROOT);if(lower.startsWith(".env")||lower.endsWith(".env")||lower.contains("/.env")||lower.endsWith(".pem")||lower.endsWith(".key")||lower.contains("secret"))sensitive.add(file);}if(line.startsWith("--- "))file=previous;continue;}
            var match=header.matcher(line);if(match.matches()){if(file==null)throw new IllegalArgumentException("缺少文件头");try{oldLeft=Integer.parseInt(match.group(2)==null?"1":match.group(2));newLeft=Integer.parseInt(match.group(4)==null?"1":match.group(4));if(oldLeft>100000||newLeft>100000)throw new IllegalArgumentException("块行数过大");}catch(NumberFormatException e){throw new IllegalArgumentException("块行数无效");}hunks++;continue;}
            if(line.isEmpty()||line.startsWith("--- ")||line.startsWith("diff --git ")||line.startsWith("index ")||line.startsWith("new file mode ")||line.startsWith("deleted file mode "))continue;
            throw new IllegalArgumentException("不支持的补丁元数据或二进制内容");
        }
        if(oldLeft!=0||newLeft!=0||hunks==0)throw new IllegalArgumentException("补丁块不完整");return Map.of("files",List.copyOf(files),"hunks",hunks,"additions",added,"deletions",deleted,"sensitivePaths",List.copyOf(sensitive),"requiresReview",!sensitive.isEmpty(),"appliesPatch",false);

    }
}
