<html>
<head>
    <style>
        table {
            border-collapse: collapse;
            width: 200px;
        }

        th {
            border: 1px solid #666666;
        }

        td {
            border: 1px solid #666666;
        }
    </style>
</head>
<body>
<h2>SSO User Login</h2><hr/>
<form action="/loginOn" method="post">
    <input type="hidden" name="returnURL" value="${returnURL}"/>
    <table>
        <tr>
            <td width="100" align="right">userName:</td>
            <td align="left"><input type="text" name="userName" value="tom"/></td>
        </tr>
        <tr>
            <td>passWord:</td>
            <td><input type="text" name="passWord" value="123"/><br/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="submit"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><b>status ${m_s_g}</b></td>
        </tr>
    </table>

</form>
</body>
</html>
