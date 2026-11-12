import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * 带购物车和学生折扣功能的结账程序。
 * 商品名称、价格、数量及顾客身份全部由操作者输入，不包含固定测试数据。
 */
public class Checkout {

    // 学生折扣率为 10%
    private static final double STUDENT_DISCOUNT_RATE = 0.10;

    /** 表示购物车中的一种商品。 */
    private static class CartItem {
        private final String name;
        private final double price;
        private int quantity;

        public CartItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        // 增加商品数量
        public void increaseQuantity(int amount) {
            quantity += amount;
        }

        // 计算该商品的总价
        public double getItemTotal() {
            return price * quantity;
        }
    }

    /** 管理购物车中的商品。 */
    private static class ShoppingCart {
        private final List<CartItem> items = new ArrayList<>();

        /** 添加商品；同名同价格商品会自动累加数量。 */
        public void addItem(String name, double price, int quantity) {
            for (CartItem item : items) {
                if (item.name.equalsIgnoreCase(name)
                        && Double.compare(item.price, price) == 0) {
                    item.increaseQuantity(quantity);
                    System.out.printf("商品数量已更新：%s × %d%n", item.name, item.quantity);
                    return;
                }
            }

            items.add(new CartItem(name, price, quantity));
            System.out.printf("已将 %s × %d 添加到购物车。%n", name, quantity);
        }

        /** 根据商品名称删除商品。 */
        public boolean removeItem(String name) {
            Iterator<CartItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.name.equalsIgnoreCase(name)) {
                    iterator.remove();
                    System.out.println("商品已删除：" + item.name);
                    return true;
                }
            }

