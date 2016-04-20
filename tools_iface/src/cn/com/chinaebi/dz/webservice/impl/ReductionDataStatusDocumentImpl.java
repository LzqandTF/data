/*
 * An XML document type.
 * Localname: reductionDataStatus
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ReductionDataStatusDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one reductionDataStatus(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ReductionDataStatusDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ReductionDataStatusDocument
{
    private static final long serialVersionUID = 1L;
    
    public ReductionDataStatusDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REDUCTIONDATASTATUS$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "reductionDataStatus");
    
    
    /**
     * Gets the "reductionDataStatus" element
     */
    public cn.com.chinaebi.dz.webservice.ReductionDataStatusType getReductionDataStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionDataStatusType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionDataStatusType)get_store().find_element_user(REDUCTIONDATASTATUS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "reductionDataStatus" element
     */
    public void setReductionDataStatus(cn.com.chinaebi.dz.webservice.ReductionDataStatusType reductionDataStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionDataStatusType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionDataStatusType)get_store().find_element_user(REDUCTIONDATASTATUS$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ReductionDataStatusType)get_store().add_element_user(REDUCTIONDATASTATUS$0);
            }
            target.set(reductionDataStatus);
        }
    }
    
    /**
     * Appends and returns a new empty "reductionDataStatus" element
     */
    public cn.com.chinaebi.dz.webservice.ReductionDataStatusType addNewReductionDataStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionDataStatusType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionDataStatusType)get_store().add_element_user(REDUCTIONDATASTATUS$0);
            return target;
        }
    }
}
