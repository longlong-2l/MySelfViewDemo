package com.study.longl.myselfviewdemo.Views.ByteBeatView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by longl on 2018/12/19.
 * 不同字符间的处理
 */

public class CharacterDiffUtil {
    public static List<CharacterDiff> diff(CharSequence oldText, CharSequence newText) {
        List<CharacterDiff> diffList = new ArrayList<>();
        Set<Integer> skip = new HashSet<>();
        for (int i = 0; i < oldText.length(); i++) {
            char c = oldText.charAt(i);
            for (int j = 0; j < newText.length(); j++) {
                if (!skip.contains(j) && c == newText.charAt(j)) {
                    skip.add(j);
                    CharacterDiff diff = new CharacterDiff();
                    diff.c = c;
                    diff.fromIndex = i;
                    diff.moveIndex = j;
                    diffList.add(diff);
                    break;
                }
            }
        }
        return diffList;
    }

    public static boolean stayHere(int index, List<CharacterDiff> differentList) {
        for (CharacterDiff different : differentList) {
            if (different.moveIndex == index) {
                return true;
            }
        }
        return false;
    }


    public static int needMove(int index, List<CharacterDiff> diffList) {
        for (CharacterDiff diff : diffList) {
            if (diff.fromIndex == index) {
                return diff.moveIndex;
            }
        }
        return -1;
    }

    /**
     * 计算x坐标的位置
     */
    public static float getOffset(int from, int move, float progress, float startX, float oldStartX, List<Float> gaps, List<Float> oldGaps) {
        float dist = startX;
        for (int i = 0; i < move; i++) {
            dist += gaps.get(i);
        }
        float cur = oldStartX;
        for (int i = 0; i < from; i++) {
            cur += oldGaps.get(i);
        }
        return cur + (dist - cur) * progress;
    }
}
