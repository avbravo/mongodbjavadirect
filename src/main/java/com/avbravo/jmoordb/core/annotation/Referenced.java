package com.avbravo.jmoordb.core.annotation ;

import com.avbravo.jmoordb.core.annotation.enumerations.TypePK;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
*
* @author 
*/


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Referenced {

String from();
 String localField();
 String foreignField();
 String as();
 boolean lazy() default false;
// boolean typeFieldkeyString() default true;
 TypePK typePK() default TypePK.STRING ;
}

