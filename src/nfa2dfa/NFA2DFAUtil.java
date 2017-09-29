package nfa2dfa;

import models.NFAModel;

import java.util.HashMap;
import java.util.HashSet;

import static sun.jvm.hotspot.debugger.win32.coff.DebugVC50X86RegisterEnums.TAG;

/**
 * @author abtion.
 * @since 17/9/28 18:38.
 */
public class NFA2DFAUtil {
    private int statusCount;    //dfa状态集的数量
    private NFAModel dfa;   // dfa
    private HashMap<String, String> nfa2dfaMapping;  //NFA的状态集到DFA状态集的映射
    private HashSet<HashSet<String>> statusSet;   //储存每个DFA状态对应的NFA状态集

    public NFA2DFAUtil() {
        statusCount = 0;
        dfa = new NFAModel();
        nfa2dfaMapping = new HashMap<>();
        statusSet = new HashSet<>();
    }

    /**
     * 获取NFA对应的DFA
     *
     * @param nfaModel NFA
     * @return DFA
     */
    public NFAModel getDFAOf(NFAModel nfaModel) {
        HashSet<String> statusTemp = nfaModel.getS();
        statusTemp = epsilonCloseSetOf(nfaModel.getF(), statusTemp);
        //将s的闭包放入映射集中
        nfa2dfaMapping.put(statusTemp.toString(), String.valueOf(++statusCount));
        statusSet.add(statusTemp);

        //s属性赋值
        dfa.getS().add(nfa2dfaMapping.get(String.valueOf(statusTemp)));

        //sigma属性赋值
        dfa.setSigma(nfaModel.getSigma());

        for (HashSet<String> status : statusSet) {
            // TODO: 17/9/29 没有完全遍历statusSet
            String statusKey = status.toString();
            for (String sigmaElement : nfaModel.getSigma()) {
                HashSet<String> newStatus;  //与DFA某一状态对应的NFA状态集
                newStatus = movedSetOf(sigmaElement, nfaModel.getF(), status);
                newStatus = epsilonCloseSetOf(nfaModel.getF(), newStatus);
                if (newStatus.size() != 0 && statusSet.add(newStatus)) {
                    nfa2dfaMapping.put(newStatus.toString(), String.valueOf(++statusCount));
                }
                System.out.println(newStatus.toString());
                //储存映射关系，当该状态集不存在该sigmaElement弧时该如何处理？
                if (newStatus.size() != 0) {
                    HashSet<String> rightStatus = new HashSet<>();  //DFA的状态的Set表示
                    rightStatus.add(String.valueOf(statusCount));
                    HashMap<String, HashSet<String>> tempMapping = new HashMap<>();
                    tempMapping.put(sigmaElement, rightStatus);
                    dfa.getF().put(String.valueOf(statusCount), tempMapping);
                }
            }

            //z属性赋值
            for (String zElement : nfaModel.getZ()) {
                if (status.contains(zElement)) {
                    dfa.getZ().add(nfa2dfaMapping.get(status.toString()));
                }
            }

        }
        //k属性赋值
        for (String str : nfa2dfaMapping.values()) {
            dfa.getK().add(str);
        }

        return dfa;
    }

    /**
     * 获取状态集的epsilon闭包
     *
     * @param f        映射集
     * @param movedSet 已移动后的临时状态集
     * @return 已转化为epsilon闭包后的状态集
     */
    private HashSet<String> epsilonCloseSetOf(HashMap<String, HashMap<String, HashSet<String>>> f, HashSet<String> movedSet) {
        int count = 0;
        for (String element : movedSet) {
            if (f.get(element) != null) {
                HashSet<String> rightSet = f.get(element).get("ep");
                if (rightSet != null) {
                    for (String status : rightSet) {
                        movedSet.add(status);
                    }
                }

            }
            if (++count == movedSet.size()) {
                return movedSet;
            }
        }
        return epsilonCloseSetOf(f, movedSet);
    }

    /**
     * 获取状态集的移动后临时状态集
     *
     * @param f            映射集
     * @param sigmaElement 弧上元素，移动的因变量之一
     * @param oldSet       初始状态集
     * @return 移动后的临时状态集
     */
    private HashSet<String> movedSetOf(String sigmaElement, HashMap<String, HashMap<String, HashSet<String>>> f, HashSet<String> oldSet) {
        HashSet<String> newSet = (HashSet<String>) oldSet.clone();
        for (String element : oldSet) {
            if (f.get(element) != null) {
                HashSet<String> rightSet = f.get(element).get(sigmaElement);
                if (rightSet != null) {
                    for (String status : rightSet) {
                        newSet.add(status);
                    }
                }
            }
            if (oldSet.size() == newSet.size()) {
                return newSet;
            }
        }
        return movedSetOf(sigmaElement, f, newSet);
    }

}
