/*
 * XML Type:  dzTradeContextType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.DzTradeContextType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * An XML dzTradeContextType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public class DzTradeContextTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.DzTradeContextType
{
    private static final long serialVersionUID = 1L;
    
    public DzTradeContextTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRACE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "trace");
    private static final javax.xml.namespace.QName OPCODE$2 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "opCode");
    private static final javax.xml.namespace.QName ISSUEINSTANT$4 = 
        new javax.xml.namespace.QName("", "issueInstant");
    
    
    /**
     * Gets the "trace" element
     */
    public java.lang.String getTrace()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRACE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "trace" element
     */
    public org.apache.xmlbeans.XmlString xgetTrace()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRACE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "trace" element
     */
    public void setTrace(java.lang.String trace)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRACE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRACE$0);
            }
            target.setStringValue(trace);
        }
    }
    
    /**
     * Sets (as xml) the "trace" element
     */
    public void xsetTrace(org.apache.xmlbeans.XmlString trace)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRACE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRACE$0);
            }
            target.set(trace);
        }
    }
    
    /**
     * Gets the "opCode" element
     */
    public java.lang.String getOpCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPCODE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "opCode" element
     */
    public org.apache.xmlbeans.XmlString xgetOpCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OPCODE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "opCode" element
     */
    public void setOpCode(java.lang.String opCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPCODE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OPCODE$2);
            }
            target.setStringValue(opCode);
        }
    }
    
    /**
     * Sets (as xml) the "opCode" element
     */
    public void xsetOpCode(org.apache.xmlbeans.XmlString opCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OPCODE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OPCODE$2);
            }
            target.set(opCode);
        }
    }
    
    /**
     * Gets the "issueInstant" attribute
     */
    public java.util.Calendar getIssueInstant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$4);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "issueInstant" attribute
     */
    public org.apache.xmlbeans.XmlDateTime xgetIssueInstant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$4);
            return target;
        }
    }
    
    /**
     * True if has "issueInstant" attribute
     */
    public boolean isSetIssueInstant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ISSUEINSTANT$4) != null;
        }
    }
    
    /**
     * Sets the "issueInstant" attribute
     */
    public void setIssueInstant(java.util.Calendar issueInstant)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISSUEINSTANT$4);
            }
            target.setCalendarValue(issueInstant);
        }
    }
    
    /**
     * Sets (as xml) the "issueInstant" attribute
     */
    public void xsetIssueInstant(org.apache.xmlbeans.XmlDateTime issueInstant)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_attribute_user(ISSUEINSTANT$4);
            }
            target.set(issueInstant);
        }
    }
    
    /**
     * Unsets the "issueInstant" attribute
     */
    public void unsetIssueInstant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ISSUEINSTANT$4);
        }
    }
}
