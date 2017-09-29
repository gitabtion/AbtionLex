import models.NFAModel;
import nfa2dfa.NFA2DFAUtil;


import java.util.HashMap;
import java.util.HashSet;

/**
 * @author abtion.
 * @since 17/9/28 18:33.
 */
public class test {
    public static void main(String[] args) {
        NFAModel nfa = new NFAModel();
        NFAModel dfa ;

        for (int i = 0;i<4;i++){
            nfa.getK().add(String.valueOf(i));
        }

        nfa.getSigma().add("a");
        nfa.getSigma().add("b");
        nfa.getS().add("0");
        nfa.getZ().add("3");

        HashSet<String> tempSet = new HashSet<>();
        HashMap<String,HashSet<String>> tempMap = new HashMap<>();
        tempSet.add("1");
        tempMap.put("ep",tempSet);
        nfa.getF().put("0",tempMap);

        HashSet<String> tempSet1 = new HashSet<>();
        HashMap<String,HashSet<String>> tempMap1 = new HashMap<>();
        tempSet1.add("2");
        tempMap1.put("a",tempSet1);
        tempMap1.put("b",tempSet1);
        nfa.getF().put("1",tempMap1);


        HashSet<String> tempSet3 = new HashSet<>();
        HashMap<String,HashSet<String>> tempMap3 = new HashMap<>();
        tempSet3.add("1");
        tempMap3.put("ep",tempSet3);
        nfa.getF().put("2",tempMap3);

        HashSet<String> tempSet4 = new HashSet<>();
        HashMap<String,HashSet<String>> tempMap4 = new HashMap<>();
        tempSet4.add("3");
        tempMap4.put("a",tempSet4);
        nfa.getF().put("2",tempMap4);

        System.out.println(nfa.toString());

        NFA2DFAUtil util = new NFA2DFAUtil();
        dfa = util.getDFAOf(nfa);
        System.out.println(dfa);
    }
}
