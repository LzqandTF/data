/*
 * An XML document type.
 * Localname: reductionErrorDataStatus
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one reductionErrorDataStatus(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class ReductionErrorDataStatusDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusDocument
{
    private static final long serialVersionUID = 1L;
    
    public ReductionErrorDataStatusDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REDUCTIONERRORDATASTATUS$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "reductionErrorDataStatus");
    
    
    /**
     * Gets the "reductionErrorDataStatus" element
     */
    public cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType getReductionErrorDataStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType)get_store().find_element_user(REDUCTIONERRORDATASTATUS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "reductionErrorDataStatus" element
     */
    public void setReductionErrorDataStatus(cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType reductionErrorDataStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType)get_store().find_element_user(REDUCTIONERRORDATASTATUS$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType)get_store().add_element_user(REDUCTIONERRORDATASTATUS$0);
            }
            target.set(reductionErrorDataStatus);
        }
    }
    
    /**
     * Appends and returns a new empty "reductionErrorDataStatus" element
     */
    public cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType addNewReductionErrorDataStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType target = null;
            target = (cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType)get_store().add_element_user(REDUCTIONERRORDATASTATUS$0);
            return target;
        }
    }
}
