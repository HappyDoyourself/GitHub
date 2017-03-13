package cn.com.jiuyao.pay.common.framework.util;

/**
 * Created by Larry on 2015/9/24.
 * Assertion utility class that assists in validating arguments.
 * 继承于org.springframework.util.Assert，添加参数格式校验方法
 */
public class PayAssert extends org.springframework.util.Assert {
    /**
     * Assert a boolean expression, throwing {@code IllegalArgumentException}
     * if the test result is {@code false}. Call isTrue if you wish to
     * throw IllegalArgumentException on an assertion failure.
     * <pre class="code">PayAssert.argument(id == null, "The id property must not already be initialized");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void argument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing {@link IllegalStateException}
     * if the test result is {@code false}.
     * <p>Call {@link #isTrue(boolean)} if you wish to
     * throw {@link IllegalArgumentException} on an assertion failure.
     * <pre class="code">PayAssert.argument(id == null);</pre>
     * @param expression a boolean expression
     * @throws IllegalArgumentException if the supplied expression is {@code false}
     */
    public static void argument(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

    public static void notNull(Object object, String message) {
        if(object == null || "".equals(object.toString())) {
            throw new IllegalArgumentException(message);
        }
    }
}
