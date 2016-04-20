/*
 * An XML document type.
 * Localname: deductChannelOpenOrClose
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one deductChannelOpenOrClose(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class DeductChannelOpenOrCloseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseDocument
{
    private static final long serialVersionUID = 1L;
    
    public DeductChannelOpenOrCloseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DEDUCTCHANNELOPENORCLOSE$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "deductChannelOpenOrClose");
    
    
    /**
     * Gets the "deductChannelOpenOrClose" element
     */
    public cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType getDeductChannelOpenOrClose()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType target = null;
            target = (cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType)get_store().find_element_user(DEDUCTCHANNELOPENORCLOSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "deductChannelOpenOrClose" element
     */
    public void setDeductChannelOpenOrClose(cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType deductChannelOpenOrClose)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType target = null;
            target = (cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType)get_store().find_element_user(DEDUCTCHANNELOPENORCLOSE$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType)get_store().add_element_user(DEDUCTCHANNELOPENORCLOSE$0);
            }
            target.set(deductChannelOpenOrClose);
        }
    }
    
    /**
     * Appends and returns a new empty "deductChannelOpenOrClose" element
     */
    public cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType addNewDeductChannelOpenOrClose()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType target = null;
            target = (cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType)get_store().add_element_user(DEDUCTCHANNELOPENORCLOSE$0);
            return target;
        }
    }
}
