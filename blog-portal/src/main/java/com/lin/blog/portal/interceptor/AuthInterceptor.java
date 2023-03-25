package com.lin.blog.portal.interceptor;

import com.lin.blog.portal.service.impl.JWTService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor
{
    @Resource
    private JWTService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String token = request.getParameter("token");
        Map<String, Object> map = jwtService.parseToken(token);

        String username = map.get("username").toString();

        String[] auth = new String[0];
        UserDetails userDetails = User.builder()
                .username(username)
                .password("")
                .authorities(auth).build();

        /*
        這行程式碼創建了一個 PreAuthenticatedAuthenticationToken 物件，該物件表示一個已經經過身分驗證的使用者身分。
        PreAuthenticatedAuthenticationToken 物件通常是在已經通過外部身分驗證系統驗證了使用者身分之後使用，例如單獨的 OAuth2 服務。
        PreAuthenticatedAuthenticationToken 物件是 Authentication 接口的一個實現，Authentication 接口代表了一個使用者的身分。

        這行程式碼中，PreAuthenticatedAuthenticationToken 的建構函式需要三個參數：
        1. Object principal：表示使用者的主要身分。
                            在這個例子中，principal 是 UserDetails 物件，它包含了當前已驗證的使用者的詳細資訊。
        2. Object credentials：表示使用者的驗證證書或密碼。
                            在這個例子中，credentials 是空字符串，因為在外部身分驗證系統中已經驗證過使用者身分，不需要再次輸入密碼。
        3. Collection<? extends GrantedAuthority> authorities：表示使用者的權限。
                            在這個例子中，authorities 是一個字串陣列，代表了使用者的權限清單。
         */
        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                AuthorityUtils.createAuthorityList(auth)
        );

        /*
        * WebAuthenticationDetails 物件提供有關當前身分驗證請求的詳細資訊，例如使用者的 IP 地址和瀏覽器資訊等。
        * 在這行程式碼中，WebAuthenticationDetails 建構函式的參數是 HttpServletRequest 物件，它包含當前請求的詳細資訊。
        * 這些詳細資訊會在後續的身分驗證過程中被使用，例如在運行一些安全檢查時，可能需要檢查使用者的 IP 地址是否與當前請求的 IP 地址相符。
        * 因此，這行程式碼確保了該身分驗證請求的詳細資訊被儲存在 authenticationToken 中，以供後續使用。
        * */
        authenticationToken.setDetails(
                new WebAuthenticationDetails(request)
        );

        /*
        這行程式碼將剛建立的 PreAuthenticatedAuthenticationToken 物件 authenticationToken 設置到當前的安全上下文中。
        更具體地說，它將 authenticationToken 設置為當前線程的安全上下文中的 Authentication 物件。
        這樣一來，當後續需要檢查使用者身分時，就可以從 SecurityContextHolder 中獲取 authenticationToken 物件，並使用它來進行驗證。

        SecurityContextHolder 是一個全域的容器，用於存儲當前線程的安全上下文。
        當使用者在應用程式中進行身分驗證時，可以將相關資訊存儲在 SecurityContextHolder 中，以便在應用程式中其他部分需要驗證使用者身分時使用。
        在這個例子中，當使用者的身分被驗證通過後，authenticationToken 物件被存儲在 SecurityContextHolder 中，以便在後續的應用程式中檢查使用者的身分。
        這樣可以確保應用程式在處理使用者請求時能夠正確識別使用者身分，保護應用程式的安全性。
         */
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return true;
    }
}