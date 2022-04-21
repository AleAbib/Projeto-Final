package com.adobe.learning.core.filters;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component(service = Filter.class,
        property = {
                EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
        })
@ServiceDescription("Demo to filter incoming requests")
@ServiceRanking(-700)
@ServiceVendor("Adobe")
public class AutorizacaoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {

        final SlingHttpServletRequest request = (SlingHttpServletRequest) servletRequest;
        final SlingHttpServletResponse response = (SlingHttpServletResponse) servletResponse;

        String URI = request.getRequestURI();

        HttpSession sessao = request.getSession();
        boolean SessaoUsuario = sessao.getAttribute("usuarioLogado") == null;

        if(URI.contains("registeruser") || URI.contains("login") || URI.contains("start.html")){
            chain.doFilter(request,response);
        } else if(SessaoUsuario){
            response.sendError(SlingHttpServletResponse.SC_UNAUTHORIZED, "User isn't logged in");
        } else{
            chain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }

}
