//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.02.22 um 11:14:03 PM CET 
//


package org.collada._2005._11.colladaschema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für gl_stencil_op_type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="gl_stencil_op_type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="KEEP"/>
 *     &lt;enumeration value="ZERO"/>
 *     &lt;enumeration value="REPLACE"/>
 *     &lt;enumeration value="INCR"/>
 *     &lt;enumeration value="DECR"/>
 *     &lt;enumeration value="INVERT"/>
 *     &lt;enumeration value="INCR_WRAP"/>
 *     &lt;enumeration value="DECR_WRAP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "gl_stencil_op_type")
@XmlEnum
public enum GlStencilOpType {

    KEEP,
    ZERO,
    REPLACE,
    INCR,
    DECR,
    INVERT,
    INCR_WRAP,
    DECR_WRAP;

    public String value() {
        return name();
    }

    public static GlStencilOpType fromValue(String v) {
        return valueOf(v);
    }

}
