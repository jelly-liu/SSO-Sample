<%@ page import="com.jelly.sso.util.GlobalConf" %>
<script type="text/javascript">
    var returnURLEncode = <%="'" + request.getAttribute(GlobalConf.PARAM_RETURN_RUL) + "'"%>;

    function gotoReturnURL(){
        window.location.href = returnURLEncode;
    }
</script>
!!Success!!<br/>
<button onclick="gotoReturnURL()">gotoReturnURL</button>