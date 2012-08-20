
/* First created by JCasGen Thu May 17 14:08:52 CEST 2012 */
package com.github.kutschkem.Qgen.annotations;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sat Aug 18 13:41:23 CEST 2012
 * @generated */
public class Questionword_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Questionword_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Questionword_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Questionword(addr, Questionword_Type.this);
  			   Questionword_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Questionword(addr, Questionword_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Questionword.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.github.kutschkem.Qgen.annotations.Questionword");
 
  /** @generated */
  final Feature casFeat_questionword;
  /** @generated */
  final int     casFeatCode_questionword;
  /** @generated */ 
  public String getQuestionword(int addr) {
        if (featOkTst && casFeat_questionword == null)
      jcas.throwFeatMissing("questionword", "com.github.kutschkem.Qgen.annotations.Questionword");
    return ll_cas.ll_getStringValue(addr, casFeatCode_questionword);
  }
  /** @generated */    
  public void setQuestionword(int addr, String v) {
        if (featOkTst && casFeat_questionword == null)
      jcas.throwFeatMissing("questionword", "com.github.kutschkem.Qgen.annotations.Questionword");
    ll_cas.ll_setStringValue(addr, casFeatCode_questionword, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Questionword_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_questionword = jcas.getRequiredFeatureDE(casType, "questionword", "uima.cas.String", featOkTst);
    casFeatCode_questionword  = (null == casFeat_questionword) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_questionword).getCode();

  }
}



    