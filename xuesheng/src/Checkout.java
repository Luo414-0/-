// 定义 Checkout 类，用于实现结账时的学生折扣功能
public class Checkout {

    /**
     * 根据顾客是否为学生计算最终付款金额。
     *
     * @param total 原始购物总金额
     * @param isStudent 是否为学生：true 表示是学生，false 表示不是学生
     * @return 应用学生折扣后的最终付款金额
     */
    public static double applyStudentDiscount(double total, boolean isStudent) {
        // 如果顾客是学生，则享受 10% 折扣，即支付原价的 90%
        if (isStudent) {
            return total * 0.90;
        }

        // 如果顾客不是学生，则不享受折扣，返回原始金额
        return total;
    }

    /**
     * 程序入口，用于测试学生和非学生两种结账情况。
     *
     * @param args 命令行参数，本程序中未使用
     */
    public static void main(String[] args) {
        // 设置原始购物总金额为 100 英镑
        double originalTotal = 100.0;

        // 输出原始购物总金额，并保留两位小数
        System.out.printf("Original purchase total: £%.2f%n", originalTotal);

        // 输出学生顾客可以享受的折扣比例
        System.out.println("Student discount: 10%");

        // ==================== 学生顾客测试 ====================

        // 参数 true 表示顾客是学生，调用方法计算折后金额
        double studentPrice = applyStudentDiscount(originalTotal, true);

        // 输出学生顾客测试标题
        System.out.println("----[Customer is student]----");

        // 折扣金额等于原始金额减去折后金额
        System.out.printf("Discount amount: £%.2f%n", originalTotal - studentPrice);

        // 输出学生顾客最终需要支付的金额
        System.out.printf("Final purchase total: £%.2f%n%n", studentPrice);

        // ==================== 非学生顾客测试 ====================

        // 参数 false 表示顾客不是学生，最终金额保持原价
        double normalPrice = applyStudentDiscount(originalTotal, false);

        // 输出非学生顾客测试标题
        System.out.println("----[Customer is NOT student]----");

        // 非学生顾客不享受折扣，因此折扣金额为 0
        System.out.println("Discount amount: £0.00");

        // 输出非学生顾客最终需要支付的金额
        System.out.printf("Final purchase total: £%.2f%n", normalPrice);
    }
}
