package com.kosmos.brushbreakfast;

import org.junit.Test;

public class ProcessCardNo {
    @Test
    public void testProcessCardNo() {
        System.out.println(processCardNo("123"));
    }

    private String processCardNo(String originCardNo) {
        if (originCardNo.length() <= 2) {
            return originCardNo;
        }

        boolean isDouble = false;
        int count = originCardNo.length() / 2;
        // 判断是单位还是双位
        if (originCardNo.length() % 2 == 0) {
            isDouble = true;
        } else {
            isDouble = false;
        }
        StringBuilder processedCardNo = new StringBuilder();
        for (int i = count; i > 0; i--) {
            if (isDouble) {
                int tempIndex = 2 * i - 2;
                processedCardNo.append(originCardNo.substring(tempIndex, tempIndex + 2));
            } else {
                int tempIndex = 2 * i - 1;
                processedCardNo.append(originCardNo.substring(tempIndex, tempIndex + 2));
                if (i == 1) {
                    processedCardNo.append(originCardNo.charAt(0));
                }
            }
        }
        return processedCardNo.toString();
    }


}
