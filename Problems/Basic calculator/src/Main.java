class Problem {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int result = 0;

        switch (args[0]) {
            case "+": result = x + y;
                break;
            case "-": result = x - y;
                break;
            case "*": result = x * y;
                break;
            default:
                System.out.println("Unknown operator");
                System.exit(0);
                break;
        }

        System.out.println(result);
    }
}