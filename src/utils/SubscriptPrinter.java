package utils;

public class SubscriptPrinter {

    public static String subscript(int value) {
        StringBuilder builder = new StringBuilder();
        do {
            switch (value % 10) {
                case 0:
                    builder.append((char)0x2080);
                    break;
                case 1:
                    builder.append((char)0x2081);
                    break;
                case 2:
                    builder.append((char)0x2082);
                    break;
                case 3:
                    builder.append((char)0x2083);
                    break;
                case 4:
                    builder.append((char)0x2084);
                    break;
                case 5:
                    builder.append((char)0x2085);
                    break;
                case 6:
                    builder.append((char)0x2086);
                    break;
                case 7:
                    builder.append((char)0x2087);
                    break;
                case 8:
                    builder.append((char)0x2088);
                    break;
                case 9:
                    builder.append((char)0x2089);
                    break;
                default:
                    break;
            }
            value /= 10;
        } while (value != 0);
        return builder.reverse().toString();
    }
}
