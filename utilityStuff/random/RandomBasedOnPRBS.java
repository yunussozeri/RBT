// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package utilityStuff.random;


import utilityStuff.Version;


/**
 * The class {@link RandomBasedOnPRBS} implements the interface {@link RandomGenerator}.
 * The random is generated by a PRBS sequence in random mode.
 * 
 * @author  Michael Schaefers  ([UTF-8]:"Michael Schäfers");
 *          Px@Hamburg-UAS.eu
 * @version {@value #encodedVersion}
 */
public class RandomBasedOnPRBS implements RandomGenerator {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_002___2023_06_13__01L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static private Version version = new Version( encodedVersion );
    /**
     * The method {@link #getDecodedVersion()} delivers the code version as reground/readable String.
     * @return version as decoded/readable String.
     */
    static public String getDecodedVersion(){ return version.getDecodedVersion(); }
    // Obiges (ab VERSION) dient nur der Versionierung.
    
    
    
    
    
    final long poly33b = 0b1000_0000__0001_0000__0010_0001__0011_0110__1L;      // 33 bit polynominal with LSB on the left and MSB on the right side
    final int polyWithOutMSB = (int)( poly33b >>> 1 );                          // 32 bit version,  the MSB known as 1 is missing
    
    private int signature;
    
    
    
    /**
     * Random Generator based on PRBS.
     * 
     * @param seed  is seed resp. start value for PRBS sequence.
     *              The seed MUST NOT be zero.
     */
    public RandomBasedOnPRBS( long seed ){
        if( 0 == seed )  throw new IllegalArgumentException( "non zero value required for seed" );
        //
        signature = (int)( seed & 0xFFFF_FFFF );                                // take lower half
        if( 0 == signature ) {
            seed >>>= 32;                                                       // take...
            signature = (int)( seed );                                          //...upper half
            assert 0 == ( seed & 0xFFFF_FFFF__0000_0000L ) : "Uuupps : internal programming errror";
        }//if
        assert 0 != signature : "Uuupps : internal programming errror";
       //\=> init/start value for signature is defined
    }//constructor()
    
    
    
    @Override
    public int nextInt( final int excludingMaximum ){
        if( 0 >= excludingMaximum ){
            throw new IllegalArgumentException( String.format(
                "natural resp. actual positive value required for excludingMaximum, but was : %d",
                excludingMaximum
            ));
        }//if
        //
        final boolean signatureMSBset = (0b1 == (signature & 0b1));
        signature >>>= 1;
        if( signatureMSBset )  signature ^= polyWithOutMSB;
        final int rawRandomValue = signature % excludingMaximum;
        final int randomValue = (0 > rawRandomValue) ? -rawRandomValue : rawRandomValue;
        return randomValue;
    }//method()
    
    @Override
    public String identifyImplementation(){
        return "PRBS";
    }//method()
    
}//class