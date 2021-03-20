package br.ufrn.imd.pds.util;

public class IdCounter {

    private static long counter = 0;
    
    public static synchronized long nextId() {
        return ++counter;     
    }
    
    public static void setCounter( long init ) {
    	counter = init;
    }
    
}
