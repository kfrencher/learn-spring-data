package com.kfrencher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class QueryUtils {

    public static String insertPredicate(String query, String predicate) {
        
        if(query.contains("where")){
            Pattern wherePattern = Pattern.compile("(.*from\\s+.+\\s+where)\\s+(?:(.+)\\s+(?=group by)(.*)|(.+))");
            Matcher whereMatcher = wherePattern.matcher(query);
            whereMatcher.find();
            String beforePredicate = whereMatcher.group(1);
            if(whereMatcher.group(2) == null){
                String originalPredicate = whereMatcher.group(4);
                return String.format("%s %s and (%s)", beforePredicate, predicate, originalPredicate);
            }else{
                String originalPredicate = whereMatcher.group(2);
                String afterPredicate = whereMatcher.group(3);
                return String.format("%s %s and (%s) %s", beforePredicate, predicate, originalPredicate, afterPredicate);
            }
        }else{
            int idx = query.indexOf(" group by");
            if(idx == -1){
                return String.format("%s where %s", query, predicate);
            }else{
                return String.format("%s where %s%s", query.substring(0,idx),predicate,query.substring(idx));
            }
        }
    }
}
