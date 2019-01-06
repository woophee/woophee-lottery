package utils;

import layout.RuleItem;

import java.util.*;

public class CacheUtil {

    public static String fileName;

    public static List<String> lotteryFile;

    public static HashSet<String> processlist = new HashSet<String>();

    public static Stack<RuleItem> ruleStack = new Stack<RuleItem>();

    public static int allNumber;

    public static int allPeople;

    public static Map<Integer,Map<String,Integer>> fileConfig = new HashMap<Integer, Map<String, Integer>>();

    public static Map<Integer, Map<String, Integer>> getFileConfig() {
        return fileConfig;
    }



}
