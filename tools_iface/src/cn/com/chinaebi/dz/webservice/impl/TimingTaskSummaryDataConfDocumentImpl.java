/*
 * An XML document type.
 * Localname: timingTaskSummaryDataConf
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one timingTaskSummaryDataConf(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class TimingTaskSummaryDataConfDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfDocument
{
    private static final long serialVersionUID = 1L;
    
    public TimingTaskSummaryDataConfDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIMINGTASKSUMMARYDATACONF$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "timingTaskSummaryDataConf");
    
    
    /**
     * Gets the "timingTaskSummaryDataConf" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType getTimingTaskSummaryDataConf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType)get_store().find_element_user(TIMINGTASKSUMMARYDATACONF$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "timingTaskSummaryDataConf" element
     */
    public void setTimingTaskSummaryDataConf(cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType timingTaskSummaryDataConf)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType)get_store().find_element_user(TIMINGTASKSUMMARYDATACONF$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType)get_store().add_element_user(TIMINGTASKSUMMARYDATACONF$0);
            }
            target.set(timingTaskSummaryDataConf);
        }
    }
    
    /**
     * Appends and returns a new empty "timingTaskSummaryDataConf" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType addNewTimingTaskSummaryDataConf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType)get_store().add_element_user(TIMINGTASKSUMMARYDATACONF$0);
            return target;
        }
    }
}
