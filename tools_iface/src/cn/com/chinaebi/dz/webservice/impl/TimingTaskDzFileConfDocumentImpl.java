/*
 * An XML document type.
 * Localname: timingTaskDzFileConf
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one timingTaskDzFileConf(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class TimingTaskDzFileConfDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfDocument
{
    private static final long serialVersionUID = 1L;
    
    public TimingTaskDzFileConfDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIMINGTASKDZFILECONF$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "timingTaskDzFileConf");
    
    
    /**
     * Gets the "timingTaskDzFileConf" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType getTimingTaskDzFileConf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType)get_store().find_element_user(TIMINGTASKDZFILECONF$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "timingTaskDzFileConf" element
     */
    public void setTimingTaskDzFileConf(cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType timingTaskDzFileConf)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType)get_store().find_element_user(TIMINGTASKDZFILECONF$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType)get_store().add_element_user(TIMINGTASKDZFILECONF$0);
            }
            target.set(timingTaskDzFileConf);
        }
    }
    
    /**
     * Appends and returns a new empty "timingTaskDzFileConf" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType addNewTimingTaskDzFileConf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType)get_store().add_element_user(TIMINGTASKDZFILECONF$0);
            return target;
        }
    }
}
