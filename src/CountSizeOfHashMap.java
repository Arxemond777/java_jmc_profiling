import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 4k - 1358576     | 1358576/4k=339.644 - macbook
 * 400k - 19331296  | 19331296/40k=48.32824 - macbook
 * 4m - 150323968   | 150323968/4m=37.580992 - macbook
 * 24m - 900323968 (0.9gb) - *theoretic*
 * 40m - 1589526216 | 1589526216/40m=39.7381554 - macbook
 *
 * 40m - 2061499136 | 2061499136/40m=51,5374784 - my PC (correlation with the equal parameters V)
 * 40m - 4122998416 | 4122998416/40m=103.075 - my PC (correlation with the equal parameters ^)
 *
 * 80m - 3435976560 | 3435976560/80m=42,949707 - my PC
 * 2billions - 95153948728 | 95153948728/2b=47,576974364 - my PC
 *
 * -Xmx120g -Xms120g -Xmn120g -XX:SurvivorRatio=1000 -verbose:gc -XX:+UseSerialGC -XX:-UseGCOverheadLimit
 *
 * -Xmx120g -Xms120g -Xmn120g -verbose:gc -XX:+UseSerialGC -XX:-UseGCOverheadLimit // survivor остается большой (12.5х2 к 95 гб eden - т.е. 2 survivor в сумме - это 26.3% (каждый по 13.15%) от общей young)
 * -Xmx120g -Xms120g -Xmn120g -XX:SurvivorRatio=1000 -verbose:gc -XX:+UseSerialGC // остается только eden
 */
public class CountSizeOfHashMap {

    static Map<Character, Long> map = new HashMap<>();

    public static void main(String[] args) {
        final long before = Runtime.getRuntime().freeMemory();
        System.out.println(before);
        System.out.println("+++++");

        a();

        System.out.println("+++++");
        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(before - Runtime.getRuntime().freeMemory());
    }

    private static void a() {
        for (long i = 0; i < 8_000L; i++) {
            map.put((char)i, i + b());
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a();
    }

    private static long b() {
        final long l = new Random().nextInt(11);
        map.put('a', l);
        return l;
    }


    // 8_000_000_000 - 13GC - MAXNEW 28.633137152GB
    // 2_000_000_000 - 0GC - MAXNEW 120 -Xmn120g -XX:SurvivorRatio=1000
//    public static void main(String[] args) throws InterruptedException {
//
//        final long before = Runtime.getRuntime().freeMemory();
//        System.out.println(before);
//        System.out.println("+++++");
//
////        for (long i = 0; i < 2_000_000_000L; i++) {
//        for (long i = 0; i < 40_000_000L; i++) {
//            map.put((char) i, i);
//        }
//
//        System.out.println("+++++");
//        System.out.println(Runtime.getRuntime().freeMemory());
//        System.out.println(before - Runtime.getRuntime().freeMemory());
//    }
}