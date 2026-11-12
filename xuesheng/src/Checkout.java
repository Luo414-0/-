import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 在线购物结账程序。
 *
 * 功能：
 * 1. 添加商品到购物车；
 * 2. 删除购物车中的商品；
 * 3. 查看购物车内容；
 * 4. 计算购物车原始总额；
 * 5. 为学生顾客提供 10% 折扣；
 * 6. 计算并显示最终付款金额。
 */
public class Checkout {

    // 学生折扣率为 10%
    private static final double STUDENT_DISCOUNT_RATE = 0.10;

    /**
     * 表示购物车中的一项商品。
     */
    private static class CartItem {

        // 商品名称
        private final String name;

        // 商品单价
        private final double price;

        // 商品数量
        private int quantity;

        /**
         * 创建购物车商品。
         *
         * @param name     商品名称
         * @param price    商品单价
         * @param quantity 商品数量
         */
        public CartItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        /**
         * 增加商品数量。
         *
         * @param amount 需要增加的数量
         */
        public void increaseQuantity(int amount) {
            quantity += amount;
        }

        /**
         * 计算该商品的总价。
         *
         * 商品总价 = 商品单价 × 商品数量
         *
         * @return 该商品的总价
         */
        public double getItemTotal() {
            return price * quantity;
        }
    }

    /**
     * 购物车类，负责保存和管理商品。
     */
    private static class ShoppingCart {

        // 使用 List 保存购物车中的商品
        private final List<CartItem> items = new ArrayList<>();

        /**
         * 添加商品到购物车。
         *
         * 如果购物车中已有同名、同价格的商品，
         * 则直接增加该商品的数量。
         *
         * @param name     商品名称
         * @param price    商品单价
         * @param quantity 商品数量
         */
        public void addItem(String name, double price, int quantity) {
            // 检查商品名称
            if (name == null || name.trim().isEmpty()) {
                System.out.println("商品名称不能为空。");
                return;
            }

            // 商品价格不能为负数
            if (price < 0) {
                System.out.println("商品价格不能为负数。");
                return;
            }

            // 商品数量必须大于 0
            if (quantity <= 0) {
                System.out.println("商品数量必须大于 0。");
                return;
            }

            // 查找购物车中是否已经存在相同商品
            for (CartItem item : items) {
                if (item.name.equalsIgnoreCase(name)
                        && Double.compare(item.price, price) == 0) {

                    // 如果商品已经存在，累加商品数量
                    item.increaseQuantity(quantity);

                    System.out.printf(
                            "商品数量已更新：%s × %d%n",
                            item.name,
                            item.quantity
                    );
                    return;
                }
            }

            // 如果商品不存在，则添加一个新的购物车项目
            items.add(new CartItem(name, price, quantity));

            System.out.printf(
                    "已添加商品：%s × %d%n",
                    name,
                    quantity
            );
        }

        /**
         * 根据商品名称删除商品。
         *
         * @param name 需要删除的商品名称
         * @return 删除成功返回 true，否则返回 false
         */
        public boolean removeItem(String name) {
            Iterator<CartItem> iterator = items.iterator();

            while (iterator.hasNext()) {
                CartItem item = iterator.next();

                // 忽略大小写比较商品名称
                if (item.name.equalsIgnoreCase(name)) {
                    iterator.remove();
                    System.out.println("已删除商品：" + item.name);
                    return true;
                }
            }

            System.out.println("购物车中没有找到商品：" + name);
            return false;
        }

        /**
         * 计算购物车原始总额。
         *
         * @return 所有商品总价之和
         */
        public double calculateSubtotal() {
            double subtotal = 0.0;

            // 累加购物车中每一种商品的总价
            for (CartItem item : items) {
                subtotal += item.getItemTotal();
            }

            return subtotal;
        }

