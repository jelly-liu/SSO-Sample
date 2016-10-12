<%@ page import="com.jelly.sso.util.GlobalConf" session="false" %>
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/jquery.cookie.js"></script>

<%
    String ssoUrl = "";
    ssoUrl = GlobalConf.SSO_SERVER_LOGIN_ON_URL + "?";
    String requestURL = request.getAttribute("requestURL").toString();
    ssoUrl += GlobalConf.PARAM_RETURN_RUL + "=" + requestURL;
    request.setAttribute("ssoUrl", "'" + ssoUrl + "'");
%>

<script type="text/javascript">
    var ssoURL = ${ssoUrl};

    function checkLogin(){
        var J_TOKEN = $.cookie('J_TOKEN');
        if(J_TOKEN){
            console.log('login already, J_TOKEN=' + J_TOKEN);
            return;
        }

        console.log('sys1 without J_TOKEN, will try to get from sso');

        $.ajax({
            type: "GET",
            url: "https://www.sys3-sso.com:9093/jsonp",
            dataType: "jsonp",
            jsonp: "JSONPCallback",
            success: function(data){
                if(!data || !data.flag){
                    console.log('sso without J_TOKEN also, will redirect to sso login page');
                    window.location.href = ssoURL;
                }else{
                    console.log('sso with J_TOKEN, will add J_TOKEN to sys1, J_TOKEN=' + data.json);
                    $.cookie('J_TOKEN', data.json, new Date((new Date()).getTime() + <%=GlobalConf.Token_Expired_Time_Seconds%> * 1000));
                }
            },
            error: function(){
                alert('call jsonp fail');
            }
        })
    }

    checkLogin();
</script>
