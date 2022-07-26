/**
 * @Author MCSQNXA
 * @CreateTime 2022-07-26 10:56:21
 * @Description 实列
 */
public class Demo {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        String s = "1+23*4/5+(6*9%7/(8+9)*2)";

        System.out.println("计算数字=" + s);
        System.out.println("计算结果=" + calculator.exec(s));
    }


}
