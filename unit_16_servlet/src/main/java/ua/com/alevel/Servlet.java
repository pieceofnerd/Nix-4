package ua.com.alevel;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "sample-servlet", urlPatterns = "/ip")
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = -8948379822734246956L;

    private static final Logger log = LoggerFactory.getLogger(Servlet.class);

    private final Map<String, String> ipAddress;

    public Servlet() {
        this.ipAddress = new ConcurrentHashMap<>();
    }


    @Override
    public void init() {
        log.info(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();

        resp.setContentType("text/html");
        responseBody.println("<h1 align=\"center\">Sample Servlet GET method processing</h1>");
        responseBody.println("<h3 align=\"center\">Request from: " + req.getRemoteHost() + "</h3>");


        String ip = req.getRemoteAddr();
        String header = req.getHeader("User-Agent");
        ipAddress.put(ip, header);

        ipAddress.keySet().forEach(
                ipAddr -> {
                    if (ip.equals(ipAddr) && header.equals(ipAddress.get(ipAddr))) {
                        responseBody.println("<h5><b>" + ipAddr + " - " + ipAddress.get(ipAddr) + "</b></h5>");
                    } else
                        responseBody.println("<h5>" + ipAddr + " - " + ipAddress.get(ipAddr) + "</h5>");
                });
    }

    @Override
    public void destroy() {
        log.info(getServletName() + " destroyed");
    }


}
