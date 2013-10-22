<#-- @ftlvariable name="oauth" type="com.ibbface.interfaces.oauth.OAuthParameter" -->
<#assign pageTitle = "用户登录授权" />
<#include "/common/page_header.ftl" />

<div class="container">
    <div class="panel panel-primary">
        <#--<div class="panel-heading"><h2 class="panel-title">登录爱童颜社区</h2></div>-->
        <div class="panel-body">
            <form id="form_login" name="formLogin" method="post" action="/oauth/authorize">
                <input type="hidden" name="client_id" value="${oauth.clientId!}" />
                <input type="hidden" name="redirect_uri" value="${oauth.redirectUri!}" />
                <input type="hidden" name="response_type" value="${oauth.responseType!}" />
                <input type="hidden" name="state" value="${oauth.state!}" />
                <input type="text" id="txt_account" name="account" class="form-control"
                       placeholder="请输入登录帐号(邮箱|手机号)" autofocus="true" required />
                <input type="text" id="txt_password" name="password" class="form-control"
                       placeholder="请输入登录密码" />
                <label class="checkbox">
                    <input type="checkbox" name="rememberMe" value="true" /> 记住登录信息
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit">登 录</button>
            </form>
        </div>
        <div class="panel-footer">
            &copy; 2013-2014 爱童颜社区
        </div>
    </div>
</div>

<#include "/common/common_js.ftl" />
<#include "/common/page_footer.ftl" />