package org.censorship.spring.service;

import org.censorship.spring.domains.CensorshipResponse;
import org.censorship.spring.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.Socket;

@Service
public class HttpCensorshipService {
    @Autowired
    DNSCensorshipDetectorService dnsCensorshipDetectorService;

    public CensorshipResponse detectHttpCensorship(String webAddress) throws Exception{
        int systemBufferedSize = getHttpResponse(webAddress);
        System.setProperty("http.proxyHost","103.89.56.50");
        System.setProperty("http.proxyPort","4145");
        int proxyBufferedSize = getHttpResponse(webAddress);
        System.setProperty("http.proxyHost","");
        System.setProperty("http.proxyPort","");
        CensorshipResponse censorshipResponse = new CensorshipResponse();
        if(systemBufferedSize==proxyBufferedSize){
            censorshipResponse.setStatus(Status.NOT_CENSORED);
            censorshipResponse.setMessage("NO HTTP CENSORSHIP DETECTED");
        }else{
            censorshipResponse.setStatus(Status.CENSORED);
            censorshipResponse.setMessage("HTTP CENSORSHIP DETECTED");
        }
        return censorshipResponse;
    }

    public int getHttpResponse(String webAddress) throws Exception{
        String refactoredWebAddress = dnsCensorshipDetectorService.refactorWebAddress(webAddress);
        Socket clientSocket;
        if(webAddress.contains("https://"))
        clientSocket= new Socket(InetAddress.getByName(refactoredWebAddress), 443);
        else
            clientSocket = new Socket(InetAddress.getByName(refactoredWebAddress), 80);
        if(clientSocket.isConnected()){
            return clientSocket.getReceiveBufferSize();
        }else
            return 0;
    }
}
