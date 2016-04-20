/*
 * XML Type:  manualHandlerBeijingAgainDzType
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * An XML manualHandlerBeijingAgainDzType(@http://www.dz.chinaebi.com.cn/webservice).
 *
 * This is a complex type.
 */
public class ManualHandlerBeijingAgainDzTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ManualHandlerBeijingAgainDzType
{
    private static final long serialVersionUID = 1L;
    
    public ManualHandlerBeijingAgainDzTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TRACE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "trace");
    private static final javax.xml.namespace.QName INSTID$2 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "instId");
    private static final javax.xml.namespace.QName SUMMARYDATE$4 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "summaryDate");
    private static final javax.xml.namespace.QName ORIGINALDZCOLUMN$6 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "originalDzColumn");
    private static final javax.xml.namespace.QName CHANNELDZCOLUMN$8 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "channelDzColumn");
    private static final javax.xml.namespace.QName INNERTTRADEHANDLER$10 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "innertTradeHandler");
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
     * Gets the "summaryDate" element
     */
    public java.lang.String getSummaryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUMMARYDATE$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARYDATE$4, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUMMARYDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUMMARYDATE$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARYDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SUMMARYDATE$4);
            }
            target.set(summaryDate);
        }
    }
    
    /**
     * Gets array of all "originalDzColumn" elements
     */
    public java.lang.String[] getOriginalDzColumnArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ORIGINALDZCOLUMN$6, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "originalDzColumn" element
     */
    public java.lang.String getOriginalDzColumnArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORIGINALDZCOLUMN$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "originalDzColumn" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetOriginalDzColumnArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ORIGINALDZCOLUMN$6, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "originalDzColumn" element
     */
    public org.apache.xmlbeans.XmlString xgetOriginalDzColumnArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORIGINALDZCOLUMN$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Returns number of "originalDzColumn" element
     */
    public int sizeOfOriginalDzColumnArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ORIGINALDZCOLUMN$6);
        }
    }
    
    /**
     * Sets array of all "originalDzColumn" element
     */
    public void setOriginalDzColumnArray(java.lang.String[] originalDzColumnArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(originalDzColumnArray, ORIGINALDZCOLUMN$6);
        }
    }
    
    /**
     * Sets ith "originalDzColumn" element
     */
    public void setOriginalDzColumnArray(int i, java.lang.String originalDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORIGINALDZCOLUMN$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(originalDzColumn);
        }
    }
    
    /**
     * Sets (as xml) array of all "originalDzColumn" element
     */
    public void xsetOriginalDzColumnArray(org.apache.xmlbeans.XmlString[]originalDzColumnArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(originalDzColumnArray, ORIGINALDZCOLUMN$6);
        }
    }
    
    /**
     * Sets (as xml) ith "originalDzColumn" element
     */
    public void xsetOriginalDzColumnArray(int i, org.apache.xmlbeans.XmlString originalDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORIGINALDZCOLUMN$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(originalDzColumn);
        }
    }
    
    /**
     * Inserts the value as the ith "originalDzColumn" element
     */
    public void insertOriginalDzColumn(int i, java.lang.String originalDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(ORIGINALDZCOLUMN$6, i);
            target.setStringValue(originalDzColumn);
        }
    }
    
    /**
     * Appends the value as the last "originalDzColumn" element
     */
    public void addOriginalDzColumn(java.lang.String originalDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORIGINALDZCOLUMN$6);
            target.setStringValue(originalDzColumn);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "originalDzColumn" element
     */
    public org.apache.xmlbeans.XmlString insertNewOriginalDzColumn(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(ORIGINALDZCOLUMN$6, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "originalDzColumn" element
     */
    public org.apache.xmlbeans.XmlString addNewOriginalDzColumn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ORIGINALDZCOLUMN$6);
            return target;
        }
    }
    
    /**
     * Removes the ith "originalDzColumn" element
     */
    public void removeOriginalDzColumn(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ORIGINALDZCOLUMN$6, i);
        }
    }
    
    /**
     * Gets array of all "channelDzColumn" elements
     */
    public java.lang.String[] getChannelDzColumnArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CHANNELDZCOLUMN$8, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "channelDzColumn" element
     */
    public java.lang.String getChannelDzColumnArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHANNELDZCOLUMN$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "channelDzColumn" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetChannelDzColumnArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CHANNELDZCOLUMN$8, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "channelDzColumn" element
     */
    public org.apache.xmlbeans.XmlString xgetChannelDzColumnArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHANNELDZCOLUMN$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Returns number of "channelDzColumn" element
     */
    public int sizeOfChannelDzColumnArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CHANNELDZCOLUMN$8);
        }
    }
    
    /**
     * Sets array of all "channelDzColumn" element
     */
    public void setChannelDzColumnArray(java.lang.String[] channelDzColumnArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(channelDzColumnArray, CHANNELDZCOLUMN$8);
        }
    }
    
    /**
     * Sets ith "channelDzColumn" element
     */
    public void setChannelDzColumnArray(int i, java.lang.String channelDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHANNELDZCOLUMN$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(channelDzColumn);
        }
    }
    
    /**
     * Sets (as xml) array of all "channelDzColumn" element
     */
    public void xsetChannelDzColumnArray(org.apache.xmlbeans.XmlString[]channelDzColumnArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(channelDzColumnArray, CHANNELDZCOLUMN$8);
        }
    }
    
    /**
     * Sets (as xml) ith "channelDzColumn" element
     */
    public void xsetChannelDzColumnArray(int i, org.apache.xmlbeans.XmlString channelDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHANNELDZCOLUMN$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(channelDzColumn);
        }
    }
    
    /**
     * Inserts the value as the ith "channelDzColumn" element
     */
    public void insertChannelDzColumn(int i, java.lang.String channelDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(CHANNELDZCOLUMN$8, i);
            target.setStringValue(channelDzColumn);
        }
    }
    
    /**
     * Appends the value as the last "channelDzColumn" element
     */
    public void addChannelDzColumn(java.lang.String channelDzColumn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHANNELDZCOLUMN$8);
            target.setStringValue(channelDzColumn);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "channelDzColumn" element
     */
    public org.apache.xmlbeans.XmlString insertNewChannelDzColumn(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(CHANNELDZCOLUMN$8, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "channelDzColumn" element
     */
    public org.apache.xmlbeans.XmlString addNewChannelDzColumn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHANNELDZCOLUMN$8);
            return target;
        }
    }
    
    /**
     * Removes the ith "channelDzColumn" element
     */
    public void removeChannelDzColumn(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CHANNELDZCOLUMN$8, i);
        }
    }
    
    /**
     * Gets the "innertTradeHandler" element
     */
    public boolean getInnertTradeHandler()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INNERTTRADEHANDLER$10, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "innertTradeHandler" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetInnertTradeHandler()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(INNERTTRADEHANDLER$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "innertTradeHandler" element
     */
    public void setInnertTradeHandler(boolean innertTradeHandler)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INNERTTRADEHANDLER$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INNERTTRADEHANDLER$10);
            }
            target.setBooleanValue(innertTradeHandler);
        }
    }
    
    /**
     * Sets (as xml) the "innertTradeHandler" element
     */
    public void xsetInnertTradeHandler(org.apache.xmlbeans.XmlBoolean innertTradeHandler)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(INNERTTRADEHANDLER$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(INNERTTRADEHANDLER$10);
            }
            target.set(innertTradeHandler);
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
