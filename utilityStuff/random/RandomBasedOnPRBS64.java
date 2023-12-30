// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package utilityStuff.random;


import  utilityStuff.Version;


/**
 * The class {@link RandomBasedOnPRBS64} implements the interface {@link RandomGenerator}.
 * The random is generated by a PRBS sequence in random mode.
 * 
 * @author  Michael Schaefers  ([UTF-8]:"Michael Schäfers");
 *          Px@Hamburg-UAS.eu
 * @version {@value #encodedVersion}
 */
public class RandomBasedOnPRBS64 implements RandomGenerator {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_002___2023_04_24__01L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static private Version version = new Version( encodedVersion );
    /**
     * The method {@link #getDecodedVersion()} delivers the code version as reground/readable String.
     * @return version as decoded/readable String.
     */
    static public String getDecodedVersion(){ return version.getDecodedVersion(); }
    // Obiges (ab VERSION) dient nur der Versionierung.
    
    
    
    
    
    // see Xilinx xapp052 & "numerical recipes"
    final String thePolynominal = "1b0000001";                                  // 65 bit prime polynominal with MSB on left side and LSB on right side
    //64, 4, 3, 1, 0                                                            // coefficients of 65 bit (prime!) polynominal
    //\=> 64, 63, 61, 60, 0                                                     // coefficients of 65 bit (prime!) polynominal, da "nur" gespiegelt
    //\=> 1__0000_0000__0 .. 0__0001_1011 [2]                                   // 65 bit polynominal with LSB on the left and MSB on the right side
    //\=>    1000_0000__0 .. 0__0000_1101 [2]                                   //"64 bit version",   the MSB known as 1 is missing
    //\=> 1__0000_0000__0000_001b [16]                                          // 65 bit polynominal with LSB on the left and MSB on the right side
    //\=>    8000_0000__0000_000d [16]                                          //"64 bit version",   the MSB known as 1 is missing and LSB on left side
    final long poly65withoutMSB = 0x8000_0000__0000_000dL;                      //"64 bit version",   the MSB known as 1 is missing and LSB on left side
    
    private long signature;
    
    
    
    
    
    public RandomBasedOnPRBS64( long seed ){
        if( 0 == seed )  throw new IllegalArgumentException( "non zero value required for seed" );
        signature = seed;
    }//constructor()
    
    
    
    
    
    @Override
    public int nextInt( final int excludingMaximum ) {
        if( 0 >= excludingMaximum ){
            throw new IllegalArgumentException( String.format(
                "natural resp. actual positive value required for excludingMaximum, but was : %d",
                excludingMaximum
            ));
        }//if
        //
        final boolean signatureMSBset = (0b1 == (signature & 0b1));
        signature >>>= 1;
        if( signatureMSBset )  signature ^= poly65withoutMSB;
        final int rawRandomValue = (int)( signature % excludingMaximum );
        final int randomValue = (0 > rawRandomValue) ? -rawRandomValue : rawRandomValue;
        return randomValue;
    }//method()
    
    public long nextLong(){
        final boolean signatureMSBset = (0b1 == (signature & 0b1));
        signature >>>= 1;
        if( signatureMSBset )  signature ^= poly65withoutMSB;
        return signature;
    }//method()
    
    
    @Override
    public String identifyImplementation(){
        return String.format(
            "%s  -  0x%s",
            getClass().getName(),
            thePolynominal
        );
    }//method()
    
}//class