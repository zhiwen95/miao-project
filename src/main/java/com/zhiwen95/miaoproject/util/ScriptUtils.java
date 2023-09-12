package com.zhiwen95.miaoproject.util;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.script.AviatorScriptEngine;

import javax.script.ScriptEngine;
import java.util.Map;

public class ScriptUtils {
    private static final AviatorEvaluatorInstance INSTANCE;
    static {
        INSTANCE = AviatorEvaluator.newInstance();

    }

    public static Object execute(String expression, Map<String, Object> env) {
        return INSTANCE.execute(expression, env);
    }

    public static Object execute(String expression) {
        return INSTANCE.execute(expression);
    }
}
