/*
 * XML Type:  timingTaskSummaryDataConfType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * An XML timingTaskSummaryDataConfType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public class TimingTaskSummaryDataConfTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType
{
    private static final long serialVersionUID = 1L;
    
    public TimingTaskSummaryDataConfTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRACE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "trace");
    private static final javax.xml.namespace.QName INSTID$2 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "instId");
    private static final javax.xml.namespace.QName GATHERDATATIME$4 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "gatherDataTime");
    private static final javax.xml.namespace.QName GATHERDATANAME$6 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "gatherDataName");
    private static final javax.xml.namespace.QName DATAHANDLER$8 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "dataHandler");
    private static final javax.xml.namespace.QName ISSUEINSTANT$10 = 
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
     * Gets the "gatherDataTime" element
     */
    public java.lang.String getGatherDataTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GATHERDATATIME$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "gatherDataTime" element
     */
    public org.apache.xmlbeans.XmlString xgetGatherDataTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GATHERDATATIME$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "gatherDataTime" element
     */
    public void setGatherDataTime(java.lang.String gatherDataTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GATHERDATATIME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GATHERDATATIME$4);
            }
            target.setStringValue(gatherDataTime);
        }
    }
    
    /**
     * Sets (as xml) the "gatherDataTime" element
     */
    public void xsetGatherDataTime(org.apache.xmlbeans.XmlString gatherDataTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GATHERDATATIME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GATHERDATATIME$4);
            }
            target.set(gatherDataTime);
        }
    }
    
    /**
     * Gets the "gatherDataName" element
     */
    public java.lang.String getGatherDataName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GATHERDATANAME$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "gatherDataName" element
     */
    public org.apache.xmlbeans.XmlString xgetGatherDataName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GATHERDATANAME$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "gatherDataName" element
     */
    public void setGatherDataName(java.lang.String gatherDataName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GATHERDATANAME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GATHERDATANAME$6);
            }
            target.setStringValue(gatherDataName);
        }
    }
    
    /**
     * Sets (as xml) the "gatherDataName" element
     */
    public void xsetGatherDataName(org.apache.xmlbeans.XmlString gatherDataName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GATHERDATANAME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GATHERDATANAME$6);
            }
            target.set(gatherDataName);
        }
    }
    
    /**
     * Gets the "dataHandler" element
     */
    public cn.com.chinaebi.dz.webservice.DataHandler.Enum getDataHandler()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAHANDLER$8, 0);
            if (target == null)
            {
                return null;
            }
            return (cn.com.chinaebi.dz.webservice.DataHandler.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "dataHandler" element
     */
    public cn.com.chinaebi.dz.webservice.DataHandler xgetDataHandler()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DataHandler target = null;
            target = (cn.com.chinaebi.dz.webservice.DataHandler)get_store().find_element_user(DATAHANDLER$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "dataHandler" element
     */
    public void setDataHandler(cn.com.chinaebi.dz.webservice.DataHandler.Enum dataHandler)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAHANDLER$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAHANDLER$8);
            }
            target.setEnumValue(dataHandler);
        }
    }
    
    /**
     * Sets (as xml) the "dataHandler" element
     */
    public void xsetDataHandler(cn.com.chinaebi.dz.webservice.DataHandler dataHandler)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DataHandler target = null;
            target = (cn.com.chinaebi.dz.webservice.DataHandler)get_store().find_element_user(DATAHANDLER$8, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.DataHandler)get_store().add_element_user(DATAHANDLER$8);
            }
            target.set(dataHandler);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$10);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$10);
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
            return get_store().find_attribute_user(ISSUEINSTANT$10) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISSUEINSTANT$10);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_attribute_user(ISSUEINSTANT$10);
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
            get_store().remove_attribute(ISSUEINSTANT$10);
        }
    }
}
