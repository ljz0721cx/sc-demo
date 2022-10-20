package com.sc.ynk.beanshell;

import bsh.EvalError;
import bsh.Interpreter;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janle on 2022/9/28
 */
public class WfBeanShellUtil {

    /**
     * 执行脚本
     *
     * @param script       脚本值
     * @param parameterMap 变量
     * @return
     */
    public static Object execute(String script, Map<String, Object> parameterMap) throws EvalError {
        if (StringUtils.isEmpty(script)) return null;
        Interpreter interpreter = new Interpreter(); // 构造一个解释器
        if (parameterMap != null && !parameterMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                interpreter.set(entry.getKey(), entry.getValue());   //变量赋值
            }
        }
        return interpreter.eval(script);
    }


    @Test
    public void test1() throws EvalError {
        String s = "int b = 1;\n" +
                "if(b==1){\n" +
                "\treturn true;\n" +
                "}else{\n" +
                "\treturn false;\n" +
                "}";
        System.out.println(execute(s, null));
    }

    /**
     * 传入变量方式
     *
     * @throws EvalError
     */
    @Test
    public void testVar() throws EvalError {
        String s = "int b = 1;\n" +
                "if(b==1 && a==\"2\"){\n" +
                "\treturn true;\n" +
                "}else{\n" +
                "\treturn false;\n" +
                "}";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("a", "1");
        System.out.println(execute(s, parameterMap));
    }

    @Test
    public void test2() {
        BSFManager mgr = new BSFManager();
        try {
            // Set variables
            mgr.declareBean("var1", 5, Integer.class);
            mgr.declareBean("date", new Date(), Date.class);

            String script = "import java.util.Date;"
                    + " String var1 = var1 + \"_\" + date.toString();"
                    + " return var1";
            Object result = mgr.eval("beanshell", "no", 0, 0, script);
            System.out.println("source result:" + result);
        } catch (BSFException ex) {
            ex.printStackTrace();
        }

    }
}
