<#-- @ftlvariable name="errorResponse" type="com.ibbface.interfaces.resp.ErrorResponse" -->
<#-- @ftlvariable name="oauth" type="com.ibbface.interfaces.oauth.OAuthParameter" -->
<#assign pageTitle = "用户登录授权" />
<#include "/common/page_header.ftl" />

<div class="container">
    <div class="panel panel-danger">
        <div class="panel-heading"><h2 class="panel-title">客户端授权错误</h2></div>
        <div class="panel-body">
            <p>您所访问的登录授权在<strong>爱童颜社区</strong><span class="text-danger">验证失败</span>！
                （error: ${errorResponse.error}）</p>
        </div>
        <div class="panel-footer">
            &copy; 2013-2014 爱童颜社区
        </div>
    </div>
</div>

<#include "/common/common_js.ftl" />
<#include "/common/page_footer.ftl" />