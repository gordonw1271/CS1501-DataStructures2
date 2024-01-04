/**
 * Basic tests for CS1501 Project 4
 * @author    Dr. Farnan
 */
package cs1501_p4;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;

import static java.time.Duration.ofSeconds;

class BasicTests {
    @Test
    @DisplayName("Check if it can read graph properly")
    void basic_read() {
        NetAnalysis na = init_basic(1);
    }

    @Test
    @DisplayName("Check lowest latency path")
    void basic_llp() {
        NetAnalysis na = init_basic(1);

        ArrayList<Integer> res = na.lowestLatencyPath(0, 4);

        ArrayList<Integer> exp = new ArrayList<Integer>();
        exp.add(0);
        exp.add(4);

        for (int i = 0; i < exp.size(); i++) {
            assertEquals(exp.get(i), res.get(i), "Incorrect vertex on path");
        }
    }

    @Test
    @DisplayName("Check bandwidth along the path")
    void basic_bap() {
        NetAnalysis na = init_basic(1);

        ArrayList<Integer> path = na.lowestLatencyPath(0, 4);
        int res = na.bandwidthAlongPath(path);

        assertEquals(100, res, "Incorrect bandwidth");
    }

    @Test
    @DisplayName("Check if it is connected only by copper cables")
    void basic_coc() {
        NetAnalysis na = init_basic(1);

        assertTrue(na.copperOnlyConnected());
    }

    @Test
    @DisplayName("Check if it survives any two failed vertices")
    void basic_stfv() {
        NetAnalysis na = init_basic(1);

        assertTrue(na.connectedTwoVertFail());
    }

