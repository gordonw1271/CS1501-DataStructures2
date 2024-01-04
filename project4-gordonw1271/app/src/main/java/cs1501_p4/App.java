/**
 * A driver for CS1501 Project 4
 * @author    Dr. Farnan
 */
package cs1501_p4;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        NetAnalysis na2 = new NetAnalysis("build/resources/main/network_data2.txt");
        ArrayList<Integer> x = new ArrayList<Integer>();
        x.add(0);
        x.add(1);
        x.add(7);
        System.out.println(na2.bandwidthAlongPath(x));
        System.out.println(na2.lowestLatencyPath(0, 8));

        NetAnalysis na11 = new NetAnalysis("build/resources/main/network_data11.txt");
        //System.out.println(na11.bandwidthAlongPath(x));
        System.out.println(na11.copperOnlyConnected());
    }
}
