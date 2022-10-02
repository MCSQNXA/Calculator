import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @Author MCSQNXA
 * @CreateTime 2022-07-25 09:59:18
 * @Description 计算器实现   Calculator implementation
 */
public class Calculator {

    /**
     * @CreateTime 2022-07-26 09:40:12
     * @Description 计算   calculation
     */
    public double exec(String str) {
        //临时栈
        Stack<Double> stack = new Stack<>();
        //后缀表达式
        ArrayList<String> list = this.infixToSuffix(str);

        for (String v : list) {
            if (this.symbol.contains(v.charAt(0))) {//运算符
                Double v0 = stack.pop();
                Double v1 = stack.pop();

                switch (v.charAt(0)) {
                    case '+':
                        stack.push(v1 + v0);
                        break;

                    case '-':
                        stack.push(v1 - v0);
                        break;

                    case '*':
                        stack.push(v1 * v0);
                        break;

                    case '/':
                        stack.push(v1 / v0);
                        break;

                    case '%':
                        stack.push(v1 % v0);
                        break;

                    default:
                        throw new RuntimeException("No Supported " + v);
                }
            } else {
                stack.push(Double.valueOf(v));//数字
            }
        }

        if (stack.size() == 1) {//计算完毕
            return stack.pop();
        }

        return 0.0;
    }

    /**
     * @CreateTime 2022-07-25 11:58:40
     * @Description 中缀表达式 转 后缀表达式   Infix expression to suffix expression
     */
    private ArrayList<String> infixToSuffix(String str) {
        if (str.length() > 2) {//处理 -50+10 变成 0-50+10
            if (str.charAt(0) == '+') {
                str = "0" + str;
            } else if (str.charAt(0) == '-') {
                str = "0" + str;
            }
        }

        //结果集合
        ArrayList<String> result = new ArrayList<>();
        //中缀表达式 转 列表
        ArrayList<String> list = this.infixToList(str);
        //临时操作符栈
        Stack<String> stack = new Stack<>();
        //栈低结束符
        stack.add("#");

        for (int i = 0; i < list.size(); i++) {
            if (this.symbol.contains(list.get(i).charAt(0))) {//运算符
                if (list.get(i).charAt(0) == '(') {
                    stack.push("(");//入栈
                } else if (list.get(i).charAt(0) == ')') {
                    while (!"(".equals(stack.peek())) {
                        result.add(stack.pop());//栈内运算符 到 后缀表达式 列表
                    }

                    stack.pop();// ( 出栈
                } else if (this.priority(stack.peek().charAt(0)) >= this.priority(list.get(i).charAt(0))) {//栈顶 运算符 优先级 比当前 运算符 优先级 高
                    result.add(stack.pop());//栈顶 运算符 出栈 到 后缀表达式列表

                    stack.push(list.get(i));//当前 运算符 入栈
                } else {
                    stack.push(list.get(i));//当前 运算符 入栈
                }
            } else {//待求数字
                result.add(list.get(i));
            }
        }

        while (!"#".equals(stack.peek())) {
            result.add(stack.pop());//栈内运算符 到 后缀表达式 列表
        }

        return result;
    }

    /**
     * @CreateTime 2022-07-25 10:03:53
     * @Description 中缀表达式 拆成 列表   Infix expression split into list
     */
    private ArrayList<String> infixToList(String str) {
        //拆出结果集合
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {//遍历所有字符
            if (this.symbol.contains(str.charAt(i))) {//运算符
                result.add(new String(new char[]{str.charAt(i)}));
            } else if (this.number.contains(str.charAt(i))) {//数字
                StringBuilder builder = new StringBuilder();

                while (i < str.length()) {
                    if (this.number.contains(str.charAt(i))) {
                        builder.append(str.charAt(i++));
                    } else {
                        i--;
                        break;
                    }
                }

                result.add(builder.toString());
            } else if (str.charAt(i) == ' ') {
                continue;
            } else {
                throw new RuntimeException("error=" + str.charAt(i));
            }
        }

        return result;
    }

    /**
     * @CreateTime 2022-07-25 12:20:02
     * @Description 运算段 运算符   Operation segment operator
     */
    private final List<Character> symbol = Arrays.asList('(', ')', '+', '-', '*', '/', '%');

    /**
     * @CreateTime 2022-07-25 12:20:42
     * @Description 运算段 待求数字   Number to be found in operation section
     */
    private final List<Character> number = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.');

    /**
     * @CreateTime 2022-07-25 19:30:24
     * @Description 运算符优先级   Operator priority
     */
    private int priority(char c) {
        switch (c) {
            case '#':
                return 0;

            case '(':
                return 0;

            case '+':
                return 1;

            case '-':
                return 1;

            case '*':
                return 2;

            case '/':
                return 2;

            case '%':
                return 2;

            case ')':
                return 3;

            default:
                throw new RuntimeException("No Supported " + c);
        }
    }


}
