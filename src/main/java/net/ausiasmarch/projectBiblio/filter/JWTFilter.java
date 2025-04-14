package net.ausiasmarch.projectBiblio.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.ausiasmarch.projectBiblio.service.JWTService;

@Component
public class JWTFilter implements Filter {

    @Autowired
    JWTService JWTHelper;

    @Override
    public void doFilter(ServletRequest oServletRequest,
            ServletResponse oServletReponse,
            FilterChain oFilterChain)
            throws IOException, ServletException {

        HttpServletRequest oHttpServletRequest = (HttpServletRequest) oServletRequest;
        HttpServletResponse oHttpServletResponse = (HttpServletResponse) oServletReponse;

        String path = oHttpServletRequest.getRequestURI();

        if (path.equals("/register") || path.equals("/auth/register")) {  
            oFilterChain.doFilter(oServletRequest, oServletReponse);
            return;
        }

        if ("OPTIONS".equals(oHttpServletRequest.getMethod())) {
            oHttpServletResponse.setStatus(HttpServletResponse.SC_OK);
            oFilterChain.doFilter(oServletRequest, oServletReponse);
        } else {
            String sToken = oHttpServletRequest.getHeader("Authorization");
            if (sToken == null) {
                // oHttpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no
                // válido");
                // return;
                oFilterChain.doFilter(oServletRequest, oServletReponse);
            } else {
                if (!sToken.startsWith("Bearer ")) {
                    oHttpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no válido");
                    return;
                } else {
                    String sTokenReal = sToken.substring(7);

                    String email = JWTHelper.validateToken(sTokenReal);

                    if (email == null) {
                        oHttpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no válido");
                        return;
                    } else {
                        oHttpServletRequest.setAttribute("email", email);
                        oFilterChain.doFilter(oServletRequest, oServletReponse);
                    }
                }

            }

        }
    }

}
