<#-- @ftlvariable name="commonTitle" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title><#if (commonTitle?? && commonTitle?has_content)>${commonTitle}<#else>爱童颜社区</#if> － ${pageTitle!}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" media="screen" href="${appConfigure.stylesUrl}/common/bs3.min.css" />
<link rel="stylesheet" media="screen" href="${appConfigure.stylesUrl}/common/bs3-theme.min.css" />
<#if (pageCss??)>${pageCss}</#if><#rt/>
<!--[if lt IE 9]>
<script type="text/javascript" src="${appConfigure.scriptsUrl}/libs/html5shiv.js"></script>
<![endif]-->
</head>

<body>
