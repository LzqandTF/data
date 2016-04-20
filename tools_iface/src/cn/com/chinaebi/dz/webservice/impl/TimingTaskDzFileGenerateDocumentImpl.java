/*
 * An XML document type.
 * Localname: timingTaskDzFileGenerate
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one timingTaskDzFileGenerate(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class TimingTaskDzFileGenerateDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateDocument
{
    private static final long serialVersionUID = 1L;
    
    public TimingTaskDzFileGenerateDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIMINGTASKDZFILEGENERATE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "timingTaskDzFileGenerate");
    
    
    /**
     * Gets the "timingTaskDzFileGenerate" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType getTimingTaskDzFileGenerate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType)get_store().find_element_user(TIMINGTASKDZFILEGENERATE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "timingTaskDzFileGenerate" element
     */
    public void setTimingTaskDzFileGenerate(cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType timingTaskDzFileGenerate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType)get_store().find_element_user(TIMINGTASKDZFILEGENERATE$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType)get_store().add_element_user(TIMINGTASKDZFILEGENERATE$0);
            }
            target.set(timingTaskDzFileGenerate);
        }
    }
    
    /**
     * Appends and returns a new empty "timingTaskDzFileGenerate" element
     */
    public cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType addNewTimingTaskDzFileGenerate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType target = null;
            target = (cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType)get_store().add_element_user(TIMINGTASKDZFILEGENERATE$0);
            return target;
        }
    }
}
