<!-- Extended Permission View for Aperte Workflow  -->
<!-- @author: mzuchowski@bluesoft.net.pl -->


<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:if test="${aperteUser.login!=null}">
    <div class="apw apw_ex_perm">
        <table>
            <thead>
                <tr>
                    <td> Kategoria </td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td> Test </td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td> Test </td>
                </tr>
            </tfoot>
        </table>
    </div>
     <script type="text/javascript">
        var dispatcherPortlet = '<portlet:resourceURL id="dispatcher"/>';
     </script>
</c:if>
<c:if test="${aperteUser.login==null}">
    <%@include file="login.jsp" %>
</c:if>

