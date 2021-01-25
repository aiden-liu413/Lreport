package com.kxingyi.common.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 
 * @author chengpan
 * 2018/8/28
 */
public class SpringELUtil {

	public static Object parseValue(Object target, String expr) {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(expr);
		EvaluationContext context = new StandardEvaluationContext(target);
		return exp.getValue(context);
	}
}
