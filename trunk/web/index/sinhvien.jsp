<%-- 
    Document   : sinhvien
    Created on : Oct 26, 2010, 12:01:47 AM
    Author     : truong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles-1.1" %>
<tiles:insert template="../WEB-INF/template/qlgvtemplate.jsp" flush="false">
    <tiles:put name="title" value="Quan Ly Diem Sinh Vien"/>
    <tiles:put name="header" value="header.jsp"/>
    <tiles:put name="body" value="../mainpage/sinhvien/sinhvien.jsp"/>
</tiles:insert>