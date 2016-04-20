/*
 * XML Type:  manualHandlerErrorDzType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * An XML manualHandlerErrorDzType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public class ManualHandlerErrorDzTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType
{
    private static final long serialVersionUID = 1L;
    
    public ManualHandlerErrorDzTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRACE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "trace");
    private static final javax.xml.namespace.QName INSTID$2 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "instId");
    private static final javax.xml.namespace.QName DZHANDLERCLASS$4 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "dzHandlerClass");
    private static final javax.xml.namespace.QName SUMMARYDATE$6 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "summaryDate");
    private static final javax.xml.namespace.QName ISSUEINSTANT$8 = 
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
     * Gets the "dzHandlerClass" element
     */
    public java.lang.String getDzHandlerClass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DZHANDLERCLASS$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "dzHandlerClass" element
     */
    public org.apache.xmlbeans.XmlString xgetDzHandlerClass()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DZHANDLERCLASS$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "dzHandlerClass" element
     */
    public void setDzHandlerClass(java.lang.String dzHandlerClass)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DZHANDLERCLASS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DZHANDLERCLASS$4);
            }
            target.setStringValue(dzHandlerClass);
        }
    }
    
    /**
     * Sets (as xml) the "dzHandlerClass" element
     */
    public void xsetDzHandlerClass(org.apache.xmlbeans.XmlString dzHandlerClass)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DZHANDLERCLASS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DZHANDLERCLASS$4);
            }
            target.set(dzHandlerClass);
        }
    }
    
    /**
     * Gets the "summaryDate" element
     */
    public java.lang.String getSummaryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUMMARYDATE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "summaryDate" element
     */
    public org.apache.xmlbeans.XmlString xgetSummaryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARYDATE$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "summaryDate" element
     */
    public void setSummaryDate(java.lang.String summaryDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUMMARYDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUMMARYDATE$6);
            }
            target.setStringValue(summaryDate);
        }
    }
    
    /**
     * Sets (as xml) the "summaryDate" element
     */
    public void xsetSummaryDate(org.apache.xmlbeans.XmlString summaryDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARYDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SUMMARYDATE$6);
            }
            target.set(summaryDate);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$8);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$8);
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
            return get_store().find_attribute_user(ISSUEINSTANT$8) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISSUEINSTANT$8);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_attribute_user(ISSUEINSTANT$8);
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
            get_store().remove_attribute(ISSUEINSTANT$8);
        }
    }
}
