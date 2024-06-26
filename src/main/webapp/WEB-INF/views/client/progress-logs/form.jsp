<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.progress-logs.form.label.recordId" path="recordId"/>	
	<acme:input-textbox code="client.progress-logs.form.label.completeness" path="completeness"/>	
	<acme:input-textbox code="client.progress-logs.form.label.comment" path="comment"/>	
	<acme:input-moment code="client.progress-logs.form.label.registrationMoment" path="registrationMoment" readonly = "true"/>	
	<acme:input-textbox code="client.progress-logs.form.label.responsable" path="responsable"/>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
	<acme:input-textbox code="client.progress-logs.form.label.contract" path="contractTitle" readonly="true"/>	
	</jstl:if>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && published == false }">
			<acme:submit code="client.progress-logs.form.button.update" action="/client/progress-logs/update"/>
			<acme:submit code="client.progress-logs.form.button.delete" action="/client/progress-logs/delete"/>
			<acme:submit code="client.progress-logs.form.button.publish" action="/client/progress-logs/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.progress-logs.list.button.create" action="/client/progress-logs/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>