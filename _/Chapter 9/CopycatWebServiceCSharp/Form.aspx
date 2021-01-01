<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Form.aspx.cs" Inherits="Form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <p>
        Enter Data:
        <asp:TextBox ID="text" runat="server" EnableViewState="False" 
            ontextchanged="text_TextChanged"></asp:TextBox>
    </p>
    <p>
        <asp:Button ID="Submit" runat="server" EnableViewState="False" 
            onclick="Submit_Click" Text="Submit" />
    </p>
    <div>
    
        <asp:Label ID="lblCopycat" runat="server" EnableViewState="False"></asp:Label>
    
    </div>
    </form>
</body>
</html>