    @Test
    @DisplayName("Check the lowest average latency spanning tree")
    void basic_lalst() {
        NetAnalysis na = init_basic(1);

        ArrayList<STE> res = na.lowestAvgLatST();

        ArrayList<STE> exp = new ArrayList<STE>();
        exp.add(new STE(0, 4));
        exp.add(new STE(1, 4));
        exp.add(new STE(2, 4));
        exp.add(new STE(3, 4));

        assertEquals(exp.size(), res.size(), "Incorrect number of spanning tree edges");
        for (STE i : exp) {
            boolean found = false;
            for (STE j : res) {
                if (i.equals(j)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Invalid spanning tree edge");
        }
    }

    NetAnalysis init_basic(int num) {
        if(num == 1){
            return new NetAnalysis("build/resources/test/network_data1.txt");
        }else if(num == 2){
            return new NetAnalysis("build/resources/main/network_data2.txt");
        }else if(num ==3){
            return new NetAnalysis("build/resources/main/network_data3.txt");
        }else if(num ==4){
            return new NetAnalysis("build/resources/main/network_data4.txt");
        }else if(num ==5){
            return new NetAnalysis("build/resources/main/network_data5.txt");
        }else if(num ==6){
            return new NetAnalysis("build/resources/main/network_data6.txt");
        }else if(num ==7){
            return new NetAnalysis("build/resources/main/network_data7.txt");
        }else if(num ==8){
            return new NetAnalysis("build/resources/main/network_data8.txt");
        }else if(num ==9){
            return new NetAnalysis("build/resources/main/network_data9.txt");
        }else{
            return null;
        }
    }

    @Test
    @DisplayName("Gordon's Test Min Spanning Tree")
    void test1() {
        NetAnalysis na = init_basic(3);

        ArrayList<STE> res = na.lowestAvgLatST();

        ArrayList<STE> exp = new ArrayList<STE>();
        exp.add(new STE(0, 1));
        exp.add(new STE(0, 2));
        exp.add(new STE(2, 3));
        exp.add(new STE(2, 3));
        exp.add(new STE(5, 4));
        exp.add(new STE(5, 6));

        assertEquals(exp.size(), res.size(), "Incorrect number of spanning tree edges");
        for (STE i : exp) {
            boolean found = false;
            for (STE j : res) {
                if (i.equals(j)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Invalid spanning tree edge");
        }
    }

    @Test
    @DisplayName("Gordon's Test Connected 2 Vert")
    void test2() {
        NetAnalysis na = init_basic(3);

        assertTrue(!na.connectedTwoVertFail());
    }

    @Test
    @DisplayName("Gordon's Test Lowest Path")
    void test3() {
        NetAnalysis na = init_basic(3);

        ArrayList<Integer> res = na.lowestLatencyPath(0, 6);

        ArrayList<Integer> exp = new ArrayList<Integer>();
        exp.add(0);
        exp.add(2);
        exp.add(5);
        exp.add(6);

        for (int i = 0; i < exp.size(); i++) {
            assertEquals(exp.get(i), res.get(i), "Incorrect vertex on path");
        }
    }

    @Test
    @DisplayName("Gordon's Test Copper")
    void test4() {
        NetAnalysis na = init_basic(3);

        assertTrue(na.copperOnlyConnected());
    }

    @Test
    @DisplayName("Check bandwidth along the path")
    void test41() {
        NetAnalysis na = init_basic(3);

        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(0);
        path.add(1);
        path.add(3);
        path.add(5);
        path.add(2);

        int res = na.bandwidthAlongPath(path);

        assertEquals(100, res, "Incorrect bandwidth");
    }
    
    // ========================================================================

    @Test
    @DisplayName("Gordon's Test Min Spanning Tree")
    void test5() {
        NetAnalysis na = init_basic(4);

        ArrayList<STE> res = na.lowestAvgLatST();

        ArrayList<STE> exp = new ArrayList<STE>();
        exp.add(new STE(0, 2));
        exp.add(new STE(2, 1));
        exp.add(new STE(1, 4));
        exp.add(new STE(2, 5));
        exp.add(new STE(5, 3));

        assertEquals(exp.size(), res.size(), "Incorrect number of spanning tree edges");
        for (STE i : exp) {
            boolean found = false;
            for (STE j : res) {
                if (i.equals(j)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Invalid spanning tree edge");
        }
    }

    @Test
    @DisplayName("Gordon's Test Connected 2 Vert")
    void test6() {
        NetAnalysis na = init_basic(4);

        assertTrue(na.connectedTwoVertFail());
    }

    @Test
    @DisplayName("Gordon's Test Lowest Path")
    void test7() {
        NetAnalysis na = init_basic(4);

        ArrayList<Integer> res = na.lowestLatencyPath(0, 4);

        ArrayList<Integer> exp = new ArrayList<Integer>();
        exp.add(0);
        exp.add(2);
        exp.add(4);

        for (int i = 0; i < exp.size(); i++) {
            assertEquals(exp.get(i), res.get(i), "Incorrect vertex on path");
        }
    }

    @Test
    @DisplayName("Gordon's Test Copper")
    void test8() {
        NetAnalysis na = init_basic(4);

        assertTrue(!na.copperOnlyConnected());
    }

    @Test
    @DisplayName("Check bandwidth along the path")
    void test51() {
        NetAnalysis na = init_basic(4);

        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(4);
        path.add(2);
        path.add(3);
        path.add(5);

        int res = na.bandwidthAlongPath(path);

        assertEquals(10, res, "Incorrect bandwidth");
    }

    @Test
    @DisplayName("Check lowest latency path")
    void test14() {
        NetAnalysis na = init_basic(1);

        ArrayList<Integer> res = na.lowestLatencyPath(0, 4);

        ArrayList<Integer> exp = new ArrayList<Integer>();
        exp.add(0);
        exp.add(4);

        for (int i = 0; i < exp.size(); i++) {
            assertEquals(exp.get(i), res.get(i), "Incorrect vertex on path");
        }
    }

    // JUSTIN'S TESTS
    @Test
    @DisplayName("Justin's tests: extremely large graph (basicaly a linked list)")
    void large_graph() {
        NetAnalysis na = init_basic(5);

        ArrayList<Integer> res = na.lowestLatencyPath(0,9999);

        for(int i=0; i<10000;i++){
            assertEquals(i, res.get(i), "Incorrect path for large graph");
        }
    }

    @Test
    @DisplayName("Justin's tests: fully connected graph")
    void fully_connected() {
        NetAnalysis na = init_basic(6);

        ArrayList<Integer> res = na.lowestLatencyPath(0,6);

        for(int i=0; i<7;i++){
            assertEquals(i, res.get(i), "Incorrect path for fully connected graph");
        }

        assertTrue(!na.copperOnlyConnected(), "Copper only connected failed");
    }

    @Test
    @DisplayName("Justin's tests: graph 7")
    void graph7() {
        NetAnalysis na = init_basic(7);

        ArrayList<Integer> res = na.lowestLatencyPath(0,6);

        assertTrue(!na.connectedTwoVertFail(), "graph should not be connected when removing 2 vertices");
        assertTrue(na.copperOnlyConnected(), "Copper only connected failed");
    }

    @Test
    @DisplayName("Justin's tests: graph 8")
    void graph8() {
        NetAnalysis na = init_basic(8);

        // check every illegal path combination
        for(int i=0; i<8; i++){
            for(int j=8; j<=9; j++){
                ArrayList<Integer> res = na.lowestLatencyPath(i,j);
                assertTrue(res==null, "lowest latency path of nonexist path didn't return null");
            }
        }
        
        // check every legal path combination
        for(int i=0; i<8; i++){
            for(int j=i+1; j<8; j++){
                ArrayList<Integer> res = na.lowestLatencyPath(i,j);
                assertTrue(res!=null, "lowest latency path of nonexist path didn't return null");
            }
        }
        
        // check a lot of illegal path combination
        for(int i=0; i<8; i++){
            for(int j=8; j<=9; j++){
                ArrayList<Integer> p = new ArrayList<>();
                p.add(i);
                p.add(j);
                assertThrows(IllegalArgumentException.class, () -> na.bandwidthAlongPath(p));
            }
        }
        
        // check some legal path combinations
        ArrayList<Integer> p = new ArrayList<>();
        p.add(1);
        p.add(2);
        assertDoesNotThrow(() -> na.bandwidthAlongPath(p));
        assertEquals(na.bandwidthAlongPath(p), 4, "incorrect bandwidth");
        p.add(4);
        assertDoesNotThrow(() -> na.bandwidthAlongPath(p));
        assertEquals(na.bandwidthAlongPath(p), 3, "incorrect bandwidth");
        p.add(3);
        assertDoesNotThrow(() -> na.bandwidthAlongPath(p));
        assertEquals(na.bandwidthAlongPath(p), 2, "incorrect bandwidth");
        p.add(5);
        assertDoesNotThrow(() -> na.bandwidthAlongPath(p));
        assertEquals(na.bandwidthAlongPath(p), 1, "incorrect bandwidth");
        p.add(7);
        assertThrows(IllegalArgumentException.class, () -> na.bandwidthAlongPath(p));
        
        // THIS TEST IS QUESTIONABLE IDK IF THIS IS ACTUALLY ALLOWED
        ArrayList<Integer> p1 = new ArrayList<>();
        p1.add(1);
        p1.add(2);
        p1.add(1);
        p1.add(2);
        p1.add(1);
        p1.add(2);
        p1.add(1);
        p1.add(2);
        assertDoesNotThrow(() -> na.bandwidthAlongPath(p1));
        assertEquals(na.bandwidthAlongPath(p1), 4, "incorrect bandwidth");
        
        assertTrue(!na.copperOnlyConnected(), "Copper only connected fail");
        assertTrue(!na.connectedTwoVertFail(), "graph should not be connected when removing 2 vertices");
        assertTrue(na.lowestAvgLatST() == null, "tree not connected, should return null");
    }
    
    @Test
    @DisplayName("Justin's tests: graph 9 (tiny graph)")
    void tiny_graph() {
        NetAnalysis na = init_basic(9);
        
        ArrayList<Integer> res = na.lowestLatencyPath(0,1);
        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(0);
        exp.add(1);
        
        ArrayList<Integer> res1 = na.lowestLatencyPath(1,0);
        ArrayList<Integer> exp1 = new ArrayList<>();
        exp1.add(1);
        exp1.add(0);

        for(int i=0; i<2; i++){
            assertEquals(res.get(i), exp.get(i), "lowest latency path WRONG");
            assertEquals(res1.get(i), exp1.get(i), "lowest latency path WRONG");
        }
        assertTrue(!na.connectedTwoVertFail(), "graph should not be connected when removing 2 vertices");
        assertTrue(na.copperOnlyConnected(), "Copper only connected fail");

        assertDoesNotThrow(() -> na.bandwidthAlongPath(exp));
        assertEquals(na.bandwidthAlongPath(exp), 1, "bandwidth wrong");
        
        assertTrue(null!=na.lowestAvgLatST(), "spanning tree wrong");

    }
}
