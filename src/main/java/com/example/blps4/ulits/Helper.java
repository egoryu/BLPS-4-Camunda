package com.example.blps4.ulits;

import com.example.blps4.entity.HumanEntity;
import com.example.blps4.enums.Gender;


public class Helper {
    public static String getRelation(HumanEntity person1, HumanEntity person2, int mod) throws Exception {
        String result;

        if (mod == 0) {
            if (person1.getSex() == person2.getSex()) {
                throw new Exception("Similar sex of person!");
            }
            if (person1.getSex() == Gender.MALE.ordinal()) {
                result = "муж и жена";
            } else {
                result = "жена и муж";
            }
        } else {
            if (person1.getSex() == Gender.MALE.ordinal()) {
                result = "отец и ";
            } else {
                result = "мать и ";
            }

            if (person2.getSex() == Gender.MALE.ordinal()) {
                result = result + "сын";
            } else {
                result = result + "дочь";
            }
        }

        return result;
    }
}
