/*
 * An XML document type.
 * Localname: dzTradeContext
 * Namespace: http://www.dz.chinaebi.com.cn/webservice
 * Java type: cn.com.chinaebi.dz.webservice.DzTradeContextDocument
 *
 * Automatically generated - do not modify.
 */
package cn.com.chinaebi.dz.webservice.impl;
/**
 * A document containing one dzTradeContext(@http://www.dz.chinaebi.com.cn/webservice) element.
 *
 * This is a complex type.
 */
public class DzTradeContextDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements cn.com.chinaebi.dz.webservice.DzTradeContextDocument
{
    private static final long serialVersionUID = 1L;
    
    public DzTradeContextDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DZTRADECONTEXT$0 = 
        new javax.xml.namespace.QName("http://www.dz.chinaebi.com.cn/webservice", "dzTradeContext");
    
    
    /**
     * Gets the "dzTradeContext" element
     */
    public cn.com.chinaebi.dz.webservice.DzTradeContextType getDzTradeContext()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DzTradeContextType target = null;
            target = (cn.com.chinaebi.dz.webservice.DzTradeContextType)get_store().find_element_user(DZTRADECONTEXT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "dzTradeContext" element
     */
    public void setDzTradeContext(cn.com.chinaebi.dz.webservice.DzTradeContextType dzTradeContext)
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DzTradeContextType target = null;
            target = (cn.com.chinaebi.dz.webservice.DzTradeContextType)get_store().find_element_user(DZTRADECONTEXT$0, 0);
            if (target == null)
            {
                target = (cn.com.chinaebi.dz.webservice.DzTradeContextType)get_store().add_element_user(DZTRADECONTEXT$0);
            }
            target.set(dzTradeContext);
        }
    }
    
    /**
     * Appends and returns a new empty "dzTradeContext" element
     */
    public cn.com.chinaebi.dz.webservice.DzTradeContextType addNewDzTradeContext()
    {
        synchronized (monitor())
        {
            check_orphaned();
            cn.com.chinaebi.dz.webservice.DzTradeContextType target = null;
            target = (cn.com.chinaebi.dz.webservice.DzTradeContextType)get_store().add_element_user(DZTRADECONTEXT$0);
            return target;
        }
    }
}
