public class Checkout {

    /**
     * 应用学生折扣：学生打9折，非学生原价
     * @param total 原始总金额
     * @param isStudent 是否为学生
     * @return 计算之后最终金额
     */
    public static double applyStudentDiscount(double total, boolean isStudent) {
        if (isStudent) {
            return total * 0.90;
        }
        return total;
    }

    // main方法做自测
    public static void main(String[] args) {
        // 测试用例1：学生，原价100 → 预期90
        double studentPrice = applyStudentDiscount(100, true);
        System.out.println("学生价格(原价100): " + studentPrice);

        // 测试用例2：非学生，原价100 → 预期100
        double normalPrice = applyStudentDiscount(100, false);
        System.out.println("非学生价格(原价100): " + normalPrice);
    }
}
