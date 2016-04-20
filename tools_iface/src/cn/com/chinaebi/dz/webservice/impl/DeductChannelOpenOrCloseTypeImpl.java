/*
 * XML Type:  deductChannelOpenOrCloseType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * An XML deductChannelOpenOrCloseType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public class DeductChannelOpenOrCloseTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType
{
    private static final long serialVersionUID = 1L;
    
    public DeductChannelOpenOrCloseTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRACE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "trace");
    private static final javax.xml.namespace.QName INSTID$2 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "instId");
    private static final javax.xml.namespace.QName OPENORCLOSE$4 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "openOrclose");
    private static final javax.xml.namespace.QName ISSUEINSTANT$6 = 
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
     * Gets the "instId" element
     */
    public int getInstId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INSTID$2, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "instId" element
     */
    public org.apache.xmlbeans.XmlInt xgetInstId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(INSTID$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "instId" element
     */
    public void setInstId(int instId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INSTID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INSTID$2);
            }
            target.setIntValue(instId);
        }
    }
    
    /**
     * Sets (as xml) the "instId" element
     */
    public void xsetInstId(org.apache.xmlbeans.XmlInt instId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(INSTID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(INSTID$2);
            }
            target.set(instId);
        }
    }
    
    /**
     * Gets the "openOrclose" element
     */
    public cn.com.chinaebi.dz.webservice.OpenOrclose.Enum getOpenOrclose()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPENORCLOSE$4, 0);
            if (target == null)
            {
                return null;
            }
            return (cn.com.chinaebi.dz.webservice.OpenOrclose.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "openOrclose" element
     */
    public cn.com.chinaebi.dz.webservice.OpenOrclose xgetOpenOrclose()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.OpenOrclose target = null;
            target = (cn.com.chinaebi.dz.webservice.OpenOrclose)get_store().find_element_user(OPENORCLOSE$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "openOrclose" element
     */
    public void setOpenOrclose(cn.com.chinaebi.dz.webservice.OpenOrclose.Enum openOrclose)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPENORCLOSE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OPENORCLOSE$4);
            }
            target.setEnumValue(openOrclose);
        }
    }
    
    /**
     * Sets (as xml) the "openOrclose" element
     */
    public void xsetOpenOrclose(cn.com.chinaebi.dz.webservice.OpenOrclose openOrclose)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.OpenOrclose target = null;
            target = (cn.com.chinaebi.dz.webservice.OpenOrclose)get_store().find_element_user(OPENORCLOSE$4, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.OpenOrclose)get_store().add_element_user(OPENORCLOSE$4);
            }
            target.set(openOrclose);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$6);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$6);
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
            return get_store().find_attribute_user(ISSUEINSTANT$6) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISSUEINSTANT$6);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_attribute_user(ISSUEINSTANT$6);
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
            get_store().remove_attribute(ISSUEINSTANT$6);
        }
    }
}
