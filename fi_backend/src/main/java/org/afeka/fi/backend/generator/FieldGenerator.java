package org.afeka.fi.backend.generator;


import net.bytebuddy.utility.RandomString;

import java.util.Random;
import java.util.UUID;

public class FieldGenerator {

    public static String id(){
        return new RandomString(3).nextString();
    }
}