        /**
         * 显示购物车中的全部商品。
         */
        public void displayCart() {
            System.out.println();
            System.out.println("============== 购物车 ==============");

            // 判断购物车是否为空
            if (items.isEmpty()) {
                System.out.println("购物车为空。");
                System.out.println("====================================");
                return;
            }

            // 输出表头
            System.out.printf(
                    "%-15s %10s %8s %12s%n",
                    "商品名称",
                    "单价",
                    "数量",
                    "商品总价"
            );

            System.out.println("------------------------------------");

            // 逐个输出商品信息
            for (CartItem item : items) {
                System.out.printf(
                        "%-15s £%9.2f %8d £%11.2f%n",
                        item.name,
                        item.price,
                        item.quantity,
                        item.getItemTotal()
                );
            }

            System.out.println("------------------------------------");

            // 输出购物车原始总额
            System.out.printf(
                    "购物车原始总额：£%.2f%n",
                    calculateSubtotal()
            );

            System.out.println("====================================");
            System.out.println();
        }

        /**
         * 清空购物车中的所有商品。
         */
        public void clearCart() {
            items.clear();
            System.out.println("购物车已清空。");
        }

        /**
         * 判断购物车是否为空。
         *
         * @return 购物车为空返回 true，否则返回 false
         */
        public boolean isEmpty() {
            return items.isEmpty();
        }
    }

    /**
     * 根据顾客是否为学生计算最终付款金额。
     *
     * @param total     原始购物总额
     * @param isStudent 是否为学生
     * @return 折扣后的最终付款金额
     */
    public static double applyStudentDiscount(
            double total,
            boolean isStudent
    ) {
        // 总金额不能为负数
        if (total < 0) {
            throw new IllegalArgumentException("购物总额不能为负数。");
        }

        // 学生顾客享受 10% 折扣
        if (isStudent) {
            return total * (1 - STUDENT_DISCOUNT_RATE);
        }

        // 非学生顾客按照原价付款
        return total;
    }

    /**
     * 对购物车中的商品进行结账。
     *
     * @param cart      需要结账的购物车
     * @param isStudent 顾客是否为学生
     */
    public static void checkout(
            ShoppingCart cart,
            boolean isStudent
    ) {
        System.out.println();
        System.out.println("============== 结账信息 ==============");

        // 如果购物车为空，则不能结账
        if (cart.isEmpty()) {
            System.out.println("购物车为空，无法结账。");
            System.out.println("======================================");
            return;
        }

        // 计算购物车原始总额
        double originalTotal = cart.calculateSubtotal();

        // 计算应用学生折扣后的最终付款金额
        double finalTotal =
                applyStudentDiscount(originalTotal, isStudent);

        // 折扣金额 = 原始总额 - 最终金额
        double discountAmount = originalTotal - finalTotal;

        // 输出顾客身份
        System.out.println(
                "顾客身份：" + (isStudent ? "学生" : "非学生")
        );

        // 输出原始购物总额
        System.out.printf(
                "原始购物总额：£%.2f%n",
                originalTotal
        );

        // 输出折扣比例
        System.out.printf(
                "学生折扣：%s%n",
                isStudent ? "10%" : "0%"
        );

        // 输出折扣金额
        System.out.printf(
                "折扣金额：£%.2f%n",
                discountAmount
        );

        // 输出最终付款金额
        System.out.printf(
                "最终付款金额：£%.2f%n",
                finalTotal
        );

        System.out.println("======================================");
        System.out.println();
    }

    /**
     * 程序入口，用于演示购物车和学生折扣功能。
     *
     * @param args 命令行参数，本程序中未使用
     */
    public static void main(String[] args) {

        // 创建一个新的购物车
        ShoppingCart cart = new ShoppingCart();

        // 添加商品：商品名称、商品单价、商品数量
        cart.addItem("Java Book", 40.00, 2);
        cart.addItem("Notebook", 5.00, 3);
        cart.addItem("Pen", 2.50, 2);

        // 再次添加同名同价格商品，数量会自动累加
        cart.addItem("Pen", 2.50, 1);

        // 显示购物车内容
        cart.displayCart();

        // 学生顾客结账测试
        checkout(cart, true);

        // 非学生顾客结账测试
        checkout(cart, false);

        // 删除购物车中的 Notebook
        cart.removeItem("Notebook");

        // 显示删除商品后的购物车
        cart.displayCart();
    }
}