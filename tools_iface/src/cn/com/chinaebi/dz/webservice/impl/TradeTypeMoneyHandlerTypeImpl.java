/*
 * XML Type:  tradeTypeMoneyHandlerType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * An XML tradeTypeMoneyHandlerType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public class TradeTypeMoneyHandlerTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TradeTypeMoneyHandlerType
{
    private static final long serialVersionUID = 1L;
    
    public TradeTypeMoneyHandlerTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRACE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "trace");
    private static final javax.xml.namespace.QName PROCESS$2 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "process");
    private static final javax.xml.namespace.QName TRADEMSGTYPE$4 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "tradeMsgType");
    private static final javax.xml.namespace.QName NAME$6 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "name_");
    private static final javax.xml.namespace.QName TRADEMONEYSTATUS$8 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "tradeMoneyStatus");
    private static final javax.xml.namespace.QName DATAHANDLER$10 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "dataHandler");
    private static final javax.xml.namespace.QName ISSUEINSTANT$12 = 
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
     * Gets the "process" element
     */
    public java.lang.String getProcess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESS$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "process" element
     */
    public org.apache.xmlbeans.XmlString xgetProcess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESS$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "process" element
     */
    public void setProcess(java.lang.String process)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROCESS$2);
            }
            target.setStringValue(process);
        }
    }
    
    /**
     * Sets (as xml) the "process" element
     */
    public void xsetProcess(org.apache.xmlbeans.XmlString process)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROCESS$2);
            }
            target.set(process);
        }
    }
    
    /**
     * Gets the "tradeMsgType" element
     */
    public int getTradeMsgType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRADEMSGTYPE$4, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "tradeMsgType" element
     */
    public org.apache.xmlbeans.XmlInt xgetTradeMsgType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TRADEMSGTYPE$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tradeMsgType" element
     */
    public void setTradeMsgType(int tradeMsgType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRADEMSGTYPE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRADEMSGTYPE$4);
            }
            target.setIntValue(tradeMsgType);
        }
    }
    
    /**
     * Sets (as xml) the "tradeMsgType" element
     */
    public void xsetTradeMsgType(org.apache.xmlbeans.XmlInt tradeMsgType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TRADEMSGTYPE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TRADEMSGTYPE$4);
            }
            target.set(tradeMsgType);
        }
    }
    
    /**
     * Gets the "name_" element
     */
    public java.lang.String getName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAME$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "name_" element
     */
    public org.apache.xmlbeans.XmlString xgetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NAME$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "name_" element
     */
    public void setName(java.lang.String name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NAME$6);
            }
            target.setStringValue(name);
        }
    }
    
    /**
     * Sets (as xml) the "name_" element
     */
    public void xsetName(org.apache.xmlbeans.XmlString name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NAME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NAME$6);
            }
            target.set(name);
        }
    }
    
    /**
     * Gets the "tradeMoneyStatus" element
     */
    public boolean getTradeMoneyStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRADEMONEYSTATUS$8, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "tradeMoneyStatus" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetTradeMoneyStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(TRADEMONEYSTATUS$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "tradeMoneyStatus" element
     */
    public void setTradeMoneyStatus(boolean tradeMoneyStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRADEMONEYSTATUS$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRADEMONEYSTATUS$8);
            }
            target.setBooleanValue(tradeMoneyStatus);
        }
    }
    
    /**
     * Sets (as xml) the "tradeMoneyStatus" element
     */
    public void xsetTradeMoneyStatus(org.apache.xmlbeans.XmlBoolean tradeMoneyStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(TRADEMONEYSTATUS$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(TRADEMONEYSTATUS$8);
            }
            target.set(tradeMoneyStatus);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAHANDLER$10, 0);
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
            target = (cn.com.chinaebi.dz.webservice.DataHandler)get_store().find_element_user(DATAHANDLER$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAHANDLER$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAHANDLER$10);
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
            target = (cn.com.chinaebi.dz.webservice.DataHandler)get_store().find_element_user(DATAHANDLER$10, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.DataHandler)get_store().add_element_user(DATAHANDLER$10);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$12);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$12);
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
            return get_store().find_attribute_user(ISSUEINSTANT$12) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ISSUEINSTANT$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ISSUEINSTANT$12);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_attribute_user(ISSUEINSTANT$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_attribute_user(ISSUEINSTANT$12);
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
            get_store().remove_attribute(ISSUEINSTANT$12);
        }
    }
}