            System.out.println("购物车中没有找到该商品：" + name);
            return false;
        }

        /** 计算购物车中所有商品的原始总额。 */
        public double calculateSubtotal() {
            double subtotal = 0.0;
            for (CartItem item : items) {
                subtotal += item.getItemTotal();
            }
            return subtotal;
        }

        /** 显示购物车内容。 */
        public void displayCart() {
            System.out.println();
            System.out.println("================ 购物车 ================");

            if (items.isEmpty()) {
                System.out.println("购物车为空。");
                System.out.println("========================================");
                return;
            }

            System.out.printf("%-18s %-10s %-8s %-12s%n",
                    "商品名称", "单价", "数量", "商品总价");
            System.out.println("----------------------------------------");

            for (CartItem item : items) {
                System.out.printf("%-18s £%-9.2f %-8d £%-11.2f%n",
                        item.name, item.price, item.quantity, item.getItemTotal());
            }

            System.out.println("----------------------------------------");
            System.out.printf("购物车原始总额：£%.2f%n", calculateSubtotal());
            System.out.println("========================================");
        }

        /** 清空购物车。 */
        public void clearCart() {
            items.clear();
            System.out.println("购物车已清空。");
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }
    }

    /**
     * 根据顾客身份计算最终金额。
     * 学生支付原价的 90%，非学生支付原价。
     */
    public static double applyStudentDiscount(double total, boolean isStudent) {
        if (isStudent) {
            return total * (1 - STUDENT_DISCOUNT_RATE);
        }
        return total;
    }

    /** 显示结账结果。 */
    private static void checkout(ShoppingCart cart, boolean isStudent) {
        if (cart.isEmpty()) {
            System.out.println("购物车为空，无法结账。");
            return;
        }

        double originalTotal = cart.calculateSubtotal();
        double finalTotal = applyStudentDiscount(originalTotal, isStudent);
        double discountAmount = originalTotal - finalTotal;

        System.out.println();
        System.out.println("============== 结账信息 ==============");
        System.out.println("顾客身份：" + (isStudent ? "学生" : "非学生"));
        System.out.printf("原始购物总额：£%.2f%n", originalTotal);
        System.out.println("学生折扣：" + (isStudent ? "10%" : "0%"));
        System.out.printf("折扣金额：£%.2f%n", discountAmount);
        System.out.printf("最终付款金额：£%.2f%n", finalTotal);
        System.out.println("======================================");
    }

    /** 显示操作者菜单。 */
    private static void displayMenu() {
        System.out.println();
        System.out.println("========== 在线购物系统 ==========");
        System.out.println("1. 添加商品");
        System.out.println("2. 删除商品");
        System.out.println("3. 查看购物车");
        System.out.println("4. 结账");
        System.out.println("5. 清空购物车");
        System.out.println("0. 退出程序");
        System.out.println("================================");
    }

    /** 安全读取一个整数。 */
    private static int readInteger(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("输入错误，请输入整数。");
            }
        }
    }

    /** 安全读取一个小数。 */
    private static double readDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (Double.isFinite(value)) {
                    return value;
                }
                System.out.println("请输入有限的数字。");
            } catch (NumberFormatException exception) {
                System.out.println("输入错误，请输入有效数字。");
            }
        }
    }

    /** 读取非空商品名称。 */
    private static String readProductName(Scanner scanner) {
        while (true) {
            System.out.print("请输入商品名称：");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                return name;
            }
            System.out.println("商品名称不能为空，请重新输入。");
        }
    }

    /** 让操作者输入商品信息并加入购物车。 */
    private static void handleAddItem(Scanner scanner, ShoppingCart cart) {
        String name = readProductName(scanner);

        double price;
        while (true) {
            price = readDouble(scanner, "请输入商品单价：");
            if (price >= 0) {
                break;
            }
            System.out.println("商品单价不能为负数。");
        }

        int quantity;
        while (true) {
            quantity = readInteger(scanner, "请输入商品数量：");
            if (quantity > 0) {
                break;
            }
            System.out.println("商品数量必须大于 0。");
        }

        cart.addItem(name, price, quantity);
    }

    /** 让操作者输入商品名称并删除商品。 */
    private static void handleRemoveItem(Scanner scanner, ShoppingCart cart) {
        if (cart.isEmpty()) {
            System.out.println("购物车为空，没有可以删除的商品。");
            return;
        }

        cart.displayCart();
        System.out.print("请输入需要删除的商品名称：");
        cart.removeItem(scanner.nextLine().trim());
    }

    /** 让操作者选择学生身份并结账。 */
    private static void handleCheckout(Scanner scanner, ShoppingCart cart) {
        if (cart.isEmpty()) {
            System.out.println("购物车为空，无法结账。");
            return;
        }

        cart.displayCart();
        while (true) {
            System.out.print("顾客是否为学生？（yes/no）：");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("yes")
                    || answer.equalsIgnoreCase("y")
                    || answer.equals("是")) {
                checkout(cart, true);
                return;
            }

            if (answer.equalsIgnoreCase("no")
                    || answer.equalsIgnoreCase("n")
                    || answer.equals("否")) {
                checkout(cart, false);
                return;
            }

            System.out.println("输入错误，请输入 yes、no、是或否。");
        }
    }

    /** 程序入口：购物数据和结账身份全部由操作者通过菜单输入。 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();
        boolean running = true;

        System.out.println("欢迎使用在线购物结账系统！");

        while (running) {
            displayMenu();
            int choice = readInteger(scanner, "请选择操作：");

            switch (choice) {
                case 1:
                    handleAddItem(scanner, cart);
                    break;
                case 2:
                    handleRemoveItem(scanner, cart);
                    break;
                case 3:
                    cart.displayCart();
                    break;
                case 4:
                    handleCheckout(scanner, cart);
                    break;
                case 5:
                    System.out.print("确定清空购物车吗？（yes/no）：");
                    String confirmation = scanner.nextLine().trim();
                    if (confirmation.equalsIgnoreCase("yes")
                            || confirmation.equalsIgnoreCase("y")
                            || confirmation.equals("是")) {
                        cart.clearCart();
                    } else {
                        System.out.println("已取消清空购物车。");
                    }
                    break;
                case 0:
                    running = false;
                    System.out.println("感谢使用，再见！");
                    break;
                default:
                    System.out.println("没有这个选项，请重新选择。");
            }
        }

        scanner.close();
    }
}
