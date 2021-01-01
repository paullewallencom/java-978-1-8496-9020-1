using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]
public class CopyCatService : System.Web.Services.WebService
{
    public CopyCatService()
    {
        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }

    [WebMethod]
    public string CopyMe(string Value) 
    {
        return Value.ToString();
    }

    [WebMethod]
    public string HelloWorld()
    {
        return "Hello World!";
    }

    [WebMethod]
    public string Complex(DataClass ComplexData)
    {
        return "success!";
    }
    [WebMethod]
    public DataClass Complex2(string strData)
    {
        return new DataClass();
    }
}
