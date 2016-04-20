/*
 * An XML document type.
 * Localname: deductChannelUpdateRam
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one deductChannelUpdateRam(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class DeductChannelUpdateRamDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamDocument
{
    private static final long serialVersionUID = 1L;
    
    public DeductChannelUpdateRamDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DEDUCTCHANNELUPDATERAM$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "deductChannelUpdateRam");
    
    
    /**
     * Gets the "deductChannelUpdateRam" element
     */
    public cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType getDeductChannelUpdateRam()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType target = null;
            target = (cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType)get_store().find_element_user(DEDUCTCHANNELUPDATERAM$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "deductChannelUpdateRam" element
     */
    public void setDeductChannelUpdateRam(cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType deductChannelUpdateRam)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType target = null;
            target = (cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType)get_store().find_element_user(DEDUCTCHANNELUPDATERAM$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType)get_store().add_element_user(DEDUCTCHANNELUPDATERAM$0);
            }
            target.set(deductChannelUpdateRam);
        }
    }
    
    /**
     * Appends and returns a new empty "deductChannelUpdateRam" element
     */
    public cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType addNewDeductChannelUpdateRam()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType target = null;
            target = (cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType)get_store().add_element_user(DEDUCTCHANNELUPDATERAM$0);
            return target;
        }
    }
}
