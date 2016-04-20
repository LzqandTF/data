/*
 * An XML document type.
 * Localname: timingTaskDzHandlerConf
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one timingTaskDzHandlerConf(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class TimingTaskDzHandlerConfDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfDocument
{
    private static final long serialVersionUID = 1L;
    
    public TimingTaskDzHandlerConfDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIMINGTASKDZHANDLERCONF$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "timingTaskDzHandlerConf");
    
    
    /**
     * Gets the "timingTaskDzHandlerConf" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType getTimingTaskDzHandlerConf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType)get_store().find_element_user(TIMINGTASKDZHANDLERCONF$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "timingTaskDzHandlerConf" element
     */
    public void setTimingTaskDzHandlerConf(cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType timingTaskDzHandlerConf)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType)get_store().find_element_user(TIMINGTASKDZHANDLERCONF$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType)get_store().add_element_user(TIMINGTASKDZHANDLERCONF$0);
            }
            target.set(timingTaskDzHandlerConf);
        }
    }
    
    /**
     * Appends and returns a new empty "timingTaskDzHandlerConf" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType addNewTimingTaskDzHandlerConf()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType)get_store().add_element_user(TIMINGTASKDZHANDLERCONF$0);
            return target;
        }
    }
}
